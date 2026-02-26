package com.demo.employeeapi.auth;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtTokenService tokenService;

  public AuthController(JwtTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @PostMapping("/token")
  public Map<String, String> token(@RequestBody Map<String, String> body) {
    String user = body.getOrDefault("username", "mgr1");

    List<String> roles = switch (user) {
      case "hr1" -> List.of("HR");
      case "admin1" -> List.of("ADMIN");

      default -> List.of("MANAGER");
    };

    return Map.of("access_token", tokenService.mint(user, roles));
  }
}
