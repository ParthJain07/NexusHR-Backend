package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.enums.Role;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable { // 1. Add implements Serializable

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    private String department;
    private String designation;
    private LocalDate joiningDate;

    @JsonIgnore // 2. Add @JsonIgnore to prevent infinite looping!
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserAuth userAuth;

    @JsonProperty("role")
    public Role getRole() {
        return userAuth != null ? userAuth.getRole() : null;
    }
}
