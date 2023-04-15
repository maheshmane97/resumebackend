package com.humancloud.resume.web.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class ResumeMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="resume_id",columnDefinition = "UUID")
    private UUID id;
    private PersonalDetails personalDetails = new PersonalDetails();

    private EducationDetails educationDetails = new EducationDetails();

    private ProfessionalSummary professionalSummary = new ProfessionalSummary();

    private SkillSet skillSet = new SkillSet();
    @OneToMany(cascade = CascadeType.ALL)
    private List<WorkExperience> workExperience = new ArrayList<WorkExperience>();

    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String deleteStatus;

}
