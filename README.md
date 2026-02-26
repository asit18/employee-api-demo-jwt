# Employee API Demo (Spring Boot + Local JWT + DB permissions + HTTPS)

This demo mints **real JWTs** locally (HS256) and validates them using Spring Security Resource Server.

## Run
```bash
docker compose up --build
```

## HTTPS
Base URL: https://localhost:8443  
Self-signed TLS for demo -> use `curl -k`

## Get a JWT (demo token endpoint)
Roles minted:
- hr1 -> HR
- admin1 -> ADMIN
- anything else -> MANAGER

```bash
TOKEN_HR=$(curl -sk -X POST https://localhost:8443/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"hr1"}' \
  | python -c "import sys,json; print(json.load(sys.stdin)['access_token'])")
```

## Call the API (permission-based sensitive fields)
```bash
curl -sk -H "Authorization: Bearer $TOKEN_HR" \
  https://localhost:8443/api/v1/employees/1 | python -m json.tool
```

Expected:
- HR/ADMIN: salary present, SSN masked
- MANAGER: salary null, SSN null

## CI/CD (GitHub Actions)
`.github/workflows/ci.yml`
- Tests + JaCoCo coverage gate (80%)
- OWASP Dependency-Check (fails build for CVSS >= 7)
- Sonar (optional via secrets)
- Veracode upload/scan (optional via secrets)
