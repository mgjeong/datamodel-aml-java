package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the InstanceHierarchy It is Description of AutomationML Project data 
 * defines base structure for InstanceHierarchy definition 
 *    
 */

public interface InstanceHierarchy extends CommonElement{
	/**
	 * @fn String getVersion()
	 * @brief Returns the InstanceHierarchy version as String type
	 * @return String	InstanceHierarchy version
	 */
	public String getVersion();
	
	/**
	 * @fn void setVersion(String version)
	 * @brief Sets the InstanceHierarchy version
	 * @param [in] version	InstanceHierarchy version
	 * @return void
	 */
	public void setVersion(String version);
	
	/**
	 * @fn List<InternalElement> getInternalElement()
	 * @brief Returns the list of InternalElement that SystemUnitClass object
	 * @return List<InternalElement>	list of InternalElement
	 */
	public List<InternalElement> getInternalElement();
	
	/**
	 * @fn void setInteralElement(List<InternalElement> internalElement)
	 * @brief Sets the list of InternalElement that SystemUnitClass object
	 * @param [in] List<InternalElement>	list of SystemUnit object
	 * @return void
	 */
	public void setInternalElement(List<InternalElement> internalElement);
	
	/**
	 * @fn void addInternalElement(String name)
	 * @brief Adds the new InternalElement that SystemUnit object
	 * @param [in] name	new InternalElement name
	 * @return void
	 */
	public void addInternalElement(String name);
	
	/**
	 * @fn void addInternalElement(String name, SystemUnitClassLib suc_lib, String suc_name)
	 * @brief Adds the new InternalElement that SystemUnit object referenced SystemUnitClassLib using SystemUnitClass name
	 * @param [in] name	new InternalElement name
	 * @param [in] suc_lib	SystemUnitClass Library to refer 
	 * @param [in] suc_name	SystemUnitClass name to search on SystemUnitClassLib
	 * @return void
	 */
	public void addInternalElement(String name, SystemUnitClassLib suc_lib, String suc_name);
	
	/**
	 * @fn InternalElement searchInternalElement(String name)
	 * @brief Searches the InternalElement at the InternalElement list
	 * @param [in] name	InternalElement name to search
	 * @return InternalElement
	 */
	public InternalElement searchInternalElement(String name);

}
