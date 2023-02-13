create schema if not exists company;
use company;
create table company (
                          id bigint not null auto_increment,
                          name varchar(50),
                          primary key (id)
);