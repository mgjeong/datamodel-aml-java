package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the InterfaceClassLib it using AutomationML class 
 * defines base structure for InterfaceClassLib definition 
 *    
 */

public interface InterfaceClassLib extends CommonElement{
	/**
	 * @fn String getDescription()
	 * @brief Returns the description about InterfaceClassLib 
	 * @return String	description of InterfaceClassLib
	 */
	public String getDescription();
	
	/**
	 * @fn void setDescription(String description)
	 * @brief Sets the description about InterfaceClassLib
	 * @param [in] description	description of InterfaceClassLib
	 * @return void
	 */
	public void setDescription(String description);
	
	/**
	 * @fn String getVersion()
	 * @brief Returns the InterfaceClassLib version as String type
	 * @return String	InterfaceClassLib version
	 */
	public String getVersion();
	
	/**
	 * @fn void setVersion(String version)
	 * @brief Sets the InterfaceClassLib version
	 * @param [in] version	InterfaceClassLib version
	 * @return void
	 */
	public void setVersion(String version);
	
	/**
	 * @fn List<InterfaceClass> getInterfaceClass()
	 * @brief Returns the InterfaceClass list 
	 * @return List<InterfaceClass>	InterfaceClass list
	 */
	public List<InterfaceClass> getInterfaceClass();
	
	/**
	 * @fn void setInterfaceClass(List<InterfaceClass> InterfaceClass)
	 * @brief Sets the List of InterfaceClass
	 * @param [in] InterfaceClass	List of InterfaceClass
	 * @return void
	 */
	public void setInterfaceClass(List<InterfaceClass> InterfaceClass);
	
	/**
	 * @fn void addInterfaceClass(String name)
	 * @brief Adds the new InterfaceClass at the InterfaceClass list
	 * @param [in] name	new InterfaceClass name
	 * @return void
	 */
	public void addInterfaceClass(String name);
	
	/**
	 * @fn void addinterfaceClass(String name, List<Attribute> attribute)
	 * @brief Adds the new InterfaceClass at the InterfaceClass list and sets Attributes on new InterfaceClass
	 * @param [in] name	new InterfaceClass name
	 * @param [in] attribute	new InterfaceClass Attributes
	 * @return void
	 */
	public void addinterfaceClass(String name, List<Attribute> attribute);
	
	/**
	 * @fn InterfaceClass searchInterfaceClass(String name)
	 * @brief Searches the InterfaceClass at the InterfaceClass list
	 * @param [in] name	InterfaceClass name to search
	 * @return InterfaceClass
	 */
	public InterfaceClass searchInterfaceClass(String Name);	

}
