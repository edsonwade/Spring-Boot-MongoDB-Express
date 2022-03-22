package com.example.springbootmongodbexpress.service;

import com.example.springbootmongodbexpress.service.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    Student student = new Student();


    private final StudentRepository studentRepository;


    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }




}
