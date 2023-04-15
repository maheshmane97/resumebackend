package com.humancloud.resume.web.service;

import com.humancloud.resume.web.entity.ResumeMasterEntity;
import com.humancloud.resume.web.repository.ResumeRepository;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Optional<ResumeMasterEntity> getResumeData(String uuid) {
        return resumeRepository.findById(UUID.fromString(uuid));
    }

    @Transactional
    public ResumeMasterEntity updateResumeData(ResumeMasterEntity resumeMasterEntity, String uuid, Principal principal) {
        Optional<ResumeMasterEntity> masterEntity = resumeRepository.findById(UUID.fromString(uuid));

        if (masterEntity != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            masterEntity.get().setModifiedBy(principal.getName());
            masterEntity.get().setModifiedDate(simpleDateFormat.format(Date.from(Instant.now())));
            masterEntity.get().setEducationDetails(resumeMasterEntity.getEducationDetails());
            masterEntity.get().setPersonalDetails(resumeMasterEntity.getPersonalDetails());
            masterEntity.get().setProfessionalSummary(resumeMasterEntity.getProfessionalSummary());
            masterEntity.get().setSkillSet(resumeMasterEntity.getSkillSet());
            masterEntity.get().setWorkExperience(resumeMasterEntity.getWorkExperience().stream().map(rm -> {
                        rm.setCreatedBy(masterEntity.get().getCreatedBy());
                        rm.setCreatedDate(masterEntity.get().getCreatedDate());
                        rm.setModifiedBy(principal.getName());
                        rm.setModifiedDate(simpleDateFormat.format(java.sql.Date.from(Instant.now())));
                        rm.getProjects().stream().map(p -> {
                            p.setCreatedBy(masterEntity.get().getCreatedBy());
                            p.setCreatedDate(masterEntity.get().getCreatedDate());
                            p.setModifiedBy(principal.getName());
                            p.setModifiedDate(simpleDateFormat.format(java.sql.Date.from(Instant.now())));
                            return p;
                        }).collect(Collectors.toList());
                        return rm;
                    }).collect(Collectors.toList())
            );
        }
        resumeRepository.save(masterEntity.get());
        return masterEntity.get();
    }

    public BaseResponseDTO updateResumeAccStatus(UUID resumeId, Principal principal){
        ResumeMasterEntity resume = resumeRepository.findById(resumeId).get();
        resume.setDeleteStatus("Y");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resume.setModifiedBy(principal.getName());
        resume.setModifiedDate(simpleDateFormat.format(Date.from(Instant.now())));
        resumeRepository.save(resume);
        BaseResponseDTO responseDTO=new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
        responseDTO.setMessage("Resume Deleted Successfully..");
        return responseDTO;
    }

}
