INSERT INTO tinderroulette.memberstatus(
            id_member_status, status)
    VALUES (1,'Élève'),
	(2,'Enseignant'),
	(3,'Administrateur');
	
INSERT INTO tinderroulette.members(
            cip, id_member_status, last_name, first_name, email)
    VALUES ('camj3302', '2', 'Camirand', 'Johany', 'johany.camirand@usherbrooke.ca'),
	('cotd2511', '1', 'Côté-Martel', 'Danick', 'danick.cote-martel@usherbrooke.ca'),
	('fanv2501', '1', 'Fan', 'Victor', 'victor.fan@usherbrooke.ca'),
	('leba3207', '1', 'Le Bouler', 'Agathe', 'agathe.le.bouler@usherbrooke.ca'),
	('chac2848', '1', 'Corentin', 'Chatelin', 'corentin.chatelin@usherbrooke.ca'),
	('pelm2528', '1', 'Pelletier', 'Marc-Éric', 'marc-eric.pelletier@usherbrooke.ca');
	
INSERT INTO tinderroulette.grouptype(
            type)
    VALUES ('Groupe Activité'),
		('Procédural'),
		('Tutorat');
		
INSERT INTO tinderroulette.ap(
            id_ap)
    VALUES ('S6GEN60002A18');

INSERT INTO tinderroulette.app(
            id_app)
    VALUES('S6APP1A18'),
('S6APP2A18'),
('S6APP3A18'),
('S6APP4A18'),
('S6APP5A18');

INSERT INTO tinderroulette.activities(id_class,cip_in_charge
            , nb_partners)
    VALUES ('S6GEN60002A18', 'camj3302', 2);

INSERT INTO tinderroulette.requesttype(request_type)
VALUES ('Team'),
('Friend');

INSERT INTO tinderroulette.memberclass(
            cip, id_class)
    VALUES ('fanv2501', 'S6GEN60002A18'),
    ('chac2848', 'S6GEN60002A18'),
    ('cotd2511', 'S6GEN60002A18'),
    ('leba3207', 'S6GEN60002A18'),
    ('pelm2528', 'S6GEN60002A18');

WITH group_id AS (INSERT INTO tinderroulette.groups(id_group_type, id_activity)
    VALUES (1, 1)
    RETURNING id_group)
INSERT INTO tinderroulette.groupstudent(
            id_group, cip)
    VALUES ((SELECT * FROM group_id), 'chac2848'), 
    ((SELECT * FROM group_id), 'leba3207');

WITH group_id AS (INSERT INTO tinderroulette.groups(id_group_type, id_activity)
    VALUES (1, 1)
    RETURNING id_group)
INSERT INTO tinderroulette.groupstudent(
            id_group, cip)
    VALUES ((SELECT * FROM group_id), pelm2528), 
    ((SELECT * FROM group_id), fanv2501);
