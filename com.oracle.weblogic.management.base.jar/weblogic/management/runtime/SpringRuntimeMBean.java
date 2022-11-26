package weblogic.management.runtime;

public interface SpringRuntimeMBean extends RuntimeMBean {
   String getSpringVersion();

   SpringBeanDefinitionRuntimeMBean[] getSpringBeanDefinitionRuntimeMBeans();

   SpringApplicationContextRuntimeMBean[] getSpringApplicationContextRuntimeMBeans();

   SpringTransactionManagerRuntimeMBean[] getSpringTransactionManagerRuntimeMBeans();

   SpringTransactionTemplateRuntimeMBean[] getSpringTransactionTemplateRuntimeMBeans();

   SpringViewRuntimeMBean[] getSpringViewRuntimeMBeans();

   SpringViewResolverRuntimeMBean[] getSpringViewResolverRuntimeMBeans();
}
