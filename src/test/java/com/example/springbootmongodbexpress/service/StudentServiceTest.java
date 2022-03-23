package com.example.springbootmongodbexpress.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootmongodbexpress.persistence.model.Address;
import com.example.springbootmongodbexpress.persistence.model.Gender;
import com.example.springbootmongodbexpress.persistence.model.Student;
import com.example.springbootmongodbexpress.persistence.repository.StudentRepository;
import com.example.springbootmongodbexpress.service.exception.StudentNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void testFindAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        when(this.studentRepository.findAll()).thenReturn(studentList);
        List<Student> actualFindAllStudentsResult = this.studentService.findAllStudents();
        assertSame(studentList, actualFindAllStudentsResult);
        assertTrue(actualFindAllStudentsResult.isEmpty());
        verify(this.studentRepository).findAll();
    }

    @Test
    void testFindAllStudents2() {
        when(this.studentRepository.findAll()).thenThrow(new StudentNotFoundException("An error occurred"));
        assertThrows(StudentNotFoundException.class, () -> this.studentService.findAllStudents());
        verify(this.studentRepository).findAll();
    }

    @Test
    void testAddNewStudent() {
        Student student = new Student();
        student.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        student.setSubjects(new ArrayList<>());
        student.setTotalSpentInBooks(BigDecimal.valueOf(42L));

        Student student1 = new Student();
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult = Optional.of(student1);
        when(this.studentRepository.save((Student) any())).thenReturn(student);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(ofResult);

        Student student2 = new Student();
        student2.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student2.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student2.setEmail("jane.doe@example.org");
        student2.setFirstName("Jane");
        student2.setGender(Gender.MALE);
        student2.setId("42");
        student2.setLastName("Doe");
        student2.setSubjects(new ArrayList<>());
        student2.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        assertThrows(StudentNotFoundException.class, () -> this.studentService.AddNewStudent(student2));
        verify(this.studentRepository).findStudentByEmail((String) any());
    }

    @Test
    void testAddNewStudent2() {
        Student student = new Student();
        Address address = new Address("GB", "Oxford", "OX1 1PT");

        student.setAddress(address);
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        ArrayList<String> stringList = new ArrayList<>();
        student.setSubjects(stringList);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        student.setTotalSpentInBooks(valueOfResult);
        when(this.studentRepository.save((Student) any())).thenReturn(student);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(Optional.empty());

        Student student1 = new Student();
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        this.studentService.AddNewStudent(student1);
        verify(this.studentRepository).save((Student) any());
        verify(this.studentRepository).findStudentByEmail((String) any());
        assertEquals(address, student1.getAddress());
        assertEquals(valueOfResult, student1.getTotalSpentInBooks());
        assertEquals("Jane", student1.getFirstName());
        assertEquals("01:01", student1.getCreated_at().toLocalTime().toString());
        assertEquals(stringList, student1.getSubjects());
        assertEquals(Gender.MALE, student1.getGender());
        assertEquals("jane.doe@example.org", student1.getEmail());
        assertEquals("42", student1.getId());
        assertEquals("Doe", student1.getLastName());
    }

    @Test
    void testDeleteStudent() {
        doThrow(new StudentNotFoundException("An error occurred")).when(this.studentRepository).deleteById((String) any());
        when(this.studentRepository.existsById((String) any())).thenReturn(true);
        assertThrows(StudentNotFoundException.class, () -> this.studentService.deleteStudent("42"));
        verify(this.studentRepository).existsById((String) any());
        verify(this.studentRepository).deleteById((String) any());
    }

    @Test
    void testDeleteStudent2() {
        doNothing().when(this.studentRepository).deleteById((String) any());
        when(this.studentRepository.existsById((String) any())).thenReturn(false);
        assertThrows(StudentNotFoundException.class, () -> this.studentService.deleteStudent("42"));
        verify(this.studentRepository).existsById((String) any());
    }

    @Test
    void testUpdateStudent() {
        Student student = mock(Student.class);
        when(student.getEmail()).thenReturn("foo");
        when(student.getFirstName()).thenReturn("Jane");
        doNothing().when(student).setAddress((Address) any());
        doNothing().when(student).setCreated_at((LocalDateTime) any());
        doNothing().when(student).setEmail((String) any());
        doNothing().when(student).setFirstName((String) any());
        doNothing().when(student).setGender((Gender) any());
        doNothing().when(student).setId((String) any());
        doNothing().when(student).setLastName((String) any());
        doNothing().when(student).setSubjects((java.util.List<String>) any());
        doNothing().when(student).setTotalSpentInBooks((BigDecimal) any());
        student.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        student.setSubjects(new ArrayList<>());
        student.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult = Optional.of(student);

        Student student1 = new Student();
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult1 = Optional.of(student1);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(ofResult1);
        when(this.studentRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(StudentNotFoundException.class,
                () -> this.studentService.updateStudent("42", "Jane", "jane.doe@example.org"));
        verify(this.studentRepository).findStudentByEmail((String) any());
        verify(this.studentRepository).findById((String) any());
        verify(student).getEmail();
        verify(student).getFirstName();
        verify(student).setAddress((Address) any());
        verify(student).setCreated_at((LocalDateTime) any());
        verify(student).setEmail((String) any());
        verify(student).setFirstName((String) any());
        verify(student).setGender((Gender) any());
        verify(student).setId((String) any());
        verify(student).setLastName((String) any());
        verify(student).setSubjects((java.util.List<String>) any());
        verify(student).setTotalSpentInBooks((BigDecimal) any());
    }

    @Test
    void testUpdateStudent2() {
        Student student = mock(Student.class);
        when(student.getEmail()).thenReturn("jane.doe@example.org");
        when(student.getFirstName()).thenReturn("foo");
        doNothing().when(student).setAddress((Address) any());
        doNothing().when(student).setCreated_at((LocalDateTime) any());
        doNothing().when(student).setEmail((String) any());
        doNothing().when(student).setFirstName((String) any());
        doNothing().when(student).setGender((Gender) any());
        doNothing().when(student).setId((String) any());
        doNothing().when(student).setLastName((String) any());
        doNothing().when(student).setSubjects((java.util.List<String>) any());
        doNothing().when(student).setTotalSpentInBooks((BigDecimal) any());
        student.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        student.setSubjects(new ArrayList<>());
        student.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult = Optional.of(student);

        Student student1 = new Student();
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult1 = Optional.of(student1);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(ofResult1);
        when(this.studentRepository.findById((String) any())).thenReturn(ofResult);
        this.studentService.updateStudent("42", "Jane", "jane.doe@example.org");
        verify(this.studentRepository).findById((String) any());
        verify(student).getEmail();
        verify(student).getFirstName();
        verify(student).setAddress((Address) any());
        verify(student).setCreated_at((LocalDateTime) any());
        verify(student).setEmail((String) any());
        verify(student, atLeast(1)).setFirstName((String) any());
        verify(student).setGender((Gender) any());
        verify(student).setId((String) any());
        verify(student).setLastName((String) any());
        verify(student).setSubjects((java.util.List<String>) any());
        verify(student).setTotalSpentInBooks((BigDecimal) any());
    }

    @Test
    void testUpdateStudent3() {
        Student student = new Student();
        student.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        student.setSubjects(new ArrayList<>());
        student.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult = Optional.of(student);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(ofResult);
        when(this.studentRepository.findById((String) any())).thenReturn(Optional.empty());
        Student student1 = mock(Student.class);
        when(student1.getEmail()).thenReturn("jane.doe@example.org");
        when(student1.getFirstName()).thenReturn("Jane");
        doNothing().when(student1).setAddress((Address) any());
        doNothing().when(student1).setCreated_at((LocalDateTime) any());
        doNothing().when(student1).setEmail((String) any());
        doNothing().when(student1).setFirstName((String) any());
        doNothing().when(student1).setGender((Gender) any());
        doNothing().when(student1).setId((String) any());
        doNothing().when(student1).setLastName((String) any());
        doNothing().when(student1).setSubjects((java.util.List<String>) any());
        doNothing().when(student1).setTotalSpentInBooks((BigDecimal) any());
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        assertThrows(StudentNotFoundException.class,
                () -> this.studentService.updateStudent("42", "Jane", "jane.doe@example.org"));
        verify(this.studentRepository).findById((String) any());
        verify(student1).setAddress((Address) any());
        verify(student1).setCreated_at((LocalDateTime) any());
        verify(student1).setEmail((String) any());
        verify(student1).setFirstName((String) any());
        verify(student1).setGender((Gender) any());
        verify(student1).setId((String) any());
        verify(student1).setLastName((String) any());
        verify(student1).setSubjects((java.util.List<String>) any());
        verify(student1).setTotalSpentInBooks((BigDecimal) any());
    }

    @Test
    void testUpdateStudent4() {
        Student student = mock(Student.class);
        when(student.getEmail()).thenReturn("jane.doe@example.org");
        when(student.getFirstName()).thenReturn("Jane");
        doNothing().when(student).setAddress((Address) any());
        doNothing().when(student).setCreated_at((LocalDateTime) any());
        doNothing().when(student).setEmail((String) any());
        doNothing().when(student).setFirstName((String) any());
        doNothing().when(student).setGender((Gender) any());
        doNothing().when(student).setId((String) any());
        doNothing().when(student).setLastName((String) any());
        doNothing().when(student).setSubjects((java.util.List<String>) any());
        doNothing().when(student).setTotalSpentInBooks((BigDecimal) any());
        student.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        student.setSubjects(new ArrayList<>());
        student.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult = Optional.of(student);

        Student student1 = new Student();
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult1 = Optional.of(student1);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(ofResult1);
        when(this.studentRepository.findById((String) any())).thenReturn(ofResult);
        this.studentService.updateStudent("42", "", "jane.doe@example.org");
        verify(this.studentRepository).findById((String) any());
        verify(student).getEmail();
        verify(student).setAddress((Address) any());
        verify(student).setCreated_at((LocalDateTime) any());
        verify(student).setEmail((String) any());
        verify(student).setFirstName((String) any());
        verify(student).setGender((Gender) any());
        verify(student).setId((String) any());
        verify(student).setLastName((String) any());
        verify(student).setSubjects((java.util.List<String>) any());
        verify(student).setTotalSpentInBooks((BigDecimal) any());
    }

    @Test
    void testUpdateStudent5() {
        Student student = mock(Student.class);
        when(student.getEmail()).thenReturn("jane.doe@example.org");
        when(student.getFirstName()).thenReturn("Jane");
        doNothing().when(student).setAddress((Address) any());
        doNothing().when(student).setCreated_at((LocalDateTime) any());
        doNothing().when(student).setEmail((String) any());
        doNothing().when(student).setFirstName((String) any());
        doNothing().when(student).setGender((Gender) any());
        doNothing().when(student).setId((String) any());
        doNothing().when(student).setLastName((String) any());
        doNothing().when(student).setSubjects((java.util.List<String>) any());
        doNothing().when(student).setTotalSpentInBooks((BigDecimal) any());
        student.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student.setEmail("jane.doe@example.org");
        student.setFirstName("Jane");
        student.setGender(Gender.MALE);
        student.setId("42");
        student.setLastName("Doe");
        student.setSubjects(new ArrayList<>());
        student.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult = Optional.of(student);

        Student student1 = new Student();
        student1.setAddress(new Address("GB", "Oxford", "OX1 1PT"));
        student1.setCreated_at(LocalDateTime.of(1, 1, 1, 1, 1));
        student1.setEmail("jane.doe@example.org");
        student1.setFirstName("Jane");
        student1.setGender(Gender.MALE);
        student1.setId("42");
        student1.setLastName("Doe");
        student1.setSubjects(new ArrayList<>());
        student1.setTotalSpentInBooks(BigDecimal.valueOf(42L));
        Optional<Student> ofResult1 = Optional.of(student1);
        when(this.studentRepository.findStudentByEmail((String) any())).thenReturn(ofResult1);
        when(this.studentRepository.findById((String) any())).thenReturn(ofResult);
        this.studentService.updateStudent("42", "Jane", "");
        verify(this.studentRepository).findById((String) any());
        verify(student).getFirstName();
        verify(student).setAddress((Address) any());
        verify(student).setCreated_at((LocalDateTime) any());
        verify(student).setEmail((String) any());
        verify(student).setFirstName((String) any());
        verify(student).setGender((Gender) any());
        verify(student).setId((String) any());
        verify(student).setLastName((String) any());
        verify(student).setSubjects((java.util.List<String>) any());
        verify(student).setTotalSpentInBooks((BigDecimal) any());
    }
}

