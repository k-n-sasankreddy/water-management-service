package com.knsr.wmgmt.service;

import com.knsr.wmgmt.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
}
