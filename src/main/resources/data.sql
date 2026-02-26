INSERT INTO permission (name) SELECT 'EMPLOYEE_READ' WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'EMPLOYEE_READ');
INSERT INTO permission (name) SELECT 'EMPLOYEE_SENSITIVE_READ' WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'EMPLOYEE_SENSITIVE_READ');

INSERT INTO role (name) SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ADMIN');
INSERT INTO role (name) SELECT 'HR' WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'HR');
INSERT INTO role (name) SELECT 'MANAGER' WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'MANAGER');

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id FROM role r, permission p
WHERE r.name = 'ADMIN' AND p.name IN ('EMPLOYEE_READ','EMPLOYEE_SENSITIVE_READ')
AND NOT EXISTS (SELECT 1 FROM role_permission rp WHERE rp.role_id = r.id AND rp.permission_id = p.id);

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id FROM role r, permission p
WHERE r.name = 'HR' AND p.name IN ('EMPLOYEE_READ','EMPLOYEE_SENSITIVE_READ')
AND NOT EXISTS (SELECT 1 FROM role_permission rp WHERE rp.role_id = r.id AND rp.permission_id = p.id);

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id FROM role r, permission p
WHERE r.name = 'MANAGER' AND p.name IN ('EMPLOYEE_READ')
AND NOT EXISTS (SELECT 1 FROM role_permission rp WHERE rp.role_id = r.id AND rp.permission_id = p.id);

INSERT INTO employee (first_name, last_name, email, title, department, manager_id, salary, ssn)
SELECT 'Ava', 'Patel', 'ava.patel@acme.com', 'Software Engineer', 'Engineering', 10, 165000.00, '123-45-6789'
WHERE NOT EXISTS (SELECT 1 FROM employee WHERE email = 'ava.patel@acme.com');

INSERT INTO employee (first_name, last_name, email, title, department, manager_id, salary, ssn)
SELECT 'Noah', 'Kim', 'noah.kim@acme.com', 'HR Generalist', 'HR', NULL, 98000.00, '987-65-4321'
WHERE NOT EXISTS (SELECT 1 FROM employee WHERE email = 'noah.kim@acme.com');

INSERT INTO employee (first_name, last_name, email, title, department, manager_id, salary, ssn)
SELECT 'Mia', 'Lopez', 'mia.lopez@acme.com', 'Product Manager', 'Product', 11, 145000.00, '111-22-3333'
WHERE NOT EXISTS (SELECT 1 FROM employee WHERE email = 'mia.lopez@acme.com');
