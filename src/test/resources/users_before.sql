delete from roles;
delete from users;

insert into users(id, email, enabled, name, password) values
(1, 'vladimirtischenko82@gmail.com', true, 'admin', '$2a$08$SbYJTe5AsaGiKXgUmCYzTuJZFaF2s2MXLYjuNQabMqbQBbb.m9mgq'),
(2, 'tegij39716@seberkd.com', true, 'Владимир', '$2a$08$9U308hvwxiMvpG2wc9IPmOHwwynCY7f0eLZ1t/taY.jSB6RY5jt2i');

insert into roles(user_id, roles) values
(1, 'USER'),
(1, 'ADMIN'),
(2, 'USER');
