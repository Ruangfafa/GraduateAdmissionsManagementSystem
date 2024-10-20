package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {
    public Student() {}
}
