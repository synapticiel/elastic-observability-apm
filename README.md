<h1 align='center'><img src='observability-logo.svg' alt='Elastic Observability' width='32' valign='middle' /> Elastic Observability Meetup</h1>

<p align='center'>This repository includes demo apps for Elastic Observability.</p>

## Elastic Morocco Users Groups

Join us for more comming events on
- [Morocco Chapter](https://community.elastic.co/morocco)

- [Meetup Group](https://www.meetup.com/Elastic-Morocco-User-Group)

- [LinkedIn](https://www.linkedin.com/groups/12289643/)

## How to use this repository

### Requirements
You need first to install following components :
- Elasticsearch (must be running on `localhost:9200`)
- Kibana (must be running on `localhost:5601`)
- APM Server (must be running on `localhost:8200`)
- Filebeat (use the setup `filebeat.yml`)
### Clone the repository

```
git clone https://github.com/synapticiel/elastic-observability-apm.git
```
### Build Spring boot micro services

The demo use 2 microservices:

- `car-back-end` : sampple CRUD app that use H2 database
- `car-value-estimator`: sample service that estimate car value based on brand, model and year, this service is called by `car-back-end` when adding or updating a car object

Boths services use the [ECS-based logging for Java applications
](https://github.com/elastic/ecs-logging-java)

Build the 2 spring boot projects using maven and move jar into `./target` of each project

Download the [Elastic Java APM Agent 1.18.1](https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.18.1/elastic-apm-agent-1.18.1.jar) into `./lib`

Update and use `run_app.bat` on each project to run the each project using APM agent

```
setlocal

set JAVA_HOME="c:\Program Files\Java\jdk1.8.0_251"

set APP_ARGS=-javaagent:../lib/elastic-apm-agent-1.18.1.jar
set APP_ARGS=%APP_ARGS% -Delastic.apm.service_name=car-backend-meetup
set APP_ARGS=%APP_ARGS% -Delastic.apm.application_packages=*
set APP_ARGS=%APP_ARGS% -Delastic.apm.server_urls=http://localhost:8200
set APP_ARGS=%APP_ARGS% -Delastic.apm.environment=Production
set APP_ARGS=%APP_ARGS% -Delastic.apm.enable_log_correlation=true

%JAVA_HOME%\bin\java %APP_ARGS% -jar target\car-back-end-1.1.jar
```

```
setlocal

set JAVA_HOME="c:\Program Files\Java\jdk1.8.0_251"

set APP_ARGS=-javaagent:../lib/elastic-apm-agent-1.18.1.jar
set APP_ARGS=%APP_ARGS% -Delastic.apm.service_name=car-estimator-meetup
set APP_ARGS=%APP_ARGS% -Delastic.apm.application_packages=*
set APP_ARGS=%APP_ARGS% -Delastic.apm.server_urls=http://localhost:8200
set APP_ARGS=%APP_ARGS% -Delastic.apm.environment=Production
set APP_ARGS=%APP_ARGS% -Delastic.apm.enable_log_correlation=true

%JAVA_HOME%\bin\java %APP_ARGS% -jar target\car-estimator-1.1.jar
```

Make sur both micro services are working
- `curl -X GET "http://localhsot:8081/api/cars"`
- `curl -X GET "http://localhsot:8082/api/estimateValue?brand=Scoda&model=Octavia&year=2016"`


### Build Front End
Front end is build using ReactJS.

```
cd car-front-end
yarn install
yarn run
```
