create schema if not exists user;
use user;
create table user (
                         id bigint not null auto_increment,
                         username varchar(50),
                         primary key (id)
);