INSERT INTO CATEGORY(id, name) VALUES (1, 'Eurogames');
INSERT INTO CATEGORY(id, name) VALUES (2, 'Ameritrash');
INSERT INTO CATEGORY(id, name) VALUES (3, 'Familiar');

INSERT INTO AUTHOR(id, name, nationality) VALUES (1, 'Alan R. Moon', 'US');
INSERT INTO AUTHOR(id, name, nationality) VALUES (2, 'Vital Lacerda', 'PT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (3, 'Simone Luciani', 'IT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (4, 'Perepau Llistosella', 'ES');
INSERT INTO AUTHOR(id, name, nationality) VALUES (5, 'Michael Kiesling', 'DE');
INSERT INTO AUTHOR(id, name, nationality) VALUES (6, 'Phil Walker-Harding', 'US');

INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (1, 'On Mars', '14', 1, 2);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (2, 'Aventureros al tren', '8', 3, 1);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (3, '1920: Wall Street', '12', 1, 4);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (4, 'Barrage', '14', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (5, 'Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (6, 'Azul', '8', 3, 5);

INSERT INTO CLIENT(id, name) VALUES (1, 'Eric');
INSERT INTO CLIENT(id, name) VALUES (2, 'Pau');
INSERT INTO CLIENT(id, name) VALUES (3, 'Forniu');
INSERT INTO CLIENT(id, name) VALUES (4, 'Gonzalo');
INSERT INTO CLIENT(id, name) VALUES (5, 'Pablo');
INSERT INTO CLIENT(id, name) VALUES (6, 'Barxino');
INSERT INTO CLIENT(id, name) VALUES (7, 'Jorro');
INSERT INTO CLIENT(id, name) VALUES (8, 'Pupi');
INSERT INTO CLIENT(id, name) VALUES (9, 'Pauper');
INSERT INTO CLIENT(id, name) VALUES (10, 'Joampo');
INSERT INTO CLIENT(id, name) VALUES (11, 'Isaac');


INSERT INTO LOAN(id, client_name, game_name, initial_date, final_date) VALUES (1, 'Eric','On Mars', '2023-01-17', '2023-02-01');
INSERT INTO LOAN(id, client_name, game_name, initial_date, final_date) VALUES (2, 'Pau','Aventureros al tren', '2022-12-13', '2022-12-23');
INSERT INTO LOAN(id, client_name, game_name, initial_date, final_date) VALUES (3, 'Jordi','Aventureros al tren', '2022-11-17', '2022-11-23');
INSERT INTO LOAN(id, client_name, game_name, initial_date, final_date) VALUES (4, 'Gonzalo','On Mars', '2022-01-17', '2022-02-01');
INSERT INTO LOAN(id, client_name, game_name, initial_date, final_date) VALUES (5, 'Pablo','Azul', '2023-01-17', '2023-02-01');
INSERT INTO LOAN(id, client_name, game_name, initial_date, final_date) VALUES (6, 'Eric','Aventureros al tren', '2023-01-17', '2023-02-01');