package com.fmi110.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
