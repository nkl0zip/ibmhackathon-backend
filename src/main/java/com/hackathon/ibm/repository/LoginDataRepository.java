package com.hackathon.ibm.repository;

import com.hackathon.ibm.model.LoginData;
import com.hackathon.ibm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
public interface LoginDataRepository extends JpaRepository<LoginData, Long> {
    Optional<LoginData> findByUserAndDate(User user, LocalDate date);
    Optional<LoginData> findByUser(User user);
    List<LoginData> findByDate(LocalDate date);
}
