package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.InstanceHierarchy;
import edge.datamodel.aml.model.InternalElement;
import edge.datamodel.aml.model.SystemUnitClass;
import edge.datamodel.aml.model.SystemUnitClassLib;



/**
 * implements InstanceHierarchy object  
 *     
 */

@XmlRootElement(name="InstanceHierarchy")
@XmlType(propOrder = {"name","version","internalElement"})
public class InstanceHierarchyImpl extends CommonElementImpl implements InstanceHierarchy{
	private List<InternalElement> InternalElement;
	private String Version;
	
	/**
	 * @fn InstanceHierarchyImpl()
	 * @brief Create new InstanceHierarhcy includes empty InternalElement list
	 */
	public InstanceHierarchyImpl(){
		InternalElement = new ArrayList<InternalElement>();
	}
	
	/**
	 * @fn AttributeImpl(String name)
	 * @brief Create new InstanceHierarhcy includes empty InternalElement list and set InstanceHierarchy name 
	 * @param [in] name	InstanceHierarchy name
	 */
	public InstanceHierarchyImpl(String name){
		InternalElement = new ArrayList<InternalElement>();
		this.setName(name);
	}
	
	@Override
	@XmlAttribute(name="Name")
	public String getName() {
		return super.getName();
	}
	
	public void setVersion(String version) {
		this.Version = version;
	}
	
	public String getVersion() {
		return Version;
	}
		
	@XmlElement(type=InternalElementImpl.class, name="InternalElement")
	public List<InternalElement> getInternalElement() {
		return InternalElement;
	}
	
	public void setInternalElement(List<InternalElement> internalElement) {
		this.InternalElement = internalElement;
	}

	public void addInternalElement(String name) {
		InternalElement newIE= new InternalElementImpl(name);
		this.InternalElement.add(newIE);
		
	}
	
	public void addInternalElement(String name, SystemUnitClassLib suc_lib, String suc_name) {
		InternalElement newIE = new InternalElementImpl(name);
		SystemUnitClass SUC = suc_lib.searchSystemUnitClass(suc_name);
		for(Attribute att: SUC.getAttribute()) {
			newIE.getAttribute().add(att.copyAttribute());
		}
		newIE.setRefBaseSystemUnitPath(suc_lib.getName() + "/" + suc_name);
		this.InternalElement.add(newIE);
	}
	
	public InternalElement searchInternalElement(String name) {
		for(InternalElement ie: this.InternalElement) {
			if(name.equals(ie.getName()) == true) {
				return ie;
			}
		}
		return null;
	}
}
