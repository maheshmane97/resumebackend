package com.humancloud.resume.web.controller;

import com.humancloud.resume.web.dto.UserDTO;
import com.humancloud.resume.web.entity.Users;
import com.humancloud.resume.web.service.EmailSendService;
import com.humancloud.resume.web.service.UserService;
import com.humancloud.resume.web.service.impl.UserServiceImpl;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/thor/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSendService emailSendService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> registerUser(@RequestBody UserDTO userDTO) throws MessagingException {
        BaseResponseDTO responseDTO = userService.registerAccount(userDTO);
        System.out.println("User Service Status: "+responseDTO.getCode());
        if(responseDTO.getCode().equals("200"))
        {
//            triggerMail(userDTO.getEmail());
            return ResponseEntity.ok(responseDTO);
        }
        else if (responseDTO.getCode().equals("400"))
        {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        else if (responseDTO.getCode().equals("503"))
        {
            return ResponseEntity.internalServerError().body(responseDTO);
        }
        else {
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }

    @GetMapping("/getallusers")
    public List<Users> retriveAllUser()
    {
        List<Users> user = userService.findAllUsers();
        return user.stream().filter(e->e.getDeleteStatus().equals("N")).collect(Collectors.toList());
    }



    public void triggerMail(String email) throws MessagingException {
        String mailBody = "Dear User," +
                "<br>" +
                "<br>" +
                "Thank you for registering with <B>ResumeMaker</B>. We're thrilled to have you as a member of our community!"
                +"<br>" +
                "<br>" +
                "To get started, simply log in to your account using the information you provided during the registration process. You can access your account at https://app.humancloud.co.in/resumemakerui" +
                "<br>" +
                "<br>" +
                "Best regards," +
                "<br>" +
                "Team ResumeMaker" +
                "<br>" +
                " <img src=\"https://i.imgur.com/Qd09mpM.png\" alt=\"\">";
        emailSendService.sendEmail(email,"ResumeMaker - User Registration Successful",mailBody);
    }

    @PutMapping("/deletebyid/{userId}")
    public ResponseEntity<BaseResponseDTO> updateUserAccStatus(@PathVariable("userId") UUID userId, Principal principal){
        BaseResponseDTO responseDTO = userService.deleteByUserId(userId, principal);
        return ResponseEntity.ok(responseDTO);
    }
}
