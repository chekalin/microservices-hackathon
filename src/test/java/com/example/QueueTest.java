package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.junit.Test;

public class QueueTest {
    private static final String QUEUE_NAME = "HELLO_WORLD_QUEUE";

    @Test
    public void testQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        String receivedMessage = new String(delivery.getBody());
        System.out.println(" [x] Received '" + receivedMessage + "'");

        channel.close();
        connection.close();
    }


}
