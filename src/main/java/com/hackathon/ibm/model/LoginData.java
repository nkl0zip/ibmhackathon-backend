package com.hackathon.ibm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "login_data", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class LoginData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDate date; // date for daily tracking

    @Column(nullable = false)
    private Integer loginHours = 0; // hours logged in for this day

    @Column(nullable = false)
    private Integer failedLoginAttempts = 0; // reset monthly
}
