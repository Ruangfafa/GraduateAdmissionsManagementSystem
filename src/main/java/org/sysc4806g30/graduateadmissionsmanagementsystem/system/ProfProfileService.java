package org.sysc4806g30.graduateadmissionsmanagementsystem.system;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.User;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProfProfileService {
    private final ProfProfileRepository profProfileRepository;

    @Autowired
    public ProfProfileService(ProfProfileRepository profProfileRepository) {
        this.profProfileRepository = profProfileRepository;
    }

    @Transactional
    public ProfProfile saveProfProfile(ProfProfile profProfile) {
        if (profProfile == null) {
            throw new IllegalArgumentException("ProfProfile cannot be null");
        }

        if (profProfile.getProfUID() == null || profProfile.getEventUID() == null) {
            throw new IllegalArgumentException("ProfUID and EventUID must be provided");
        }
        return profProfileRepository.save(profProfile);
    }

    public ProfProfile getProfProfileByID(long profProfileUID) {
        return profProfileRepository.findById(profProfileUID)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profile not found with ID: " + profProfileUID));
    }

    public List<ProfProfile> getAllProfProfiles() {
        return profProfileRepository.findAll();
    }



    public List<ProfProfile> getProfProfilesByProfUID(Long profUID) {
        if (profUID == null) {
            throw new IllegalArgumentException("ProfUID cannot be null");
        }
        return profProfileRepository.findByProfUID(profUID);
    }

    public List<ProfProfile> getProfProfilesByProfUIDAndEventUID(Long profUID, Long eventUID) {
        if (profUID == null || eventUID == null) {
            throw new IllegalArgumentException("ProfUID and EventUID cannot be null");
        }

        return profProfileRepository.findByProfUIDAndEventUID(profUID, eventUID);
    }
}