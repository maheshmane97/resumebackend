package com.humancloud.resume.web.service;


import com.humancloud.resume.web.dto.UserDTO;
import com.humancloud.resume.web.entity.Users;
import com.humancloud.resume.web.utils.BaseResponseDTO;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserService {
    BaseResponseDTO registerAccount(UserDTO userDTO);

    List<Users> findAllUsers();

    BaseResponseDTO deleteByUserId(UUID userId, Principal principal);


}
