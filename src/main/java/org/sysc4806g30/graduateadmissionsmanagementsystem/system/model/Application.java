package org.sysc4806g30.graduateadmissionsmanagementsystem.system.model;

import jakarta.persistence.*;

import java.util.Base64;
import java.util.Objects;

@Entity
@Table(name = "APPLICATIONS")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APPLICATIONUID")
    private Long applicationUID;
    @Column(name = "CVF", length = 16777215)
    private byte[] coverLetterFile;
    @Column(name = "DIPLOMAF", length = 16777215)
    private byte[] diplomaFile;
    @Column(name = "GRADEAUDITF", length = 16777215)
    private byte[] gradeAuditFile;
    @Column(name = "USERUID")
    private Long userUID;
    @Column(name = "EVENTUID")
    private Long eventUID;
    @Column(name = "DESPROF")
    private String desireProfessors;
    @Column(name = "PROFCOMMENT")
    private String profcomment;
    @Column(name = "STDFIELDS")
    private String stdFields;
    private String cv64;
    private String dp64;
    private String gd64;

    // Getters for all fields
    public Long getApplicationUID() {
        return applicationUID;
    }

    public byte[] getCoverLetterFile() {
        return coverLetterFile;
    }

    public byte[] getDiplomaFile() {
        return diplomaFile;
    }

    public byte[] getGradeAuditFile() {
        return gradeAuditFile;
    }

    public Long getUserUID() {
        return userUID;
    }

    public String getDesireProfessors() {return desireProfessors;}

    public Long getEventUID() {
        return eventUID;
    }

    public String getStdFields(){return stdFields;}

    // Getters and Setters
    public String getProfcomment() { // Getter matches field name
        return profcomment;
    }

    public void setProfcomment(String profcomment) { // Setter matches field name
        this.profcomment = profcomment;
    }

    public void updateFileData(){
        this.coverLetterFile =  Base64.getDecoder().decode(this.cv64);
        this.diplomaFile =  Base64.getDecoder().decode(this.dp64);
        this.gradeAuditFile =  Base64.getDecoder().decode(this.gd64);
        this.cv64 = "";
        this.dp64 = "";
        this.gd64 = "";
    }

    public String getCv64(){return this.cv64;}
    public String getDp64(){return this.dp64;}
    public String getGd64(){return this.gd64;}

    public String getTargetFileEncode(String fileType){
        byte[] fileByte = null;
        String encodeString = "";
        if (Objects.equals(fileType, "cv")) {
            fileByte = this.coverLetterFile;
        } else if (Objects.equals(fileType, "dp")) {
            fileByte = this.diplomaFile;
        } else if (Objects.equals(fileType, "gd")){
            fileByte = this.gradeAuditFile;
        }else {
            System.out.println("incorrect file type - received: " + fileType);
        }
        if (fileByte != null) {
            encodeString = Base64.getEncoder().encodeToString(fileByte);
        }
        return encodeString;
    }
}
