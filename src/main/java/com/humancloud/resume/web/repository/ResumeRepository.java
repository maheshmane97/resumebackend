package com.humancloud.resume.web.repository;

import com.humancloud.resume.web.entity.PersonalDetails;
import com.humancloud.resume.web.entity.ResumeMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<ResumeMasterEntity, UUID> {
        //List<ResumeMasterEntity> findAllCreatedBy(String createdBy);
}
