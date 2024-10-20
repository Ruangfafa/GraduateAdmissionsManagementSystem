package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "users")  // Changed table name to "users"
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "user_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "student"),
        @JsonSubTypes.Type(value = Professor.class, name = "professor"),
        @JsonSubTypes.Type(value = Administrator.class, name = "administrator")
})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long userUID;
    protected String userName,userPassword;

    public long getUserUID() {
        return userUID;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"Ruangfafa\",\"userPassword\":\"332335\",\"user_type\":\"student\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"Rossa\",\"userPassword\":\"000000\",\"user_type\":\"professor\"}"