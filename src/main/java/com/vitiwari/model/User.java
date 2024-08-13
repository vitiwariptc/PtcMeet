package com.vitiwari.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "user1")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;
    private String emailId;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Notes> notes;

    public User(int userId, String userName, String emailId, String password) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
    }


    //    @ManyToMany(mappedBy = "users")
//    private List<Notes> notes;
//    This is where the relationship is mapped to
}
