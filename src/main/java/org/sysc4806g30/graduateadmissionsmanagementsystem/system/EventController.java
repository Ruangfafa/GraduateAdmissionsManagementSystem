package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<Long> getUserEvents(@PathVariable String userType, @PathVariable Long userUID) {
        if (userType.equals("std")){
            List<Application> applications = applicationRepository.findByUserUID(userUID);

            ArrayList<Long> eventIDs = new ArrayList<>();
            for (Long eventID : activeEvents()){
                eventIDs.add(eventID);
            }
            for (Application application : applications) {
                Long tempEventUID = application.getEventUID();
                if (!eventIDs.contains(tempEventUID)) eventIDs.add(tempEventUID);
            }
            return eventIDs;
        }
        else if (userType.equals("admin")){
            List<Event> allEvents = eventRepository.findAll();
            ArrayList<Long> eventIDs = new ArrayList<>();
            for (Event event : allEvents) {
                eventIDs.add(event.getEventUID());
            }
            return eventIDs;
        }
        else if (userType.equals("professor")){
            List<ProfProfile> allProfProfiles = profProfileRepository.findByProfUID(userUID);

            ArrayList<Long> eventIDs = new ArrayList<>();
            for (ProfProfile profProfile : allProfProfiles) {
                Long tempEventUID = profProfile.getEventUID();
                if (!eventIDs.contains(tempEventUID)) eventIDs.add(tempEventUID);
            }
            return eventIDs;
        }
        else {
            return null;
        }
    }

    @PostMapping("/admin/{uid}")
    @ResponseBody
    public Event createEvent(@RequestBody Event event, @PathVariable Long uid) {
        return eventRepository.save(event);
    }


    private ArrayList<Long> activeEvents(){
        ArrayList<Long> eventIDs = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            LocalDate initDate = event.getInitDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long duration = event.getDur1()+event.getDur2()+event.getDur3()+event.getDur4()+event.getDur5();
            long daysBetween = ChronoUnit.DAYS.between(initDate, currentDate);
            if (daysBetween >= 0 && daysBetween <= duration) {
                eventIDs.add(event.getEventUID());
            }
        }
        return eventIDs;
    }
}
