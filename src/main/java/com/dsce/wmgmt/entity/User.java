package com.dsce.wmgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "water_mgmt")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String role;
    @Column(name = "keycloak_id")
    private String keycloakId;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }
}

