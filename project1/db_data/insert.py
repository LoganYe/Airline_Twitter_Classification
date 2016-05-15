#!../flask/bin/python
# -*- coding: utf-8 -*-

import csv
import MySQLdb as mysql

# connect database
db = mysql.connect(user="root", passwd="a5iQb0eK", db="Library", charset="utf8")
db.autocommit(True)
c = db.cursor()

# read books3.csv file
csvfile = file('books3.csv', 'rb')
reader = csv.reader(csvfile,delimiter='\t')

sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	elif 1 == cnt:
		sql = 'INSERT IGNORE INTO `BOOK` (`Book_id`, `Title`) VALUES ("%s", "%s")' % (line[0], line[2].replace('"',''))
	elif cnt > 1:
		sql += ', ("%s", "%s")' % (line[0], line[2].replace('"',''))

	cnt += 1
	if cnt > 20000:
		cnt = 1
		try:
			# insert data into BOOK table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in INSERT BOOK table from books3.csv"
		sql = ''

if len(sql) != 0:
	try:
		# insert data into BOOK table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT BOOK table from books3.csv"

# close books3.csv file
csvfile.close()

# read books3.csv file
csvfile = file('books3.csv', 'rb')
reader = csv.reader(csvfile,delimiter='\t')

sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	elif 1 == cnt:
		sql = 'INSERT IGNORE INTO BOOK_AUTHORS (Book_id, Author_name) VALUES ("%s", "%s")' % \
		(line[0], line[3].replace('"',''))
	elif cnt > 1:
		sql += ', ("%s", "%s")' % (line[0], line[3].replace('"',''))

	cnt += 1
	if cnt > 20000:
		cnt = 1
		try:
			# insert data into BOOK_AUTHORS table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in BOOK_AUTHORS table from books3.csv"
		sql = ''

if len(sql) != 0:
	try:
		# insert data into BOOK_AUTHORS table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT BOOK_AUTHORS table from books3.csv"

# close books3.csv file
csvfile.close()


# read library_branch.csv file
csvfile = file('library_branch.csv', 'rb')
reader = csv.reader(csvfile,delimiter='\t')

sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	elif 1 == cnt:
		sql = 'INSERT INTO LIBRARY_BRANCH (Branch_id, Branch_name, Address) VALUES (%d, "%s", "%s")' % \
		(int(line[0]), line[1], line[2])
	elif cnt > 1:
		sql += ', (%d, "%s", "%s")' % (int(line[0]), line[1], line[2])

	cnt += 1
	if cnt > 5:
		cnt = 1
		try:
			# insert data into LIBRARY_BRANCH table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in LIBRARY_BRANCH table from library_branch.csv"
		sql = ''

if len(sql) != 0:
	try:
		# insert data into LIBRARY_BRANCH table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT LIBRARY_BRANCH table from library_branch.csv"

# close library_branch.csv file
csvfile.close()


# read book_copies.csv file
csvfile = file('book_copies.csv', 'rb')
reader = csv.reader(csvfile,delimiter='\t')

sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	elif 1 == cnt:
		sql = 'INSERT IGNORE INTO BOOK_COPIES (Book_id, Branch_id, No_of_copies) VALUES ("%s", %d, %d)' % \
		(line[0], int(line[1]), int(line[2]))
	elif cnt > 1:
		sql += ', ("%s", %d, %d)' % (line[0], int(line[1]), int(line[2]))

	cnt += 1
	if cnt > 50000:
		cnt = 1
		try:
			# insert data into BOOK_COPIES table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in BOOK_COPIES table from book_copies.csv"
			# sql = ''
			# break
		sql = ''

if len(sql) != 0:
	try:
		# insert data into BOOK_COPIES table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT BOOK_COPIES table from book_copies.csv"

# close book_copies.csv file
csvfile.close()


# read borrowers.csv file
csvfile = file('borrowers.csv', 'rb')
reader = csv.reader(csvfile,delimiter=',')


sql = ''
cnt = 0
for line in reader:
	if 0 == cnt:	# except first row
		cnt += 1
		continue
	elif 1 == cnt:
			sql = 'INSERT IGNORE INTO BORROWER (Card_no, Fname, Lname, Address, Phone) VALUES (%d, "%s", "%s", "%s", "%s")' % \
			(int(line[0]), line[1], line[2], line[4], line[7])
	elif cnt > 1:
			sql += ', (%d, "%s", "%s", "%s", "%s")' % (int(line[0]), line[1], line[2], line[4], line[7])

	cnt += 1
	if cnt > 20000:
		cnt = 1
		try:
			# insert data into BORROWER table
			c.execute(sql)
		except mysql.IntegrityError:
			print "IntegrityError in BORROWER table from borrowers.csv"
		sql = ''

if len(sql) != 0:
	try:
		# insert data into BORROWER table
		c.execute(sql)
	except mysql.IntegrityError:
		print "IntegrityError in INSERT BORROWER table from borrowers.csv"

# close borrowers.csv file
csvfile.close()







# try:
# 	sql = "INSERT INTO BOOK_AUTHORS (Book_id, Author_name) VALUES ('%s', '%s')" % ('12347585', 'Harry Potter')
# 	c.execute(sql)
# except mysql.IntegrityError:
# 	print "IntegrityError in INSERT BOOK_AUTHORS table"

# # show BOOK table
# sql = "SELECT * FROM BOOK"
# c.execute(sql)
# for data in c.fetchall():
# 	print data
# 	# print data[0]
# 	# print [data[0], data[1]]

print "\r\n\r\nInsert Finished!!!"

