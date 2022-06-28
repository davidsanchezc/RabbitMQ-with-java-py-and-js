#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

inicio = []
final = []
tiempo = []

amqp.connect('amqp://localhost', function(error0, connection) {
    if (error0) {
        throw error0;
    }
    connection.createChannel(function(error1, channel) {
        if (error1) {
            throw error1;
        }
        
        var queue = 'segundo_envio';
        
        channel.assertQueue(queue, {
            durable: false
        });

        console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", queue);
        
        const { Client } = require('pg')
        const connectionData = {
            user: 'postgres',
            host: 'localhost',
            database: 'ventas',
            password: 'aurorabel',
            port: 5432,
            max: 25,
            statement_timeout: 10000,
            query_timeout: 10000,
            idleTimeoutMillis: 30000,
            connectionTimeoutMillis: 6000
            // keepAlive: true
        }

        channel.consume(queue, function(msg) {
            inicio.push(Date.now());
            var mensaje = msg.content.toString();
            var arr = mensaje.split(',');
            console.log(`[x] ID: ${arr[0]} Nombre: ${arr[1]}`);
            var sql = `INSERT INTO usuarios (id, nombre, correo, clave, dni, telefono, amigos_frecuentes) VALUES
            ('${arr[0]}','${arr[1]}','${arr[2]}','${arr[3]}','${arr[4]}','${arr[5]}','${arr[6]}')`
            const client = new Client(connectionData)
            client.connect(err => {
              if (err) {
                  console.error('connection error', err.stack)
                } else {
                    console.log('connected')
                    client.query(sql)
                    .then(response => {
                        console.log(response.rows)
                        final.push(Date.now())
                        // console.log(inicio)
                        // console.log(final)
                        tiempo.push(final[0] - inicio[0])
                        inicio.shift()
                        final.shift()
                        // console.log(tiempo)
                        let total = tiempo.reduce((a, b) => a + b, 0);
                        console.log("Tiempo de ejecucion: " + total);
                        client.end()
                    })
                    .catch(err => {
                        console.log("query error: " + err.message)
                        client.end()
                    })
                }
            });
            
        },
        {
            noAck: true
        });
    });
});

