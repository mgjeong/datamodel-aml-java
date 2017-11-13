package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the SystemUnitClass It is Description of AutomationML Project data 
 * defines base structure for SystemUnitClass definition 
 *    
 */

public interface SystemUnitClass extends CommonElement{
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
	 * @brief Returns the list of InternalElement that SystemUnitClass object
	 * @return List<InternalElement>	list of InternalElement
	 */
	public List<InternalElement> getInternalElement();
	
	/**
	 * @fn void setInteralElement(List<InternalElement> internalElement)
	 * @brief Sets the list of InternalElement that SystemUnitClass object
	 * @param [in] List<InternalElement>	list of InternalElement
	 * @return void
	 */
	public void setInternalElement(List<InternalElement> internalElement);
	
	/**
	 * @fn String getRefBaseCLassPath()
	 * @brief Returns the referencing SystemUnitClass path
	 * @return String
	 */
	public String getRefBaseClassPath();
	
	/**
	 * @fn void setRefBaseClassPath(String refBaseClassPath)
	 * @brief Sets the SystemUnitClass path to refer
	 * @param [in] refBaseClassPath	SystemUnitClass path to refer
	 * @return void
	 */
	public void setRefBaseClassPath(String refBaseClassPath);
	
	/**
	 * @fn void addInternalElement(String name)
	 * @brief Adds the new InternalElement that SystemUnit object
	 * @param [in] name	new InternalElement name
	 * @return void
	 */
	public InternalElement addSubInternalElement(String name);
	
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
	 * @brief Searches the InternalElement at the InternalElement list
	 * @param [in] name	InternalElement name to search
	 * @return InternalElement
	 */
	public InternalElement searchInternalElement(String name);
	
	/**
	 * @fn Attribute searchAttribute(String name)
	 * @brief Searches the Attribute at the Attribute list
	 * @param [in] name	attribute name to search
	 * @return Attribute
	 */
	public Attribute searchAttribute(String name);
}
