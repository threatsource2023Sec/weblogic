package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StartupMBeanImpl extends XMLElementMBeanDelegate implements StartupMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_startupClass = false;
   private String startupClass;
   private boolean isSet_startupUri = false;
   private String startupUri;

   public String getStartupClass() {
      return this.startupClass;
   }

   public void setStartupClass(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.startupClass;
      this.startupClass = value;
      this.isSet_startupClass = value != null;
      this.checkChange("startupClass", old, this.startupClass);
   }

   public String getStartupUri() {
      return this.startupUri;
   }

   public void setStartupUri(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.startupUri;
      this.startupUri = value;
      this.isSet_startupUri = value != null;
      this.checkChange("startupUri", old, this.startupUri);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<startup");
      result.append(">\n");
      if (null != this.getStartupClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<startup-class>").append(this.getStartupClass()).append("</startup-class>\n");
      }

      if (null != this.getStartupUri()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<startup-uri>").append(this.getStartupUri()).append("</startup-uri>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</startup>\n");
      return result.toString();
   }
}
