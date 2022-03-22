package com.example.springbootmongodbexpress.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.springbootmongodbexpress.persistence.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findStudentByEmail(String email);
    Optional<Student> findStudentByEmail(String email,String email1);
}
