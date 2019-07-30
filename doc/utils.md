## Утилиты wsgen, wsimport, xcj, java2ws, wsdl2java

Утилиты `wsgen`, `wsimport` и `xcj` идут в комплекте с `JDK`, минимум до версии 1.8.

Утилиты `java2ws`, `wsdl2java`, а так же несколько других, идут в комплекте с фреймворком `Apache CXF`.

### Генерация WSDL

Утилиты `wsgen` и `java2ws` генерируют WSDL-файлы на основе классов сервиса, остальные же, напротив,
используют WSDL/XSD для генерации классов для клиентской стороны.

Обе эти утилиты потребляют скомпилированные классы `.class` а не исходные коды `.java`.

Список ключей:

| wsgen  | java2ws | Описание
| ------------- | ------------- | ------------- |
| -help  | -?,-h,-help  | Справка |
| -version,-fullversion  | -version,-v  | Версия утилиты |
| -cp,-classpath | -cp,-classpath | Путь к классам SEI (Service Endpoint Interface). CXF позоволяет читать из zip/jar файлов. |
|  | classname | Имя SEI класса |
| -d | -classdir | Путь для сгенерированных выходных файлов |
| -r  | -d | Путь для сгенерированных ресурсов, таких как WSDL-файлы |
| -s | -s | Путь для сгенерированных исходников |
| -wsdl[:protocol] | -wsdl, -soap12 | Генерировать WSDL-файл одной из двух версий. По умолчанию генерируется версия 1.1. Варианты: [soap1.1, Xsoap1.2]. [Xsoap1.2] только в сочетании с -extension |
|  | -o | Имя генерируемого WSDL-файла |
| -servicename | -servicename | Имя сервиса, используемое в генерируемом WSDL. Использовать в связке с -wsdl |
| -portname | -portname | Имя порта, используемое в генерируемом WSDL. Использовать в связке с -wsdl |
|  | -address | Адрес порта |
|  | -t | Пространство имён, используемое в генерируемом WSDL |
| -inlineSchemas |  | Включить XSD-схему в генерируемый WSDL. Использовать в связке с -wsdl |
|  | -createxsdimports | Напротив, вынести XSD-схемы в отдельные файлы, оставив только их импорт в WSDL |
| -encoding |  | Кодировка исходных файлов |
| -extension -Xnocompile |  | Всякие расширения. Например, не компилировать сгенерированные исходники |
| -J<javacOption> |  | Ключ для javac |
| -keep |  | Не перезаписывать сгенерированные ранее файлы |
| -x |  | External Web Service Metadata xml descriptor |
| -verbose | -verbose | Выводить всю активность в консоль |
|  | -quiet | Напротив, ничего не выводить в консоль |
|  | --databinding | Привязка данных (aegis или jaxb). По умолчанию это jaxb для фронтенда jaxws, и aegis для simple фронтенда |
|  | -frontend | Фронтенд: jaxws либо simple |
|  | -wrapperbean | Генерировать wrapper и fault бины |
|  | -client | Генерировать код клиента |
|  | -server | Генерировать код сервера |
|  | -ant | Генерировать build.xml для Ant |
|  | -beans | Путь к файлу, определяющему бины Spring для настройки привязки данных |

Примеры:

`wsgen -verbose -cp ./target/classes -s ./src/main/java -d ./target/classes md.leonis.soap.HelloWorldWS -wsdl -r ./src/main/resources/wsdl`

`java2ws.bat -client -server -wsdl -wrapperbean -o ws.wsdl -cp C:\projects\soap-examples\soap-apache-cxf-ejb-client\target\classes md.leonis.ws.server.UserServiceImpl`

#### Плагин cxf-java2ws-plugin 

Для Maven доступен плагин cxf-java2ws-plugin, позволяющий проделывать почти всё то же самое, что и в консоли.

```xml
            <plugin>
                <groupId>org.apache.cxf</groupId>
                <artifactId>cxf-java2ws-plugin</artifactId>
                <version>${cxf.version}</version>
                <dependencies>
                    <dependency>
                        <groupId>org.apache.cxf</groupId>
                        <artifactId>cxf-rt-frontend-jaxws</artifactId>
                        <version>${cxf.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.apache.cxf</groupId>
                        <artifactId>cxf-rt-frontend-simple</artifactId>
                        <version>${cxf.version}</version>
                    </dependency>
                </dependencies>

                <configuration>
                    <className>md.leonis.ws.server.UserService</className>
                    <serviceName>UserServiceImpl</serviceName>
                    <genServer>true</genServer>
                    <genClient>true</genClient>
                    <genWsdl>true</genWsdl>
                    <verbose>true</verbose>
                    <!--<attachWsdl>true</attachWsdl>-->
                    <outputFile>C:\projects\soap-examples\soap-apache-cxf-ejb-client\src\main\resources\wsdl\ws.wsdl</outputFile>
                </configuration>

                <executions>
                    <execution>
                        <id>process-classes</id>
                        <phase>process-classes</phase>
                        <goals>
                            <goal>java2ws</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

*Впрочем, стоит ли захламлять `pom.xml` для разовой операции?*

### Генерация клиента

Утилиты wsimport, xjc, wsdl2java на основе WSDL/XSD файлов генерируют клиентский код.
Чего-то сильно нового по сравнению с предыдущими утилитами они не делают, многие ключи повторяются, остальные интутивно понятны. Примеры:

`wsimport -d ./target/classes -s ./src/main/java -verbose -p md.leonis.soap.client http://localhost:8080/hello/newEndpoint?wsdl`

`wsimport -d ./target/classes -s ./src/main/java -verbose -p md.leonis.soap.client -wsdlLocation ../src/resources/META-INF/service.wsdl`

`wsimport HelloWorldWSService.wsdl -b HelloWorldWSService_schema1.xsd`

`wsdl2java.bat -p md.leonis.soap -client -server -impl -b HelloWorldWSService_schema1.xsd -wsdlLocation classpath:wsdl/HelloWorldWSService.wsdl HelloWorldWSService.wsdl`

[<< назад](chapter-10.md) | [⌂ оглавление](../README.md)