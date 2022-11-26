package weblogic.j2ee.descriptor;

public interface AssemblyDescriptorBean {
   SecurityRoleBean[] getSecurityRoles();

   SecurityRoleBean createSecurityRole();

   void destroySecurityRole(SecurityRoleBean var1);

   MethodPermissionBean[] getMethodPermissions();

   MethodPermissionBean createMethodPermission();

   void destroyMethodPermission(MethodPermissionBean var1);

   ContainerTransactionBean[] getContainerTransactions();

   ContainerTransactionBean createContainerTransaction();

   void destroyContainerTransaction(ContainerTransactionBean var1);

   InterceptorBindingBean[] getInterceptorBindings();

   InterceptorBindingBean createInterceptorBinding();

   void destroyInterceptorBinding(InterceptorBindingBean var1);

   MessageDestinationBean[] getMessageDestinations();

   MessageDestinationBean createMessageDestination();

   void destroyMessageDestination(MessageDestinationBean var1);

   ExcludeListBean getExcludeList();

   ExcludeListBean createExcludeList();

   void destroyExcludeList(ExcludeListBean var1);

   ApplicationExceptionBean[] getApplicationExceptions();

   ApplicationExceptionBean createApplicationException();

   void destroyApplicationException(ApplicationExceptionBean var1);

   String getId();

   void setId(String var1);
}
