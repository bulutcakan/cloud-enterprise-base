package com.cloud.base.service.impl;

import com.cloud.base.dto.RoleDTO;
import com.cloud.base.dto.UserDTO;
import com.cloud.base.exception.RoleException;
import com.cloud.base.exception.code.ErrorCode;
import com.cloud.base.models.Role;
import com.cloud.base.models.RoleEnum;
import com.cloud.base.models.User;
import com.cloud.base.repository.RoleRepository;
import com.cloud.base.repository.UserRepository;
import com.cloud.base.service.SecurityUserService;
import com.cloud.base.service.UserService;
import com.cloud.base.util.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public String createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        Set<RoleDTO> roleDTOS = userDTO.getRole();
        user.setActive(false);
        Set<Role> roles = new HashSet<>();

        if (CollectionUtils.isEmpty(roleDTOS)) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RoleException(ErrorCode.ROLE.ROLE_NOT_FOUND, "Role not found"));
            roles.add(userRole);
        } else {
            roleDTOS.forEach(roleDTO -> {
                switch (roleDTO.getRoleName()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleException(ErrorCode.ROLE.ROLE_NOT_FOUND, "Role not found"));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(RoleEnum.ROLE_INSTRUCTOR)
                                .orElseThrow(() -> new RoleException(ErrorCode.ROLE.ROLE_NOT_FOUND, "Role not found"));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RoleException(ErrorCode.ROLE.ROLE_NOT_FOUND, "Role not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        securityUserService.sendActivationCode(user.getEmail());
        return "User registered successfully!";
    }

}
