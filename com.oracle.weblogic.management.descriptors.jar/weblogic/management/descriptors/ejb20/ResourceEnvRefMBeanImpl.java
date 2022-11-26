package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ResourceEnvRefMBeanImpl extends XMLElementMBeanDelegate implements ResourceEnvRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_resourceEnvRefType = false;
   private String resourceEnvRefType;
   private boolean isSet_resourceEnvRefName = false;
   private String resourceEnvRefName;

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

   public String getResourceEnvRefType() {
      return this.resourceEnvRefType;
   }

   public void setResourceEnvRefType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resourceEnvRefType;
      this.resourceEnvRefType = value;
      this.isSet_resourceEnvRefType = value != null;
      this.checkChange("resourceEnvRefType", old, this.resourceEnvRefType);
   }

   public String getResourceEnvRefName() {
      return this.resourceEnvRefName;
   }

   public void setResourceEnvRefName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resourceEnvRefName;
      this.resourceEnvRefName = value;
      this.isSet_resourceEnvRefName = value != null;
      this.checkChange("resourceEnvRefName", old, this.resourceEnvRefName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<resource-env-ref");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getResourceEnvRefName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<resource-env-ref-name>").append(this.getResourceEnvRefName()).append("</resource-env-ref-name>\n");
      }

      if (null != this.getResourceEnvRefType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<resource-env-ref-type>").append(this.getResourceEnvRefType()).append("</resource-env-ref-type>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</resource-env-ref>\n");
      return result.toString();
   }
}
