package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.ejb11.ContainerTransactionMBean;
import weblogic.management.descriptors.ejb11.SecurityRoleMBean;

public interface AssemblyDescriptorMBean extends weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean {
   SecurityRoleMBean[] getSecurityRoles();

   void setSecurityRoles(SecurityRoleMBean[] var1);

   void addSecurityRole(SecurityRoleMBean var1);

   void removeSecurityRole(SecurityRoleMBean var1);

   weblogic.management.descriptors.ejb11.MethodPermissionMBean[] getMethodPermissions();

   void setMethodPermissions(weblogic.management.descriptors.ejb11.MethodPermissionMBean[] var1);

   void addMethodPermission(weblogic.management.descriptors.ejb11.MethodPermissionMBean var1);

   void removeMethodPermission(weblogic.management.descriptors.ejb11.MethodPermissionMBean var1);

   ContainerTransactionMBean[] getContainerTransactions();

   void setContainerTransactions(ContainerTransactionMBean[] var1);

   void addContainerTransaction(ContainerTransactionMBean var1);

   void removeContainerTransaction(ContainerTransactionMBean var1);

   ExcludeListMBean getExcludeList();

   void setExcludeList(ExcludeListMBean var1);
}
