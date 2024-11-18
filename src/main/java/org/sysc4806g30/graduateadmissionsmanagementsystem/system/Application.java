package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.persistence.*;
import org.sysc4806g30.graduateadmissionsmanagementsystem.enums.PROFCOMMENT;

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
    private String diplomaFile;
    @Column(name = "GRADEAUDITF")
    private String gradeAuditFile;
    @Column(name = "USERUID")
    private Long userUID;
    @Column(name = "EVENTUID")
    private Long eventUID;
    @Column(name = "DESPROF")
    private String desireProfessors;
    @Column(name = "PROFCOMMENT")
    private PROFCOMMENT profcomment;

    public Long getApplicationUID() {return applicationUID;}
    public String getCoverLetterFile() {return coverLetterFile;}
    public String getDiplomaFile() {return diplomaFile;}
    public String getGradeAuditFile() {return gradeAuditFile;}
    public Long getUserUID() {return userUID;}
    public String getDesireProfessors() {return desireProfessors;}
    public Long getEventUID() {
        return eventUID;
    }
}
