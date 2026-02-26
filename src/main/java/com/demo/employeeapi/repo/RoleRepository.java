package com.demo.employeeapi.repo;

import com.demo.employeeapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("""
        select count(p) > 0
        from Role r
        join r.permissions p
        where r.name in :roleNames
          and p.name = :permission
    """)
  boolean anyRoleHasPermission(@Param("roleNames") Collection<String> roleNames,
                               @Param("permission") String permission);
}
