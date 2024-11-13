package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.persistence.*;

@Entity
@Table(name = "APPLICATIONS")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APPLICATIONUID")
    private Long applicationUID;
    @Column(name = "CVF")
    private String coverLetterFile;
    @Column(name = "DIPLOMAF")
    private String deplomaFile;
    @Column(name = "GRADEAUDITF")
    private String gradeAuditFile;
    @Column(name = "USERUID")
    private Long userUID;
    @Column(name = "EVENTUID")
    private Long eventUID;
    @Column(name = "DESPROF")
    private String desireProfessors;

    public Long getEventUID() {
        return eventUID;
    }
}