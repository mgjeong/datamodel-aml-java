package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the SystemUnitClassLib It is Description of AutomationML Project data 
 * defines base structure for SystemUnitClassLib definition 
 *    
 */

public interface SystemUnitClassLib extends CommonElement{
	/**
	 * @fn String getVersion()
	 * @brief Returns the SystemUnitClassLib version as String type
	 * @return String	SystemUnitClassLib version
	 */
	public String getVersion();
	
	/**
	 * @fn void setVersion(String version)
	 * @brief Sets the SystemUnitClassLib version
	 * @param [in] version	SystemUnitClassLib version
	 * @return void
	 */
	public void setVersion(String version);
	
	/**
	 * @fn List<SystemUnitClass> getSystemUnitClass()
	 * @brief Returns the SystemUnitClass list 
	 * @return List<SystemUnitClass>	SystemUnitClass list
	 */
	public List<SystemUnitClass> getSystemUnitClass();
	
	/**
	 * @fn void setSystemUnitClass(List<SystemUnitClass> SystemUnitClass)
	 * @brief Sets the List of SystemUnitClass
	 * @param [in] SystemUnitClass	List of SystemUnitClass
	 * @return void
	 */
	public void setSystemUnitClass(List<SystemUnitClass> SystemUnitClass);	
	
	/**
	 * @fn void addSystemUnitClass(String name)
	 * @brief Adds the new SystemUnitClass at the SystemUnitClass list
	 * @param [in] name	new SystemUnitClass name
	 * @return void
	 */
	public void addSystemUnitClass(String Name);
	
	/**
	 * @fn void addSystemUnitClass(String name, List<Attribute> attribute)
	 * @brief Adds the new SystemUnitClass at the SystemUnitClass list and sets Attributes on new SystemUnitClass
	 * @param [in] name	new SystemUnitClass name
	 * @param [in] attribute	new SystemUnitClass Attributes
	 * @return void
	 */
	public void addSystemUnitClass(String Name, List<Attribute> attribute);
	
	/**
	 * @fn SystemUnitClass searchSystemUnitClass(String name)
	 * @brief Searches the SystemUnitClass at the SystemUnitClass list
	 * @param [in] name	SystemUnitClass name to search
	 * @return SystemUnitClass
	 */
	public SystemUnitClass searchSystemUnitClass(String Name);
	
}
