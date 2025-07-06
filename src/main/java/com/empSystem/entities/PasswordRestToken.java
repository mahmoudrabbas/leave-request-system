package com.empSystem.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
public class PasswordRestToken {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;
    @Column(name = "token", nullable = false)
    private String token;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserAccount userAccount;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    public PasswordRestToken() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
