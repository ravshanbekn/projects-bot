CREATE TABLE IF NOT EXISTS projects
(
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name           VARCHAR(64) NOT NULL UNIQUE,
    parent_project BIGINT,

    CONSTRAINT fk_parent_project_id FOREIGN KEY (parent_project) REFERENCES projects (id) ON DELETE CASCADE
);

CREATE INDEX idx_projects_name ON projects (name);