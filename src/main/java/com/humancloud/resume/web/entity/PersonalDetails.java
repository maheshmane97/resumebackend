package com.humancloud.resume.web.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class PersonalDetails {

    @NotBlank
    private String empName;
    @Email
    private String email;
    @NotBlank
    private String designation;

    @NotBlank
    @Size(max = 10)
    private String mobileNo;
    @NotBlank
    private String address;
    @NotBlank
    private String gender;
    @NotBlank
    private String maritalStatus;
}
