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
    List<Application> findByEventUID(Long eventUID);

    @Query("SELECT a FROM Application a WHERE a.userUID = :stdUID AND a.eventUID = :eventUID")
    Application getApplicationByStdUIDAndEventUID(@Param("stdUID") Long stdUID, @Param("eventUID") Long eventUID);

    @Modifying
    @Transactional
    @Query("UPDATE Application a SET " +
            "a.coverLetterFile = :CVFile, a.diplomaFile = :diplomaFile, a.gradeAuditFile = :gradeFile," +
            "a.desireProfessors = :desProfs, a.stdFields = :stdFields " +
            "WHERE a.userUID = :stdUID AND a.eventUID = :eventUID")
    void overWriteApplication(@Param("stdUID") Long stdUID, @Param("eventUID") Long eventUID,
                              @Param("CVFile") String CVFile, @Param("diplomaFile") String diplomaFile, @Param("gradeFile") String gradeFile,
                              @Param("desProfs") String desProfs, @Param("stdFields") String stdFields);

}