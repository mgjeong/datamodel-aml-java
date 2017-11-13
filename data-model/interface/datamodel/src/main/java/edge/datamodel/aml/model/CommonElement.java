package edge.datamodel.aml.model;

/**
 * represents the CommonElement it using AutomationML class 
 * defines base structure for CommonElement definition 
 *    
 */

public interface CommonElement {
	/**
	 * @fn String getName()
	 * @brief Returns the Class name 
	 * @return String Class name
	 */
	public String getName();
	
	/**
	 * @fn void setName(String name)
	 * @brief Sets the Class name
	 * @param [in] name	Class name
	 * @return void
	 */
	public void setName(String name);
}
