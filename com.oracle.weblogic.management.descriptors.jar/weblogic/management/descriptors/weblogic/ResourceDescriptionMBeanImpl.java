package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ResourceDescriptionMBeanImpl extends XMLElementMBeanDelegate implements ResourceDescriptionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_jndiName = false;
   private String jndiName;
   private boolean isSet_resRefName = false;
   private String resRefName;

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

   public String getResRefName() {
      return this.resRefName;
   }

   public void setResRefName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resRefName;
      this.resRefName = value;
      this.isSet_resRefName = value != null;
      this.checkChange("resRefName", old, this.resRefName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<resource-description");
      result.append(">\n");
      if (null != this.getResRefName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<res-ref-name>").append(this.getResRefName()).append("</res-ref-name>\n");
      }

      if (null != this.getJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jndi-name>").append(this.getJNDIName()).append("</jndi-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</resource-description>\n");
      return result.toString();
   }
}
