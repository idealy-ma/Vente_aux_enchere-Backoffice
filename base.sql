Create database enchere;

CREATE TABLE Admin (
  idAdmin SERIAL NOT NULL, 
  email   varchar(75) NOT NULL, 
  mdp     varchar(75) NOT NULL, 
  PRIMARY KEY (idAdmin)
  );
  insert into Admin(email,mdp) values ('cedi','0000');

CREATE TABLE Categorie (
  idCategorie  SERIAL NOT NULL, 
  nomCategorie varchar(75) NOT NULL, 
  PRIMARY KEY (idCategorie)
  );
  insert into Categorie(nomCategorie) values ('Pieces autos');
  insert into Categorie(nomCategorie) values ('Bijoux');
  insert into Categorie(nomCategorie) values ('Mobiliers de bureaux');

CREATE TABLE Client (
  idClient SERIAL NOT NULL, 
  nom      varchar(75), 
  prenom   varchar(75) NOT NULL, 
  email    varchar(75) NOT NULL, 
  mdp      varchar(75) NOT NULL, 
  soldeClient double precision default 0,
  PRIMARY KEY (idClient)
  );
INSERT INTO Client(nom,prenom,email,mdp) values ('Falimanantsoa','Ando','ando@gmail.com','andolove');
INSERT INTO Client(nom,prenom,email,mdp) values ('Ramarojaona','Miantsa','miantsa@gmail.com','miantsalove');
INSERT INTO Client(nom,prenom,email,mdp) values ('Rabenanahary','Seta','seta@gmail.com','setalove');

CREATE TABLE Commission (
  idCommission SERIAL NOT NULL, 
  pourcentage  int4, 
  PRIMARY KEY (idCommission)
  );

CREATE TABLE Enchere (
  idEnchere            SERIAL NOT NULL, 
  nomProduit           varchar(255) NOT NULL, 
  dateDebut            timestamp default NULL, 
  dateFin              timestamp default NULL, 
  prixMin              float8 NOT NULL, 
  description          varchar(255) NOT NULL, 
  idCategorie int4 NOT NULL, 
  idClient       int4 NOT NULL, 
  PRIMARY KEY (idEnchere),
  Foreign key(idCategorie) REFERENCES Categorie(idCategorie),
  Foreign key(idClient) REFERENCES Client(idClient)
  );
insert into Enchere(idClient,idCategorie,nomProduit,dateDebut,dateFin,prixMin,description) values ('1','2','Collier','16-01-2023 06:00:00','20-01-2023 12:00:00','60000','Fabriqué en Inde');

CREATE TABLE EnchereValide(
  idEnchereValide serial primary key,
  dateValidation timestamp default CURRENT_TIMESTAMP,
  idEnchere int not null,
  foreign key(idEnchere) REFERENCES Enchere(idEnchere)
);
CREATE TABLE Mise (
  idMise           SERIAL NOT NULL, 
  dateMise         timestamp NOT NULL default CURRENT_TIMESTAMP, 
  soldeMise        float8 NOT NULL, 
  idClient   int4 NOT NULL, 
  idEnchere int4 NOT NULL, 
  PRIMARY KEY (idMise),
  Foreign key(idClient) REFERENCES Client(idClient),
  Foreign key(idEnchere) REFERENCES Enchere(idEnchere)
  );
  insert into Mise(soldeMise,idClient,idEnchere) values ('70000','2','1');


CREATE TABLE MvmtCompte (
  idMvmt         SERIAL NOT NULL, 
  montant        float8 NOT NULL, 
  dateMvmt       timestamp NOT NULL, 
  statut         int4 NOT NULL, 
  idClient int4 NOT NULL, 
  PRIMARY KEY (idMvmt),
  FOREIGN key(idClient) REFERENCES Client(idClient)
  );

CREATE TABLE PhotoEnchere (
  idPhotoEnchere   SERIAL NOT NULL, 
  photo            varchar(255) NOT NULL, 
  idEnchere int4 NOT NULL, 
  PRIMARY KEY (idPhotoEnchere),
  FOREIGN key(idEnchere) REFERENCES Enchere(idEnchere)
  );

CREATE TABLE ResultatEnchere (
  idResultat       SERIAL NOT NULL, 
  idEnchere int4 NOT NULL, 
  MiseidMise       int4 NOT NULL, 
  PRIMARY KEY (idResultat),
  FOREIGN key(idEnchere) REFERENCES Enchere(idEnchere)
  );


CREATE VIEW v_detailEnchere AS
  SELECT e.*,nomCategorie
  FROM Enchere e join Categorie c on e.idCategorie=c.idCategorie;

CREATE VIEW v_HistoriqueEnchere AS
  SELECT nom,prenom,soldeMise,dateMise
  FROM Client c join Mise m on c.idClient=m.idClient;

Create TABLE RechargementCompte(
    idRechargement serial primary key,
    dateRechargement date default CURRENT_DATE,
    montant double precision,
    idClient int not null,
    etat int not null default 0,
    Foreign key(idClient) REFERENCES Client(idClient)
);

Create table rechargevalide(
    idRechargeValide serial primary key,
    idRechargement int not null,
    foreign key(idRechargement) REFERENCES RechargementCompte(idRechargement),
    dateValidation date
);

CREATE TABLE TokenUserModel(
  idClient INT REFERENCES admin(idAdmin),
  hash text,
  expirationDate timestamp not null
);




ALTER TABLE Enchere DROP CONSTRAINT FKEnchere39417;
ALTER TABLE PhotoEnchere DROP CONSTRAINT FKPhotoEnche405191;
ALTER TABLE Mise DROP CONSTRAINT FKMise233586;
ALTER TABLE Mise DROP CONSTRAINT FKMise453344;
ALTER TABLE MvmtCompte DROP CONSTRAINT FKMvmtCompte470086;
ALTER TABLE Enchere DROP CONSTRAINT FKEnchere553311;
ALTER TABLE SoldeClient DROP CONSTRAINT FKSoldeClien569343;
ALTER TABLE ResultatEnchere DROP CONSTRAINT FKResultatEn121037;
ALTER TABLE ResultatEnchere DROP CONSTRAINT FKResultatEn664810;
DROP VIEW IF EXISTS v_HistoriqueEnchere;
DROP VIEW IF EXISTS v_detailEnchere;
DROP TABLE IF EXISTS Admin CASCADE;
DROP TABLE IF EXISTS Categorie CASCADE;
DROP TABLE IF EXISTS Client CASCADE;
DROP TABLE IF EXISTS Commission CASCADE;
DROP TABLE IF EXISTS Enchere CASCADE;
DROP TABLE IF EXISTS Mise CASCADE;
DROP TABLE IF EXISTS MvmtCompte CASCADE;
DROP TABLE IF EXISTS PhotoEnchere CASCADE;
DROP TABLE IF EXISTS ResultatEnchere CASCADE;
DROP TABLE IF EXISTS SoldeClient CASCADE;
