package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterService;
import weblogic.cluster.migration.MigrationException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.work.ExecuteThread;
import weblogic.work.StackableThreadContext;

@Service
public final class SingletonServicesManagerService implements RemoteSingletonServicesControl, BeanUpdateListener, SingletonServicesManager {
   static final String PROP_CLUSTER_MIGRATION_TIMEOUT = "weblogic.cluster.migration.timeout.millis";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final SingletonManager singletonManager = new SingletonManager();
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private volatile SingletonController controller;
   private final HashMap constructedClassesMap = new HashMap();
   private ClusterMBean myCluster;
   private ServerMBean myserver;

   public SingletonServicesManagerService() {
      this.controller = new SerialSingletonController(this.singletonManager, (Leasing)null);
   }

   public void start() throws ServiceFailureException {
      this.myserver = ManagementService.getRuntimeAccess(kernelId).getServer();
      this.myCluster = this.myserver.getCluster();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      domain.addBeanUpdateListener(this);
      Leasing leasingService = ClusterService.getServices().getSingletonLeasingService();
      if (this.myCluster.isConcurrentSingletonActivationEnabled()) {
         this.controller = new ConcurrentSingletonController(this.singletonManager, leasingService);
         if (DEBUG) {
            this.p("Using concurrent singleton activation");
         }
      } else {
         this.controller = new SerialSingletonController(this.singletonManager, leasingService);
         if (DEBUG) {
            this.p("Using serial singleton activation");
         }
      }

      this.startSingletonServices();

      try {
         int timeout = Integer.getInteger("weblogic.cluster.migration.timeout.millis", ClusterService.getClusterServiceInternal().getHeartbeatTimeoutMillis());
         ServerHelper.exportObject(this, timeout);
      } catch (RemoteException var4) {
         throw new ServiceFailureException(var4);
      }
   }

   public void stop() {
      final ArrayList list = new ArrayList();
      this.singletonManager.iterate(new SingletonManager.SingletonIterator() {
         public void traverse(SingletonServiceInfo info) {
            if (info.isActivated()) {
               list.add(info);
            }

         }
      });
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         SingletonServiceInfo info = (SingletonServiceInfo)var2.next();

         try {
            this.deactivateService(info.getName());
         } catch (Exception var5) {
         }
      }

   }

   public String[] getActiveServiceNames() {
      final ArrayList list = new ArrayList();
      this.singletonManager.iterate(new SingletonManager.SingletonIterator() {
         public void traverse(SingletonServiceInfo info) {
            if (info.isActivated()) {
               list.add(info.getName());
            }

         }
      });
      String[] serviceNames = new String[list.size()];
      list.toArray(serviceNames);
      return serviceNames;
   }

   Object[] getRegisteredSingletonServices() {
      return this.singletonManager.getServiceNames();
   }

   Object[] getInternalSingletonServices() {
      final ArrayList result = new ArrayList();
      this.singletonManager.iterate(new SingletonManager.SingletonIterator() {
         public void traverse(SingletonServiceInfo info) {
            if (info.isInternalService()) {
               result.add(info.getName());
            }

         }
      });
      return result.toArray();
   }

   public static SingletonServicesManagerService getInstance() {
      return (SingletonServicesManagerService)GlobalServiceLocator.getServiceLocator().getService(SingletonServicesManagerService.class, new Annotation[0]);
   }

   public void startSingletonServices() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      SingletonServiceMBean[] singletonServiceClasses = domain.getSingletonServices();

      for(int i = 0; i < singletonServiceClasses.length; ++i) {
         try {
            if ((singletonServiceClasses[i].getCluster() == null || singletonServiceClasses[i].getCluster().getName().equals(this.myCluster.getName())) && singletonServiceClasses[i].isCandidate(this.myserver)) {
               this.addConfiguredService(singletonServiceClasses[i].getName(), this.constructSingletonService(singletonServiceClasses[i].getClassName(), (ClassLoader)null));
            }
         } catch (DeploymentException var5) {
            if (DEBUG) {
               this.p("Couldn't deploy " + singletonServiceClasses[i].getClassName(), var5);
            }
         }
      }

   }

   public SingletonService getService(String name) {
      SingletonServiceInfo serviceInfo = this.singletonManager.getServiceInfo(name);
      return serviceInfo == null ? null : serviceInfo.getService();
   }

   public boolean isServiceActive(String name) {
      SingletonServiceInfo serviceInfo = this.singletonManager.getServiceInfo(name);
      return serviceInfo == null ? false : serviceInfo.isActivated();
   }

   public boolean isServiceRegistered(String name) {
      SingletonServiceInfo serviceInfo = this.singletonManager.getServiceInfo(name);
      return serviceInfo != null;
   }

   public void activateService(String name) throws RemoteException {
      if (this.controller == null) {
         throw new IllegalArgumentException("Cannot activate singleton " + name + " as SingletonServicesManagerService not started.  Check if MigrationBasis for cluster is configured.");
      } else if (ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown()) {
         String mesg = "Singleton named " + name + " cannot be activated as the server is shutting down";
         if (DEBUG) {
            this.p(mesg);
         }

         throw new RemoteException(mesg);
      } else {
         try {
            this.controller.execute(SingletonController.Activate, name);
         } catch (LeasingException | SingletonOperationException var3) {
            throw new RemoteException(String.format("Could not activate singleton %s: %s", name, var3.getMessage()), var3);
         }

         this.logActivatedSingletonService(name);
      }
   }

   public void deactivateService(String name) throws RemoteException {
      if (this.controller != null) {
         try {
            this.controller.execute(SingletonController.Deactivate, name);
         } catch (LeasingException | SingletonOperationException var3) {
            throw new RemoteException(String.format("Could not deactivate singleton %s: %s", name, var3.getMessage()), var3);
         }

         this.logDeactivatedSingletonService(name);
      }
   }

   public void restartService(String name) throws RemoteException {
      try {
         ExecuteThread.updateWorkDescription("Trying to restart the singleton service : " + name);
         this.controller.execute(SingletonController.Restart, name);
      } catch (LeasingException | SingletonOperationException var6) {
         throw new RemoteException(String.format("Could not restart singleton %s: %s", name, var6.getMessage()), var6);
      } finally {
         ExecuteThread.updateWorkDescription((String)null);
      }

   }

   public void addConfiguredService(String name, SingletonService service) throws IllegalArgumentException {
      this.singletonManager.addConfiguredService(name, service, (StackableThreadContext)null);
      this.logSingletonRegisteredMessage(name);
   }

   public void addConfiguredService(String name, SingletonService service, StackableThreadContext context) throws IllegalArgumentException {
      this.singletonManager.addConfiguredService(name, service, context);
   }

   public void add(String name, SingletonService service) throws IllegalArgumentException {
      this.singletonManager.addInternalService(name, service, (StackableThreadContext)null);
   }

   public void add(String name, SingletonService service, StackableThreadContext context) throws IllegalArgumentException {
      this.singletonManager.addInternalService(name, service, context);
   }

   public void remove(String name) {
      if (DEBUG) {
         this.p("De-registering singleton " + name + " on this server.");
      }

      if (this.controller == null) {
         this.singletonManager.remove(name);
      } else {
         this.controller.remove(name);
      }

      this.logSingletonUnregisteredMessage(name);
   }

   public SingletonService constructSingletonService(String className, ClassLoader gcl) throws DeploymentException {
      return this.constructSingletonService(className, gcl, (Object)null);
   }

   public SingletonService constructSingletonService(String className, ClassLoader gcl, Object cic) throws DeploymentException {
      try {
         Class clazz = null;
         if (gcl != null) {
            clazz = gcl.loadClass(className);
         } else {
            clazz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
         }

         if (clazz == null) {
            throw new ClassNotFoundException();
         } else {
            Constructor cons = clazz.getDeclaredConstructor();
            SingletonService svc = (SingletonService)cons.newInstance();
            return new SingletonServiceWrapper(svc, cic);
         }
      } catch (ClassNotFoundException var7) {
         throw new DeploymentException("Could not find specified class: " + className, var7);
      } catch (IllegalAccessException var8) {
         throw new DeploymentException("No permission to construct specified class: " + className, var8);
      } catch (NoSuchMethodException var9) {
         throw new DeploymentException("Could not find constructor for: " + className, var9);
      } catch (InstantiationException | InvocationTargetException var10) {
         throw new DeploymentException("Could not construct specified class: " + className, var10);
      }
   }

   public boolean isRestartInPlaceEnabled(String service) {
      MigratableTargetMBean target = this.getMigratableTarget(deQualifyServiceName(service));
      return target != null && target.getRestartOnFailure();
   }

   public boolean restartInPlace(String service) {
      MigratableTargetMBean target = this.getMigratableTarget(deQualifyServiceName(service));
      if (target == null) {
         return true;
      } else {
         int restartAttempts = this.getNumberOfRestartAttempts(target);

         for(int i = 0; i < restartAttempts; ++i) {
            if (i >= 1) {
               try {
                  Thread.sleep((long)(target.getSecondsBetweenRestarts() * 1000));
               } catch (InterruptedException var6) {
               }
            }

            try {
               if (DEBUG) {
                  this.p("Trying to re-start singleton " + service + " " + (i + 1) + "/" + restartAttempts + " on local");
               }

               this.restartService(service);
               if (DEBUG) {
                  this.p("retartInPlace(" + service + ") returns true.");
               }

               return true;
            } catch (RemoteException var7) {
               if (DEBUG) {
                  this.p("Trying to re-start failed because of RemoteException", var7);
                  var7.printStackTrace();
               }
            } catch (MigrationException var8) {
               if (DEBUG) {
                  this.p("Trying to re-start failed because of MigrationException", var8);
                  var8.printStackTrace();
               }
            }
         }

         if (DEBUG) {
            this.p("restartInPlace(" + service + ") returns false.");
         }

         return false;
      }
   }

   private int getNumberOfRestartAttempts(MigratableTargetMBean target) {
      int numberOfRestartAttempts = target.getNumberOfRestartAttempts();
      return numberOfRestartAttempts != -1 ? numberOfRestartAttempts : Integer.MAX_VALUE;
   }

   private MigratableTargetMBean getMigratableTarget(String name) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      MigratableTargetMBean bean = domain.lookupMigratableTarget(name);
      if (bean != null) {
         return bean;
      } else {
         ServerMBean[] servers = domain.getServers();

         for(int i = 0; i < servers.length; ++i) {
            MigratableTargetMBean bean = servers[i].getJTAMigratableTarget();
            if (bean != null && bean.getName().equals(name)) {
               return bean;
            }
         }

         return null;
      }
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

      for(int i = 0; i < updates.length; ++i) {
         if (updates[i].getAddedObject() instanceof SingletonServiceMBean) {
            this.prepareSingletonBean((SingletonServiceMBean)updates[i].getAddedObject());
         } else if (updates[i].getRemovedObject() instanceof SingletonServiceMBean) {
            this.checkDeactivateSingletonBean((SingletonServiceMBean)updates[i].getRemovedObject());
         }
      }

   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

      for(int i = 0; i < updates.length; ++i) {
         if (updates[i].getAddedObject() instanceof SingletonServiceMBean) {
            this.activateSingletonBean((SingletonServiceMBean)updates[i].getAddedObject());
         } else if (updates[i].getRemovedObject() instanceof SingletonServiceMBean) {
            this.deactivateSingletonBean((SingletonServiceMBean)updates[i].getRemovedObject());
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private void prepareSingletonBean(SingletonServiceMBean proposedBean) throws BeanUpdateRejectedException {
      if (proposedBean.getCluster() == null || proposedBean.getCluster().getName().equals(this.myCluster.getName())) {
         if (this.getService(proposedBean.getName()) != null) {
            throw new BeanUpdateRejectedException(proposedBean.getName() + " has already been deployed.");
         } else {
            if (DEBUG) {
               this.p("Preparing " + proposedBean + " for deployment on this server.");
            }

            String className = proposedBean.getClassName();

            try {
               SingletonService constructedClass = this.constructSingletonService(className, (ClassLoader)null);
               this.constructedClassesMap.put(className, constructedClass);
            } catch (DeploymentException var5) {
               throw new BeanUpdateRejectedException("Error updating bean: " + var5, var5);
            }
         }
      }
   }

   private void activateSingletonBean(SingletonServiceMBean bean) throws BeanUpdateFailedException {
      if (bean.getCluster() == null || bean.getCluster().getName().equals(this.myCluster.getName())) {
         if (!this.isServiceActive(bean.getName())) {
            if (DEBUG) {
               this.p("Activating " + bean);
            }

            SingletonService constructedClass = (SingletonService)this.constructedClassesMap.get(bean.getClassName());

            try {
               if (bean.isCandidate(this.myserver)) {
                  this.addConfiguredService(bean.getName(), constructedClass);
               }

            } catch (IllegalArgumentException var4) {
               throw new BeanUpdateFailedException("Could not activate bean: " + var4, var4);
            }
         }
      }
   }

   private void checkDeactivateSingletonBean(SingletonServiceMBean bean) {
   }

   private void deactivateSingletonBean(SingletonServiceMBean bean) {
      if (DEBUG) {
         this.p("Deactivating " + bean);
      }

      this.remove(bean.getName());
      if (bean.getCluster() == null || bean.getCluster().getName().equals(this.myCluster.getName())) {
         this.constructedClassesMap.remove(bean.getClassName());
      }
   }

   public void addActiveService(String name) {
      SingletonServiceInfo svc = this.singletonManager.getServiceInfo(name);
      if (svc != null) {
         svc.setActivated(true);
      }

   }

   public void removeActiveService(String name) {
      SingletonServiceInfo svc = this.singletonManager.getServiceInfo(name);
      if (svc != null) {
         svc.setActivated(false);
      }

   }

   private void p(Object o) {
      SingletonServicesDebugLogger.debug("SingletonServicesManagerService " + o.toString());
   }

   private void p(Object o, Exception e) {
      SingletonServicesDebugLogger.debug("SingletonServicesManagerService " + o.toString(), e);
   }

   private void reset() {
      this.singletonManager.clear();
      this.constructedClassesMap.clear();
   }

   private void logActivatedSingletonService(String name) {
      ClusterLogger.logActivatedSingletonService(name);
   }

   private void logDeactivatedSingletonService(String name) {
      ClusterLogger.logDeactivatedSingletonService(name);
   }

   private void logSingletonRegisteredMessage(String singletonName) {
      if (ManagementService.getRuntimeAccess(kernelId).getServer().getCluster() == null) {
         ClusterExtensionLogger.logRegisteredSingletonServiceNoActivation(singletonName);
      } else {
         ClusterLogger.logRegisteredSingletonService(singletonName);
      }

   }

   private void logSingletonUnregisteredMessage(String singletonName) {
      ClusterLogger.logUnregisteredSingletonService(singletonName);
   }

   static String deQualifyServiceName(String serviceName) {
      int idx = serviceName.indexOf(".");
      return idx >= 0 ? serviceName.substring(idx + 1) : serviceName;
   }
}
