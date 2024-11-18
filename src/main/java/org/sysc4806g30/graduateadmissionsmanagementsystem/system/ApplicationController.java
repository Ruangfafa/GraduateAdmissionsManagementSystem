package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/student")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/{eventUID}/stdEvent/{stdUID}")
    public String getStudentApplicationPage(
            @PathVariable Long stdUID,
            @PathVariable Long eventUID,
            Model model){
        HashMap<Long, String> profList = applicationService.getProfListByEventUID(eventUID);
        model.addAttribute("profList", profList);
        return "studentApplication";
    }

    @PostMapping(value = "/{eventUID}/stdEvent/{stdUID}", consumes = "application/json")
    public String createApplication(
            @PathVariable Long stdUID,
            @PathVariable Long eventUID,
            @RequestBody Application application
    ){
        applicationService.saveApplication(application);
        return "studentApplication";
    }
}
