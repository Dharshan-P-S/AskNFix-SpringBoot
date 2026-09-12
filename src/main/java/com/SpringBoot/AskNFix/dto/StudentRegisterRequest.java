package com.SpringBoot.AskNFix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterRequest {

    private String rollNumber;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String residingStatus;
    private String hostelName;
}