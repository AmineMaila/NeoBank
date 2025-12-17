INSERT INTO users (username, hashed_password, role) VALUES ('john_doe', 'pass123', 'ROLE_USER');
INSERT INTO users (username, hashed_password, role) VALUES ('jane_smith', 'pass123', 'ROLE_USER');

INSERT INTO accounts (user_id, balance) VALUES (1, 1000.00);
INSERT INTO accounts (user_id, balance) VALUES (2, 500.00);