package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class InitParamMBeanImpl extends XMLElementMBeanDelegate implements InitParamMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_paramName = false;
   private String paramName;
   private boolean isSet_paramValue = false;
   private String paramValue;

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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<init-param");
      if (this.isSet_paramValue) {
         result.append(" value=\"").append(String.valueOf(this.getParamValue())).append("\"");
      }

      if (this.isSet_paramName) {
         result.append(" name=\"").append(String.valueOf(this.getParamName())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</init-param>\n");
      return result.toString();
   }
}
