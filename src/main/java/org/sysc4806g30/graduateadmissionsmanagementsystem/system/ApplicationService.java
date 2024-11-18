package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    // Existing method for fetching assigned students for a professor
    public List<Long> addASSIGNEDSTDUIDLISTForProf(Long profUID) {
        List<Long> assignedStdUidList = new ArrayList<>();
        List<Application> applicationsList = applicationRepository.findAll();

        for (Application a : applicationsList) {
            if (a.getDesProf() == null || a.getDesProf().isEmpty()) {
                continue;
            }

            List<String> desProfList = Arrays.asList(a.getDesProf().split(","));
            if (desProfList.contains(profUID.toString())) {
                assignedStdUidList.add(a.getUserUID());
            }
        }

        // Remove duplicates
        return new ArrayList<>(new HashSet<>(assignedStdUidList));
    }

    // New method to get assigned students for a professor and a specific event
    public List<Long> getAssignedStudentsForEvent(Long profUID, Long eventUID) {
        List<Long> assignedStudents = new ArrayList<>();
        List<Application> applicationsList = applicationRepository.findAll();

        for (Application a : applicationsList) {
            // Check if the professor is in the desired professors list
            if (a.getDesProf() != null && a.getDesProf().contains(profUID.toString())
                    && a.getEventUID() != null && a.getEventUID().equals(eventUID)) {
                assignedStudents.add(a.getUserUID());
            }
        }

        // Remove duplicates
        return new ArrayList<>(new HashSet<>(assignedStudents));
    }

    // New method to update profComment by userUID
    @Transactional
    public void updateProfCommentByUserUID(Long userUID, Integer profComment) {
        List<Application> applications = applicationRepository.findByUserUID(userUID);

        for (Application application : applications) {
            Long applicationUID = application.getApplicationUID();
            System.out.println("Updating profComment for userUID: " + userUID + " with value: " + profComment);
            applicationRepository.updateProfComment(applicationUID, String.valueOf(profComment)); // Save as a numeric value
        }
    }
}
