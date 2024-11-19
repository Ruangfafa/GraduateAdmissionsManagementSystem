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
        @JsonSubTypes.Type(value = Admin.class, name = "ADMIN")
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
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"s1\",\"userPassword\":\"1\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"s2\",\"userPassword\":\"2\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"s3\",\"userPassword\":\"3\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"s4\",\"userPassword\":\"4\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"s5\",\"userPassword\":\"5\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"s6\",\"userPassword\":\"6\",\"USERTYPE\":\"STD\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"p1\",\"userPassword\":\"1\",\"USERTYPE\":\"PROF\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"p2\",\"userPassword\":\"2\",\"USERTYPE\":\"PROF\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"p3\",\"userPassword\":\"3\",\"USERTYPE\":\"PROF\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"a1\",\"userPassword\":\"1\",\"USERTYPE\":\"ADMIN\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"
//curl.exe --% -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"userName\":\"a2\",\"userPassword\":\"2\",\"USERTYPE\":\"ADMIN\",\"eMail\":\"clksdysjsh0317@gmail.com\"}"