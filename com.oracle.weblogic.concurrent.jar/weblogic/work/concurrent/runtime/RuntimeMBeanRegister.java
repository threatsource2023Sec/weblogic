package weblogic.work.concurrent.runtime;

import java.util.List;
import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;

public abstract class RuntimeMBeanRegister {
   public abstract ManagedExecutorServiceRuntimeMBeanImpl createManagedExecutorRuntimeMBean(ManagedExecutorServiceImpl var1, WorkManagerRuntimeMBean var2) throws ManagementException;

   public abstract ManagedScheduledExecutorServiceRuntimeMBeanImpl createManagedScheduleExecutorRuntimeMBean(ManagedScheduledExecutorServiceImpl var1, WorkManagerRuntimeMBean var2) throws ManagementException;

   public abstract ManagedThreadFactoryRuntimeMBeanImpl createManagedThreadFactoryRuntimeMBean(ManagedThreadFactoryImpl var1) throws ManagementException;

   public abstract ManagedExecutorServiceBean[] getManagedExecutorBeans();

   public abstract ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorBeans();

   public abstract ManagedThreadFactoryBean[] getManagedThreadFactoryBeans();

   public static RuntimeMBeanRegister createEJBRegister(ComponentConcurrentRuntimeMBean compRTMBean, WeblogicEjbJarBean ejb) {
      return new EJBRuntimeMBeanRegister(compRTMBean, ejb);
   }

   public static RuntimeMBeanRegister createAppRegister(ApplicationRuntimeMBean applicationRuntimeMBean, WeblogicApplicationBean app) {
      return new AppRuntimeMBeanRegister(applicationRuntimeMBean, app);
   }

   public static RuntimeMBeanRegister createWebRegister(List components, WeblogicWebAppBean webapp) {
      return new WebRuntimeMBeanRegister(components, webapp);
   }
}
