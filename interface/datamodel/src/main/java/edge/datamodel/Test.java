package edge.datamodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edge.datamodel.aml.model.AMLModel;
import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.CAEXFile;
import edge.datamodel.aml.model.InstanceHierarchy;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.SupportedRoleClass;
import edge.datamodel.aml.model.Impl.AMLObjectImpl;
import edge.datamodel.aml.model.Impl.CAEXFileImpl;
import edge.datamodel.api.InterfaceAdapter;
import edge.datamodel.api.Representation;


public class Test {

    public static Event getEdgeXEvent() {

        List<Reading> readings = null;
        readings = new ArrayList<Reading>();

        Random rand = new Random();
        Reading reading1 = new Reading();
        reading1.setName("TestReading1");
        reading1.setValue(rand.nextLong() + "");
        reading1.setCreated(0);
        reading1.setDevice("TestDevice1");
        reading1.setModified(10);
        reading1.setId("id1");
        reading1.setOrigin(15);
        reading1.setPushed(20);

        Reading reading2 = new Reading();
        reading2.setName("TestReading2");
        reading2.setValue(rand.nextLong() + "");
        reading2.setCreated(25);
        reading2.setDevice("TestDevice2");
        reading2.setModified(30);
        reading2.setId("id2");
        reading2.setOrigin(35);
		System.out.println("Event\n");
        reading2.setPushed(30);

        readings.add(reading1);
        readings.add(reading2);

        Event event = new Event("Test", readings);
        event.setCreated(10);
        event.setModified(20);
        event.setId("id");
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
