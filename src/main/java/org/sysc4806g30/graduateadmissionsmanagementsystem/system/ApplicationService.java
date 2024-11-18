package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;

import java.util.HashMap;

@Service
@Transactional
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ProfProfileRepository profProfileRepository;
    @Autowired
    private UserRepository userRepository;

    public Application saveApplication(Application application) {
        if (application == null) {
            throw new NullPointerException("Application is null");
        }
        if (application.getEventUID() == null) {
            throw new NullPointerException("Event UID must be provided");
        }
        if (application.getUserUID() == null) {
            throw new NullPointerException("User UID must be provided");
        }
        if (application.getCoverLetterFile() == null) {
            throw new NullPointerException("CV File must be provided");
        }
        if (application.getDiplomaFile() == null) {
            throw new NullPointerException("Diploma File must be provided");
        }
        if (application.getGradeAuditFile() == null) {
            throw new NullPointerException("Grade Audit File must be provided");
        }
        if (application.getDesireProfessors() == null) {
            throw new NullPointerException("Desired Professors must be provided");
        }
        if (application.getStdFields() == null || application.getStdFields().isEmpty()) {
            throw new NullPointerException("Student Research Field must be provided");
        }
        return applicationRepository.save(application);
    }

    public HashMap<Long, String> getProfListByEventUID(Long eventUID) {
        HashMap<Long, String> returnedMap = new HashMap<>();
        for (Long profUID : profProfileRepository.findProUIDByEventUID(eventUID)) {
            returnedMap.put(profUID, userRepository.findNameById(profUID));
        }
        return returnedMap;
    }
}
