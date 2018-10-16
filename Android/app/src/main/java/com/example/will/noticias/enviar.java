package com.example.will.noticias;

import java.lang.Thread;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class enviar extends Thread{
    private static final String EXCHANGE_NAME = "topic_logs";

    String Titulo, Conteudo, Host, Rota;

    public enviar(String Titulo, String Conteudo, String Host, String Rota){
        this.Titulo = Titulo;
        this.Conteudo = Conteudo;
        this.Host = Host;
        this.Rota = Rota;
    }

    public void run() {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Host);
            factory.setUsername("admin");
            factory.setPassword("123");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String message = this.Titulo+"|%|"+this.Conteudo;

            channel.basicPublish(EXCHANGE_NAME, this.Rota, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + this.Rota + "':'" + message + "'");

        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception ignore) {}
            }
        }
    }
}
