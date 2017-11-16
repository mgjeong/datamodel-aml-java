package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.SupportedRoleClass;
import edge.datamodel.aml.model.SystemUnitClass;
import edge.datamodel.aml.model.SystemUnitClassLib;

/**
 * implements InternalElement object  
 *     
 */

@XmlRootElement(name="InternalElement")
@XmlType(propOrder = {"name","refBaseSystemUnitPath","attribute","internalElement","supportedRoleClass"})
public class InternalElementImpl extends CommonElementImpl implements InternalElement{
	private List<Attribute> Attribute;
	private String RefBaseSystemUnitPath;
	private SupportedRoleClass SupportedRoleClass;
	private List<InternalElement> InternalElement;

	/**
	 * @fn InternalElementImpl()
	 * @brief Create new InternalElement includes empty inner InternalElement list, RoleRequirements and Attribute list
	 * 
	 */
	public InternalElementImpl(){
		InternalElement = new ArrayList<InternalElement>();
		Attribute = new ArrayList<Attribute>();
	}
	
	/**
	 * @fn InternalElementImpl(String name)
	 * @brief Create new InternalElement and set InternalElement name 
	 * 		  includes empty inner InternalElement list, RoleRequirements and Attribute list
	 * @param [in] name	InternalElement name
	 * 
	 */
	public InternalElementImpl(String name){
		InternalElement = new ArrayList<InternalElement>();
		Attribute = new ArrayList<Attribute>();
		
		this.setName(name);		
	}
	
	@XmlAttribute(name="Name")
	public String getName() {
		return super.getName();
	}
	
	@XmlElement(type=AttributeImpl.class, name="Attribute")
	public List<Attribute> getAttribute() {
		return Attribute;
	}
	
	public void setAttribute(List<Attribute> attribute) {
		this.Attribute = attribute;
	}
	
	@XmlAttribute(name="RefBaseSystemUnitPath")
	public String getRefBaseSystemUnitPath() {
		return RefBaseSystemUnitPath;
	}

	public void setRefBaseSystemUnitPath(String refBaseSystemUnitPath) {
		this.RefBaseSystemUnitPath = refBaseSystemUnitPath;
	}

	@XmlElement(type=InternalElementImpl.class, name="InternalElement")
	public List<InternalElement> getInternalElement() {
		return InternalElement;
	}

	public void setInternalElement(List<InternalElement> internalElement) {
		this.InternalElement = internalElement;
	}

	@XmlElement(type=SupportedRoleClassImpl.class, name="SupportedRoleClass")
	public SupportedRoleClass getSupportedRoleClass() {
		return SupportedRoleClass;
	}

	public void setSupportedRoleClass(SupportedRoleClass supportedRoleClass){
		
		this.SupportedRoleClass = supportedRoleClass;
		
	}
	
	public InternalElement searchInternalElement(String name) {
		for(InternalElement ie: this.InternalElement) {
			if(name.equals(ie.getName()) == true) {
				return ie;
			}
		}
		return null;
	}

	public Attribute searchAttribute(String name) {
		for(Attribute att: this.Attribute) {
			if(name.contentEquals(att.getName()) == true) {
				return att;
			}
		}
		return null;
	}		
	
	public void setAttributeValue(String name, String value) {
		Attribute att = this.searchAttribute(name);
		if(att == null){
			System.out.println("Not matching attribute name\n");
			return;
		}
		att.setValue(value);
	}

	public String getAttributeValue(String name) {
		Attribute att = searchAttribute(name);
		if(att == null){
			System.out.println("Not matching attribute name\n");
			return null;
		}
		
		return att.getValue();
	}
		
	public InternalElement addSubInternalElement(String name) {
		InternalElement newIE = new InternalElementImpl(name);
		this.InternalElement.add(newIE);
		
		return newIE;
	}

	public InternalElement addSubInternalElement(String name, SystemUnitClassLib suc_lib, String suc_name) {
		InternalElement newIE = new InternalElementImpl(name);
		SystemUnitClass SUC = suc_lib.searchSystemUnitClass(suc_name);

		for(Attribute att: SUC.getAttribute()) {
			newIE.getAttribute().add(att.copyAttribute());
		}
		newIE.setRefBaseSystemUnitPath(suc_lib.getName() + "/" + suc_name);
		
		newIE.setSupportedRoleClass(SUC.getSupportedRoleClass());
		
		this.InternalElement.add(newIE);
		return newIE;
	}

	public InternalElement addSubInternalElement(String name, SystemUnitClass suc, String refBaseSystemUnitPath) {
		InternalElement newIE = new InternalElementImpl(name);
				
		for(Attribute att: suc.getAttribute()) {
			newIE.getAttribute().add(att.copyAttribute());
		}

		newIE.setRefBaseSystemUnitPath(refBaseSystemUnitPath);
		
		newIE.setSupportedRoleClass(suc.getSupportedRoleClass());
				
		this.InternalElement.add(newIE);
		return newIE;
	}
}
