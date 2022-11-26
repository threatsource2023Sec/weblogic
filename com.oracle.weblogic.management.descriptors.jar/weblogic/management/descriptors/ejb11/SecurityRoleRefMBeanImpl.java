package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SecurityRoleRefMBeanImpl extends XMLElementMBeanDelegate implements SecurityRoleRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_roleLink = false;
   private String roleLink;
   private boolean isSet_roleName = false;
   private String roleName;
   private boolean isSet_description = false;
   private String description;

   public String getRoleLink() {
      return this.roleLink;
   }

   public void setRoleLink(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.roleLink;
      this.roleLink = value;
      this.isSet_roleLink = value != null;
      this.checkChange("roleLink", old, this.roleLink);
   }

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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<security-role-ref");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getRoleName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<role-name>").append(this.getRoleName()).append("</role-name>\n");
      }

      if (null != this.getRoleLink()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<role-link>").append(this.getRoleLink()).append("</role-link>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</security-role-ref>\n");
      return result.toString();
   }
}
