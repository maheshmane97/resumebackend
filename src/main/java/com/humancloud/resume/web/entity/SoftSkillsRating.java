package com.humancloud.resume.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftSkillsRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer softSkillId;
    @NotBlank(message = "Soft skill Name should not be blank")
    private String skillName;
    @Min(value = 1, message = "Value must be greater than or equal to 0")
    @Max(value = 4, message = "Value must be less than or equal to 100")
    private Integer rating;
}
