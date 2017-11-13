package edge.datamodel.aml.model.Impl;

import javax.xml.bind.annotation.XmlTransient;

import edge.datamodel.aml.model.CommonElement;

/**
 * implements CommonElement object  
 *     
 */
@XmlTransient
public class CommonElementImpl implements CommonElement{
	private String Name;
	
	public String getName() {
		return this.Name;
	}
	public void setName(String name) {
		this.Name = name;		
	}
}
