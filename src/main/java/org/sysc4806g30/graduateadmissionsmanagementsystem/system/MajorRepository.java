package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByProfUID(Long profUID);
}
