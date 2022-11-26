package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface AssemblyDescriptorMBean extends XMLElementMBean {
   SecurityRoleMBean[] getSecurityRoles();

   void setSecurityRoles(SecurityRoleMBean[] var1);

   void addSecurityRole(SecurityRoleMBean var1);

   void removeSecurityRole(SecurityRoleMBean var1);

   MethodPermissionMBean[] getMethodPermissions();

   void setMethodPermissions(MethodPermissionMBean[] var1);

   void addMethodPermission(MethodPermissionMBean var1);

   void removeMethodPermission(MethodPermissionMBean var1);

   ContainerTransactionMBean[] getContainerTransactions();

   void setContainerTransactions(ContainerTransactionMBean[] var1);

   void addContainerTransaction(ContainerTransactionMBean var1);

   void removeContainerTransaction(ContainerTransactionMBean var1);
}
