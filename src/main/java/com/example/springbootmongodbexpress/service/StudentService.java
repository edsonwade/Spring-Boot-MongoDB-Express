package com.example.springbootmongodbexpress.service;

import com.example.springbootmongodbexpress.service.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    Student student = new Student();


    @Autowired
    private StudentRepository studentRepository;


    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }


    public Student findStudentByEmail(String email) {
        Optional<Student> students = studentRepository.findStudentByEmail(email);
        if (students.isPresent()) {
            return student;
        }
        throw new StudentNotFoundException();
    }


}
