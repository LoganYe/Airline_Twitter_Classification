#!../flask/bin/python
# -*- coding: utf-8 -*-
# just test
import datetime
import time

# s = "david beckham richard"
# s = "david"
# print s.split(' ')

# authors = s.split(' ')
# gen_str = ''
# cnt = 0
# for author_str in authors:
# 	if 0 == cnt:
# 		gen_str = "BOOK_AUTHORS.Author_name like '%%%s%%'" % author_str
# 	else:
# 		gen_str += " and BOOK_AUTHORS.Author_name like '%%%s%%'" % author_str
# 	cnt += 1
# sql = "select BOOK.Book_id, BOOK.Title, BOOK_AUTHORS.Author_name from BOOK_AUTHORS inner join BOOK on %s and BOOK.Book_id = BOOK_AUTHORS.Book_id order by BOOK_AUTHORS.Book_id;" % gen_str
# print sql

a = "a"
b = "b"
if "" != a and "b" == b:
	print 'not null'
else:
	print 'null'

a = datetime.date.today()
print a

d = time.strptime("2015-11-11",'%Y-%m-%d')
b = datetime.date(*d[:3])
print b
delta = b - a
print delta.days
if b > a:
	print "bigger"
elif b<=a:
	print "little"