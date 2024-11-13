package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;


@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USERTYPE", discriminatorType = DiscriminatorType.STRING)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "USERTYPE")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "STD"),
        @JsonSubTypes.Type(value = Professor.class, name = "PROF"),
        @JsonSubTypes.Type(value = Administrator.class, name = "ADMIN")
})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USERUID")
    protected long userUID;
    @Column(name = "USERNAME")
    protected String userName;
    @Column(name = "USERPASSWORD")
    protected String userPassword;
    @Column(name = "EMAIL")
    protected String eMail;

    public Long getUserUID() {
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
    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"Ruangfafa\",\"userPassword\":\"332335\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"Rossa\",\"userPassword\":\"000000\",\"USERTYPE\":\"PROF\"}"