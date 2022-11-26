package weblogic.ejb.container.deployer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.cache.EntityCache;
import weblogic.ejb.container.cache.EntityNRUCache;
import weblogic.ejb.container.cache.QueryCache;
import weblogic.ejb.spi.EJBCache;
import weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBean;
import weblogic.j2ee.descriptor.wl.MaxCacheSizeBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.QueryCacheRuntimeMBean;

final class EJBCacheDeployment {
   private static final DebugLogger debugLogger;

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }

   static final class Factory implements AppDeploymentExtensionFactory {
      public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
         return new PostProcessor();
      }

      public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
         return new PreProcessor();
      }
   }

   static final class PostProcessor extends BaseAppDeploymentExtension {
      public void activate(ApplicationContextInternal appCtx) {
         if (appCtx.isEar()) {
            if (EJBCacheDeployment.debugLogger.isDebugEnabled()) {
               EJBCacheDeployment.debugLogger.debug(" starting cache scrubbers for app: " + appCtx.getApplicationId());
            }

            PreProcessor pp = (PreProcessor)appCtx.getAppDeploymentExtension(PreProcessor.class.getName());
            if (pp.getCacheMap() != null) {
               Iterator var3 = pp.getCacheMap().values().iterator();

               while(var3.hasNext()) {
                  EJBCache o = (EJBCache)var3.next();
                  o.startScrubber();
               }
            }

         }
      }

      public void deactivate(ApplicationContextInternal appCtx) {
         if (appCtx.isEar()) {
            if (EJBCacheDeployment.debugLogger.isDebugEnabled()) {
               EJBCacheDeployment.debugLogger.debug(" stopping cache scrubbers for app: " + appCtx.getApplicationId());
            }

            PreProcessor pp = (PreProcessor)appCtx.getAppDeploymentExtension(PreProcessor.class.getName());
            if (pp.getCacheMap() != null) {
               Iterator var3 = pp.getCacheMap().values().iterator();

               while(var3.hasNext()) {
                  EJBCache o = (EJBCache)var3.next();
                  o.stopScrubber();
               }
            }

         }
      }
   }

   static final class CachePool implements ApplicationRuntimeMBeanImpl.ReInitializableCachePool {
      private final EJBCache[] caches;

      CachePool(EJBCache[] caches) {
         this.caches = caches;
      }

      public boolean hasCache() {
         return this.caches != null && this.caches.length > 0;
      }

      public void reInitialize() {
         if (this.caches != null) {
            EJBCache[] var1 = this.caches;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               EJBCache c = var1[var3];
               c.reInitializeCacheAndPools();
            }
         }

      }
   }

   static final class PreProcessor extends BaseAppDeploymentExtension {
      private Map cacheMap;
      private Map queryCacheMap;

      public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
         if (appCtx.isEar()) {
            this.cacheMap = new HashMap();
            this.queryCacheMap = new HashMap();
            if (appCtx.getWLApplicationDD() != null && appCtx.getWLApplicationDD().getEjb() != null) {
               ApplicationEntityCacheBean[] var2 = appCtx.getWLApplicationDD().getEjb().getEntityCaches();
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  ApplicationEntityCacheBean cacheBean = var2[var4];
                  boolean usesMaxBeansInCache = true;
                  int maxBeans = -1;
                  long maxSize = -1L;
                  MaxCacheSizeBean maxSizeBean;
                  if (cacheBean.getMaxCacheSize() == null) {
                     maxBeans = cacheBean.getMaxBeansInCache();
                  } else {
                     usesMaxBeansInCache = false;
                     maxSizeBean = cacheBean.getMaxCacheSize();
                     if (maxSizeBean.getBytes() == -1) {
                        maxSize = (long)maxSizeBean.getMegabytes() * 1048576L;
                     } else {
                        maxSize = (long)maxSizeBean.getBytes();
                     }
                  }

                  maxSizeBean = null;
                  Object cache;
                  if (cacheBean.getCachingStrategy().equalsIgnoreCase("Exclusive")) {
                     if (usesMaxBeansInCache) {
                        cache = new EntityNRUCache(cacheBean.getEntityCacheName(), maxBeans);
                     } else {
                        cache = new EntityNRUCache(cacheBean.getEntityCacheName(), maxSize);
                     }
                  } else {
                     if (!cacheBean.getCachingStrategy().equalsIgnoreCase("MultiVersion")) {
                        throw new AssertionError("illegal caching scheme: " + cacheBean.getCachingStrategy());
                     }

                     if (usesMaxBeansInCache) {
                        cache = new EntityCache(cacheBean.getEntityCacheName(), maxBeans);
                     } else {
                        cache = new EntityCache(cacheBean.getEntityCacheName(), maxSize);
                     }
                  }

                  this.cacheMap.put(cacheBean.getEntityCacheName(), cache);
                  this.queryCacheMap.put(cacheBean.getEntityCacheName(), new QueryCache(cacheBean.getEntityCacheName(), cacheBean.getMaxQueriesInCache()));
               }
            }

            ApplicationRuntimeMBeanImpl runtime = appCtx.getRuntime();
            EJBCache[] cacheArray = (EJBCache[])this.cacheMap.values().toArray(new EJBCache[0]);
            runtime.setReInitializableCachePool(new CachePool(cacheArray));

            try {
               Iterator var14 = this.queryCacheMap.keySet().iterator();

               while(var14.hasNext()) {
                  String name = (String)var14.next();
                  QueryCacheRuntimeMBean rtmb = ((weblogic.ejb.container.interfaces.QueryCache)this.queryCacheMap.get(name)).createRuntimeMBean(name, runtime);
                  runtime.addQueryCacheRuntimeMBean(name, rtmb);
               }
            } catch (ManagementException var11) {
               throw new DeploymentException("Error setting up Query Cache runtimes", var11);
            }

            if (!this.cacheMap.containsKey("ExclusiveCache")) {
               this.cacheMap.put("ExclusiveCache", new EntityNRUCache("ExclusiveCache", 1000));
            }

            if (!this.cacheMap.containsKey("MultiVersionCache")) {
               this.cacheMap.put("MultiVersionCache", new EntityCache("MultiVersionCache", 1000));
            }

         }
      }

      public void unprepare(ApplicationContextInternal appCtx) {
         if (appCtx.isEar()) {
            appCtx.getRuntime().setReInitializableCachePool(new CachePool(new EJBCache[0]));
            if (this.cacheMap != null) {
               this.cacheMap.clear();
            }

            if (this.queryCacheMap != null) {
               this.queryCacheMap.clear();
            }

         }
      }

      Map getCacheMap() {
         return this.cacheMap;
      }

      Map getQueryCacheMap() {
         return this.queryCacheMap;
      }
   }
}
