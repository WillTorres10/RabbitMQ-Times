package valendo;

import com.rabbitmq.client.*;
import java.io.IOException;
import view.noticias;

public class ReceiveLogsTopic extends Thread{

  private static final String EXCHANGE_NAME = "topic_logs";
  private String Host;
  
  public ReceiveLogsTopic(String host){
      this.Host = host;
  }
  
  public void run(){
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(this.Host);
    factory.setUsername("admin");
    factory.setPassword("123");
      try {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "corinthans");
        channel.queueBind(queueName, EXCHANGE_NAME, "ambos");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        noticias not = new noticias();
        not.setVisible(true);
        Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
            String message = new String(body, "UTF-8");
            //String m = message.replace(" ", "__");
            String []partes = message.split("§",2);
            not.setTitulo(partes[0]);
            not.setCont(partes[1]);
            //System.out.println("titulo: "+partes[0]+" \nConteudo: "+partes[1]);
            //System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + "Titulo: "+partes[0] + "Conteudo: "+partes[1]);
          }
        };
        channel.basicConsume(queueName, true, consumer);
    } catch (Exception e) {
          System.out.println(e);
      }
  }
}

