package com.humancloud.resume.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humancloud.resume.web.entity.InterviewFeedbackForm;
import com.humancloud.resume.web.service.FeedbackFormService;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/thor")
public class FeedbackFormController {
    @Autowired
    private FeedbackFormService feedbackFormService;

    @PostMapping(value = "/form")
    public ResponseEntity<BaseResponseDTO> submitForm(@Valid @RequestParam("form") String form, @RequestParam("attachments") List<MultipartFile> file, Principal principal) throws JsonProcessingException {
        BaseResponseDTO responseDTO = feedbackFormService.submitForm(form, principal, file);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/allformsdata")
    public List<InterviewFeedbackForm> findAllFeedbackForms(){
        return feedbackFormService.findAllFeedbackForms();
    }

    @PutMapping("/deletebyid/{formId}")
    public ResponseEntity<BaseResponseDTO> updateFormsAccDeleteStatus(@PathVariable("formId") Integer formId, Principal principal){
        BaseResponseDTO responseDTO = feedbackFormService.updateFormsAccDeleteStatus(formId, principal);
        return ResponseEntity.ok(responseDTO);
    }
}
