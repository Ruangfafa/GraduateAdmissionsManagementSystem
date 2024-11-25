package org.sysc4806g30.graduateadmissionsmanagementsystem.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.ProfProfile;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.services.ProfProfileService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final ProfProfileService profProfileService;

    @Autowired
    public StudentController(ProfProfileService profProfileService) {
        this.profProfileService = profProfileService;
    }

    @GetMapping("/stdEvent/{eventUID}/profProfiles")
    @ResponseBody
    public List<ProfProfile> getProfProfilesByEventUID(@PathVariable Long eventUID) {
        try {
            return profProfileService.getProfProfilesByEventUID(eventUID);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error fetching profiles: " + e.getMessage()
            );
        }
    }
}
