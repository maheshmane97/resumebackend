package com.humancloud.resume.web.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class SkillSet {
    private String technologies;
    private String languages;
    private String tools;
    private String databaseUsed;
    private String operatingSystems;
    private String ideUsed;
    private String webServer;
}
