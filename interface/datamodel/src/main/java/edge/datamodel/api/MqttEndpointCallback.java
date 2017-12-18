package edge.datamodel.api;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.JsonParser;

import client.EndpointCallback;

public class MqttEndpointCallback implements MqttCallback{
	
	EndpointCallback Endpointcb;
	
	public MqttEndpointCallback(EndpointCallback endpointcb) {
		Endpointcb = endpointcb;
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection Lost!");		
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("messageArrived Endpointcb");
		//if(topic.contains("edge/get/response/requestid"
		System.out.println("topic : " + topic);
		if(true) {
			System.out.println("contain topic");
			String jsonString = message.toString();
			System.out.println("message : " + jsonString);
			JsonParser jsonparser = new JsonParser();
			
			String type = jsonparser.parse(jsonString).getAsJsonObject().get("type").getAsString();
			System.out.println("type:" + type);
			String endpoint = jsonparser.parse(jsonString).getAsJsonObject().get("endpoint").getAsString();
			System.out.println("endpoint:" + endpoint);
			
			if(type.equals("streaming")) {
				System.out.println("stream");
				Endpointcb.setStreamEndpoint(endpoint);
			} else if(type.equals("batch")) {
				Endpointcb.setBatchEndpoint(endpoint);
			} else if(type.equals("file")) {
				Endpointcb.setFileEndpoint(endpoint);
			}
		}
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
}
