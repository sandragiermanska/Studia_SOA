INSERT INTO user_entity(id, login) VALUES (1, 'annanowak');
INSERT INTO user_entity(id, login) VALUES (2, 'anonim');
INSERT INTO user_entity(id, login) VALUES (3, 'zbyszek11');

INSERT INTO thematiclist_entity(id, name, ownerid) VALUES (1, 'kwiaty doniczkowe', 1);
INSERT INTO thematiclist_entity(id, name, ownerid) VALUES (2, 'ksiazki', 2);

INSERT INTO comment_entity(id, message, authorid, thematiclistid, touserid) VALUES (1, 'Gdzie powinnam postawic kwiaty doniczkowe?', 1, 1, null);
INSERT INTO comment_entity(id, message, authorid, thematiclistid, touserid) VALUES (2, 'Kwiaty pownny stac w dobrze naslonecznionym miejscu', 2, 1, null);
INSERT INTO comment_entity(id, message, authorid, thematiclistid, touserid) VALUES (3, 'A jak czesto podlewac?', 1, 1, null);
INSERT INTO comment_entity(id, message, authorid, thematiclistid, touserid) VALUES (4, 'To zalezy od kwiatow', 2, 1, 1);
INSERT INTO comment_entity(id, message, authorid, thematiclistid, touserid) VALUES (5, 'Jakie ksiazki polecacie?', 2, 2, null);
INSERT INTO comment_entity(id, message, authorid, thematiclistid, touserid) VALUES (6, 'Ja to lubie Hobbita Tolkiena', 3, 2, null);

INSERT INTO subscribe_entity(subscriberid, thematiclistid) VALUES (1, 1);
INSERT INTO subscribe_entity(subscriberid, thematiclistid) VALUES (2, 2);
INSERT INTO subscribe_entity(subscriberid, thematiclistid) VALUES (2, 1);
INSERT INTO subscribe_entity(subscriberid, thematiclistid) VALUES (3, 2);