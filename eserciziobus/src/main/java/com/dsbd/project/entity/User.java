package com.dsbd.project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // meglio evitare "user" che è parola riservata in DB
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The email parameter must not be blank!")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "The password parameter must not be blank!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @NotNull(message = "The name parameter must not be blank!")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Credit must be non-negative!")
    @Column(nullable = false)
    private BigDecimal credit = BigDecimal.ZERO;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    // Getters e Setters
    public Integer getId() { return id; }
    public User setId(Integer id) { this.id = id; return this; }

    public String getEmail() { return email; }
    public User setEmail(String email) { this.email = email; return this; }

    public String getPassword() { return password; }
    public User setPassword(String password) { this.password = password; return this; }

    public String getName() { return name; }
    public User setName(String name) { this.name = name; return this; }

    public BigDecimal getCredit() { return credit; }
    public User setCredit(BigDecimal credit) {
        if (credit != null && credit.compareTo(BigDecimal.ZERO) >= 0) {
            this.credit = credit;
        }
        return this;
    }

    public List<String> getRoles() { return roles; }
    public User setRoles(List<String> roles) {
        this.roles = roles != null ? roles : new ArrayList<>();
        return this;
    }
}
