package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.RoleClass;
import edge.datamodel.aml.model.RoleClassLib;

/**
 * implements RoleClassLib object  
 *     
 */

@XmlRootElement(name="RoleClassLib")
@XmlType(propOrder = {"name","description","version","roleClass"})
public class RoleClassLibImpl extends CommonElementImpl implements RoleClassLib{
	private String Description;
	private String Version;
	private List<RoleClass> RoleClass;
	
	/**
	 * @fn RoleClassLibImpl()
	 * @brief Create new RoleClassLib includes empty RoleClass list
	 * 
	 */
	public RoleClassLibImpl() {
		RoleClass = new ArrayList<RoleClass>();
		this.setVersion("0.0.1");
	};
	
	/**
	 * @fn RoleClassLibImpl(String name)
	 * @brief Create new RoleClassLib and set RoleClassLib name includes empty RoleClass list
	 * @param [in] name	RoleClassLib name
	 * 
	 */
	public RoleClassLibImpl(String name) {
		RoleClass = new ArrayList<RoleClass>();
		this.setName(name);
		this.setVersion("0.0.1");
	};
	
	/**
	 * @fn RoleClassLibImpl(String name, String version)
	 * @brief Create new RoleClassLib and set RoleClassLib name and version includes empty RoleClass list
	 * @param [in] name	RoleClassLib name
	 * @param [in] version	RoleClassLib version
	 * 
	 */
	public RoleClassLibImpl(String name, String version) {
		RoleClass = new ArrayList<RoleClass>();
		this.setName(name);
		this.setVersion(version);
	};
	
	@Override
	@XmlAttribute(name="Name")
	public String getName() {
		return super.getName();
	}
	
	@XmlElement(name="Description")
	public String getDescription() {
		return Description;
	}
	
	public void setDescription(String description) {
		this.Description = description;
	}
	
	@XmlElement(name="Version")
	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		this.Version = version;
	}
	
	@XmlElement(type=RoleClassImpl.class, name="RoleClass")
	public List<RoleClass> getRoleClass() {
		return RoleClass;
	}

	public void setRoleClass(List<RoleClass> roleClass) {
		this.RoleClass = roleClass;
	}

	public void addRoleClass(String name){
		RoleClass newRC = new RoleClassImpl(name);
		this.RoleClass.add(newRC);
	}

	public void addRoleClass(String name, List<Attribute> attribute){
		RoleClass newRC = new RoleClassImpl(name);
		newRC.setAttribute(attribute);
		this.RoleClass.add(newRC);
	}

	public RoleClass searchRoleClass(String name){
		for(RoleClass rc: this.RoleClass) {
			if(name.equals(rc.getName()) == true) {
				return rc;
			}
		}
		return null;
	}
}
