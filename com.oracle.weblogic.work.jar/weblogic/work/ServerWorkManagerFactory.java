package weblogic.work;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Map;
import javax.management.InvalidAttributeValueException;
import weblogic.application.ApplicationAccessService;
import weblogic.diagnostics.image.ImageManager;
import weblogic.health.HealthMonitorService;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.kernel.ExecuteQueueMBeanStub;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.WorkManagerWrapper;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ExecuteQueueMBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.WorkManagerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.cmm.MemoryPressureService;
import weblogic.work.api.WorkManagerCollector;
import weblogic.work.api.WorkManagerCollectorGenerator;

public final class ServerWorkManagerFactory extends SelfTuningWorkManagerFactory {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ServerWorkManagerFactory SINGLETON;
   private static volatile RuntimeAccess runtimeAccess;

   private ServerWorkManagerFactory() {
   }

   static ServerWorkManagerFactory get() {
      return SINGLETON;
   }

   private static RuntimeAccess getRuntimeAccess() {
      if (runtimeAccess != null) {
         return runtimeAccess;
      } else {
         Class var0 = ServerWorkManagerImpl.class;
         synchronized(ServerWorkManagerImpl.class) {
            if (runtimeAccess != null) {
               return runtimeAccess;
            } else {
               runtimeAccess = (RuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
                  public RuntimeAccess run() {
                     return (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
                  }
               });
               return runtimeAccess;
            }
         }
      }
   }

   public static synchronized void initialize(KernelMBean kmb) throws ManagementException {
      SecurityServiceManager.checkKernelPermission();
      if (SINGLETON == null) {
         SINGLETON = new ServerWorkManagerFactory();
         WorkManagerFactory.set(SINGLETON);
         WorkManagerLogger.logInitializingSelfTuning();
         SINGLETON.initializeHere(kmb);
      }
   }

   public static synchronized void initialize(int capacity) {
      SecurityServiceManager.checkKernelPermission();
      if (SINGLETON == null) {
         SINGLETON = new ServerWorkManagerFactory();
         WorkManagerFactory.set(SINGLETON);
         WorkManagerLogger.logInitializingSelfTuning();
         SINGLETON.initializeHere(capacity);
      }
   }

   private void initializeHere(KernelMBean kmb) throws ManagementException {
      ServerWorkManagerImpl.initialize();
      PartitionUtility.setInstance(new LivePartitionUtility(kernelId));
      IncrementAdvisor.setIncrementByCPUCount(kmb.isAddWorkManagerThreadsByCpuCount());
      IncrementAdvisor.setMinThreadPoolSize(kmb.getSelfTuningThreadPoolSizeMin());
      IncrementAdvisor.setMaxThreadPoolSize(kmb.getSelfTuningThreadPoolSizeMax());
      IncrementAdvisor2.setMinThreadPoolSize(kmb.getSelfTuningThreadPoolSizeMin());
      IncrementAdvisor2.setMaxThreadPoolSize(kmb.getSelfTuningThreadPoolSizeMax());
      ExecuteThread.setUseDetailedThreadName(kmb.isUseDetailedThreadName());
      ExecuteThread.setCleanupTLAfterEachRequest(kmb.isEagerThreadLocalCleanup());
      kmb.addBeanUpdateListener(new KernelBeanUpdateListener());
      GlobalWorkManagerComponentsFactory.initialize();
      super.initializeHere();
      MemoryPressureService mph = (MemoryPressureService)GlobalServiceLocator.getServiceLocator().getService(MemoryPressureService.class, new Annotation[0]);
      mph.registerListener("SelfTuning", new SelfTuningMemoryPressureListener());
      Kernel.addDummyDefaultQueue(new WorkManagerWrapper("weblogic.kernel.Default"));
      ExecuteQueueMBean[] queueBeans = kmb.getExecuteQueues();
      if (queueBeans != null) {
         boolean logExecuteQueueWarning = false;
         int i = queueBeans.length;

         while(true) {
            --i;
            if (i < 0) {
               if (logExecuteQueueWarning) {
                  WorkManagerLogger.logNoSelfTuningForExecuteQueues();
               }
               break;
            }

            ExecuteQueueMBean queueBean = queueBeans[i];
            String name = queueBean.getName();
            if (!"weblogic.kernel.Default".equalsIgnoreCase(name) && !"default".equalsIgnoreCase(name)) {
               if (name.startsWith("wl_bootstrap_")) {
                  name = name.substring(13);
               }

               this.create(name, queueBean);
               logExecuteQueueWarning = true;
            }
         }
      }

      ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).registerImageSource("WORK_MANAGER", WorkManagerImageSource.getInstance());

      try {
         ThreadPoolRuntimeMBeanImpl mbean = new ThreadPoolRuntimeMBeanImpl(RequestManager.getInstance());
         HealthMonitorService.register("threadpool", mbean, false);
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   protected void createDomainWorkManagers(Map map) {
      GlobalWorkManagerComponentsFactory factory = getGlobalWorkManagerComponentsFactoryForCurrentContext();
      Map templates = factory.getNonAppScopeWorkManagerTemplates();

      String name;
      WorkManagerService wms;
      for(Iterator var4 = templates.entrySet().iterator(); var4.hasNext(); map.put(name, wms)) {
         Map.Entry entry = (Map.Entry)var4.next();
         name = (String)entry.getKey();
         GlobalWorkManagerComponentsFactory.WorkManagerTemplate template = (GlobalWorkManagerComponentsFactory.WorkManagerTemplate)entry.getValue();
         ServerWorkManagerImpl wm = create(name, (String)null, (String)null, template.getRequestClass(), template.getMaxThreadsConstraint(), template.getMinThreadsConstraint(), template.getCapacity(), (StuckThreadManager)null);
         wm.setInternal(false);
         wms = WorkManagerServiceImpl.createService(wm);
         wms.start();
         if (KernelStatus.isServer()) {
            createRuntimeMBean(wm);
         }
      }

   }

   protected void stopDomainWorkManagers(Map domainWorkManagers) {
      if (domainWorkManagers != null) {
         Iterator var2 = domainWorkManagers.values().iterator();

         while(var2.hasNext()) {
            WorkManager workManager = (WorkManager)var2.next();
            if (workManager instanceof WorkManagerLifecycle) {
               ((WorkManagerLifecycle)workManager).shutdown((ShutdownCallback)null);
            }
         }

      }
   }

   protected void startDomainWorkManagers(Map domainWorkManagers) {
      if (domainWorkManagers != null) {
         Iterator var2 = domainWorkManagers.values().iterator();

         while(var2.hasNext()) {
            WorkManager workManager = (WorkManager)var2.next();
            if (workManager instanceof WorkManagerLifecycle) {
               ((WorkManagerLifecycle)workManager).start();
            }
         }

      }
   }

   private SelfTuningWorkManagerImpl createDomainWorkManagerFromTemplate(String name) {
      SelfTuningWorkManagerImpl wm = null;
      GlobalWorkManagerComponentsFactory.WorkManagerTemplate template = GlobalWorkManagerComponentsFactory.getInstance().removeWorkManagerTemplate(name);
      ComponentInvocationContext currentContext = PartitionUtility.getCurrentComponentInvocationContext();
      if (currentContext != null && !currentContext.isGlobalRuntime()) {
         GlobalWorkManagerComponentsFactory globalWorkManagerComponentsFactory = GlobalWorkManagerComponentsFactory.getInstance(currentContext.getPartitionName());
         if (globalWorkManagerComponentsFactory != null) {
            template = globalWorkManagerComponentsFactory.removeWorkManagerTemplate(name);
         }
      }

      if (template != null) {
         wm = create(name, (String)null, (String)null, template.getRequestClass(), template.getMaxThreadsConstraint(), template.getMinThreadsConstraint(), template.getCapacity(), (StuckThreadManager)null);
         WorkManagerLogger.logGlobalInternalWorkManagerOverriden(name);
      }

      return wm;
   }

   private void initializeHere(int capacity) {
      ServerWorkManagerImpl.initialize(capacity);
      super.initializeHere();
      Kernel.addDummyDefaultQueue(new WorkManagerWrapper("weblogic.kernel.Default"));
   }

   protected SelfTuningWorkManagerImpl create(String workManagerName, int fairshare, int responseTime, int minThreadsConstraint, int maxThreadsConstraint) {
      SelfTuningWorkManagerImpl wm = this.createDomainWorkManagerFromTemplate(workManagerName);
      if (wm == null) {
         MinThreadsConstraint min = null;
         if (minThreadsConstraint != -1) {
            min = new MinThreadsConstraint(workManagerName, minThreadsConstraint, getPartitionMinThreadsConstraintForCurrentContext());
            min.setShared(false);
         }

         MaxThreadsConstraint max = null;
         if (maxThreadsConstraint != -1) {
            max = new MaxThreadsConstraint(workManagerName, maxThreadsConstraint, 8192, getPartitionMaxThreadsConstraintForCurrentContext());
            max.setShared(false);
         }

         RequestClass requestClass = null;
         if (responseTime > 0) {
            requestClass = new ResponseTimeRequestClass(workManagerName, responseTime, getPartitionFairShareForCurrentContext());
         } else if (fairshare > 0) {
            requestClass = new FairShareRequestClass(workManagerName, fairshare, getPartitionFairShareForCurrentContext());
         }

         wm = create(workManagerName, (String)null, (String)null, (RequestClass)requestClass, max, min, (OverloadManager)null, (StuckThreadManager)null);
      }

      if (KernelStatus.isServer()) {
         createRuntimeMBean((SelfTuningWorkManagerImpl)wm);
      }

      return (SelfTuningWorkManagerImpl)wm;
   }

   protected void cleanupWorkManager(WorkManager manager) {
      if (manager instanceof ServerWorkManagerImpl) {
         ServerWorkManagerImpl swm = (ServerWorkManagerImpl)manager;
         swm.cleanup();
         ServerRuntimeMBean serverRuntime = runtimeAccess.getServerRuntime();
         if (!swm.isGlobalRuntime()) {
            String partitionName = swm.getPartitionName();
            PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(partitionName);
            WorkManagerRuntimeMBean[] var6 = partitionRuntime.getWorkManagerRuntimes();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               WorkManagerRuntimeMBean wm = var6[var8];
               if (wm.getName().equals(manager.getName())) {
                  try {
                     ((RuntimeMBeanDelegate)wm).unregister();
                  } catch (ManagementException var11) {
                     throw new RuntimeException(var11);
                  }

                  partitionRuntime.removeWorkManagerRuntime(wm);
                  return;
               }
            }
         } else {
            WorkManagerRuntimeMBean[] var13 = serverRuntime.getWorkManagerRuntimes();
            int var14 = var13.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               WorkManagerRuntimeMBean wm = var13[var15];
               if (wm.getName().equals(manager.getName())) {
                  try {
                     ((RuntimeMBeanDelegate)wm).unregister();
                  } catch (ManagementException var12) {
                     throw new RuntimeException(var12);
                  }

                  serverRuntime.removeWorkManagerRuntime(wm);
                  return;
               }
            }
         }
      }

   }

   private static void createRuntimeMBean(SelfTuningWorkManagerImpl wm) {
      if (wm instanceof ServerWorkManagerImpl) {
         try {
            ServerRuntimeMBean serverRuntime = getRuntimeAccess().getServerRuntime();
            Object parent;
            if (wm.isGlobalRuntime()) {
               parent = serverRuntime;
            } else {
               parent = serverRuntime.lookupPartitionRuntime(wm.getPartitionName());
            }

            WorkManagerRuntimeMBean mbean = new WorkManagerRuntimeMBeanImpl((ServerWorkManagerImpl)wm, (RuntimeMBean)parent);
            RequestClass rc = wm.getRequestClass();
            if (rc != null) {
               mbean.setRequestClassRuntime(new RequestClassRuntimeMBeanImpl(rc, mbean, true));
            }

            MinThreadsConstraint min = wm.getMinThreadsConstraint();
            if (min != null) {
               mbean.setMinThreadsConstraintRuntime(new MinThreadsConstraintRuntimeMBeanImpl(min, mbean));
            }

            MaxThreadsConstraint max = wm.getMaxThreadsConstraint();
            if (max != null) {
               mbean.setMaxThreadsConstraintRuntime(new MaxThreadsConstraintRuntimeMBeanImpl(max, mbean));
            }

            if (parent != null) {
               if (parent instanceof ServerRuntimeMBean) {
                  serverRuntime.addWorkManagerRuntime(mbean);
               } else {
                  ((PartitionRuntimeMBean)parent).addWorkManagerRuntime(mbean);
               }
            }
         } catch (ManagementException var7) {
            WorkManagerLogger.logRuntimeMBeanCreationError(wm.getName(), var7);
         }
      }

   }

   private WorkManager create(String name, ExecuteQueueMBean eqmb) {
      WorkManagerLogger.logCreatingExecuteQueueFromMBean(name);
      WorkManager manager = new KernelDelegator(name, eqmb);
      this.getDomainWorkManagersMap().put(name, manager);
      return manager;
   }

   public static WorkManager createExecuteQueue(String name, int noOfThreads) {
      SecurityServiceManager.checkKernelPermission();
      ExecuteQueueMBean queueBean = new ExecuteQueueMBeanStub();

      try {
         queueBean.setThreadCount(noOfThreads);
         queueBean.setThreadsIncrease(0);
         queueBean.setThreadsMaximum(noOfThreads);
      } catch (InvalidAttributeValueException var4) {
         throw new AssertionError("Invalid ExecuteQueueMBean attributes specified for " + name);
      }

      return get().create(name, queueBean);
   }

   protected WorkManager findAppScoped(String workManagerName, String appName, String moduleName, boolean warnIfNotFound, boolean lookupModuleName) {
      ApplicationAccessService applicationAccess = (ApplicationAccessService)LocatorUtilities.getService(ApplicationAccessService.class);
      WorkManagerCollectorGenerator generator = (WorkManagerCollectorGenerator)LocatorUtilities.getService(WorkManagerCollectorGenerator.class);
      WorkManagerCollector workManagerCollector;
      if (appName != null && appName.length() != 0) {
         workManagerCollector = generator.getWorkManagerCollector(this.getSanitizedAppName(appName));
      } else {
         workManagerCollector = generator.getWorkManagerCollector();
      }

      if (workManagerCollector == null) {
         return null;
      } else {
         String temp = moduleName;
         if (moduleName == null && lookupModuleName) {
            temp = applicationAccess.getCurrentModuleName();
         }

         WorkManager manager = workManagerCollector.getWorkManagerCollection().get(temp, workManagerName, warnIfNotFound);
         return manager;
      }
   }

   private String getSanitizedAppName(String appName) {
      if (appName != null && appName.length() != 0) {
         int i = appName.indexOf("@");
         if (i >= 0) {
            appName = appName.substring(0, i);
         }
      }

      return appName;
   }

   static ServerWorkManagerImpl create(String name, String appName, String moduleName) {
      return create(name, appName, moduleName, (RequestClass)null, (MaxThreadsConstraint)null, (MinThreadsConstraint)null, (OverloadManager)null, (StuckThreadManager)null);
   }

   static ServerWorkManagerImpl create(String name, String appName, String moduleName, StuckThreadManager stm) {
      return create(name, appName, moduleName, (RequestClass)null, (MaxThreadsConstraint)null, (MinThreadsConstraint)null, (OverloadManager)null, stm);
   }

   protected static ServerWorkManagerImpl create(String name, String appName, String moduleName, RequestClass p, MaxThreadsConstraint max, MinThreadsConstraint min, OverloadManager overload, StuckThreadManager stm) {
      OverloadManager partitionOverload = null;
      ComponentInvocationContext currentContext = PartitionUtility.getCurrentComponentInvocationContext();
      ComponentInvocationContext wmContext = null;
      if (currentContext != null) {
         wmContext = PartitionUtility.createComponentInvocationContext(currentContext.getPartitionName(), appName == null ? null : currentContext.getApplicationName(), appName == null ? null : currentContext.getApplicationVersion(), moduleName == null ? null : currentContext.getModuleName(), moduleName == null ? null : currentContext.getComponentName());
         GlobalWorkManagerComponentsFactory factory = GlobalWorkManagerComponentsFactory.getInstance(currentContext.getPartitionName());
         partitionOverload = factory.getParitionOverloadManager();
         if (max == null) {
            max = factory.getPartitionMaxThreadsConstraint();
         }
      }

      return new ServerWorkManagerImpl(name, appName, moduleName, p, (MaxThreadsConstraint)max, min, overload, partitionOverload, getPartitionFairShareForCurrentContext(), stm, wmContext);
   }

   static ServerWorkManagerImpl create(String partitionName, String appName, String moduleName, WorkManagerMBean mbean, StuckThreadManager stm) {
      GlobalWorkManagerComponentsFactory.WorkManagerTemplate template = GlobalWorkManagerComponentsFactory.getInstance(partitionName).findWorkManagerTemplate(mbean);
      return create(mbean.getName(), appName, moduleName, template.getRequestClass(), template.getMaxThreadsConstraint(), template.getMinThreadsConstraint(), template.getCapacity(), stm);
   }

   private static GlobalWorkManagerComponentsFactory getGlobalWorkManagerComponentsFactoryForCurrentContext() {
      ComponentInvocationContext currentContext = PartitionUtility.getCurrentComponentInvocationContext();
      GlobalWorkManagerComponentsFactory factory = null;
      if (currentContext.isGlobalRuntime()) {
         factory = GlobalWorkManagerComponentsFactory.getInstance();
      } else {
         factory = GlobalWorkManagerComponentsFactory.getInstance(currentContext.getPartitionName());
      }

      return factory;
   }

   private static PartitionFairShare getPartitionFairShareForCurrentContext() {
      GlobalWorkManagerComponentsFactory factory = getGlobalWorkManagerComponentsFactoryForCurrentContext();
      return factory == null ? null : factory.getPartitionFairShare();
   }

   private static PartitionMinThreadsConstraint getPartitionMinThreadsConstraintForCurrentContext() {
      GlobalWorkManagerComponentsFactory factory = getGlobalWorkManagerComponentsFactoryForCurrentContext();
      return factory == null ? null : factory.getPartitionMinThreadsConstraint();
   }

   private static PartitionMaxThreadsConstraint getPartitionMaxThreadsConstraintForCurrentContext() {
      GlobalWorkManagerComponentsFactory factory = getGlobalWorkManagerComponentsFactoryForCurrentContext();
      return factory == null ? null : factory.getPartitionMaxThreadsConstraint();
   }
}
