package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ModuleProviderMBeanImpl extends XMLElementMBeanDelegate implements ModuleProviderMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_moduleFactoryClassName = false;
   private String moduleFactoryClassName;

   public String getModuleFactoryClassName() {
      return this.moduleFactoryClassName;
   }

   public void setModuleFactoryClassName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.moduleFactoryClassName;
      this.moduleFactoryClassName = value;
      this.isSet_moduleFactoryClassName = value != null;
      this.checkChange("moduleFactoryClassName", old, this.moduleFactoryClassName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<module-provider");
      result.append(">\n");
      if (null != this.getName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<name>").append(this.getName()).append("</name>\n");
      }

      if (null != this.getModuleFactoryClassName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<module-factory-class-name>").append(this.getModuleFactoryClassName()).append("</module-factory-class-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</module-provider>\n");
      return result.toString();
   }
}
