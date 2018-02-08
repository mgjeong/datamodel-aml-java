package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.RoleClass;

/**
 * implements RoleClass object  
 *     
 */

@XmlRootElement(name="RoleClass")
public class RoleClassImpl extends CommonElementImpl implements RoleClass{
	private String Description;
	private List<Attribute> Attribute;
	private String RefBassClassPath;
	
	/**
	 * @fn RoleClassImpl()
	 * @brief Create new RoleClass includes empty Attribute list
	 * 
	 */
	public RoleClassImpl() {
		Attribute = new ArrayList<Attribute>();
	};
	
	/**
	 * @fn RoleClassImpl(String name)
	 * @brief Create new RoleClass and set RoleClass name includes empty Attribute list
	 * @param [in] name	RoleClass name
	 * 
	 */
	public RoleClassImpl(String name) {
		Attribute = new ArrayList<Attribute>();
		this.setName(name);
	};
	
	@Override
	@XmlAttribute(name="Name")
	public String getName() {
		return super.getName();
	}
	
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		this.Description = description;	
	}
	public String getRefBassClassPath() {
		return RefBassClassPath;
	}
	public void setRefBassClassPath(String refBassClassPath) {
		RefBassClassPath = refBassClassPath;
	}

	@XmlElement(type=AttributeImpl.class, name="Attribute")
	public List<Attribute> getAttribute() {
		return Attribute;
	}
	public void setAttribute(List<Attribute> attribute) {
		this.Attribute = attribute;
		
	}
	
	public Attribute searchAttribute(String name) {
		for(Attribute att: this.Attribute) {
			if(name.contentEquals(att.getName()) == true) {
				return att;
			}
		}
		return null;
	}	
}
