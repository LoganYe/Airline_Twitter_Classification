#!flask/bin/python
# -*- coding: utf-8 -*-

if __name__ == '__main__':
	from app import app
	app.run(debug = True)		# for localhost
	#app.run(host='0.0.0.0')	# for all IP
