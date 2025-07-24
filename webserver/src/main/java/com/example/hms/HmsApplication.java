package com.example.hms;


import com.example.hms.Model.Hospital;
import com.example.hms.repository.HospitalRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class HmsApplication {


	public static void main(String[] args){
		//SpringApplication.run(HmsApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(HmsApplication.class, args);
		HospitalRepository hospitalRepository = context.getBean(HospitalRepository.class);
	}

}