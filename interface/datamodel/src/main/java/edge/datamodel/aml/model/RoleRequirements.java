package edge.datamodel.aml.model;

/**
 * represents the RoleRequirements It is Description of AutomationML Project data 
 * defines base structure for RoleRequirements definition 
 *    
 */

public interface RoleRequirements {
	/**
	 * @fn String getRefBaseRoleClassPath()
	 * @brief Returns the referencing RoleClass path
	 * @return String
	 */
	public String getRefBaseRoleClassPath();
	
	/**
	 * @fn void setRefBaseRoleClassPath(String refBaseClassPath)
	 * @brief Sets the RoleClass path to refer
	 * @param [in] refBaseClassPath	RoleClass path to refer
	 * @return void
	 */
	public void setRefBaseRoleClassPath(String refBaseRoleClassPath);
}
