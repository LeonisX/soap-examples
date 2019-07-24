## Динамическое подключение к серверу

[Ссылка на репозиторий](./soap-java-se-rpc)

**Если не нужно, то просто листаем далее.**

Врядли это кому-нибудь пригодится на производстве, но есть возможность динамически подключаться к необходимым веб-службам.
Пример такого клиента: 

```java
URL url = new URL("http://localhost:8080/for/dispatcher?wsdl");
QName qname = new QName("http://soap.leonis.md/", "HelloWorldWSService");
Service service = Service.create(url, qname);

Dispatch<Source> helloDispatch =
       service.createDispatch(
               new QName("http://soap.leonis.md/", "HelloWorldWSPort"),
               Source.class, Service.Mode.PAYLOAD);
String request = "<ns2:helloWorldWebMethod xmlns:ns2=\"http://soap.leonis.md/\">\n" +
       "\t\t\t\t<arg0>Leonis</arg0>\n" +
       "\t\t\t</ns2:helloWorldWebMethod>";
Source requestSource = new StreamSource(new StringReader(request));
Source responseSource = helloDispatch.invoke(requestSource);
Result responseResult = new StreamResult(System.out);
Transformer transformer = TransformerFactory.newInstance().newTransformer();
transformer.transform(responseSource, responseResult);
```

Результат в консоли:

`<?xml version="1.0" encoding="UTF-8"?><ns2:helloWorldWebMethodResponse xmlns:ns2="http://soap.leonis.md/" xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"><return>Hello World JAX-WS Leonis</return></ns2:helloWorldWebMethodResponse>Disconnected from the target VM, address: '127.0.0.1:49650', transport: 'socket'`

Имеется возможность вызывать даже те операции, которые не были определены в WSDL файле

```java
Service helloService = Service.create(new QName("http://samples/hello", "HelloService"));
helloService.addPort(
     new QName("http://samples/hello", "HelloPort"),
     SOAPBinding.SOAP11HTTP_BINDING,
     "http://localhost/hello"
);
```

Естественно, вызываемый сервис должен быть доступен.

`WebServiceProvider` предоставляет аналогичную функциональность для динамического создания веб-служб.

*Поскольку не удалось быстро привести в чувства динамический веб-сервис, оставим его для самостоятельного изучения, если кому-то случайно это понадобится.*

[<< назад](chapter-4.md) | [⌂ оглавление](../README.md) | [далее >>](chapter-6.md)