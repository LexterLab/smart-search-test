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
VALUES ('dca392f0-b89a-490e-ba57-d2dfc0cd0900', 'Hogwarts Mysteries', 'Mystery')

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
VALUES ('4d5e6f7a-8b9c-0d1e-2f3a-4b5c6d7f8g9h', 'Virtual Reality', 'Science Fiction');

INSERT INTO movies (id, name, genre)
VALUES ('5f6g7h8i-9j0k-1l2m-3n4o-5p6q7r8s9t0u', 'Shadow Hunters', 'Horror');

INSERT INTO movies (id, name, genre)
VALUES ('6h7i8j9k-0l1m-2n3o-4p5q-6r7s8t9u0v1w', 'The Time Traveler', 'Fantasy');

INSERT INTO movies (id, name, genre)
VALUES ('7i8j9k0l-1m2n-3o4p-5q6r-7s8t9u0v1w2x', 'Whispers in the Dark', 'Mystery');
