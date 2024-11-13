package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProfProfileRepository profProfileRepository;

    @GetMapping("/{userType}/{userUID}")
    public ArrayList<Long> getUserEvents(@PathVariable String userType, @PathVariable Long userUID) {
        if (userType.equals("std")){
            List<Application> applications = applicationRepository.findByUserUID(userUID);

            ArrayList<Long> eventIDs = new ArrayList<>();
            for (Application application : applications) {
                Long tempEventUID = application.getEventUID();
                if (!eventIDs.contains(tempEventUID))eventIDs.add(tempEventUID);
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
        else if (userType.equals("prof")){
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

    @PostMapping
    @ResponseBody
    public Event createEvent(@RequestBody Event event) {return eventRepository.save(event);}
}
