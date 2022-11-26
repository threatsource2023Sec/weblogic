@start rule: main
package @package_name;

public class @element_class_name extends weblogic.xml.dtdc.XmlElement implements java.lang.Cloneable {

  // ============================================================
  // Constructor

  public @element_class_name(java.lang.String name, org.xml.sax.AttributeList attributes) throws org.xml.sax.SAXException {
    if(!name.equals("@element_name")) {
      throw new org.xml.sax.SAXException("Attempt to construct a " + getClass().getName() + " with a " +
                             name + " element");
    }
    initialize(name, attributes);
  }

  public @element_class_name() {}

  public void initialize(java.lang.String name, org.xml.sax.AttributeList attributes) throws org.xml.sax.SAXException {
@set_attributes
  }
  
  public boolean isEmpty() {
    return @is_empty;
  }

  public java.lang.String getElementName() { return "@element_realname"; }

  // ============================================================
  // Attributes

@attributes
  // ============================================================
  // Contained Elements

  public @element_class_name addDataElement(java.lang.String string) {
    return (@element_class_name) super._addDataElement(string);
  }

  // ============================================================
  // Cloneable implementation

  public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
    return super.clone();
  }

  
@subelements

}

@end rule: main

@start rule: set_attribute
    if (attributes.getValue("@attribute_realname") != null) {
	@attribute_varname = attributes.getValue("@attribute_realname");
    }
    attributeValues.put("@attribute_realname", @attribute_varname);
@end rule: set_attribute

@start rule: attribute
  private java.lang.String @attribute_varname="@attribute_default";
  public java.lang.String @attribute_value_getter() {
    return @attribute_varname == null ? "" : @attribute_varname;
  }
  public @element_class_name @attribute_value_setter(java.lang.String value) {
    @attribute_varname = value;

    attributeValues.put("@attribute_realname", @attribute_varname);
    return this;
  }

@end rule: attribute

@start rule: subelement
  private java.util.List @sub_element_varname = new java.util.ArrayList();
  public java.util.List @sub_elements_getter() { return @sub_element_varname; }
  public @element_class_name @sub_element_adder(@sub_element_class_name subelement) {
    @sub_element_varname.add(subelement);
    subElements.add(subelement);
    return this;
  }

@end rule: subelement

