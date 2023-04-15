package com.humancloud.resume.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Technology {
    @Id
    private Integer techId;
    private String techName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "techId")
    private List<TechnicalSkills> TechSkills = new ArrayList<>();

    public Technology(Integer techId, String techName) {
        this.techId = techId;
        this.techName = techName;
    }
}
