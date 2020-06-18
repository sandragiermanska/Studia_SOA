INSERT INTO user_entity(id, firstname, lastname, wantnewbooknotifications) VALUES (1, 'admin', 'admin', false);
INSERT INTO user_entity(id, firstname, lastname, wantnewbooknotifications) VALUES (2, 'Anna', 'Nowak', false);
INSERT INTO user_entity(id, firstname, lastname, wantnewbooknotifications) VALUES (3, 'Barbara', 'Ksiazek', true);
INSERT INTO user_entity(id, firstname, lastname, wantnewbooknotifications) VALUES (4, 'Cezary', 'Ksiazek', true);

INSERT INTO author_entity (id, firstname, lastname) VALUES (1, 'Agatha', 'Christie');
INSERT INTO author_entity (id, firstname, lastname) VALUES (2, 'Henryk', 'Sienkiewicz');
INSERT INTO author_entity (id, firstname, lastname) VALUES (3, 'William', 'Shakespeare');
INSERT INTO author_entity (id, firstname, lastname) VALUES (4, 'Joanne Kathleen', 'Rowling');

INSERT INTO category_entity (id, name) VALUES (1, 'kryminal');
INSERT INTO category_entity (id, name) VALUES (2, 'powiesc historyczna');
INSERT INTO category_entity (id, name) VALUES (3, 'fantastyka');
INSERT INTO category_entity (id, name) VALUES (4, 'literatura klasyczna');

INSERT INTO book_entity (id, title, authorid, isbn, categoryid, isborrowed) VALUES (1, 'Morderstwo w OrientExpressie', 1, '123456987', 1, false);
INSERT INTO book_entity (id, title, authorid, isbn, categoryid, isborrowed) VALUES (2, 'Morderstwo na polu golfowym', 1, '789987987', 1, false);
INSERT INTO book_entity (id, title, authorid, isbn, categoryid, isborrowed) VALUES (3, 'Romeo i Julia', 3, '654456987', 4, false);
INSERT INTO book_entity (id, title, authorid, isbn, categoryid, isborrowed) VALUES (4, 'Harry Potter i Kamien Filozoficzny', 4, '222333987', 3, false);
INSERT INTO book_entity (id, title, authorid, isbn, categoryid, isborrowed) VALUES (5, 'Krzyzacy', 2, '228333987', 2, false);