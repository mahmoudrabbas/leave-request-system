package com.empSystem.services;

import com.empSystem.abstracts.AuthService;
import com.empSystem.abstracts.EmployeeService;
import com.empSystem.dtos.AuthResponse;
import com.empSystem.dtos.LoginRequest;
import com.empSystem.dtos.ResetPassword;
import com.empSystem.dtos.SignupRequest;
import com.empSystem.entities.PasswordRestToken;
import com.empSystem.entities.UserAccount;
import com.empSystem.enums.Role;
import com.empSystem.exceptions.AlreadyExistsException;
import com.empSystem.exceptions.BadRequestException;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.jwt.JwtUtils;
import com.empSystem.repository.PasswordRestTokenRepo;
import com.empSystem.repository.UserAccountRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private PasswordRestTokenRepo passwordRestTokenRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;


    @Override
    @Transactional
    public AuthResponse register(SignupRequest request) {
        Optional<UserAccount> usr = userAccountRepo.findByUsername(request.username());
        if (usr.isPresent()) {
            throw new AlreadyExistsException("Username Is Already exists");
        }


        UserAccount userAccount = new UserAccount();


        userAccount.setUsername(request.username());
        userAccount.setPassword(passwordEncoder.encode(request.password()));
        userAccount.setRole(Role.USER);
        userAccount.setEmail(request.email());
        userAccountRepo.save(userAccount);

        emailService.sendEmail(userAccount.getEmail(), request.username());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(userAccount.getId());
        authResponse.setEmployee(userAccount.getEmployee());
        authResponse.setUsername(userAccount.getUsername());

        return authResponse;
    }

    @Override
    public String login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        var authenticated = authenticationManager.authenticate(authentication);

        return jwtUtils.generateToken((UserDetails) authenticated.getPrincipal());
    }

    @Override
    @Transactional
    public void initiatePasswordResetToken(String email) {
//        UserAccount existedUser = userAccountRepo.findByEmail(email).orElseThrow(() -> new BadRequestException("Invalid token"));
//        Optional<PasswordRestToken> existedToken = passwordRestTokenRepo.findByUserAccount(existedUser);
//        existedToken.ifPresent(passwordRestToken -> passwordRestTokenRepo.delete(passwordRestToken));

        try {
            UserAccount userAccount = userAccountRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Invalid Token"));
            PasswordRestToken passwordRestToken = new PasswordRestToken();

            String token = UUID.randomUUID().toString();
            passwordRestToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
            passwordRestToken.setToken(token);
            passwordRestToken.setUserAccount(userAccount);


            passwordRestTokenRepo.save(passwordRestToken);

            emailService.sendEmailToResetPassword(email, token);
        } catch (Exception e) {
            throw new BadRequestException("Sending reset password link failed! :(");
        }

    }

    @Override
    @Transactional
    public void resetPassword(ResetPassword resetPassword) {
        PasswordRestToken passwordRestToken = passwordRestTokenRepo.findByToken(resetPassword.token())
                .orElseThrow(() -> new NotFoundException("Invalid Token"));

        if (passwordRestToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            passwordRestTokenRepo.delete(passwordRestToken);
            throw new BadRequestException("Expired Token");
        }

        passwordRestToken.getUserAccount().setPassword(passwordEncoder.encode(resetPassword.newPassword()));


        userAccountRepo.save(passwordRestToken.getUserAccount());

        passwordRestTokenRepo.delete(passwordRestToken);

    }


}
