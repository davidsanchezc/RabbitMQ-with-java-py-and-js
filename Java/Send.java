package Java;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        System.out.println("SIUUUU");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
              channel.queueDeclare(QUEUE_NAME, false, false, false, null);
              String message = "Hello World! sent from Java";
              channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
              System.out.println(" [x] Sent '" + message + "'");
              
            
        }
        
    }
  }

  // id: 1 (definido por ud)
  // nombre: fulano
  // correo: fulano@memail.com
  // clave: 1234
  // dni: 20453629
  // telefono: 89674539
  // amigo(s) frecuente(s): rosa juan lucas (pueden ser cero o varios amigos, sus ids se
  // encuentren en la misma BD1)