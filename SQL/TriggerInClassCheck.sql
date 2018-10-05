CREATE OR REPLACE FUNCTION InClassCheck()
  RETURNS trigger AS
$$
BEGIN    
    IF NOT EXISTS(SELECT cip, memberclass.id_class 
		FROM tinderroulette.activities, 
		tinderroulette.memberclass, 
		tinderroulette.groups
		WHERE groups.id_group = NEW.id_group
		AND groups.id_activity = activities.id_activity 
		AND activities.id_class = memberclass.id_class
		AND memberclass.cip =  NEW.cip) THEN
	RAISE EXCEPTION 'Student is not part of this activity';
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER in_class_check
BEFORE INSERT OR UPDATE ON tinderroulette.groupstudent
    FOR EACH ROW EXECUTE PROCEDURE InClassCheck();
