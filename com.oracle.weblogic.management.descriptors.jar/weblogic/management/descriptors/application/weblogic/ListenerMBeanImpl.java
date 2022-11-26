package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ListenerMBeanImpl extends XMLElementMBeanDelegate implements ListenerMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_listenerUri = false;
   private String listenerUri;
   private boolean isSet_listenerClass = false;
   private String listenerClass;
   private boolean isSet_runAsPrincipalName = false;
   private String runAsPrincipalName;

   public String getListenerUri() {
      return this.listenerUri;
   }

   public void setListenerUri(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.listenerUri;
      this.listenerUri = value;
      this.isSet_listenerUri = value != null;
      this.checkChange("listenerUri", old, this.listenerUri);
   }

   public String getListenerClass() {
      return this.listenerClass;
   }

   public void setListenerClass(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.listenerClass;
      this.listenerClass = value;
      this.isSet_listenerClass = value != null;
      this.checkChange("listenerClass", old, this.listenerClass);
   }

   public String getRunAsPrincipalName() {
      return this.runAsPrincipalName;
   }

   public void setRunAsPrincipalName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.runAsPrincipalName;
      this.runAsPrincipalName = value;
      this.isSet_runAsPrincipalName = value != null;
      this.checkChange("runAsPrincipalName", old, this.runAsPrincipalName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<listener");
      result.append(">\n");
      if (null != this.getListenerClass()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<listener-class>").append(this.getListenerClass()).append("</listener-class>\n");
      }

      if (null != this.getListenerUri()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<listener-uri>").append(this.getListenerUri()).append("</listener-uri>\n");
      }

      if (null != this.getRunAsPrincipalName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<run-as-principal-name>").append(this.getRunAsPrincipalName()).append("</run-as-principal-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</listener>\n");
      return result.toString();
   }
}
