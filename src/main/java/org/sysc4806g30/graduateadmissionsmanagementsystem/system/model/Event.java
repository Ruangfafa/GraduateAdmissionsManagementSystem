package org.sysc4806g30.graduateadmissionsmanagementsystem.system.model;

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

    @Column(name = "DUR1")
    private int dur1;

    @Column(name = "DUR2")
    private int dur2;

    @Column(name = "DUR3")
    private int dur3;

    @Column(name = "DUR4")
    private int dur4;

    @Column(name = "DUR5")
    private int dur5;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

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

    public void setDur1(int dur1) {this.dur1 = dur1;}

    public int getDur1() {return dur1;}

    public void setDur2(int dur2) {this.dur2 = dur2;}

    public int getDur2() {return dur2;}

    public void setDur3(int dur3) {this.dur3 = dur3;}

    public int getDur3() {return dur3;}

    public void setDur4(int dur4) {this.dur4 = dur4;}

    public int getDur4() {return dur4;}

    public void setDur5(int dur5) {this.dur5 = dur5;}

    public int getDur5() {return dur5;}

    public void setTitle(String title) {this.title = title;}

    public String getTitle() {return title;}

    public void setDescription(String description) {this.description = description;}

    public String getDescription() {return description;}
}
