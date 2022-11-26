package weblogic.work.concurrent.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BaseExecutorServiceMBean;
import weblogic.management.configuration.BaseThreadFactoryMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ManagedExecutorServiceTemplateMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceTemplateMBean;
import weblogic.management.configuration.ManagedThreadFactoryTemplateMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerCollection;
import weblogic.work.WorkManagerLifecycleImpl;
import weblogic.work.concurrent.ConcurrentManagedObject;
import weblogic.work.concurrent.ConcurrentWork;
import weblogic.work.concurrent.context.ContextCache;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.spi.ContextProviderManager;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.concurrent.utils.DefaultConcurrentObjectInfo;
import weblogic.work.concurrent.utils.LogUtils;

public class ConcurrentManagedObjectCollection {
   public static final int SHUTDOWN = 0;
   public static final int STARTED = 1;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private String partitionName;
   final String appId;
   private ApplicationRuntimeMBean applicationRuntimeMBean;
   private boolean initialized;
   private ApplicationObjectAndRuntimeManager concurrentObjectAndRuntimeManager;
   private int state = 0;
   private static String thisCluster;
   private static String thisServer;
   private WorkManagerCollection workManagerCollection;
   private ConcurrentBuilderSetting builderFactory;
   private static Map CMOCollections = Collections.synchronizedMap(new HashMap());
   private Set appsContainingTasksFromCurrentApp = Collections.synchronizedSet(new HashSet());

   public ConcurrentManagedObjectCollection(String appId) {
      this.appId = appId;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("creating a new ConcurrentManagedObjectCollection for app: " + appId);
      }

      this.concurrentObjectAndRuntimeManager = new ApplicationObjectAndRuntimeManager(appId);
      CMOCollections.put(appId, this);
   }

   private boolean isTargeted(DeploymentMBean mbean) {
      if (mbean.getParent() instanceof PartitionMBean) {
         return true;
      } else {
         TargetMBean[] targets = mbean.getTargets();
         TargetMBean[] var3 = targets;
         int var4 = targets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean target = var3[var5];
            String targetName = target.getName();
            if (targetName.equals(thisServer) || targetName.equals(thisCluster)) {
               return true;
            }
         }

         return false;
      }
   }

   public synchronized void initialize(WeblogicApplicationBean weblogicApplicationBean, WorkManagerCollection workManagerCollection) throws DeploymentException {
      if (!this.initialized) {
         this.workManagerCollection = workManagerCollection;
         this.partitionName = RuntimeAccessUtils.getCurrentPartitionName();
         this.builderFactory = new AppConcurrentBuilderSetting(this.partitionName, this.appId, workManagerCollection);
         if (thisServer == null || thisCluster == null) {
            thisServer = RuntimeAccessUtils.getServerName();
            thisCluster = RuntimeAccessUtils.getClusterName();
         }

         RuntimeMBeanRegister appRuntimeMBeanRegister = RuntimeMBeanRegister.createAppRegister(this.applicationRuntimeMBean, weblogicApplicationBean);
         if (weblogicApplicationBean != null) {
            this.populate((String)null, appRuntimeMBeanRegister);
         }

         PartitionMBean partitionMBean = RuntimeAccessUtils.getPartitionMBean(this.partitionName);
         if (partitionMBean != null) {
            this.traverseConcurrentObjectMBeans(partitionMBean.getManagedThreadFactoryTemplates(), appRuntimeMBeanRegister);
            this.traverseConcurrentObjectMBeans(partitionMBean.getManagedExecutorServiceTemplates(), appRuntimeMBeanRegister);
            this.traverseConcurrentObjectMBeans(partitionMBean.getManagedScheduledExecutorServiceTemplates(), appRuntimeMBeanRegister);
         }

         DomainMBean domainMBean = RuntimeAccessUtils.getDomainMBean();
         if (domainMBean != null) {
            this.traverseConcurrentObjectMBeans(domainMBean.getManagedThreadFactoryTemplates(), appRuntimeMBeanRegister);
            this.traverseConcurrentObjectMBeans(domainMBean.getManagedExecutorServiceTemplates(), appRuntimeMBeanRegister);
            this.traverseConcurrentObjectMBeans(domainMBean.getManagedScheduledExecutorServiceTemplates(), appRuntimeMBeanRegister);
         }

         ConcurrentManagedObjectBuilder builder;
         if (this.getDefaultContextService() == null) {
            builder = new ConcurrentManagedObjectBuilder();
            this.builderFactory.defaultContextServiceConfig(builder);
            this.concurrentObjectAndRuntimeManager.createContextService(builder, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
         }

         if (this.getDefaultManagedThreadFactory() == null) {
            builder = new ConcurrentManagedObjectBuilder();
            this.builderFactory.defaultMTFConfig(builder);
            this.concurrentObjectAndRuntimeManager.createManagedThreadFactory(builder, appRuntimeMBeanRegister, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
         }

         WorkManagerRuntimeMBean wmRuntimeMBean;
         if (this.getDefaultManagedExecutorService() == null) {
            builder = new ConcurrentManagedObjectBuilder();
            this.builderFactory.defaultExecutorConfig(builder);
            wmRuntimeMBean = workManagerCollection.getRuntimeMBean(builder.getWorkManager());
            this.concurrentObjectAndRuntimeManager.createManagedExecutor(builder, appRuntimeMBeanRegister, wmRuntimeMBean, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
         }

         if (this.getDefaultManagedScheduledExecutorService() == null) {
            builder = new ConcurrentManagedObjectBuilder();
            this.builderFactory.defaultScheduledExecutorConfig(builder);
            wmRuntimeMBean = workManagerCollection.getRuntimeMBean(builder.getWorkManager());
            this.concurrentObjectAndRuntimeManager.createManagedScheduledExecutor(builder, appRuntimeMBeanRegister, wmRuntimeMBean, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
         }

         this.initialized = true;
      }
   }

   private void traverseConcurrentObjectMBeans(ManagedThreadFactoryTemplateMBean[] managedThreadFactoryMBeans, RuntimeMBeanRegister appRuntimeMBeanRegister) throws DeploymentException {
      if (managedThreadFactoryMBeans != null) {
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         ManagedThreadFactoryTemplateMBean[] var4 = managedThreadFactoryMBeans;
         int var5 = managedThreadFactoryMBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ManagedThreadFactoryTemplateMBean mbean = var4[var6];
            if (this.isTargeted(mbean) && this.concurrentObjectAndRuntimeManager.getManagedThreadFactory(mbean.getName(), (String)null) == null) {
               this.builderFactory.config(builder, (BaseThreadFactoryMBean)mbean, (String)null);
               this.concurrentObjectAndRuntimeManager.createManagedThreadFactory(builder, appRuntimeMBeanRegister, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
            }
         }

      }
   }

   private void traverseConcurrentObjectMBeans(ManagedExecutorServiceTemplateMBean[] managedExecutorServiceMBeans, RuntimeMBeanRegister appRuntimeMBeanRegister) throws DeploymentException {
      if (managedExecutorServiceMBeans != null) {
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         ManagedExecutorServiceTemplateMBean[] var4 = managedExecutorServiceMBeans;
         int var5 = managedExecutorServiceMBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ManagedExecutorServiceTemplateMBean mbean = var4[var6];
            if (this.isTargeted(mbean) && this.concurrentObjectAndRuntimeManager.getManagedExecutorService(mbean.getName(), (String)null) == null) {
               this.builderFactory.config(builder, (BaseExecutorServiceMBean)mbean, (String)null);
               WorkManagerRuntimeMBean wmRuntimeMBean = this.workManagerCollection.getRuntimeMBean(builder.getWorkManager());
               this.concurrentObjectAndRuntimeManager.createManagedExecutor(builder, appRuntimeMBeanRegister, wmRuntimeMBean, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
            }
         }

      }
   }

   private void traverseConcurrentObjectMBeans(ManagedScheduledExecutorServiceTemplateMBean[] managedScheduledExecutorServiceMBeans, RuntimeMBeanRegister appRuntimeMBeanRegister) throws DeploymentException {
      if (managedScheduledExecutorServiceMBeans != null) {
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         ManagedScheduledExecutorServiceTemplateMBean[] var4 = managedScheduledExecutorServiceMBeans;
         int var5 = managedScheduledExecutorServiceMBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ManagedScheduledExecutorServiceTemplateMBean mbean = var4[var6];
            if (this.isTargeted(mbean) && this.concurrentObjectAndRuntimeManager.getManagedScheduledExecutorService(mbean.getName(), (String)null) == null) {
               this.builderFactory.config(builder, (BaseExecutorServiceMBean)mbean, (String)null);
               WorkManagerRuntimeMBean wmRuntimeMBean = this.workManagerCollection.getRuntimeMBean(builder.getWorkManager());
               this.concurrentObjectAndRuntimeManager.createManagedScheduledExecutor(builder, appRuntimeMBeanRegister, wmRuntimeMBean, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
            }
         }

      }
   }

   public void setApplicationRuntime(ApplicationRuntimeMBean applicationRuntime) {
      this.applicationRuntimeMBean = applicationRuntime;
   }

   private void checkDefaultConfig(String moduleId, String name) throws DeploymentException {
      if (ConcurrentUtils.isDefaultJSR236Name(name)) {
         if (moduleId != null) {
            String msg = LogUtils.getMessageDefaultConcurrentObjectOverrideNotAllowed(name);
            throw new DeploymentException(msg);
         }
      }
   }

   public void populate(String moduleId, RuntimeMBeanRegister register) throws DeploymentException {
      if (register == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RuntimeMBeanRegister is not set", new NullPointerException());
         }

      } else if (this.builderFactory == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("builderFactory is not set, appId = " + this.appId, new NullPointerException());
         }

      } else {
         int var4;
         int var5;
         ConcurrentManagedObjectBuilder builder;
         if (register.getManagedThreadFactoryBeans() != null) {
            ManagedThreadFactoryBean[] var3 = register.getManagedThreadFactoryBeans();
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ManagedThreadFactoryBean bean = var3[var5];
               this.checkDefaultConfig(moduleId, bean.getName());
               builder = new ConcurrentManagedObjectBuilder();
               this.builderFactory.config(builder, bean, moduleId);
               this.concurrentObjectAndRuntimeManager.createManagedThreadFactory(builder, register, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
            }
         }

         WorkManagerRuntimeMBean wmRuntimeMBean;
         if (register.getManagedExecutorBeans() != null) {
            ManagedExecutorServiceBean[] var9 = register.getManagedExecutorBeans();
            var4 = var9.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ManagedExecutorServiceBean bean = var9[var5];
               this.checkDefaultConfig(moduleId, bean.getName());
               builder = new ConcurrentManagedObjectBuilder();
               this.builderFactory.config(builder, bean, moduleId);
               wmRuntimeMBean = this.workManagerCollection.getRuntimeMBean(builder.getWorkManager());
               this.concurrentObjectAndRuntimeManager.createManagedExecutor(builder, register, wmRuntimeMBean, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
            }
         }

         if (register.getManagedScheduledExecutorBeans() != null) {
            ManagedScheduledExecutorServiceBean[] var10 = register.getManagedScheduledExecutorBeans();
            var4 = var10.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ManagedScheduledExecutorServiceBean bean = var10[var5];
               this.checkDefaultConfig(moduleId, bean.getName());
               builder = new ConcurrentManagedObjectBuilder();
               this.builderFactory.config(builder, (ManagedExecutorServiceBean)bean, moduleId);
               wmRuntimeMBean = this.workManagerCollection.getRuntimeMBean(builder.getWorkManager());
               this.concurrentObjectAndRuntimeManager.createManagedScheduledExecutor(builder, register, wmRuntimeMBean, (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)null);
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("populated CMOs defined in app " + this.appId + " module " + moduleId);
         }

      }
   }

   public void removeModuleEntries(String moduleId) {
      if (moduleId != null) {
         List list = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects();
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            ConcurrentManagedObject lifecycle = (ConcurrentManagedObject)var3.next();
            if (lifecycle.getModuleId() != null && lifecycle.getModuleId().equals(moduleId)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("-- flow -- removeModuleEntries - " + lifecycle.toString());
               }

               lifecycle.stop();
               lifecycle.terminate();
               this.concurrentObjectAndRuntimeManager.removeObject(lifecycle.getJSR236Class(), lifecycle.getName(), moduleId);
            }
         }

         ContextCache.removeData(this.appId, moduleId);
         ContextProviderManager.removeContextProviders(this.appId, moduleId);
      }
   }

   ManagedThreadFactory getDefaultManagedThreadFactory() {
      return this.concurrentObjectAndRuntimeManager.getManagedThreadFactory(ConcurrentUtils.getDefaultMTFInfo().getName(), (String)null);
   }

   ContextService getDefaultContextService() {
      return this.concurrentObjectAndRuntimeManager.getContextService(ConcurrentUtils.getDefaultCSInfo().getName(), (String)null);
   }

   public ManagedExecutorService getDefaultManagedExecutorService() {
      return this.concurrentObjectAndRuntimeManager.getManagedExecutorService(ConcurrentUtils.getDefaultMESInfo().getName(), (String)null);
   }

   ManagedScheduledExecutorService getDefaultManagedScheduledExecutorService() {
      return this.concurrentObjectAndRuntimeManager.getManagedScheduledExecutorService(ConcurrentUtils.getDefaultMSESInfo().getName(), (String)null);
   }

   public synchronized int getState() {
      return this.state;
   }

   public synchronized void setState(int state) {
      if (state == 0) {
         ContextCache.removeData(this.appId, (String)null);
      }

      this.state = state;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ConcurrentManagedObjectCollection setState state=" + state + " appId=" + this.appId);
      }

   }

   public static boolean isStarted(String appId) {
      ConcurrentManagedObjectCollection c = (ConcurrentManagedObjectCollection)CMOCollections.get(appId);
      return c != null && c.getState() == 1;
   }

   public static void connectRelatedApp(String containingTaskAppId, String submittingAppId) {
      ConcurrentManagedObjectCollection c = (ConcurrentManagedObjectCollection)CMOCollections.get(submittingAppId);
      if (c != null) {
         c.appsContainingTasksFromCurrentApp.add(containingTaskAppId);
      }
   }

   public void stop() {
      this.setState(0);
      HashSet wms = new HashSet();
      List list = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects();
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         ConcurrentManagedObject lifecycle = (ConcurrentManagedObject)var3.next();
         if (lifecycle.stop() && debugLogger.isDebugEnabled()) {
            debugLogger.debug("-- flow -- stopping - " + lifecycle.toString());
         }

         WorkManager wm = lifecycle.getWorkManager();
         if (wm instanceof WorkManagerLifecycleImpl) {
            wms.add((WorkManagerLifecycleImpl)wm);
         }
      }

      WorkManagerLifecycleImpl wm;
      for(var3 = wms.iterator(); var3.hasNext(); wm.releaseExecutingRequests(ConcurrentWork.CONCURRENT_WORK_FILTER)) {
         wm = (WorkManagerLifecycleImpl)var3.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("releasing works for WorkManager " + wm.getName());
         }
      }

      ArrayList tmpApps = new ArrayList(this.appsContainingTasksFromCurrentApp);
      Iterator var9 = tmpApps.iterator();

      while(var9.hasNext()) {
         String appContainingTasksFromCurrentApp = (String)var9.next();
         ConcurrentManagedObjectCollection collection = (ConcurrentManagedObjectCollection)CMOCollections.get(appContainingTasksFromCurrentApp);
         if (collection != null) {
            collection.releaseTasksSubmittedBy(this.appId);
         }
      }

      this.appsContainingTasksFromCurrentApp.clear();
   }

   public void releaseTasksSubmittedBy(String applicationId) {
      if (1 != this.state) {
         debugLogger.debug("skip release tasks in " + this + " submitted by " + applicationId + " when state=" + this.state);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("release tasks in " + this + " submitted by " + applicationId);
         }

         HashSet wms = new HashSet();
         List list = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects();
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            ConcurrentManagedObject lifecycle = (ConcurrentManagedObject)var4.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("release tasks in " + lifecycle + " submitted by " + applicationId);
            }

            lifecycle.shutdownThreadsSubmittedBy(applicationId);
            WorkManager wm = lifecycle.getWorkManager();
            if (wm instanceof WorkManagerLifecycleImpl) {
               wms.add((WorkManagerLifecycleImpl)wm);
            }
         }

         WorkManagerLifecycleImpl wm;
         for(var4 = wms.iterator(); var4.hasNext(); wm.releaseExecutingRequests(new ConcurrentWork.SubmittingComponentWorkFilter(applicationId))) {
            wm = (WorkManagerLifecycleImpl)var4.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("releasing works for WorkManager " + wm.getName() + " submitted by " + applicationId);
            }
         }

      }
   }

   public void deactive(Context appJndiContext) throws DeploymentException {
      this.stop();
      this.unbindDefaultConcurrentManagedObjects(appJndiContext, false);
   }

   public void terminate() {
      List list = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects();

      ConcurrentManagedObject lifecycle;
      for(Iterator var2 = list.iterator(); var2.hasNext(); lifecycle.terminate()) {
         lifecycle = (ConcurrentManagedObject)var2.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("-- flow -- closing - " + lifecycle.toString());
         }
      }

      this.clear();
      ContextProviderManager.removeContextProviders(this.appId, (String)null);
      CMOCollections.remove(this.appId);
   }

   public void clear() {
      this.concurrentObjectAndRuntimeManager.clear();
   }

   public void start() {
      this.setState(1);
      List list = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects();
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ConcurrentManagedObject lifecycle = (ConcurrentManagedObject)var2.next();
         if (lifecycle.start() && debugLogger.isDebugEnabled()) {
            debugLogger.debug("-- flow -- starting - " + lifecycle.toString());
         }
      }

   }

   public void activate(Context appJndiContext, ClassLoader classLoader, Context jndi, String moduleType) throws DeploymentException {
      boolean bindAppJNDI = WebLogicModuleType.MODULETYPE_EAR.equals(moduleType) || WebLogicModuleType.MODULETYPE_WAR.equals(moduleType) || WebLogicModuleType.MODULETYPE_EJB.equals(moduleType);
      if (bindAppJNDI) {
         try {
            this.bindDefaultConcurrentManagedObjects(appJndiContext, (String)null, (String)null, classLoader, jndi, false);
         } catch (NamingException var7) {
            throw new DeploymentException(var7);
         }
      }

      this.start();
   }

   public Object getBindObject(String className, String moduleId, String objectName, String componentName, ClassLoader cl, Context jndi) {
      ConcurrentManagedObject obj = this.concurrentObjectAndRuntimeManager.getObject(className, moduleId, objectName);
      return obj == null ? null : obj.getOrCreateJSR236Delegator(moduleId, componentName, cl, jndi);
   }

   public void bindDefaultConcurrentManagedObjects(Context baseJavaContext, String moduleId, String compName, ClassLoader classLoader, Context jndiContext, boolean isJSR236Object) throws NamingException {
      if (baseJavaContext == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No jndi context");
         }

      } else {
         Iterator var7 = ConcurrentUtils.getAllDefaultConcurrentObjectInfo().iterator();

         while(var7.hasNext()) {
            DefaultConcurrentObjectInfo info = (DefaultConcurrentObjectInfo)var7.next();
            ConcurrentManagedObject object = this.concurrentObjectAndRuntimeManager.getObject(info.getClassName(), moduleId, info.getName());
            if (object == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(String.format("Could not find default %s in app %s", info.getName(), this.appId));
               }

               return;
            }

            Object obj = null;
            if (isJSR236Object) {
               obj = object.getOrCreateJSR236Delegator(moduleId, compName, classLoader, jndiContext);
               baseJavaContext.bind(info.getCompJndiLeafName(), obj);
            } else {
               obj = object.getOrCreateApplicationDelegator(classLoader, jndiContext);
               baseJavaContext.bind(info.getAppJndiLeafName(), obj);
            }
         }

      }
   }

   public void unbindDefaultConcurrentManagedObjects(Context baseJavaContext, boolean isJSR236Object) {
      if (baseJavaContext == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No jndi context");
         }

      } else {
         Iterator var3 = ConcurrentUtils.getAllDefaultConcurrentObjectInfo().iterator();

         while(var3.hasNext()) {
            DefaultConcurrentObjectInfo info = (DefaultConcurrentObjectInfo)var3.next();

            try {
               if (isJSR236Object) {
                  baseJavaContext.unbind(info.getCompJndiLeafName());
               } else {
                  baseJavaContext.unbind(info.getAppJndiLeafName());
               }
            } catch (NamingException var6) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(String.format("%s is not bound in application %s", info.getName(), this.appId));
               }
            }
         }

      }
   }

   public void updateContexts(String moduleId, String ejbName, ClassLoader classLoader) {
      Iterator var4 = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects().iterator();

      while(var4.hasNext()) {
         ConcurrentManagedObject obj = (ConcurrentManagedObject)var4.next();
         obj.updateContexts(moduleId, ejbName, classLoader);
      }

   }

   public ManagedExecutorService getManagedExecutorService(String moduleId, String mesName) {
      return (ManagedExecutorService)this.concurrentObjectAndRuntimeManager.getObject(ManagedExecutorService.class.getName(), moduleId, mesName);
   }

   public boolean hasEntries(String moduleId) {
      List list = this.concurrentObjectAndRuntimeManager.getAllConcurrentManagedObjects();
      Iterator var3 = list.iterator();

      ConcurrentManagedObject lifecycle;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         lifecycle = (ConcurrentManagedObject)var3.next();
      } while(!ConcurrentUtils.isSameString(moduleId, lifecycle.getModuleId()));

      return true;
   }
}
