package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.SystemUnitClass;

/**
 * implements SystemUnitClass object  
 *     
 */

@XmlRootElement(name="SystemUnitClass")
@XmlType(propOrder = {"name","refBaseClassPath","attribute","internalElement"})
public class SystemUnitClassImpl extends CommonElementImpl implements SystemUnitClass{
	private List<Attribute> Attribute;
	private List<InternalElement> InternalElement;
	private String RefBaseClassPath;
	
	/**
	 * @fn SystemUnitClassImpl()
	 * @brief Create new SystemUnitClass includes empty InternalElement list and Attribute list
	 * 
	 */
	public SystemUnitClassImpl() {
		InternalElement = new ArrayList<InternalElement>();
		Attribute = new ArrayList<Attribute>();
	}
	
	/**
	 * @fn SystemUnitClassImpl(String name)
	 * @brief Create new SystemUnitClass and set SystemUnitClass name includes empty InternalElement and Attribute list
	 * @param [in] name	SystemUnitClass name
	 * 
	 */
	public SystemUnitClassImpl(String name) {
		InternalElement = new ArrayList<InternalElement>();
		Attribute = new ArrayList<Attribute>();
		
		this.setName(name);
	}
	
	/**
	 * @fn SystemUnitClassImpl(String name, String refBaseClassPath)
	 * @brief Create new SystemUnitClass and set SystemUnitClass name and SystemUnitClass path to refer 
	 * 		  includes empty InternalElement and Attribute list
	 * @param [in] name	RoleClass name
	 * 
	 */
	public SystemUnitClassImpl(String name, String refBaseClassPath) {
		InternalElement = new ArrayList<InternalElement>();
		Attribute = new ArrayList<Attribute>();
		
		this.setName(name);
		this.setRefBaseClassPath(refBaseClassPath);
	}
	
	@Override
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
	
	@XmlElement(type=InternalElementImpl.class, name="InternalElement")
	public List<InternalElement> getInternalElement() {
		return InternalElement;
	}

	public void setInternalElement(List<InternalElement> internalElement) {
		this.InternalElement = internalElement;
	}
	
	public InternalElement addSubInternalElement(String name) {
		InternalElement newIE = new InternalElementImpl(name);
		this.InternalElement.add(newIE);
		
		return newIE;
	}

	public InternalElement addSubInternalElement(String name, SystemUnitClass suc, String refBaseSystemUnitPath) {
		InternalElement newIE = new InternalElementImpl(name);
//		newIE.setBaseSystemUnit(suc);				
		// Copy Class Value
		//TODO : Deep Copy?

		for(Attribute att: suc.getAttribute()) {
			newIE.getAttribute().add(att.copyAttribute());
		}
						
		newIE.setRefBaseSystemUnitPath(refBaseSystemUnitPath);
		
		this.InternalElement.add(newIE);
		
		return newIE;
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
	
	public String getRefBaseClassPath() {
		return RefBaseClassPath;
	}

	public void setRefBaseClassPath(String refBaseClassPath) {
		this.RefBaseClassPath = refBaseClassPath;
	}
}
