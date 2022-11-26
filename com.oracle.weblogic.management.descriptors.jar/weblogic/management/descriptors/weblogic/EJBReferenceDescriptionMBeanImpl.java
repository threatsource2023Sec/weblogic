package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBReferenceDescriptionMBeanImpl extends XMLElementMBeanDelegate implements EJBReferenceDescriptionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_jndiName = false;
   private String jndiName;
   private boolean isSet_ejbRefName = false;
   private String ejbRefName;

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

   public String getEJBRefName() {
      return this.ejbRefName;
   }

   public void setEJBRefName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbRefName;
      this.ejbRefName = value;
      this.isSet_ejbRefName = value != null;
      this.checkChange("ejbRefName", old, this.ejbRefName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb-reference-description");
      result.append(">\n");
      if (null != this.getEJBRefName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-ref-name>").append(this.getEJBRefName()).append("</ejb-ref-name>\n");
      }

      if (null != this.getJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jndi-name>").append(this.getJNDIName()).append("</jndi-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-reference-description>\n");
      return result.toString();
   }
}
