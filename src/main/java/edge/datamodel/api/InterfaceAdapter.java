package edge.datamodel.api;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.EndpointCallback;


public class InterfaceAdapter{
	
	//temp///////////////////////////////////////////////////////////////////////////////////
	String pubTopicEndpoint = "v1/userid/cloudcontroller/sessionid/service/get/request/requestid";
    String subTopicEndpoint = "v1/userid/cloudcontroller/sessionid/service/get/response/requestid";
	//////////////////////////////////////////////////////////////////////////////////////////////
    
	String Broker;
	String ClientId;
	int Qos;
	MemoryPersistence Persistence;
	MqttConnectOptions ConnOpts;
	
	String Username;
	String Password;
	
	MqttClient MqttClient;
	EndpointCallback endpointcb;
	
	boolean sendflag = true;
	
	public boolean initInterfaceAdapter(String broker, String clientId, EndpointCallback cb) {
		this.Broker = broker;
		this.ClientId = clientId;
		this.Persistence = new MemoryPersistence();
		this.Qos = 0;
		
		this.endpointcb = cb;
		
		this.Username = "username";
		this.Password = "password";
		
		if(this.setNewMqttClient()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean connectToBroker() {
		this.ConnOpts = new MqttConnectOptions();
		this.ConnOpts.setCleanSession(true);
	    this.ConnOpts.setUserName(this.Username);
	    this.ConnOpts.setPassword(this.Password.toCharArray());
	    this.ConnOpts.setConnectionTimeout(3);
		
	    //this.MqttClient.setCallback(new sampleMqttCallback(this.endpointcb));
	    this.MqttClient.setCallback(new MqttEndpointCallback(this.endpointcb));
	    try {
			this.MqttClient.connect(this.ConnOpts);
		    System.out.println("Connected");
		    return true;
		} catch (MqttSecurityException e) {
			System.out.println("Connect security error :  " + e);
			//e.printStackTrace();
			return false;
		} catch (MqttException e) {
			System.out.println("Connect error :  " + e);
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean getEndpoint(String dest, String appId, String type) {
		Gson gson = new Gson();
		
		JsonObject tempobject = new JsonObject();
		tempobject.addProperty("name", dest);
		tempobject.addProperty("appid", appId);
		tempobject.addProperty("type", type);
		String requestEndpoint = gson.toJson(tempobject);
				
		if(this.subEndpointToBroker(this.subTopicEndpoint)) {
			System.out.println("sub tocic : " + subTopicEndpoint);
			if(this.pubEndpointToBroker(this.pubTopicEndpoint, requestEndpoint)) {
				System.out.println("pub tocic : " + pubTopicEndpoint);
				return true;
			}
		}	
		
		System.out.println("Failed get Endpoint");
		return false;
	}
	
	public boolean pubEndpointToBroker(String topic, String message){
		MqttMessage mqmessage = new MqttMessage(message.getBytes());
		mqmessage.setQos(this.Qos);
		try {
			MqttClient.publish(topic, mqmessage);
			System.out.println("Publishing topic : " + topic);
			return true;
		} catch (MqttPersistenceException e) {
			System.out.println("Publish Persistence error : " + e);
			//e.printStackTrace();
			return false;
		} catch (MqttException e) {
			System.out.println("Publish error : " + e);
			//e.printStackTrace();
			return false;
		}
	}

	public boolean subEndpointToBroker(String topic) {
		try {
			MqttClient.subscribe(topic);
			System.out.println("Subscribing topic : " + topic);
			return true;
		} catch (MqttException e) {
			System.out.println("Subscrive error : " + e);
			//e.printStackTrace();
			return false;
		}
	}
	
	
	public void sendMessageToBroker(String topic, String message){
		if(!sendflag) {
			while(true){
				System.out.println("err");
			}
		}
		sendflag = false;
		MqttMessage mqmessage = new MqttMessage(message.getBytes());
		mqmessage.setQos(this.Qos);
		try {
			MqttClient.publish(topic, mqmessage);
			System.out.println("Publishing topic : " + topic);
		} catch (MqttPersistenceException e) {
			System.out.println("Publish Persistence error : " + e);
			//e.printStackTrace();
		} catch (MqttException e) {
			System.out.println("Publish error : " + e);
			//e.printStackTrace();
		}
		sendflag = true;
	}

	public void subMessageToBroker(String topic) {
		try {
			MqttClient.setCallback(new MqttClientCallback());
			MqttClient.subscribe(topic);
			System.out.println("Subscribing topic : " + topic);
		} catch (MqttException e) {
			System.out.println("Subscrive error : " + e);
			//e.printStackTrace();
		}
	}
	
	// getter, setter
	public void setUserInfo(String username, String password) {
		this.Username = username;
		this.Password = password;
	}
	
	public boolean setNewMqttClient() {
		try {
			this.MqttClient = new MqttClient(this.Broker, this.ClientId, this.Persistence);
			return true;
		} catch (MqttException e) {
			System.out.println("Create new MqttCliet error : " + e );
			//e.printStackTrace();
			return false;
		}
	}
	
	public MqttClient getNewMqttClient() {
		return MqttClient;
	}
	
	public int getQos() {
		return Qos;
	}

	public void setQos(int qos) {
		this.Qos = qos;
	}
	
	public String getBroker() {
		return Broker;
	}
	
	public void setBroker(String broker) {
		this.Broker = broker;
	}
	
	public String getClientId() {
		return ClientId;
	}
	
	public void setClientId(String clientId) {
		this.ClientId = clientId;
	}
}
