package weblogic.work.concurrent.runtime;

import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;

public class WebRuntimeMBeanRegister extends RuntimeMBeanRegister {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private List components;
   private WeblogicWebAppBean webapp;

   public WebRuntimeMBeanRegister(List components, WeblogicWebAppBean webapp) {
      this.components = components;
      this.webapp = webapp;
   }

   public ManagedExecutorServiceRuntimeMBeanImpl createManagedExecutorRuntimeMBean(ManagedExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         ComponentConcurrentRuntimeMBean parent = (ComponentConcurrentRuntimeMBean)var3.next();
         ManagedExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedExecutorServiceRuntimeMBeanImpl(executor, parent, wmRuntimeMBean);
         parent.addManagedExecutorServiceRuntime(runtimeMBean);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + parent);
         }
      }

      return null;
   }

   public ManagedScheduledExecutorServiceRuntimeMBeanImpl createManagedScheduleExecutorRuntimeMBean(ManagedScheduledExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         ComponentConcurrentRuntimeMBean parent = (ComponentConcurrentRuntimeMBean)var3.next();
         ManagedScheduledExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedScheduledExecutorServiceRuntimeMBeanImpl(executor, parent, wmRuntimeMBean);
         parent.addManagedScheduledExecutorServiceRuntime(runtimeMBean);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + parent);
         }
      }

      return null;
   }

   public ManagedThreadFactoryRuntimeMBeanImpl createManagedThreadFactoryRuntimeMBean(ManagedThreadFactoryImpl mtf) throws ManagementException {
      Iterator var2 = this.components.iterator();

      while(var2.hasNext()) {
         ComponentConcurrentRuntimeMBean parent = (ComponentConcurrentRuntimeMBean)var2.next();
         ManagedThreadFactoryRuntimeMBeanImpl mtfRuntime = new ManagedThreadFactoryRuntimeMBeanImpl(mtf, parent);
         parent.addManagedThreadFactoryRuntime(mtfRuntime);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(mtfRuntime + " for " + mtfRuntime.getName() + " is created and installed under " + parent);
         }
      }

      return null;
   }

   public ManagedExecutorServiceBean[] getManagedExecutorBeans() {
      return this.webapp.getManagedExecutorServices();
   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorBeans() {
      return this.webapp.getManagedScheduledExecutorServices();
   }

   public ManagedThreadFactoryBean[] getManagedThreadFactoryBeans() {
      return this.webapp.getManagedThreadFactories();
   }
}
