select count(*) from BOOK_AUTHORS;
select * from BOOK_AUTHORS limit 10;


select * from BOOK_AUTHORS where Author_name like '%richard%' and Author_name like '%lim%';

select BOOK.ISBN, BOOK.Title, BOOK_AUTHORS.Author_name from BOOK, BOOK_AUTHORS where Author_name like '%richard%' and Author_name like '%lim%' and BOOK.ISBN = BOOK_AUTHORS.ISBN;		-- slow

select BOOK.ISBN, BOOK.Title, BOOK_AUTHORS.Author_name from BOOK_AUTHORS inner join BOOK on BOOK_AUTHORS.Author_name like '%richard%' and BOOK_AUTHORS.Author_name like '%lim%' and BOOK.ISBN = BOOK_AUTHORS.ISBN order by BOOK_AUTHORS.ISBN;	-- fast
select BOOK.ISBN, BOOK.Title, BOOK_AUTHORS.Author_name from BOOK_AUTHORS inner join BOOK on BOOK_AUTHORS.Author_name like '%david%' and BOOK_AUTHORS.Author_name like '%adams%' and BOOK.ISBN = BOOK_AUTHORS.ISBN order by BOOK_AUTHORS.ISBN;