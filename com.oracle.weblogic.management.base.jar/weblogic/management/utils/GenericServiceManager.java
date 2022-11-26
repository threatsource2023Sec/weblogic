package weblogic.management.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.internal.DeploymentHandlerHome;

public class GenericServiceManager implements DeploymentHandler {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDeployment");
   private static final GenericServiceManager myself = new GenericServiceManager();
   private boolean initialized;
   private final List handlerList = new ArrayList();

   private GenericServiceManager() {
   }

   public static GenericServiceManager getManager() {
      return myself;
   }

   public GenericManagedService register(Class mBeanClass, Class handlerClass, boolean handleMigration) {
      if (!DeploymentMBean.class.isAssignableFrom(mBeanClass)) {
         throw new IllegalArgumentException("The class " + mBeanClass.getName() + " does not implement DeploymentMBean");
      } else if (!GenericAdminHandler.class.isAssignableFrom(handlerClass)) {
         throw new IllegalArgumentException("The class " + handlerClass.getName() + " does not implement GenericAdminHandler");
      } else {
         GenericManagedService service = new GenericManagedService(mBeanClass, handlerClass, handleMigration);
         synchronized(this) {
            this.handlerList.add(service);
            if (!this.initialized) {
               DeploymentHandlerHome.addDeploymentHandler(this);
               this.initialized = true;
            }

            return service;
         }
      }
   }

   private synchronized GenericManagedService findService(DeploymentMBean bean) {
      Iterator i = this.handlerList.iterator();

      GenericManagedService service;
      do {
         if (!i.hasNext()) {
            return null;
         }

         service = (GenericManagedService)i.next();
      } while(!service.getMBeanClass().isAssignableFrom(bean.getClass()));

      return service;
   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      GenericManagedService service = this.findService(deployment);
      if (service != null) {
         service.prepareDeployment(deployment);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("GenericDeploymentManager.prepareDeployment(" + deployment + ", " + context + ")service=" + service);
      }

   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      GenericManagedService service = this.findService(deployment);
      if (service != null) {
         service.activateDeployment(deployment);
      }

   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      GenericManagedService service = this.findService(deployment);
      if (service != null) {
         service.deactivateDeployment(deployment);
      }

   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      GenericManagedService service = this.findService(deployment);
      if (service != null) {
         service.unprepareDeployment(deployment);
      }

   }
}
