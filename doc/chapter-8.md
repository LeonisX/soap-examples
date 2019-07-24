## SOAP HelloWorld для Apache CXF (POJO)

[Ссылка на репозиторий](./soap-apache-cxf-pojo)

Все примеры, связанные с `Apache CXF` взяты из официальных примеров, доступных вместе с фреймворком. [Ссылка для скачивания](https://cxf.apache.org/download.html).

Код, где было возможно, был упрощён. Копирайты на пол страницы тоже убраны. Сорри.

Итак, `Java First POJO DEMO`

Мы научимся, как на основе Apache CXF разрабатывать сервисы, используя подход "code first".

В примере используется связывание `JAXB Data`, но никто не мешает переключиться на связывание `Aegis Data`, раскомментировав эти четыре строчки:

(Client.java)

```java
//import org.apache.cxf.aegis.databinding.AegisDatabinding;
//factory.getServiceFactory().setDataBinding(new AegisDatabinding());
```

(Server.java)

```java
//import org.apache.cxf.aegis.databinding.AegisDatabinding;
//svrFactory.getServiceFactory().setDataBinding(new AegisDatabinding());
```

### pom.xml

Чего-то сильно нового здесь нет. Создано два профиля для запуска сервера и клиента, а так же подключены три зависимости:

* `cxf-rt-frontend-jaxws`: Apache CXF Runtime JAX-WS Frontend
* `cxf-rt-transports-http`: Apache CXF Runtime HTTP Transport
* `cxf-rt-transports-http-jetty` (фактически нужна только если используется `CXFServlet`)


Кстати, если при запуске встречается следующая ошибка:

```
INFO: Creating Service {http://server.hw.demo/}HelloWorld from class demo.hw.server.HelloWorld
Jul 23, 2019 6:20:58 PM org.apache.cxf.transport.http.HTTPTransportFactory getDestination
SEVERE: Cannot find any registered HttpDestinationFactory from the Bus.
Exception in thread "main" javax.xml.ws.WebServiceException: org.apache.cxf.service.factory.ServiceConstructionException
	at org.apache.cxf.jaxws.EndpointImpl.doPublish(EndpointImpl.java:375)
	at org.apache.cxf.jaxws.EndpointImpl.publish(EndpointImpl.java:255)
	at org.apache.cxf.jaxws.spi.ProviderImpl.createAndPublishEndpoint(ProviderImpl.java:141)
	at javax.xml.ws.Endpoint.publish(Endpoint.java:272)
	at demo.hw.server.Server.<init>(Server.java:33)
	at demo.hw.server.Server.main(Server.java:38)
Caused by: org.apache.cxf.service.factory.ServiceConstructionException
	... 5 more
Caused by: java.io.IOException: Cannot find any registered HttpDestinationFactory from the Bus.
```

А именно: `java.io.IOException: Cannot find any registered HttpDestinationFactory from the Bus`, то скорее всего в зависимостях не хватает именно `cxf-rt-transports-http-jetty`.

Ещё три зависимости понадобятся, если мы будем логировать всю активность.

* `slf4j-jdk14` - Связываем JDK 1.4 и SLF4j
* `jcl-over-slf4j` - JCL (Java Class Libraries) поверх SLF4j
* `cxf-rt-features-logging`- продвинутые возможности логирования от CXF. [подробнее](https://cxf.apache.org/docs/message-logging.html)

### Сервис

Традиционно, начинаем с интерфейса веб-сервиса. Структуру пакетов трогать не будем, пусть глаза привыкают к разнообразию.

```java
package demo.hw.server;

public interface HelloWorld {

    String sayHi(String text);
}
```

У нашего сервиса будет операция `sayHi`, принимающая имя, и приветствующая клиентов при каждом вызове.

Сервис:

```java
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}
```

Опять же, голый POJO, никаких аннотаций и непонятных конструкций. Простейшая реализация.

### Сервер

Поскольку мы не указали ни одной аннотации, то придётся поработать ручками и головой. Ctrl+C, Ctrl+V.

```java
import java.util.Collections;

import org.apache.cxf.ext.logging.LoggingFeature;
//import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ServerFactoryBean;

public class Server {

    protected Server() {
        HelloWorldImpl helloworldImpl = new HelloWorldImpl();
        ServerFactoryBean svrFactory = new ServerFactoryBean();
        svrFactory.setServiceClass(HelloWorld.class);
        svrFactory.setAddress("http://localhost:9000/Hello");
        svrFactory.setServiceBean(helloworldImpl);
        svrFactory.setFeatures(Collections.singletonList(new LoggingFeature()));
        //svrFactory.getServiceFactory().setDataBinding(new AegisDatabinding());
        svrFactory.create();
    }

    public static void main(String[] args) throws Exception {
        new Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
```

Обратите внимание на метод `main()`. Сервер тоже зависает в режиме ожидания, но в нашем примере мы его принудительно выключим спустя 5 минут. Этого можно и не делать.

Код, в принципе, не сложный. Мы создаём `ServerFactoryBean` и даём ему всю необходимую информацию для запуска сервера:

* Класс сервиса (интерфейс)
* Адрес, по которому сервис будет доступен
* Проинициализированный бин класса веб-сервиса
* Какие-то другие плюшки, например, логирование 

#### ServerFactoryBean

Это вспомогательный класс. Он принимает объект `org.apache.cxf.service.Service` и выставляет его как ендпоинт на стороне сервера.
В случае отсутствия `Service`, он автоматически создаётся с помощью `ReflectionServiceFactoryBean`.

Самый простой сценарий использования `ServerFactoryBean` такой:

```java
  ServerFactoryBean sf = new ServerFactoryBean();
  sf.setServiceClass(MyService.class);
  sf.setAddress("http://localhost:8080/MyService");
  sf.create();
```

В этом примере мы даже не передали реализацию интерфейса. Естественно, в клиенте мы словим ошибку, 
но факт остаётся фактом - ендпоинт был создан. Логи:

```
INFO: Creating Service {http://server.hw.demo/}HelloWorld from class demo.hw.server.HelloWorld
Invoke sayHi()....
Exception in thread "main" org.apache.cxf.binding.soap.SoapFault: Could not instantiate service class demo.hw.server.HelloWorld because it is an interface.
```

Естественно, эту фабрику сервисов можно настраивать так, как душе угодно. Например: 

```java
  ReflectionServiceFactory serviceFactory = new ReflectionServiceFactory();
  serviceFactory.setServiceClass(MyService.class);
  
  ..
  
  //Тут мы настраиваем фабрику сервисов...
  serviceFactory.setWrapped(false);
  
  ...
  
  ServerFactoryBean sf = new ServerFactoryBean();
  sf.setServiceFactory(serviceFactory);
  sf.setAddress("http://localhost:8080/MyService");
  sf.create();
```

#### ReflectionServiceFactoryBean

При помощи рефлексии изучает класс иIntrospects a class and builds a {@link Service} from it. If a WSDL URL is
 * specified, a Service model will be directly from the WSDL and then metadata
 * will be filled in from the service class. If no WSDL URL is specified, the
 * Service will be constructed directly from the class structure.
 */
//CHECKSTYLE:OFF:NCSS    -   This class is just huge and complex
public class ReflectionServiceFactoryBean

### Клиент

```java
//import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import demo.hw.server.HelloWorld;

public final class Client {

    public static void main(String[] args) {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        if (args != null && args.length > 0 && !"".equals(args[0])) {
            factory.setAddress(args[0]);
        } else {
            factory.setAddress("http://localhost:9000/Hello");
        }
        //factory.getServiceFactory().setDataBinding(new AegisDatabinding());
        HelloWorld client = factory.create(HelloWorld.class);
        System.out.println("Invoke sayHi()....");
        System.out.println(client.sayHi(System.getProperty("user.name")));
        System.exit(0);
    }
}
```

**Если у клиента будет несколько конструкторов, то обязательно создайте конструктор по-умолчанию, `Client()`.**

Если не обращать внимание, на незнакомый класс `ClientProxyFactoryBean`, то можно увидеть, что нечто похожее мы уже писали.
Можно передать адрес сервиса в командной строке, в другом случае будет использоваться заранее определённый адрес.

Мы создаём фабрику `ClientProxyFactoryBean`, с её помощью создаём клиента и вызываем его метод.

#### ClientProxyFactoryBean

Этот класс создаёт клиента веб-сервиса, реализующего указанный класс сервиса. Основная его задача - принять все необходимые данные и создать экземпляр клиента. Пример:

```java
  ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
  factory.setServiceClass(YourServiceInterface.class);
  YourServiceInterface client = (YourServiceInterface) factory.create(); 
```

Отсюда можно обратиться к объекту `Client`, лежащему в основе:
 
```java
  Client cxfClient = ClientProxy.getClient(client);
```

Если заглянуть в его исходный код, то сразу можно обратить внимание на обилие геттеров и сеттеров для:

* Пароля
* Имени пользователя
* Адреса
* Класса сервиса
* Пути к WSDL
* QName (квалифицированного имени ендпоинта)
* QName (квалифицированного имени сервиса)
* Различный свойств
* Bus (почитаем о нём ниже)
* И так далее

#### ClientProxy

Хранит в себе объекты Client и Endpoint. Endpoint он берёт из Client при своей инициализации.
С его помощью можно:
 
 * Вызывать методы (операции), в том числе и синхронно
 * Получать доступ к контексту запроса
 * Получать доступ к контексту ответа
 * Client

#### Client

Набор методов типа `invoke` и `invokeWrapped` для вызова операций, а так же геттеры для следующих объектов:

* Контекст запроса (`Map<String, Object>`)
* Контект ответа (`Map<String, Object>`)
* `Endpoint`
* `Conduit`
* `ConduitSelector`
* `Bus`

#### Bus

Шина это самое центровое место в `CXF`. Его основная задача - предоставлять доступ к различным расширениям,
таким как `DestinationFactoryManager`, `ConduitFactoryManager`, `BindingFactoryManager`, и так далее.
etc). В зависимости от реализации шины, она так же может отвечать за связывание различных внутренностей `CXF`.
 */

### Сборка и запуск в Maven

В каталоге, где находится `README.md`, притаился `pom.xml` файл, который всё сделает за нас. 

Полезные команды:

```
  mvn install   (собрать проект)
  mvn -Pserver  (запустить сервер)
  mvn -Pclient  (запустить клиента)
```

Чтобы убрать весь код, который был сгенерирован из `WSDL-файла`, а так же `.class` файлы, достаточно выполнить "`mvn clean`".

### Запуск

Запускаем сервер, затем клиент.

Консоль сервера (логи SLF4J убраны):

```
Server ready...
sayHi called
```

Консоль клиента (логи SLF4J убраны):

```
Invoke sayHi()....
Hello user
```

[<< назад](chapter-7.5.md) | [⌂ оглавление](../README.md) | [далее >>](chapter-9.md)