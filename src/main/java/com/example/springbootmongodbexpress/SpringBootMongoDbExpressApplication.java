package com.example.springbootmongodbexpress;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {

        return args -> {
            String email = "rui@academy.pt";
            Student student = new Student(
                    "rui",
                    "pinto",
                    email,
                    Gender.MALE,
                    new Address("Portugal", "Porto", "4000-001"),
                    List.of("Computer Science", "Software Developer", "Designer", "Q.A"),
                    BigDecimal.TEN,
                    LocalDateTime.now()


            );

            // UsingMongoTemplateAndQuery(repository,mongoTemplate,email,student);
            repository.findStudentByEmail(email).ifPresentOrElse(s -> {

                System.out.println(s + "Already Exists");
            }, () -> {
                System.out.println("Inserted students " + student);
                repository.insert(student);
            });


        };
    }

    private void UsingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
        // to check if the email exist. mongodb template to do query
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1) {
            throw new IllegalStateException("found many students with email" + email);
        }
        System.out.println((students.isEmpty()) ? "Inserting student " + students + "/n" + repository.insert(students) : "Already exists");
    }

}
