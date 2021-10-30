CREATE TABLE USER(
    User_Id INT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    First_Name VARCHAR(50) NOT NULL,
    Last_Name VARCHAR(50) NOT NULL,
    Date_Of_Birth Date NOT NULL,
    Gender ENUM('Male','Female','Undisclosed') NOT NULL,
    Phone_Number VARCHAR(11) NOT NULL,
    Email_Id VARCHAR(100) NOT NULL UNIQUE,
    Role ENUM('ADMIN','USER') NOT NULL
);

CREATE TABLE USER_SEQUENCE(
    User_id INT NOT NULL,
    next_val INT
);

INSERT INTO USER_SEQUENCE(USER_ID,next_val) VALUES(4210,4211);

CREATE TABLE PUBLISHER(
    Publisher_Id INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL UNIQUE,
    Email_id VARCHAR(50),
    Phone_Number VARCHAR(11)
);

CREATE TABLE PUBLISHER_SEQUENCE(
    Publisher_Id INT NOT NULL,
    next_val INT
);
INSERT INTO PUBLISHER_SEQUENCE(Publisher_Id, next_val) values(4000,4001);

CREATE TABLE BOOK(
    Book_Id INT PRIMARY KEY,
    ISBN VARCHAR(50) NOT NULL UNIQUE,
    Title VARCHAR(50) NOT NULL,
    Publisher_Id INT NOT NULL,
    Year_Published INT NOT NULL,
    Edition VARCHAR(20),
    FOREIGN KEY fk_publisher(Publisher_Id) REFERENCES PUBLISHER(Publisher_Id)
);

CREATE TABLE BOOK_SEQUENCE(
    Book_Id INT NOT NULL,
    next_val INT
);

INSERT INTO BOOK_SEQUENCE(Book_Id,next_val) VALUES(1000,1001);

CREATE TABLE AUTHOR(
    Author_Id INT PRIMARY KEY,
    First_Name VARCHAR(50) NOT NULL,
    Last_Name VARCHAR(50) NOT NULL,
    Date_Of_Birth DATE NOT NULL,
    Gender ENUM('Male','Female','Undisclosed') NOT NULL
);
CREATE TABLE AUTHOR_SEQUENCE(
    Author_Id INT NOT NULL,
    next_val INT)
);
INSERT INTO AUTHOR_SEQUENCE(Author_Id,next_val) VALUES(3111,3112);

CREATE TABLE BOOK_AUTHOR(
    Book_Id INT,
    Author_Id INT,
    FOREIGN KEY fk_author(Author_Id) REFERENCES AUTHOR(Author_Id),
    FOREIGN KEY fk_book(Book_Id) REFERENCES BOOK(Book_Id)
);

CREATE TABLE BOOK_STATUS(
    Book_Id INT PRIMARY KEY,
    Status ENUM('ACTIVE','UNACTIVE') NOT NULL,
    Total_Number_Of_Copies INT DEFAULT 0,
    FOREIGN KEY fk_book(Book_Id) REFERENCES BOOK(Book_Id);
);


































