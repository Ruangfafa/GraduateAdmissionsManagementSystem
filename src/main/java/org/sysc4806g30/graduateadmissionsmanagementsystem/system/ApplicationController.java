package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApplicationController {

    @GetMapping("/admin/{userUID}/adminEvent/{eventUID}/adminApp/{applicationUID}")
    public String viewStudentApplication(@PathVariable Long applicationUID, @PathVariable Long userUID, @PathVariable Long eventUID, Model model) {
        return "adminApplication";
    }

}
