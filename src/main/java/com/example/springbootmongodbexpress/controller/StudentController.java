package com.example.springbootmongodbexpress.controller;

import com.example.springbootmongodbexpress.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootmongodbexpress.persistence.model.Student;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private Student student;

    @GetMapping(value = "/")
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return (students != null) ? ResponseEntity.ok().body(students) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "/search/{email}")
    public ResponseEntity<Student> findStudentByEmail(@RequestParam(value = "email") @PathVariable String email) {
         Optional<Student> students = Optional.ofNullable(studentService.findStudentByEmail(email));
        return (students.isPresent()) ? ResponseEntity.ok().body(student) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
