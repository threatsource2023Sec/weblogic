package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;

public interface WeblogicEJBJarMBean extends XMLElementMBean, XMLDeclarationMBean {
   String getDescription();

   void setDescription(String var1);

   WeblogicEnterpriseBeanMBean[] getWeblogicEnterpriseBeans();

   void setWeblogicEnterpriseBeans(WeblogicEnterpriseBeanMBean[] var1);

   void addWeblogicEnterpriseBean(WeblogicEnterpriseBeanMBean var1);

   void removeWeblogicEnterpriseBean(WeblogicEnterpriseBeanMBean var1);

   SecurityRoleAssignmentMBean[] getSecurityRoleAssignments();

   void setSecurityRoleAssignments(SecurityRoleAssignmentMBean[] var1);

   void addSecurityRoleAssignment(SecurityRoleAssignmentMBean var1);

   void removeSecurityRoleAssignment(SecurityRoleAssignmentMBean var1);

   RunAsRoleAssignmentMBean[] getRunAsRoleAssignments();

   void setRunAsRoleAssignments(RunAsRoleAssignmentMBean[] var1);

   void addRunAsRoleAssignment(RunAsRoleAssignmentMBean var1);

   void removeRunAsRoleAssignment(RunAsRoleAssignmentMBean var1);

   SecurityPermissionMBean getSecurityPermission();

   void setSecurityPermission(SecurityPermissionMBean var1);

   TransactionIsolationMBean[] getTransactionIsolations();

   void setTransactionIsolations(TransactionIsolationMBean[] var1);

   void addTransactionIsolation(TransactionIsolationMBean var1);

   void removeTransactionIsolation(TransactionIsolationMBean var1);

   IdempotentMethodsMBean getIdempotentMethods();

   void setIdempotentMethods(IdempotentMethodsMBean var1);

   boolean getEnableBeanClassRedeploy();

   void setEnableBeanClassRedeploy(boolean var1);

   String[] getDisableWarnings();

   void setDisableWarnings(String[] var1);

   void addDisableWarning(String var1);
}
