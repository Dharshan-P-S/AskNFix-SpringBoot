package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Student;
import com.SpringBoot.AskNFix.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {

        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student getStudentByRollNumber(String rollNumber) {

        return studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long studentId, Student updatedStudent) {

        Student student = getStudentById(studentId);

        student.setRollNumber(updatedStudent.getRollNumber());
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setResidingStatus(updatedStudent.getResidingStatus());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {

        Student student = getStudentById(studentId);

        studentRepository.delete(student);
    }
}