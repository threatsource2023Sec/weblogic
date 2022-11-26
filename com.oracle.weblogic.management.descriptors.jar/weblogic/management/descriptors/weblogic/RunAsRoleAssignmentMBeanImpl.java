package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class RunAsRoleAssignmentMBeanImpl extends XMLElementMBeanDelegate implements RunAsRoleAssignmentMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_roleName = false;
   private String roleName;
   private boolean isSet_runAsPrincipalName = false;
   private String runAsPrincipalName;

   public String getRoleName() {
      return this.roleName;
   }

   public void setRoleName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.roleName;
      this.roleName = value;
      this.isSet_roleName = value != null;
      this.checkChange("roleName", old, this.roleName);
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
      result.append(ToXML.indent(indentLevel)).append("<run-as-role-assignment");
      result.append(">\n");
      if (null != this.getRoleName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<role-name>").append(this.getRoleName()).append("</role-name>\n");
      }

      if (null != this.getRunAsPrincipalName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<run-as-principal-name>").append(this.getRunAsPrincipalName()).append("</run-as-principal-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</run-as-role-assignment>\n");
      return result.toString();
   }
}
