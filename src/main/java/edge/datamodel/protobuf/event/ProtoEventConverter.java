package edge.datamodel.protobuf.event;

import java.util.ArrayList;
import java.util.List;

import org.edgexfoundry.domain.core.Event;
import org.edgexfoundry.domain.core.Reading;

import edge.datamodel.protobuf.event.ProtoEvent.Event.Builder;

/**
 * This class represents the ProtoAMLConverter It provides methods to convert
 * edgeCoredata to protocol buffer data
 */
public class ProtoEventConverter {
	
	/**
	 * @fn byte[] toProtoBuf(Event event)
	 * @brief Convert edgeCoredata Event to Protocol buffer event byte array
	 * @param [in] event	edgeCoredata Event
	 * @return byte[]	Converted Protobuf event as byte array
	 */
	public static byte[] toProtoBuf(Event event) {
		if(event == null) {
			return null;
		}
		ProtoEvent.Event protoEvent = null;
		try{
			Builder eventbuilder = ProtoEvent.Event.newBuilder();
			
			eventbuilder.setId(event.getId()!=null?event.getId():"")
						.setCreated(event.getCreated())
						.setModified(event.getModified())
						.setOrigin(event.getOrigin())
						.setPushed(event.getPushed())
						.setDevice(event.getDevice());
			
			for(Reading reading: event.getReadings()) {
				eventbuilder.addReading(ProtoEvent.Reading.newBuilder().setId(reading.getId() != null?reading.getId():"")
																	   .setCreated(reading.getCreated())
																	   .setModified(reading.getModified())
																	   .setOrigin(reading.getOrigin())
																	   .setPushed(reading.getPushed())
																	   .setName(reading.getName())
																	   .setValue(reading.getValue())
																	   .setDevice(reading.getDevice()));		
			}
			
			protoEvent = eventbuilder.build();
		} catch (Exception e) {
			System.out.println("toProtoBuf: Invalid byte array");
		}
		
		if(protoEvent == null) {
			return null;
		}
		
		return protoEvent.toByteArray();
	}
	
	/**
	 * @fn byte[] toProtoBuf(Event event)
	 * @brief Convert byte[] array of Protocol buffer event to edgeCoredata event.
	 * @param [in]	event edgeCoredata Event
	 * @return Event	Converted edgeCoredata event
	 */
	public static Event toEdgeXEvent(byte[] event) {
		if(event == null) {
			return null;
		}

		Event edgexEvent = new Event(null);
		try {
			ProtoEvent.Event protoEvent = ProtoEvent.Event.parseFrom(event);
			
			edgexEvent.setId(protoEvent.getId());
			edgexEvent.setCreated(protoEvent.getCreated());
			edgexEvent.setModified(protoEvent.getModified());
			edgexEvent.setOrigin(protoEvent.getOrigin());
			edgexEvent.setPushed(protoEvent.getPushed());
			edgexEvent.setDevice(protoEvent.getDevice());

			List<Reading> edgexReadings = new ArrayList<Reading>();
			
			for(ProtoEvent.Reading reading: protoEvent.getReadingList()) {
				Reading edgexReading = new Reading();
				
				edgexReading.setId(reading.getId());
				edgexReading.setCreated(reading.getCreated());
				edgexReading.setModified(reading.getModified());
				edgexReading.setOrigin(reading.getOrigin());
				edgexReading.setPushed(reading.getPushed());
				edgexReading.setName(reading.getName());
				edgexReading.setValue(reading.getValue());
				edgexReading.setDevice(reading.getDevice());
				
				edgexReadings.add(edgexReading);
			}
			
			edgexEvent.setReadings(edgexReadings);			
		} catch (Exception e) {
			System.out.println("toEdgeXEvent: Inavlid byte array");
			e.printStackTrace();
		}
		
		return edgexEvent;
	}

}
