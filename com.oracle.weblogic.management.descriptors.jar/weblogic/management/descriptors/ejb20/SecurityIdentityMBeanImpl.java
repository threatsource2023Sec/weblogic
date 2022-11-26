package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SecurityIdentityMBeanImpl extends XMLElementMBeanDelegate implements SecurityIdentityMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_useCallerIdentity = false;
   private boolean useCallerIdentity;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_runAs = false;
   private RunAsMBean runAs;

   public boolean getUseCallerIdentity() {
      return this.useCallerIdentity;
   }

   public void setUseCallerIdentity(boolean value) {
      boolean old = this.useCallerIdentity;
      this.useCallerIdentity = value;
      this.isSet_useCallerIdentity = true;
      this.checkChange("useCallerIdentity", old, this.useCallerIdentity);
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.description;
      this.description = value;
      this.isSet_description = value != null;
      this.checkChange("description", old, this.description);
   }

   public RunAsMBean getRunAs() {
      return this.runAs;
   }

   public void setRunAs(RunAsMBean value) {
      RunAsMBean old = this.runAs;
      this.runAs = value;
      this.isSet_runAs = value != null;
      this.checkChange("runAs", old, this.runAs);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<security-identity");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getRunAs()) {
         result.append(this.getRunAs().toXML(indentLevel + 2)).append("\n");
      } else if (this.isSet_useCallerIdentity) {
         result.append(ToXML.indent(indentLevel + 2)).append(this.getUseCallerIdentity() ? "<use-caller-identity/>\n" : "");
      }

      result.append(ToXML.indent(indentLevel)).append("</security-identity>\n");
      return result.toString();
   }
}
