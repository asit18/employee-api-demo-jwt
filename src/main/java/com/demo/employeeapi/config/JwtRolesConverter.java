package com.demo.employeeapi.config;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private final String rolesClaimName;

  public JwtRolesConverter(String rolesClaimName) {
    this.rolesClaimName = rolesClaimName;
  }

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Object rolesObj = jwt.getClaims().getOrDefault(rolesClaimName, List.of());

    List<String> roles = (rolesObj instanceof List<?> l)
        ? l.stream().map(String::valueOf).toList()
        : List.of();

    Set<GrantedAuthority> authorities = roles.stream()
        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
        .collect(Collectors.toSet());

    return authorities;
  }
}
