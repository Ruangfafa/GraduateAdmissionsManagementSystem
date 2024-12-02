package org.sysc4806g30.graduateadmissionsmanagementsystem.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.requestPackage.FileRequest;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.EventRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.ProfProfile;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.ProfProfileRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.Application;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.Event;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.ApplicationRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProfProfileRepository profProfileRepository;

    @GetMapping("/{userType}/{userUID}/api/eventList")
    @ResponseBody
    public List<Map<String, Object>> getUserEvents(@PathVariable String userType, @PathVariable Long userUID) {
        List<Map<String, Object>> eventList = new ArrayList<>();

        if (userType.equals("student")) {
            eventList.addAll(activeEvents());
            List<Application> applications = applicationRepository.findByUserUID(userUID);
            for (Application application : applications) {
                Long tempEventUID = application.getEventUID();
                if (eventList.stream().noneMatch(e -> e.get("eventID").equals(tempEventUID))) {
                    Map<String, Object> eventMap = new HashMap<>();
                    eventMap.put("eventID", tempEventUID);
                    eventMap.put("title",eventRepository.findTitleByEventUID(tempEventUID));
                    eventList.add(eventMap);
                }
            }
        } else if (userType.equals("admin")) {
            List<Event> allEvents = eventRepository.findAll();
            for (Event event : allEvents) {
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("eventID", event.getEventUID());
                eventMap.put("title", event.getTitle());
                eventList.add(eventMap);
            }
        } else if (userType.equals("professor")) {
            eventList.addAll(activeEvents());
            List<ProfProfile> allProfProfiles = profProfileRepository.findByProfUID(userUID);
            for (ProfProfile profProfile : allProfProfiles) {
                Long tempEventUID = profProfile.getEventUID();
                if (tempEventUID != null && eventList.stream().noneMatch(e -> e.get("eventID").equals(tempEventUID))) {
                    Map<String, Object> eventMap = new HashMap<>();
                    eventMap.put("eventID", tempEventUID);
                    eventMap.put("title", eventRepository.findTitleByEventUID(tempEventUID));
                    eventList.add(eventMap);
                }
            }
        }

        return eventList;
    }

    @PostMapping("/professor/{userUID}/profEvent/{eventUID}")
    @ResponseBody
    public String getUserEvents(@PathVariable Long userUID, @PathVariable Long eventUID, @RequestBody FileRequest fileRequest) {
//        System.out.println("receive type: " + fileRequest.getFileType() + "   student UID: " + fileRequest.getStudentUID());
        Application application = applicationRepository.getApplicationByStdUIDAndEventUID(fileRequest.getStudentUID(), eventUID);
        return application.getTargetFileEncode(fileRequest.getFileType());
    }

    @PostMapping("/admin/{uid}")
    @ResponseBody
    public Event createEvent(@RequestBody Event event, @PathVariable Long uid) {
        return eventRepository.save(event);
    }

    @GetMapping("/{userType}/api/eventList")
    @ResponseBody
    public List<Map<String, Object>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<Map<String, Object>> eventList = new ArrayList<>();
        for (Event event : events) {
            Map<String, Object> eventMap = new HashMap<>();
            eventMap.put("eventID", event.getEventUID());
            eventMap.put("title", event.getTitle());
            eventList.add(eventMap);
        }
        return eventList;
    }

    @GetMapping("/{userType}/{userUID}/api/eventDetails/{eventID}")
    @ResponseBody
    public String redirectToEventPage(
            @PathVariable String userType,
            @PathVariable Long userUID,
            @PathVariable Long eventID) {
        if (userType.equals("student"))
        return "/" + userType + "/" + userUID + "/stdEvent/" + eventID;
        if (userType.equals("professor")) return "/" + userType + "/" + userUID + "/profEvent/" + eventID;
        return "/" + userType + "/" + userUID + "/" + userType + "Event/" + eventID;
    }

    private List<Map<String, Object>> activeEvents() {
        List<Map<String, Object>> activeEventList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        List<Event> events = eventRepository.findAll();

        for (Event event : events) {
            LocalDate initDate = event.getInitDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long duration = event.getDur1() + event.getDur2() + event.getDur3() + event.getDur4() + event.getDur5();
            long daysBetween = ChronoUnit.DAYS.between(initDate, currentDate);

            if (daysBetween >= 0 && daysBetween <= duration) {
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("eventID", event.getEventUID());
                eventMap.put("title", event.getTitle());
                activeEventList.add(eventMap);
            }
        }
        return activeEventList;
    }
}
