package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.persistence.*;

@Entity
@Table(name = "PROFPROFILES")
public class ProfProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROFPROFILEUID")
    private Long profProfileUID;
    @Column(name = "PROFUID")
    private Long profUID;
    @Column(name = "RESEARCH")
    private String research;
    @Column(name = "EVENTUID")
    private Long eventUID;
    @Column(name = "INFO")
    private String info;



    public Long getEventUID() {
        return eventUID;
    }
}
