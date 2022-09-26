CREATE TABLE ROLES (
	roleId SERIAL PRIMARY KEY,
    roleType VARCHAR(30) NOT NULL
);

CREATE TABLE HASHTAGS (
	hashtagId SERIAL PRIMARY KEY,
    hashtagName VARCHAR(40) NOT NULL
);

CREATE TABLE USERS (
	userId SERIAL PRIMARY KEY,
    userName VARCHAR(30) NOT NULL,
    userPassword VARCHAR(100) NOT NULL,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    isActive BOOLEAN NOT NULL
);

CREATE TABLE BLOGPOSTS (
	blogpostId SERIAL PRIMARY KEY,
    timePosted TIMESTAMP WITH TIME ZONE,
    blogTitle VARCHAR(100) NOT NULL,
    blogType VARCHAR(30) NOT NULL,
    blogStatus VARCHAR(30) NOT NULL,
    blogPhoto VARCHAR(255),
    blogContent TEXT NOT NULL,
    userId INT,
    FOREIGN KEY (userId) references USERS(userId)
);

CREATE TABLE BLOGCOMMENTS (
	commentId SERIAL PRIMARY KEY,
    timePosted TIMESTAMP with time zone,
    commentsContent TEXT NOT NULL,
    userId INT,
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    blogpostId INT,
    FOREIGN KEY (blogpostId) REFERENCES BLOGPOSTS (blogpostId)
);

CREATE TABLE BLOGPOST_HASHTAG (
	blogpostId INT,
    hashtagId INT,
    PRIMARY KEY (blogpostId, hashtagId),
    FOREIGN KEY (blogpostId) REFERENCES BLOGPOSTS (blogpostId),
    FOREIGN KEY (hashtagId) REFERENCES HASHTAGS (hashtagId)
);

CREATE TABLE USER_ROLE (
	userId INT,
    roleId INT,
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    FOREIGN KEY (roleId) REFERENCES ROLES(roleId)
);