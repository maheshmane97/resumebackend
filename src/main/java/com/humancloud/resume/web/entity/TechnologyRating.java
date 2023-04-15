package com.humancloud.resume.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer techId;
    @NotBlank(message = "Technology Name should not be blank")
    private String techName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "techId")
    private Set<TechnicalSkillsRating> techSkills = new LinkedHashSet<>();
}
