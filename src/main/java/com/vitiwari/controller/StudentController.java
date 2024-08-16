package com.vitiwari.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Data
class Student{
    public int id;
    public String name;
    public int marks;

    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public Student(){ }
}


@RestController("")
public class StudentController {

    public List<Student> studentList= new ArrayList<>(List.of(
            new Student(1, "vishnu", 65),
            new Student(2, "aashish", 75)
            ));

    @GetMapping("/student")
    public List<Student> getStudentList() {
        return studentList;
    }

    @GetMapping("/csrfToken")
    public CsrfToken getCsrfToken(HttpServletRequest req) {
        return (CsrfToken) req.getAttribute("_csrf");
    }

    @PostMapping("/student")
    public String addStudent(@RequestBody Student student){
        studentList.add(student);
        return "Success";
    }
}

// one session can have multiple csrf token
// one user can have multiple session and one session can have multiple csrf tokens