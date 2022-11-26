package weblogic.management.descriptors.application.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SecurityRoleAssignmentMBeanImpl extends XMLElementMBeanDelegate implements SecurityRoleAssignmentMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_roleName = false;
   private String roleName;
   private boolean isSet_principalNames = false;
   private List principalNames;

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

   public String[] getPrincipalNames() {
      if (this.principalNames == null) {
         return new String[0];
      } else {
         String[] result = new String[this.principalNames.size()];
         result = (String[])((String[])this.principalNames.toArray(result));
         return result;
      }
   }

   public void setPrincipalNames(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getPrincipalNames();
      }

      this.isSet_principalNames = true;
      if (this.principalNames == null) {
         this.principalNames = Collections.synchronizedList(new ArrayList());
      } else {
         this.principalNames.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.principalNames.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("PrincipalNames", _oldVal, this.getPrincipalNames());
      }

   }

   public void addPrincipalName(String value) {
      this.isSet_principalNames = true;
      if (this.principalNames == null) {
         this.principalNames = Collections.synchronizedList(new ArrayList());
      }

      this.principalNames.add(value);
   }

   public void removePrincipalName(String value) {
      if (this.principalNames != null) {
         this.principalNames.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<security-role-assignment");
      result.append(">\n");
      if (null != this.getRoleName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<role-name>").append(this.getRoleName()).append("</role-name>\n");
      }

      for(int i = 0; i < this.getPrincipalNames().length; ++i) {
         result.append(ToXML.indent(indentLevel + 2)).append("<principal-name>").append(this.getPrincipalNames()[i]).append("</principal-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</security-role-assignment>\n");
      return result.toString();
   }
}
