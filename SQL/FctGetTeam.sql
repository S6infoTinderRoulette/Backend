SET SCHEMA 'tinderroulette';

CREATE OR REPLACE FUNCTION tinderroulette.get_team(_cip character(8), _id_activity integer)
  RETURNS TABLE(cip character(8), id_group integer) AS
$BODY$
	SELECT t2.cip, t2.id_group 
	FROM tinderroulette.groupstudent as t1,
	tinderroulette.groupstudent as t2
	LEFT JOIN tinderroulette.groups ON (id_activity = _id_activity AND id_group_type = 1)
	WHERE t1.cip = _cip 
	AND t1.id_group = t2.id_group
	AND t1.id_group = groups.id_group;
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
