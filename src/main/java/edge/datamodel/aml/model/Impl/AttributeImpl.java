package edge.datamodel.aml.model.Impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.Attribute;

/**
 * implements Attribute object  
 *     
 */

//@XmlRootElement(name="Attribute")
@XmlType(propOrder = {"name","attributeDataType","description","value","attribute"})
public class AttributeImpl extends CommonElementImpl implements Attribute{
	private String AttributeDataType;
	private String Description;
	private String Value;
	private List<Attribute> Attribute;
	
	/**
	 * @fn AttributeImpl()
	 * @brief Create new Attribute
	 */
	
	public AttributeImpl() {
	};
	
	/**
	 * @fn AttributeImpl(String name)
	 * @brief Create new Attribute and set Attribute's name 
	 * @param [in] name	Attribute name
	 */
	public AttributeImpl(String name) {
		this.setName(name);
	};
	
	/**
	 * @fn AttributeImpl(String name, String attributeDataType)
	 * @brief Create new Attribute and set Attribute's name and datatype
	 * @param [in] name	Attribute name
	 * @param [in] attributeDataType	Attribute datatype
	 */
	public AttributeImpl(String name, String attributeDataType) {
		this.setName(name);
		this.setAttributeDataType(attributeDataType);
	};
	
	/**
	 * @fn AttributeImpl(String name, String attributeDataType, String value)
	 * @brief Create new Attribute and set Attribute's name, datatype and value
	 * @param [in] name	Attribute name
	 * @param [in] attributeDataType	Attribute datatype
	 * @param [in] value	Attribute value
	 */
	public AttributeImpl(String name, String attributeDataType, String value) {
		this.setName(name);
		this.setAttributeDataType(attributeDataType);
		this.setValue(value);
	};
	
	/**
	 * @fn AttributeImpl(String name, String attributeDataType, String value, String description)
	 * @brief Create new Attribute and set Attribute's name, datatype, value and description
	 * @param [in] name	Attribute name
	 * @param [in] attributeDataType	Attribute datatype
	 * @param [in] value	Attribute value
	 * @param [in] description	Attribute description
	 */
	public AttributeImpl(String name, String attributeDataType, String value, String description) {
		this.setName(name);
		this.setAttributeDataType(attributeDataType);
		this.setValue(value);
		this.setDescription(description);
	};	
	
	@Override
	@XmlAttribute(name="Name")
	public String getName() {
		return super.getName();
	}
	
	@XmlAttribute(name="AttributeDataType")
	public String getAttributeDataType() {
		return AttributeDataType;
	}
	public void setAttributeDataType(String attributeDataType) {
		AttributeDataType = attributeDataType;
	}
	@XmlElement(name="Description")
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	@XmlElement(name="Value")
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	
	@XmlElement(type=AttributeImpl.class, name="Attribute")
	public List<Attribute> getAttribute() {
		return Attribute;
	}

	public void setAttribute(List<Attribute> attribute) {
		this.Attribute = attribute;
	}

	public void addSubAttribute(String name) {
		Attribute newAtt = new AttributeImpl(name);
		this.Attribute.add(newAtt);
		
	}
	
	public Attribute searchAttribute(String name) {
		for(Attribute att: this.Attribute) {
			if(name.contentEquals(att.getName()) == true) {
				return att;
			}
		}
		return null;
	}
	
	public Attribute copyAttribute() {
		Attribute att = new AttributeImpl();
		try { 
			att = (Attribute) this.clone();
		} catch(CloneNotSupportedException e) {
			System.out.println("Clone not supported");
		}
		return att;
	}
}
