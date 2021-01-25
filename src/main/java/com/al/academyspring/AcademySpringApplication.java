package com.al.academyspring;

import com.al.academyspring.repository.StudentRepository;
import com.al.academyspring.service.StudentServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcademySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademySpringApplication.class, args);
	}

}
