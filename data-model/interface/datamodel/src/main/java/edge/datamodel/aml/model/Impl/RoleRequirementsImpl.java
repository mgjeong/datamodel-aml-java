package edge.datamodel.aml.model.Impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edge.datamodel.aml.model.RoleRequirements;

/**
 * implements RoleRequirements object  
 *     
 */

@XmlRootElement(name="RoleRequirements")
public class RoleRequirementsImpl implements RoleRequirements{
	private String RefBaseRoleClassPath;
	
	@XmlElement(name="RefBaseRoleClassPath")
	public String getRefBaseRoleClassPath() {
		return RefBaseRoleClassPath;
	}
	public void setRefBaseRoleClassPath(String refBaseRoleClassPath) {
		this.RefBaseRoleClassPath = refBaseRoleClassPath;
	}
}
