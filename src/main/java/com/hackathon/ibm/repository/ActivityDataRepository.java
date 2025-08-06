package com.hackathon.ibm.repository;

import com.hackathon.ibm.model.ActivityData;
import com.hackathon.ibm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface ActivityDataRepository extends JpaRepository<ActivityData, Long>{
    Optional<ActivityData> findByUserAndDate(User user, LocalDate date);
}
