package com.humancloud.resume.web.entity;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EducationDetails {
    private String degree;
    private String university;
    private String passingYear;
}
