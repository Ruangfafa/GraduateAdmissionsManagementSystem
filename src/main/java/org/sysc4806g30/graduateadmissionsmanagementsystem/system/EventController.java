package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private MajorRepository majorRepository;

    // 获取特定用户参与的事件列表
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
            List<Major> allMajors = majorRepository.findByProfUID(userUID);

            ArrayList<Long> eventIDs = new ArrayList<>();
            for (Major major : allMajors) {
                Long tempEventUID = major.getEventUID();
                if (!eventIDs.contains(tempEventUID)) eventIDs.add(tempEventUID);
            }
            return eventIDs;
        }
        else {
            return null;
        }
    }

}
