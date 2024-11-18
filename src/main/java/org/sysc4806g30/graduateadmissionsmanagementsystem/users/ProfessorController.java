package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.ApplicationService;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.SelectionRateUpdate;

import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ApplicationService applicationService;

    // Render the applications.html page for a specific professor and event
    @GetMapping("/{profUID}/profEvent/{eventUID}")
    public String serveApplicationsPage(@PathVariable Long profUID, @PathVariable Long eventUID) {
        return "applications"; // Points to applications.html in src/main/resources/templates
    }

    // Retrieve assigned students for a specific professor and event as JSON
    @ResponseBody
    @GetMapping("/{profUID}/profEvent/{eventUID}/assigned-students")
    public List<Long> getAssignedStudents(@PathVariable Long profUID, @PathVariable Long eventUID) {
        return applicationService.getAssignedStudentsForEvent(profUID, eventUID); // Updated service method
    }

    @PostMapping("/submit-selection")
    public ResponseEntity<?> submitSelection(@RequestBody List<SelectionRateUpdate> selections) {
        selections.forEach(selection -> {
            System.out.println("Student ID: " + selection.getStudentId());
            System.out.println("Rating: " + selection.getRating());

            // Call the service method to update the database
            applicationService.updateProfCommentByUserUID(selection.getStudentId(), selection.getRating());
        });

        return ResponseEntity.ok("Selection submitted successfully!");
    }
}
