package com.cloud.base.service;

import com.cloud.base.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    String createUser(UserDTO userDTO);

    List<UserDTO> getAllUser(Pageable pageable);

    void delete(Long id);

    UserDTO update(UserDTO userDTO);




}
