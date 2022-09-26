INSERT INTO ROLES (roleId,roleType) VALUES
	(1,'ROLE_ADMIN'),
    (2,'ROLE_MANAGER'),
    (3,'ROLE_USER');

INSERT INTO USERS (userId, userName, userPassword, firstName, lastName, email, isActive) VALUES 
	(1,'manager', 'password', 'Biraj', 'Das', 'birajdas.@gmail.com', true),
    (2,'admin', 'password', 'Silviu', 'Badica', 'silviu@gmail.com', true),
    (3,'user', 'password', 'Scott', 'Hollas', 'scho@gmail.com', true);
    
INSERT INTO USER_ROLE (userId, roleId) VALUES
	(1, 1), (1, 2), (1, 3),
    (2, 2), (2, 3),
    (3, 3);
    
INSERT INTO HASHTAGS (hashtagName) VALUES
	('Table Tennis'),
    ('Running'),
    ('Powerlifting'),
    ('Football');
    
    UPDATE USERS SET userPassword = '$2a$10$zaD5sc.G1WAWSpvPTZY7l.H8wWcSy.Vszwuz22ezCmA2PueQOvcgK' WHERE userId = 1;
    UPDATE USERS SET userPassword = '$2a$10$zaD5sc.G1WAWSpvPTZY7l.H8wWcSy.Vszwuz22ezCmA2PueQOvcgK' WHERE userId = 2;
	UPDATE USERS SET userPassword = '$2a$10$zaD5sc.G1WAWSpvPTZY7l.H8wWcSy.Vszwuz22ezCmA2PueQOvcgK' WHERE userId = 3;    
	
INSERT INTO BLOGPOSTS (timePosted, blogTitle, blogType, blogStatus, userId, blogContent) VALUES
	('2022-03-07', 'How to become good at table tennis', 'blog', 'public', 1,
		'Table tennis, also known as ping-pong and whiff-whaff, is a sport in which two or four players hit a lightweight ball, also known as the ping-pong ball, back and forth across a table using small solid rackets.
	 	 The game takes place on a hard table divided by a net. Except for the initial serve, the rules are generally as follows: players must allow a ball played toward them to bounce once on their side of the table and must
	 	 return it so that it bounces on the opposite side at least once. A point is scored when a player fails to return the ball within the rules. Play is fast and demands quick reactions. Spinning the ball alters its trajectory
	 	 and limits an opponent options, giving the hitter a great advantage.'),
	('2022-03-29', 'How to run', 'blog', 'public', 1,
		'Running is a method of terrestrial locomotion allowing humans and other animals to move rapidly on foot. Running is a type of gait characterized by an aerial phase in which all feet are above the ground (though there are exceptions).
	 	 This is in contrast to walking, where one foot is always in contact with the ground, the legs are kept mostly straight and the center of gravity vaults over the stance leg or legs in an inverted pendulum fashion.
	 	 A feature of a running body from the viewpoint of spring-mass mechanics is that changes in kinetic and potential energy within a stride occur simultaneously, with energy storage accomplished by springy tendons and passive muscle elasticity.
	 	 The term running can refer to any of a variety of speeds ranging from jogging to sprinting.'),
	('2022-04-07', 'About', 'static', 'public', 1,
		'This blog is about sports. I like sport and think that people should do more sport and upload their propgress whilst doing sport.'), 
	('2022-05-19', 'Powerlifting', 'blog', 'public', 1,
		'Powerlifting is a strength sport that consists of three attempts at maximal weight on three lifts: squat, bench press, and deadlift. As in the sport of Olympic
	 	weightlifting, it involves the athlete attempting a maximal weight single-lift effort of a barbell loaded with weight plates. Powerlifting evolved from a sport known as
	 	"odd lifts", which followed the same three-attempt format but used a wider variety of events, akin to strongman competition. Eventually odd lifts became standardized to the current three.'),
	('2022-09-23', 'How to kick a football', 'blog', 'public', 1,
		'Football is a family of team sports that involve, to varying degrees, kicking a ball to score a goal. Unqualified, the word football normally means the form of football that is the most popular where the word is used.
	 	Sports commonly called football include association football (known as soccer in North America and Oceania); gridiron football (specifically American football or Canadian football); Australian rules football; rugby union and rugby league; and Gaelic football.
	 	These various forms of football share to varying extent common origins and are known as football codes..');
       
INSERT INTO BLOGPOST_HASHTAG (blogpostId, hashtagId) VALUES 
	(1, 1), (2, 2), (3, 1), (4, 3), (5, 4);

INSERT INTO BLOGCOMMENTS (timePosted, commentsContent, userId, blogpostId) VALUES 
	('2022-08-18', 'I hurt my wrist playing table tennis, terrible sport', 3, 1),
    ('2022-09-21', 'Only positve comments please!', 2, 1),
	('2022-10-11', 'I just ran 0.5k', 3, 2),
    ('2022-11-12', 'Congratulations to you!', 2, 2),
	('2022-12-03', 'I am gigachad', 3, 4),
    ('2022-12-31', 'Why do I need to learn how to kick a football?', 3, 5);