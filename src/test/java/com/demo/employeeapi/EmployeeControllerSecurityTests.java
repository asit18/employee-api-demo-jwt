package com.demo.employeeapi;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeControllerSecurityTests {

  @Autowired MockMvc mvc;

  @Test
  void unauthorizedWithoutJwt() throws Exception {
    mvc.perform(get("/api/v1/employees/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void managerCanReadButNoSensitiveFields() throws Exception {
    mvc.perform(get("/api/v1/employees/1")
            .with(SecurityMockMvcRequestPostProcessors.jwt()
                .authorities(new SimpleGrantedAuthority("ROLE_MANAGER"))
                .jwt(jwt -> jwt.claim("roles", java.util.List.of("MANAGER"))))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.salary").value(Matchers.nullValue()))
        .andExpect(jsonPath("$.ssn").value(Matchers.nullValue()));
  }

  @Test
  void hrCanReadSensitiveFields() throws Exception {
    mvc.perform(get("/api/v1/employees/1")
            .with(SecurityMockMvcRequestPostProcessors.jwt()
                .authorities(new SimpleGrantedAuthority("ROLE_HR"))
                .jwt(jwt -> jwt.claim("roles", java.util.List.of("HR"))))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.salary").value(Matchers.notNullValue()))
        .andExpect(jsonPath("$.ssn").value(Matchers.notNullValue()));
  }
}
