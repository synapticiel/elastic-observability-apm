package com.bvader.estimator;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring Framework provides a CorsFilter. We declare the filter here 
 * to allow the HTTP OPTIONS request from the Elastic APM RUM agent.
 * 
 * Basically, this Spring Boot back-end application (http://localhost;8080)
 * will receive an OPTIONS request with the following headers:
 *
 *	Access-Control-Request-Headers: elastic-apm-traceparent
 *	Access-Control-Request-Method: [request-method]
 *	Origin: [request-origin]
 *
 *	And we should respond to it with these headers:
 *
 *	Access-Control-Allow-Headers: elastic-apm-traceparent
 *	Access-Control-Allow-Methods: [allowed-methods]
 *	Access-Control-Allow-Origin: [request-origin]
 *
 * See: https://www.elastic.co/guide/en/apm/agent/js-base/master/distributed-tracing-guide.html
 * 
 * and
 * 
 * See: https://spring.io/blog/2015/06/08/cors-support-in-spring-framework#filter-based-cors-support
 * 
 * Also: https://stackoverflow.com/questions/31724994/spring-data-rest-and-cors
 * 
 * @author Adam Quan
 */
@Configuration
public class MyCorsConfiguration {

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}