package org.sysc4806g30.graduateadmissionsmanagementsystem.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.User;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;

import java.util.List;

@Service
public class ProfProfileService {

    @Autowired
    private ProfProfileRepository profProfileRepository;

    public ProfProfile saveProfProfile(ProfProfile profProfile) {
        return profProfileRepository.save(profProfile);
    }

    public ProfProfile getProfProfileByID(long profProfileUID) {
        return profProfileRepository.findById(profProfileUID).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public List<ProfProfile> getAllProfProfiles() {
        return profProfileRepository.findAll();
    }

    public List<ProfProfile> getProfProfilesByProfUID(Long profUID) {
        return profProfileRepository.findByProfUID(profUID);
    }
}
