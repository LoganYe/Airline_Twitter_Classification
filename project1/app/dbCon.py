#!../flask/bin/python
# -*- coding: utf-8 -*-

# from app import app
import datetime
import time
import MySQLdb as mysql
# import json

db = mysql.connect(user="root", passwd="a5iQb0eK", db="Library", charset="utf8")
db.autocommit(True)
c = db.cursor()

db2 = mysql.connect(user="root", passwd="a5iQb0eK", db="Library", charset="utf8")
db2.autocommit(True)
c2 = db2.cursor()


def search_BOOK(args):
	search_method = args.get('search')
	# search by book id
	if 'book_id' == search_method:
		# print '\r\nbook id\r\n'
		if '' == args.get('book_id'):
			return []
		sql = "SELECT BOOK.Book_id, BOOK.Title, BOOK_AUTHORS.Author_name FROM BOOK_AUTHORS INNER JOIN BOOK ON BOOK_AUTHORS.Book_id \
		= '%s' and BOOK.Book_id = BOOK_AUTHORS.Book_id ORDER BY BOOK_AUTHORS.Book_id;" % args.get('book_id')
		c.execute(sql)
		res = []
		for i in c.fetchall():
			sql = "SELECT Branch_id, No_of_copies FROM BOOK_COPIES WHERE Book_id = '%s' and No_of_copies > 0 ORDER BY Branch_id;" % i[0]
			c2.execute(sql)
			branch_info = [{'branch_id':j[0], 'no_of_copies':j[1]} for j in c2.fetchall()]
			res.append({'book_id':i[0], 'title':i[1], 'author':i[2], 'branch':branch_info})

		# print res
		return res



	# search by title
	elif 'title' == search_method:
		# print '\r\ntitle\r\n'
		if '' == args.get('title'):
			return []
		sql = "SELECT BOOK.Book_id, BOOK.Title, BOOK_AUTHORS.Author_name FROM BOOK INNER JOIN BOOK_AUTHORS ON BOOK.Title LIKE '%%%s%%' \
		and BOOK.Book_id = BOOK_AUTHORS.Book_id ORDER BY BOOK.Book_id;" % args.get('title')
		c.execute(sql)
		res = []
		for i in c.fetchall():
			sql = "SELECT Branch_id, No_of_copies FROM BOOK_COPIES WHERE Book_id = '%s' and No_of_copies > 0 ORDER BY Branch_id;" % i[0]
			c2.execute(sql)
			branch_info = [{'branch_id':j[0], 'no_of_copies':j[1]} for j in c2.fetchall()]
			res.append({'book_id':i[0], 'title':i[1], 'author':i[2], 'branch':branch_info})
		return res

	# search by authors
	elif 'author' == search_method:
		# print '\r\nauthor\r\n'
		if '' == args.get('author'):
			return []
		authors = args.get('author').split(' ')
		gen_str = ''
		cnt = 0
		for author_str in authors:
			if 0 == cnt:
				gen_str = "BOOK_AUTHORS.Author_name LIKE '%%%s%%'" % author_str
			else:
				gen_str += " and BOOK_AUTHORS.Author_name LIKE '%%%s%%'" % author_str
			cnt += 1
		sql = "SELECT BOOK.Book_id, BOOK.Title, BOOK_AUTHORS.Author_name FROM BOOK_AUTHORS INNER JOIN BOOK ON %s and BOOK.Book_id \
		= BOOK_AUTHORS.Book_id ORDER BY BOOK_AUTHORS.Book_id;" % gen_str
		c.execute(sql)
		res = []
		for i in c.fetchall():
			sql = "SELECT Branch_id, No_of_copies FROM BOOK_COPIES WHERE Book_id = '%s' and No_of_copies > 0 ORDER BY Branch_id;" % i[0]
			c2.execute(sql)
			branch_info = [{'branch_id':j[0], 'no_of_copies':j[1]} for j in c2.fetchall()]
			res.append({'book_id':i[0], 'title':i[1], 'author':i[2], 'branch':branch_info})
		return res
	else:
		# print '\r\nno args\r\n'
		return []


def check_out_BOOK(args):
	book_id = args.get('book_id')
	branch_id = args.get('branch_id')
	card_no = args.get('card_no')

	# error: no args
	if (None == book_id or None == branch_id or None == card_no):
		retStr = ""
		return retStr,[]

	if ('' != book_id and '' != branch_id and '' != card_no):
		# valid args: book_id & branch_id
		sql = "SELECT COUNT(*) FROM BOOK_COPIES WHERE Book_id = '%s' and Branch_id = %d;" % (book_id, int(branch_id))
		c.execute(sql)
		resTemp = c.fetchone()
		if 0 == int(resTemp[0]):
			retStr = "Error: no data for such book id or branch id"
			return retStr,[]

		# valid args: card_no
		sql = "SELECT COUNT(*) FROM BORROWER WHERE Card_no = %d;" % int(card_no)
		c.execute(sql)
		resTemp = c.fetchone()
		if 0 == int(resTemp[0]):
			retStr = "Error: no data for such card no"
			return retStr,[]

		# valid whether borrower have right to borrow more than 3 books
		sql = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Card_no = %d and Date_in is NULL;" % int(card_no)
		c.execute(sql)
		resTemp = c.fetchone()
		if int(resTemp[0]) >= 3:
			retStr = "Error: cannot borrow more than 3 books"
			return retStr,[]

		# get number of book copies
		sql = "SELECT No_of_copies FROM BOOK_COPIES WHERE Book_id = '%s' and Branch_id = %d;" % (book_id, int(branch_id))
		c.execute(sql)
		resTemp = c.fetchone()
		Num_book_copies = int(resTemp[0])

		# valid whether there are book to borrow
		sql = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Book_id = '%s' and Branch_id = %d and Date_in is NULL;" % (book_id, int(branch_id))
		c.execute(sql)
		resTemp = c.fetchone()
		# print "%d,%d \r\n" % (Num_book_copies,int(resTemp[0]))
		# print sql
		if Num_book_copies <= int(resTemp[0]):
			retStr = "Error: no book you can borrow, bave been borrowed by other people"
			return retStr,[]

		# get new Loan_id
		sql = "SELECT COUNT(*) FROM BOOK_LOANS;"
		c.execute(sql)
		resTemp = c.fetchone()
		new_Loan_id = int(resTemp[0]) + 2

		# borrow book
		sql = "INSERT INTO `BOOK_LOANS` (`Loan_id`,`Book_id`,`Branch_id`,`Card_no`,`Date_out`,`Due_date`,`Date_in`) VALUES (%d, '%s', %d, %d, CURDATE(), DATE_ADD(CURDATE(),INTERVAL 14 DAY), NULL);" % (int(new_Loan_id), book_id, int(branch_id), int(card_no))
		c.execute(sql)

		res = []
		# print res
		retStr = "Loan ok!"
		return retStr,res

	else:
		print '\r\nwrong args\r\n'
		retStr = "Error: wrong input(input box should not leave blank)"
		return retStr,[]


def check_in_BOOK(args):
	check_in_mode = args.get('check_in_mode')
	search_method = args.get('search')

	# no args
	if '' == check_in_mode or None == check_in_mode:
		retStr = ""
		return retStr,[]

	# search book_loan
	if 'search' == check_in_mode:
		# search by book id
		if 'book_id' == search_method:
			if '' == args.get('book_id') or None == args.get('book_id'):
				retStr = "Error: wrong input(input box should not leave blank)"
				return retStr,[]

			sql = "SELECT Loan_id, Book_id, Branch_id, Card_no, Date_out, Due_date FROM BOOK_LOANS WHERE Book_id = '%s' and Date_in is NULL;" % args.get('book_id')
			c.execute(sql)
			res = []
			for i in c.fetchall():
				res.append({'loan_id':i[0], 'book_id':i[1], 'branch_id':i[2], 'card_no':i[3], 'date_out':i[4], 'due_date':i[5]})

			# print res
			retStr = ""
			return retStr,res

		# search by card no
		elif 'card_no' == search_method:
			if '' == args.get('card_no') or None == args.get('card_no'):
				retStr = "Error: wrong input(input box should not leave blank)"
				return retStr,[]

			sql = "SELECT Loan_id, Book_id, Branch_id, Card_no, Date_out, Due_date FROM BOOK_LOANS WHERE Card_no = %d and Date_in is NULL;" % int(args.get('card_no'))
			c.execute(sql)
			res = []
			for i in c.fetchall():
				res.append({'loan_id':i[0], 'book_id':i[1], 'branch_id':i[2], 'card_no':i[3], 'date_out':i[4], 'due_date':i[5]})

			# print res
			retStr = ""
			return retStr,res

		# # search by borrower name
		elif 'borrower' == search_method:
			if '' == args.get('borrower') or None == args.get('borrower'):
				retStr = "Error: wrong input(input box should not leave blank)"
				return retStr,[]

			sql = "SELECT Card_no FROM BORROWER WHERE Fname LIKE '%%%s%%' or Lname LIKE '%%%s%%' ORDER BY Card_no;" % (args.get('borrower'), args.get('borrower'))
			c.execute(sql)
			res = []
			for i in c.fetchall():
				sql = "SELECT Loan_id, Book_id, Branch_id, Card_no, Date_out, Due_date FROM BOOK_LOANS WHERE Card_no = %d and Date_in is NULL;" % int(i[0])
				c2.execute(sql)
				for j in c2.fetchall():
					res.append({'loan_id':j[0], 'book_id':j[1], 'branch_id':j[2], 'card_no':j[3], 'date_out':j[4], 'due_date':j[5]})
			retStr = ""
			return retStr,res


	# check in book_loan
	if 'check_in' == check_in_mode:

		if '' == args.get('loan_id') or None == args.get('loan_id'):
			retStr = "Error: please select one!"
			return retStr,[]

		# whether had loan this book
		sql = "SELECT count(*) FROM BOOK_LOANS WHERE Loan_id = %d and Date_in is NULL;" % int(args.get('loan_id'))
		c.execute(sql)
		resTemp = c.fetchone()
		if 0 == int(resTemp[0]):
			retStr = "Error: no book to check in"
			return retStr,[]

		sql = "UPDATE BOOK_LOANS SET Date_in = CURDATE() WHERE Loan_id = %d and Date_in is NULL;" % int(args.get('loan_id'))
		c.execute(sql)

		retStr = "check in ok!"
		return retStr,[]

	else:
		# print '\r\nno args\r\n'
		retStr = "Error: wrong input(input box should not leave blank)"
		return retStr,[]


def borrower_mgr(args):
	f_name = args.get('fname')
	l_name = args.get('lname')
	address = args.get('address')
	phone = args.get('phone')

	if (None == f_name or None == l_name or None == address or None == phone):
		retStr = ""
		return retStr,[]

	#if this borrower exists
	if ('' != f_name and '' != l_name and '' != address and '' != phone):
		sql = "SELECT COUNT(*) FROM BORROWER WHERE Fname = '%s' and Lname = '%s' and Address = '%s';" % (f_name, l_name, address)
		c.execute(sql)
		resTemp = c.fetchone()
		if int(resTemp[0]) >= 1:
			retStr = "Error: borrower exists"
			return retStr,[]

		#insert new borrower
		sql = "SELECT COUNT(*) FROM BORROWER"
		c.execute(sql)
		resTemp = c.fetchone()
		new_card_no = int(resTemp[0]) + 1

		sql = 'INSERT INTO BORROWER (Card_no, Fname, Lname, Address, Phone) VALUES (%d, "%s", "%s", "%s", "%s");' % (new_card_no, f_name, l_name, address, phone)
		c.execute(sql)

		retStr = "Success! Your card no:%d" % new_card_no
		return retStr,[]


	else:
		retStr = "Error: wrong input(name, address and should not be blank)"
		return retStr,[]


def fine(args):
	fine_mode = args.get('fine_mode')
	# no args
	if '' == fine_mode or None == fine_mode:
		retStr = ""
		return retStr,[]

	# search fine
	if 'search' == fine_mode:
		card_no = args.get('card_no')

		if (None == card_no):
			retStr = ""
			return retStr,[]

		#if this card_no exists
		if ('' != card_no):
			sql = "SELECT BOOK_LOANS.Card_no, FINE.Loan_id, FINE.Est_amt FROM FINE INNER JOIN BOOK_LOANS ON FINE.Est_amt != 0 and BOOK_LOANS.Card_no = %d and FINE.Loan_id = BOOK_LOANS.Loan_id GROUP BY FINE.Loan_id;" % int(card_no)
			c.execute(sql)
			res = []
			for i in c.fetchall():
				res.append({'card_id':i[0], 'loan_id':i[1], 'est_amt':i[2]})

			retStr = ""
			return retStr,res
			# sql2 = "SELECT (Card_no, Loan_id, Fine_amt) FROM BOOK_LOANS, FINE WHERE BOOK_LOANS.Loan_id = FINE.Loan_id and Paid_attr = 1 and Card_no = %d and Loan_id = %d and Fine_amt = %f GROUP BY Card_no;" % (Card_no, Loan_id, Fine_amt)
			# c.execute(sql2)
			# resStr2 = c.fetchall()
			# return retStr2,[]

	# pay
	if 'pay' == fine_mode:
		loan_id = args.get('loan_id')

		if (None == loan_id or '' == loan_id):
			retStr = "you need select one to pay"
			return retStr,[]

		#if this loan_id exists
		sql = "SELECT Est_amt FROM FINE WHERE Loan_id = %d;" % int(loan_id)
		c.execute(sql)
		resTemp = c.fetchone()
		fine_amt = float(resTemp[0])

		sql = "UPDATE FINE SET Fine_amt = %.2f, Paid_attr = 1 WHERE Loan_id = %d and Paid_attr = 0;" % (fine_amt, int(loan_id))
		c.execute(sql)

		retStr = "check in ok!"
		return retStr,[]

	else:
		retStr = "Error: wrong input(Card_no should not be blank)"
		return retStr,[]


# if __name__ == '__main__':
# 	print search_BOOK()
