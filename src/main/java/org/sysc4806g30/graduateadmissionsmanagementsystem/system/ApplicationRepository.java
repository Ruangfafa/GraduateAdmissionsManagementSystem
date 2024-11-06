package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserUID(Long userUID); // 查找指定用户的应用记录
}