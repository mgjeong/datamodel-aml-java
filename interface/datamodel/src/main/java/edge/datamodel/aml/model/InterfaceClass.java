package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the InterfaceClass it using AutomationML class 
 * defines base structure for InterfaceClass definition 
 *    
 */

public interface InterfaceClass extends CommonElement{
	/**
	 * @fn String getDescription()
	 * @brief Returns the description about InterfaceClass 
	 * @return String	description
	 */
	public String getDescription();
	
	/**
	 * @fn void setDescription(String description)
	 * @brief Sets the description about InterfaceClass
	 * @param [in] description	description of InterfaceClass
	 * @return void
	 */
	public void setDescription(String description);
	
	/**
	 * @fn List<Attribute> getAttribute()
	 * @brief Returns the Attribute list
	 * @return List<Attribute>
	 */
	public List<Attribute> getAttribute();
	
	/**
	 * @fn void setAttribute(List<Attribute> attribute)
	 * @brief Sets the list of Attribute
	 * @param [in] attribute	Attribute list
	 * @return void
	 */
	public void setAttribute(List<Attribute> attribute);
	
	/**
	 * @fn String getRefBaseCLassPath()
	 * @brief Returns the referencing InterfaceClass path
	 * @return String
	 */
	public String getRefBassClassPath();
	
	/**
	 * @fn void setRefBaseClassPath(String refBaseClassPath)
	 * @brief Sets the InterfaceClass path to refer
	 * @param [in] refBaseClassPath	InterfaceClass path to refer
	 * @return void
	 */
	public void setRefBassClassPath(String refBassClassPath);
	
	/**
	 * @fn Attribute searchAttribute(String name)
	 * @brief Searches the Attribute at the Attribute list
	 * @param [in] name	attribute name to search
	 * @return Attribute
	 */
	public Attribute searchAttribute(String Name);
}
