setlocal

set JAVA_HOME="c:\Program Files\Java\jdk1.8.0_251"

set APP_ARGS=-javaagent:../lib/elastic-apm-agent-1.18.1.jar
set APP_ARGS=%APP_ARGS% -Delastic.apm.service_name=car-estimator-meetup
set APP_ARGS=%APP_ARGS% -Delastic.apm.application_packages=*
set APP_ARGS=%APP_ARGS% -Delastic.apm.server_urls=http://localhost:8200
set APP_ARGS=%APP_ARGS% -Delastic.apm.environment=Production
set APP_ARGS=%APP_ARGS% -Delastic.apm.enable_log_correlation=true

%JAVA_HOME%\bin\java %APP_ARGS% -jar target\car-estimator-1.1.jar
