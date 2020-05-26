package com.cloud.base.service.impl;

import com.cloud.base.dto.PasswordDTO;
import com.cloud.base.dto.request.LoginRequest;
import com.cloud.base.dto.response.JwtResponse;
import com.cloud.base.exception.TokenException;
import com.cloud.base.models.PasswordResetToken;
import com.cloud.base.models.User;
import com.cloud.base.repository.PasswordResetTokenRepository;
import com.cloud.base.repository.UserRepository;
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
import java.util.List;
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

    @Override
    public String resetPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent())
            throw new UsernameNotFoundException("");

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(userOptional.get(), token);

        return "Email Sent to "+email;
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
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

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(DateUtils.addDays(new Date(), 1));
        passwordResetTokenRepository.save(resetToken);
        //mail gonder
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
