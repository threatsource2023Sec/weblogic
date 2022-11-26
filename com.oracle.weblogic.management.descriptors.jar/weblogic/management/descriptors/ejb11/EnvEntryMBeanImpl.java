package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EnvEntryMBeanImpl extends XMLElementMBeanDelegate implements EnvEntryMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_envEntryValue = false;
   private String envEntryValue;
   private boolean isSet_envEntryName = false;
   private String envEntryName;
   private boolean isSet_envEntryType = false;
   private String envEntryType;

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

   public String getEnvEntryValue() {
      return this.envEntryValue;
   }

   public void setEnvEntryValue(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.envEntryValue;
      this.envEntryValue = value;
      this.isSet_envEntryValue = value != null;
      this.checkChange("envEntryValue", old, this.envEntryValue);
   }

   public String getEnvEntryName() {
      return this.envEntryName;
   }

   public void setEnvEntryName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.envEntryName;
      this.envEntryName = value;
      this.isSet_envEntryName = value != null;
      this.checkChange("envEntryName", old, this.envEntryName);
   }

   public String getEnvEntryType() {
      return this.envEntryType;
   }

   public void setEnvEntryType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.envEntryType;
      this.envEntryType = value;
      this.isSet_envEntryType = value != null;
      this.checkChange("envEntryType", old, this.envEntryType);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<env-entry");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEnvEntryName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<env-entry-name>").append(this.getEnvEntryName()).append("</env-entry-name>\n");
      }

      if (null != this.getEnvEntryType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<env-entry-type>").append(this.getEnvEntryType()).append("</env-entry-type>\n");
      }

      if (null != this.getEnvEntryValue()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<env-entry-value>").append(this.getEnvEntryValue()).append("</env-entry-value>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</env-entry>\n");
      return result.toString();
   }
}
