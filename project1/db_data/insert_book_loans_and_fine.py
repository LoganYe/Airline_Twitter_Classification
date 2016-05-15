#!../flask/bin/python
# -*- coding: utf-8 -*-

import csv
import datetime
import time
import MySQLdb as mysql

# connect database
db = mysql.connect(user="root", passwd="a5iQb0eK", db="Library", charset="utf8")
db.autocommit(True)
c = db.cursor()

# insert book_loans
# read book_loans.csv file
csvfile = file('book_loans.csv', 'rb')
reader = csv.reader(csvfile,delimiter='\t')

sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	elif 1 == cnt:
		if "NULL" == line[6]:
			sql = 'INSERT IGNORE INTO `BOOK_LOANS` (`Loan_id`, `Book_id`, `Branch_id`, `Card_no`, `Date_out`, `Due_date`, `Date_in`) VALUES (%d, "%s", %d, %d, "%s", "%s", NULL)' % (int(line[0]), line[1], int(line[2]), int(line[3]), line[4], line[5])
		else:
			sql = 'INSERT IGNORE INTO `BOOK_LOANS` (`Loan_id`, `Book_id`, `Branch_id`, `Card_no`, `Date_out`, `Due_date`, `Date_in`) VALUES (%d, "%s", %d, %d, "%s", "%s", "%s")' % (int(line[0]), line[1], int(line[2]), int(line[3]), line[4], line[5], line[6])
	elif cnt > 1:
		if "NULL" == line[6]:
			sql += ', (%d, "%s", %d, %d, "%s", "%s", NULL)' % (int(line[0]), line[1], int(line[2]), int(line[3]), line[4], line[5])
		else:
			sql += ', (%d, "%s", %d, %d, "%s", "%s", "%s")' % (int(line[0]), line[1], int(line[2]), int(line[3]), line[4], line[5], line[6])

	cnt += 1
	if cnt > 100:
		cnt = 1
		try:
			# insert data into BOOK_LOANS table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in BOOK_AUTHORS table from book_loans.csv"
		sql = ''

if len(sql) != 0:
	try:
		# insert data into BOOK_LOANS table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT BOOK_AUTHORS table from book_loans.csv"

# close book_loans.csv file
csvfile.close()

# insert fine
# read book_loans.csv file
csvfile = file('book_loans.csv', 'rb')
reader = csv.reader(csvfile,delimiter='\t')

sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	else:
		today = datetime.date.today()
		t_due_date = time.strptime(line[5],'%Y-%m-%d')
		due_date = datetime.date(*t_due_date[:3])
		if "NULL" != line[6]:
			t_date_in = time.strptime(line[6],'%Y-%m-%d')
			date_in = datetime.date(*t_date_in[:3])

		# before the due date
		if due_date >= today:
			fine_amount = 0
			estimate_fine = 0
			pay_attr = 0
		# maybe need pay
		else:
			# need pay
			if "NULL" == line[6]:
				fine_amount = 0
				estimate_fine = (today - due_date).days * 0.25
				pay_attr = 0
			# already check in
			else:
				if due_date >= date_in:
					fine_amount = 0
					estimate_fine = 0
					pay_attr = 1
				else:
					fine_amount = (date_in - due_date).days * 0.25
					estimate_fine = fine_amount
					pay_attr = 1

		if 1 == cnt:
			sql = 'INSERT IGNORE INTO FINE (Loan_id, Fine_amt, Est_amt, Paid_attr) VALUES (%d, %f, %f, %d)' % (int(line[0]), fine_amount, estimate_fine, int(pay_attr))
		if cnt > 1:
			sql += ', (%d, %f, %f, %d)' % (int(line[0]), fine_amount, estimate_fine, int(pay_attr))

	cnt += 1
	if cnt > 100:
		cnt = 1
		try:
			# insert data into FINE table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in BOOK_AUTHORS table from book_loans.csv"
		sql = ''

if len(sql) != 0:
	try:
		# insert data into FINE table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT BOOK_AUTHORS table from book_loans.csv"

# close book_loans.csv file
csvfile.close()
