package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.InterfaceClass;
import edge.datamodel.aml.model.InterfaceClassLib;


/**
 * implements InterfaceClassLib object  
 *     
 */

public class InterfaceClassLibImpl extends CommonElementImpl implements InterfaceClassLib{
	private String Description;
	private String Version;
	private List<InterfaceClass> InterfaceClass;
	
	/**
	 * @fn InterfaceClassLibImpl()
	 * @brief Create new InterfaceClassLib includes empty InterfaceClass list
	 * 
	 */
	public InterfaceClassLibImpl() {
		InterfaceClass = new ArrayList<InterfaceClass>();
	}
	
	/**
	 * @fn InterfaceClassLibImpl(String name)
	 * @brief Create new InterfaceClassLib and set InterfaceClassLib name includes empty InterfaceClass list
	 * @param [in] name	InterfaceClassLib name
	 * 
	 */
	public InterfaceClassLibImpl(String name) {
		InterfaceClass = new ArrayList<InterfaceClass>();
		this.setName(name);
	}
	
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		this.Version = version;
		
	}

	public List<InterfaceClass> getInterfaceClass() {
		return InterfaceClass;
	}

	public void setInterfaceClass(List<InterfaceClass> interfaceClass) {
		this.InterfaceClass = interfaceClass;
		
	}

	public void addInterfaceClass(String name) {
		InterfaceClass newIC = new InterfaceClassImpl(name);
		this.InterfaceClass.add(newIC);
	}

	public void addinterfaceClass(String name, List<Attribute> attribute) {
		InterfaceClass newIC = new InterfaceClassImpl(name);
		newIC.setAttribute(attribute);
		this.InterfaceClass.add(newIC);
	}

	public InterfaceClass searchInterfaceClass(String name) {
		for(InterfaceClass ic: this.InterfaceClass) {
			if(name.equals(ic.getName()) == true) {
				return ic;
			}
		}
		return null;
	}

}
