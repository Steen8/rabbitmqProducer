package com.producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

public class Producer {
    private final static String QUEUE_NAME = "Test queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            while (true) {
                String message = randomMessage();
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(1000);
            }
        }
    }

    private static String randomMessage() {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder outputString = new StringBuilder();
        Random random = new Random();

        while (outputString.length() < 10) {
            outputString.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        outputString.append(" : ").append(LocalDateTime.now());
        return outputString.toString();
    }

}
