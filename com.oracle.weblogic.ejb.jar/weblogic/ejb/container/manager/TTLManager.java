package weblogic.ejb.container.manager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.ejb.EntityBean;
import javax.ejb.ObjectNotFoundException;
import javax.naming.Context;
import javax.transaction.Transaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.QueryCache;
import weblogic.ejb.container.interfaces.ReadOnlyManager;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.logging.Loggable;
import weblogic.management.runtime.EntityEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class TTLManager extends DBManager implements ReadOnlyManager {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private EntityBeanInfo info;
   private QueryCache queryCache;
   private int readTimeoutMillis;
   private Method findByCategoryMethod;
   private final Random readRand = new Random();
   private WorkManager workManager;
   private Map eagerRefreshMethodMap;
   private Map eagerRefreshJobs;
   private EagerRefreshListener eagerRefreshListener;
   private TimerManager defaultTimerManager;
   private final Map categories = Collections.synchronizedMap(new HashMap());

   public TTLManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, EJBCache cache, QueryCache qcache, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, i, environmentContext, cache, sh);
      this.info = (EntityBeanInfo)i;
      if (this.isReadOnly()) {
         if (qcache == null) {
            this.queryCache = new weblogic.ejb.container.cache.QueryCache(this.info.getEJBName(), this.info.getMaxQueriesInCache());
            this.queryCache.setRuntimeMBean(((EntityEJBRuntimeMBean)this.getEJBRuntimeMBean()).getQueryCacheRuntime());
         } else {
            this.queryCache = qcache;
         }

         if (!this.isBeanManagedPersistence && this.info.getCategoryCmpField() != null) {
            Method[] var8 = this.info.getGeneratedBeanClass().getMethods();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Method m = var8[var10];
               if (m.getName().equals("ejbFindByCategory__WL_")) {
                  this.findByCategoryMethod = m;
                  break;
               }
            }

            if (this.findByCategoryMethod == null) {
               throw new AssertionError("null findByCategoryMethod");
            }

            this.defaultTimerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
         }
      }

      this.updateReadTimeoutSeconds(this.info.getCachingDescriptor().getReadTimeoutSeconds());
   }

   public boolean isCategoryEnabled() {
      return this.findByCategoryMethod != null;
   }

   public Object getCategoryValue(CMPBean bean) {
      return bean._WL__getCategoryValue();
   }

   public void registerCategoryTimer(Object categoryValue, long lastLoadTime) {
      if (!this.categories.containsKey(categoryValue)) {
         this.categories.put(categoryValue, lastLoadTime);
         if (debugLogger.isDebugEnabled()) {
            debug("adding " + categoryValue + " to categories with lastLoadTime: " + lastLoadTime);
         }

         CategoryTimerListener listener = new CategoryTimerListener(this, categoryValue);
         long timeoutDelay = lastLoadTime + (long)(0.9F * (float)this.readTimeoutMillis) - System.currentTimeMillis();
         if (debugLogger.isDebugEnabled()) {
            debug("scheduling timer: " + categoryValue + " delay: " + timeoutDelay + " lastLoadTime: " + lastLoadTime);
         }

         if (timeoutDelay < 0L) {
            timeoutDelay = 0L;
         }

         this.defaultTimerManager.schedule(listener, timeoutDelay);
      }

   }

   public void invokeFindByCategory(Object categoryValue) throws InternalException {
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("Invoking findByCategory with categoryValue: " + categoryValue);
         }

         this.pushEnvironment();
         SecurityHelper.pushCallerPrincipal(KERNEL_ID);
         SecurityHelper.pushAnonymousSubject(KERNEL_ID);
         Collection var2 = this.collectionFinder(this.findByCategoryMethod, new Object[]{categoryValue}, this.info.hasLocalClientView());
      } finally {
         SecurityHelper.popRunAsSubject(KERNEL_ID);

         try {
            SecurityHelper.popCallerPrincipal(KERNEL_ID);
         } catch (PrincipalNotFoundException var8) {
            debug("Error popping caller principal. " + StackTraceUtilsClient.throwable2StackTrace(var8));
         }

         this.popEnvironment();
      }

   }

   public QueryCache getQueryCache() {
      return this.queryCache;
   }

   protected void loadBean(Object pk, EntityBean bean, RSInfo rsInfo, boolean forceLoad) throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debug("loadBean called, EJB= " + this.info.getEJBName() + ", pk= " + pk);
      }

      boolean stateValid = ((WLEntityBean)bean).__WL_isBeanStateValid();
      if (rsInfo == null) {
         if (forceLoad || !stateValid) {
            synchronized(bean) {
               bean.ejbLoad();
            }
         }
      } else if (!stateValid) {
         synchronized(bean) {
            CMPBean cmpBean = (CMPBean)bean;
            cmpBean.__WL_initialize();
            this.persistence.loadBeanFromRS(bean, rsInfo);
            cmpBean.__WL_superEjbLoad();
            cmpBean.__WL_setLastLoadTime(System.currentTimeMillis());
         }
      }

   }

   public boolean supportsCopy() {
      return this.isReadOnly() && !this.isBeanManagedPersistence;
   }

   public void doCopy(CMPBean source, CMPBean target) throws InternalException {
      synchronized(source) {
         try {
            target.__WL_initialize();
            target.__WL_copyFrom(source, true);
            target.__WL_superEjbLoad();
            target.__WL_setLastLoadTime(source.__WL_getLastLoadTime());
         } catch (Throwable var6) {
            EJBLogger.logErrorFromLoad(var6);
            EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var6);
         }

      }
   }

   public void perhapsCopy(Object pk, EntityBean bean) throws InternalException {
      assert this.supportsCopy();

      CMPBean source = this.getCache().getLastLoadedValidInstance(new CacheKey(pk, this));
      if (source != null) {
         this.doCopy(source, (CMPBean)bean);
      }

   }

   public void loadBeanFromRS(CacheKey key, EntityBean bean, RSInfo rsInfo) throws InternalException {
      if (this.uses20CMP) {
         synchronized(bean) {
            this.persistence.loadBeanFromRS(bean, rsInfo);
         }
      }

   }

   protected boolean shouldStore(EntityBean bean) throws Throwable {
      return !this.isReadOnly();
   }

   protected void initLastLoad(Object pk, EntityBean bean) {
      ((WLEntityBean)bean).__WL_setLastLoadTime(System.currentTimeMillis());
   }

   protected EntityBean alreadyCached(Object txOrThread, Object pk) throws InternalException {
      EntityBean bean = this.getCache().getIfNotTimedOut(txOrThread, new CacheKey(pk, this), false);
      if (bean != null && !this.isBeanManagedPersistence && ((CMPBean)bean).__WL_getIsRemoved()) {
         Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(pk.toString());
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", new ObjectNotFoundException(l.getMessageText()));
         bean = null;
      }

      return bean;
   }

   public void enrollNotTimedOutBean(Transaction tx, CacheKey key, EntityBean bean) throws InternalException {
      Object pk = key.getPrimaryKey();
      if (debugLogger.isDebugEnabled()) {
         debug("enrollNotTimedOut called, EJB= " + this.info.getEJBName() + ", pk= " + pk);
      }

      if (this.isReadOnly()) {
         this.setupTxListener(pk, tx);
      } else {
         this.setupTxListenerAndTxUser(pk, tx, (WLEntityBean)bean);
      }

   }

   public void updateReadTimeoutSeconds(int seconds) {
      this.readTimeoutMillis = seconds * 1000;
      Class bc = this.info.getGeneratedBeanClass();

      try {
         Field field = bc.getField("__WL_readTimeoutMS");
         field.setInt(bc, this.readTimeoutMillis);
      } catch (NoSuchFieldException var4) {
         throw new AssertionError(var4);
      } catch (IllegalAccessException var5) {
         throw new AssertionError(var5);
      }
   }

   public void beforeCompletion(Collection primaryKeys, Object txOrThread) throws InternalException {
      if (!this.isReadOnly()) {
         super.beforeCompletion(primaryKeys, txOrThread);
      }

   }

   public Object getFromQueryCache(String query, int maxElements, boolean isLocal) throws InternalException {
      Object txOrThread = this.getInvokeTxOrThread();
      QueryCacheKey qkey = new QueryCacheKey(query, maxElements, this, 0);
      Object ret = this.queryCache.get(txOrThread, qkey, isLocal, false);
      if (debugLogger.isDebugEnabled() && ret == null) {
         debug("Cache miss: " + qkey);
      }

      return ret;
   }

   public Object getFromQueryCache(String methodId, Object[] args, boolean isLocal) throws InternalException {
      Object txOrThread = this.getInvokeTxOrThread();
      QueryCacheKey qkey = new QueryCacheKey(methodId, args, this, 0);
      Object ret = this.queryCache.get(txOrThread, qkey, isLocal, false);
      if (debugLogger.isDebugEnabled() && ret == null) {
         debug("Cache miss: " + qkey);
      }

      return ret;
   }

   public Object getFromQueryCache(String methodId, Object[] args, boolean isLocal, boolean isSet) throws InternalException {
      Object txOrThread = this.getInvokeTxOrThread();
      return this.queryCache.get(txOrThread, new QueryCacheKey(methodId, args, this, 1), isLocal, isSet);
   }

   public void putInQueryCache(QueryCacheKey qkey, Collection ckeys) {
      qkey.setTimeoutMillis(this.readTimeoutMillis);
      if (this.eagerRefreshMethodMap != null) {
         Method method = (Method)this.eagerRefreshMethodMap.get(qkey.getMethodId());
         if (method != null) {
            Object[] args = qkey.getArguments();
            long timeout = (long)((int)((this.readRand.nextDouble() * 0.25 + 0.75) * (double)this.readTimeoutMillis));
            this.registerEagerRefreshJob(method, args, timeout);
         }
      }

      boolean success = this.queryCache.put(qkey, ckeys);
      if (debugLogger.isDebugEnabled()) {
         debug("Cache put: " + qkey + ": " + success);
      }

   }

   public void putInQueryCache(QueryCacheKey qkey, QueryCacheElement qce) {
      qkey.setTimeoutMillis(this.readTimeoutMillis);
      boolean success = this.queryCache.put(qkey, qce);
      if (debugLogger.isDebugEnabled()) {
         debug("Cache put: " + qkey + ": " + success);
      }

   }

   public void invalidateLocalServer(Object txOrThread, Object primaryKey) {
      if (this.isReadOnly()) {
         this.invalidateQueryCache(new CacheKey(primaryKey, this));
      }

      super.invalidateLocalServer(txOrThread, primaryKey);
   }

   public void invalidateLocalServer(Object txOrThread, Collection pks) {
      Collection keyCollection = new ArrayList();

      CacheKey key;
      for(Iterator iter = pks.iterator(); iter.hasNext(); keyCollection.add(key)) {
         key = new CacheKey(iter.next(), this);
         if (this.isReadOnly()) {
            this.invalidateQueryCache(key);
         }
      }

      this.getCache().invalidate(txOrThread, (Collection)keyCollection);
      if (this.invalidationTargetBM != null) {
         this.invalidationTargetBM.invalidateLocalServer(txOrThread, pks);
      }

   }

   public void invalidateAllLocalServer(Object txOrThread) {
      if (this.isReadOnly()) {
         this.queryCache.invalidateAll();
      }

      super.invalidateAllLocalServer(txOrThread);
   }

   public EntityBean enrollIfNotTimedOut(Object txOrThread, CacheKey ckey) throws InternalException {
      assert ckey.getCallback() == this;

      EntityBean bean = this.getCache().getIfNotTimedOut(txOrThread, ckey, false);
      return bean != null && ((CMPBean)bean).__WL_getIsRemoved() ? null : bean;
   }

   public void selectedForReplacement(CacheKey key, EntityBean bean) {
      if (this.isReadOnly()) {
         this.invalidateQueryCache(key);
      }

      super.selectedForReplacement(key, bean);
   }

   private void invalidateQueryCache(CacheKey ckey) {
      this.queryCache.invalidate(ckey);
   }

   private static void debug(String s) {
      debugLogger.debug("[TTLManager] " + s);
   }

   public Map getCategories() {
      return this.categories;
   }

   public EntityBean getBeanFromRS(Object txOrThread, Object pk, RSInfo rsInfo) throws InternalException {
      EntityBean bean = null;

      assert txOrThread != null;

      CacheKey key = new CacheKey(pk, this);
      bean = this.getCache().get(txOrThread, key, rsInfo, true);
      this.cacheRTMBean.incrementCacheAccessCount();
      if (bean != null) {
         this.cacheRTMBean.incrementCacheHitCount();
         if ((this.isCategoryEnabled() || this.isEagerRefreshEnabled()) && rsInfo != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("persistence.loadBeanFromRS() called for " + bean);
            }

            ((CMPBean)bean).__WL_initialize();
            this.persistence.loadBeanFromRS(bean, rsInfo);
            ((CMPBean)bean).__WL_setLastLoadTime(System.currentTimeMillis());
            ((WLEntityBean)bean).__WL_setOperationsComplete(false);
            if (bean instanceof CMPBean) {
               ((CMPBean)bean).__WL_setNonFKHolderRelationChange(false);
               ((CMPBean)bean).__WL_setM2NInsert(false);
            }

            this.getCache().release(txOrThread, key);
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("Didn't find the bean in entity cache");
         }

         bean = this.getBeanFromPool();
         ((CMPBean)bean).__WL_initialize();
         this.persistence.loadBeanFromRS(bean, rsInfo);
         if (!this.finderCacheInsert(bean)) {
            return null;
         }
      }

      return bean;
   }

   public void addEagerRefreshMethod(Method m, String methodId) {
      if (this.eagerRefreshMethodMap == null) {
         this.eagerRefreshMethodMap = new HashMap();
         this.eagerRefreshJobs = Collections.synchronizedMap(new HashMap());
         this.eagerRefreshListener = new EagerRefreshListener(this, this.eagerRefreshJobs);
         this.workManager = WorkManagerFactory.getInstance().getDefault();
         this.workManager.schedule(this.eagerRefreshListener);
      }

      this.eagerRefreshMethodMap.put(methodId, m);
   }

   private boolean isEagerRefreshEnabled() {
      return this.eagerRefreshListener != null;
   }

   private void registerEagerRefreshJob(Method m, Object[] args, long timeoutDelay) {
      EagerRefreshInfo eri = new EagerRefreshInfo(m, args);
      if (!this.eagerRefreshJobs.containsKey(eri)) {
         this.eagerRefreshJobs.put(eri, System.currentTimeMillis() + timeoutDelay);
         if (debugLogger.isDebugEnabled()) {
            debug("adding " + eri + " eager refresh job with timeoutDelay: " + timeoutDelay);
         }
      } else if (debugLogger.isDebugEnabled()) {
         debug("eager refresh job " + eri + " already scheduled - skipping new job registration");
      }

   }

   public void invokeEagerRefreshQuery(EagerRefreshInfo eri) throws InternalException {
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("Invoking eagerRefreshQuery: " + eri);
         }

         this.pushEnvironment();
         SecurityHelper.pushCallerPrincipal(KERNEL_ID);
         SecurityHelper.pushAnonymousSubject(KERNEL_ID);
         Collection var2 = this.collectionFinder(eri.getMethod(), eri.getArgs(), this.info.hasLocalClientView());
      } finally {
         SecurityHelper.popRunAsSubject(KERNEL_ID);

         try {
            SecurityHelper.popCallerPrincipal(KERNEL_ID);
         } catch (PrincipalNotFoundException var8) {
            debug("PrincipalNotFoundException thrown when invoking eager refresh query: " + var8);
         }

         this.popEnvironment();
      }

   }

   private static class CategoryTimerListener implements TimerListener {
      final TTLManager beanManager;
      final Object categoryValue;

      public CategoryTimerListener(TTLManager beanManager, Object categoryValue) {
         this.beanManager = beanManager;
         this.categoryValue = categoryValue;
      }

      public void timerExpired(Timer timer) {
         try {
            ManagedInvocationContext mic = this.beanManager.getBeanInfo().setCIC();
            Throwable var3 = null;

            try {
               this.beanManager.invokeFindByCategory(this.categoryValue);
               this.beanManager.getCategories().remove(this.categoryValue);
            } catch (Throwable var13) {
               var3 = var13;
               throw var13;
            } finally {
               if (mic != null) {
                  if (var3 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } catch (InternalException var15) {
            if (BaseEJBManager.debugLogger.isDebugEnabled()) {
               TTLManager.debug("Category Finder invocation failed. Later the data will be loaded from database." + StackTraceUtilsClient.throwable2StackTrace(var15));
            }

            this.beanManager.getCategories().remove(this.categoryValue);
         }

      }
   }

   private static class EagerRefreshListener implements Runnable {
      private final TTLManager beanManager;
      private final Map eagerRefreshJobs;

      EagerRefreshListener(TTLManager beanManager, Map eagerRefreshJobs) {
         this.beanManager = beanManager;
         this.eagerRefreshJobs = eagerRefreshJobs;
      }

      public void run() {
         ArrayList expiringJobs = new ArrayList();

         while(true) {
            long curTime = System.currentTimeMillis();
            expiringJobs.clear();
            synchronized(this.eagerRefreshJobs) {
               Iterator var5 = this.eagerRefreshJobs.keySet().iterator();

               while(true) {
                  if (!var5.hasNext()) {
                     break;
                  }

                  EagerRefreshInfo eri = (EagerRefreshInfo)var5.next();
                  if ((Long)this.eagerRefreshJobs.get(eri) <= curTime + 10000L) {
                     expiringJobs.add(eri);
                  }
               }
            }

            Iterator var4 = expiringJobs.iterator();

            while(var4.hasNext()) {
               EagerRefreshInfo eri = (EagerRefreshInfo)var4.next();

               try {
                  this.beanManager.invokeEagerRefreshQuery(eri);
                  this.eagerRefreshJobs.remove(eri);
               } catch (InternalException var9) {
                  if (BaseEJBManager.debugLogger.isDebugEnabled()) {
                     TTLManager.debug("eager refresh query invoke failed: " + var9);
                  }
               }
            }

            try {
               Thread.sleep(10000L);
            } catch (InterruptedException var10) {
               if (BaseEJBManager.debugLogger.isDebugEnabled()) {
                  TTLManager.debug("Thread.sleep got interrupted: " + StackTraceUtilsClient.throwable2StackTrace(var10));
               }
            }
         }
      }
   }

   private static class EagerRefreshInfo {
      private final Method method;
      private final Object[] args;

      EagerRefreshInfo(Method method, Object[] args) {
         this.method = method;
         this.args = args;
      }

      Method getMethod() {
         return this.method;
      }

      Object[] getArgs() {
         return this.args;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append(this.method.getName());
         sb.append('(');

         for(int i = 0; i < this.args.length; ++i) {
            if (i > 0) {
               sb.append(", ");
            }

            sb.append(this.args[i]);
         }

         sb.append(')');
         return sb.toString();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            EagerRefreshInfo that = (EagerRefreshInfo)o;
            if (!Arrays.equals(this.args, that.args)) {
               return false;
            } else {
               if (this.method != null) {
                  if (!this.method.equals(that.method)) {
                     return false;
                  }
               } else if (that.method != null) {
                  return false;
               }

               return true;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.method != null ? this.method.hashCode() : 0;
         result = 31 * result + (this.args != null ? Arrays.hashCode(this.args) : 0);
         return result;
      }
   }
}
