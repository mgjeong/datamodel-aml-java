package edge.datamodel.aml.model;

/**
 * represents the AMLmodel it make AutomationML class structure 
 * defines base structure for AMLModel definition 
 *    
 */

public interface AMLModel {
	/**
	 * @fn RoleClassLib getRoleClassLib()
	 * @brief Returns the AutomationML RoleClassLib
	 * @return RoleClassLib
	 */
	public RoleClassLib getRoleClassLib();
	
	/**
	 * @fn void setRoleClassLib(RoleClassLib roleClassLib)
	 * @brief Sets the AutomationML RoleClassLib
	 * @param [in] roleClassLib	AutomationML RoleClassLib
	 * @return void
	 */
	public void setRoleClassLib(RoleClassLib roleClassLib);
	
	/**
	 * @fn SystemUnitClassLib getSystemUnitClassLib()
	 * @brief Returns the AutomationML SystemUnitClassLib
	 * @return SystemUnitClassLib
	 */
	public SystemUnitClassLib getSystemUnitClassLib();
	
	/**
	 * @fn void setSystemUnitClassLib(SystemUnitClassLib systemUnitClassLib)
	 * @brief Sets the AutomationML SystemUnitClassLib
	 * @param [in] systemUnitClassLib	AutomationML SystemUnitClassLib
	 * @return void
	 */
	public void setSystemUnitClassLib(SystemUnitClassLib systemUnitClassLib);
}
