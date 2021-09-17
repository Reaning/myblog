create table article
(
    id bigint auto_increment,
    title varchar(50),
    sub_title varchar(50),
    description TEXT,
    gmt_create bigint,
    gmt_modified bigint,
    creator bigint,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256),
    constraint QUESTION_PK
        primary key (id)
);