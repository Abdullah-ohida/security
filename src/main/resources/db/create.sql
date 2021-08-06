
create database if not exists security;

-- drop user if exists 'blogger'@'localhost'
create user if not exists 'security'@'localhost' identified by '#Security123';
grant all privileges  on security.* to 'security'@'localhost';
flush privileges;