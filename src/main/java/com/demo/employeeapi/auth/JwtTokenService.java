package com.demo.employeeapi.auth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenService {

  private final byte[] secret;

  public JwtTokenService(@Value("${demo.jwt.secret}") String secret) {
    this.secret = secret.getBytes();
  }

  public String mint(String username, List<String> roles) {
    Instant now = Instant.now();

    JWTClaimsSet claims = new JWTClaimsSet.Builder()
        .subject(username)
        .issuer("demo-local")
        .issueTime(Date.from(now))
        .expirationTime(Date.from(now.plusSeconds(3600)))
        .claim("roles", roles)
        .build();

    SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);

    try {
      jwt.sign(new MACSigner(secret));
      return jwt.serialize();
    } catch (Exception e) {
      throw new RuntimeException("Failed to sign JWT", e);
    }
  }
}
