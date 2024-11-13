package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{
    public Admin() {}
}
