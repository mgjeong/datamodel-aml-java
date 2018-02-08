package edge.datamodel.aml.model.Impl;

import edge.datamodel.aml.model.AMLModel;
import edge.datamodel.aml.model.RoleClassLib;
import edge.datamodel.aml.model.SystemUnitClassLib;

/**
 * implements AMLmodel object  
 *     
 */

public class AMLModelImpl extends CommonElementImpl implements AMLModel{
	private RoleClassLib RoleClassLib;
	private SystemUnitClassLib SystemUnitClassLib;
	
	/**
	 * @fn AMLModelImpl()
	 * @brief Create new AMLModel includes empty RoleClassLib and SystemUnitClassLib
	 */
	public AMLModelImpl(){
		RoleClassLib = new RoleClassLibImpl();
		SystemUnitClassLib = new SystemUnitClassLibImpl();
	}
	/**
	 * @fn AMLModelImpl(String name)
	 * @brief Set AMLModel's name. Create new AMLModel includes empty RoleClassLib and SystemUnitClassLib
	 * @param [in] name	AMLModel name
	 */
	public AMLModelImpl(String name){
		RoleClassLib = new RoleClassLibImpl();
		SystemUnitClassLib = new SystemUnitClassLibImpl();
		
		this.setName(name);		
	}
	
	public RoleClassLib getRoleClassLib() {
		return RoleClassLib;
	}
	public void setRoleClassLib(RoleClassLib roleClassLib) {
		this.RoleClassLib = roleClassLib;
	}
	public SystemUnitClassLib getSystemUnitClassLib() {
		return SystemUnitClassLib;
	}
	public void setSystemUnitClassLib(SystemUnitClassLib systemUnitClassLib) {
		this.SystemUnitClassLib = systemUnitClassLib;
	}
}
