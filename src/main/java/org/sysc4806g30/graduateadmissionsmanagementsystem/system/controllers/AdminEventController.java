package org.sysc4806g30.graduateadmissionsmanagementsystem.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.Application;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.ProfProfile;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.ApplicationRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.ProfProfileRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.services.ApplicationService;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.services.MailingSystem;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.services.ProfProfileService;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminEventController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProfProfileRepository profProfileRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ProfProfileService profProfileService;

    @Autowired
    private MailingSystem mailingSystem;

    @Autowired
    private UserRepository userRepository;

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
            mailingSystem.sendEmail(profUID,"GAMS Auto Mailing","There are new students just assigned to you.");
            return ResponseEntity.ok("Assigned student successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor or Event not found.");
        }
    }

    @GetMapping("/admin/{adminUID}/adminEvent/{eventUID}/finalDecision")
    public String viewFinalDecisionPage(@PathVariable Long adminUID,
                                        @PathVariable Long eventUID,
                                        Model model) {
        // Get all applications for this event
        List<Application> applications = applicationRepository.findByEventUID(eventUID);

        // Get all professor UIDs for this event
        List<Long> profUIDs = profProfileRepository.findProUIDByEventUID(eventUID);

        // Create a map of professor assignments
        Map<Long, List<Application>> profApplications = new HashMap<>();

        for (Long profUID : profUIDs) {
            ProfProfile profile = profProfileRepository.findByProfUIDAndEventUID(profUID, eventUID);
            List<Application> profApps = new ArrayList<>();
            if (profile != null) {
                String[] assignedStudents = profile.getAssignedstduidlist().split(",");

                for (String stdUid : assignedStudents) {
                    if (!stdUid.trim().isEmpty()) {
                        Long studentId = Long.parseLong(stdUid.trim());
                        applications.stream()
                                .filter(app -> app.getUserUID().equals(studentId))
                                .findFirst()
                                .ifPresent(profApps::add);
                    }
                }

                profApplications.put(profUID, profApps);
            }
        }

        model.addAttribute("profApplications", profApplications);
        model.addAttribute("adminUID", adminUID);
        model.addAttribute("eventUID", eventUID);
        return "finalDecisionPage";
    }

    @GetMapping("/admin/{userUID}/adminEvent/{eventID}/fetchFinalDecision/api")
    @ResponseBody
    public String fetchFinalDecision(
            @PathVariable Long userUID,
            @PathVariable Long eventID) {
        return "/admin/" + userUID + "/adminEvent/" + eventID + "/finalDecision";
    }

    @PostMapping("/admin/{adminUID}/adminEvent/{eventUID}/profprofile/{profUID}/finalDecision")
    @ResponseBody
    public ResponseEntity<?> submitFinalDecision(
            @PathVariable Long adminUID,
            @PathVariable Long eventUID,
            @PathVariable Long profUID,
            @RequestParam(value = "selectedStudents[]", required = false) List<Long> selectedStudents) {

        try {
            // Find the specific prof profile
            ProfProfile profProfile = profProfileRepository.findByProfUIDAndEventUID(profUID, eventUID);

            if (profProfile == null) {
                return ResponseEntity.notFound().build();
            }

            // Update finalstdlist
            if (selectedStudents != null && !selectedStudents.isEmpty()) {
                profProfile.setFinalstdlist(String.join(",",
                        selectedStudents.stream().map(String::valueOf).collect(Collectors.toList())));
            } else {
                profProfile.setFinalstdlist("");
            }

            profProfileRepository.save(profProfile);
            String mailingBody = "Your Final Students List are Decided:\n";
            StringJoiner studentNames = new StringJoiner(", ");
            for (String userUID : selectedStudents.stream().map(String::valueOf).collect(Collectors.toList())) {
                studentNames.add(userRepository.findNameById(Long.parseLong(userUID)));
                mailingSystem.sendEmail(Long.valueOf(userUID), "GAMS Auto Mailing", "You are registered to professor "+userRepository.findNameById(profUID)+".");
            }
            mailingBody += studentNames.toString();
            mailingSystem.sendEmail(profUID, "GAMS Auto Mailing", mailingBody);
            return ResponseEntity.ok().body("Final decisions saved successfully for professor " + profUID);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving final decisions: " + e.getMessage());
        }
    }
}

