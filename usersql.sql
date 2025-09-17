-- CREATE TABLE USERS ( 
--       id INTEGER PRIMARY KEY AUTOINCREMENT,
--       firstName TEXT,
--       lastName TEXT ,
--       username TEXT NOT NULL UNIQUE,
--       email TEXT NOT NULL UNIQUE,
--       password TEXT NOT NULL
--       );

-- INSERT INTO users (firstName, lastName, username, email, password)
-- VALUES ("Isaac", "Dereje", "yisusername", "yis@gmail.com", "yispass");

-- INSERT INTO users (firstName, username, email, password)
-- VALUES ("yoni","yoniusername", "yoni@gmail.com", "yonipasswrd");

-- DELETE FROM USERS
-- WHERE username = "natiusername";

-- DROP TABLE POSTS;
-- SELECT * FROM users WHERE username = yoniusername AND password = yonipasswrd;


-- CREATE TABLE POSTS (
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     user_id INTEGER NOT NULL,
--     content TEXT NOT NULL,
--     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY(user_id) REFERENCES USERS(id) ON DELETE CASCADE
-- );

-- INSERT INTO POSTS (user_id, content) 
-- VALUES (1, 'This is my first post!');

-- CREATE TABLE LIKES (
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     post_id INTEGER NOT NULL,
--     user_id INTEGER NOT NULL,
--     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY(post_id) REFERENCES POSTS(id) ON DELETE CASCADE,
--     FOREIGN KEY(user_id) REFERENCES USERS(id) ON DELETE CASCADE,
--     UNIQUE(post_id, user_id) -- each user can like a post only once
-- );

-- INSERT INTO LIKES (post_id, user_id) VALUES (1, 1);
-- INSERT INTO LIKES (post_id, user_id) VALUES (1, 2);

-- SELECT * FROM USERS;
-- SELECT * FROM POSTS;
-- SELECT * FROM LIKES;

-- SELECT COUNT(*) AS like_count
-- FROM LIKES
-- WHERE post_id = 1;





