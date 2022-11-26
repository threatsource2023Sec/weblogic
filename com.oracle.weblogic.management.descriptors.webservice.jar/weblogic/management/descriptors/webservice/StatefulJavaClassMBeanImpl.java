package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StatefulJavaClassMBeanImpl extends XMLElementMBeanDelegate implements StatefulJavaClassMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_componentName = false;
   private String componentName;
   private boolean isSet_className = false;
   private String className;

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

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.className;
      this.className = value;
      this.isSet_className = value != null;
      this.checkChange("className", old, this.className);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<stateful-java-class");
      if (this.isSet_componentName) {
         result.append(" name=\"").append(String.valueOf(this.getComponentName())).append("\"");
      }

      if (this.isSet_className) {
         result.append(" class-name=\"").append(String.valueOf(this.getClassName())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</stateful-java-class>\n");
      return result.toString();
   }
}
