insert into twitter.profile (id, first_name, last_name, email, phone) values (1, 'billy', 'bob', 'billybob@gmail.com', '3125486532');
insert into twitter.credentials (id, username, password) values (1, '@billybob', '1234');
insert into twitter.user (id, username, profile_id, credentials_id, active) values (1, '@billybob', 1, 1, false);

insert into twitter.profile (id, first_name, last_name, email, phone) values (2, 'mike', 'smith', 'mikesmith@gmail.com', '3128496320');
insert into twitter.credentials (id, username, password) values (2, '@mikesmith', '1234');
insert into twitter.user (id, username, profile_id, credentials_id, active) values (2, '@mikesmith', 2, 2, true);

insert into twitter.profile (id, first_name, last_name, email, phone) values (3, 'tommy', 'white', 'tommywhite@gmail.com', '3124503548');
insert into twitter.credentials (id, username, password) values (3, '@tommywhite', '1234');
insert into twitter.user (id, username, profile_id, credentials_id, active) values (3, '@tommywhite', 3, 3, true);

insert into twitter.tweet (id, active, content, posted, user_id) values (1, true, 'Hello', '31-08-1982 10:20:56', 1);
insert into twitter.tweet (id, active, content, posted, user_id, in_reply_to_id) values (2, true, 'CUBS WIN', '31-08-1983 10:20:56', 3, 1);

insert into twitter.tag (id, first_used, label, last_used) values (1, '02-11-2016 11:45:56', 'CUBS', '03-11-2016 02:31:16');

insert into twitter.tags_tweets (tweet_id, tag_id) values (2, 1);