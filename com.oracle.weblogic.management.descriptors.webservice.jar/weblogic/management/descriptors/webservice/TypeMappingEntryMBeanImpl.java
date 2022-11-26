package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;
import weblogic.xml.stream.XMLName;

public class TypeMappingEntryMBeanImpl extends XMLElementMBeanDelegate implements TypeMappingEntryMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_xsdTypeName = false;
   private XMLName xsdTypeName;
   private boolean isSet_deserializerName = false;
   private String deserializerName;
   private boolean isSet_serializerName = false;
   private String serializerName;
   private boolean isSet_className = false;
   private String className;
   private boolean isSet_elementName = false;
   private XMLName elementName;

   public XMLName getXSDTypeName() {
      return this.xsdTypeName;
   }

   public void setXSDTypeName(XMLName value) {
      XMLName old = this.xsdTypeName;
      this.xsdTypeName = value;
      this.isSet_xsdTypeName = value != null;
      this.checkChange("xsdTypeName", old, this.xsdTypeName);
   }

   public String getDeserializerName() {
      return this.deserializerName;
   }

   public void setDeserializerName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.deserializerName;
      this.deserializerName = value;
      this.isSet_deserializerName = value != null;
      this.checkChange("deserializerName", old, this.deserializerName);
   }

   public String getSerializerName() {
      return this.serializerName;
   }

   public void setSerializerName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.serializerName;
      this.serializerName = value;
      this.isSet_serializerName = value != null;
      this.checkChange("serializerName", old, this.serializerName);
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.className;
      this.className = value;
      this.isSet_className = value != null;
      this.checkChange("className", old, this.className);
   }

   public XMLName getElementName() {
      return this.elementName;
   }

   public void setElementName(XMLName value) {
      XMLName old = this.elementName;
      this.elementName = value;
      this.isSet_elementName = value != null;
      this.checkChange("elementName", old, this.elementName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<type-mapping-entry");
      if (this.isSet_deserializerName) {
         result.append(" deserializer=\"").append(String.valueOf(this.getDeserializerName())).append("\"");
      }

      if (this.isSet_xsdTypeName) {
         result.append(" xmlns:").append(this.getXSDTypeName().getPrefix()).append("=\"").append(this.getXSDTypeName().getNamespaceUri()).append("\" type=\"").append(this.getXSDTypeName().getQualifiedName()).append("\"");
      }

      if (this.isSet_serializerName) {
         result.append(" serializer=\"").append(String.valueOf(this.getSerializerName())).append("\"");
      }

      if (this.isSet_className) {
         result.append(" class-name=\"").append(String.valueOf(this.getClassName())).append("\"");
      }

      if (this.isSet_elementName) {
         result.append(" xmlns:").append(this.getElementName().getPrefix()).append("=\"").append(this.getElementName().getNamespaceUri()).append("\" element=\"").append(this.getElementName().getQualifiedName()).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</type-mapping-entry>\n");
      return result.toString();
   }
}
