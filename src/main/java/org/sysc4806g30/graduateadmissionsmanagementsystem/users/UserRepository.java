package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
