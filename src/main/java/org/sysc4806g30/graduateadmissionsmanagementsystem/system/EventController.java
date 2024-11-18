package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userType}/api/eventDetails/{eventID}")
    @ResponseBody
    public Map<String, String> getEventDetails(@PathVariable Long eventID) {
        Event event = eventRepository.findById(eventID).orElse(null);
        Map<String, String> details = new HashMap<>();
        if (event != null) {
            details.put("title", event.getTitle());
            details.put("description", event.getDescription());
        }
        return details;
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
