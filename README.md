# Пример SpringBoot + MongoDB + Camel

Проект из [https://github.com/vitinsinha/spring-boot-camel-mongo-embedded.git](https://github.com/vitinsinha/spring-boot-camel-mongo-embedded.git
)

В этом репозитории мои изменения.

__MongoDB__ — это документоориентированная система управления базами данных (СУБД) с открытым исходным кодом, относящаяся к классу NoSQL.

__Модель данных__. Данные хранятся в виде коллекций и документов, где документ представляет собой набор пар «ключ — значение». В отличие от реляционных баз данных, здесь нет жёсткой схемы таблиц, а данные могут иметь разную структуру.

Для горизонтального масштабирования данные разбиваются на части (шарды), которые размещаются на разных серверах.

Java 8:

````shell
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
````

### Запуск:

````shell
./run.sh
````

Или:

````shell
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
./gradlew bootRun
````

### Тестирование

Посмотреть все записи:

````shell
http :8080/camel/person
````
````json
[
    {
        "firstName": "firstName1",
        "id": "1",
        "lastName": "lastName1"
    }
]
````

Добавить запись:

````shell
http post :8080/camel/person < doc/person1.json
````

````shell
http post :8080/camel/person < doc/person2.json
````

Поиск по параметру:

````shell
 http :8080/camel/person?lastName=lastName2
````
````json
[
    {
        "firstName": "firstName2",
        "id": "2",
        "lastName": "lastName2"
    }
]
````

````shell
http :8080/camel/person?firstName=firstName1
````
````json
[
    {
        "firstName": "firstName1",
        "id": "1",
        "lastName": "lastName1"
    }
]
````

Вывести все записи:

````shell
http :8080/camel/person
````

````json
[
    {
        "firstName": "firstName1",
        "id": "1",
        "lastName": "lastName1"
    },
    {
        "firstName": "firstName2",
        "id": "2",
        "lastName": "lastName2"
    }
]
````

Удалить одну из записей:

````shell
http DELETE :8080/camel/person/1
````

# Camel

Пример из [RestRouteBuilder.java](src/main/java/ru/perm/v/springbootcameldemo/routes/RestRouteBuilder.java)

````java
        rest("/person").id("rest-person")
                // "/person" or "/person?firstName=First" or "/person?lastName=Last"
                .get("").id("rest-person-get").consumes("application/json").produces("application/json").to("direct:getAllPerson")
                // "/person/id"
                .get("/{id}").id("rest-person-get-id").consumes("application/json").produces("application/json").to("direct:getSinglePerson")
                .post("").id("rest-person-post").consumes("application/json").produces("application/json").type(Person.class).to("direct:postPerson");

        rest("/person").id("delete-person").delete("/{id}").to("bean:personProcessor?method=deletePerson");
````
# Оригинальный Readme:

# Spring Boot Camel Mongo Embedded

This is a demo application to show how to build a backend application using

- Spring Boot
- Apache Camel
    - Rest routes - GET, POST etc
    - Path param & Query Param handling
    - Direct & Seda routes
    - Exception Handling
    - Response status handling
    - Multicast & Aggregation
- Spring Data
- Mongo DB (Embedded)
- Camel Swagger

## Running the application

Run using the included gradle wrapper

```
./gradlew bootRun
```

And then go to ```http://localhost:8080/camel/api-doc``` to access the Swagger documentation.

## cURL Commands

You can try the following API's once the server is running.

###### GET /person
````shell 
curl http://localhost:8080/camel/person
````

###### POST /person

````shell  
curl -X POST \
      http://localhost:8080/camel/person \
      -H 'content-type: application/json' \
      -d '{
    	"firstName": "First",
    	"lastName": "Last",
    	"id": "1"
    }' 
````

###### GET /person?lastName=Last
````shell 
curl http://localhost:8080/camel/person?lastName=Last 
````

###### GET /person?firstName=First
````shell
 curl http://localhost:8080/camel/person?firstName=First 
 ````

###### GET /person/name?firstName=First&lastName=Last
````shell 
curl "http://localhost:8080/camel/person/name?firstName=First&lastName=Last" 
````