## Теория: от XML до стилей сообщений SOAP

Чтобы знакомство с самыми простыми примерами из мира `JAX-WS` и `SOAP` было продуктивнее, 
самое время ненадолго остановиться и освежить теоретические знания.

Почти вся эта информация в этой главе была написана под впечатлением от книги `Apache CXF Web Service Development`,
авторы смогли очень кратко изложить основные теоретические положения.


### XML

`XML` - это аббревиатура от `Extensible Markup Language`.
 
* `XML` - это язык разметки, который определяет и описывает формат данных для обмена между двумя сторонами.
* Данные, `теги` или `элементы` структурированы в иерархическом порядке. 
* Для представления структурированной информации пользователь может создать свой собственный `тег`.
* `XML` считается стандартом представления структурированной информации.

Несколько важных терминов, связанных с XML-документами:

* `Пространство имён XML` (`XML Namespace`) - способ добиться уникальности элементов и атрибутов в XML-документе. 
Идея пространства имён сильно напоминает идею, стоящую за реализацией пакетов в Java. 
Конфликт имён классов решается за счёт их размещения в разных пакетах. 
Пространство имён определяется при помощи зарезервированного XML-атрибута `xmlns`, 
значение которого должно быть ссылкой `URI` (Uniform Resource Identifier), например,
`xmlns=http://www.w3.org/1999/xhtml` , или, используя префикс, `xmlns:xhtml=http://www.w3.org/1999/xhtml` .
* `XML Schema` это средство для определения структуры, содержания и семантики XML-документов. 
Модель данных XML-схемы включает в себя: словарь (имена элементов и атрибутов), модель содержимого 
(отношения и структура) и типы данных. Пример XML-схемы для описания видеоигры:

```xml
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
   <xs:element name="videoGame" type="VideoGame" />
   <xs:complexType name="VideoGame">
      <xs:sequence>
         <xs:element name="title" type="xs:string" />
         <xs:element name="publisher" type="xs:string" />
         <xs:element name="developer" type="xs:string" />
         <xs:element name="year" type="xs:date" />
         <xs:element name="region" type="xs:string" />
      </xs:sequence>
   </xs:complexType>
</xs:schema>
```

В этом примере, `xs` представляет собой `пространство имён` XML-схемы.

* Элемент `videoGame` имеет тип `VideoGame`. 
* Тип `VideoGame`, в свою очередь, представляет собой комплексный тип, состоящий из элементов
" `title` ", " `publisher` ", " `developer` ", и " `region` " типа `string`,
а так же " `year` " типа `date`. 

В приведёном ниже листинге представлен валидный XML-документ, соответствующий этой схеме.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<videoGame xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="videoGame.xsd">
   <title>Aladdin</title>
   <publisher>Sega, Disney Software</publisher>
   <developer>Virgin Games</developer>
   <year>1993-11-11</year>
   <region>US, EU, JP</region>
</videoGame>
```


### SOAP (Simple Object Access Protocol)

`SOAP` (простой протокол доступа к объектам) это протолок обмена XML-сообщениями по сети. 
Обычно он используется поверх HTTP-протокола. 
SOAP-сообщение состоит из конверта `SOAP Envelope`, который включает в себя всю информацию запроса.
В конверте находятся: `необязательный заголовок` и `тело`. В заголовок, как правило, помещают информацию,
связанную с безопасностью и транзакциями, в то время как тело содержит XML-сообщение.

В приведённом ниже листинге показан пример SOAP-сообщения, содержащего информацию о видеоигре:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://tv-games.ru/game/megadrive/Aladdin.html" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
   <soapenv:Header />
   <soapenv:Body>
      <ns1:VideoGame>
         <ns1:title>Aladdin</ns1:title>
         <ns1:publisher>Sega, Disney Software</ns1:publisher>
         <ns1:developer>Virgin Games</ns1:developer>
         <ns1:year>1993-11-11</ns1:year>
         <ns1:region>US, EU, JP</ns1:region>
      </ns1:VideoGame>
   </soapenv:Body>
</soapenv:Envelope>
```


### WSDL (Web Services Description language)

`WSDL` - язык описания веб-сервисов, основанный на XML.
В WSDL описывается коллекция ендпоинтов, которые веб-сервис открывает для обмена сообщениями.
Эти ендпоинты называются портами (`port`). Каждый ендпоинт состоит из двух частей:

* Первая часть: абстрактное определение операций (аналогично методам в Java), предоставляемых сервисом,
и сообщений (типы входных и выходных параметров для методов), которые необходимы для вызова сервиса. 
Набор абстрактных определений операций так же называется типом порта (`port type`).
* Вторая часть: конкретная привязка (`binding`) этих абстрактных определений операций к конкретному сетевому протоколу, 
место расположения сервиса и формат сообщений для этого сервиса.
Привязка WSDL описывает, как сервис связан с протоколом обмена сообщениями, в нашем случае, с протоколом обмена сообщениями SOAP. 

Как правило, `WSDL-файлы` генерируются автоматически на основе классов. 

Следующий блок кода демонстрирует сервис для получения информации об игре. В нём используется XML-схема `VideoGame`. 
Не пугайтесь, знакомьтесь и читайте комментарии к коду:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:ns1="http://schemas.xmlsoap.org/soap/http" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://soap.leonis.md/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="VideoGameImplService" targetNamespace="http://soap.leonis.md/">
   <wsdl:types>

      <!-- Определение схемы для элемента VideoGame. Это результат вызова сервиса VideoGame. -->
      <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://soap.leonis.md/" attributeFormDefault="unqualified" elementFormDefault="unqualified" targetNamespace="http://soap.leonis.md/">
         <xs:element name="VideoGame" type="tns:videoGame" />
         <xs:element name="getVideoGame" type="tns:getVideoGame" />
         <xs:element name="getVideoGameResponse" type="tns:getVideoGameResponse" />
         <xs:complexType name="getVideoGame">
            <xs:sequence>
               <xs:element minOccurs="0" name="arg0" type="tns:string" />
            </xs:sequence>
         </xs:complexType>
         <xs:complexType name="videoGame">
            <xs:sequence>
               <xs:element minOccurs="0" name="title" type="xs:string" />
               <xs:element minOccurs="0" name="publisher" type="xs:string" />
               <xs:element minOccurs="0" name="developer" type="xs:string" />
               <xs:element minOccurs="0" name="year" type="xs:date" />
               <xs:element minOccurs="0" name="region" type="xs:string" />
            </xs:sequence>
         </xs:complexType>
         <xs:complexType name="getVideoGameResponse">
            <xs:sequence>
               <xs:element minOccurs="0" name="return" type="xs:videoGame" />
            </xs:sequence>
         </xs:complexType>
      </xs:schema>
   </wsdl:types>

   <!-- Определяем сообщения для сервиса VideoGame. -->
   <wsdl:message name="getVideoGameResponse">
      <wsdl:part element="tns:getVideoGameResponse" name="parameters" />
   </wsdl:message>
   <wsdl:message name="getVideoGame">
      <wsdl:part element="tns:getVideoGame" name="parameters" />
   </wsdl:message>

   <!-- Определяем операции для сервиса VideoGame. -->
   <wsdl:portType name="GetVideoGameProcess">
      <wsdl:operation name="getVideoGame">
         <wsdl:input message="tns:getVideoGame" name="getVideoGame" />
         <wsdl:output message="tns:getVideoGameResponse" name="getVideoGameResponse" />
      </wsdl:operation>
   </wsdl:portType>

   <!-- Определяем SOAP Binding для VideoGame Process. -->
   <wsdl:binding name="GetVideoGameProcessImplServiceSoapBinding" type="tns:GetVideoGameProcess">
      <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http" />
      <wsdl:operation name="getVideoGame">
         <soap:operation soapAction="" style="document" />
         <wsdl:input name="getVideoGame">
            <soap:body use="literal" />
         </wsdl:input>
         <wsdl:output name="getVideoGameResponse">
            <soap:body use="literal" />
         </wsdl:output>
      </wsdl:operation>
   </wsdl:binding>

   <!-- Определение сервиса для VideoGame. -->
   <!-- Здесь указывается фактический URL, по которому можно найти сервис. -->
   <wsdl:service name="GetVideoGameProcessImplService">
      <wsdl:port binding="tns:GetVideoGameProcessImplServiceSoapBinding" name="GetVideoGameProcessImplPort">
         <soap:address location="http://localhost:8080/GetVideoGameProcess" />
      </wsdl:port>
   </wsdl:service>
</wsdl:definitions>
```

Несмотря на то, что WSDL является подмножеством XML, синтаксически он заметно проще. 
В нём не задействованы элементы `element` и `attribute`. 
Элементами верхнего уровня (сущностями) в WSDL являются `message`, `portType`, `binding` и `service`.

* `wsdl:import`: Отдельный документ WSDL 1.1 с описаниями, включаемый в текущий документ. Является необязательным.
* `wsdl:types`: Определение отправляемых и получаемых сервисом XML-сообщений (XSD формат)
* `wsdl:message`: Определение сообщений, используемых Web-сервисом
* `wsdl:portType`: Список абстрактных операций (`operation`), которые могут быть выполнены над сообщениями
* `wsdl:binding`: Описание привязки сервисов (способов доставки сообщений) к протоколу и стилю вызова (Document/RPC)
* `wsdl:service`: Описание сервиса в целом, как правило, включая один или несколько элементов <wsdl:port> 
с информацией доступа для элементов <wsdl:binding>


* Элемент `portType` - это набор семантически связанных операций, во многом подобный интерфейсу в языке программировании. 
* Элемент `binding` не обязан охватывать все элементы `operation` данного `portType`. 
Например, определенные в одном `portType` элементы operation могут использовать различные транспортные протоколы. 
* Поскольку `service` - это набор элементов `port`, а `port` ссылается на отдельный `binding`, 
можно определять сервисы, которые не предлагают все `operation`, определенные в `portType`. 
Однако, наличие таких сервисов является признаком плохо разделенных элементов `portType`.


### Веб-сервис

Веб-сервис (`Web Service`) это программная система, идентифицируемая по `URI`, 
чьи публичные интерфейсы и привязки определены и описаны с использованием XML (в частности, `WSDL`). 
Благодаря этим определениям, сервисом могут пользоваться другие программные системы. 
Эти системы могут взаимодействовать с веб-сервисом в порядке, установленном в его описании, 
используя XML-сообщения, передаваемые по интернет-протоколам.

#### Два подхода в разработке веб-сервисов

В настоящее время распространение получили два архитектурных стиля для разработки веб-сервисов: 
* `SOAP` (Simple Object Access Protocol) и 
* `REST` (Representational State Transfer).

В веб-сервисах на основе `SOAP` поставщик услуг публикует контракт (`WSDL-файл`) сервиса. 
Потребитель услуг обычно генерирует код клиента веб-сервиса на основе этого WSDL-файла.

В случае `RESTful` веб-сервисов такого контракта попросту нет. Клиент должен знать формат сообщений, 
например, `XML` или `JSON` (Java Script Object Notation), а так же операции, который поддерживает сервис. 
Доступ к веб-сервису обычно осуществляется с использованием стандартных HTTP-методов, таких как `GET` или `POST`.

Выбор между `SOAP` или `REST` зависит от требований, предъявляемых к приложению.

* Если нужно передавать и получать простые XML-сообщения, без лишней головомойки, то `RESTful` веб-сервисы сгодятся.
* Но в случае больших энтерпраз систем, где нужно определять контракты для согласования поставщика и потребителя, 
такие как `WSDL`, а так же придерживаться различных спецификаций веб-сервисов (`WS Specifications`), 
например, безопасности веб-сервисов для корпоративного внедрения, то разумнее реализовать `SOAP` веб-сервисы. 
При этом надо иметь понятие о стилях SOAP-сообщений.

#### Стили сообщений веб-сервисов SOAP

* Существует два типа стилей сообщений SOAP: `Document` и `RPC`. 
Эти стили определяются в WSDL-документе в разделе `binding` как атрибут привязки SOAP (атрибут `style` тега `soap:binding`).
* Привязка SOAP может быть либо `кодированной`, либо `текстовой` (`encoded` / `literal`) (атрибут `use` тега `soap:body`). 
Кодирование это ряд правил сериализации, описанных в разделе 5 спецификации SOAP. 
Практичнее всего использовать кодирование в сочетании с RPC (удалённым вызовом процедур). 
Текстовый формат не подразумевает какой-либо трансформации сообщения, оно передаётся как есть.

Стиль `Document`, как и следует из названия, имеет дело с XML-документами, 
которые придерживаются чётко определённых контрактов, обычно определяемых XML-схемами. 
Схема задаёт формат сообщений, которыми будут обмениваться поставщик веб-услуг и их потребитель. 
Этот стиль, в сочетании с текстовым форматом (`Document/Literal`) является предпочтительныым для достижения совместимости.

Стиль `RPC` (Remote Procedure Call), с другой стороны, указывает, что тело SOAP является XML-представлением метода. 
Для того, чтобы без проблем можно было сериализовать параметры метода в SOAP-сообщение, 
и потом десериализовать обратно, в спецификации SOAP определён стандартный набор правил кодирования. 
Так что обычно RPC используется в сочетании с правилами кодирования SOAP, это записывается как `RPC/Encoded`. 
Однако, никто не запрещает писать в стиле `RPC/Literal`, но сообщения будут по-прежнему ограничены форматом RPC, 
ориентированным на вызов методов, в то время, как сами сообщения, в виду отсутствия XML-схемы, не могут быть валидированы. 

**На практике RPC стиль по-возможности следует избегать, поскольку у него могут быть проблемы с совместимостью.**

*В связи с тем, что стандарт `SOAP` появился раньше `WSDL`, в WSDL был включён архаичный стиль `RPC/Encoded`. 
`Document` универсальнее и поддерживает валидацию.*

Для веб-сервисов SOAP разработано множество спецификаций, связанных с безопасностью, надёжностью обмена сообщениями, 
управлением и транзакциями для слабосвязанных систем. Все эти спецификации построены на основе XML и стандартов SOAP.

### Стили параметров

Чтобы эта глава не была слишком короткой, давайте рассмотрим здесь аннотацию уровня веб-сервиса `@SOAPBinding`. 
Это значения, принимаемые по умолчанию:

`@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)`

А это альтернативные значения:

`@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE, style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED)`

Нам осталось разобраться со стилями параметров (`ParameterStyle`).

`ParameterStyle.Bare` и `ParameterStyle.Wrapped` влияют только на определение сообщений запроса и ответа в WSDL-файле,
а так же на то, как будут форматироваться тела сообщений, передаваемых/получаемых от веб-сервиса.

* `BARE`: каждый параметр помещается в тело сообщения как дочерний элемент корня сообщения. 
* `WRAPPED`: все входящие параметры "заворачиваются" в один-единственный элемент. Используется по-умолчанию.

В качестве простого примера рассмотрим веб-сервис с методом "`test`", у которого два входящих параметра "`string1`" и "`string2`".
Он возвращает строку как "`rstring`".

При использовании стиля `ParameterStyle.BARE` имена параметров будут видны в `WSDL`.

Сообщение запроса (WSDL):

```xml
<message name="test">
   <part name="string1" element="tns:string1" />
   <part name="string2" element="tns:string2" />
</message>
```

Сообщение ответа (WSDL):

```xml
<message name="testResponse">
   <part name="rstring" element="tns:rstring" />
</message>
```

В XSD-схеме типы `test` и `testResponse` определены следующим образом, и элементы в WSDL-файле точно им соответствуют:

```xml
   <xs:complexType name="test">
      <xs:sequence>
         <xs:element name="string1" type="xs:string" minOccurs="0" />
         <xs:element name="string2" type="xs:string" minOccurs="0" />
      </xs:sequence>
   </xs:complexType>
   <xs:complexType name="testResponse">
      <xs:sequence>
         <xs:element name="rstring" type="xs:string" minOccurs="0" />
      </xs:sequence>
   </xs:complexType>
```

При использовании стиля `ParameterStyle.WRAPPED` сообщения запроса и ответа будут завёрнуты как
"`parameter`" и "`result`" соответственно. Они будут ссылаться на соответствующие элементы в XSD-схеме.

Сообщение запроса (WSDL):

```xml
<message name="test">
   <part name="parameters" element="tns:test" />
</message>
```

Сообщение ответа (WSDL):

```xml
<message name="testResponse">
   <part name="result" element="tns:testResponse" />
</message>
```

XSD при этом никак не изменится.

**Разница только в том, как сообщения будут описаны в `WSDL-файле`. Поскольку всё генерируется автоматически, то для программиста разницы особой нет.**

Надо добавить, что этот пример был для типа `Document`, поскольку, у типа `RPC` нет `XSD-схемы`, и применим только стиль сообщений `WRAPPED`.

[<< назад](chapter-3.md) | [⌂ оглавление](../README.md) | [далее >>](chapter-4.md)