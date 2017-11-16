package edge.datamodel.protobuf.aml;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.InstanceHierarchy;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.SupportedRoleClass;
import edge.datamodel.aml.model.Impl.AttributeImpl;
import edge.datamodel.aml.model.Impl.InstanceHierarchyImpl;
import edge.datamodel.aml.model.Impl.InternalElementImpl;
import edge.datamodel.aml.model.Impl.SupportedRoleClassImpl;
import edge.datamodel.protobuf.aml.ProtoAML.InstanceHierarchy.Builder;

/**
 * This class represents the ProtoAMLConverter It provides methods to convert
 * AutomationML data to protocol buffer data
 */
public class ProtoAMLConverter {
	/**
	 * @fn ProtoAML.InternalElement.Builder toProtoIE(InternalElement ie)
	 * @brief Convert AutomationML InternalElement to Protocol buffer InternalElement builder
	 * @param [in] ie	AutomationML InternalElement
	 * @return ProtoAML.InternalElement.Builder	Converted Protobuf InternalElement builder
	 */
	public static ProtoAML.InternalElement.Builder toProtoIE(InternalElement ie) {
		ProtoAML.InternalElement.Builder iebuilder = ProtoAML.InternalElement.newBuilder();
		iebuilder.setName(ie.getName());
		iebuilder.setRefBaseSystemUnitPath(ie.getRefBaseSystemUnitPath());
		ProtoAML.SupportedRoleClass.Builder srcbuilder = ProtoAML.SupportedRoleClass.newBuilder();

		//rrbuilder.setRefBaseRoleClassPath(ie.getRoleRequirements().getRefBaseRoleClassPath());

		//iebuilder.setRoleRequirements(rrbuilder);

		if(ie.getInternalElement() != null) {
			for(InternalElement sie: ie.getInternalElement()) {
				iebuilder.addInternalElement(toProtoIE(sie));
			}
		}
		if(ie.getAttribute() != null) {
			for(Attribute att: ie.getAttribute()) {
	
				iebuilder.addAttribute(toProtoATT(att));
			}
		}

		return iebuilder;
	}
	
	/**
	 * @fn ProtoAML.Attribute.Builder toProtoATT(Attribute att)
	 * @brief Convert AutomationML Attribute to Protocol buffer Attribute builder
	 * @param [in] att	AutomationML Attribute
	 * @return ProtoAML.Attribute.Builder	Converted Protobuf Attribute builder
	 */
	public static ProtoAML.Attribute.Builder toProtoATT(Attribute att) {

		ProtoAML.Attribute.Builder attbuilder = ProtoAML.Attribute.newBuilder();
		attbuilder.setName(att.getName()!=null?att.getName():"");
		attbuilder.setAttributeDataType(att.getAttributeDataType());
		attbuilder.setValue(att.getValue()!=null?att.getValue():"");

		if(att.getAttribute() != null) {
			for(Attribute satt: att.getAttribute()) {	
				attbuilder.addAttribute(toProtoATT(satt));
			}
		}
		
		return attbuilder;
	}
	
	/**
	 * @fn byte[] toProtoBuf(InstanceHierarchy ih)
	 * @brief Convert AutomationML InstanceHierarchy to Protocol buffer InstanceHierarchy byte array
	 * @param [in] ih	AutomationML InstanceHierarchy
	 * @return byte[]	Converted Protobuf InstanceHierarchy as byte array
	 */
	public static byte[] toProtoBuf(InstanceHierarchy ih) {
		if(ih == null) {
			System.out.println("ih == null");
			return null;
		}
		
		ProtoAML.InstanceHierarchy protoIh = null;		
		try{
			Builder ihbuilder = ProtoAML.InstanceHierarchy.newBuilder();
			ihbuilder.setName(ih.getName());
			
			//ihbuilder.setVersion(ih.getVersion());
			
			for(InternalElement ie: ih.getInternalElement()) {
				ihbuilder.addInternalElement(toProtoIE(ie));	

			}
			
			protoIh = ihbuilder.build();
		} catch (Exception e) {
			System.out.println("toProtoBuf: Invalid byte array 1");
		}
		
		if(protoIh == null) {
			System.out.println("protoIh == null");
			return null;
		}
		
		return protoIh.toByteArray();
	}

	/**
	 * @fn Attribute toAMLATT(ProtoAML.Attribute att)
	 * @brief Convert Protocol buffer Attribute to AutomationML Attribute
	 * @param [in] att	Protocol buffer Attribute
	 * @return Attribute	Converted AutomationML Attribute
	 */
	public static Attribute toAMLATT(ProtoAML.Attribute att) {
		Attribute AMLAtt = new AttributeImpl();
		AMLAtt.setName(att.getName());
		AMLAtt.setAttributeDataType(att.getAttributeDataType());
		AMLAtt.setValue(att.getValue()) ;
				
		for(ProtoAML.Attribute satt: att.getAttributeList()) {
			AMLAtt.getAttribute().add(toAMLATT(satt));
		}
		
		return AMLAtt;
	}
	
	/**
	 * @fn byte[] toAMLIE(ProtoAML.InternalElement ie)
	 * @brief Convert Protocol buffer InternalElement to AutomationML InternalElement
	 * @param [in] ie	Protocol buffer InternalElement
	 * @return InternalElement	Converted AutomationML InternalElement
	 */
	public static InternalElement toAMLIE(ProtoAML.InternalElement ie) {
		InternalElement AMLIe = new InternalElementImpl();
		
		AMLIe.setName(ie.getName());
		AMLIe.setRefBaseSystemUnitPath(ie.getRefBaseSystemUnitPath());
		
		SupportedRoleClass AMLSRC = new SupportedRoleClassImpl();
		AMLSRC.setRefRoleClassPath(ie.getSupportedRoleClass().getRefRoleClassPath());	
		AMLIe.setSupportedRoleClass(AMLSRC);
		
		for(ProtoAML.InternalElement sie: ie.getInternalElementList()) {
			AMLIe.getInternalElement().add(toAMLIE(sie));
		}
		
		for(ProtoAML.Attribute att: ie.getAttributeList()) {
			AMLIe.getAttribute().add(toAMLATT(att));
		}
		
		return AMLIe;
	}
	
	/**
	 * @fn byte[] toAMLIh(ProtoAML.InternalElement ie)
	 * @brief Convert byte[] array of protocol buffer InstanceHierarchy to AutomationML InstanceHierarchy
	 * @param [in] ih	Protocol buffer InstanceHierarchy byte array
	 * @return InstanceHierarchy	Converted AutomationML InstanceHierarchy
	 */
	public static InstanceHierarchy toAMLIh(byte[] ih) {
		if(ih == null) {
			return null;
		}
		
		InstanceHierarchy AMLIh = new InstanceHierarchyImpl();
		try {
			ProtoAML.InstanceHierarchy protoIh = ProtoAML.InstanceHierarchy.parseFrom(ih);
			
			AMLIh.setName(protoIh.getName());
			AMLIh.setVersion(protoIh.getVersion());
			
			for(ProtoAML.InternalElement ie: protoIh.getInternalElementList()) {
				AMLIh.getInternalElement().add(toAMLIE(ie));								
			}
			
		} catch (Exception e) {
			System.out.println("toAMLIh: Invalid byte array 3");
			e.printStackTrace();
		}
		return AMLIh;
	}
}
