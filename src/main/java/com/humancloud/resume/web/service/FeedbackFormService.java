package com.humancloud.resume.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humancloud.resume.web.entity.InterviewFeedbackForm;
import com.humancloud.resume.web.repository.FeedbackFormRepository;
import com.humancloud.resume.web.service.impl.UserServiceImpl;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackFormService {

    @Autowired
    private FeedbackFormRepository feedbackFormRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    public BaseResponseDTO submitForm(String form, Principal principal, List<MultipartFile> file) throws JsonProcessingException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        ObjectMapper objectMapper=new ObjectMapper();
        InterviewFeedbackForm interviewFeedbackForm = objectMapper.readValue(form, InterviewFeedbackForm.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        interviewFeedbackForm.setInterviewerName(userServiceImpl.userName(principal.getName()));
        interviewFeedbackForm.setCreatedBy(principal.getName());
        interviewFeedbackForm.setCreatedDate(simpleDateFormat.format(Date.from(Instant.now())));
        interviewFeedbackForm.setDeleteStatus("N");
        List<byte[]> bytes = new ArrayList<>();
        file.forEach(f-> {
            try {
                bytes.add(f.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        interviewFeedbackForm.setAttachments(bytes);
        feedbackFormRepo.save(interviewFeedbackForm);
        responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
        responseDTO.setMessage("Feedback Submitted Successfully..");
        return responseDTO;
    }

    public List<InterviewFeedbackForm> findAllFeedbackForms(){
        List<InterviewFeedbackForm> forms = feedbackFormRepo.findAll();
        return forms.stream().filter(e -> e.getDeleteStatus().equals("N")).collect(Collectors.toList());
    }

    public BaseResponseDTO updateFormsAccDeleteStatus(Integer formId, Principal principal){
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        InterviewFeedbackForm form = feedbackFormRepo.findById(formId).get();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setDeleteStatus("Y");
        form.setModifiedBy(principal.getName());
        form.setModifiedDate(simpleDateFormat.format(Date.from(Instant.now())));
        feedbackFormRepo.save(form);
        responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
        responseDTO.setMessage("Feedback Deleted Successfully..");
        return responseDTO;
    }
}
