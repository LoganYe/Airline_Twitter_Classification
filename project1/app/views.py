# -*- coding: utf-8 -*-

from app import app
from app import dbCon
from flask import render_template, request

@app.route('/')
@app.route('/index/')
def index():
	return render_template("index.html", title = 'Home')

@app.route('/search/', methods=['GET'])
def showbook():
	res = dbCon.search_BOOK(request.args)
	return render_template('search.html', title = 'Book Search',book_datas = res)

@app.route('/check_out/', methods=['GET'])
def loan_check_out():
	errStr, res = dbCon.check_out_BOOK(request.args)
	return render_template('check_out.html', title = 'Book Loan', errStr = errStr, book_datas = res)

@app.route('/check_in/', methods=['GET'])
def loan_check_in():
	errStr, res = dbCon.check_in_BOOK(request.args)
	return render_template('check_in.html', title = 'Book Loan', errStr = errStr, book_datas = res)

@app.route('/borrower/', methods=['GET'])
def borrower():
	errStr, res = dbCon.borrower_mgr(request.args)
	return render_template('borrower.html', title = 'Borrower Manager', errStr = errStr, book_datas = res)

@app.route('/fine/', methods=['GET'])
def fine():
	print "\r\naaa\r\n"
	errStr, res = dbCon.fine(request.args)
	return render_template('fine.html', title = 'Fine', errStr = errStr, book_datas = res)
