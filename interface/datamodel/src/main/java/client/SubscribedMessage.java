package client;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.api.Representation;

public class SubscribedMessage implements IMqttMessageListener {

    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	
    	if(topic.equals("bbb/aml")) {
    		
	        System.out.println("subcribed topic : " + topic);
	        
	    	System.out.println("aml**************************************************************************");
	    	String amlmessage = message.toString();
	    	
	    	Representation amlrep = new Representation();
	    	AMLObject amlobject = amlrep.representAmlToAmlobject(amlmessage);
	    	
	    	Event edgexEvent = amlrep.representAmlobjectToData(amlobject);
	    	        	
			System.out.println("Event\n");
			System.out.println("Event device : " + edgexEvent.getDevice());
			System.out.println("Event pushed : " + edgexEvent.getPushed());
			
			System.out.println("Event id : " + edgexEvent.getId());
			System.out.println("Event created : " + edgexEvent.getCreated());
			System.out.println("Event modified : " + edgexEvent.getModified());
			System.out.println("Event origin : " + edgexEvent.getOrigin());
			System.out.println("\n");
			
			for(Reading reading: edgexEvent.getReadings()) {
				System.out.println("Data\n");
				System.out.println("Data device : " + reading.getDevice());
				System.out.println("Data name : " + reading.getName());
				System.out.println("Data value : " + reading.getValue());
				System.out.println("Data pushed : " + reading.getPushed());
				
				System.out.println("Data id : " + reading.getId());
				System.out.println("Data created : " + reading.getCreated());
				System.out.println("Data modified : " + reading.getModified());
				System.out.println("Data origin : " + reading.getOrigin());
				System.out.println("\n");
			}
	    	System.out.println("aml**************************************************************************");        
    	}
    	else {
   		
            System.out.println("subcribed topic : " + topic);
            System.out.println("subcribed Message : " + message);
    	}
    }
}