package edge.datamodel.aml.model.Impl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import edge.datamodel.aml.model.SupportedRoleClass;

/**
 * implements RoleRequirements object  
 *     
 */

@XmlRootElement(name="SupportedRoleClass")
public class SupportedRoleClassImpl implements SupportedRoleClass{
	private String RefRoleClassPath;
	
	@XmlAttribute(name="RefRoleClassPath")
	public String getRefRoleClassPath() {
		return RefRoleClassPath;
	}
	public void setRefRoleClassPath(String refBaseRoleClassPath) {
		this.RefRoleClassPath = refBaseRoleClassPath;
	}
}
