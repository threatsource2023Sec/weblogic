package weblogic.managedbean;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ModuleContext;
import weblogic.diagnostics.debug.DebugLogger;

public class InjectionBasedManagedBeanCreator implements ManagedBeanCreator {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugManagedBean");
   private final List managedInstances = new ArrayList();
   private final BeanManager beanManager;

   public InjectionBasedManagedBeanCreator(ModuleContext moduleContext, InjectionContainer injectionContainer) {
      InjectionDeployment injectionDeployment = injectionContainer.getDeployment();
      this.beanManager = injectionDeployment.getBeanManager(moduleContext.getId());
   }

   public Object createInstance(String className) {
      Object managedInstance = this.beanManager.getBean(className);
      this.storeManagedInstance(managedInstance);
      return managedInstance;
   }

   public Object createInstance(Class clazz) {
      Object managedInstance = this.beanManager.getBean(clazz);
      this.storeManagedInstance(managedInstance);
      return managedInstance;
   }

   public void notifyPreDestroy(String compName, Object obj) {
   }

   public void notifyPostConstruct(String compName, Object obj) {
   }

   public void destroy() {
      Object managedInstance;
      for(Iterator var1 = this.managedInstances.iterator(); var1.hasNext(); this.beanManager.destroyBean(managedInstance)) {
         managedInstance = var1.next();

         try {
            this.beanManager.invokePreDestroy(managedInstance);
         } catch (InjectionException var4) {
            LOGGER.debug("Exception occurred while trying to invoke @PreDestroy on a bean from: " + this, var4);
         }
      }

      this.managedInstances.clear();
   }

   private void storeManagedInstance(Object managedInstance) {
      this.managedInstances.add(managedInstance);
   }
}
