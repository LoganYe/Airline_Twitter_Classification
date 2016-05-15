#-- Run this script directly in the MySQL server query window it will automatically create the database and all the database objects.
-- DROP DATABASE Library;
CREATE DATABASE Library CHARACTER SET utf8;

#'Creating Library Schema
USE Library;

DROP TABLE FINE;
DROP TABLE BOOK_LOANS;
DROP TABLE BORROWER;
DROP TABLE BOOK_COPIES;
DROP TABLE LIBRARY_BRANCH;
DROP TABLE BOOK_AUTHORS;
DROP TABLE BOOK;

CREATE TABLE BOOK (
  Book_id    char(10) not null,
  Title      varchar(300) not null,
  CONSTRAINT pk_Book primary key (Book_id)
);

CREATE TABLE BOOK_AUTHORS (
  Book_id       char(10) not null,
  Author_name   varchar(50) not null,
  CONSTRAINT pk_book_authors primary key (Book_id,Author_name)
  -- CONSTRAINT fk_book_authors_book foreign key (Book_id) references BOOK(Book_id)
);

CREATE TABLE LIBRARY_BRANCH (
  Branch_id     int not null,
  Branch_name   varchar(20) not null,
  Address       varchar(40) not null,
  CONSTRAINT pk_branch_id primary key (Branch_id),
  CONSTRAINT uk_branch_name UNIQUE (Branch_name)
);

CREATE TABLE BOOK_COPIES (
  Book_id       char(10) not null,
  Branch_id     int not null,
  No_of_copies  int not null,
  CONSTRAINT pk_book_copies primary key (Book_id,Branch_id)
  -- CONSTRAINT fk_book_copies_book foreign key (Book_id) references BOOK(Book_id),
  -- CONSTRAINT fk_book_copies_library_branch foreign key (Branch_id) references LIBRARY_BRANCH(Branch_id)
);

CREATE TABLE BORROWER (
  Card_no    int not null,
  Fname      varchar(20) not null,
  Lname      varchar(20) not null,
  Address    varchar(40) not null,
  Phone      varchar(20) not null,
  CONSTRAINT pk_borrower primary key (Card_no)
);

CREATE TABLE BOOK_LOANS (
  Loan_id       int not null,
  Book_id       char(10) not null,
  Branch_id     int not null,
  Card_no       int not null,
  Date_out      date not null,
  Due_date      date not null,
  Date_in       date,
  CONSTRAINT pk_book_loans primary key (Loan_id),
  CONSTRAINT fk_book_loans_book foreign key (Book_id) references BOOK(Book_id),
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
