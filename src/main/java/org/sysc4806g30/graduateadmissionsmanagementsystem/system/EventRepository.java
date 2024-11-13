package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
}

