package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.sysc4806g30.graduateadmissionsmanagementsystem.system.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ApplicationService applicationService;

    // Endpoint to retrieve assigned students for a professor by their profUID
    @GetMapping("/{profUID}/assigned-students")
    public List<Long> getAssignedStudents(@PathVariable  Long profUID) {
        return applicationService.addASSIGNEDSTDUIDLISTForProf(profUID);
    }

}