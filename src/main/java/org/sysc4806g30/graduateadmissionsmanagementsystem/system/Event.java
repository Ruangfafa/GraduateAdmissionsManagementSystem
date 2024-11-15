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
        this.info = title + "%" + description;
    }

    public String getInfo() {
        return info;
    }

    public String getTitle() {
        if (info != null && info.contains("%")) {
            return info.split("%")[0];
        }
        return null;
    }

    public String getDescription() {
        if (info != null && info.contains("%")) {
            String[] parts = info.split("%");
            if (parts.length > 1) {
                return parts[1];
            }
        }
        return null;
    }
}
