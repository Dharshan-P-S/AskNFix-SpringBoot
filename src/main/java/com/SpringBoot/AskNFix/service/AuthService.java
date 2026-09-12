package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.dto.LoginRequest;
import com.SpringBoot.AskNFix.dto.LoginResponse;
import com.SpringBoot.AskNFix.dto.StudentRegisterRequest;
import com.SpringBoot.AskNFix.entity.Staff;
import com.SpringBoot.AskNFix.entity.Student;
import com.SpringBoot.AskNFix.repository.StaffRepository;
import com.SpringBoot.AskNFix.repository.StudentRepository;
import com.SpringBoot.AskNFix.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            StudentRepository studentRepository,
            StaffRepository staffRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse studentLogin(LoginRequest request) {

        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                student.getPasswordHash())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                student.getEmail(),
                "STUDENT"
        );

        return new LoginResponse(
                token,
                "STUDENT",
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getResidingStatus()
        );
    }

    public LoginResponse staffLogin(LoginRequest request) {

        Staff staff = staffRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                staff.getPasswordHash())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                staff.getEmail(),
                staff.getRole()
        );

        return new LoginResponse(
                token,
                staff.getRole(),
                staff.getStaffId(),
                staff.getName(),
                staff.getEmail(),
                null
        );
    }

    public String studentRegister(StudentRegisterRequest request) {

        if (request.getRollNumber() == null ||
                !request.getRollNumber().matches("^202310\\d{4}$")) {

            throw new RuntimeException(
                    "Roll number must be exactly 10 digits and start with 202310"
            );
        }

        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new RuntimeException("Roll number already registered");
        }

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Student student = Student.builder()
                .rollNumber(request.getRollNumber())
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .residingStatus(request.getResidingStatus())
                .build();

        studentRepository.save(student);

        return "Student registered successfully";
    }
}