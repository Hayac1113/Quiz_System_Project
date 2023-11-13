-- DROP FOREIGN KEY CONSTRAINTS QUIZMASTER

ALTER TABLE Quiz
DROP CONSTRAINT `fk_Quiz_Course1`;

ALTER TABLE Course
DROP CONSTRAINT `fk_Course_User1`;
 
ALTER TABLE Question
DROP CONSTRAINT `fk_Question_Quiz1`;

ALTER TABLE `Group`
DROP CONSTRAINT `fk_Group_Course`,
DROP CONSTRAINT `fk_Group_User1`;
 
ALTER TABLE GroupAssignment
DROP CONSTRAINT `fk_Group_has_User_Group1`,
DROP CONSTRAINT `fk_Group_has_User_User2`;
 
ALTER TABLE studentRegistration
DROP CONSTRAINT `fk_Group_has_User_User1`,
DROP CONSTRAINT `fk_studentRegistration_Course1`;
 
ALTER TABLE QuizResult
DROP CONSTRAINT `fk_User_has_Quiz_User1`,
DROP CONSTRAINT `fk_User_has_Quiz_Quiz1`;