package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ModuleRefMBeanImpl extends XMLElementMBeanDelegate implements ModuleRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_moduleUri = false;
   private String moduleUri;

   public String getModuleUri() {
      return this.moduleUri;
   }

   public void setModuleUri(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.moduleUri;
      this.moduleUri = value;
      this.isSet_moduleUri = value != null;
      this.checkChange("moduleUri", old, this.moduleUri);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<module-ref");
      result.append(">\n");
      if (null != this.getModuleUri()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<module-uri>").append(this.getModuleUri()).append("</module-uri>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</module-ref>\n");
      return result.toString();
   }
}
