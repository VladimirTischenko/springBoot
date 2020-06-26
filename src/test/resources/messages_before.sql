delete from messages;

insert into messages(id, text, user_id) values
(1, 'text1', 1),
(2, 'text2', 1),
(3, 'сообщение1', 2),
(4, 'updated message', 2);

-- alter sequence hibernate_sequence restart with 10;