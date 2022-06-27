#!/usr/bin/env python
import pika, sys, os
import mysql.connector

def main():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='primer_envio')

    def callback(ch, method, properties, body):
        msg = body.decode('utf-8')
        id, nombre, correo, clave, dni, telefono, amigos = msg.split(",")
        print("[x] Received ID: {}  NOMBRE: {}  DNI: {}  AMIGOS: {}".format(id, nombre, dni, amigos))

        aprobado = verificar(dni, amigos)

        if aprobado:
           enviar(msg)

    channel.basic_consume(queue='primer_envio', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()


def verificar(dni, amigos):

    aprobado = 0

    cnx = mysql.connector.connect(host='localhost', user= 'root', password='aurorabel', database='DNI' )
    cursor1 = cnx.cursor()

    cursor1.execute("SELECT * FROM DNI WHERE dni = " + dni)
    resultado = cursor1.fetchall()
    print("DATOS ENCONTRADOS:", resultado)

    if len(resultado) != 0:
        cursor2 = cnx.cursor()

        arramigos = amigos.split()
        print(arramigos)
        for amigo in arramigos:
            cursor2.execute("SELECT * FROM DNI WHERE dni =" + amigo)
            amigoresult = cursor2.fetchall()
            
            if len(amigoresult) == 0:
                print("El amigo con dni {} no existe".format(amigo))
                break
        
        if len(amigoresult) != 0:
            aprobado = 1
            print("Todos los amigos existen")
        
        cursor2.close()
    
    else: print("El usuario ingresado no existe")
    cursor1.close()
    cnx.close()

    return aprobado

def enviar(msg):
    connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='segundo_envio')

    channel.basic_publish(exchange='', routing_key='segundo_envio', body = msg.encode('utf-8'))
    print(" [x] Sent %r" % msg)
    connection.close()

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)

# // id: 1 (definido por ud)
#   // nombre: fulano
#   // correo: fulano@memail.com
#   // clave: 1234
#   // dni: 20453629
#   // telefono: 89674539
#   // amigo(s) frecuente(s): rosa juan lucas (pueden ser cero o varios amigos, sus ids se
#   // encuentren en la misma BD1)
# "1,fulano,fulano@gmail.com,1234,312321,313123213,piero juan"