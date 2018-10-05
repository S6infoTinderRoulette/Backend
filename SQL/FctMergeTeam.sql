SET SCHEMA 'tinderroulette';

CREATE OR REPLACE FUNCTION tinderroulette.merge_teams(
	_cip1 character(8), 
	_cip2 character(8), 
	_id_activity integer)
  RETURNS TABLE(test integer) AS
$BODY$
DECLARE 
nb_groups bigint;
nb_membre bigint;
BEGIN
	CREATE TEMP TABLE temp_groups AS
	SELECT * FROM tinderroulette.get_team(_cip1,_id_activity) UNION
	SELECT * FROM tinderroulette.get_team(_cip2,_id_activity);
	
	nb_groups := (SELECT COUNT(DISTINCT temp_groups.id_group) FROM temp_groups);
	nb_membre := (SELECT COUNT(cip) FROM temp_groups);
	
	IF (nb_groups = 2) THEN		
		IF (tinderroulette.group_size_check(nb_membre::int,_id_activity)) THEN
			RETURN QUERY (SELECT 2); RETURN;
		END IF;
	ELSIF (nb_groups = 1) THEN
		IF (tinderroulette.group_size_check(nb_membre::int + 1,_id_activity)) THEN
			RETURN QUERY (SELECT 1); RETURN;
		END IF;
	ELSE
		IF (tinderroulette.group_size_check(2,_id_activity)) THEN
			RETURN QUERY (SELECT 0); RETURN;
		END IF;
	END IF;
	RETURN QUERY (SELECT -1);
	DROP TABLE temp_groups;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
