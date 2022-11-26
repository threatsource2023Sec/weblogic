package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;
import weblogic.xml.stream.XMLName;

public class ReturnParamMBeanImpl extends XMLElementMBeanDelegate implements ReturnParamMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_paramName = false;
   private String paramName;
   private boolean isSet_paramType = false;
   private XMLName paramType;
   private boolean isSet_className = false;
   private String className;
   private boolean isSet_location = false;
   private String location;

   public String getParamName() {
      return this.paramName;
   }

   public void setParamName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.paramName;
      this.paramName = value;
      this.isSet_paramName = value != null;
      this.checkChange("paramName", old, this.paramName);
   }

   public XMLName getParamType() {
      return this.paramType;
   }

   public void setParamType(XMLName value) {
      XMLName old = this.paramType;
      this.paramType = value;
      this.isSet_paramType = value != null;
      this.checkChange("paramType", old, this.paramType);
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

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.location;
      this.location = value;
      this.isSet_location = value != null;
      this.checkChange("location", old, this.location);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<return-param");
      if (this.isSet_paramType) {
         result.append(" xmlns:").append(this.getParamType().getPrefix()).append("=\"").append(this.getParamType().getNamespaceUri()).append("\" type=\"").append(this.getParamType().getQualifiedName()).append("\"");
      }

      if (this.isSet_location) {
         result.append(" location=\"").append(String.valueOf(this.getLocation())).append("\"");
      }

      if (this.isSet_paramName) {
         result.append(" name=\"").append(String.valueOf(this.getParamName())).append("\"");
      }

      if (this.isSet_className) {
         result.append(" class-name=\"").append(String.valueOf(this.getClassName())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</return-param>\n");
      return result.toString();
   }
}
