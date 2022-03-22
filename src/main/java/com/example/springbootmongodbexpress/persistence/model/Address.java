package com.example.springbootmongodbexpress.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Address implements Serializable {
    private String country;
    private String city;
    private String postCode;
}
