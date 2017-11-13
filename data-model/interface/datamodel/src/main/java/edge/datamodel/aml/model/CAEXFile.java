package edge.datamodel.aml.model;
/**
 * represents the AMLObject it includes AutomationML InstanceHierarchy class structure 
 *     
 */

public interface CAEXFile {
	
	/**
	 * @fn InstanceHierarchy getInstanceHierarchy()
	 * @brief Returns the AutomationML InstanceHierarchy
	 * @return InstanceHierarchy
	 */
	public InstanceHierarchy getInstanceHierarchy();
	
	/**
	 * @fn void setInstanceHierarchy(InstanceHierarchy instanceHierarchy)
	 * @brief Sets the AutomationML InstanceHierarchy
	 * @param [in] instanceHierarchy	AutomationML InstanceHierarchy
	 * @return void
	 */
	public void setInstanceHierarchy(InstanceHierarchy instanceHierarchy);
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
