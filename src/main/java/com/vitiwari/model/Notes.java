package com.vitiwari.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "notes1")
@AllArgsConstructor
@NoArgsConstructor
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notesId;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToMany
//    @JoinTable(
//            name = "User_notes",
//            joinColumns = @JoinColumn(name = "notes_id"), // owner of this relationship
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    @JsonIgnore
//    private List<User> users;
//    This is the owner of this relationship
}
