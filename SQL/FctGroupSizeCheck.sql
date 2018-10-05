SET SCHEMA 'tinderroulette';

CREATE FUNCTION group_size_check(groupSize int, activityId int)
 RETURNS boolean
 AS
$body$
WITH freeSpace AS (
	-- Spot left for normal size/plus size
	WITH teamNb AS (
	-- Number of normal team/ plus team
		SELECT * FROM tinderroulette.get_number_group(activityId)),
	currentSize AS (
	-- Size of current teams
		SELECT * FROM tinderroulette.get_current_group(activityId))
	SELECT teamNb.nbteamplussize - (SELECT COUNT(currentSize)
	FROM currentSize, teamNb 
	WHERE currentSize.teamSize = teamNb.teamsize + 1) AS spotLeft,
	teamNb.teamsize+1 as teamsize
	FROM teamNb)
	SELECT 
		CASE 
		WHEN groupSize = freeSpace.teamsize THEN (freeSpace.spotleft - 1) >= 0
		WHEN groupSize < freeSpace.teamsize THEN true
		ELSE false
		END
	FROM freeSpace 	
$body$
LANGUAGE sql;
