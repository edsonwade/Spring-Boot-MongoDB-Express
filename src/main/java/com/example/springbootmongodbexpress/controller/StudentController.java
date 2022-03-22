package com.example.springbootmongodbexpress.controller;

import com.example.springbootmongodbexpress.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springbootmongodbexpress.persistence.model.Student;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return (students != null) ? ResponseEntity.ok().body(students) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
