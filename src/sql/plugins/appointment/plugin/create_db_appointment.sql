--
-- Structure for table appointment_appointment
--
DROP TABLE IF EXISTS appointment_appointment;
CREATE TABLE appointment_appointment (
	id_appointment int(11) NOT NULL default '0',
	first_name varchar(255) NOT NULL default '',
	last_name varchar(255) NOT NULL default '',
	email varchar(255) NOT NULL default '',
	id_user varchar(255) NULL default '',
	authentication_service varchar(255) NULL default '',
	date_appointment DATE NOT NULL,
	id_slot int(11) NOT NULL,
	status smallint NOT NULL,
	id_action_cancel int(11) NOT NULL,
	PRIMARY KEY (id_appointment)
);
CREATE INDEX idx_appointment_id_slot ON appointment_appointment (id_slot);
--
-- Structure for table appointment_form
--
DROP TABLE IF EXISTS appointment_form;
CREATE TABLE appointment_form (
	id_form int(11) NOT NULL,
	title varchar(255) NOT NULL default '',
	description long varchar NOT NULL,
	time_start varchar(10) NOT NULL default '0',
	time_end varchar(10) NOT NULL default '0',
	duration_appointments INT(11) NOT NULL default '0',
	is_open_monday SMALLINT NOT NULL,
	is_open_tuesday SMALLINT NOT NULL,
	is_open_wednesday SMALLINT NOT NULL,
	is_open_thursday SMALLINT NOT NULL,
	is_open_friday SMALLINT NOT NULL,
	is_open_saturday SMALLINT NOT NULL,
	is_open_sunday SMALLINT NOT NULL,
	date_start_validity date NULL,
	date_end_validity date NULL,
	is_active SMALLINT NOT NULL,
	dispolay_title_fo SMALLINT NOT NULL,
	nb_weeks_to_display INT(11) NOT NULL default '0',
	people_per_appointment INT(11) NOT NULL default '0',
	id_workflow INT(11) NOT NULL default '0',
	is_captcha_enabled SMALLINT NOT NULL,
	users_can_cancel_appointments SMALLINT NOT NULL,
	PRIMARY KEY (id_form)
);


DROP TABLE IF EXISTS appointment_day;
CREATE TABLE appointment_day (
	id_day INT(11) NOT NULL,
	id_form INT(11) NOT NULL,
	is_open SMALLINT NOT NULL,
	date_day DATE NOT NULL,
	opening_hour INT(11) NOT NULL,
	opening_minute INT(11) NOT NULL,
	closing_hour INT(11) NOT NULL,
	closing_minute INT(11) NOT NULL,
	appointment_duration INT(11) NOT NULL,
	people_per_appointment INT(11) NOT NULL,
	PRIMARY KEY (id_day)
);
CREATE INDEX idx_appointment_day_id_form ON appointment_day (id_form);

DROP TABLE IF EXISTS appointment_slot;
CREATE TABLE appointment_slot (
	id_slot INT(11) NOT NULL,
	id_form INT(11) NOT NULL,
	id_day INT(11) NOT NULL,
	day_of_week INT(11) NOT NULL,
	nb_places INT(11) NOT NULL,
	starting_hour INT(11) NOT NULL,
	starting_minute INT(11) NOT NULL,
	ending_hour INT(11) NOT NULL,
	ending_minute INT(11) NOT NULL,
	is_enabled SMALLINT NOT NULL,
	PRIMARY KEY (id_slot)
);
CREATE INDEX idx_appointment_slot_id_form ON appointment_slot (id_form);
CREATE INDEX idx_appointment_slot_id_day ON appointment_slot (id_day);

DROP TABLE IF EXISTS appointment_appointment_response;
CREATE TABLE appointment_appointment_response (
	id_appointment INT(11) NOT NULL,
	id_response INT(11) NOT NULL,
	PRIMARY KEY (id_appointment,id_response)
);

DROP TABLE IF EXISTS appointment_form_messages;
CREATE TABLE appointment_form_messages (
	id_form INT(11) NOT NULL,
	calendar_title varchar(255) NOT NULL default '',
	field_firstname_title varchar(255) NOT NULL default '',
	field_firstname_help varchar(255) NOT NULL default '',
	field_lastname_title varchar(255) NOT NULL default '',
	field_lastname_help varchar(255) NOT NULL default '',
	field_email_title varchar(255) NOT NULL default '',
	field_email_help varchar(255) NOT NULL default '',
	text_appointment_created long varchar NOT NULL,
	url_redirect_after_creation varchar(255) NOT NULL default '',
	text_appointment_canceled long varchar NOT NULL,
	label_button_redirection varchar(255) NOT NULL default '',
	PRIMARY KEY (id_form)
);