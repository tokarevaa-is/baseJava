DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS section;
DROP TABLE IF EXISTS resume;
CREATE TABLE resume (
  uuid CHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT NOT NULL
);
CREATE TABLE contact (
  id SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type TEXT NOT NULL,
  value TEXT NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);
create table section
(
    id          serial
        constraint section_pk
            primary key,
    resume_uuid char(36) not null
        constraint section_resume_uuid_fkey
            references resume
            on delete cascade,
    type        text     not null,
    value       text     not null
);
create index section_resume_uuid_type_index
    on section (resume_uuid, type);