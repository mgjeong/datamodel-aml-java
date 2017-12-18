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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edge.datamodel.api.MqttClientCallback;

public class WebMqttClient{
    
	public static void main(String[] args) throws InterruptedException{
		//String broker = "ws://172.17.42.1:15675/ws";
	    // "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:1883";
	    
		//String broker = "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:5601/ws";
		String broker = "ws://172.17.42.1:15675/ws";
		String clientId = "datamodel_sample2";
	    
		String pubTopicEndpoint = "aaa/aaa";
	    String subTopicEndpoint = "bbb/#";
	    
	    int qos = 1;
	    MemoryPersistence persistence = new MemoryPersistence();
	    
	    //Connect to Broker
	    try {
	    	MqttClient sampleClient;
			MqttConnectOptions connOpts = new MqttConnectOptions();
		    
			connOpts.setCleanSession(true);
		    connOpts.setUserName("username");
		    connOpts.setPassword("password".toCharArray());
		    connOpts.setConnectionTimeout(3);
		    System.out.println("Connecting to broker: " + broker);
	        sampleClient = new MqttClient(broker, clientId, persistence);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            
	        sampleClient.setCallback(new MqttClientCallback());
	        	        
	        System.out.println("Connected");
	        
	        //setup topic
	        sampleClient.subscribe(subTopicEndpoint, qos);

        	while(true) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    System.out.println("Subscribing request Endpoint message");
		    
		    String requestEndpointstr;
		    Gson gson = new Gson();
		    
		    JsonObject requestEndpoint = new JsonObject();
		    requestEndpoint.addProperty("name", "ingestionmanager");
		    requestEndpoint.addProperty("appid", "app1");
		    requestEndpoint.addProperty("type", "streaming");
		    requestEndpoint.addProperty("host", "100.0.0.1");
		    requestEndpoint.addProperty("endpoint", "v1/userid/ingestionmanager/appid");
		    requestEndpointstr = gson.toJson(requestEndpoint);
		    
		    MqttMessage message = new MqttMessage(requestEndpointstr.getBytes());
		    message.setQos(qos);
		    sampleClient.publish(pubTopicEndpoint, message);
	        
		    System.out.println("Publishing request Endpoint message");
		    
		    System.out.println("Message published");
        	}
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