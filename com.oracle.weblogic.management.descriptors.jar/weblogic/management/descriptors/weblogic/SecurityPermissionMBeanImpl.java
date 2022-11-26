package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SecurityPermissionMBeanImpl extends XMLElementMBeanDelegate implements SecurityPermissionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_securityPermissionSpec = false;
   private String securityPermissionSpec;

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

   public String getSecurityPermissionSpec() {
      return this.securityPermissionSpec;
   }

   public void setSecurityPermissionSpec(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.securityPermissionSpec;
      this.securityPermissionSpec = value;
      this.isSet_securityPermissionSpec = value != null;
      this.checkChange("securityPermissionSpec", old, this.securityPermissionSpec);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<security-permission");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</security-permission>\n");
      return result.toString();
   }
}
