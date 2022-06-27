import pymysql

miConexion = pymysql.connect( host='localhost', user= 'root', passwd='', db='nuevadb' )
cur = miConexion.cursor()
cur.execute( "SELECT * FROM usuarios" )
for nombre, apellido in cur.fetchall() :
    print (nombre, apellido)
miConexion.close()
