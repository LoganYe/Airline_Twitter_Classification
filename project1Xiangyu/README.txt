before run install_flask.sh you should install python-2.7, python-dev, virtualenv(python-virtualenv), mysql-server, mysql-client, libmysqld-dev
python-dev & libmysqld-dev is for flask-mysql
run the install_flask.sh to install all of them

in ./db_data/
first create database
or log in to mysql to create the database using
source create-library-data-for-MySQL.sql

then import data use ./db_data/insert.py

run server:
use ./run.py

in a web browser, type localhost:5000