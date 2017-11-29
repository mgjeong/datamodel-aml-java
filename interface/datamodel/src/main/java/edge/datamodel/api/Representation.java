package edge.datamodel.api;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import edge.datamodel.aml.model.AMLModel;
import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.CAEXFile;
import edge.datamodel.aml.model.InstanceHierarchy;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.RoleClass;
import edge.datamodel.aml.model.RoleClassLib;
import edge.datamodel.aml.model.SystemUnitClass;
import edge.datamodel.aml.model.SystemUnitClassLib;
import edge.datamodel.aml.model.Impl.AMLModelImpl;
import edge.datamodel.aml.model.Impl.AMLObjectImpl;
import edge.datamodel.aml.model.Impl.AttributeImpl;
import edge.datamodel.aml.model.Impl.CAEXFileImpl;
import edge.datamodel.aml.model.Impl.InstanceHierarchyImpl;
import edge.datamodel.aml.model.Impl.RoleClassLibImpl;
import edge.datamodel.aml.model.Impl.SystemUnitClassLibImpl;
import edge.datamodel.protobuf.aml.ProtoAMLConverter;



/**
 * represents the TranslateHandler It provides API to convert AutomationML data to edgeCoredata data 
 * and configurate format 
 * 
 */

public class Representation {	
	/**
	 * @fn AMLModel initialize()
	 * @brief Initialize AutomationML datamodel to using translate job. This API should be called first, before usnig Translate API
	 * @return AMLModel	AutomationML data model
	 */
	public AMLModel initialize() {
		AMLModel model = new AMLModelImpl();
		configureDataModel(model);
		return model;
	}
	/**
	 * @fn AMLModel initialize(String name)
	 * @brief Initialize AutomationML datamodel set AMLModel's name and set to using translate job. This API should be called first, before usnig Translate API
	 * @return AMLModel	AutomationML data model
	 */
	public AMLModel initialize(String name) {
		AMLModel model = new AMLModelImpl(name);
		configureDataModel(model);
		return model;
	}

	public AMLModel initialize(String name, String path) {
		AMLModel model = new AMLModelImpl(name);
		try {
			configureDataModel(model, path);
		} catch (JAXBException e) {
			System.out.println("Failed Unmarshal");
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * @fn AMLObject representAMLObject(Event data, AMLModel model)
	 * @brief Represents edgeCoredata Event to AutomationML data object
	 * @param [in] event	edgeCoredata Event
	 * @param [in] model	AutomationML data object 
	 * @return AMLObject AutomationML data
	 */
	public AMLObject representAMLObject(Event data, AMLModel model) {
		AMLObject object = new AMLObjectImpl();
		
		InstanceHierarchy ih = new InstanceHierarchyImpl("Edge_CoreData");
		
		putEventData(data, model.getSystemUnitClassLib(), ih);
		putReadingData(data.getReadings(), model.getSystemUnitClassLib(), ih);
		
		object.setInstanceHierarchy(ih);
		
		return object; 
	}
	
	public AMLObject representCoreObject(String xmldata) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CAEXFileImpl.class);
        
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmldata);
        CAEXFile caexfile = (CAEXFile)jaxbUnmarshaller.unmarshal(reader);
        
        AMLObject object = new AMLObjectImpl();
        object.setInstanceHierarchy(caexfile.getInstanceHierarchy());
        
        return object;
	}
	
	/**
	 * @fn String representAML(Object object)
	 * @brief Represents AutomationML data to XML format String
	 * @param [in] object	AutomationML object
	 * @return String
	 */
    public String representAML(Object object) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CAEXFileImpl.class, InstanceHierarchyImpl.class, RoleClassLibImpl.class, SystemUnitClassLibImpl.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(object, sw);

        String ret= sw.toString();
        return ret;
    }

	/**
	 * @fn Event representCoredata(AMLObject object)
	 * @brief Represents AutomationML data object to edgeCoredata Event data
	 * @param [in] object	AutomationML data object
	 * @return Event	edgeCoredata Event data
	 */
	public Event representCoredata(AMLObject object) {
		InternalElement ie = object.getInstanceHierarchy().searchInternalElement("Event");
		
		Event data = getEventData(ie);

		List<Reading> readings = getReadingsData(ie.getInternalElement());
		
		data.setReadings(readings);
		
		return data;
	}	

	/**
	 * @fn byte[] serialize(AMLObject object)
	 * @brief Serialize AutomationML data object to Protobuf AutomationML byte array
	 * @param [in] object	AutomationML data object
	 * @return byte[]	Protobuf AutomationML byte array
	 */
	public byte[] serialize(AMLObject object) {
		InstanceHierarchy ih = object.getInstanceHierarchy();
		byte[] byteAML = ProtoAMLConverter.toProtoBuf(ih);
		
		return byteAML;
	}
	
	/**
	 * @fn AMLObject deserialize(byte[] byteAML)
	 * @brief Deserialize Protobuf AutomationML byte array to AutomationML data object
	 * @param [in] byteAML	Protobuf AutomationML byte array
	 * @return AMLObject	AutomationML data object
	 */
	public AMLObject deserialize(byte[] byteAML) {
		AMLObject object = new AMLObjectImpl();
		
		InstanceHierarchy ih = ProtoAMLConverter.toAMLIh(byteAML);
		object.setInstanceHierarchy(ih);
		
		return object;
	}
	
	
	/**
	 * @fn void putReadingData(List<Reading> readings, SystemUnitClassLib suclib, InstanceHierarchy ih)
	 * @brief Put edgeCoredata Reading data to AutomationML Class
	 * @param [in] readings	List of edgeCoredata Reading
	 * @param [in] suclib	AutomationML SystemUnitClassLib
	 * @param [out] ih	AutomationML InstanceHierarchy
	 * @return void
	 */
	private void putReadingData(List<Reading> readings, SystemUnitClassLib suclib, InstanceHierarchy ih) {
		if(suclib.searchSystemUnitClass("Data") == null) {
			System.out.println("SystemUnitClass is null\n");
			return;
		}
		if(readings == null) {
			System.out.println("Readings is null\n");
			return;
		}
		
		InternalElement event = ih.searchInternalElement("Event");
		if(event == null) {
			System.out.println("Event is null\n");
			return;
		}
		
		InternalElement ie = null;
		
		for(Reading data: readings) {
			ie = event.addSubInternalElement("Data",suclib,"Data");
			ie.setAttributeValue("device", data.getDevice());
			ie.setAttributeValue("name", data.getName());
			ie.setAttributeValue("value", data.getValue());
			ie.setAttributeValue("pushed", Long.toString(data.getPushed()));
			
			ie.setAttributeValue("id", data.getId());
			ie.setAttributeValue("created", Long.toString(data.getCreated()));
			ie.setAttributeValue("modified", Long.toString(data.getModified()));
			ie.setAttributeValue("origin", Long.toString(data.getOrigin()));
		}
		
	}
	
	/**
	 * @fn void putEventData(Event event, SystemUnitClassLib suclib, InstanceHierarchy ih)
	 * @brief Put edgeCoredata Event data to AutomationML Class
	 * @param [in] event	edgeCoredata Event
	 * @param [in] suclib	AutomationML SystemUnitClassLib
	 * @param [out] ih	AutomationML InstanceHierarchy
	 * @return void
	 */
	private void putEventData(Event event, SystemUnitClassLib suclib, InstanceHierarchy ih) {
		if(suclib.searchSystemUnitClass("Event") == null) {
			System.out.println("SystemUnitClass is null\n");
			return;
		}
		if(event == null) {
			System.out.println("Event is null\n");
			
			return;
		}
		
		ih.addInternalElement("Event", suclib, "Event");
		InternalElement ie = ih.searchInternalElement("Event");
		
		ie.setAttributeValue("device", event.getDevice());
		ie.setAttributeValue("pushed", Long.toString(event.getPushed()));
		
		ie.setAttributeValue("id", event.getId());
		ie.setAttributeValue("created", Long.toString(event.getCreated()));
		ie.setAttributeValue("modified", Long.toString(event.getModified()));
		ie.setAttributeValue("origin", Long.toString(event.getOrigin()));
	}
	
	/**
	 * @fn Event getEventData(InternalElement ie)
	 * @brief Get edgeCoredata Event data from AutomationML InternalElement
	 * @param [in] ie	AutomationML InternalElement
	 * @return Event edgeCoredata Event data
	 */
	private Event getEventData(InternalElement ie) {
		Event event = new Event(null);
		
		if(ie == null) {
			System.out.println("InternalElement is null\n");
			return null;
		}
		
		event.setDevice(ie.getAttributeValue("device"));
		event.setPushed(Long.parseLong(ie.getAttributeValue("pushed")));
		
		event.setId(ie.getAttributeValue("id"));
		event.setCreated(Long.parseLong(ie.getAttributeValue("created")));
		event.setModified(Long.parseLong(ie.getAttributeValue("modified")));
		event.setOrigin(Long.parseLong(ie.getAttributeValue("origin")));
		
		return event;
	}
	
	/**
	 * @fn List<Reading> getReadingsData(List<InternalElement> list)
	 * @brief Get edgeCoredata List of Reading data from List of AutomationML InternalElement
	 * @param [in] list	List of AutomationML InternalElement
	 * @return List<Reading> List of edgeCoredata Reading Data
	 */
	private List<Reading> getReadingsData(List<InternalElement> list) {
		List<Reading> readings = new ArrayList<Reading>();
		if(list == null) {
			System.out.println("InternalElement list is null\n");
			return null;
		}
		
		for(InternalElement ie: list) {
			if("Data".equals(ie.getName()) == true) {
				Reading reading = new Reading(null);
				reading.setName(ie.getAttributeValue("name"));
				reading.setDevice(ie.getAttributeValue("device"));
				reading.setValue(ie.getAttributeValue("value"));
				reading.setPushed(Long.parseLong(ie.getAttributeValue("pushed")));
				
				reading.setId(ie.getAttributeValue("id"));
				reading.setCreated(Long.parseLong(ie.getAttributeValue("created")));
				reading.setModified(Long.parseLong(ie.getAttributeValue("modified")));
				reading.setOrigin(Long.parseLong(ie.getAttributeValue("origin")));
				
				readings.add(reading);
			}
		}
		
		return readings;
	}
	


//	
//	sample.addInternalElement("CNC1", sample_suclib, "Event");
//	//sample.searchInternalElement("CNC1").addSubInternalElement("TEMP", sample_suclib.searchSystemUnitClass("Data"));
//	sample.searchInternalElement("CNC1").addSubInternalElement("TEMP", sample_suclib, "Data");
//	
//	rc = sample_rclib.searchRoleClass("ValueDescriptor");
//	
//	sample.searchInternalElement("CNC1").searchInternalElement("TEMP").setRolerequirements(sample_rclib.getName() + "/" + rc.getName());
//	
	//TODO: Change the structure to using XML file
	/**
	 * @fn void configurateDataModel(AMLModel model)
	 * @brief Configurate AutomationML data Model
	 * @param [in] model AutomationML data model
	 * @return void
	 * @throws JAXBException 
	 */
	public void configureDataModel(AMLModel model, String path) throws JAXBException {
		File amlFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(CAEXFileImpl.class);
        
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        CAEXFile caexfile = (CAEXFile)jaxbUnmarshaller.unmarshal(amlFile);
        
		model.setRoleClassLib(caexfile.getRoleClassLib());
		model.setSystemUnitClassLib(caexfile.getSystemUnitClassLib());
	}
	
	public void configureDataModel(AMLModel model) {
		//TODO : Make Configure Module that using XML
		
		//Make RoleClassLib
		RoleClassLib rclib = new RoleClassLibImpl("Edge_ValueDescriptorLib");
		
		//Add ValueDescriptor RC
		rclib.addRoleClass("ValueDescriptor");
		
		List<Attribute> sample_att_list = new ArrayList<Attribute>();
		sample_att_list.add(new AttributeImpl("name", "xs:string", "null", "ValueDescriptor Name"));
		sample_att_list.add(new AttributeImpl("min", "Object", "null"));
		sample_att_list.add(new AttributeImpl("max", "Object", "null"));
		sample_att_list.add(new AttributeImpl("type", "IoTType", "null"));
		sample_att_list.add(new AttributeImpl("uomLabel", "xs:string", "null"));
		sample_att_list.add(new AttributeImpl("defaultValue", "Object", "null"));
		sample_att_list.add(new AttributeImpl("formatting", "xs:string", "null"));
		sample_att_list.add(new AttributeImpl("labels", "xs:string[]", "null"));
		
		RoleClass rc = rclib.searchRoleClass("ValueDescriptor");
		rc.setAttribute(sample_att_list);
		
		//Make SystemUnitClassLib
		SystemUnitClassLib suclib = new SystemUnitClassLibImpl("Edge_CoreDataClassLib");
		
		//Add Data SUC
		sample_att_list = new ArrayList<Attribute>();
		sample_att_list.add(new AttributeImpl("name", "xs:string", null, "ValueDescriptor Name"));
		sample_att_list.add(new AttributeImpl("pushed", "xs:long", null, "Data's timestamp"));
		sample_att_list.add(new AttributeImpl("value", "xs:string", null, "Data's data value"));
		sample_att_list.add(new AttributeImpl("device", "xs:string", null, "Device Name"));
		
		//for the BaseObject Class data
		sample_att_list.add(new AttributeImpl("id", "xs:string", null, "database generated identifier"));
		sample_att_list.add(new AttributeImpl("created", "xs:long", null, "Timestamp of the record was added to DB"));
		sample_att_list.add(new AttributeImpl("modified", "xs:long", null, "Timestamp of the last modification to the record"));
		sample_att_list.add(new AttributeImpl("origin", "xs:long", null, "Timestamp of the record was created"));
		
		suclib.addSystemUnitClass("Data", sample_att_list);
		
		//Add Event SUC
		suclib.addSystemUnitClass("Event");
		
		sample_att_list = new ArrayList<Attribute>();
		sample_att_list.add(new AttributeImpl("device", "xs:string", null, "Device Name"));
		sample_att_list.add(new AttributeImpl("pushed", "xs:long", null, "Event's timestamp"));
		
		//for the BaseObject Class data
		sample_att_list.add(new AttributeImpl("id", "xs:string", null, "database generated identifier"));
		sample_att_list.add(new AttributeImpl("created", "xs:long", null, "Timestamp of the record was added to DB"));
		sample_att_list.add(new AttributeImpl("modified", "xs:long", null, "Timestamp of the last modification to the record"));
		sample_att_list.add(new AttributeImpl("origin", "xs:long", null, "Timestamp of the record was created"));
		
		
		SystemUnitClass suc = suclib.searchSystemUnitClass("Event");
		if(suc == null) {
			System.out.println("null");
		}
		suc.setAttribute(sample_att_list);
		
		SystemUnitClass sub_suc = suclib.searchSystemUnitClass("Data");
		
		suc.addSubInternalElement("Data", sub_suc, suclib.getName() + "/" + sub_suc.getName());
		
		model.setRoleClassLib(rclib);
		model.setSystemUnitClassLib(suclib);
	}
}