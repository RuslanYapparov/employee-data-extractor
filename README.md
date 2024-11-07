[По-русски](doc%2Freadme%2FREADME_rus.md)

# Employee-data-extractor

___

*This is a module of the project 'rzd-engineer-assistant', responsible for retrieving extended information about
enterprise employees, such as job tenure, education, and graduation year. It simulates a connection to the human
resources management database system (ASUTR).*

___

![asutr.jpg](doc%2Freadme%2Fasutr.jpg)
When creating protocols for JSC "Russian Railways" that investigate transport process violations or other incidents
caused by employees of divisions, it becomes necessary to specify information regarding the duration of employment in the
company, job tenure, and educational background. To obtain this data, engineers typically contact the HR department,
which in turn retrieves information from the human resources management system. The process of automated document
creation using the 'rzd-engineer-assistant' application is simplified by sending a request to this service and obtaining
the necessary data.

___

The application API consists of a single endpoint that accepts an array of integer personnel IDs
(also known as identifiers in the database). It returns a DTO with the necessary employee data. A Microsoft Excel
sheet with fictional data and an OpenAPI specification reflecting the endpoint and DTO model are provided in the
[doc](doc) folder.

___

Java version: 21;

The application runs on the Spring Boot framework version 3.3.3;

Build system: Apache Maven;

Database: PostgreSQL, with flyway for data migration;

Database interaction and entity mapping: spring-boot-starter-data-jpa, Hibernate;

Testing: JUnit, spring-boot-test, Mockito;

___

The service is divided into 3 functional modules and 1 auxiliary module:

client - contains the Spring component (@Service) client that allows sending HTTP requests to this service and
processing responses to obtain necessary data.

dtos - contains the DTO model of returned data and a utility class for creating a test object.

server - contains the logic for retrieving and processing employee data requests.

commonclasses - a module of the "RZD Engineer Assistant" project that contains common data (exceptions, DTOs, etc.)
for all modules.

___

Instructions for running the application locally:

1. The following software is required to run the application:

- Git (installation guide here https://learn.microsoft.com/ru-ru/devops/develop/git/install-and-set-up-git);
- JDK (Java SE 21, installation guide here https://blog.sf.education/ustanovka-jdk-poshagovaya-instrukciya-dlya-novichkov/);
- Apache Maven (installation guide for Windows here https://byanr.com/installation-guides/maven-windows-11/);

2. Open a terminal/command prompt/PowerShell and execute the following commands:

```
cd [target directory for project download]

git clone git@github.com:RuslanYapparov/employee-data-extractor.git

cd employee-data-extractor/

mvn install

cd server/

mvn spring-boot:run
```

3. Before running the application, ensure port 8072 is available, as it will be the default for incoming HTTP requests
according to the API (http://localhost:8072/). If necessary, you can change this setting in the
[application.properties](server%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) file.