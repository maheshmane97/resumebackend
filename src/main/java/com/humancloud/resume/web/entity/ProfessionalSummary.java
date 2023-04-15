package com.humancloud.resume.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Embeddable
@Data
public class ProfessionalSummary {

    @Size(min=100, max = 2000, message = "Summary Details required minimum 100 and maximum 2000 characters.")
    @Column(length = 2000)
    private List<String> summaryDetails;
}
