insert into user_entity(id, first_name, age, avatar) values (1, 'Sandra', 22, null);
insert into user_entity(id, first_name, age, avatar) values (2, 'Antek', 23, null);
insert into user_entity(id, first_name, age, avatar) values (3, 'Bartek', 40, null);

insert into movie_entity(id, title, uri) values (1, 'Harry Potter i Kamien Filozoficzny', 'google.com');
insert into movie_entity(id, title, uri) values (2, 'Titanic', 'google.com');
insert into movie_entity(id, title, uri) values (3, 'Avatar', 'google.com');
insert into movie_entity(id, title, uri) values (4, 'Duze dzieci', 'google.com');

insert into user_movie(user_id, movie_id) values (1, 4);
insert into user_movie(user_id, movie_id) values (1, 1);
