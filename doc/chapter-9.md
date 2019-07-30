## SOAP HelloWorld для Apache CXF (JAX-WS)

[Ссылка на репозиторий](../soap-apache-cxf-jax-ws)

Итак, `Java First demo using JAX-WS APIs`

Мы научимся, как на основе `Apache CXF` с помощью `JAX-WS` API разрабатывать сервисы, используя подход "code first".

**Не использовать этот пример с EE-контейнерами, вроде Wildfly!!!**

В этом примере используется `CXF Servlet`, и такой подход очень плохо подходит для деплоя в такие контейнеры, как `Wildfly`.
Дело в том, что у Wildfly уже есть предустановленные модули `org.apache.cxf`, `org.apache.cxf.impl`, `org.apache.cxf.services-sts` и `org.apache.cxf.ws-security`,
зато нет модуля для `Spring`, то есть деплой в этот контейнер - задача не тривиальная.

В официальной документации описано два решения, но сразу скажем, что это путь боли и страданий, по которому идти не стоит.

* Отключить в `WildFly` всё, что мешает, по-сути, превратив его в банальный контейнер сервлетов.
* Поверить, что (документация по интеграции WildFly и Apache CXF)[https://cxf.apache.org/docs/application-server-specific-configuration-guide.html#ApplicationServerSpecificConfigurationGuide-JBossApplicationServer] написана верно и следовать её шагам. 
Как минимум, надо будет убрать из `pom.xml` (`scope=provided`) всё, что связано с `Apache CXF` и `Java EE`. Потом написать `web.xml`, ...

Оба подхода в производстве скорее всего неприменимы, надо реализовать более грамотное решение и отказаться от `CXF Servlet`.

*Для наших учебных целей подойдёт контейнер сервлетов `Tomcat`.*

И не отчаивайтесь так. В следующей главе мы всё-таки заставим `Apache CXF` подружиться с `WildFly`.

### pom.xml

По сравнению с предыдущим проектом (`POJO`) добавился код для деплоя в `Tomcat 8`.

Apache CXF сильно завязан на Spring. Так что, от греха подальше, надо добавить две эти зависимости:

```
        <!--java.lang.NoClassDefFoundError: Lorg/springframework/web/context/support/XmlWebApplicationContext;-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!--java.lang.ClassNotFoundException: org.springframework.context.ApplicationListener-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
```

### Сервис

Традиционно, начинаем с интерфейса веб-сервиса.

```java
@WebService
public interface HelloWorld {

    String sayHi(String text);

    /* Более продвинутый пример передачи интерфейса. JAX-WS/JAXB напрямую интерфейсы не поддерживает.
     * Чтобы с этим справиться, должны быть написаны специальные XmlAdapter классы.
     */
    String sayHiToUser(User user);


    /* Передача Map.
     * JAXB так же не поддерживает мапы. Зато он отлично справляется со списками (List).
     * Так что, и тут не обойтись без XmlAdapter для перевода мап в бины, которые JAXB сможет использовать.
     */
    @XmlJavaTypeAdapter(IntegerUserMapAdapter.class)
    Map<Integer, User> getUsers();
}
```

Интерфейс `User`:

```java
@XmlJavaTypeAdapter(UserAdapter.class)
public interface User {

    String getName();
}
```

И его `XmlAdapter`:

```java
public class UserAdapter extends XmlAdapter<UserImpl, User> {

    public UserImpl marshal(User v) {
        if (v instanceof UserImpl) {
            return (UserImpl)v;
        }
        return new UserImpl(v.getName());
    }

    public User unmarshal(UserImpl v) {
        return v;
    }
}
```

Класс `UserImpl`, который JAXB сможет обработать.

```java
@XmlType(name = "User")
public class UserImpl implements User {

    private String name;

    public UserImpl() {
    }

    public UserImpl(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }
}
```

Конструктор по умолчанию `UserImpl` необходим, чтобы избежать следующую ошибку: 
`Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Unmarshalling Error: null`

Класс `IntegerUserMapAdapter` преобразует `Map<Integer, User>` в `IntegerUserMap` и обратно.

```java
public class IntegerUserMapAdapter extends XmlAdapter<IntegerUserMap, Map<Integer, User>> {
    public IntegerUserMap marshal(Map<Integer, User> v) {
        IntegerUserMap map = new IntegerUserMap();
        for (Map.Entry<Integer, User> e : v.entrySet()) {
            IntegerUserMap.IntegerUserEntry iue = new IntegerUserMap.IntegerUserEntry();
            iue.setUser(e.getValue());
            iue.setId(e.getKey());
            map.getEntries().add(iue);
        }
        return map;
    }

    public Map<Integer, User> unmarshal(IntegerUserMap v) {
        Map<Integer, User> map = new LinkedHashMap<>();
        for (IntegerUserMap.IntegerUserEntry e : v.getEntries()) {
            map.put(e.getId(), e.getUser());
        }
        return map;
    }
}
```

Класс `IntegerUserMap` можно будет скормить JAXB.

```java
@XmlType(name = "IntegerUserMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class IntegerUserMap {
    @XmlElement(nillable = false, name = "entry")
    private List<IntegerUserEntry> entries = new ArrayList<>();

    public List<IntegerUserEntry> getEntries() {
        return entries;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "IdentifiedUser")
    public static class IntegerUserEntry {
        //Map keys cannot be null
        @XmlElement(required = true, nillable = false)
        Integer id;

        User user;

        void setId(Integer k) {
            id = k;
        }
        Integer getId() {
            return id;
        }

        void setUser(User u) {
            user = u;
        }
        User getUser() {
            return user;
        }
    }
}
```

Сервис:

```java
@WebService(endpointInterface = "demo.hw.server.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    private Map<Integer, User> users = new LinkedHashMap<>();

    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }

    public String sayHiToUser(User user) {
        System.out.println("sayHiToUser called");
        users.put(users.size() + 1, user);
        return "Hello "  + user.getName();
    }

    public Map<Integer, User> getUsers() {
        System.out.println("getUsers called");
        return users;
    }
}
```

`IntelliJ IDEA` подсвечивает метод `sayHiToUser` красным и справедливо сообщает, что: `Web method problem class User not public or does not allow instantiation`

Естественно, ведь это интерфейс. Но `IDEA`, видимо, ещё не совсем хорошо знакома с фреймворком `Apache CXF` и его особенностями. Возможные решения:

* Не обращать внимания, и не тратить время, всё и так будет работать.
* Вместо `User` передавать `UserImpl`.
* Убрать аннотацию `@WebService`.

### Сервер

Поскольку мы не указали ни одной аннотации, то придётся поработать ручками и головой. Ctrl+C, Ctrl+V.

```java
public class Server {

    private Server() {
        System.out.println("Starting Server");
        HelloWorldImpl implementor = new HelloWorldImpl();
        String address = "http://localhost:9000/helloWorld";
        Endpoint.publish(address, implementor, new LoggingFeature());
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

Этот код в целом очень похож на самые ранние примеры, ничего нового, от CXF здесь только `LoggingFeature`.

### Клиент

```java
public final class Client {

    private static final QName SERVICE_NAME = new QName("http://server.hw.demo/", "HelloWorld");
    private static final QName PORT_NAME = new QName("http://server.hw.demo/", "HelloWorldPort");

    public static void main(String[] args) {
        Service service = Service.create(SERVICE_NAME);
        // Адрес ендпоинта
        String endpointAddress = "http://localhost:9000/helloWorld";
        // Если веб-сервис задеплоен в Tomcat (standalone либо embedded), то ендпоинт должен быть таким:
        // String endpointAddress = "http://localhost:8080/soap_apache_cxf_jax_ws/services/hello_world";

        // Добавляем в Service порт
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);

        HelloWorld hw = service.getPort(HelloWorld.class);
        System.out.println(hw.sayHi("World"));

        User user = new UserImpl("World");
        System.out.println(hw.sayHiToUser(user));

        user = new UserImpl("Galaxy");
        System.out.println(hw.sayHiToUser(user));

        user = new UserImpl("Universe");
        System.out.println(hw.sayHiToUser(user));

        System.out.println();
        System.out.println("Users: ");
        Map<Integer, User> users = hw.getUsers();

        for (Map.Entry<Integer, User> e : users.entrySet()) {
            System.out.println("  " + e.getKey() + ": " + e.getValue().getName());
        }
    }
}
```

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

Консоль сервера:

```
Connected to the target VM, address: '127.0.0.1:57760', transport: 'socket'
Starting Server
Jul 23, 2019 6:23:27 PM org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean buildServiceFromClass
INFO: Creating Service {http://server.hw.demo/}HelloWorld from class demo.hw.server.HelloWorld
Jul 23, 2019 6:23:28 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be http://localhost:9000/helloWorld
Jul 23, 2019 6:23:28 PM org.eclipse.jetty.util.log.Log initialized
INFO: Logging initialized @1159ms to org.eclipse.jetty.util.log.Slf4jLog
Jul 23, 2019 6:23:28 PM org.eclipse.jetty.server.Server doStart
INFO: jetty-9.4.18.v20190429; built: 2019-04-29T20:42:08.989Z; git: e1bc35120a6617ee3df052294e433f3a25ce7097; jvm 1.8.0_211-b12
Jul 23, 2019 6:23:28 PM org.eclipse.jetty.server.AbstractConnector doStart
INFO: Started ServerConnector@782a4fff{HTTP/1.1,[http/1.1]}{localhost:9000}
Jul 23, 2019 6:23:28 PM org.eclipse.jetty.server.Server doStart
INFO: Started @1458ms
Jul 23, 2019 6:23:28 PM org.eclipse.jetty.server.handler.ContextHandler setContextPath
WARNING: Empty contextPath
Jul 23, 2019 6:23:28 PM org.eclipse.jetty.server.handler.ContextHandler doStart
INFO: Started o.e.j.s.h.ContextHandler@6cc0bcf6{/,null,AVAILABLE}
Server ready...
Jul 23, 2019 6:23:59 PM org.apache.cxf.ext.logging.slf4j.Slf4jEventSender send
INFO: REQ_IN
    Address: http://localhost:9000/helloWorld
    HttpMethod: POST
    Content-Type: text/xml; charset=UTF-8
    ExchangeId: ca532c22-1c9b-4832-b021-ad449f478ab1
    ServiceName: HelloWorld
    PortName: HelloWorldImplPort
    PortTypeName: HelloWorld
    Headers: {SOAPAction="", Accept=*/*, Cache-Control=no-cache, User-Agent=Apache-CXF/3.3.2, connection=keep-alive, content-type=text/xml; charset=UTF-8, Host=localhost:9000, Pragma=no-cache, Content-Length=185}
    Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:sayHi xmlns:ns2="http://server.hw.demo/"><arg0>World</arg0></ns2:sayHi></soap:Body></soap:Envelope>

sayHi called
Jul 23, 2019 6:23:59 PM org.apache.cxf.ext.logging.slf4j.Slf4jEventSender send
INFO: RESP_OUT
    Content-Type: text/xml
    ResponseCode: 200
    ExchangeId: ca532c22-1c9b-4832-b021-ad449f478ab1
    ServiceName: HelloWorld
    PortName: HelloWorldImplPort
    PortTypeName: HelloWorld
    Headers: {}
    Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:sayHiResponse xmlns:ns2="http://server.hw.demo/"><return>Hello World</return></ns2:sayHiResponse></soap:Body></soap:Envelope>
...
```

Сервер поднимается на основе `Jetty`, так же логируются запросы и ответы, что очень удобно.

Консоль клиента:

```
Jul 23, 2019 6:23:58 PM org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean buildServiceFromClass
INFO: Creating Service {http://server.hw.demo/}HelloWorld from class demo.hw.server.HelloWorld
Jul 23, 2019 6:23:59 PM org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean buildServiceFromClass
INFO: Creating Service {http://server.hw.demo/}HelloWorld from class demo.hw.server.HelloWorld
Hello World
Hello World
Hello Galaxy
Hello Universe

Users: 
  1: World
  2: Galaxy
  3: Universe
```

### Деплой WAR-архива в Tomcat

1.) Обновить значение `endpointAddress` в классе `client.Client` на адрес задеплоенного сервиса, вроде:
"http://localhost:8080/soap_apache_cxf_jax_ws/services/hello_world".

2.) `Standalone Tomcat`: Вручную скопировать `WAR` файл в каталог `webapps` Tomcat, или, в случае Maven
и плагина Tomcat Maven Plugin (http://tomcat.apache.org/maven-plugin-2.2/tomcat7-maven-plugin/index.html) 
использовать команду `mvn tomcat7:redeploy`.  

Для `embedded Tomcat 7`: Выполнить `mvn tomcat7:run-war`
Для `embedded Tomcat 8`: Выполнить `mvn cargo:run`

До запуска клиента (`mvn -Pclient`) стоит убедиться, что сгенерированный WSDL можно посмотреть в браузере:
http://localhost:8080/soap_apache_cxf_jax_ws/services/hello_world?wsdl

### Деплой WAR архива в WildFly

**PLEASE, DON'T DO IT!!!**

Если выбран `WildFly`, то следует сполна насладиться `Java EE` интеграцией, и подход с `CXF Servlet` не годится.

[<< назад](chapter-7.md) | [⌂ оглавление](../README.md) | [далее >>](chapter-9.md)