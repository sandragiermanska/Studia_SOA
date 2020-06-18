create table if not exists user_entity (id int not null primary key,login varchar(255) unique not null);

create table if not exists thematicList_entity (id int not null primary key,name varchar(255) not null,ownerId int not null,constraint thematicList_ownerId foreign key (ownerId) references user_entity);

create table if not exists comment_entity (id int not null primary key,message varchar(255),authorId int,thematicListId int,toUserId int,constraint comments_authorId foreign key (authorId) references user_entity,constraint comments_thematicListId foreign key (thematicListId) references thematicList_entity,constraint comments_toUserId foreign key (toUserId) references user_entity);

create table if not exists subscribe_entity (subscriberId int,thematicListId int,primary key (thematicListId, subscriberId),constraint subscribes_subscriberId foreign key (subscriberId) references user_entity,constraint subscribes_thematicListId foreign key (thematicListId) references thematicList_entity);

create table if not exists notification_entity (id int not null primary key,text varchar(255),userId int,constraint notifications_userId foreign key (userId) references user_entity);
