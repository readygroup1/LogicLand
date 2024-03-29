-- SQL Dump for Database

-- Dropping tables if they exist (to prevent errors in recreating the schema)
DROP TABLE IF EXISTS HIGHSCORE;
DROP TABLE IF EXISTS LEVELS;
DROP TABLE IF EXISTS PLAYER;
DROP TABLE IF EXISTS CLASSROOM;
DROP TABLE IF EXISTS ADMIN;
DROP TABLE IF EXISTS SANDBOX;

-- Creating table SANDBOX
CREATE TABLE SANDBOX (
    SandboxID INT PRIMARY KEY,
    ProjectTitle VARCHAR(255),
    LastModified DATE,
    SaveState VARCHAR(8000)
);

-- Creating table ADMIN
CREATE TABLE ADMIN (
    AdminID INT PRIMARY KEY,
    AdminName VARCHAR(255),
    Initials VARCHAR(255),
    AdminPassword VARCHAR(255),
    AdminEmail VARCHAR(255),
    SandboxID INT,
    FOREIGN KEY (SandboxID) REFERENCES SANDBOX(SandboxID)
);

-- Creating table CLASSROOM
CREATE TABLE CLASSROOM (
    ClassID INT PRIMARY KEY,
    ClassName VARCHAR(255),
    AdminID INT,
    FOREIGN KEY (AdminID) REFERENCES ADMIN(AdminID)
);

-- Creating table PLAYER
CREATE TABLE PLAYER (
    PlayerID INT PRIMARY KEY,
    Name VARCHAR(255),
    Initials VARCHAR(255),
    Password VARCHAR(255),
    Email VARCHAR(255),
    SandboxID INT,
    inTutorial BOOLEAN,
    ClassID INT,
    Minutes INT,
    FOREIGN KEY (SandboxID) REFERENCES SANDBOX(SandboxID),
    FOREIGN KEY (ClassID) REFERENCES CLASSROOM(ClassID)
);

-- Creating table LEVELS
CREATE TABLE LEVELS (
    LevelID INT PRIMARY KEY,
    CurrentLevel INT,
    LevelScore INT,
    CurrentLevelSaveState VARCHAR(8000),
    PlayerID INT,
    FOREIGN KEY (PlayerID) REFERENCES PLAYER(PlayerID)
);

-- Creating table HIGHSCORE
CREATE TABLE HIGHSCORE (
    PlayerID INT,
    Initials VARCHAR(255),
    UserScore INT,
    FOREIGN KEY (PlayerID) REFERENCES PLAYER(PlayerID)
);

-- Inserting sample data into SANDBOX
INSERT INTO SANDBOX VALUES (1, 'Sample Project', '2024-01-01', 'Sample Save State');

-- Inserting sample data into ADMIN
INSERT INTO ADMIN VALUES (1, 'AdminName', 'ANI', 'AdminPass', 'admin@example.com', 1);

-- Inserting sample data into CLASSROOM
INSERT INTO CLASSROOM VALUES (1, 'Sample Class', 1);

-- Inserting sample data into PLAYER
INSERT INTO PLAYER VALUES (1, 'John Doe', 'JD', 'password123', 'john.doe@example.com', 1, FALSE, 1, 120);

-- Inserting sample data into LEVELS
INSERT INTO LEVELS VALUES (1, 1, 100, 'Level State', 1);

-- Inserting sample data into HIGHSCORE
INSERT INTO HIGHSCORE VALUES (1, 'JD', 5000);

-- Repeat the INSERT INTO statements as needed to populate the database with initial data
