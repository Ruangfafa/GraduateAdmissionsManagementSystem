package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/admin/{userUID}/adminEvent/{eventUID}/adminApp/{applicationUID}")
    public String viewStudentApplication(@PathVariable Long applicationUID, @PathVariable Long userUID, @PathVariable Long eventUID, Model model) {
        return "adminApplication";
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
        applicationService.saveApplication(application);
        return "studentApplication";
    }
}
