package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // it is the starting point or root of project. It acts as configuration file/class in which only we can use @Bean annotation.
public class BlogApplication {

	@Bean // it will create modelMapper object, and the object address need to be injected in
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
//THIS IS THE BASE CLASS. ALL SPRING BOOT PROJECT EXECUTION STARTS FROM HERE.

// ModelMapper library or MapStruct -
// we have developed dto to entity and entity to dto method manually. what if entity/ dto has many fields. we have to write set get code many times.
// ModelMapper library do that automatically. use to map data of one object to another object.
// for an external libraries, spring ioc can't create object . @Autowired annotation and constructor based injection will not work
//once you have object of model mapper, it will not create bean automatically because it is not inbuilt libraries.
// it was an external library thats why dependency injection cannot happen directly.

// if you are using external library, before doing @Autowired annotation and constructor based injection, @Bean step is mandatory.
// without @Bean object creation will not happen.
// we have to define a method with bean annotation in config file. bean annotation generates the object and does dependency injection.

//spring validation is a very important library in spring framework because whenever you store data in
// database, it is very important that data is being checked before it stored.i.e email id , mobile are in correct form or not.
// then accordigly we can return back error message back to postman.