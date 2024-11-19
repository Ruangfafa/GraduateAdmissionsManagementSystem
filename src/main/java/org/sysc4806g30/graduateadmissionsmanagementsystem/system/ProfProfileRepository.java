package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProfProfileRepository extends JpaRepository<ProfProfile, Long> {

    List<ProfProfile> findByProfUID(Long profUID);

    ProfProfile findByProfUIDAndEventUID(Long profUID, Long eventUID);

    @Query("SELECT p.profUID FROM ProfProfile p WHERE p.eventUID = :eventID")
    List<Long> findProUIDByEventUID(@Param("eventID") Long eventID);

    @Query("SELECT p FROM ProfProfile p WHERE p.eventUID = :eventID")
    List<ProfProfile> findByEventUID(@Param("eventID") Long eventID);

}
