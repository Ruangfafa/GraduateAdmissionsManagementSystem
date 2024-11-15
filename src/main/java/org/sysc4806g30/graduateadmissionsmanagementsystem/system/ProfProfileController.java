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

@Controller
@RequestMapping("/prof")
public class ProfProfileController {
    private final ProfProfileService profProfileService;
    private static final Logger logger = LoggerFactory.getLogger(ProfProfileController.class);

    @Autowired
    public ProfProfileController(ProfProfileService profProfileService) {
        this.profProfileService = profProfileService;
    }

    @PostMapping("/{profUID}/profevent/{eventUID}")
    @ResponseBody
    public ProfProfile createProfProfile(
            @PathVariable Long profUID,
            @PathVariable Long eventUID,
            @RequestBody ProfProfile profProfile) {

        profProfile.setProfUID(profUID);
        profProfile.setEventUID(eventUID);

        ProfProfile saved = profProfileService.saveProfProfile(profProfile);
        return saved;
    }

    @GetMapping("/{profUID}/profevent/{eventUID}")
    public String getProfProfilePage() {
        return "prof";
    }

    @GetMapping("/{profUID}")
    public @ResponseBody List<ProfProfile> getProfProfilesByProfUID(@PathVariable Long profUID) {
        return profProfileService.getProfProfilesByProfUID(profUID);
    }

    @GetMapping("/{profUID}/profevent/{eventUID}/data")
    public @ResponseBody List<ProfProfile> getProfProfilesByProfUIDAndEventUID(
            @PathVariable Long profUID,
            @PathVariable Long eventUID) {
        return profProfileService.getProfProfilesByProfUIDAndEventUID(profUID, eventUID);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ProfProfile> getAllProfProfiles() {
        return profProfileService.getAllProfProfiles();
    }

}
