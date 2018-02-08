package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the RoleClass It is Description of AutomationML Project data 
 * defines base structure for RoleClass definition 
 *    
 */

public interface RoleClass extends CommonElement{
	/**
	 * @fn String getDescription()
	 * @brief Returns the description about RoleClass 
	 * @return String	description
	 */
	public String getDescription();
	
	/**
	 * @fn void setDescription(String description)
	 * @brief Sets the description about RoleClass
	 * @param [in] description	description of RoleClass
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
	 * @brief Returns the referencing RoleClass path
	 * @return String
	 */
	public String getRefBassClassPath();
	
	/**
	 * @fn void setRefBaseClassPath(String refBaseClassPath)
	 * @brief Sets the RoleClass path to refer
	 * @param [in] refBaseClassPath	RoleClass path to refer
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
