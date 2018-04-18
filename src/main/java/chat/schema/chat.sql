CREATE database chat DEFAULT CHARACTER SET 'utf8';

use chat;

CREATE TABLE user(
  id bigint unsigned NOT NULL auto_increment,
  account VARCHAR(16) NOT NULL,
  password VARCHAR(32) NOT NULL,
  username VARCHAR(64) NOT NULL DEFAULT 'customer',
  PRIMARY KEY (id)
);

CREATE TABLE message(
  id bigint unsigned NOT NULL auto_increment,
  user_id bigint unsigned NOT NULL,
  to_user_id bigint unsigned NOT NULL,
  content VARCHAR(1024) NOT NULL,
  create_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT msg_fk_user FOREIGN KEY (user_id) REFERENCES user(id),
  CONSTRAINT msg_fk_to_user FOREIGN KEY (user_id) REFERENCES user(id),
)
