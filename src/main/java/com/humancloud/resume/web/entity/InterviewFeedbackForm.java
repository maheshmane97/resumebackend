package com.humancloud.resume.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.humancloud.resume.web.entity.constants.InterviewResult;
import com.humancloud.resume.web.entity.constants.InterviewRound;
import com.humancloud.resume.web.entity.constants.InterviewType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewFeedbackForm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer  formId;
    private String  candidateId;
    @NotBlank(message = "Candidate Name should not be blank")
    private String candidateName;
    private String interviewerName;
    @NotNull(message = "Interview type should be The_Converge or Humancloud_Internal")
    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;
    @NotNull(message = "Interview Round should be L1/L2/L3")
    @Enumerated(EnumType.STRING)
    private InterviewRound interviewRound;
    private String experience;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "formId")
    private Set<TechnologyRating> technologyRating = new LinkedHashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "formId")
    private Set<SoftSkillsRating> softSkillRatings = new LinkedHashSet<>();
    private String goodAt;
    private String improvmentAreas;
    @NotNull(message = "Interview result should be SELECTED/REJECTED/HOLD")
    @Enumerated(EnumType.STRING)
    private InterviewResult result;
    private String comments;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private String createdDate;
    @JsonIgnore
    private String deleteStatus;
    @JsonIgnore
    private String modifiedBy;
    @JsonIgnore
    private String modifiedDate;
    @ElementCollection
    private List<byte[]> attachments;
    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
