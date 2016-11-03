insert into twitter.profile (id, first_name, last_name, email, phone) values (1, 'billy', 'bob', 'billybob@gmail.com', '3125486532');

insert into twitter.credentials (id, username, password) values (1, 'billybob', '1234');

insert into twitter.user (id, username, profile_id, credentials_id, active) values (1, 'billybob', 1, 1, true);