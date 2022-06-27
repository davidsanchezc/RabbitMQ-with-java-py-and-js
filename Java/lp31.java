package Java;

import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class lp31 {
    private final static String QUEUE_NAME = "segundo_envio";

    public static void main(String[] argv) throws Exception {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String nombres[] = { "Paul", "John", "George", "Ringo", "Faraon", "Billie", "Dan", "Daniel", "Ben", "Chris",
                    "Jon", "Guy", "Will", "Tom", "Colin", "Jonny", "Ed", "Phil", "David", "Roger", "Syd", "Richard",
                    "Robert", "Jimmy", "Adam", "Tony", "Ozzy", "Bill", "Avril", "Monserrat", "Natalia", "Julieta",
                    "Gloria", "Shakira", "Laura", "Alejandra", "Madonna", "Taylor", "Freddy", "Brian", "Jesse", "Joy",
                    "Camila", "Hanna", "Ashley", "Jose", "Raul", "Yuri" };
            String servicios[] = { "gmail", "hotmail", "outlook", "yahoo" };
            int DNIs[] = new int[1001];
            DNIs[0] = ((int) (Math.random() * 90000000) + 10000000);
            for (int i = 1; i <= 1; i++) {

                String id = Integer.toString(i);
                String nombre = nombres[(int) (Math.random() * (nombres.length))];
                String clave = Integer.toString((int) (Math.random() * 9000) + 1000);
                String correo = nombre + clave + "@" + servicios[(int) (Math.random() * servicios.length)]
                        + ".com";
                int DNI = ((int) (Math.random() * 90000000) + 10000000);
                for (int j = 0; j < i; j++) {
                    if (DNI == DNIs[j]) {
                        DNI = ((int) (Math.random() * 90000000) + 10000000);
                        j = 0;
                    }
                }
                DNIs[i] = DNI;
                String dni = Integer.toString(DNIs[i]);
                String telefono = Integer.toString((int) (Math.random() * 900000000) + 100000000);

                String amigosfrecuentes = Integer.toString((int) (Math.random() * 90000000) + 10000000) + " "
                        + Integer.toString((int) (Math.random() * 90000000) + 10000000);

                String message = id + "," + nombre + "," + correo + "," + clave + "," + dni + "," + telefono + ","
                        + amigosfrecuentes;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }

}