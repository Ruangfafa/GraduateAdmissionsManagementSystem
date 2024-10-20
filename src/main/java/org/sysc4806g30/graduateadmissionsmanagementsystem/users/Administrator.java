package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Administrator")
public class Administrator extends User{
    public Administrator() {}
}
