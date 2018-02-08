package edge.datamodel.aml.model.Impl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edge.datamodel.aml.model.CAEXFile;
import edge.datamodel.aml.model.InstanceHierarchy;
import edge.datamodel.aml.model.RoleClassLib;
import edge.datamodel.aml.model.SystemUnitClassLib;

/**
 * implements CAEXFile object  
 *     
 */
@XmlRootElement(name="CAEXFile")
@XmlType(propOrder = {"fileName","schemaVersion","noNamespace","xsi","instanceHierarchy","roleClassLib","systemUnitClassLib"})
public class CAEXFileImpl implements CAEXFile{
	private InstanceHierarchy InstanceHierarchy;
	private RoleClassLib RoleClassLib;
	private SystemUnitClassLib SystemUnitClassLib;
	
	private String FileName;
	private String SchemaVersion;
	private String noNamespace;
	private String xsi;
	
	/**
	 * @fn CAEXFileImpl()
	 * @brief Create new CAEXFile model includes empty RoleClassLib, SystemUnitClassLib and InstanceHierarchy with CAEX attributes.
	 */
	public CAEXFileImpl(){
		InstanceHierarchy = new InstanceHierarchyImpl();
		RoleClassLib = new RoleClassLibImpl();
		SystemUnitClassLib = new SystemUnitClassLibImpl();
		
		setFileName("test.aml");
		setSchemaVersion("2.15");
		setNoNamespace("CAEX_ClassModel_V2.15.xsd");
		setXsi("http://www.w3.org/2001/XMLSchema-instance");
	}
	
	/**
	 * @fn CAEXFileImpl(String filename)
	 * @brief Set CAEXFile's name. Create new CAEXFile model includes empty RoleClassLib, SystemUnitClassLib and InstanceHierarchy with CAEX attributes.
	 * @param [in] filename	CAEXFile name
	 */
	public CAEXFileImpl(String filename){
		InstanceHierarchy = new InstanceHierarchyImpl();
		RoleClassLib = new RoleClassLibImpl();
		SystemUnitClassLib = new SystemUnitClassLibImpl();
		
		setFileName(filename + ".aml");
		setSchemaVersion("2.15");
		setNoNamespace("CAEX_ClassModel_V2.15.xsd");
		setXsi("http://www.w3.org/2001/XMLSchema-instance");	
	}

	@XmlElement(type=InstanceHierarchyImpl.class, name="InstanceHierarchy")
	public InstanceHierarchy getInstanceHierarchy() {
		return InstanceHierarchy;
	}

	public void setInstanceHierarchy(InstanceHierarchy instanceHierarchy) {
		this.InstanceHierarchy = instanceHierarchy;
	}

	@XmlElement(type=RoleClassLibImpl.class, name="RoleClassLib")
	public RoleClassLib getRoleClassLib() {
		return RoleClassLib;
	}

	public void setRoleClassLib(RoleClassLib roleClassLib) {
		this.RoleClassLib = roleClassLib;		
	}

	@XmlElement(type=SystemUnitClassLibImpl.class, name="SystemUnitClassLib")
	public SystemUnitClassLib getSystemUnitClassLib() {
		return SystemUnitClassLib;
	}

	public void setSystemUnitClassLib(SystemUnitClassLib systemUnitClassLib) {
		this.SystemUnitClassLib = systemUnitClassLib;
	}

	@XmlAttribute(name="FileName")
	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	@XmlAttribute(name="SchemaVersion")
	public String getSchemaVersion() {
		return SchemaVersion;
	}

	public void setSchemaVersion(String schemaVersion) {
		SchemaVersion = schemaVersion;
	}

	@XmlAttribute(name="xsi:noNamespaceSchemaLocation")
	public String getNoNamespace() {
		return noNamespace;
	}

	public void setNoNamespace(String noNamespace) {
		this.noNamespace = noNamespace;
	}

	@XmlAttribute(name="xmlns:xsi")
	public String getXsi() {
		return xsi;
	}

	public void setXsi(String xsi) {
		this.xsi = xsi;
	}
}
