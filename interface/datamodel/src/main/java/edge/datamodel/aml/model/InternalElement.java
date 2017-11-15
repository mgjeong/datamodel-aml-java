package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the InternalElement It is Description of AutomationML Project data 
 * defines base structure for InternalElement definition 
 *    
 */

public interface InternalElement extends CommonElement{
	/**
	 * @fn String getRefBaseSystemUnitPath()
	 * @brief Returns the referencing SystemUnitClass path
	 * @return String
	 */
	public String getRefBaseSystemUnitPath();
	
	/**
	 * @fn void setRefBaseSystemUnitPath(String refBaseSystemUnitPath)
	 * @brief Sets the SystemUnitClass path to refer
	 * @param [in] refBaseSystemUnitPath	SystemUnitClass path to refer
	 * @return void
	 */
	public void setRefBaseSystemUnitPath(String refBaseSystemUnitPath);
	
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
	 * @fn List<InternalElement> getInternalElement()
	 * @brief Returns the inner InternalElement list 
	 * @return List<InternalElement>
	 */
	public List<InternalElement> getInternalElement();
	
	/**
	 * @fn void setInternalElement(List<InternalElement> internalElement)
	 * @brief Sets the list of inner InternalElement
	 * @param [in] internalElement	InternalElement List
	 * @return void
	 */
	public void setInternalElement(List<InternalElement> internalElement);
	
	/**
	 * @fn InternalElement addSubInternalElement(String name)
	 * @brief Adds the new subInternalElement at the inner InternalElement list
	 * @param [in] name	new InternalElement name
	 * @return InternalElement	created new InternalElement
	 */
	public InternalElement addSubInternalElement(String name);
	
	/**
	 * @fn InternalElement addSubInternalElement(String name, StstemUnitClassLib suc_lib, String suc_name)
	 * @brief Adds the new InternalElement that SystemUnit object referenced SystemUnitClassLib using SystemUnitClass name 
	 * 		  at the inner InternalElement list
	 * @param [in] name	new InternalElement name
	 * @param [in] suc_lib	SystemUnitClass Library to refer 
	 * @param [in]  suc_name	SystemUnitClass name to search on SystemUnitClassLib
	 * @return InternalElement	created new InternalElement
	 */
	public InternalElement addSubInternalElement(String name, SystemUnitClassLib suc_lib, String suc_name);
	
	/**
	 * @fn InternalElement addSubInternalElement(String name, SystemUnitClass suc, String refBaseSystemUnitPath)
	 * @brief Adds the new InternalElement that SystemUnitClass and sets SystemUnitClass path 
	 * @param [in] name	new InternalElement name
	 * @param [in] suc	SystemUnitClass to add
	 * @param [in] refBaseSystemUnitPath	SystemUnitClass path to add
	 * @return InternalElement	created new InternalElement
	 */
	public InternalElement addSubInternalElement(String name, SystemUnitClass suc, String refBaseSystemUnitPath);
	
	/**
	 * @fn InternalElement searchInternalElement(String name)
	 * @brief Searches the InternalElement at the inner InternalElement list
	 * @param [in] name	InternalElement name to search
	 * @return InternalElement
	 */
	public InternalElement searchInternalElement(String name);
	
	/**
	 * @fn void setRolerequirements(String refBaseRoleClassPath)
	 * @brief Sets the RoleClass path to refer
	 * @param [in] refBaseRoleClassPath	RoleClass path
	 * @return void
	 */
	public void setRolerequirements(String refBaseRoleClassPath);

	/**
	 * @fn RoleRequirements getRoleRequirements()
	 * @brief Returns the RoleRequirements includes referencing RoleClass path
	 * @return RoleRequirements
	 */
	public RoleRequirements getRoleRequirements();
	
	/**
	 * @fn void setAttributeValue(String name, String value)
	 * @brief Sets the Attribute value using predefined Attribute name 
	 * @param [in] name	Attribute name to search
	 * @param [in] value	Attribute value to set
	 * @return void
	 */
	public void setAttributeValue(String name, String value);
	
	/**
	 * @fn String getAttributeValue(String name)
	 * @brief Returns the Attribute value that using predefined Attribute name to search
	 * @return String
	 */
	public String getAttributeValue(String name);

}
	
