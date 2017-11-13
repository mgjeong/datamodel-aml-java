package edge.datamodel.aml.model.Impl;

import edge.datamodel.aml.model.AMLObject;
import edge.datamodel.aml.model.InstanceHierarchy;

/**
 * implements AMLobject object  
 *     
 */

public class AMLObjectImpl extends CommonElementImpl implements AMLObject{
	private InstanceHierarchy InstanceHierarchy;
	
	/**
	 * @fn AMLModelImpl()
	 * @brief Create new AMLModel includes empty RoleClassLib and SystemUnitClassLib
	 */
	public AMLObjectImpl(){
		InstanceHierarchy = new InstanceHierarchyImpl();
	}
	/**
	 * @fn AMLModelImpl(String name)
	 * @brief Set AMLModel's name. Create new AMLModel includes empty RoleClassLib and SystemUnitClassLib
	 * @param [in] name	AMLModel name
	 */
	public AMLObjectImpl(String name){
		InstanceHierarchy = new InstanceHierarchyImpl();
		
		this.setName(name);		
	}
	
	public InstanceHierarchy getInstanceHierarchy() {
		return InstanceHierarchy;
	}
	
	public void setInstanceHierarchy(InstanceHierarchy instanceHierarchy) {
		this.InstanceHierarchy = instanceHierarchy;
	}
	
}
