package Receptor;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsTopic {

  private static final String EXCHANGE_NAME = "topic_logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    String queueName = channel.queueDeclare().getQueue();

    /*if (argv.length < 1) {
      System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
      System.exit(1);
    }*/
    
    channel.queueBind(queueName, EXCHANGE_NAME, "palmeiras");
    channel.queueBind(queueName, EXCHANGE_NAME, "ambos");
    
    /*for (String bindingKey : argv) {
      channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
    }*/

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        //String m = message.replace(" ", "__");
        String []partes = message.split("§",2);
        System.out.println("titulo: "+partes[0]+" \nConteudo: "+partes[1]);
        //System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + "Titulo: "+partes[0] + "Conteudo: "+partes[1]);
      }
    };
    channel.basicConsume(queueName, true, consumer);
  }
}

