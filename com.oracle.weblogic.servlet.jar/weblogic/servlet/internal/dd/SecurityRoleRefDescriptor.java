package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.management.descriptors.webapp.SecurityRoleRefMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class SecurityRoleRefDescriptor extends BaseServletDescriptor implements SecurityRoleRefMBean, ToXML {
   private static final long serialVersionUID = 5435403745362765252L;
   private String description;
   private String roleName;
   private SecurityRoleMBean roleLink;
   private String linkName;

   public SecurityRoleRefDescriptor() {
   }

   public SecurityRoleRefDescriptor(SecurityRoleRefMBean mbean) {
      this.setDescription(mbean.getDescription());
      this.setRoleName(mbean.getRoleName());
      this.setRoleLink(mbean.getRoleLink());
   }

   public SecurityRoleRefDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setRoleName(DOMUtils.getValueByTagName(parentElement, "role-name"));
      this.linkName = DOMUtils.getOptionalValueByTagName(parentElement, "role-link");
      SecurityRoleMBean[] wadRole = wad.getSecurityRoles();

      for(int i = 0; i < wadRole.length; ++i) {
         if (wadRole[i].getRoleName().equals(this.linkName)) {
            this.setRoleLink(wadRole[i]);
         }
      }

   }

   public void setDescription(String d) {
      String old = this.description;
      this.description = d;
      if (!comp(old, d)) {
         this.firePropertyChange("description", this.description, d);
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setRoleName(String d) {
      String old = this.roleName;
      this.roleName = d;
      if (!comp(old, d)) {
         this.firePropertyChange("roleName", old, d);
      }

   }

   public String getRoleName() {
      return this.roleName;
   }

   public void setRoleLink(SecurityRoleMBean d) {
      this.linkName = null;
      SecurityRoleMBean old = this.roleLink;
      this.roleLink = d;
      if (!comp(old, d)) {
         this.firePropertyChange("roleLink", old, d);
      }

   }

   public SecurityRoleMBean getRoleLink() {
      return this.roleLink;
   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.roleName == null || this.roleName.length() <= 0) {
         this.addDescriptorError("NO_SECURITY_ROLE_NAME_SET");
         ok = false;
      }

      if (this.roleLink == null) {
         this.addDescriptorError("NO_SECURITY_ROLE_LINK_SET");
         ok = false;
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<security-role-ref>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<role-name>" + this.getRoleName() + "</role-name>\n";
      SecurityRoleMBean mb = this.getRoleLink();
      if (mb != null) {
         result = result + this.indentStr(indent) + "<role-link>" + mb.getRoleName() + "</role-link>\n";
      } else if (this.linkName != null && (this.linkName = this.linkName.trim()).length() != 0) {
         result = result + this.indentStr(indent) + "<role-link>" + this.linkName + "</role-link>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</security-role-ref>\n";
      return result;
   }
}
