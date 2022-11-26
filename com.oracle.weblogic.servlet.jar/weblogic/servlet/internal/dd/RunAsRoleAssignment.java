package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.management.descriptors.webappext.RunAsRoleAssignmentMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class RunAsRoleAssignment extends BaseServletDescriptor implements RunAsRoleAssignmentMBean {
   private static final String RUN_AS_IDENTITY = "run-as-role-assignment";
   private static final String ROLE_NAME = "role-name";
   private static final String RUN_AS_PRINCIPAL_NAME = "run-as-principal-name";
   private String runAsPrincipalName = null;
   private SecurityRoleMBean securityRole;

   public RunAsRoleAssignment(SecurityRoleMBean role) {
      this.securityRole = role;
      if (this.securityRole == null) {
         throw new NullPointerException();
      } else {
         this.runAsPrincipalName = null;
      }
   }

   public RunAsRoleAssignment(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      String roleName = DOMUtils.getValueByTagName(parentElement, "role-name");
      this.securityRole = this.findRole(wad, roleName);
      if (this.securityRole == null) {
         throw new DOMProcessingException("SecurityRole with name " + roleName + " not defined in web.xml");
      } else {
         this.setRunAsPrincipalName(DOMUtils.getValueByTagName(parentElement, "run-as-principal-name"));
      }
   }

   public void setRoleName(String name) throws DOMProcessingException {
      this.securityRole.setRoleName(name);
      this.setIdentity();
   }

   public String getRoleName() {
      return this.securityRole.getRoleName();
   }

   public void setSecurityRole(SecurityRoleMBean securityRole) {
      if (securityRole == null) {
         throw new NullPointerException();
      } else {
         this.securityRole = securityRole;
         this.setIdentity();
      }
   }

   public SecurityRoleMBean getSecurityRole() {
      return this.securityRole;
   }

   public void setRunAsPrincipalName(String name) {
      String old = this.runAsPrincipalName;
      this.runAsPrincipalName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("runAsPrincipalName", old, name);
      }

      this.setIdentity();
   }

   public String getRunAsPrincipalName() {
      return this.runAsPrincipalName;
   }

   private void setIdentity() {
      if (this.securityRole != null && this.runAsPrincipalName != null) {
         this.securityRole.setRunAsIdentity(this.runAsPrincipalName);
      }

   }

   private SecurityRoleMBean findRole(WebAppDescriptor wad, String roleName) {
      SecurityRoleMBean[] s = wad.getSecurityRoles();
      if (s == null) {
         return null;
      } else {
         SecurityRoleMBean ret = null;

         for(int i = 0; i < s.length; ++i) {
            if (s[i] != null && roleName.equals(s[i].getRoleName())) {
               ret = s[i];
               break;
            }
         }

         return ret;
      }
   }

   public String toString() {
      return this.getRoleName();
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = this.getRunAsPrincipalName();
      if (result != null && result.trim().length() != 0) {
         result = "";
         result = result + this.indentStr(indent) + "<" + "run-as-role-assignment" + ">\n";
         indent += 2;
         result = result + this.indentStr(indent) + "<" + "role-name" + ">" + this.getRoleName() + "</" + "role-name" + ">\n";
         result = result + this.indentStr(indent) + "<" + "run-as-principal-name" + ">" + this.getRunAsPrincipalName() + "</" + "run-as-principal-name" + ">\n";
         indent -= 2;
         result = result + this.indentStr(indent) + "</" + "run-as-role-assignment" + ">\n";
         return result;
      } else {
         return "";
      }
   }
}
