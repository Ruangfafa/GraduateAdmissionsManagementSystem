package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.persistence.*;

@Entity
@Table(name = "MAJORS")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MAJORUID")
    private Long majorUID;
    @Column(name = "EVENTUID")
    private String majorName;
    @Column(name = "MAJORNAME")
    private Long eventUID;
    @Column(name = "PROFUID")
    private Long profUID;

    public Long getEventUID() {
        return eventUID;
    }
}
