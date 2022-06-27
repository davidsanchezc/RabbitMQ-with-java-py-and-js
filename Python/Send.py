#!/usr/bin/env python
import pika

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

channel.queue_declare(queue='segundo_envio')

channel.basic_publish(exchange='', routing_key='segundo_envio', body='Hello World sent from Python')
print(" [x] Sent 'Hello World'")
connection.close()
