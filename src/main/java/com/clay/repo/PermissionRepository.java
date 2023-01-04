package com.clay.repo;

import com.clay.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p where p.user.id = ?1")
    List<Permission> findByUserId(Long userId);
}
