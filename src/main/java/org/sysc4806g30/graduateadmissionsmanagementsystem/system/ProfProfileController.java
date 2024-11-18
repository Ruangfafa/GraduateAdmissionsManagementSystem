package org.sysc4806g30.graduateadmissionsmanagementsystem.system;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/professor")
public class ProfProfileController {
    private final ProfProfileService profProfileService;
    private static final Logger logger = LoggerFactory.getLogger(ProfProfileController.class);

    @Autowired
    public ProfProfileController(ProfProfileService profProfileService) {
        this.profProfileService = profProfileService;
    }

    @PostMapping("/{profUID}/profevent/{eventUID}/submit")
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
            List<ProfProfile> existingProfiles = profProfileService.getProfProfilesByProfUIDAndEventUID(profUID, eventUID);
            for (ProfProfile existing : existingProfiles) {
                profProfileService.deleteProfProfile(existing.getProfProfileUID());
            }

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

    @GetMapping("/{profUID}/profevent/{eventUID}/page")
    public String getProfProfilePage() {
        return "profProfile";
    }

    @GetMapping("/{profUID}/profevent/{eventUID}/data")
    @ResponseBody
    public List<ProfProfile> getProfProfilesByProfUIDAndEventUID(
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
