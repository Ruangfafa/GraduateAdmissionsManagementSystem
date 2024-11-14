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
    @Column(name = "ASSIGNEDSTDUIDLIST")
    private String assignedstduidlist;
    @Column(name = "FINALSTDLIST")
    private String finalstdlist;



    public Long getEventUID() {
        return eventUID;
    }
    public void setEventUID(Long eventUID) {
        this.eventUID = eventUID;
    }
    public Long getProfProfileUID() {
        return profProfileUID;
    }
    public void setProfProfileUID(Long profProfileUID) {
        this.profProfileUID = profProfileUID;
    }
    public Long getProfUID() {
        return profUID;
    }
    public void setProfUID(Long profUID) {
        this.profUID = profUID;
    }
    public String getResearch() {
        return research;
    }
    public void setResearch(String research) {
        this.research = research;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getAssignedstduidlist() {
        return assignedstduidlist;
    }
    public void setAssignedstduidlist(String assignedstduidlist) {
        this.assignedstduidlist = assignedstduidlist;
    }
    public String getFinalstdlist() {
        return finalstdlist;
    }
    public void setFinalstdlist(String finalstdlist) {
        this.finalstdlist = finalstdlist;
    }

}
