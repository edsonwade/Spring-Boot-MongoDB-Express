package com.example.springbootmongodbexpress.persistence.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Document
@Data
@NoArgsConstructor
public class Student implements Serializable {
    private static final long serialVersion = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private Gender gender;
    private Address address;
    private List<String> subjects;
    private BigDecimal totalSpentInBooks;
    private LocalDateTime created_at;

    public Student(String firstName, String lastName, String email, Gender gender, Address address, List<String> subjects, BigDecimal totalSpentInBooks, LocalDateTime created_at) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.subjects = subjects;
        this.totalSpentInBooks = totalSpentInBooks;
        this.created_at = created_at;
    }
}
