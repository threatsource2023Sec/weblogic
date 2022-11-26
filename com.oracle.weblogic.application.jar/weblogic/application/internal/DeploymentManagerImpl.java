package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationException;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.DeploymentManager;
import weblogic.application.MBeanFactory;
import weblogic.application.ModuleListener;
import weblogic.application.WorkDeployment;
import weblogic.application.background.BackgroundDeployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.CacheMap;
import weblogic.application.utils.ManagementUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.utils.Debug;

@Service
public final class DeploymentManagerImpl extends DeploymentManager {
   private static final boolean TIMES = Debug.getCategory("weblogic.application.times").isEnabled();
   private static final ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
   private VersionedDeployments deployments = new VersionedDeployments();
   private final MBeanFactory mbeanFactory = MBeanFactory.getMBeanFactory();
   private List listeners = Collections.emptyList();
   private final CacheMap matchedFactoriesTemp = new CacheMap(100, 60000L) {
      public synchronized DeploymentFactory put(String key, DeploymentFactory value) {
         return Controls.deploymentfactorycache.disabled ? null : (DeploymentFactory)super.put(key, value);
      }
   };
   private final Map matchedFactories = new ConcurrentHashMap() {
      public DeploymentFactory put(String key, DeploymentFactory value) {
         return Controls.deploymentfactorycache.disabled ? null : (DeploymentFactory)super.put(key, value);
      }
   };
   private final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");

   private String getId(BasicDeploymentMBean mbean) {
      return mbean instanceof AppDeploymentMBean ? ((AppDeploymentMBean)mbean).getApplicationIdentifier() : mbean.getName();
   }

   public DeploymentManagerImpl() {
      if (ManagementUtils.isRuntimeAccessAvailable()) {
         ManagementUtils.getServerMBean().getStagingDirectoryName();
      }

   }

   public DeploymentManager.DeploymentCreator getDeploymentCreator(BasicDeploymentMBean mbean, File path) throws DeploymentException {
      try {
         if (mbean instanceof AppDeploymentMBean) {
            String applicationId = ((AppDeploymentMBean)mbean).getApplicationIdentifier();
            ApplicationVersionLifecycleNotifier.sendLifecycleEventNotification(applicationId, ApplicationVersionLifecycleNotifier.ApplicationLifecycleAction.PRE_DEPLOY);
         }

         return new DeploymentCreatorImpl();
      } catch (DeploymentException var5) {
         Throwable cause = var5.getCause();
         if (cause == null || !(cause instanceof ApplicationException)) {
            this.logUnexpectedException(var5);
         }

         throw var5;
      } catch (RuntimeException var6) {
         this.logUnexpectedException(var6);
         throw var6;
      } catch (Error var7) {
         this.logUnexpectedException(var7);
         throw var7;
      }
   }

   private void logUnexpectedException(Throwable e) {
      e.printStackTrace();
   }

   private Deployment createDeployment(BasicDeploymentMBean mbean, File f) throws DeploymentException {
      DeploymentFactory matchingFactory = this.getDeploymentFactory(this.getId(mbean), mbean, f);
      if (matchingFactory == null) {
         Loggable l = J2EELogger.logInvalidApplicationLoggable(f.getAbsolutePath());
         throw new DeploymentException(l.getMessage());
      } else {
         Deployment d = null;
         if (mbean instanceof AppDeploymentMBean) {
            d = matchingFactory.createDeployment((AppDeploymentMBean)mbean, f);
         } else {
            d = matchingFactory.createDeployment((SystemResourceMBean)mbean, f);
         }

         if (d == null) {
            Loggable l = J2EELogger.logInvalidApplicationLoggable(f.getAbsolutePath());
            throw new DeploymentException(l.getMessage());
         } else {
            Object d;
            if (TIMES) {
               d = this.isBackgroundDeployment(mbean) ? new BackgroundDeployment(new DeploymentTimer(d)) : d;
            } else {
               d = this.isBackgroundDeployment(mbean) ? new BackgroundDeployment(d) : d;
            }

            d = TIMES ? new DeploymentTimer((Deployment)d) : new DeploymentStateChecker((Deployment)d);
            this.deployments.put(mbean, (Deployment)d);
            return (Deployment)d;
         }
      }
   }

   private void dfCacheDebug(String message) {
      this.debugger.debug("DeploymentFactory Cache: " + message);
   }

   private DeploymentFactory getDeploymentFactory(String appId, BasicDeploymentMBean mbean, File file) throws DeploymentException {
      if (this.debugger.isDebugEnabled()) {
         this.dfCacheDebug("getDeploymentFactory(" + appId + ", " + mbean + ", " + file + ")");
      }

      boolean isLibrary = mbean instanceof LibraryMBean;
      String cPath = null;

      try {
         cPath = file.getCanonicalPath();
      } catch (IOException var7) {
         throw new DeploymentException(var7);
      }

      DeploymentFactory factory;
      if (!isLibrary) {
         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Searching path " + cPath);
         }

         if (this.matchedFactoriesTemp.containsKey(cPath)) {
            factory = (DeploymentFactory)this.matchedFactoriesTemp.get(cPath);
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Found for path " + cPath + ": " + factory);
            }

            if (appId != null) {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("appId non-null for path " + cPath + ": " + appId + ". Moving factory from temp cache to main cache.");
               }

               this.matchedFactories.put(appId, factory);
               this.matchedFactoriesTemp.remove(cPath);
            }

            return factory;
         }

         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Searching appId " + appId);
         }

         if (appId != null && this.matchedFactories.containsKey(appId)) {
            factory = (DeploymentFactory)this.matchedFactories.get(appId);
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Found for appId " + appId + ": " + factory);
            }

            return factory;
         }

         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("DeploymentFactory not found in cache. Searching all factories.");
         }
      } else if (this.debugger.isDebugEnabled()) {
         this.dfCacheDebug("Ignoring DeploymentFactory cache for LibraryMBean appId " + appId + ", path " + cPath);
      }

      factory = this.findFactory(mbean, file, new IteratorFetcher() {
         public Iterator get() {
            return DeploymentManagerImpl.afm.getDeploymentFactories();
         }
      });
      if (factory != null) {
         if (!isLibrary) {
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Search among all factories success for appId " + appId + " and path " + cPath + ": " + factory);
            }

            if (appId != null) {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Caching deployment factory for appId " + appId + ": " + factory);
               }

               this.matchedFactories.put(appId, factory);
            } else {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Temporarily caching deployment factory for cPath " + cPath + ": " + factory);
               }

               this.matchedFactoriesTemp.put(cPath, factory);
            }
         } else if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Not saving DeploymentFactory in cache for LibraryMBean " + appId + ": " + factory);
         }
      }

      return factory;
   }

   public ComponentMBeanFactory getComponentMBeanFactory(String appId, BasicDeploymentMBean mbean, File file) throws DeploymentException {
      if (this.debugger.isDebugEnabled()) {
         this.dfCacheDebug("getComponentMBeanFactory(" + appId + ", " + mbean + ", " + file + ")");
      }

      String cPath = null;

      try {
         cPath = file.getCanonicalPath();
      } catch (IOException var7) {
         throw new DeploymentException(var7);
      }

      if (this.debugger.isDebugEnabled()) {
         this.dfCacheDebug("Searching path " + cPath);
      }

      DeploymentFactory factory;
      ComponentMBeanFactory factory;
      if (this.matchedFactoriesTemp.containsKey(cPath)) {
         factory = (DeploymentFactory)this.matchedFactoriesTemp.get(cPath);
         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Found cached factory for path " + cPath + ": " + factory);
         }

         if (factory instanceof ComponentMBeanFactory) {
            factory = (ComponentMBeanFactory)factory;
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Cached DeploymentFactory confirmed to be an instance of ComponentMBeanFactory: " + factory);
            }

            if (appId != null) {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("appId non-null for path " + cPath + ": " + appId + ". Moving factory from temp cache to main cache.");
               }

               this.matchedFactories.put(appId, factory);
               this.matchedFactoriesTemp.remove(cPath);
            }

            return factory;
         } else {
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Cached DeploymentFactory not an instance of ComponentMBeanFactory, returning default factory instead: " + factory);
            }

            return ComponentMBeanFactory.DEFAULT_FACTORY;
         }
      } else {
         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Searching appId " + appId);
         }

         if (appId != null && this.matchedFactories.containsKey(appId)) {
            factory = (DeploymentFactory)this.matchedFactories.get(appId);
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Found for appId " + appId + ": " + factory);
            }

            if (factory instanceof ComponentMBeanFactory) {
               factory = (ComponentMBeanFactory)factory;
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Cached DeploymentFactory confirmed to be an instance of ComponentMBeanFactory: " + factory);
               }

               return factory;
            } else {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Cached DeploymentFactory not an instance of ComponentMBeanFactory, returning default factory instead: " + factory);
               }

               return ComponentMBeanFactory.DEFAULT_FACTORY;
            }
         } else {
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("DeploymentFactory not found in cache. Searching all factories.");
            }

            factory = this.findFactory(mbean, file, new IteratorFetcher() {
               public Iterator get() {
                  return DeploymentManagerImpl.afm.getComponentMBeanFactories();
               }
            });
            if (factory != null) {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Search among all factories success for appId " + appId + " and path " + cPath + ": " + factory);
               }

               if (appId != null) {
                  if (this.debugger.isDebugEnabled()) {
                     this.dfCacheDebug("Caching deployment factory for appId " + appId + ": " + factory);
                  }

                  this.matchedFactories.put(appId, factory);
               } else {
                  if (this.debugger.isDebugEnabled()) {
                     this.dfCacheDebug("Temporarily caching deployment factory for cPath " + cPath + ": " + factory);
                  }

                  this.matchedFactoriesTemp.put(cPath, factory);
               }

               return (ComponentMBeanFactory)factory;
            } else {
               return null;
            }
         }
      }
   }

   private DeploymentFactory findFactory(BasicDeploymentMBean mbean, File file, IteratorFetcher iFetcher) throws DeploymentException {
      if (this.debugger.isDebugEnabled()) {
         this.dfCacheDebug("findFactory(" + mbean + ", " + file + "," + iFetcher + ") basic pass");
      }

      Iterator it = iFetcher.get();
      DeploymentFactory matchingFactory = null;

      DeploymentFactory factory;
      while(it.hasNext() && matchingFactory == null) {
         factory = (DeploymentFactory)it.next();
         if (factory.isSupportedBasic(mbean, file)) {
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Checking for basic match with " + factory + ": supported");
            }

            matchingFactory = factory;
         } else {
            if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Checking for basic match with " + factory + ": not supported");
            }

            if (Controls.deploymentfactorycache.disabled) {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Feature found to be disabled, checking advanced support");
               }

               if (factory.isSupportedAdvanced(mbean, file)) {
                  if (this.debugger.isDebugEnabled()) {
                     this.dfCacheDebug("Checking for advanced match with " + factory + ": supported");
                  }

                  matchingFactory = factory;
               } else if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Checking for advanced match with " + factory + ": not supported");
               }
            }
         }
      }

      if (Controls.deploymentfactorycache.enabled && matchingFactory == null) {
         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("findFactory(" + mbean + ", " + file + "," + iFetcher + ") advanced pass");
         }

         it = iFetcher.get();

         while(it.hasNext() && matchingFactory == null) {
            factory = (DeploymentFactory)it.next();
            if (factory.isSupportedAdvanced(mbean, file)) {
               if (this.debugger.isDebugEnabled()) {
                  this.dfCacheDebug("Checking for advanced match with " + factory + ": supported");
               }

               matchingFactory = factory;
            } else if (this.debugger.isDebugEnabled()) {
               this.dfCacheDebug("Checking for advanced match with " + factory + ": not supported");
            }
         }
      }

      return matchingFactory;
   }

   public WorkDeployment findDeployment(BasicDeploymentMBean mbean) {
      return this.deployments.get(this.getId(mbean));
   }

   public Deployment findDeployment(String applicationId) {
      return this.deployments.get(applicationId);
   }

   public Deployment removeDeployment(BasicDeploymentMBean mbean) {
      try {
         String applicationId = this.getId(mbean);
         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Removing DeploymentFactory cache entry " + applicationId);
         }

         this.matchedFactories.remove(applicationId);
         return this.deployments.remove(this.getId(mbean));
      } catch (RuntimeException var3) {
         this.logUnexpectedException(var3);
         throw var3;
      } catch (Error var4) {
         this.logUnexpectedException(var4);
         throw var4;
      }
   }

   public Deployment removeDeployment(String applicationId) {
      try {
         if (this.debugger.isDebugEnabled()) {
            this.dfCacheDebug("Removing DeploymentFactory cache entry " + applicationId);
         }

         this.matchedFactories.remove(applicationId);
         return this.deployments.remove(applicationId);
      } catch (RuntimeException var3) {
         this.logUnexpectedException(var3);
         throw var3;
      } catch (Error var4) {
         this.logUnexpectedException(var4);
         throw var4;
      }
   }

   public Iterator getDeployments() {
      return this.deployments.getDeployments();
   }

   public Map getAllVersionsOfLibraries(List libaryids) {
      Map multiVersionState = new HashMap();
      synchronized(this.deployments) {
         Iterator var4 = libaryids.iterator();

         label45:
         while(var4.hasNext()) {
            String configuredId = (String)var4.next();
            String configuredIdPrefix = configuredId + (configuredId.contains("#") ? "." : "#");
            Iterator var7 = this.deployments.getDeploymentEntrySet().iterator();

            while(true) {
               String inferredId;
               Deployment deployment;
               do {
                  if (!var7.hasNext()) {
                     continue label45;
                  }

                  Map.Entry entry = (Map.Entry)var7.next();
                  inferredId = (String)entry.getKey();
                  deployment = (Deployment)entry.getValue();
               } while(!inferredId.equals(configuredId) && !inferredId.startsWith(configuredIdPrefix));

               if (deployment.getApplicationContext().getAppDeploymentMBean() instanceof LibraryMBean) {
                  Map inferredIdStates = (Map)multiVersionState.get(configuredId);
                  if (inferredIdStates == null) {
                     inferredIdStates = new HashMap();
                     multiVersionState.put(configuredId, inferredIdStates);
                  }

                  ((Map)inferredIdStates).put(inferredId, "STATE_ACTIVE");
               }
            }
         }

         return multiVersionState;
      }
   }

   public Iterator getApplicationDeployments(String applicationName) {
      return this.deployments.getApplicationDeployments(applicationName);
   }

   public MBeanFactory getMBeanFactory() {
      return this.mbeanFactory;
   }

   public synchronized void addModuleListener(ModuleListener ml) {
      List nl = new ArrayList(this.listeners.size() + 1);
      nl.addAll(this.listeners);
      nl.add(ml);
      this.listeners = nl;
   }

   public synchronized void removeModuleListener(ModuleListener ml) {
      List nl = new ArrayList(this.listeners.size() - 1);
      Iterator it = this.listeners.iterator();

      while(it.hasNext()) {
         ModuleListener n = (ModuleListener)it.next();
         if (!ml.equals(n)) {
            nl.add(n);
         }
      }

      this.listeners = nl;
   }

   public Iterator getModuleListeners() {
      return this.listeners.iterator();
   }

   private boolean isBackgroundDeployment(BasicDeploymentMBean mbean) {
      if (!(mbean instanceof AppDeploymentMBean)) {
         return false;
      } else {
         return ((AppDeploymentMBean)mbean).isBackgroundDeployment();
      }
   }

   public String confirmApplicationName(boolean isRedeployment, File appSourceFile, File altDescriptor, String tentativeName, String tentativeApplicationId, DomainMBean domain) throws DeploymentException {
      try {
         return JavaEEName.instance.confirmApplicationName(isRedeployment, appSourceFile, altDescriptor, tentativeName, tentativeApplicationId, domain, (String)null, (String)null, (String)null);
      } catch (DeploymentException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new DeploymentException("Unable to confirm the application name.", var9);
      }
   }

   public String confirmApplicationName(boolean isRedeployment, File appSourceFile, File altDescriptor, String tentativeName, String tentativeApplicationId, DomainMBean domain, String rgt, String rg, String partition) throws DeploymentException {
      try {
         return JavaEEName.instance.confirmApplicationName(isRedeployment, appSourceFile, altDescriptor, tentativeName, tentativeApplicationId, domain, rgt, rg, partition);
      } catch (DeploymentException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new DeploymentException("Unable to confirm the application name.", var12);
      }
   }

   public class DeploymentCreatorImpl implements DeploymentManager.DeploymentCreator {
      public Deployment createDeployment(BasicDeploymentMBean mbean, File path) throws DeploymentException {
         return DeploymentManagerImpl.this.createDeployment(mbean, path);
      }
   }

   private static class VersionedDeployments {
      private ReadWriteLock rwl;
      private final Map versionedDeployments;
      private final Map deployments;
      private static final Iterator EMPTY_DEPLOYMENT_ITERATOR = (new LinkedList()).iterator();

      private VersionedDeployments() {
         this.rwl = new ReentrantReadWriteLock();
         this.versionedDeployments = new HashMap();
         this.deployments = new HashMap();
      }

      private void put(BasicDeploymentMBean bean, Deployment dep) {
         Lock l = this.rwl.writeLock();
         l.lock();

         try {
            this.deployments.put(bean.getName(), dep);
            if (bean instanceof AppDeploymentMBean) {
               String applicationName = ((AppDeploymentMBean)bean).getApplicationName();
               String versionId = ((AppDeploymentMBean)bean).getVersionIdentifier();
               Map version2Dep = (Map)this.versionedDeployments.get(applicationName);
               if (version2Dep == null) {
                  version2Dep = new LinkedHashMap();
                  this.versionedDeployments.put(applicationName, version2Dep);
               }

               ((Map)version2Dep).put(versionId, dep);
            }
         } finally {
            l.unlock();
         }

      }

      private Deployment remove(String applicationId) {
         Lock l = this.rwl.writeLock();
         l.lock();

         Deployment var5;
         try {
            String[] nameAndVersion = ApplicationVersionUtils.getNonPartitionName(applicationId).split("#");
            Map deploymentsByVersion = (Map)this.versionedDeployments.get(nameAndVersion[0]);
            if (deploymentsByVersion != null) {
               deploymentsByVersion.remove(nameAndVersion.length > 1 ? nameAndVersion[1] : null);
               if (deploymentsByVersion.isEmpty()) {
                  this.versionedDeployments.remove(nameAndVersion[0]);
               }
            }

            var5 = (Deployment)this.deployments.remove(applicationId);
         } finally {
            l.unlock();
         }

         return var5;
      }

      private Deployment get(String applicationId) {
         Lock l = this.rwl.readLock();
         l.lock();

         Deployment var3;
         try {
            var3 = (Deployment)this.deployments.get(applicationId);
         } finally {
            l.unlock();
         }

         return var3;
      }

      private Iterator getDeployments() {
         Lock l = this.rwl.readLock();
         l.lock();

         Iterator var2;
         try {
            var2 = (new LinkedList(this.deployments.values())).iterator();
         } finally {
            l.unlock();
         }

         return var2;
      }

      private Set getDeploymentEntrySet() {
         Lock l = this.rwl.readLock();
         l.lock();

         Set var2;
         try {
            var2 = this.deployments.entrySet();
         } finally {
            l.unlock();
         }

         return var2;
      }

      private Iterator getApplicationDeployments(String applicationName) {
         Lock l = this.rwl.readLock();
         l.lock();

         Iterator var4;
         try {
            Map appDeploymentMap = (Map)this.versionedDeployments.get(applicationName);
            var4 = appDeploymentMap == null ? EMPTY_DEPLOYMENT_ITERATOR : (new LinkedList(appDeploymentMap.values())).iterator();
         } finally {
            l.unlock();
         }

         return var4;
      }

      private Deployment get(String applicationName, String versionId) {
         Lock l = this.rwl.readLock();
         l.lock();

         Deployment var5;
         try {
            Map version2Dep = (Map)this.versionedDeployments.get(applicationName);
            var5 = version2Dep == null ? null : (Deployment)version2Dep.get(versionId);
         } finally {
            l.unlock();
         }

         return var5;
      }

      // $FF: synthetic method
      VersionedDeployments(Object x0) {
         this();
      }
   }

   private interface IteratorFetcher {
      Iterator get();
   }
}
