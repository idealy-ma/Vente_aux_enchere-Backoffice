insert into utilisateur values ('1', 'RAZAFY', 'Idealy', '2003-04-17', 12000, 54, 1.65, 5, 50, 3, 'je , je, je');

insert into critere values ('1', 'Age');
insert into critere values ('2', 'Salaire');
insert into critere values ('3', 'Taille');
insert into critere values ('4', 'Couleur');
insert into critere values ('5', 'Mophologie');
insert into critere values ('6', 'Foi');
insert into critere values ('7', 'Bacc');

-- insert into coefficientCritere values ('1', );

select * from coefficientCritere;

select utilisateur.id, utilisateur.nom, utilisateur.prenom, critere.* from utilisateur cross join critere;

select * from utilisateur;

insert into sexe values(default, 'Homme');
insert into sexe values(default, 'Femme');

select * from sexe;

SELECT * FROM Critere WHERE 1=1;
