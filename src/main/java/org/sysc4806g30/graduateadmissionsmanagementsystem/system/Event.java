package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENTUID")
    private long eventUID;
    @Column(name = "INITDATE")
    private Date initDate;
    @Column(name = "DURATION")
    private String durations;
    @Column(name = "INFO")
    private String info;
    @Column(name = "PROFS")
    private String profs;
    public void setEventUID(Long eventUID) {
        this.eventUID = eventUID;
    }
    public Long getEventUID() {
        return eventUID;
    }
    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }
    public Date getInitDate() {
        return initDate;
    }
    public void setDurations(String durations) {
        this.durations = durations;
    }
    public String getDurations() {
        return durations;
    }
    public void setInfo(String title, String description) {
        this.info = title + "|" + description;
    }
    public String getInfo(String info) {
        String[] tempInfoSet = info.split("\\|");
        switch (this.info) {
            case "title":
                return tempInfoSet[0];
            case "description":
                return tempInfoSet[1];
            default:
                return null;
        }
    }
}
