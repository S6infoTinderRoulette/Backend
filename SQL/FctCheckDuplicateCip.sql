CREATE FUNCTION check_duplicate_cip(userCip character(8),activityId int)
 RETURNS boolean
 AS
$body$	
BEGIN
	RETURN NOT EXISTS (SELECT * 
	FROM tinderroulette.groupstudent, tinderroulette.groups
	WHERE groupstudent.cip = userCip 
	AND groupstudent.id_group = groups.id_group
	AND groups.id_activity = activityId);
END
$body$
LANGUAGE plpgsql;
