package weblogic.management.runtime;

public interface SpringBeanDefinitionRuntimeMBean extends RuntimeMBean {
   int ROLE_APPLICATION = 0;
   int ROLE_SUPPORT = 1;
   int ROLE_INFRASTRUCTURE = 2;

   String getBeanId();

   String[] getAliases();

   String[] getDependencies();

   String getBeanClassname();

   String getParentId();

   int getRole();

   String getScope();

   boolean isAbstract();

   boolean isLazyInit();

   boolean isAutowireCandidate();

   String getResourceDescription();

   boolean isSingleton();

   String getApplicationContextDisplayName();

   SpringBeanDependencyValue[] getDependencyValues();
}
