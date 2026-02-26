create table if not exists permission (
  id bigserial primary key,
  name varchar(100) not null unique
);

create table if not exists role (
  id bigserial primary key,
  name varchar(100) not null unique
);

create table if not exists role_permission (
  role_id bigint not null references role(id) on delete cascade,
  permission_id bigint not null references permission(id) on delete cascade,
  primary key (role_id, permission_id)
);

create table if not exists employee (
  id bigserial primary key,
  first_name varchar(100) not null,
  last_name varchar(100) not null,
  email varchar(200) not null unique,
  title varchar(150),
  department varchar(150),
  manager_id bigint,
  salary numeric(19,2),
  ssn varchar(16),
  updated_at timestamp with time zone default now()
);

create index if not exists idx_employee_department on employee(department);
create index if not exists idx_employee_manager_id on employee(manager_id);
