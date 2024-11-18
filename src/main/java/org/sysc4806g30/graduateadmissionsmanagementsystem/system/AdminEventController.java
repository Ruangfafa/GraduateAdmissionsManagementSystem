package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdminEventController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProfProfileRepository profProfileRepository;

    // Renders the adminEvent page with applications data
    @GetMapping("/admin/{adminUID}/adminEvent/{eventUID}")
    public String viewEventApplications(@PathVariable Long adminUID, @PathVariable Long eventUID, Model model) {
        List<Application> applications = applicationRepository.findByEventUID(eventUID);
        model.addAttribute("eventUID", eventUID);
        model.addAttribute("applications", applications);
        return "adminEvent";
    }

    // Provides applications data as JSON for the given eventUID
    @GetMapping("/api/adminEvent/{eventUID}/applications")
    @ResponseBody
    public List<Application> getEventApplications(@PathVariable Long eventUID) {
        return applicationRepository.findByEventUID(eventUID);
    }

    @PostMapping("/admin/{applicationUID}/assignToProf")
    public ResponseEntity<String> assignToProf(@PathVariable Long applicationUID, @RequestBody Map<String, String> request) {
        Long profUID = Long.parseLong(request.get("profUID"));
        Long eventUID = Long.parseLong(request.get("eventUID"));

        Optional<Application> applicationOptional = applicationRepository.findById(applicationUID);

        if (applicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found.");
        }
        Long userUID = applicationOptional.get().getUserUID();
        ProfProfile profProfile = profProfileRepository.findByProfUIDAndEventUID(profUID, eventUID);

        if (!(profProfile ==null)) {
            String assignedList = profProfile.getAssignedstduidlist();

            if (assignedList == null) {
                assignedList = "";
            }

            if (!assignedList.contains(userUID.toString())) {
                assignedList += userUID + ",";
                profProfile.setAssignedstduidlist(assignedList);
                profProfileRepository.save(profProfile);
            }

            return ResponseEntity.ok("Assigned student successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor or Event not found.");
        }
    }
}
