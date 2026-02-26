package com.demo.employeeapi.service;

import com.demo.employeeapi.repo.RoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

  private final RoleRepository repo;

  public PermissionService(RoleRepository repo) {
    this.repo = repo;
  }

  public boolean hasPermission(Authentication auth, String permission) {
    var roles = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .filter(a -> a.startsWith("ROLE_"))
        .map(a -> a.substring("ROLE_".length()))
        .toList();

    if (roles.isEmpty()) return false;
    return repo.anyRoleHasPermission(roles, permission);
  }
}
