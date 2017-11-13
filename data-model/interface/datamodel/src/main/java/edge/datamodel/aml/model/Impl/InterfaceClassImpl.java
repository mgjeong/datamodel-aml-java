package edge.datamodel.aml.model.Impl;

import java.util.ArrayList;
import java.util.List;

import edge.datamodel.aml.model.Attribute;
import edge.datamodel.aml.model.InterfaceClass;

/**
 * implements InterfaceClass object  
 *     
 */

public class InterfaceClassImpl extends CommonElementImpl implements InterfaceClass{
	private String Description;
	private List<Attribute> Attribute;
	private String RefBassClassPath;
	
	/**
	 * @fn InterfaceClassImpl()
	 * @brief Create new InterfaceClass includes empty Attribute list
	 * 
	 */
	public InterfaceClassImpl() {
		Attribute = new ArrayList<Attribute>();
	}
	
	/**
	 * @fn InterfaceClassImpl(String name)
	 * @brief Create new InterfaceClass and set InterfaceClass name includes empty Attribute list
	 * @param [in] name	InterfaceClass name
	 * 
	 */
	public InterfaceClassImpl(String name) {
		Attribute = new ArrayList<Attribute>();
		this.setName(name);
	}
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
		
	}

	public List<Attribute> getAttribute() {
		return Attribute;
	}

	public void setAttribute(List<Attribute> attribute) {
		this.Attribute = attribute;
		
	}

	public String getRefBassClassPath() {
		return RefBassClassPath;
	}

	public void setRefBassClassPath(String refBassClassPath) {
		this.RefBassClassPath = refBassClassPath;		
	}

	public Attribute searchAttribute(String Name) {
		for(Attribute att: this.Attribute) {
			if(Name.contentEquals(att.getName()) == true) {
				return att;
			}
		}
		return null;
	}
}
