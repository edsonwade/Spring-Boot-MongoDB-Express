package com.example.springbootmongodbexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public List<Student> findAllStudents() {

        return studentRepository.findAll();
    }
}
