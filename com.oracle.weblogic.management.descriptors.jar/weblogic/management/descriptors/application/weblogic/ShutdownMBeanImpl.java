package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ShutdownMBeanImpl extends XMLElementMBeanDelegate implements ShutdownMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_shutdownUri = false;
   private String shutdownUri;
   private boolean isSet_shutdownClass = false;
   private String shutdownClass;

   public String getShutdownUri() {
      return this.shutdownUri;
   }

   public void setShutdownUri(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.shutdownUri;
      this.shutdownUri = value;
      this.isSet_shutdownUri = value != null;
      this.checkChange("shutdownUri", old, this.shutdownUri);
   }

   public String getShutdownClass() {
      return this.shutdownClass;
   }

   public void setShutdownClass(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.shutdownClass;
      this.shutdownClass = value;
      this.isSet_shutdownClass = value != null;
      this.checkChange("shutdownClass", old, this.shutdownClass);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<shutdown");
      result.append(">\n");
      if (null != this.getShutdownClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<shutdown-class>").append(this.getShutdownClass()).append("</shutdown-class>\n");
      }

      if (null != this.getShutdownUri()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<shutdown-uri>").append(this.getShutdownUri()).append("</shutdown-uri>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</shutdown>\n");
      return result.toString();
   }
}
