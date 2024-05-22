CREATE TABLE IF NOT EXISTS email
(
	id serial NOT NULL,
    recipient varchar(150) NOT NULL,
	subject varchar(200) NOT NULL,
	template_version varchar(256) NOT NULL,
	bag_id varchar(100) NOT NULL,
	username varchar(150) NOT NULL,
	processing_date timestamp NOT NULL,
    preview text NULL,
    stage varchar(50) NULL,
	CONSTRAINT email_pkey PRIMARY KEY (id)
);