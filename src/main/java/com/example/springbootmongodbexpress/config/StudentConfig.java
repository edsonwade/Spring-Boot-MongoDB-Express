package com.example.springbootmongodbexpress.config;


import com.example.springbootmongodbexpress.persistence.model.Address;
import com.example.springbootmongodbexpress.persistence.model.Gender;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner runner(StudentRepository repository) {

        return args -> {
            String email = "mariana@academy.pt";
            final   Student student1 = new Student(
                    "mariana",
                    "augusta",
                    email,
                    Gender.MALE,
                    new Address("Brazil", "São Paulo", "00-12345-0987"),
                    List.of("Social Science", "Telemarketing","Physiotherapy"),
                    BigDecimal.TEN,
                    LocalDateTime.now()


            );
            String email1 = "benedito@academy.ao";
           final Student student2 = new Student(
                    "Benedito",
                    "mauro",
                    email1,
                    Gender.MALE,
                    new Address("Angola", "Luanda", "000-0000"),
                    List.of("Software Developer", "Telemarketing","Social Networking"),
                    BigDecimal.TEN,
                    LocalDateTime.now()


            );

                repository.findStudentByEmail(email,email1).ifPresentOrElse(s -> {
                System.out.println(s + "Already Exists");
            }, () -> {
                System.out.println("Students created with success : " + student1 + student2);
                //repository.insert(student1);
                repository.saveAll(List.of(student1, student2));
            });


        };
    }
}
