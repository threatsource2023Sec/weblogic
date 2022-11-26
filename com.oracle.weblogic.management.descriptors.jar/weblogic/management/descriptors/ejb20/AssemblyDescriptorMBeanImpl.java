package weblogic.management.descriptors.ejb20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.ejb11.ContainerTransactionMBean;
import weblogic.management.descriptors.ejb11.SecurityRoleMBean;
import weblogic.management.tools.ToXML;

public class AssemblyDescriptorMBeanImpl extends XMLElementMBeanDelegate implements AssemblyDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_excludeList = false;
   private ExcludeListMBean excludeList;
   private boolean isSet_securityRoles = false;
   private List securityRoles;
   private boolean isSet_methodPermissions = false;
   private List methodPermissions;
   private boolean isSet_containerTransactions = false;
   private List containerTransactions;

   public ExcludeListMBean getExcludeList() {
      return this.excludeList;
   }

   public void setExcludeList(ExcludeListMBean value) {
      ExcludeListMBean old = this.excludeList;
      this.excludeList = value;
      this.isSet_excludeList = value != null;
      this.checkChange("excludeList", old, this.excludeList);
   }

   public SecurityRoleMBean[] getSecurityRoles() {
      if (this.securityRoles == null) {
         return new SecurityRoleMBean[0];
      } else {
         SecurityRoleMBean[] result = new SecurityRoleMBean[this.securityRoles.size()];
         result = (SecurityRoleMBean[])((SecurityRoleMBean[])this.securityRoles.toArray(result));
         return result;
      }
   }

   public void setSecurityRoles(SecurityRoleMBean[] value) {
      SecurityRoleMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getSecurityRoles();
      }

      this.isSet_securityRoles = true;
      if (this.securityRoles == null) {
         this.securityRoles = Collections.synchronizedList(new ArrayList());
      } else {
         this.securityRoles.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.securityRoles.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("SecurityRoles", _oldVal, this.getSecurityRoles());
      }

   }

   public void addSecurityRole(SecurityRoleMBean value) {
      this.isSet_securityRoles = true;
      if (this.securityRoles == null) {
         this.securityRoles = Collections.synchronizedList(new ArrayList());
      }

      this.securityRoles.add(value);
   }

   public void removeSecurityRole(SecurityRoleMBean value) {
      if (this.securityRoles != null) {
         this.securityRoles.remove(value);
      }
   }

   public weblogic.management.descriptors.ejb11.MethodPermissionMBean[] getMethodPermissions() {
      if (this.methodPermissions == null) {
         return new weblogic.management.descriptors.ejb11.MethodPermissionMBean[0];
      } else {
         weblogic.management.descriptors.ejb11.MethodPermissionMBean[] result = new weblogic.management.descriptors.ejb11.MethodPermissionMBean[this.methodPermissions.size()];
         result = (weblogic.management.descriptors.ejb11.MethodPermissionMBean[])((weblogic.management.descriptors.ejb11.MethodPermissionMBean[])this.methodPermissions.toArray(result));
         return result;
      }
   }

   public void setMethodPermissions(weblogic.management.descriptors.ejb11.MethodPermissionMBean[] value) {
      weblogic.management.descriptors.ejb11.MethodPermissionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getMethodPermissions();
      }

      this.isSet_methodPermissions = true;
      if (this.methodPermissions == null) {
         this.methodPermissions = Collections.synchronizedList(new ArrayList());
      } else {
         this.methodPermissions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.methodPermissions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("MethodPermissions", _oldVal, this.getMethodPermissions());
      }

   }

   public void addMethodPermission(weblogic.management.descriptors.ejb11.MethodPermissionMBean value) {
      this.isSet_methodPermissions = true;
      if (this.methodPermissions == null) {
         this.methodPermissions = Collections.synchronizedList(new ArrayList());
      }

      this.methodPermissions.add(value);
   }

   public void removeMethodPermission(weblogic.management.descriptors.ejb11.MethodPermissionMBean value) {
      if (this.methodPermissions != null) {
         this.methodPermissions.remove(value);
      }
   }

   public ContainerTransactionMBean[] getContainerTransactions() {
      if (this.containerTransactions == null) {
         return new ContainerTransactionMBean[0];
      } else {
         ContainerTransactionMBean[] result = new ContainerTransactionMBean[this.containerTransactions.size()];
         result = (ContainerTransactionMBean[])((ContainerTransactionMBean[])this.containerTransactions.toArray(result));
         return result;
      }
   }

   public void setContainerTransactions(ContainerTransactionMBean[] value) {
      ContainerTransactionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getContainerTransactions();
      }

      this.isSet_containerTransactions = true;
      if (this.containerTransactions == null) {
         this.containerTransactions = Collections.synchronizedList(new ArrayList());
      } else {
         this.containerTransactions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.containerTransactions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ContainerTransactions", _oldVal, this.getContainerTransactions());
      }

   }

   public void addContainerTransaction(ContainerTransactionMBean value) {
      this.isSet_containerTransactions = true;
      if (this.containerTransactions == null) {
         this.containerTransactions = Collections.synchronizedList(new ArrayList());
      }

      this.containerTransactions.add(value);
   }

   public void removeContainerTransaction(ContainerTransactionMBean value) {
      if (this.containerTransactions != null) {
         this.containerTransactions.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<assembly-descriptor");
      result.append(">\n");
      int i;
      if (null != this.getSecurityRoles()) {
         for(i = 0; i < this.getSecurityRoles().length; ++i) {
            result.append(this.getSecurityRoles()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getMethodPermissions()) {
         for(i = 0; i < this.getMethodPermissions().length; ++i) {
            result.append(this.getMethodPermissions()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getContainerTransactions()) {
         for(i = 0; i < this.getContainerTransactions().length; ++i) {
            result.append(this.getContainerTransactions()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getExcludeList()) {
         result.append(this.getExcludeList().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</assembly-descriptor>\n");
      return result.toString();
   }
}
