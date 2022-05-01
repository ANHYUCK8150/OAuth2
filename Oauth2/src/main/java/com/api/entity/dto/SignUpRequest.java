package com.api.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

    private String name;
    private String email;
    private String password;
    
    public SignUpRequest() {
    	
    }

    @Builder
    public SignUpRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
