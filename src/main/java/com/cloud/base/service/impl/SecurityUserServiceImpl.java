package com.cloud.base.service.impl;

import com.cloud.base.constans.MailType;
import com.cloud.base.dto.Mail;
import com.cloud.base.dto.PasswordDTO;
import com.cloud.base.dto.UserDTO;
import com.cloud.base.dto.JwtResponseDTO;
import com.cloud.base.exception.TokenException;
import com.cloud.base.models.PasswordResetToken;
import com.cloud.base.models.User;
import com.cloud.base.repository.PasswordResetTokenRepository;
import com.cloud.base.repository.UserRepository;
import com.cloud.base.service.MailService;
import com.cloud.base.service.SecurityUserService;
import com.cloud.base.util.DateUtils;
import com.cloud.base.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MailService mailService;

    @Override
    public String resetPassword(String email) {
        String token = getGeneratedToken(email);
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new TokenException(""));
        User user = resetToken.getUser();
        Mail mail = new Mail();
        mail.setMailTo(user.getEmail());
        mail.setSubject("Reset Password");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", user.getUsername());
        model.put("token", token);
        mail.setProps(model);
        mail.setMailType(MailType.RESET_PASSWORD);
        mailService.sendEmail(mail);
        //MAIL GONDER
        return "Reset Email Sent to " + email;
    }

    private String getGeneratedToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent())
            throw new UsernameNotFoundException("");

        String token = UUID.randomUUID().toString();
        createTokenForUser(userOptional.get(), token);
        return token;
    }

    @Override
    public String sendActivationCode(String email) {
        String token = getGeneratedToken(email);
        //MAIL GONDER
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new TokenException(""));
        User user = resetToken.getUser();
        Mail mail = new Mail();
        mail.setMailTo(user.getEmail());
        mail.setSubject("Register Account");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", user.getUsername());
        model.put("token", token);
        mail.setProps(model);
        mail.setMailType(MailType.ACTIVATE);
        mailService.sendEmail(mail);
        return "Active Email Sent to " + email;
    }

    @Override
    public JwtResponseDTO authenticateUser(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponseDTO(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public String changePassword(PasswordDTO passwordDTO) {
        if (StringUtils.isEmpty(passwordDTO.getToken()) || !isValidPasswordResetToken(passwordDTO.getToken()))
            throw new TokenException("Invalid Token");

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.
                findByToken(passwordDTO.getToken()).get();
        User user = passwordResetToken.getUser();
        user.setPassword(encoder.encode(passwordDTO.getNewPassword()));
        passwordResetTokenRepository.delete(passwordResetToken);
        userRepository.save(user);
        return "Password changed";
    }

    @Override
    public String activateUser(String token) {
        if (isValidPasswordResetToken(token)) {
            PasswordResetToken passwordResetToken = passwordResetTokenRepository.
                    findByToken(token).orElseThrow(() -> new TokenException("Token invalid"));
            User user = passwordResetToken.getUser();
            user.setActive(true);
            passwordResetTokenRepository.delete(passwordResetToken);
            userRepository.save(user);
            return "User account activated";

        }
        return null;
    }

    public void createTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(DateUtils.addDays(new Date(), 1));
        passwordResetTokenRepository.save(resetToken);
    }

    public boolean isValidPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.
                findByToken(token).orElseThrow(() -> new TokenException("Token invalid"));
        String savedToken = passwordResetToken.getToken();
        if (isTokenExpired(passwordResetToken))
            throw new TokenException("Token has been expired ");
        return true;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
