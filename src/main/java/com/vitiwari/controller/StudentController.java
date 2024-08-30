package com.vitiwari.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Getter
class Student{
    private int id;
    private String name;
    private int marks;

    private Student(StudentBuilder1 stb) {
        this.id = stb.id;
        this.name = stb.name;
        this.marks = stb.marks;
    }

    private Student(StudentBuilder2 stb){
        this.id = stb.id;
        this.name = stb.name;
        this.marks = stb.marks;
    }

    public Student() { }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }

    static class StudentBuilder1{
        public int id;
        public String name;
        public int marks;
        public StudentBuilder1() {

        }

        public StudentBuilder1 setId(int id) {
            this.id = id;
            return this;
        }

        public StudentBuilder1 setName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder1 setMarks(int marks) {
            this.marks = marks;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
    static class StudentBuilder2{
        public int id;
        public String name;
        public int marks;
        public StudentBuilder2() { }

        public static StudentBuilder2 builder() {
            return new StudentBuilder2();
        }

        public StudentBuilder2 setId(int id) {
            this.id = id;
            return this;
        }

        public StudentBuilder2 setName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder2 setMarks(int marks) {
            this.marks = marks;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
// Builders are generally used with immuatable class

@RestController("")
@CrossOrigin
public class StudentController {

    public List<Student> studentList= new ArrayList<>(List.of(
                new Student.StudentBuilder1().setName("Vishnu").setId(1).setMarks(65).build(),
                Student.StudentBuilder2.builder().setName("Aashish").setId(2).setMarks(75).build()
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