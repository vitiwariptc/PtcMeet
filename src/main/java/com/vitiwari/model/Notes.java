package com.vitiwari.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "notes1")
@AllArgsConstructor
@NoArgsConstructor
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notesId;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Notes(int notesId, String title, String description) {
        this.notesId = notesId;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notesId=" + notesId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

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
