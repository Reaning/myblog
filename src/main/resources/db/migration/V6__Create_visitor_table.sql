create table visitor
(
    ip varchar(20),
    place varchar(50) null,
    datetime varchar(50) null,
    constraint VISITOR_pk
        primary key (ip)
);