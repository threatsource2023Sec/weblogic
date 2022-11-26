package weblogic.ejb.container.interfaces;

import weblogic.application.naming.ModuleRegistry;
import weblogic.ejb.container.deployer.SingletonDependencyResolver;

public interface SingletonSessionBeanInfo extends SessionBeanInfo {
   boolean usesContainerManagedConcurrency();

   boolean initOnStartup();

   void registerSingletonDependencyResolver(ModuleRegistry var1);

   SingletonDependencyResolver getSingletonDependencyResolver();

   weblogic.ejb.container.internal.MethodDescriptor getPostConstructMethodDescriptor();

   weblogic.ejb.container.internal.MethodDescriptor getPreDestroyMethodDescriptor();
}
