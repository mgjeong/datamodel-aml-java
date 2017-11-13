package edge.datamodel.aml.model;

import java.util.List;

/**
 * represents the Attribute it using AutomationML class 
 * defines base structure for Attribute definition 
 *    
 */

public interface Attribute extends CommonElement, Cloneable{
	/**
	 * @fn String getAttributeDataType()
	 * @brief Returns the Attribute data type
	 * @return String	datatype string value
	 */
	public String getAttributeDataType();
	
	/**
	 * @fn void setAttributeDataType(String attributeDataType)
	 * @brief Sets the Attribute data type
	 * @param [in] attributeDataType	datatype string value
	 * @return void
	 */
	public void setAttributeDataType(String attributeDataType);
	
	/**
	 * @fn String getDescription()
	 * @brief Returns the description about Attribute 
	 * @return String	description
	 */
	public String getDescription();
	
	/**
	 * @fn void setDescription(String description)
	 * @brief Sets the description about Attribute
	 * @param [in] description	description of Attribute
	 * @return void
	 */
	public void setDescription(String description);
	
	/**
	 * @fn String getValue()
	 * @brief Returns the Attribute value as String type
	 * @return String	Attribute value
	 */
	public String getValue();
	
	/**
	 * @fn void setValue(String value)
	 * @brief Sets the Attribute value
	 * @param [in] value	Attribute value as String type
	 * @return void
	 */
	public void setValue(String value);
	
	/**
	 * @fn List<Attribute> getAttribute()
	 * @brief Returns the inner Attribute list 
	 * @return List<Attribute>	inner attributes list
	 */
	public List<Attribute> getAttribute();
	
	/**
	 * @fn void setAttribute(List<Attribute> attribute)
	 * @brief Sets the List of inner attributes
	 * @param [in] attribute	List of Attribute
	 * @return void
	 */
	public void setAttribute(List<Attribute> attribute);
	
	/**
	 * @fn void addSubAttribute(String name)
	 * @brief Adds the new subattribute at the inner attribute list
	 * @param [in] name	new attribute name
	 * @return void
	 */
	public void addSubAttribute(String name);
	
	/**
	 * @fn Attribute searchAttribute(String name)
	 * @brief Searches the attribute at the inner attribute list
	 * @param [in] name	attribute name to search
	 * @return Attribute
	 */
	public Attribute searchAttribute(String name);
	
	/**
	 * @fn Attribute copyAttribute()
	 * @brief Returns the new attribute has copied value this attribute   
	 * @return Attribute new attribute have copied value
	 */
	public Attribute copyAttribute();	
}
