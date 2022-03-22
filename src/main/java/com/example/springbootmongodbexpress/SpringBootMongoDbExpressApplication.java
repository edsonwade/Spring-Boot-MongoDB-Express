package com.example.springbootmongodbexpress;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.example.springbootmongodbexpress.persistence.model.Address;
import com.example.springbootmongodbexpress.persistence.model.Gender;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EnableMongoRepositories(basePackageClasses = StudentRepository.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootMongoDbExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongoDbExpressApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository) {

        return args -> {
            Student student = new Student(
                    "edson",
                    "wayne",
                    "edson@academy.pt",
                    Gender.MALE,
                    new Address("Portugal", "Lisbon", "3000-001"),
                    List.of("Computer Science", "Software Developer","Designer"),
                    BigDecimal.TEN,
                    LocalDateTime.now()


            );
            repository.insert(student);
        };
    }

}
