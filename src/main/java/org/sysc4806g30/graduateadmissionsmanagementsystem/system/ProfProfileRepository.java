package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProfProfileRepository extends JpaRepository<ProfProfile, Long> {

    List<ProfProfile> findByProfUID(Long profUID);

    List<ProfProfile> findByEventUID(Long eventUID);

    List<ProfProfile> findByProfUIDAndEventUID(Long profUID, Long eventUID);

    @Query("SELECT p FROM ProfProfile p WHERE p.profUID = :profUID AND p.eventUID = :eventUID")
    List<ProfProfile> findProfilesByProfAndEvent(
            @Param("profUID") Long profUID,
            @Param("eventUID") Long eventUID
    );

    @Query("SELECT p.profUID FROM ProfProfile p WHERE p.eventUID = :eventID")
    List<Long> findProUIDByEventUID(@Param("eventID") Long eventID);

}
