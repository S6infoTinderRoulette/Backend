CREATE FUNCTION get_single_member(activityId int)
 RETURNS TABLE(id_class text, cip character(8))
 AS
$body$	
SELECT memberclass.id_class, memberclass.cip
FROM tinderroulette.memberclass, tinderroulette.activities
WHERE activities.id_activity = activityId
AND activities.id_class = memberclass.id_class
AND NOT EXISTS (
	SELECT * 
	FROM tinderroulette.groups,
	tinderroulette.groupstudent
	WHERE groups.id_activity = activityId
	AND groups.id_group = groupstudent.id_group
	AND groupstudent.cip = memberclass.cip)
$body$
LANGUAGE sql;