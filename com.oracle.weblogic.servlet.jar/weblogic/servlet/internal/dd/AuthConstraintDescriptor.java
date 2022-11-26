package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.AuthConstraintMBean;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class AuthConstraintDescriptor extends BaseServletDescriptor implements ToXML, AuthConstraintMBean {
   private static final long serialVersionUID = -6323007461514307324L;
   private static final String ROLE_NAME = "role-name";
   private static final String DESCRIPTION = "description";
   private String description;
   private SecurityRoleMBean[] roles;

   public AuthConstraintDescriptor(WebAppDescriptor wad, Element parent) throws DOMProcessingException {
      this.description = DOMUtils.getOptionalValueByTagName(parent, "description");
      List elts = DOMUtils.getOptionalElementsByTagName(parent, "role-name");
      if (elts != null && !elts.isEmpty()) {
         List roleNames = DOMUtils.getTextDataValues(elts);
         if (roleNames != null && !roleNames.isEmpty()) {
            SecurityRoleMBean[] sr = wad.getSecurityRoles();
            Hashtable masterRolesList = new Hashtable();

            for(int i = 0; i < sr.length; ++i) {
               masterRolesList.put(sr[i].getRoleName(), sr[i]);
            }

            List rolesList = new ArrayList();
            Iterator i = roleNames.iterator();

            while(i.hasNext()) {
               String name = (String)i.next();
               if (!name.equals("*")) {
                  SecurityRoleMBean mb = (SecurityRoleMBean)masterRolesList.get(name);
                  if (mb == null) {
                     HTTPLogger.logBadSecurityRoleInAC(name);
                  } else {
                     rolesList.add(mb);
                  }
               } else {
                  SecurityRoleMBean starmb = new SecurityRoleDescriptor();
                  starmb.setRoleName("*");
                  rolesList.add(starmb);
               }
            }

            this.roles = (SecurityRoleMBean[])((SecurityRoleMBean[])rolesList.toArray(new SecurityRoleMBean[0]));
         }
      }

   }

   public AuthConstraintDescriptor() {
      this.roles = new SecurityRoleMBean[0];
   }

   public AuthConstraintDescriptor(AuthConstraintMBean mbean) {
      this.setDescription(mbean.getDescription());
      this.setRoles(mbean.getRoles());
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      SecurityRoleMBean[] roles = this.getRoles();
      if (roles == null || roles.length == 0) {
         this.addDescriptorError("NO_ROLE_NAMES");
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public void setRoles(SecurityRoleMBean[] s) {
      SecurityRoleMBean[] old = this.roles;
      this.roles = s;
      if (!comp(old, this.roles)) {
         this.firePropertyChange("roles", old, this.roles);
      }

   }

   public SecurityRoleMBean[] getRoles() {
      return this.roles;
   }

   public void addRole(SecurityRoleMBean x) {
      SecurityRoleMBean[] prev = this.getRoles();
      if (prev == null) {
         prev = new SecurityRoleMBean[]{x};
         this.setRoles(prev);
      } else {
         SecurityRoleMBean[] curr = new SecurityRoleMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setRoles(curr);
      }
   }

   public void removeRole(SecurityRoleMBean x) {
      SecurityRoleMBean[] prev = this.getRoles();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            SecurityRoleMBean[] curr = new SecurityRoleMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setRoles(curr);
         }

      }
   }

   public void setDescription(String s) {
      String old = this.description;
      this.description = s;
      if (!comp(old, s)) {
         this.firePropertyChange("description", old, s);
      }

   }

   public String getDescription() {
      return this.description;
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<auth-constraint>\n";
      indent += 2;
      if (this.description != null && this.description.length() > 0) {
         result = result + this.indentStr(indent) + "<description>" + this.description + "</description>\n";
      }

      for(int i = 0; this.roles != null && i < this.roles.length; ++i) {
         if (this.roles[i] != null) {
            String r = this.roles[i].getRoleName();
            result = result + this.indentStr(indent) + "<role-name>" + r + "</role-name>\n";
         }
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</auth-constraint>\n";
      return result;
   }

   public String toString() {
      String result = "AuthConstraintDescriptor(";
      String m = "{";

      for(int i = 0; i < this.roles.length; ++i) {
         m = m + this.roles[i].getRoleName();
         if (i == this.roles.length - 1) {
            m = m + "}";
         } else {
            m = m + ",";
         }
      }

      result = result + "roles=" + m + ",";
      result = result + ")";
      return result;
   }
}
