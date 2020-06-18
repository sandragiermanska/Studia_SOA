create table user_entity(id int not null primary key, first_name varchar(50), age int, avatar bytea);
create table movie_entity(id int not null primary key, title varchar(255), uri varchar(255));

create table user_movie(user_id int not null, movie_id int not null, primary key (user_id, movie_id), constraint user_id_conn foreign key (user_id) references user_entity, constraint movie_id_conn foreign key (movie_id) references movie_entity);

create sequence user_seq increment 1 start 100;
create sequence movie_seq increment 1 start 100;

