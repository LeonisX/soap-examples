# Simple examples of SOAP JAX-WS servers and clients for Maven and Java 8
# Простые примеры серверов и клиентов SOAP JAX-WS для Maven и Java 8

Серия примеров, рассчитанная на современного программиста, не знакомого с `SOAP`, `WSDL` и `JAX-WS`.
Рассчитана на быстрое знакомство с технологией, без глубокого погружения в теоретические дебри.

Упор делается на `Maven` и, где используется контейнер, то `WildFly` (`JBoss`).

Любой из модулей можно использовать как каркас для создания своего проекта.

Читать следует в таком порядке:

**Starter**

* [Основы JAX-WS и SOAP для современного программиста](doc/chapter-1.md)
* [SOAP HelloWorld для Java SE](doc/chapter-2.md) (soap-java-se-document)
* [SOAP HelloWorld для Java SE (RPC Style)](doc/chapter-3.md) (soap-java-se-rpc)
* [Теория: от XML до стилей сообщений SOAP](doc/chapter-3.5.md)
* [SOAP HelloWorld для Java EE](doc/chapter-4.md) (soap-java-ee)
* [Правила присвоения имён SOAP](doc/soap-naming-convention.md)

**Intermediary**

* [Динамическое подключение к серверу](doc/chapter-5.md)
* [SOAP HelloWorld для Java SE с BASIC Authentication](doc/chapter-6.md) (soap-java-se-password-auth)
* [SOAP HelloWorld для Java EE с BASIC Authentication](doc/chapter-7.md) (soap-java-ee-basic-auth, soap-java-ee-basic-auth-client)


* [Apache CXF](doc/chapter-7.5.md)
* [SOAP HelloWorld для Apache CXF (POJO)](doc/chapter-8.md) (soap-apache-cxf-pojo)
* [SOAP HelloWorld для Apache CXF (JAX-WS)](doc/chapter-9.md) (soap-apache-cxf-jax-ws)
* [Apache CXF client as EJB](doc/chapter-10.md) (soap-apache-cxf-ejb-client, soap-apache-cxf-ejb-server)
* [Утилиты wsgen, wsimport, xcj, java2ws, wsdl2java](doc/utils.md)

На этом всё, физически больше нет времени писать, жду любые замечания и вопрос на почту.

Написано специально для http://javatut.tv-games.ru.