package com.rama.springboot.HelloWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author RamaRao
 *
 */

@RestController
@SpringBootApplication
public class SpringBootHelloWorld 
{
    @RequestMapping("/hello")
	public String sayHello() {
		
		return "Hello from spring boot";
	}
		
	
	public static void main( String[] args )
    {
        SpringApplication.run(SpringBootHelloWorld.class, args);
    }
}
