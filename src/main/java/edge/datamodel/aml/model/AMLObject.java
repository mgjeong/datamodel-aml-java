package edge.datamodel.aml.model;
/**
 * represents the AMLObject it includes AutomationML InstanceHierarchy class structure 
 *     
 */

public interface AMLObject {
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
	
}
