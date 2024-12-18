package org.sysc4806g30.graduateadmissionsmanagementsystem.system.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.services.ProfProfileService;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.ProfProfile;

@Controller
@RequestMapping("/professor")
public class ProfProfileController {
    private final ProfProfileService profProfileService;
    private static final Logger logger = LoggerFactory.getLogger(ProfProfileController.class);

    @Autowired
    public ProfProfileController(ProfProfileService profProfileService) {
        this.profProfileService = profProfileService;
    }

    @PostMapping("/{profUID}/profEvent/{eventUID}/submit")
    @ResponseBody
    public ProfProfile createProfProfile(
            @PathVariable Long profUID,
            @PathVariable Long eventUID,
            @RequestBody ProfProfile profProfile) {
        try {
            logger.info("Received profile submission for profUID: {} and eventUID: {}", profUID, eventUID);
            logger.debug("Profile data: {}", profProfile);

            profProfile.setProfUID(profUID);
            profProfile.setEventUID(eventUID);

            // Delete existing profiles
            ProfProfile existingProfiles = profProfileService.getProfProfilesByProfUIDAndEventUID(profUID, eventUID);
            if (existingProfiles != null) {profProfileService.deleteProfProfile(existingProfiles.getProfProfileUID());}



            // Save new profile
            ProfProfile saved = profProfileService.saveProfProfile(profProfile);
            logger.info("Created profile for profUID: {} and eventUID: {}", profUID, eventUID);
            return saved;
        } catch (Exception e) {
            logger.error("Error creating profile", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating profile: " + e.getMessage()
            );
        }
    }

    @GetMapping("/{profUID}/profEvent/{eventUID}/page")
    public String getProfProfilePage() {
        return "profProfile";
    }

    @GetMapping("/{profUID}/profEvent/{eventUID}/data")
    @ResponseBody
    public ProfProfile getProfProfilesByProfUIDAndEventUID(
            @PathVariable Long profUID,
            @PathVariable Long eventUID) {
        try {
            return profProfileService.getProfProfilesByProfUIDAndEventUID(profUID, eventUID);
        } catch (Exception e) {
            logger.error("Error fetching profiles", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error fetching profiles: " + e.getMessage()
            );
        }
    }
}
