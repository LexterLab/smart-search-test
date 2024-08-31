CREATE TABLE movies
(
    id    UUID         NOT NULL,
    name  VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    CONSTRAINT pk_movies PRIMARY KEY (id)
);

ALTER TABLE movies
    ADD CONSTRAINT uc_movies_name UNIQUE (name);

INSERT INTO movies (id, name, genre)
VALUES ('dca392f0-b89a-490e-ba57-d2dfc0cd0900', 'Hogwarts Mysteries', 'Mystery');

INSERT INTO movies (id, name, genre)
VALUES ('14951104-0dd0-4327-928c-db056f93aa66', 'Galactic Adventures', 'Science Fiction');

INSERT INTO movies (id, name, genre)
VALUES ('d360d3d5-c67d-42a5-846d-91b6f4cbf5fd', 'Lost in the Woods', 'Thriller');

INSERT INTO movies (id, name, genre)
VALUES ('6dbd055d-6412-4183-b715-4c5c5c3d0052', 'City Lights', 'Romance');

INSERT INTO movies (id, name, genre)
VALUES ('eb58389c-3951-40a6-824d-372af727308d', 'Pirate Legends', 'Adventure');

INSERT INTO movies (id, name, genre)
VALUES ('92c85345-7915-4911-b89f-76ad8fcbd56a', 'Culinary Dreams', 'Drama');

INSERT INTO movies (id, name, genre)
VALUES ('7e87b2f3-d518-48b4-ae6d-5adb22c45cdb', 'Virtual Reality', 'Science Fiction');

INSERT INTO movies (id, name, genre)
VALUES ('ec9d7476-b0fa-47f2-aa9c-854e49a4fc01', 'Shadow Hunters', 'Horror');

INSERT INTO movies (id, name, genre)
VALUES ('df9d6da0-1b0a-4f3a-9c36-b7228d57bdb6', 'The Time Traveler', 'Fantasy');

INSERT INTO movies (id, name, genre)
VALUES ('473593c7-a07a-4dfa-b711-7e2c0ddea764', 'Whispers in the Dark', 'Mystery');
