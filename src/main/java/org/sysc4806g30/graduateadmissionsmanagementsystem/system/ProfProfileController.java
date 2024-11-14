package org.sysc4806g30.graduateadmissionsmanagementsystem.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prof")
public class ProfProfileController {
    @Autowired
    private ProfProfileService profProfileService;

    @PostMapping("/{profUID}/profevent/{eventUID}")
    public @ResponseBody ProfProfile createProfProfile(@PathVariable Long profUID, @PathVariable Long eventUID, @RequestBody ProfProfile profProfile) {
        profProfile.setProfUID(profUID);
        profProfile.setEventUID(eventUID);
        return profProfileService.saveProfProfile(profProfile);
    }

    @GetMapping("/{profUID}/profevent/{eventUID}")
    public @ResponseBody List<ProfProfile> getProfProfilesByProfUIDAndEventUID(@PathVariable Long profUID, @PathVariable Long eventUID) {
        return profProfileService.getProfProfilesByProfUID(profUID).stream()
                .filter(profile -> profile.getEventUID().equals(eventUID))
                .toList();
    }

    @GetMapping("/{profUID}")
    public @ResponseBody List<ProfProfile> getProfProfilesByProfUID(@PathVariable Long profUID) {
        return profProfileService.getProfProfilesByProfUID(profUID);
    }

    @GetMapping
    public List<ProfProfile> getAllProfProfiles() {
        return profProfileService.getAllProfProfiles();
    }

    @GetMapping("/profProfilePage")
    public String getProfProfilePage() {
        return "prof";
    }
}
