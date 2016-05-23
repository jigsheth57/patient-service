package io.pivotal.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("io.pivotal.demo")
@Controller
public class PatientDataServiceApplication {

	final static String queueName = "patient-change-event";
	private static final Log log = LogFactory.getLog(PatientDataServiceApplication.class);
	
	@Value("${spring.rabbitmq.host}")
	String amqp_host;
	@Value("${spring.rabbitmq.port}")
	String amqp_port;
	@Value("${spring.rabbitmq.username}")
	String amqp_username;
	@Value("${spring.rabbitmq.password}")
	String amqp_password;
	
	public static void main(String[] args) {
		SpringApplication.run(PatientDataServiceApplication.class, args);
	}
		
	@Bean
	public Queue contactChangeEventQueue() {
		log.debug("amqp host: "+amqp_host);
		log.debug("amqp port: "+amqp_port);
		log.debug("amqp username: "+amqp_username);
		log.debug("amqp password: "+amqp_password);
		return new Queue(queueName);
	}

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
        		.select()                                  
                .apis(RequestHandlerSelectors.any())              
                .paths(PathSelectors.any())                          
                .build();  
    }
     
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Event-Driven Patient Data Service sample")
                .description("Event-Driven Patient Data Service demo using Spring JPA, REST, AMQP with Swagger")
                .termsOfServiceUrl("http://pivotal.io/")
                .contact(new Contact("Jignesh Sheth",null,null))
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .version("2.0")
                .build();
    }
    
	@RequestMapping("/")
	public String home() {
		return "redirect:/swagger-ui.html";
	}

}
