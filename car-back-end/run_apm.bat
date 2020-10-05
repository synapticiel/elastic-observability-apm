java -javaagent:../lib/elastic-apm-agent-1.18.0.jar 
-Delastic.apm.service_name=car-back-end 
-Delastic.apm.application_packages=com.packt.cardatabase
-Delastic.apm.server_urls=http://apm.synapticiel.co
-Delastic.apm.environment=QA
-Delastic.apm.enable_log_correlation=true 
-jar target/car-back-end-0.1.0.jar