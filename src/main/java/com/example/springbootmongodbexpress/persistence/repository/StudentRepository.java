package com.example.springbootmongodbexpress.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.springbootmongodbexpress.persistence.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
}
