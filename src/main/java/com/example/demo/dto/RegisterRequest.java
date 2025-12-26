package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    
    public static RegisterRequestBuilder builder() {
        return new RegisterRequestBuilder();
    }
    
    public static class RegisterRequestBuilder {
        private String name;
        private String email;
        private String password;
        private String role;
        
        public RegisterRequestBuilder name(String name) { this.name = name; return this; }
        public RegisterRequestBuilder email(String email) { this.email = email; return this; }
        public RegisterRequestBuilder password(String password) { this.password = password; return this; }
        public RegisterRequestBuilder role(String role) { this.role = role; return this; }
        
        public RegisterRequest build() {
            RegisterRequest request = new RegisterRequest();
            request.name = this.name;
            request.email = this.email;
            request.password = this.password;
            request.role = this.role;
            return request;
        }
    }
    
    public RegisterRequest() {}
}