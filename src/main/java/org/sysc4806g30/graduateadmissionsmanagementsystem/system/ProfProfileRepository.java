package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProfProfileRepository extends JpaRepository<ProfProfile, Long> {
    List<ProfProfile> findByProfUID(Long profUID);
}
