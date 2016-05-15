#-- Run this script directly in the MySQL server query window it will automatically create the database and all the database objects.
-- DROP DATABASE Library;
DROP DATABASE IF EXISTS library;
CREATE DATABASE Library CHARACTER SET utf8;

#'Creating Library Schema
USE Library;

DROP TABLE IF EXISTS FINE;
DROP TABLE IF EXISTS BOOK_LOANS;
DROP TABLE IF EXISTS BORROWER;
DROP TABLE IF EXISTS BOOK_COPIES;
DROP TABLE IF EXISTS LIBRARY_BRANCH;
DROP TABLE IF EXISTS BOOK_AUTHORS;
DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK (
  ISBN       char(10) not null,
  Title      varchar(300) not null,
  CONSTRAINT pk_Book primary key (ISBN)
);

CREATE TABLE BOOK_AUTHORS (
  ISBN       char(10) not null,
  Author_id  varchar(50) not null,
  CONSTRAINT pk_book_authors primary key (ISBN,Author_id)
  -- CONSTRAINT fk_book_authors_book foreign key (Book_id) references BOOK(Book_id)
);

CREATE TABLE AUTHORS (
  Author_id   varchar(50) not null,
  Title       varchar(50) ,
  Fname       varchar(50) ,
  Mname       varchar(50) ,
  Lname       varchar(50) ,
  Suffix      varchar(50) ,
CONSTRAINT pk_authors primary key (Author_id)
);

CREATE TABLE LIBRARY_BRANCH (
  Branch_id     int not null,
  Branch_name   varchar(20) ,
  Address       varchar(40) ,
  CONSTRAINT pk_branch_id primary key (Branch_id),
  CONSTRAINT uk_branch_name UNIQUE (Branch_name)
);

CREATE TABLE BOOK_COPIES (
  ISBN       char(10) not null,
  Branch_id     int not null,
  No_of_copies  int ,
  CONSTRAINT pk_book_copies primary key (ISBN,Branch_id)
  -- CONSTRAINT fk_book_copies_book foreign key (Book_id) references BOOK(Book_id),
  -- CONSTRAINT fk_book_copies_library_branch foreign key (Branch_id) references LIBRARY_BRANCH(Branch_id)
);

CREATE TABLE BORROWER (
  Card_no    char(20) not null,
  Ssn		     char(50) not null,
  Fname      varchar(20) not null,
  Lname      varchar(20) not null,
  Address    varchar(40) not null,
  Phone      varchar(20) not null,
  CONSTRAINT pk_borrower primary key (Card_no)
);

CREATE TABLE BOOK_LOANS (
  Loan_id       int not null,
  ISBN          char(10) not null,
  Branch_id     int not null,
  Card_no       char(20) not null,
  Date_out      date not null,
  Due_date      date not null,
  Date_in       date,
  CONSTRAINT pk_book_loans primary key (Loan_id),
  CONSTRAINT fk_book_loans_book foreign key (ISBN) references BOOK(ISBN),
  CONSTRAINT fk_book_loans_library_branch foreign key (Branch_id) references LIBRARY_BRANCH(Branch_id),
  CONSTRAINT fk_book_loans_borrower foreign key (Card_no) references BORROWER(Card_no)
);

CREATE TABLE FINE (
  Loan_id    int not null,
  Fine_amt   float not null,    #amount
  Est_amt    float not null,
  Paid_attr  int not null,      #paid or not
  CONSTRAINT pk_fine primary key (Loan_id),
  CONSTRAINT fk_fine foreign key (Loan_id) references BOOK_LOANS(Loan_id)
);
