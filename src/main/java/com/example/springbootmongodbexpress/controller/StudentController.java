package com.example.springbootmongodbexpress.controller;

import com.example.springbootmongodbexpress.service.StudentService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootmongodbexpress.persistence.model.Student;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/students")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;

    private Student student;

    @GetMapping(value = "/")
    public ResponseEntity<List<Student>> fetchAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return (students != null) ? ResponseEntity.ok().body(students) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/student")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.AddNewStudent(student);
    }

}
