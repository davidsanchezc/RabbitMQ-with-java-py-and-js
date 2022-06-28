package Java;

import java.util.Scanner;
import java.lang.System;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class lp3 {
    private final static String QUEUE_NAME = "primer_envio";

    public static void main(String[] argv) throws Exception {

        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        long startTime = 0;
        long stopTime = 0;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("davidsanchezc");
        factory.setPassword("davidsanchezc");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            while (flag) {
                System.out.print("id: ");
                String id = sc.nextLine();
                System.out.print("nombre: ");
                String nombre = sc.nextLine();
                System.out.print("correo: ");
                String correo = sc.nextLine();
                System.out.print("clave: ");
                String clave = sc.nextLine();
                System.out.print("dni: ");
                String dni = sc.nextLine();
                System.out.print("telefono: ");
                String telefono = sc.nextLine();
                System.out.print("amigo(s) frecuente(s): ");
                String amigosfrecuentes = sc.nextLine();

                startTime = System.currentTimeMillis();
                String message = id + "," + nombre + "," + correo + "," + clave + "," + dni + "," + telefono + ","
                        + amigosfrecuentes;
                // String message = "Hello World! sent from Java";

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                stopTime = System.currentTimeMillis();
                System.out.println("Tiempo transcurrido " + (stopTime - startTime) + " milisegundos.");

                System.out.print("Desea realizar un nuevo registro?(Y/N): ");
                String f = sc.nextLine();
                if (f.equals("N") || f.equals("n")) {
                    flag = false;
                }
            }
        }
    }
}
