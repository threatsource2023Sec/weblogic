package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;
import weblogic.xml.stream.XMLName;

public class ParamMBeanImpl extends XMLElementMBeanDelegate implements ParamMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_implicit = false;
   private boolean implicit;
   private boolean isSet_contentType = false;
   private String contentType;
   private boolean isSet_paramName = false;
   private String paramName;
   private boolean isSet_paramType = false;
   private XMLName paramType;
   private boolean isSet_paramStyle = false;
   private String paramStyle;
   private boolean isSet_className = false;
   private String className;
   private boolean isSet_location = false;
   private String location;

   public boolean isImplicit() {
      return this.implicit;
   }

   public void setImplicit(boolean value) {
      boolean old = this.implicit;
      this.implicit = value;
      this.isSet_implicit = true;
      this.checkChange("implicit", old, this.implicit);
   }

   public String getContentType() {
      return this.contentType;
   }

   public void setContentType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.contentType;
      this.contentType = value;
      this.isSet_contentType = value != null;
      this.checkChange("contentType", old, this.contentType);
   }

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

   public String getParamStyle() {
      return this.paramStyle;
   }

   public void setParamStyle(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.paramStyle;
      this.paramStyle = value;
      this.isSet_paramStyle = value != null;
      this.checkChange("paramStyle", old, this.paramStyle);
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
      result.append(ToXML.indent(indentLevel)).append("<param");
      if (this.isSet_implicit) {
         result.append(" implicit=\"").append(String.valueOf(this.isImplicit())).append("\"");
      }

      if (this.isSet_paramStyle) {
         result.append(" style=\"").append(String.valueOf(this.getParamStyle())).append("\"");
      }

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

      if (this.isSet_contentType) {
         result.append(" content-type=\"").append(String.valueOf(this.getContentType())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</param>\n");
      return result.toString();
   }
}
