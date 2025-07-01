package com.empSystem.services;

import com.empSystem.abstracts.AuthService;
import com.empSystem.abstracts.EmployeeService;
import com.empSystem.dtos.AuthResponse;
import com.empSystem.dtos.LoginRequest;
import com.empSystem.dtos.SignupRequest;
import com.empSystem.entities.Employee;
import com.empSystem.entities.UserAccount;
import com.empSystem.enums.Role;
import com.empSystem.exceptions.AlreadyExistsException;
import com.empSystem.jwt.JwtUtils;
import com.empSystem.repository.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public AuthResponse register(SignupRequest request) {
        Optional<UserAccount> usr = userAccountRepo.findByUsername(request.username());
        if (usr.isPresent()) {
            throw new AlreadyExistsException("Username Is Already exists");
        }


        UserAccount userAccount = new UserAccount();
        Employee employee = employeeService.getEmployeeById(request.employeeId());

        userAccount.setUsername(request.username());
        userAccount.setPassword(passwordEncoder.encode(request.password()));
        userAccount.setEmployee(employee);
        userAccount.setRole(Role.ADMIN);
        userAccountRepo.save(userAccount);

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
}
