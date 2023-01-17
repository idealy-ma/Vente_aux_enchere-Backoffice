CREATE TABLE marque(
    id SERIAL PRIMARY KEY,
    label VARCHAR(100)
);

CREATE TABLE vehicule(
    id SERIAL PRIMARY KEY,
    matricule VARCHAR(50) UNIQUE,
    idMarque INTEGER REFERENCES marque(id)
);

INSERT INTO marque VALUES (DEFAULT, 'Volvo');
INSERT INTO marque VALUES (DEFAULT, 'Volswagen');

INSERT INTO vehicule VALUES (DEFAULT, '00000', 1);