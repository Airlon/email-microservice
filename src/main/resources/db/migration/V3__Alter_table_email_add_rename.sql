ALTER TABLE email
ADD IF NOT EXISTS template_name VARCHAR(200);

ALTER TABLE email
ADD IF NOT EXISTS status VARCHAR(100);

ALTER TABLE email
RENAME template_version to template_id;