package com.hackathon.ibm.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "employee_id", unique = true)
    private String employeeId;

    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name = "access", nullable = false)
    private AccessLevel accessLevel = AccessLevel.LOW;

    @Column(nullable = false)
    private Integer performance = 0;

    @Column(nullable = false)
    private Integer salaryBand = 1;

    @Column(name = "tenure_months", nullable = false)
    private Integer tenureMonths = 0; // default 0 for new employee

}
