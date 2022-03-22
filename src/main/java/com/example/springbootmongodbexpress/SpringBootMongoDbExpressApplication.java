package com.example.springbootmongodbexpress;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;


import java.util.List;

@EnableMongoRepositories(basePackageClasses = StudentRepository.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootMongoDbExpressApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootMongoDbExpressApplication.class, args);
    }


}
