package edge.datamodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edge.datamodel.aml.model.AMLModel;
import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.CAEXFile;
import edge.datamodel.aml.model.InstanceHierarchy;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.SupportedRoleClass;
import edge.datamodel.aml.model.Impl.AMLObjectImpl;
import edge.datamodel.aml.model.Impl.CAEXFileImpl;
import edge.datamodel.api.JsonFormatter;
import edge.datamodel.api.Representation;


public class Test {

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

	
	public static void main(String[] args) throws Exception {
		Representation sample_rep = new Representation();
		AMLModel sample_model = sample_rep.initialize();
		
		// Test Unmarshalling
		AMLModel testmodel = sample_rep.initialize(null, "../../aml-models/data_modeling.aml");
		
		String teststring = sample_rep.representAmlobjectToAml(testmodel.getRoleClassLib());
		System.out.println(teststring);
		
		teststring = sample_rep.representAmlobjectToAml(testmodel.getSystemUnitClassLib());
		System.out.println(teststring);
		
		System.out.println("/////////////////////////////////////////////////////////////");
		
		//Make Sample Event
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
		
//		event.setId("");
		event.setCreated(0);
		event.setModified(0);
		event.setOrigin(0);
		
		//Input Value
		//InstanceHierarchy sample = sample_handler.putData(event, sample_model);
		AMLObject sample = null;
		sample = sample_rep.representDataToAmlobject(event, testmodel);
//		sample = sample_rep.representAMLObject(event, sample_model);
//		Event event2 = sample_rep.representCoredata(sample);
				
		//Output Value
		
		//1st Event
		System.out.println("Event\n");
		System.out.println("Event device : " + event.getDevice());
		System.out.println("Event pushed : " + event.getPushed());
		System.out.println("Event id : " + event.getId());
		System.out.println("\n");
		
		for(Reading reading: event.getReadings()) {
			System.out.println("Reading\n");
			System.out.println("Reading device : " + reading.getDevice());
			System.out.println("Reading name : " + reading.getName());
			System.out.println("Reading value : " + reading.getValue());
			System.out.println("Reading pushed : " + reading.getPushed());
			System.out.println("\n");
		}
						
		
		// Parsing AML
//		String xmlString = sample_rep.representAML(sample_model.getRoleClassLib());
//		System.out.println(xmlString);
//		
//		xmlString = sample_rep.representAML(sample_model.getSystemUnitClassLib());
//		System.out.println(xmlString);
//		
//		xmlString = sample_rep.representAML(sample.getInstanceHierarchy());
//		System.out.println(xmlString);

		// Parsing AutomationML Format xml file.
//		CAEXFile testaml = new CAEXFileImpl();
//		testaml.setInstanceHierarchy(sample.getInstanceHierarchy());
//		testaml.setRoleClassLib(sample_model.getRoleClassLib());
//		testaml.setSystemUnitClassLib(sample_model.getSystemUnitClassLib());
//		
//		String xmlString = sample_rep.representAML(testaml);
//		System.out.println(xmlString);
		
		
		// Parsing AutomationML Format xml file using aml file.
		System.out.println("**************************************************");
		CAEXFile testaml = new CAEXFileImpl();
		testaml.setInstanceHierarchy(sample.getInstanceHierarchy());
		testaml.setRoleClassLib(testmodel.getRoleClassLib());
		testaml.setSystemUnitClassLib(testmodel.getSystemUnitClassLib());
		
		String xmlString = sample_rep.representAmlobjectToAml(testaml);
		System.out.println(xmlString);
		System.out.println("**************************************************");
		
		
		// Parsing JSON Format
		
		String json = sample_rep.representAmlobjectToJson(sample);
		System.out.println(json);
				
		AMLObject jsonsample = sample_rep.representJsontoAmlobject(json);
		String jsonresult = sample_rep.representAmlobjectToAml(jsonsample.getInstanceHierarchy());
		System.out.println(jsonresult);
		
		
		JsonParser jsonparser = new JsonParser();
		
		String jsonString = "{\"name\":\"ingestionmanager\",\"appid\":\"datamodel_app1\",\"type\":\"streaming\",\"host\":\"ws://ec2-52-14-109-113.us-east-2.compute.amazonaws.com:5601/ws\",\"endpoint\":\"v1/userid/ingestionmanager/datamodel_app1\"}";
		System.out.println(jsonString);

		
		String type = jsonparser.parse(jsonString).getAsJsonObject().get("type").getAsString();
		System.out.println("type:" + type);
		String endpoint = jsonparser.parse(jsonString).getAsJsonObject().get("endpoint").getAsString();
		System.out.println("endpoint:" + endpoint);
		
		
		
		
		
		
		
	////////////////////////////////////////////////////////////////////////	
//		// Parsing EdgeX
//		System.out.println("Event\n");
//		System.out.println("Event device : " + event2.getDevice());
//		System.out.println("Event pushed : " + event2.getPushed());
//		System.out.println("\n");
//		
//		for(Reading reading: event2.getReadings()) {
//			System.out.println("Reading\n");
//			System.out.println("Reading device : " + reading.getDevice());
//			System.out.println("Reading name : " + reading.getName());
//			System.out.println("Reading value : " + reading.getValue());
//			System.out.println("Reading pushed : " + reading.getPushed());
//			System.out.println("\n");
//		}				
//		System.out.println("///////////////////////////////////////////////////////");
//		
//		
//		// Test Protobuf(AML)
//		
//		byte[] byteAML = sample_rep.serialize(sample);
//		AMLObject objectAML = sample_rep.deserialize(byteAML);
//		xmlString = sample_rep.representAML(objectAML.getInstanceHierarchy());
//		System.out.println(xmlString);
//		
//		
//		// Test Protobuf(EdgeX)
//		Event protoevent = getEdgeXEvent();
//		
//		System.out.println("Event\n");
//		System.out.println("Event device : " + protoevent.getDevice());
//		System.out.println("Event pushed : " + protoevent.getPushed());
//		
//		System.out.println("Event id : " + protoevent.getId());
//		System.out.println("Event created : " + protoevent.getCreated());
//		System.out.println("Event modified : " + protoevent.getModified());
//		System.out.println("Event origin : " + protoevent.getOrigin());
//		
//		System.out.println("\n");
//		
//		for(Reading reading: protoevent.getReadings()) {
//			System.out.println("Reading\n");
//			System.out.println("Reading device : " + reading.getDevice());
//			System.out.println("Reading name : " + reading.getName());
//			System.out.println("Reading value : " + reading.getValue());
//			System.out.println("Reading pushed : " + reading.getPushed());
//			
//			System.out.println("Reading id : " + reading.getId());
//			System.out.println("Reading created : " + reading.getCreated());
//			System.out.println("Reading modified : " + reading.getModified());
//			System.out.println("Reading origin : " + reading.getOrigin());
//			System.out.println("\n");
//		}				
//
//		System.out.println("//////////////////////////////////////////////////////////");
//		
//		byte[] byteEvent = ProtoEventConverter.toProtoBuf(protoevent);
//		Event edgexEvent = ProtoEventConverter.toEdgeXEvent(byteEvent);
//		
//		System.out.println("Event\n");
//		System.out.println("Event device : " + edgexEvent.getDevice());
//		System.out.println("Event pushed : " + edgexEvent.getPushed());
//		
//		System.out.println("Event id : " + protoevent.getId());
//		System.out.println("Event created : " + protoevent.getCreated());
//		System.out.println("Event modified : " + protoevent.getModified());
//		System.out.println("Event origin : " + protoevent.getOrigin());
//		System.out.println("\n");
//		
//		for(Reading reading: edgexEvent.getReadings()) {
//			System.out.println("Reading\n");
//			System.out.println("Reading device : " + reading.getDevice());
//			System.out.println("Reading name : " + reading.getName());
//			System.out.println("Reading value : " + reading.getValue());
//			System.out.println("Reading pushed : " + reading.getPushed());
//			
//			System.out.println("Reading id : " + reading.getId());
//			System.out.println("Reading created : " + reading.getCreated());
//			System.out.println("Reading modified : " + reading.getModified());
//			System.out.println("Reading origin : " + reading.getOrigin());
//			System.out.println("\n");
//		}		
	}
}
