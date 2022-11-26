package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ParameterMBeanImpl extends XMLElementMBeanDelegate implements ParameterMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_paramValue = false;
   private String paramValue;
   private boolean isSet_paramName = false;
   private String paramName;

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.description;
      this.description = value;
      this.isSet_description = value != null;
      this.checkChange("description", old, this.description);
   }

   public String getParamValue() {
      return this.paramValue;
   }

   public void setParamValue(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.paramValue;
      this.paramValue = value;
      this.isSet_paramValue = value != null;
      this.checkChange("paramValue", old, this.paramValue);
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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<parameter");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</parameter>\n");
      return result.toString();
   }
}
