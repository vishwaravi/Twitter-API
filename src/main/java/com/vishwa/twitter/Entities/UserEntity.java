package com.vishwa.twitter.Entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name= "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "user_email")
    private String user_email;
    
    @Column(name = "user_dob")
    private LocalDate user_dob;

    @Column(name = "user_passwd")
    private String user_passwd;

    @Column(name = "time_stamp")
    private String time_stamp;
}
