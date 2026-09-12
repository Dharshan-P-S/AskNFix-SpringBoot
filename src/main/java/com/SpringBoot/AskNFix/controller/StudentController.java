package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.Student;
import com.SpringBoot.AskNFix.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(
            @PathVariable Long studentId) {

        return studentService.getStudentById(studentId);
    }

    @GetMapping("/roll/{rollNumber}")
    public Student getStudentByRollNumber(
            @PathVariable String rollNumber) {

        return studentService.getStudentByRollNumber(rollNumber);
    }

    @PutMapping("/{studentId}")
    public Student updateStudent(
            @PathVariable Long studentId,
            @RequestBody Student student) {

        return studentService.updateStudent(studentId, student);
    }

    @DeleteMapping("/{studentId}")
    public String deleteStudent(
            @PathVariable Long studentId) {

        studentService.deleteStudent(studentId);

        return "Student deleted successfully";
    }
}