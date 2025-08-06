package com.hackathon.ibm.repository;

import com.hackathon.ibm.model.ModelData;
import com.hackathon.ibm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ModelDataRepository extends JpaRepository<ModelData, Long>{
    List<ModelData> findByUser(User user);
}
