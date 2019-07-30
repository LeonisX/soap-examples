## Apache CXF клиент как EJB

[Ссылка на репозиторий](../soap-apache-cxf-ejb-client)
[Ссылка на репозиторий](../soap-apache-cxf-ejb-server)

В этой, последней главе, мы напишем более реалистичный клиент, который будет деплоиться в `WildFly`, и вызываться по `REST` запросу.
Фактически, не важно как клиент будет вызываться, по таймеру, или по JMS-сообщению, главное принцип.
Клиент будет обращаться к серверу, защищённому BASIC аутентификацией.


### Maven

Поскольку нам придётся использовать достаточно большой зверинец зависимостей, очень легко запутаться в версиях
и нарваться на `Jar Hell`. Чтобы этого не случилось, можно воспользоваться уже готовым решением: 
[коллекцией BOM файлов для WildFly](https://github.com/wildfly/boms). 
(Детали из книги)[http://www.mastertheboss.com/jboss-frameworks/maven-tutorials/jboss-maven/using-wildfly-boms].

В них перечисленны все библиотеки, существующие в конкретной версии `WildFly`, и что важно, их версии.

В родительский pom.xml надо добавить следующую зависимость:

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.wildfly.bom</groupId>
            <artifactId>wildfly-javaee8</artifactId>
            <scope>import</scope>
            <type>pom</type>
            <version>17.0.1.Final</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Если планируется использовать предыдущие версии `WildFly`, то просто выберите в `Git` нужую ветку. Пример для версии 10:

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.wildfly.bom</groupId>
            <artifactId>wildfly-javaee7</artifactId>
            <scope>import</scope>
            <type>pom</type>
            <version>10.0.0.Final</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Другой `BOM`, который можно использовать, это `wildfly-javaee8-with-tools`. 
В него дополнительно включены зависимости, связанные с тестированием `Arquillian/JUnit`.
[Все BOM- файлы](https://mvnrepository.com/artifact/org.wildfly.bom)

Выгода от этого подхода заключается в том, что теперь в модулях не надо указывать версии зависимостей, они будут
автоматически браться из этого BOM.

Например:

```
        <dependency>
            <groupId>javax.enterprise</groupId>
            <artifactId>cdi-api</artifactId>
            <scope>provided</scope>
        </dependency>
```

Обратите внимание на область видимости "`provided`". Поскольку эти библиотеки уже есть в контейнере `Wildfly`, 
то нет нужды повторно упаковывать их в `WAR` файл.

Дополнительно стоит указать путь до репозитория `JBoss`. Это гарантирует, что все зависимости скачаются как полагается.

```
    <properties>
        <maven.repository.url>https://repository.jboss.org/nexus/content/groups/public/</maven.repository.url>
    </properties>
    
    <repositories>
        <repository>
            <releases>
                <enabled>true</enabled>
                <updatePolicy>never</updatePolicy>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>never</updatePolicy>
            </snapshots>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Repository Group</name>
            <url>${maven.repository.url}</url>
            <layout>default</layout>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Repository Group</name>
            <url>${maven.repository.url}</url>
        </pluginRepository>
    </pluginRepositories>
```

Посмотреть полные примеры можно в [GitHub репозитории WildFly](https://github.com/wildfly/quickstart).


### WildFly

Воспользуемся последней версией, `WildFly 17` с `Java EE 8`.

Краткий обзор версий:

| Application Server  | Java EE |
| ------------- | ------------- |
| JBoss AS 7  | 6  |
| WildFly 8  | 7  |
| ...  | ...  |
| WildFly 12  | 7 + preview of 8  |
| WildFly 13  | 7 + preview of 8  |
| WildFly 14+  | 8  |


#### Запуск сервера вручную:

В документации `WildFly` описано 2 профиля: `Full` и `Web`.

Поскольку приложение будет очень простым, то нам будет достаточно профиля настроек, используемого по умолчанию (`standalone.xml`).
Он соответствует `веб-профилю JavaEE` + некоторые расширения, вроде `RESTFul` веб-сервисов и поддержки удалённого вызова `EJB3`. 

А это: логирование, DataSource, поиск JNDI, Servlet 4.0, JSP 2.3, JavaMail 1.6, веб-сервисы (SOAP и REST), JSF 2.3, JPA 2.2, EJB 3.2 и CDI 2.0.

Команда запуска:

`standalone.bat`

Если в приложении понадобится использовать `JMS-сообщения`, то будет нужен полный профиль:

`standalone.bat -c standalone-full.xml`

Полный профиль имеет сделующие дополнительные расширения:

* org.jboss.as.jsr77 (J2EE Management 1.1)
* **org.wildfly.extension.messaging-activemq**
* org.wildfly.iiop-openjdk

Это весь `JavaEE` стек. [Посмотреть различия](https://docs.wildfly.org/17/Getting_Started_Guide.html)

Так же любой из этих двух профилей можно запустить с `High-Availability` настройками, а это дополнительно: 
определение нодов через `JGroups` и репликация сессий через `Infinispan`:

* org.jboss.as.clustering.jgroups
* org.jboss.as.modcluster
* org.wildfly.extension.clustering.singleton

Примеры:

```
standalone.bat -c standalone-ha.xml
standalone.bat -c standalone-full-ha.xml
```


#### Запуск сервера из IntelliJ IDEA Ultimate

Подробно на этом останавливаться не будем, можно лишь сказать, что при настройке сервера (`JBoss Server`) `Remote` означает, что сервер будет
запускаться вручную и `IDEA` будет деплоить артефакты в него. Плюс такого подхода - не тратится время на запуск контейнера, а
минус - нет полной поддержки отладки от IDEA. `Local` - IDE сама запускает сервер при каждом деплое.

Перед настройкой сервера уже должен быть скомпилирован `WAR-архив`, чтобы указать его во вкладке `Deployments`.


### Разработка приложения

Итак, мы пишем более реалистичный клиент, который будет деплоиться в `WildFly`, и вызываться по `REST` запросу.
Клиент будет обращаться к серверу, защищённому `BASIC` аутентификацией.

* При разработке будем активно пользоваться помощью фреймворка `Apache CXF`
* Для вызова клиента напишем один `REST`-ендпоинт, `/rest/client`
* Для ещё большей простоты создадим титульную страницу, откуда можно будет вызвать этот ендпоинт


#### Сервер

Серверная часть, по сравнению с предыдущими примерами в техническом плане не станет откровением.


##### web.xml

Защищаем все ендпоинты с помощью `Basic Authentication`. 
Чтобы всё заработало, надо будет ещё создать пользователя приложения (`application user`) на уровне контейнера `WildFly`.
Как это сделать мы рассматривали ранее.

* login: `user`
* password: `password`
* role: `USER_ROLE`


##### jboss-web.xml

Меняем префикс сервиса на `/ws`


##### beans.xml

Включаем `Contexts and Dependency Injection` (CDI), чтобы заработало внедрение зависимостей.


##### Модель данных

Возьмём что-то посложнее, но не слишком замороченное, например:

```java
public class User {
    private int id;
    private String name;
    private BigDecimal uid;
    private Dates dates = new Dates();
    private Map<String, Integer> notes = new HashMap<>();
    private List<String> hobbies = new ArrayList<>();
    ...
}
```

Не забываем конструктор по умолчанию, чтобы не нарваться на:

`Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Unmarshalling Error: null`

Как мы дальше увидим, никаких особенных адаптеров для интерфейсов `Map` и `List` не требуется.

```java
public class Dates {
    private Date date = new Date();
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlSchemaType(name = "date")
    private LocalDate localDate = LocalDate.now();
    ...
}
```

`LocalDate` был выбран не случайно. Если сериализация `Date` проходит без проблем, то в случае `LocalDate` (и `LocalDateTime`) 
всё иначе. У них нет конструкторов по умолчанию, поэтому мы написали простенький адаптер `LocalDateAdapter`.

Если его не подключить, то клиент увидит следующее:

```
On client side: org.jboss.resteasy.spi.UnhandledException: javax.xml.ws.soap.SOAPFaultException: Unmarshalling Error:
Caused by: javax.xml.bind.UnmarshalException
```

Аннотация `@XmlSchemaType(name = "date")` нужна по другой причине. Сервер прекрасно будет работать и без неё, но мы помним,
что на основе классов сервера сгенерируем WSDL-файл, а на его основе сгенерируем классы клиента. Так вот. По умолчанию
клиент будет отправлять данные в формате `dateTime`, что-то вроде `2002-05-30T09:30:10+06:00`, а это закончится так:

```
org.jboss.resteasy.spi.UnhandledException: javax.xml.ws.soap.SOAPFaultException: Unmarshalling Error: java.time.format.DateTimeParseException: Text '' could not be parsed at index 0 
Caused by: org.apache.cxf.binding.soap.SoapFault: Unmarshalling Error: java.time.format.DateTimeParseException: Text '' could not be parsed at index 0
[com.sun.istack.SAXParseException2; lineNumber: 1; columnNumber: 220; java.time.format.DateTimeParseException: Text '' could not be parsed at index 0] 
Caused by: com.sun.xml.bind.api.AccessorException: java.time.format.DateTimeParseException: Text '' could not be parsed at index 0
```

Даже после подключения адаптера `IntelliJ IDEA` будет подсвечивать методы сервиса красным, утверждая, что: 

`Web Method problem: Class java.time.LocalDate not public or does not allow instantiation`

Это ложное срабатывание, проблема давно известна, но её почему-то никак не удосужатся починить.


##### Веб-сервис

Чтобы было интереснее, мы создадим сразу несколько методов. Обратите внимание, что аннотация `@WebParam` 
задаётся на уровне интерфейса. Это не случайно. На уровне метода она почему-то не работает и аргументы именуются как: `arg0`, ...


##### Генерация WSDL-файла

Воспользуемся утилитой из комплекта `Apache CXF`:

`java2ws.bat -wsdl -o C:\projects\soap-examples\soap-apache-cxf-ejb-server\src\main\resources\wsdl\userService.wsdl -address http://localhost:8080/ws/userService -cp C:\projects\soap-examples\soap-apache-cxf-ejb-server\target\classes md.leonis.ws.server.UserServiceImpl`

Ключом `-address http://localhost:8080/ws/userService` мы перезапишем `URI` для доступа к сервису.
Этого можно и не делать. В клиенте мы покажем способ, как можно вручную задать этот путь.


#### Клиент

`pom.xml` заметно подрос, но без указанных зависимостей ничего не заработает :(

Несмотря на то, что `Apache CXF` берёт на себя львиную долю работы, сгенерированные классы придётся слегка обработать напильником.

##### Генерация файлов клиента

`wsdl2java.bat -p md.leonis.ws.client -client -wsdlLocation classpath:wsdl/userService.wsdl userService.wsdl`

Если задан ключ `-client`, то дополнительно сгенерируется файл `UserService_UserServiceImplPort_Client.java`.
Отчасти мы позаимствует его код, создавая своего клиента.

Ключ `-impl` позволяет создать файл `UserServiceImplPortImpl.java`, правда, толку в нём никакого, поэтому, не используем.

*Ранее мы специально не использовали многие аннотации. Самое время посмотреть сгенерированные классы и познакомиться с ними.*

##### jboss-deployment-structure.xml

Это та самая магия, которая заставит работать нашего клиента.Нужно подключить модуль `org.apache.cxf.impl`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jboss-deployment-structure>
    <deployment>
        <dependencies>
            <module name="org.apache.cxf.impl">
                <imports>
                    <include path="META-INF"/>
                    <include path="META-INF/cxf"/>
                </imports>
            </module>
        </dependencies>
    </deployment>
</jboss-deployment-structure>
```

java.lang.NoClassDefFoundError: org/apache/cxf/jaxws/JaxWsProxyFactoryBean
Caused by: java.lang.ClassNotFoundException: org.apache.cxf.jaxws.JaxWsProxyFactoryBean from [Module "deployment.soap-apache-cxf-ejb-client-1.0-SNAPSHOT.war" from Service Module Loader]

##### Титульная страница

Очень простой `HTML`-файл со ссылкой на `REST`-ендпоинт. `index.html` будет доступен по адресу: http://localhost:8080


##### REST Endpoint

http://localhost:8080/rest/client

Дополнительно можно передать параметр запроса:

http://localhost:8080/rest/client?name=Leonis

##### ClientService

Давайте сначала рассмотрим конструктор:

```java
public ClientService() {
        /*UserService_Service ss = new UserService_Service();
        userService = ss.getUserServiceImplPort();*/

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(UserService.class);
        factory.setAddress("http://localhost:8080/ws/userService");
        userService = (UserService) factory.create();
        
        Client client = ClientProxy.getClient(userService);
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());
        
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        conduit.getAuthorization().setUserName("user");
        conduit.getAuthorization().setPassword("password");
    }
```

Здесь используется два разных способа создания `userService`:

* Первый, закомментированный вариант, отлично подходит для случая, когда ничего менять не требуется. 
Впрочем, если значения в классе `UserService_Service` не устраивают, то их можно передать в его конструкторе вручную.
Речь о параметрах `WSDL_LOCATION` и `SERVICE`.
* Второй вариант интересен тем, что для создания класса сервиса мы используем `JaxWsProxyFactoryBean`.
Фабрика хорошо справляется с созданием экземпляра сервиса, но если вручную не задать его адрес, то получится:

```
org.jboss.resteasy.spi.UnhandledException: javax.xml.ws.soap.SOAPFaultException: Fault string, and possibly fault code, not set
```

Метод `factory.setAddress("http://localhost:8080/ws/userService")` задаёт значение для `org.apache.cxf.message.Message.ENDPOINT_ADDRESS`.

Ниже мы получаем ссылку на объект `org.apache.cxf.endpoint.Client` и начинаем его настраивать.

В первую очередь, добавляем перехватчики для логирования исходящих и входящих сообщений. Они пригодятся в целях отладки.

* org.apache.cxf.ext.logging.LoggingInInterceptor;
* org.apache.cxf.ext.logging.LoggingOutInterceptor;

На их примере не составит труда написать свои перехватчики, например, для сохранения запросов. 
Для этого достаточно получить OutputStream: `CachedOutputStream cos = message.getContent(CachedOutputStream.class);`

Пример логирования:

```
15:32:36,041 INFO  [org.apache.cxf.services.UserService.REQ_OUT] (default task-1) REQ_OUT
    Address: http://localhost:8080/ws/userService
    HttpMethod: POST
    Content-Type: text/xml
    ExchangeId: ae8851dd-f392-4c33-b814-65d2ec6e5173
    ServiceName: userService
    PortName: UserServiceImplPort
    PortTypeName: UserService
    Headers: {Authorization=Basic dXNlcjpwYXNzd29yZA==, SOAPAction="", Accept=*/*}
    Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:create xmlns:ns2="http://server.ws.leonis.md/"/></soap:Body></soap:Envelope>
    
    15:32:36,129 INFO  [org.apache.cxf.services.UserService.RESP_IN] (default task-1) RESP_IN
        Content-Type: text/xml;charset=UTF-8
        ResponseCode: 200
        ExchangeId: ae8851dd-f392-4c33-b814-65d2ec6e5173
        ServiceName: userService
        PortName: UserServiceImplPort
        PortTypeName: UserService
        Headers: {Cache-Control=no-cache, no-store, must-revalidate, connection=keep-alive, content-type=text/xml;charset=UTF-8, Expires=0, Pragma=no-cache, Content-Length=347, Date=Tue, 30 Jul 2019 12:32:36 GMT}
        Payload: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:createResponse xmlns:ns2="http://server.ws.leonis.md/"><return><id>1</id><name>newUser</name><uid>1</uid><dates><date>2019-07-30T15:32:36.107+03:00</date><localDate>2019-07-30</localDate></dates><notes/></return></ns2:createResponse></soap:Body></soap:Envelope>
```

Далее от `Client` мы получаем ссылку на `org.apache.cxf.transport.http.HTTPConduitHTTPConduit`. 
Слово "conduit" переводися как канал или трубопровод. 
`HTTPConduit` отвечает за передачу данных по транспортным протоколам `HTTP` и `HTTPS`. 

Мы будем обращаться к защищённым ендпоинтам, поэтому надо указать имя пользователя и пароль. 
Если этого не сделать, то получим стандартную ошибку `401`:

```
org.jboss.resteasy.spi.UnhandledException: javax.xml.ws.WebServiceException: Could not send Message.
Caused by: org.apache.cxf.transport.http.HTTPException: HTTP response '401: Unauthorized' when communicating with http://localhost:8080/ws/userService
```

Работающие примеры можно найти в соответствующих модулях.

Над названиями сервисов можно ещё поработать, например, убрать "Service".

[<< назад](chapter-9.md) | [⌂ оглавление](../README.md) | [далее >>](utils.md)