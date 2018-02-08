package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the RoleClassLib It is Description of AutomationML Project data 
 * defines base structure for RoleClassLib definition 
 *    
 */

public interface RoleClassLib extends CommonElement{
	/**
	 * @fn String getDescription()
	 * @brief Returns the description about RoleClassLib 
	 * @return String	description of RoeClassLib
	 */
	public String getDescription();
	
	/**
	 * @fn void setDescription(String description)
	 * @brief Sets the description about RoeClassLib
	 * @param [in] description	description of RoeClassLib
	 * @return void
	 */
	public void setDescription(String description);
	
	/**
	 * @fn String getVersion()
	 * @brief Returns the RoeClassLib version as String type
	 * @return String	RoeClassLib version
	 */
	public String getVersion();
	
	/**
	 * @fn void setVersion(String version)
	 * @brief Sets the RoeClassLib version
	 * @param [in] version	RoeClassLib version
	 * @return void
	 */
	public void setVersion(String version);
	
	/**
	 * @fn List<RoleClass> getRoleClass()
	 * @brief Returns the RoleClass list 
	 * @return List<RoleClass>	RoleClass list
	 */
	public List<RoleClass> getRoleClass();
	
	/**
	 * @fn void setRoleClass(List<RoleClass> roleClass)
	 * @brief Sets the List of RoleClass
	 * @param [in] roleClass	List of RoleClass
	 * @return void
	 */
	public void setRoleClass(List<RoleClass> roleClass);
	
	/**
	 * @fn void addRoleClass(String name)
	 * @brief Adds the new RoleClass at the RoleClass list
	 * @param [in] name	new RoleClass name
	 * @return void
	 */
	public void addRoleClass(String name);
	
	/**
	 * @fn void addRoleClass(String name, List<Attribute> attribute)
	 * @brief Adds the new RoleClass at the RoleClass list and sets Attributes on new RoleClass
	 * @param [in] name	new RoleClass name
	 * @param [in] attribute	new RoleClass Attributes
	 * @return void
	 */
	public void addRoleClass(String name, List<Attribute> attribute);
	
	/**
	 * @fn RoleClass searchRoleClass(String name)
	 * @brief Searches the RoleClass at the RoleClass list
	 * @param [in] name	RoleClass name to search
	 * @return RoleClass
	 */
	public RoleClass searchRoleClass(String name);	
}
