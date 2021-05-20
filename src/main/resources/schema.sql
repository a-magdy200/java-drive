CREATE TABLE IF NOT EXISTS users (
  userId INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS notes (
  noteId INT PRIMARY KEY auto_increment,
  title VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  userId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES users(userId)
);


CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialId INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userId INT,
    foreign key (userId) references users(userId)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    name VARCHAR,
    type VARCHAR,
    size DECIMAL,
    userId INT,
    url VARCHAR,
    foreign key (userId) references users(userId)
);