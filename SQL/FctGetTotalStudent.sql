SET SCHEMA 'tinderroulette';

CREATE FUNCTION get_total_student(activityId int)
 RETURNS TABLE(totalstudent bigint)
 AS
$body$
	-- Get total number of students
	SELECT COUNT(cip) AS total
	FROM tinderroulette.memberclass, tinderroulette.activities 
	WHERE activities.id_activity = activityId AND activities.id_class = memberclass.id_class
$body$
LANGUAGE sql;
