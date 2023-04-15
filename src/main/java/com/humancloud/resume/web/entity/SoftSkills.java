package com.humancloud.resume.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftSkills {

    @Id
    private Integer softSkillId;
    private String skillName;
}
