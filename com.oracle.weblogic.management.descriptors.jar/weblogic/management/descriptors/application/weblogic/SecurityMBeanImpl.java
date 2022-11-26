package weblogic.management.descriptors.application.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class SecurityMBeanImpl extends XMLElementMBeanDelegate implements SecurityMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_realmName = false;
   private String realmName;
   private boolean isSet_roleAssignments = false;
   private List roleAssignments;

   public String getRealmName() {
      return this.realmName;
   }

   public void setRealmName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.realmName;
      this.realmName = value;
      this.isSet_realmName = value != null;
      this.checkChange("realmName", old, this.realmName);
   }

   public SecurityRoleAssignmentMBean[] getRoleAssignments() {
      if (this.roleAssignments == null) {
         return new SecurityRoleAssignmentMBean[0];
      } else {
         SecurityRoleAssignmentMBean[] result = new SecurityRoleAssignmentMBean[this.roleAssignments.size()];
         result = (SecurityRoleAssignmentMBean[])((SecurityRoleAssignmentMBean[])this.roleAssignments.toArray(result));
         return result;
      }
   }

   public void setRoleAssignments(SecurityRoleAssignmentMBean[] value) {
      SecurityRoleAssignmentMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getRoleAssignments();
      }

      this.isSet_roleAssignments = true;
      if (this.roleAssignments == null) {
         this.roleAssignments = Collections.synchronizedList(new ArrayList());
      } else {
         this.roleAssignments.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.roleAssignments.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("RoleAssignments", _oldVal, this.getRoleAssignments());
      }

   }

   public void addRoleAssignment(SecurityRoleAssignmentMBean value) {
      this.isSet_roleAssignments = true;
      if (this.roleAssignments == null) {
         this.roleAssignments = Collections.synchronizedList(new ArrayList());
      }

      this.roleAssignments.add(value);
   }

   public void removeRoleAssignment(SecurityRoleAssignmentMBean value) {
      if (this.roleAssignments != null) {
         this.roleAssignments.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<security");
      result.append(">\n");
      if (null != this.getRealmName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<realm-name>").append(this.getRealmName()).append("</realm-name>\n");
      }

      if (null != this.getRoleAssignments()) {
         for(int i = 0; i < this.getRoleAssignments().length; ++i) {
            result.append(this.getRoleAssignments()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</security>\n");
      return result.toString();
   }
}
