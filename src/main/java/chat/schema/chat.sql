CREATE database chat DEFAULT CHARACTER SET 'utf8';

use chat;

CREATE TABLE user(
  id bigint unsigned NOT NULL auto_increment,
  account VARCHAR(16) NOT NULL,
  password VARCHAR(32) NOT NULL,
  username VARCHAR(64) NOT NULL DEFAULT 'customer',
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE session(
  id bigint unsigned NOT NULL auto_increment,
  user_id bigint unsigned NOT NULL,
  to_user_id bigint unsigned NOT NULL,
  create_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL,
  unread int unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT session_fk_user FOREIGN KEY (user_id) REFERENCES user(id),
  CONSTRAINT session_fk_to_user FOREIGN KEY (to_user_id) REFERENCES user(id)
);

CREATE TABLE message(
  id bigint unsigned NOT NULL auto_increment,
  user_id bigint unsigned NOT NULL,
  to_user_id bigint unsigned NOT NULL,
  content VARCHAR(1024) NOT NULL,
  session_id bigint unsigned NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT msg_fk_user FOREIGN KEY (user_id) REFERENCES user(id),
  CONSTRAINT msg_fk_to_user FOREIGN KEY (user_id) REFERENCES user(id),
  CONSTRAINT msg_fk_session FOREIGN KEY (session_id) REFERENCES session(id)
);
