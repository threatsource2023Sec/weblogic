package weblogic.j2ee.descriptor.wl;

public interface WeblogicEjbJarBean {
   String getDescription();

   void setDescription(String var1);

   WeblogicEnterpriseBeanBean[] getWeblogicEnterpriseBeans();

   WeblogicEnterpriseBeanBean createWeblogicEnterpriseBean();

   void destroyWeblogicEnterpriseBean(WeblogicEnterpriseBeanBean var1);

   WeblogicEnterpriseBeanBean lookupWeblogicEnterpriseBean(String var1);

   SecurityRoleAssignmentBean[] getSecurityRoleAssignments();

   SecurityRoleAssignmentBean createSecurityRoleAssignment();

   void destroySecurityRoleAssignment(SecurityRoleAssignmentBean var1);

   RunAsRoleAssignmentBean[] getRunAsRoleAssignments();

   RunAsRoleAssignmentBean createRunAsRoleAssignment();

   void destroyRunAsRoleAssignment(RunAsRoleAssignmentBean var1);

   SecurityPermissionBean getSecurityPermission();

   SecurityPermissionBean createSecurityPermission();

   void destroySecurityPermission(SecurityPermissionBean var1);

   TransactionIsolationBean[] getTransactionIsolations();

   TransactionIsolationBean createTransactionIsolation();

   void destroyTransactionIsolation(TransactionIsolationBean var1);

   MessageDestinationDescriptorBean[] getMessageDestinationDescriptors();

   MessageDestinationDescriptorBean createMessageDestinationDescriptor();

   void destroyMessageDestinationDescriptor(MessageDestinationDescriptorBean var1);

   IdempotentMethodsBean getIdempotentMethods();

   IdempotentMethodsBean createIdempotentMethods();

   void destroyIdempotentMethods(IdempotentMethodsBean var1);

   SkipStateReplicationMethodsBean getSkipStateReplicationMethods();

   SkipStateReplicationMethodsBean createSkipStateReplicationMethods();

   void destroySkipStateReplicationMethods(SkipStateReplicationMethodsBean var1);

   RetryMethodsOnRollbackBean[] getRetryMethodsOnRollbacks();

   RetryMethodsOnRollbackBean createRetryMethodsOnRollback();

   void destroyRetryMethodsOnRollback(RetryMethodsOnRollbackBean var1);

   boolean isEnableBeanClassRedeploy();

   void setEnableBeanClassRedeploy(boolean var1);

   String getTimerImplementation();

   void setTimerImplementation(String var1);

   String[] getDisableWarnings();

   void addDisableWarning(String var1);

   void removeDisableWarning(String var1);

   void setDisableWarnings(String[] var1);

   WorkManagerBean[] getWorkManagers();

   WorkManagerBean createWorkManager();

   void destroyWorkManager(WorkManagerBean var1);

   ManagedExecutorServiceBean[] getManagedExecutorServices();

   ManagedExecutorServiceBean createManagedExecutorService();

   void destroyManagedExecutorService(ManagedExecutorServiceBean var1);

   ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorServices();

   ManagedScheduledExecutorServiceBean createManagedScheduledExecutorService();

   void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean var1);

   ManagedThreadFactoryBean[] getManagedThreadFactories();

   ManagedThreadFactoryBean createManagedThreadFactory();

   void destroyManagedThreadFactory(ManagedThreadFactoryBean var1);

   String getComponentFactoryClassName();

   void setComponentFactoryClassName(String var1);

   WeblogicCompatibilityBean getWeblogicCompatibility();

   String getId();

   void setId(String var1);

   CoherenceClusterRefBean getCoherenceClusterRef();

   CoherenceClusterRefBean createCoherenceClusterRef();

   void destroyCoherenceClusterRef();

   String getVersion();

   void setVersion(String var1);

   CdiDescriptorBean getCdiDescriptor();
}
