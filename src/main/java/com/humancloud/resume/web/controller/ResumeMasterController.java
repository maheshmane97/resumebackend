package com.humancloud.resume.web.controller;

import com.humancloud.resume.web.entity.ResumeMasterEntity;
import com.humancloud.resume.web.helper.PDFGeneratorHelper;
import com.humancloud.resume.web.repository.ResumeRepository;
import com.humancloud.resume.web.service.ResumeService;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/thor/resume")
public class ResumeMasterController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/saveresume")
    public ResponseEntity<BaseResponseDTO> saveResumeData (@Valid @RequestBody ResumeMasterEntity resumeMasterEntity, Principal principal)
    {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        System.out.println("Professional Summary: "+resumeMasterEntity.getProfessionalSummary().getSummaryDetails());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resumeMasterEntity.setDeleteStatus("N");
        resumeMasterEntity.setCreatedBy(principal.getName());
        resumeMasterEntity.setCreatedDate(simpleDateFormat.format(Date.from(Instant.now())));
        resumeMasterEntity.setWorkExperience(resumeMasterEntity.getWorkExperience().stream().map(rm->{
                    rm.setCreatedBy(principal.getName());
                    rm.setCreatedDate(simpleDateFormat.format(Date.from(Instant.now())));
                    rm.getProjects().stream().map(p -> {
                        p.setCreatedBy(principal.getName());
                        p.setCreatedDate(simpleDateFormat.format(Date.from(Instant.now())));
                        return p;
                    }).collect(Collectors.toList());
                    return rm;
                }).collect(Collectors.toList())
        );
        try
        {
            resumeRepository.save(resumeMasterEntity);
            responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
            responseDTO.setMessage("Resume created successfully for " + resumeMasterEntity.getPersonalDetails().getEmpName());
            return ResponseEntity.ok(responseDTO);
        }
        catch(Exception e)
        {
            responseDTO.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            responseDTO.setMessage("Something went wrong..");
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/deleteresume/{id}")
    public ResponseEntity<BaseResponseDTO> deleteResumeData(@PathVariable("id") UUID uuid, Principal principal)
    {
        BaseResponseDTO responseDTO = resumeService.updateResumeAccStatus(uuid,principal);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/downloadresume/{id}")
    public void generatePDFFromDB(@PathVariable("id") String uuid, HttpServletResponse response) throws DocumentException, IOException
    {
        response.setContentType("application/pdf");
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=Resume_"+uuid+".pdf";
        response.setHeader(headerkey, headerValue);
        Optional<ResumeMasterEntity> resumeMasterEntity = resumeService.getResumeData(uuid);
        PDFGeneratorHelper generator = new PDFGeneratorHelper();
        generator.GeneratePDF(resumeMasterEntity,response);

    }
    @GetMapping("/getallresume")
    public List<ResumeMasterEntity> retrieveAllData()
    {
        List<ResumeMasterEntity> resumes = resumeRepository.findAll();
        return resumes.stream().filter(r->r.getDeleteStatus().equals("N")).collect(Collectors.toList());
    }

    @GetMapping("/getresume/{id}")
    public Optional<ResumeMasterEntity> retrieveSingleData(@PathVariable("id") String uuid)
    {
        return resumeRepository.findById(UUID.fromString(uuid));
    }
    @GetMapping("/getresumebyuser")
    public List<ResumeMasterEntity> getResumeByEmail(Principal principal)
    {
        List<ResumeMasterEntity> resumeMasterEntityList = resumeRepository.findAll();
        List<ResumeMasterEntity> collect = resumeMasterEntityList.stream().filter(a -> a.getCreatedBy().equals(principal.getName())).collect(Collectors.toList());
        return collect;
    }
    @PutMapping("/updateresume/{id}")
    public ResumeMasterEntity updateResume(@RequestBody ResumeMasterEntity resumeMasterEntity, @PathVariable("id") String uuid, Principal principal)
    {
        return resumeService.updateResumeData(resumeMasterEntity,uuid, principal);
    }
}
