package org.sysc4806g30.graduateadmissionsmanagementsystem.system.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.ProfProfile;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.ProfProfileRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.Application;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.repositories.ApplicationRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.repositories.UserRepository;

import java.util.HashMap;
import java.util.*;

@Service
@Transactional
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ProfProfileRepository profProfileRepository;
    @Autowired
    private UserRepository userRepository;

    // Existing method for fetching assigned students for a professor
    public List<Long> addASSIGNEDSTDUIDLISTForProf(Long profUID) {
        List<Long> assignedStdUidList = new ArrayList<>();
        List<Application> applicationsList = applicationRepository.findAll();

        for (Application a : applicationsList) {
            if (a.getDesireProfessors() == null || a.getDesireProfessors().isEmpty()) {
                continue;
            }

            List<String> desProfList = Arrays.asList(a.getDesireProfessors().split(","));
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
        ProfProfile profProfile = profProfileRepository.findByProfUIDAndEventUID(profUID,eventUID);

        String tempStdList = profProfile.getAssignedstduidlist();
        String[] stdIdArray = tempStdList.split(",");
        for (String id : stdIdArray) {
            assignedStudents.add(Long.parseLong(id));
        }

//        for (Application a : applicationsList) {
//            // Check if the professor is in the desired professors list
//            if (a.getDesireProfessors() != null && a.getDesireProfessors().contains(profUID.toString())
//                    && a.getEventUID() != null && a.getEventUID().equals(eventUID)) {
//                assignedStudents.add(a.getUserUID());
//            }
//        }

        // Remove duplicates
        return new ArrayList<>(new HashSet<>(assignedStudents));
    }

    // New method to update profComment by userUID
    @Transactional
    public void updateProfCommentByUserUID(Long userUID, Long profUID, Integer profComment) {
        List<Application> applications = applicationRepository.findByUserUID(userUID);

        for (Application application : applications) {
            Long applicationUID = application.getApplicationUID();
            String existingComments = application.getProfcomment();

            // Parse existing comments into a map
            Map<Long, Integer> commentsMap = new HashMap<>();
            if (existingComments != null && !existingComments.isEmpty()) {
                String[] entries = existingComments.split(";");
                for (String entry : entries) {
                    String[] parts = entry.split(",");
                    Long existingProfID = Long.parseLong(parts[0]);
                    Integer existingComment = Integer.parseInt(parts[1]);
                    commentsMap.put(existingProfID, existingComment);
                }
            }

            // Update or add the new comment
            commentsMap.put(profUID, profComment);

            // Convert map back to the desired string format
            StringBuilder updatedComments = new StringBuilder();
            for (Map.Entry<Long, Integer> entry : commentsMap.entrySet()) {
                updatedComments.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
            }

            // Save the updated profComment string
            applicationRepository.updateProfComment(applicationUID, updatedComments.toString());
        }
    }


    public void saveApplication(Application application){
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

        if(applicationRepository.getApplicationByStdUIDAndEventUID(application.getUserUID(), application.getEventUID()) == null){
            applicationRepository.save(application);
        }else {
            applicationRepository.overWriteApplication(application.getUserUID(), application.getEventUID(),
                    application.getCoverLetterFile(), application.getDiplomaFile(), application.getGradeAuditFile(),
                    application.getDesireProfessors(), application.getStdFields());
        }
    }

    public HashMap<Long, String> getProfListByEventUID(Long eventUID) {
        HashMap<Long, String> returnedMap = new HashMap<>();
        for (Long profUID : profProfileRepository.findProUIDByEventUID(eventUID)) {
            returnedMap.put(profUID, userRepository.findNameById(profUID));
        }
        return returnedMap;
    }

    public List<Application> getApplicationsByEventUID(Long eventUID) {
        return applicationRepository.findByEventUID(eventUID);
    }

    public Application getApplicationByApplicationID(Long applicationID) {
        return applicationRepository.getApplicationByApplicationUID(applicationID);
    }
}
