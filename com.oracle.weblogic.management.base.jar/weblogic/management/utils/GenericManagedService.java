package weblogic.management.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.cluster.migration.MigrationManager;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

public class GenericManagedService {
   private Class mBeanClass;
   private String mBeanName;
   private Class handlerClass;
   private final Map deploymentManagers = new HashMap();
   private volatile boolean started;
   private boolean handleMigration;
   private static final boolean debug = System.getProperty("weblogic.management.utils.debug") != null;
   private MigrationManager migrationManager;

   GenericManagedService(Class mBeanClass, Class handlerClass, boolean handleMigration) {
      this.mBeanClass = mBeanClass;
      this.mBeanName = mBeanClass.getName();
      this.handlerClass = handlerClass;
      this.handleMigration = handleMigration;
   }

   Class getMBeanClass() {
      return this.mBeanClass;
   }

   Class getHandlerClass() {
      return this.handlerClass;
   }

   boolean isHandleMigration() {
      return this.handleMigration;
   }

   boolean isStarted() {
      return this.started;
   }

   MigrationManager getMigrationManager() {
      this.loadMigrationManager();
      return this.migrationManager;
   }

   public void start() throws ServiceFailureException {
      if (!this.started) {
         this.started = true;
         if (debug) {
            this.logDebug("Subsystem called start");
         }

         ArrayList managers;
         synchronized(this) {
            managers = new ArrayList(this.deploymentManagers.values());
         }

         ServiceFailureException lastException = null;
         Iterator i = managers.iterator();

         while(i.hasNext()) {
            GenericDeploymentManager manager = (GenericDeploymentManager)i.next();
            synchronized(manager) {
               try {
                  manager.start();
               } catch (ServiceFailureException var8) {
                  lastException = var8;
               }
            }
         }

         if (lastException != null) {
            throw lastException;
         }
      }
   }

   public void stop() throws ServiceFailureException {
      if (this.started) {
         this.started = false;
         if (debug) {
            this.logDebug("Subsystem called stop");
         }

         ArrayList managers = null;
         synchronized(this) {
            managers = new ArrayList(this.deploymentManagers.values());
         }

         ServiceFailureException lastException = null;
         Iterator i = managers.iterator();

         while(i.hasNext()) {
            GenericDeploymentManager manager = (GenericDeploymentManager)i.next();
            synchronized(manager) {
               try {
                  manager.stop();
               } catch (ServiceFailureException var8) {
                  lastException = var8;
               }
            }
         }

         if (lastException != null) {
            throw lastException;
         }
      }
   }

   void prepareDeployment(DeploymentMBean bean) throws DeploymentException {
      if (debug) {
         this.logDebug("Got prepareDeployment");
      }

      GenericDeploymentManager manager = this.findGenericDeploymentManager(bean);
      if (manager == null) {
         manager = new GenericDeploymentManager(this, bean);
         this.addGenericDeploymentManager(bean, manager);
      }

      synchronized(manager) {
         manager.prepare(bean);
      }
   }

   void activateDeployment(DeploymentMBean bean) throws DeploymentException {
      if (debug) {
         this.logDebug("Got activateDeployment");
      }

      GenericDeploymentManager manager = this.findGenericDeploymentManager(bean);
      if (manager == null) {
         throw new DeploymentException("Deployment " + bean + " was never prepared");
      } else {
         synchronized(manager) {
            manager.activate(bean);
         }
      }
   }

   void deactivateDeployment(DeploymentMBean bean) throws UndeploymentException {
      if (debug) {
         this.logDebug("Got deactivateDeployment");
      }

      GenericDeploymentManager manager = this.findGenericDeploymentManager(bean);
      if (manager == null) {
         throw new UndeploymentException("Deployment " + bean + " was never prepared");
      } else {
         synchronized(manager) {
            manager.deactivate(bean);
         }
      }
   }

   void unprepareDeployment(DeploymentMBean bean) throws UndeploymentException {
      if (debug) {
         this.logDebug("Got unprepareDeployment");
      }

      GenericDeploymentManager manager = this.findGenericDeploymentManager(bean);
      if (manager == null) {
         throw new UndeploymentException("Deployment " + bean + " was never prepared");
      } else {
         synchronized(manager) {
            manager.unprepare(bean);
         }

         this.removeGenericDeploymentManager(bean);
      }
   }

   private synchronized GenericDeploymentManager findGenericDeploymentManager(DeploymentMBean bean) {
      return (GenericDeploymentManager)this.deploymentManagers.get(bean.getName());
   }

   private synchronized void addGenericDeploymentManager(DeploymentMBean bean, GenericDeploymentManager manager) {
      this.deploymentManagers.put(bean.getName(), manager);
   }

   private synchronized void removeGenericDeploymentManager(DeploymentMBean bean) {
      this.deploymentManagers.remove(bean.getName());
   }

   private void logDebug(String msg) {
      System.out.println("GenericManagedService " + this.mBeanName + ": " + msg);
   }

   private void loadMigrationManager() throws RuntimeException {
      if (this.migrationManager == null) {
         this.migrationManager = (MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0]);
      }

   }
}
