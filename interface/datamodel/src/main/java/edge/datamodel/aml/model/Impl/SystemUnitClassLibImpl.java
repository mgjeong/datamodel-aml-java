package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.SystemUnitClass;
import edge.datamodel.aml.model.SystemUnitClassLib;

/**
 * implements SystemUnitClassLib object  
 *     
 */

@XmlRootElement(name="SystemUnitClassLib")
@XmlType(propOrder = {"name","version","systemUnitClass"})
public class SystemUnitClassLibImpl extends CommonElementImpl implements SystemUnitClassLib{
	private String Version;
	private List<SystemUnitClass> SystemUnitClass;
	
	/**
	 * @fn SystemUnitClassLibImpl()
	 * @brief Create new SystemUnitClassLib includes empty SystemUnitClass list
	 * 
	 */
	public SystemUnitClassLibImpl() {
		SystemUnitClass = new ArrayList<SystemUnitClass>();
		this.setVersion("0.0.1");
	}
	
	/**
	 * @fn SystemUnitClassLibImpl(String name)
	 * @brief Create new SystemUnitClassLib and set SystemUnitClassLib name includes empty SystemUnitClass list
	 * @param [in] name	SystemUnitClassLib name
	 * 
	 */
	public SystemUnitClassLibImpl(String name) {
		SystemUnitClass = new ArrayList<SystemUnitClass>();
		this.setName(name);
		this.setVersion("0.0.1");
	}
	
	/**
	 * @fn SystemUnitClassLibImpl(String name, String version)
	 * @brief Create new SystemUnitClassLib and set SystemUnitClassLib name and version includes empty SystemUnitClass list
	 * @param [in] name	SystemUnitClassLib name
	 * @param [in] version	SystemUnitClassLib version
	 * 
	 */
	public SystemUnitClassLibImpl(String name, String version) {
		SystemUnitClass = new ArrayList<SystemUnitClass>();
		this.setName(name);
		this.setVersion(version);
	}
	
	@Override
	@XmlAttribute(name="Name")
	public String getName() {
		return super.getName();
	}
	
	@XmlElement(name="Version")
	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		this.Version = version;
	}
	
	@XmlElement(type=SystemUnitClassImpl.class, name="SystemUnitClass")
	public List<SystemUnitClass> getSystemUnitClass() {
		return SystemUnitClass;
	}

	public void setSystemUnitClass(List<SystemUnitClass> systemUnitClass) {
		this.SystemUnitClass = systemUnitClass;
	}
	
	public void addSystemUnitClass(String name){
		SystemUnitClass newSUC = new SystemUnitClassImpl(name);
		this.SystemUnitClass.add(newSUC);
	}
	
	public void addSystemUnitClass(String name, List<Attribute> attribute){
		SystemUnitClass newSUC = new SystemUnitClassImpl(name);
		newSUC.setAttribute(attribute);
		this.SystemUnitClass.add(newSUC);
	}
	
	public SystemUnitClass searchSystemUnitClass(String name){
		for(SystemUnitClass suc: this.SystemUnitClass) {
			if(name.equals(suc.getName()) == true) {
				return suc;
			}
		}
		return null;
	}
}
