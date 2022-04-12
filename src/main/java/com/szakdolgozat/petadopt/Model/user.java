package com.szakdolgozat.petadopt.Model;


import javax.persistence.*;

@Entity
@Table(name="users")
public class user {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String surName;
    private String firstName;

    private String email;
    private String password;
    private String phone;
    private boolean tokenExp;
}
