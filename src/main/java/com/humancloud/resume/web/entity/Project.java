package com.humancloud.resume.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Embeddable
@Data
public class Project {

    @NotBlank
    private String projectName;
    @Column(length = 2000)
    private String description;
    @Column(length = 800)
    private String technologies;
    @Column(length = 2000)
    private List<String> responsibilities;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;


}
