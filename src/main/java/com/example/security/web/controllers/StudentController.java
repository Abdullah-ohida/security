package com.example.security.web.controllers;

import com.example.security.data.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James bond"),
            new Student(2, "Maria james"),
            new Student(3, "Anna, smaith")
    );

    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        log.info("End point hit");
        return STUDENTS.stream().filter(student -> studentId.equals(student.getId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("Student with " + studentId + " does not exist"));
    }
}
