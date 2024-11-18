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
    private String profcomment;

    // Getters for all fields
    public Long getApplicationUID() {
        return applicationUID;
    }

    public String getCoverLetterFile() {
        return coverLetterFile;
    }

    public String getDiplomaFile() {
        return diplomaFile;
    }

    public String getGradeAuditFile() {
        return gradeAuditFile;
    }

    public Long getUserUID() {
        return userUID;
    }

    public Long getEventUID() {
        return eventUID;
    }

    public String getDesProf() {
        return desireProfessors;
    }



    // Getters and Setters
    public String getProfcomment() { // Getter matches field name
        return profcomment;
    }

    public void setProfcomment(String profcomment) { // Setter matches field name
        this.profcomment = profcomment;
    }
    public String getDesireProfessors() {
        return desireProfessors;
    }


}
