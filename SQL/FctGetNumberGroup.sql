SET SCHEMA 'tinderroulette';

CREATE FUNCTION get_number_group(activityId int)
 RETURNS TABLE(nbTeamNormalSize bigint, nbTeamPlusSize bigint, teamSize int)
 AS
$body$
	-- Number of normal team/ plus team
	WITH studentNbStat AS (
	-- Calculate number of normal team/plus team
		WITH nbPartners AS 
		-- Get number of students in team
			(SELECT activities.nb_partners AS sub
			FROM tinderroulette.activities 
			WHERE activities.id_activity = activityId),
		totalStudents AS 
		-- Get total number of students
			(SELECT * FROM tinderroulette.get_total_student(activityId))
		SELECT * FROM nbPartners, totalStudents)
	SELECT studentNbStat.totalstudent/studentNbStat.sub - studentNbStat.totalstudent%studentNbStat.sub AS nbTeamNormalSize, 
	studentNbStat.totalstudent%studentNbStat.sub AS nbTeamPlusSize,
	studentNbStat.sub AS teamSize
	FROM studentNbStat
$body$
LANGUAGE sql;
