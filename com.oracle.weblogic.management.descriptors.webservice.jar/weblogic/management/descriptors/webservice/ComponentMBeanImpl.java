package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ComponentMBeanImpl extends XMLElementMBeanDelegate implements ComponentMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_componentName = false;
   private String componentName;

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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<component");
      if (this.isSet_componentName) {
         result.append(" name=\"").append(String.valueOf(this.getComponentName())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</component>\n");
      return result.toString();
   }
}
