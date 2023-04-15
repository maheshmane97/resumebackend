package com.humancloud.resume.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id",columnDefinition = "UUID")
    private UUID userId;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String createdDate;
    @JsonIgnore
    private String deleteStatus;
    @JsonIgnore
    private String modifiedBy;
    @JsonIgnore
    private String modifiedDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name = "email", referencedColumnName = "email"),
    inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

}
