package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    //method for prof to add studentID to ASSIGNEDSTDUIDLIST if prof is in DESPROF list
    public List<Long> addASSIGNEDSTDUIDLISTForProf(Long profUID){

        List<Long> assignedStdUidList = new ArrayList<>();

        List<Application> applicationsList = applicationRepository.findAll();

        for (Application a:applicationsList){
            List<String> stingOfDesPROFList = new ArrayList<>();

            stingOfDesPROFList = Arrays.asList(a.getDesProf().split(","));

            if(stingOfDesPROFList.contains(profUID.toString())){

                Long userID = a.getUserUID();

                assignedStdUidList.add(userID);

            }


        }

        //making sure no duplicate in the lift
        assignedStdUidList = new ArrayList<>(new HashSet<>(assignedStdUidList));

        return assignedStdUidList;

    }

}
