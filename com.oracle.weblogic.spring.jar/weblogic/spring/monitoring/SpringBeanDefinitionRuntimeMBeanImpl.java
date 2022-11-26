package weblogic.spring.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SpringBeanDefinitionRuntimeMBean;
import weblogic.management.runtime.SpringBeanDependencyValue;

public class SpringBeanDefinitionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements SpringBeanDefinitionRuntimeMBean {
   private String beanId;
   private String beanClassname;
   private String scope;
   private int role;
   private boolean singleton;
   private boolean abstractFlag = false;
   private boolean autowire = false;
   private boolean lazyInit = false;
   private String resourceDescription;
   private String applicationContextDisplayName;
   private String[] aliases;
   private String[] dependencies;
   private String parentId;
   private SpringBeanDependencyValue[] dependencyValues;

   public SpringBeanDefinitionRuntimeMBeanImpl(String name, RuntimeMBean parent, String beanId, String beanClassname, String parentId, String[] aliases, String[] dependencies, boolean singleton, boolean lazyInit, boolean abstractFlag, boolean autowire, int role, String scope, String resourceDescription, String acDisplayName, SpringBeanDependencyValue[] dependencyValues) throws ManagementException {
      super(name, parent);
      this.beanId = beanId;
      this.beanClassname = beanClassname;
      this.parentId = parentId;
      this.aliases = aliases;
      this.dependencies = dependencies;
      this.singleton = singleton;
      this.lazyInit = lazyInit;
      this.abstractFlag = abstractFlag;
      this.autowire = autowire;
      this.role = role;
      this.scope = scope;
      this.resourceDescription = resourceDescription;
      this.applicationContextDisplayName = acDisplayName;
      this.dependencyValues = dependencyValues;
   }

   public String getBeanId() {
      return this.beanId;
   }

   public String getBeanClassname() {
      return this.beanClassname;
   }

   public boolean isAbstract() {
      return this.abstractFlag;
   }

   public boolean isLazyInit() {
      return this.lazyInit;
   }

   public String getResourceDescription() {
      return this.resourceDescription;
   }

   public boolean isSingleton() {
      return this.singleton;
   }

   public String getApplicationContextDisplayName() {
      return this.applicationContextDisplayName;
   }

   public String[] getAliases() {
      return this.aliases;
   }

   public String[] getDependencies() {
      return this.dependencies;
   }

   public String getParentId() {
      return this.parentId;
   }

   public int getRole() {
      return this.role;
   }

   public String getScope() {
      return this.scope;
   }

   public boolean isAutowireCandidate() {
      return this.autowire;
   }

   public SpringBeanDependencyValue[] getDependencyValues() {
      return this.dependencyValues;
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }
}
