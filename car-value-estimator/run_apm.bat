java -javaagent:../lib/elastic-apm-agent-1.18.0.jar
-Delastic.apm.server_urls=http://apm.synapticiel.co 
-Delastic.apm.environment=QA 
-Delastic.apm.service_name=car-value-estimator 
-Delastic.apm.enable_log_correlation=true 
-jar target/car-value-estimator-service-0.1.0.jar