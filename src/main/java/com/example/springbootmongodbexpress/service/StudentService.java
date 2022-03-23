package com.example.springbootmongodbexpress.service;

import com.example.springbootmongodbexpress.service.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {


    private final StudentRepository studentRepository;


    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }


    public void AddNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new StudentNotFoundException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(String studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) {
            throw new StudentNotFoundException("student with id " + studentId + " does not exists");

        }
        studentRepository.deleteById(studentId);

    }


    public void updateStudent(String studentId, String firstName, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException("student with id " + studentId + " does not exists"));
        if (firstName != null && firstName.length() > 0 && !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new StudentNotFoundException("email taken");
            }
            student.setEmail(email);
        }

    }


}
