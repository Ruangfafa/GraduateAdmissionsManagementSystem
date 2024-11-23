package org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e.title FROM Event e WHERE e.eventUID = :eventID")
    String findTitleByEventUID(@Param("eventID") Long eventID);
}

