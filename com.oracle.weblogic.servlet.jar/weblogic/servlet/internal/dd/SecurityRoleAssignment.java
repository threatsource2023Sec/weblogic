package weblogic.servlet.internal.dd;

import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.management.descriptors.webappext.SecurityRoleAssignmentMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class SecurityRoleAssignment extends BaseServletDescriptor implements SecurityRoleAssignmentMBean {
   private static final long serialVersionUID = -3874048726826475026L;
   private final String SECURITY_ROLE_ASSIGNMENT = "security-role-assignment";
   private final String ROLE_NAME = "role-name";
   private final String PRINCIPAL_NAME = "principal-name";
   private final String GLOBAL_ROLE = "global-role";
   private final String EXTERNALLY_DEFINED = "externally-defined";
   private SecurityRoleMBean role = null;
   private String[] principal = null;
   private boolean externallyDefined = false;
   private static String refErr = "Can't define security-role-assignment in weblogic.xml because web.xml has no matching security-role";

   public SecurityRoleAssignment() {
   }

   public SecurityRoleAssignment(WebAppDescriptor wad, SecurityRoleAssignmentMBean mbean) throws DOMProcessingException {
      this.setRole(wad, mbean.getRole().getRoleName());
      this.setPrincipalNames(mbean.getPrincipalNames());
      this.setExternallyDefined(mbean.isExternallyDefined());
   }

   public SecurityRoleAssignment(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      String roleName = DOMUtils.getValueByTagName(parentElement, "role-name");
      if (roleName == null) {
         throw new DOMProcessingException("You must specify a role-name element within security-role-assignment");
      } else {
         this.setRole(wad, roleName);
         String externallyDefinedTag = DOMUtils.getOptionalValueByTagName(parentElement, "externally-defined");
         if (externallyDefinedTag == null) {
            externallyDefinedTag = DOMUtils.getOptionalValueByTagName(parentElement, "global-role");
         }

         if (externallyDefinedTag == null) {
            this.externallyDefined = false;
         } else if (!externallyDefinedTag.equals("") && !externallyDefinedTag.equalsIgnoreCase("true")) {
            this.externallyDefined = false;
         } else {
            this.externallyDefined = true;
         }

         List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "principal-name");
         if (elts != null && elts.size() >= 1) {
            Iterator iter = elts.iterator();
            String[] result = new String[elts.size()];

            for(int ii = 0; iter.hasNext(); ++ii) {
               Element node = (Element)iter.next();
               result[ii] = DOMUtils.getTextData(node);
            }

            this.setPrincipalNames(result);
         } else {
            this.setPrincipalNames(new String[0]);
            if (!this.externallyDefined) {
               throw new DOMProcessingException("Neither principal-names nor  externally-defined element specified for security-role-assignment");
            }
         }

      }
   }

   private void setRole(WebAppDescriptor wad, String name) throws DOMProcessingException {
      SecurityRoleMBean[] sr = wad.getSecurityRoles();
      if (sr != null) {
         for(int i = 0; i < sr.length; ++i) {
            if (sr[i].getRoleName().equals(name)) {
               this.role = sr[i];
               break;
            }
         }
      } else {
         HTTPLogger.logBadSecurityRoleInSRA(name);
      }

      if (this.role == null) {
         HTTPLogger.logBadSecurityRoleInSRA(name);
      }

   }

   public void setRole(SecurityRoleMBean r) {
      if (this.role == null) {
         HTTPLogger.logBadSecurityRoleInSRA(this.getName());
      }

      SecurityRoleMBean old = this.role;
      this.role = r;
      if (!comp(old, r)) {
         this.firePropertyChange("role", old, r);
      }

   }

   public SecurityRoleMBean getRole() {
      return this.role;
   }

   public void setPrincipalNames(String[] name) {
      String[] old = this.principal;
      this.principal = name;
      if (!comp(old, name)) {
         this.firePropertyChange("principalNames", old, name);
      }

   }

   public String[] getPrincipalNames() {
      return this.principal;
   }

   public void addPrincipalName(String x) {
      String[] prev = this.getPrincipalNames();
      if (prev == null) {
         prev = new String[]{x};
         this.setPrincipalNames(prev);
      } else {
         String[] curr = new String[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setPrincipalNames(curr);
      }
   }

   public void removePrincipalName(String x) {
      String[] prev = this.getPrincipalNames();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            String[] curr = new String[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setPrincipalNames(curr);
         }

      }
   }

   public void setExternallyDefined(boolean b) {
      boolean old = this.externallyDefined;
      this.externallyDefined = b;
      if (old != b) {
         this.firePropertyChange("externallyDefined", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isExternallyDefined() {
      return this.externallyDefined;
   }

   public boolean isGlobalRole() {
      return this.isExternallyDefined();
   }

   public void setGlobalRole(boolean globalRole) {
      this.setExternallyDefined(globalRole);
   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.role == null || this.role.getRoleName() == null) {
         this.addDescriptorError("role-name is not set in security-role-assignment");
         ok = false;
      }

      ok = this.hasEitherPrincipalOrExternal();
      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   private boolean hasEitherPrincipalOrExternal() {
      boolean ret = false;
      if ((this.principal == null || this.principal.length == 0) && this.externallyDefined) {
         ret = true;
      }

      if (this.principal != null && this.principal.length != 0 && !this.externallyDefined) {
         ret = true;
      }

      if (this.principal != null && this.principal.length != 0 && this.externallyDefined) {
         ret = false;
      }

      if ((this.principal == null || this.principal.length == 0) && !this.externallyDefined) {
         ret = false;
      }

      return ret;
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<" + "security-role-assignment" + ">\n";
      indent += 2;
      if (this.role != null) {
         result = result + this.indentStr(indent) + "<" + "role-name" + ">" + this.role.getRoleName() + "</" + "role-name" + ">\n";
      }

      if (this.principal != null) {
         for(int i = 0; i < this.principal.length; ++i) {
            result = result + this.indentStr(indent) + "<" + "principal-name" + ">" + this.principal[i] + "</" + "principal-name" + ">\n";
         }
      }

      if (this.externallyDefined) {
         result = result + this.indentStr(indent) + "<" + "externally-defined" + "/>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</" + "security-role-assignment" + ">\n";
      return result;
   }
}
