SET SCHEMA 'tinderroulette';

CREATE FUNCTION get_current_group(activityId int)
 RETURNS TABLE(teamSize bigint)
 AS
$body$
	SELECT COUNT(groupstudent.cip) as teamSize FROM tinderroulette.groups, tinderroulette.groupstudent 
	WHERE groups.id_activity = activityId AND groups.id_group = groupstudent.id_group 
	GROUP BY groupstudent.id_group
$body$
LANGUAGE sql;
