/*******************************************************************************
 * Copyright (c) 1999, 2015 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Author:
 * - Originally taken from https://www.eclipse.org/paho/clients/java/
 * - James Sutton - Packaged Example and added WebSockets.
 */
package client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class WebMqttClient {
    public static void main(String[] args) throws InterruptedException {

    	String pubTopic1 = "aaa/aaa";
        String pubTopic2 = "aaa/bbb";
        String pubTopic3 = "aaa/ccc";
                
        String subTopic = "#";
        String content = "Message from MqttPublishSample";
        int qos = 0;
        // String broker =
        //String broker = "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:5601/ws";
        String broker = "ws://172.17.42.1:15675/ws";
        // "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:1883";
        String clientId = "JavaSample1";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId,
                    persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("username");
            connOpts.setPassword("password".toCharArray());
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
        	
        	while(true) {
        		Thread.sleep(10000);
        		
            SubscribedMessage subMsg = new SubscribedMessage();
            sampleClient.subscribe(subTopic, subMsg);
//            System.out.println("Publishing topic: " + pubTopic);
            System.out.println("Publishing message: " + content);
            MqttMessage message1 = new MqttMessage((content + "1").getBytes());
            MqttMessage message2 = new MqttMessage((content + "2").getBytes());
            MqttMessage message3 = new MqttMessage((content + "3").getBytes());
            message1.setQos(qos);
            message2.setQos(qos);
            message3.setQos(qos);
            sampleClient.publish(pubTopic1, message1);
            sampleClient.publish(pubTopic2, message2);
            sampleClient.publish(pubTopic3, message3);
            System.out.println("Message published");
            
            
        	}   
            // sampleClient.disconnect();
            // System.out.println("Disconnected");
            // System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}