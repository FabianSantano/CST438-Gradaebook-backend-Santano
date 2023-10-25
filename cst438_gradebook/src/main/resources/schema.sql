CREATE TABLE user_table (
  id INT AUTO_INCREMENT NOT NULL,
  alias VARCHAR(255) DEFAULT NULL,
  password VARCHAR(100),
  role varchar(25),
  PRIMARY KEY(id)
);


CREATE TABLE course (
  course_id int NOT NULL,
  instructor varchar(255) DEFAULT NULL,
  semester varchar(255) DEFAULT NULL,
  title varchar(255) DEFAULT NULL,
  year int NOT NULL,
  PRIMARY KEY (course_id),
  FOREIGN KEY (instructor) REFERENCES user_table (alias) on delete cascade 
);

CREATE TABLE enrollment (
  id int  NOT NULL AUTO_INCREMENT,
  student_email varchar(255) DEFAULT NULL,
  student_name varchar(255) DEFAULT NULL,
  course_id int  DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (course_id) REFERENCES course (course_id) on delete cascade 
);

CREATE TABLE assignment (
  id int NOT NULL AUTO_INCREMENT,
  due_date date DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  course_id int DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (course_id) REFERENCES course (course_id) on delete cascade 
) ;

CREATE TABLE assignment_grade (
  id int NOT NULL AUTO_INCREMENT,
  score int DEFAULT NULL,
  assignment_id int DEFAULT NULL,
  enrollment_id int DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (enrollment_id) REFERENCES enrollment (id) on delete cascade,
  FOREIGN KEY (assignment_id) REFERENCES assignment (id) on delete cascade
);



