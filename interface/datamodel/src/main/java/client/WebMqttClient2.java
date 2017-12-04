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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import edge.datamodel.aml.model.AMLModel;
import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.aml.model.CAEXFile;
import edge.datamodel.aml.model.Impl.CAEXFileImpl;
import edge.datamodel.api.Representation;

public class WebMqttClient2 {
    public static void main(String[] args) throws InterruptedException {

    	String pubTopic = "bbb/aml";
        
        String subTopic = "aaa/#";
        String content = "Message from MqttPublishSample2";
        int qos = 0;
        // String broker =
        //String broker = "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:5601/ws";
        String broker = "ws://172.17.42.1:15675/ws";
        // "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:1883";
        String clientId = "JavaSample2";
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
            SubscribedMessage subMsg = new SubscribedMessage();
            sampleClient.subscribe(subTopic, subMsg);
            
        	while(true) {
        		Thread.sleep(10000);
        		

//            System.out.println("Publishing topic: " + pubTopic);
            System.out.println("Publishing message: " + content);
            
            //////////////////////////////////////////////////////////////////////////////////////
    		Representation sample_rep = new Representation();
    		
    		// Test Unmarshalling
    		AMLModel testmodel = sample_rep.initialize(null, "../../aml-models/data_modeling.aml");
    		
    		String teststring;
			try {
				teststring = sample_rep.representAmlobjectToAml(testmodel.getRoleClassLib());
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		try {
				teststring = sample_rep.representAmlobjectToAml(testmodel.getSystemUnitClassLib());
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
            List<Reading> readings = new ArrayList<Reading>();
    		Reading test_reading = new Reading("TEMP1", "10");
    		test_reading.setDevice("CNC");
    		test_reading.setPushed(1);
    		
    		test_reading.setId("1");
    		test_reading.setCreated(1);
    		test_reading.setModified(1);
    		test_reading.setOrigin(1);
    		
    		readings.add(test_reading);
    		
    		test_reading = new Reading("TEMP2", "20");
    		test_reading.setDevice("CNC");
    		test_reading.setPushed(2);
    		readings.add(test_reading);
    		
    		test_reading.setId("2");
    		test_reading.setCreated(2);
    		test_reading.setModified(2);
    		test_reading.setOrigin(2);
    		
    		test_reading = new Reading("TEMP3", "30");
    		test_reading.setDevice("CNC");
    		test_reading.setPushed(3);
    		readings.add(test_reading);
    		
    		test_reading.setId("3");
    		test_reading.setCreated(3);
    		test_reading.setModified(3);
    		test_reading.setOrigin(3);
    		
    		Event event = new Event("CNC",readings);
    		event.setPushed(0);
    		
//    		event.setId("");
    		event.setCreated(0);
    		event.setModified(0);
    		event.setOrigin(0);
    		
    		AMLObject sample = null;
    		sample = sample_rep.representDataToAmlobject(event, testmodel);
    		System.out.println("**************************************************");
    		CAEXFile testaml = new CAEXFileImpl();
    		testaml.setInstanceHierarchy(sample.getInstanceHierarchy());
    		testaml.setRoleClassLib(testmodel.getRoleClassLib());
    		testaml.setSystemUnitClassLib(testmodel.getSystemUnitClassLib());
    		
    		String xmlString = null;
			try {
				xmlString = sample_rep.representAmlobjectToAml(testaml);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

            System.out.println("Publishing aml message: ");
            MqttMessage message = new MqttMessage(xmlString.getBytes());
            message.setQos(qos);
            sampleClient.publish(pubTopic, message);
            System.out.println("aml message published");
			
    		System.out.println("**************************************************");
            //////////////////////////////////////////////////////////////////////////////////////
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