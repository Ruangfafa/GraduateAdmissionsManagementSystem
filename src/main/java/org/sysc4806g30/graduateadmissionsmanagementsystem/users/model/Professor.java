package org.sysc4806g30.graduateadmissionsmanagementsystem.users.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Professor")
public class Professor extends User {
    public Professor() {}
}
