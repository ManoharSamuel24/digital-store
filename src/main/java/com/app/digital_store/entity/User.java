package com.app.digital_store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;
    private Integer downloads = 0;
    private String bankName;
    private String pin;
    private String accountNumber;
    private String phoneNumber;
}
