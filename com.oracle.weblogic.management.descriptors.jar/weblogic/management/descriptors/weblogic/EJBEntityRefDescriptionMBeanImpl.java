package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBEntityRefDescriptionMBeanImpl extends XMLElementMBeanDelegate implements EJBEntityRefDescriptionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_jndiName = false;
   private String jndiName;
   private boolean isSet_remoteEJBName = false;
   private String remoteEJBName;

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

   public String getRemoteEJBName() {
      return this.remoteEJBName;
   }

   public void setRemoteEJBName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.remoteEJBName;
      this.remoteEJBName = value;
      this.isSet_remoteEJBName = value != null;
      this.checkChange("remoteEJBName", old, this.remoteEJBName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb-entity-ref-description");
      result.append(">\n");
      if (null != this.getRemoteEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<remote-ejb-name>").append(this.getRemoteEJBName()).append("</remote-ejb-name>\n");
      }

      if (null != this.getJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jndi-name>").append(this.getJNDIName()).append("</jndi-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-entity-ref-description>\n");
      return result.toString();
   }
}
