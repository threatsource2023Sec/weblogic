package weblogic.work.concurrent.runtime;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;

public class AppRuntimeMBeanRegister extends RuntimeMBeanRegister {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private ApplicationRuntimeMBean applicationRuntimeMBean;
   private WeblogicApplicationBean app;

   public AppRuntimeMBeanRegister(ApplicationRuntimeMBean applicationRuntimeMBean, WeblogicApplicationBean app) {
      this.applicationRuntimeMBean = applicationRuntimeMBean;
      this.app = app;
   }

   public ManagedExecutorServiceRuntimeMBeanImpl createManagedExecutorRuntimeMBean(ManagedExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      ManagedExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedExecutorServiceRuntimeMBeanImpl(executor, this.applicationRuntimeMBean, wmRuntimeMBean);
      this.applicationRuntimeMBean.addManagedExecutorServiceRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.applicationRuntimeMBean);
      }

      return runtimeMBean;
   }

   public ManagedScheduledExecutorServiceRuntimeMBeanImpl createManagedScheduleExecutorRuntimeMBean(ManagedScheduledExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      ManagedScheduledExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedScheduledExecutorServiceRuntimeMBeanImpl(executor, this.applicationRuntimeMBean, wmRuntimeMBean);
      this.applicationRuntimeMBean.addManagedScheduledExecutorServiceRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.applicationRuntimeMBean);
      }

      return runtimeMBean;
   }

   public ManagedThreadFactoryRuntimeMBeanImpl createManagedThreadFactoryRuntimeMBean(ManagedThreadFactoryImpl mtf) throws ManagementException {
      ManagedThreadFactoryRuntimeMBeanImpl mtfRuntime = new ManagedThreadFactoryRuntimeMBeanImpl(mtf, this.applicationRuntimeMBean);
      this.applicationRuntimeMBean.addManagedThreadFactoryRuntime(mtfRuntime);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(mtfRuntime + " for " + mtfRuntime.getName() + " is created and installed under " + this.applicationRuntimeMBean);
      }

      return mtfRuntime;
   }

   public ManagedExecutorServiceBean[] getManagedExecutorBeans() {
      return this.app.getManagedExecutorServices();
   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorBeans() {
      return this.app.getManagedScheduledExecutorServices();
   }

   public ManagedThreadFactoryBean[] getManagedThreadFactoryBeans() {
      return this.app.getManagedThreadFactories();
   }
}
