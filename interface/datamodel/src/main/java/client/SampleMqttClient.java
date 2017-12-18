package client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import edge.datamodel.aml.model.AMLModel;
import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.aml.model.CAEXFile;
import edge.datamodel.aml.model.Impl.CAEXFileImpl;
import edge.datamodel.api.InterfaceAdapter;
import edge.datamodel.api.Representation;

public class SampleMqttClient {
	static String StreamEndpoint; 
	static String BatchEndpoint;
	static String FileEndpoint;

	public static Event getEdgeXEvent() {

        List<Reading> readings = null;
        readings = new ArrayList<Reading>();
        Random rand = new Random();
        Reading reading1 = new Reading();
        reading1.setName("Robot_Model");
        reading1.setValue("SR-P7-R970");
        reading1.setCreated(0);
        reading1.setDevice("Robot1");
        reading1.setModified(0);
        reading1.setId(rand.nextLong() + "");
        reading1.setOrigin(new Timestamp(System.currentTimeMillis()).getTime());
        reading1.setPushed(20);

        Reading reading2 = new Reading();
        reading2.setName("Robot_SW_Version");
        reading2.setValue("0.0.1");
        reading2.setCreated(0);
        reading2.setDevice("Robot1");
        reading2.setModified(0);
        reading2.setId(rand.nextLong() + "");
        reading2.setOrigin(new Timestamp(System.currentTimeMillis()).getTime());
        reading2.setPushed(30);

        Reading reading3 = new Reading();
        reading3.setName("Robot_Servo_Status");
        reading3.setValue("5");
        reading3.setCreated(0);
        reading3.setDevice("Robot1");
        reading3.setModified(0);
        reading3.setId(rand.nextLong() + "");
        reading3.setOrigin(new Timestamp(System.currentTimeMillis()).getTime());
        reading3.setPushed(30);
        
        Reading reading4 = new Reading();
        reading4.setName("Robot_Status");
        reading4.setValue("2");
        reading4.setCreated(0);
        reading4.setDevice("Robot1");
        reading4.setModified(0);
        reading4.setId(rand.nextLong() + "");
        reading4.setOrigin(new Timestamp(System.currentTimeMillis()).getTime());
        reading4.setPushed(30);
        
        Reading reading5 = new Reading();
        reading5.setName("Robot_Log");
        reading5.setValue("[2,Reconfigure Request]");
        reading5.setCreated(0);
        reading5.setDevice("Robot1");
        reading5.setModified(0);
        reading5.setId(rand.nextLong() + "");
        reading5.setOrigin(new Timestamp(System.currentTimeMillis()).getTime());
        reading5.setPushed(30);
        
        readings.add(reading1);
        readings.add(reading2);
        readings.add(reading3);
        readings.add(reading4);
        readings.add(reading5);
        
        Event event = new Event("Robot1", readings);
        event.setCreated(0);
        event.setModified(0);
        event.setId(rand.nextLong() + "");
        event.markPushed(new Timestamp(System.currentTimeMillis()).getTime());
        event.setOrigin(new Timestamp(System.currentTimeMillis()).getTime());

        return event;
    }
	
	public class endpointCallback implements EndpointCallback {
		@Override
		public void setStreamEndpoint(String endpoint) {
			System.out.println("Get Stream Endpoint");
			System.out.println("Endpoint : " + endpoint);
			StreamEndpoint = endpoint;
		}

		@Override
		public void setBatchEndpoint(String endpoint) {
			System.out.println("Get Batch Endpoint");
			System.out.println("Endpoint : " + endpoint);
			BatchEndpoint = endpoint;
		}

		@Override
		public void setFileEndpoint(String endpoint) {
			System.out.println("Get File Endpoint");
			System.out.println("Endpoint : " + endpoint);
			FileEndpoint = endpoint;
		}
	}
	
	private static EndpointCallback endpointCallback;
	
	private static void callback() {
		endpointCallback = new EndpointCallback() {

			@Override
			public void setStreamEndpoint(String endpoint) {
				StreamEndpoint = endpoint;
			}

			@Override
			public void setBatchEndpoint(String endpoint) {
				BatchEndpoint = endpoint;
			}

			@Override
			public void setFileEndpoint(String endpoint) {
				FileEndpoint = endpoint;
			}
		};
	}	
	
	public static void main(String[] args) {
		String Broker = "ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:5601/ws";
		//String Broker = "ws://172.17.42.1:15675/ws";
		String ClientId = "datamodel_sample1";
		callback();
		
		InterfaceAdapter sampleAdapter = new InterfaceAdapter();
		if(!sampleAdapter.initInterfaceAdapter(Broker, ClientId, endpointCallback)) {
			System.out.println("Failed to init InterfaceAdapter");
		}
		
		if(!sampleAdapter.connectToBroker()) {
			System.out.println("Failed to Connect to Broker");
		}
		
		String Dest = "ingestionmanager";
		String AppId = "datamodel_app1";
		String Type = "streaming";
		
		if(!sampleAdapter.getEndpoint(Dest, AppId, Type)) {
			System.out.println("Failed to Get Endpoint");
		}
		
		// Test Unmarshalling////////////////////////////////////////////////////////////
		Representation sample_rep = new Representation();
		
		AMLModel testmodel = sample_rep.initialize(null, "../../aml-models/data_modeling.aml");
		
		Event event = getEdgeXEvent();
		
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
		///////////////////////////////////////////////////////////////////////////
		
		//send message to StreamEndpoint
		//sampleAdapter.pubMessageToBroker(StreamEndpoint, xmlString);
		
		for(int i = 0; i<20;){
			try { 
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(StreamEndpoint == null) {
				System.out.println("Do not have Streampoint");
			} else {
				i++;
				long now = System.currentTimeMillis();
				System.out.println("Receive Streampoint : " + i);
				System.out.println("timestamp : " + now);
				sampleAdapter.sendMessageToBroker(StreamEndpoint, xmlString);
				
			}
		}
	}
}
