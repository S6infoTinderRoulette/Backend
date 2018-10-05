CREATE OR REPLACE FUNCTION TeacherCheck()
  RETURNS trigger AS
$$
BEGIN    
    IF NOT EXISTS (SELECT * FROM tinderroulette.members WHERE cip = NEW.cip_in_charge AND id_member_status = '2') THEN
		RAISE EXCEPTION 'Member in charge must be a teacher';
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER teacher_check_trigger
BEFORE INSERT OR UPDATE ON tinderroulette.activities
    FOR EACH ROW EXECUTE PROCEDURE TeacherCheck();
