package com.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oauth.entity.AuthProvider;
import com.oauth.entity.Role;
import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String imageUrl;

    @Column
    private Role role;

    @JsonIgnore
    @Column
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column
    private String providerId;

    @Builder
    public User(String name, String email, String imageUrl, Role role, Boolean emailVerified, String password, AuthProvider provider, String providerId) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public User update(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }
}
