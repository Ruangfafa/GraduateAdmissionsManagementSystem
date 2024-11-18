package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserUID(Long userUID);


    // Custom query to update PROFCOMMENT
    @Transactional
    @Modifying
    @Query("UPDATE Application a SET a.profcomment = :profComment WHERE a.applicationUID = :applicationUID")
    void updateProfComment(@Param("applicationUID") Long applicationUID, @Param("profComment") String profComment);
}