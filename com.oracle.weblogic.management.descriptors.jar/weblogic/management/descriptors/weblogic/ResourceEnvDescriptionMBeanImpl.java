package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ResourceEnvDescriptionMBeanImpl extends XMLElementMBeanDelegate implements ResourceEnvDescriptionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_resEnvRefName = false;
   private String resEnvRefName;
   private boolean isSet_jndiName = false;
   private String jndiName;

   public String getResEnvRefName() {
      return this.resEnvRefName;
   }

   public void setResEnvRefName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resEnvRefName;
      this.resEnvRefName = value;
      this.isSet_resEnvRefName = value != null;
      this.checkChange("resEnvRefName", old, this.resEnvRefName);
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.jndiName;
      this.jndiName = value;
      this.isSet_jndiName = value != null;
      this.checkChange("jndiName", old, this.jndiName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<resource-env-description");
      result.append(">\n");
      if (null != this.getResEnvRefName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<res-env-ref-name>").append(this.getResEnvRefName()).append("</res-env-ref-name>\n");
      }

      if (null != this.getJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jndi-name>").append(this.getJNDIName()).append("</jndi-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</resource-env-description>\n");
      return result.toString();
   }
}
