create table author_entity (id int not null primary key,firstName varchar(255) not null, lastName varchar(255) not null);
create table user_entity (id int not null primary key,firstName varchar(255) not null, lastName varchar(255) not null, wantNewBookNotifications boolean not null);
create table category_entity (id int not null primary key,name varchar(255) not null);
create table book_entity (id int not null primary key,title varchar(255) not null, authorId int not null, isbn varchar(255), categoryId int,isBorrowed boolean not null, constraint book_authorId foreign key (authorId) references author_entity, constraint book_categoryId foreign key (categoryId) references category_entity);
create table borrow_entity (id int not null primary key,borrowDate timestamp, returnDate timestamp, bookId int not null, readerId int not null, constraint borrow_readerId foreign key (readerId) references user_entity, constraint borrow_bookId foreign key (bookId) references book_entity);
create table follows_entity (followerId int, bookId int,primary key (bookId, followerId),constraint follows_followerId foreign key (followerId) references user_entity,constraint follows_bookId foreign key (bookId) references book_entity);
create table notification_entity (id int not null primary key,text varchar(255),userId int not null ,constraint notifications_userId foreign key (userId) references user_entity);
