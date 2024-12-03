package org.sysc4806g30.graduateadmissionsmanagementsystem.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
//import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.services.ApplicationService;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.model.Application;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.model.User;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/{userUID}/adminEvent/{eventUID}/adminApp/{applicationUID}")
    public String viewStudentApplication(
            @PathVariable Long applicationUID,
            @PathVariable Long userUID,
            @PathVariable Long eventUID,
            Model model) {
        Application application = applicationService.getApplicationByApplicationID(applicationUID);
        String desiredProf = application.getDesireProfessors();
        ArrayList<String> profNames = new ArrayList<>();
        for (String pUID: desiredProf.split(",")){
            User prof = userRepository.findById(Long.parseLong(pUID));
            if (prof != null){
                profNames.add(prof.getUserName());
            }
        }
        model.addAttribute("desiredP", profNames.toString().substring(1, profNames.toString().length() - 1));
        model.addAttribute("researchF", application.getStdFields());
        return "adminApplication";
    }

    @PostMapping("/admin/{userUID}/adminEvent/{eventUID}/adminApp/{applicationUID}")
    @ResponseBody
    public String viewStudentApplication(
            @PathVariable Long applicationUID,
            @PathVariable Long userUID,
            @PathVariable Long eventUID,
            @RequestBody String fileType) {
        Application application = applicationService.getApplicationByApplicationID(applicationUID);
        return application.getTargetFileEncode(fileType);
    }

    @GetMapping("/admin/{userUID}/adminEvent/{eventID}/adminApp/{applicationUID}/api")
    @ResponseBody
    public String redirectToApplication(
            @PathVariable Long userUID,
            @PathVariable Long eventID,
            @PathVariable Long applicationUID) {
            return "/admin/" + userUID + "/adminEvent/" + eventID + "/adminApp/" + applicationUID;
    }

    @GetMapping("/student/{stdUID}/stdEvent/{eventUID}")
    public String getStudentApplicationPage(
            @PathVariable Long stdUID,
            @PathVariable Long eventUID,
            Model model){
        HashMap<Long, String> profList = applicationService.getProfListByEventUID(eventUID);
        model.addAttribute("profList", profList);
        return "studentApplication";
    }

    @PostMapping(value = "/student/{stdUID}/stdEvent/{eventUID}", consumes = "application/json")
    public String createApplication(
            @PathVariable Long stdUID,
            @PathVariable Long eventUID,
            @RequestBody Application application
    ){
//        System.out.println("cv 64: " + application.getCv64());
//        System.out.println("diploma 64: " + application.getDp64());
//        System.out.println("grade 64: " + application.getGd64());

        application.updateFileData();
//        System.out.println("cv after decode: " + application.getCoverLetterFile());
//        System.out.println("diploma after decode: " + application.getDiplomaFile());
//        System.out.println("grade after decode: " + application.getGradeAuditFile());
        applicationService.saveApplication(application);
        return "studentApplication";
    }

    @GetMapping("/professor/{userUID}/profEvent/{eventID}/api/editPage")
    @ResponseBody
    public String redirectToEditPage(
            @PathVariable Long userUID,
            @PathVariable Long eventID) {
        return "/professor/" + userUID + "/profEvent/" + eventID + "/page";
    }
}
