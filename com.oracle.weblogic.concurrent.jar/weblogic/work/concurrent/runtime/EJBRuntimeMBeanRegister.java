package weblogic.work.concurrent.runtime;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;

public class EJBRuntimeMBeanRegister extends RuntimeMBeanRegister {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private ComponentConcurrentRuntimeMBean compRTMBean;
   private WeblogicEjbJarBean ejb;

   public EJBRuntimeMBeanRegister(ComponentConcurrentRuntimeMBean compRTMBean, WeblogicEjbJarBean ejb) {
      this.compRTMBean = compRTMBean;
      this.ejb = ejb;
   }

   public ManagedExecutorServiceRuntimeMBeanImpl createManagedExecutorRuntimeMBean(ManagedExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      ManagedExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedExecutorServiceRuntimeMBeanImpl(executor, this.compRTMBean, wmRuntimeMBean);
      this.compRTMBean.addManagedExecutorServiceRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.compRTMBean);
      }

      return runtimeMBean;
   }

   public ManagedScheduledExecutorServiceRuntimeMBeanImpl createManagedScheduleExecutorRuntimeMBean(ManagedScheduledExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      ManagedScheduledExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedScheduledExecutorServiceRuntimeMBeanImpl(executor, this.compRTMBean, wmRuntimeMBean);
      this.compRTMBean.addManagedScheduledExecutorServiceRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.compRTMBean);
      }

      return runtimeMBean;
   }

   public ManagedThreadFactoryRuntimeMBeanImpl createManagedThreadFactoryRuntimeMBean(ManagedThreadFactoryImpl mtf) throws ManagementException {
      ManagedThreadFactoryRuntimeMBeanImpl mtfRuntime = new ManagedThreadFactoryRuntimeMBeanImpl(mtf, this.compRTMBean);
      this.compRTMBean.addManagedThreadFactoryRuntime(mtfRuntime);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(mtfRuntime + " for " + mtfRuntime.getName() + " is created and installed under " + this.compRTMBean);
      }

      return mtfRuntime;
   }

   public ManagedExecutorServiceBean[] getManagedExecutorBeans() {
      return this.ejb.getManagedExecutorServices();
   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorBeans() {
      return this.ejb.getManagedScheduledExecutorServices();
   }

   public ManagedThreadFactoryBean[] getManagedThreadFactoryBeans() {
      return this.ejb.getManagedThreadFactories();
   }
}
