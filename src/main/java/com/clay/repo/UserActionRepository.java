package com.clay.repo;

import com.clay.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserActionRepository extends JpaRepository<Action, Long> {

    @Query("select ordinal from Action where permission.id = ?1 and name in ?2")
    List<Integer> getOrdinalByPermissionAndNameIn(Long permissionId, List<String> actionNames);


    @Query("select name from Action where permission.id = ?1 and ordinal in ?2")
    List<String> getActionNamesByPermissionIdAndOrdinals(Long permissionId, List<Integer> ordinals);

    @Query
    List<String> findActionNameByPermissionId(Long permissionId);
}
