package com.humancloud.resume.web.service.impl;

import com.humancloud.resume.web.dto.UserDTO;
import com.humancloud.resume.web.entity.Role;
import com.humancloud.resume.web.entity.Users;
import com.humancloud.resume.web.exception.BaseException;
import com.humancloud.resume.web.repository.RoleRepository;
import com.humancloud.resume.web.repository.UserRegistrationRepository;
import com.humancloud.resume.web.service.UserService;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public BaseResponseDTO registerAccount(UserDTO userDTO) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        //validateAccount(userDTO);
        Users userRegistration = userRegistrationRepository.findByEmail(userDTO.getEmail());
        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        if (ObjectUtils.isEmpty(userDTO)) {
            //throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Data must not empty");
            responseDTO.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            responseDTO.setMessage("Data must not empty");
        } else if (!ObjectUtils.isEmpty(userRegistration)) {
            // throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Email had Exists");
            responseDTO.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            responseDTO.setMessage("Email had Exists");
        } else if (!roles.contains(userDTO.getRole())) {
            //throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Invalid Role");
            responseDTO.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            responseDTO.setMessage("Invalid Role");
        }
        else {
            Users userRegistration1 = saveUserData(userDTO);
            try {
                userRegistration1.setDeleteStatus("N");
                userRegistrationRepository.save(userRegistration1);
                responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
                responseDTO.setMessage("User account created successfully for " + userDTO.getEmail());
            } catch (Exception e) {
                responseDTO.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
                responseDTO.setMessage("SERVICE UNAVAILABLE");
            }
        }
        return responseDTO;
    }

    @Override
    public List<Users> findAllUsers() {
        return userRegistrationRepository.findAll();
    }


    @Override
    public BaseResponseDTO deleteByUserId(UUID userId, Principal principal) {
        Users user = userRegistrationRepository.findById(userId).get();
        BaseResponseDTO responseDTO=new BaseResponseDTO();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setDeleteStatus("Y");
        user.setModifiedBy(principal.getName());
        user.setModifiedDate(simpleDateFormat.format(Date.from(Instant.now())));
        userRegistrationRepository.save(user);
        responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
        responseDTO.setMessage("User Deleted Successfully..");
        return responseDTO;
    }

    private Users saveUserData(UserDTO userDTO) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Users users = new Users();
        users.setEmail(userDTO.getEmail());
        users.setFullName(userDTO.getFullName());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setCreatedDate(simpleDateFormat.format(Date.from(Instant.now())));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(userDTO.getRole()));
        users.setRoles(roles);

        return users;

    }

    private void validateAccount(UserDTO userDTO) {
        if (ObjectUtils.isEmpty(userDTO)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Data must not empty");
        }
        Users userRegistration = userRegistrationRepository.findByEmail(userDTO.getEmail());
        if (!ObjectUtils.isEmpty(userRegistration)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Email had Exists");
        }

        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        if (!roles.contains(userDTO.getRole())) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid Role");
        }
    }

    public String userName(String emailId) {
        Users user = userRegistrationRepository.findByEmail(emailId);
        return user.getFullName();
    }

}
