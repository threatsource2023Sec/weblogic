package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StatelessEJBMBeanImpl extends XMLElementMBeanDelegate implements StatelessEJBMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_componentName = false;
   private String componentName;
   private boolean isSet_ejbLink = false;
   private EJBLinkMBean ejbLink;
   private boolean isSet_jndiName = false;
   private JNDINameMBean jndiName;

   public String getComponentName() {
      return this.componentName;
   }

   public void setComponentName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.componentName;
      this.componentName = value;
      this.isSet_componentName = value != null;
      this.checkChange("componentName", old, this.componentName);
   }

   public EJBLinkMBean getEJBLink() {
      return this.ejbLink;
   }

   public void setEJBLink(EJBLinkMBean value) {
      EJBLinkMBean old = this.ejbLink;
      this.ejbLink = value;
      this.isSet_ejbLink = value != null;
      this.checkChange("ejbLink", old, this.ejbLink);
   }

   public JNDINameMBean getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(JNDINameMBean value) {
      JNDINameMBean old = this.jndiName;
      this.jndiName = value;
      this.isSet_jndiName = value != null;
      this.checkChange("jndiName", old, this.jndiName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<stateless-ejb");
      if (this.isSet_componentName) {
         result.append(" name=\"").append(String.valueOf(this.getComponentName())).append("\"");
      }

      result.append(">\n");
      if (null != this.getEJBLink()) {
         result.append(this.getEJBLink().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getJNDIName()) {
         result.append(this.getJNDIName().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</stateless-ejb>\n");
      return result.toString();
   }
}
