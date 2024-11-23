package org.sysc4806g30.graduateadmissionsmanagementsystem.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findById(long id);
    @Query("SELECT u.userName FROM User u WHERE u.userUID = :userId")
    String findNameById(@Param("userId") long userId);
}
