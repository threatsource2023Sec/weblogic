package weblogic.ejb.container.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBContext;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EnterpriseBean;
import javax.ejb.EntityBean;
import javax.ejb.ObjectNotFoundException;
import javax.naming.Context;
import javax.transaction.Transaction;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.cache.EntityNRUCache;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.EntityEJBContextImpl;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.locks.ExclusiveLockManager;
import weblogic.ejb.container.locks.LockManager;
import weblogic.ejb.container.monitoring.EJBCacheRuntimeMBeanImpl;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.RuntimeCheckerException;
import weblogic.ejb20.cache.CacheFullException;
import weblogic.ejb20.locks.LockTimedOutException;
import weblogic.logging.Loggable;
import weblogic.management.runtime.EntityEJBRuntimeMBean;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;

public class ExclusiveEntityManager extends BaseEntityManager implements BeanManager, CMPBeanManager, CachingManager {
   private boolean cacheBetweenTransactions = false;
   private boolean delayUpdatedUntilEndOfTx = true;
   private LockManager lockManager;
   protected EntityNRUCache cache;
   protected EntityBeanInfo info;
   private EJBCacheRuntimeMBeanImpl cacheRTMBean;
   private int beanSize;
   private int idleTimeoutSeconds;
   boolean scrubberStarted = false;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 5093397990852965126L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.ExclusiveEntityManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;

   public ExclusiveEntityManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      throw new AssertionError("BeanManager.setup() should never be called on ExclusiveEntityManager.");
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, EJBCache cache, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, i, environmentContext, sh);
      EntityEJBRuntimeMBean mb = (EntityEJBRuntimeMBean)this.getEJBRuntimeMBean();
      this.cacheRTMBean = (EJBCacheRuntimeMBeanImpl)mb.getCacheRuntime();
      this.info = (EntityBeanInfo)i;
      this.lockManager = new ExclusiveLockManager(mb.getLockingRuntime());
      this.lockManager.setup(this.info);
      this.idleTimeoutSeconds = this.info.getCachingDescriptor().getIdleTimeoutSecondsCache();
      if (cache == null) {
         this.cache = new EntityNRUCache(this.getDisplayName(), this.info.getCachingDescriptor().getMaxBeansInCache());
         this.beanSize = 1;
         this.cache.setScrubInterval(this.idleTimeoutSeconds);
         this.cache.startScrubber();
         this.scrubberStarted = true;
      } else {
         if (!(cache instanceof EntityNRUCache)) {
            Loggable l = EJBLogger.lognotAnExclusiveCacheLoggable(this.info.getEJBName(), this.info.getCacheName());
            throw new WLDeploymentException(l.getMessage());
         }

         this.cache = (EntityNRUCache)cache;
         if (this.cache.usesMaxBeansInCache()) {
            this.beanSize = 1;
         } else {
            this.beanSize = this.info.getEstimatedBeanSize();
         }

         this.cache.setScrubInterval(this.idleTimeoutSeconds);
      }

      this.cache.register(this);
      this.cacheRTMBean.setReInitializableCache(this.cache);
      this.cacheBetweenTransactions = this.info.getCacheBetweenTransactions();
      this.delayUpdatedUntilEndOfTx = this.info.getBoxCarUpdates();
   }

   private boolean acquireLock(InvocationWrapper wrap, Object pk) throws InternalException {
      int timeoutSeconds = wrap.getMethodDescriptor().getTxTimeoutSeconds();
      Object txOrThread = wrap.getInvokeTxOrThread();
      return this.acquireLock(pk, txOrThread, timeoutSeconds);
   }

   private boolean acquireLock(Object pk, Object txOrThread, int timeoutSeconds) throws InternalException {
      try {
         return this.lockManager.lock(pk, txOrThread, timeoutSeconds);
      } catch (LockTimedOutException var5) {
         throw new InternalException(var5.getMessage(), var5);
      }
   }

   protected boolean shouldLoad(Object pk, boolean cacheBetweenTransactions, boolean alreadyLoadedInthisTx) {
      return !cacheBetweenTransactions && !alreadyLoadedInthisTx;
   }

   public EnterpriseBean preInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[2];
            Object[] var10000 = var6.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         var6.resetPostBegin();
      }

      EntityBean var9;
      try {
         super.preInvoke();
         Object pk = wrap.getPrimaryKey();
         Object txOrThread = wrap.getInvokeTxOrThread();
         int timeoutSeconds = wrap.getMethodDescriptor().getTxTimeoutSeconds();
         EntityBean bean = this.getReadyBean(pk, txOrThread, timeoutSeconds);
         this.checkForReentrant(bean, pk);
         ((WLEntityBean)bean).__WL_setBusy(true);
         var9 = bean;
      } catch (Throwable var8) {
         if (var6 != null) {
            var6.th = var8;
            var6.ret = null;
            InstrumentationSupport.process(var6);
         }

         throw var8;
      }

      if (var6 != null) {
         var6.ret = var9;
         InstrumentationSupport.process(var6);
      }

      return var9;
   }

   private EntityBean getReadyBean(Object pk, Object txOrThread, int timeoutSeconds) throws InternalException {
      boolean alreadyLoaded = this.acquireLock(pk, txOrThread, timeoutSeconds);
      boolean forceLoad = false;
      EntityBean bean = null;
      if (!$assertionsDisabled && alreadyLoaded && txOrThread == null) {
         throw new AssertionError();
      } else {
         CacheKey key = new CacheKey(pk, this);

         try {
            this.cacheRTMBean.incrementCacheAccessCount();
            bean = (EntityBean)this.cache.get(key);
            if (bean == null) {
               EJBObject eo = null;
               EJBLocalObject elo = null;
               if (this.remoteHome != null) {
                  eo = this.remoteHome.allocateEO(pk);
               }

               if (this.localHome != null) {
                  elo = this.localHome.allocateELO(pk);
               }

               bean = this.getBeanFromPool();
               WLEJBContext ctx = (WLEJBContext)((WLEntityBean)bean).__WL_getEJBContext();
               ((EntityEJBContextImpl)ctx).__WL_setPrimaryKey(pk);
               ctx.setEJBObject(eo);
               ctx.setEJBLocalObject(elo);
               this.cache.put(key, bean);
               this.cacheRTMBean.incrementCachedBeansCurrentCount();
               forceLoad = true;

               try {
                  bean.ejbActivate();
                  this.cacheRTMBean.incrementActivationCount();
               } catch (Throwable var13) {
                  EJBLogger.logErrorDuringActivate(StackTraceUtils.throwable2StackTrace(var13));
                  this.destroyPooledBean(bean);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbActivate:", var13);
                  throw new AssertionError("does not reach");
               }
            } else {
               this.cacheRTMBean.incrementCacheHitCount();
            }

            if (this.shouldLoad(pk, this.cacheBetweenTransactions, alreadyLoaded) || forceLoad) {
               try {
                  bean.ejbLoad();
               } catch (Throwable var12) {
                  EJBLogger.logErrorFromLoad(var12);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var12);
                  throw new AssertionError("does not reach");
               }
            }

            if (!alreadyLoaded) {
               this.setupTxListenerAndTxUser(pk, txOrThread, (WLEntityBean)bean);
            }

            return bean;
         } catch (Throwable var14) {
            this.cache.removeOnError(key);
            if (!alreadyLoaded) {
               this.lockManager.unlock(pk, txOrThread);
            }

            EJBRuntimeUtils.throwInternalException("Exception during transition from pooled to ready:", var14);
            throw new AssertionError("does not reach");
         }
      }
   }

   protected boolean shouldStoreAfterMethod(InvocationWrapper wrap) {
      return wrap.getInvokeTx() == null || !this.delayUpdatedUntilEndOfTx;
   }

   public int getBeanSize() {
      return this.beanSize;
   }

   public void enrollInTransaction(Transaction tx, CacheKey key, EntityBean bean, RSInfo rsInfo) throws InternalException {
      throw new AssertionError("method 'enrollInTransaction' not valid for ExclusiveEntityManager");
   }

   public void selectedForReplacement(CacheKey key, EntityBean bean) {
      throw new AssertionError("method 'selectedForReplacement' not valid for ExclusiveEntityManager");
   }

   public void loadBeanFromRS(CacheKey key, EntityBean bean, RSInfo rsInfo) {
      throw new AssertionError("method 'copy' not valid for ExclusiveEntityManager");
   }

   public void removedFromCache(CacheKey key, Object bean) {
      ((WLEntityBean)bean).__WL_setBusy(false);
      this.releaseBeanToPool((EntityBean)bean);
      this.cacheRTMBean.decrementCachedBeansCurrentCount();
   }

   public void removedOnError(CacheKey key, Object bean) {
      this.cacheRTMBean.decrementCachedBeansCurrentCount();
      this.destroyPooledBean((EntityBean)bean);
   }

   public void swapIn(CacheKey key, Object bean) {
      if (debugLogger.isDebugEnabled()) {
         debug("Activating key: " + key);
      }

      if (!$assertionsDisabled && !(bean instanceof EntityBean)) {
         throw new AssertionError();
      } else {
         try {
            ((EntityBean)bean).ejbActivate();
         } catch (Exception var4) {
            this.cache.removeOnError(key);
            this.cacheRTMBean.decrementCachedBeansCurrentCount();
            EJBLogger.logExceptionDuringEJBActivate(var4);
         }

      }
   }

   public void swapOut(CacheKey key, Object bean, long timeLastTouched) {
      if (debugLogger.isDebugEnabled()) {
         debug("Passivating key: " + key);
      }

      if (!$assertionsDisabled && !(bean instanceof EntityBean)) {
         throw new AssertionError();
      } else {
         try {
            this.cacheRTMBean.incrementPassivationCount();
            ((EntityBean)bean).ejbPassivate();
         } catch (Exception var6) {
            EJBLogger.logErrorDuringPassivation(StackTraceUtils.throwable2StackTrace(var6));
         }

      }
   }

   public void replicate(CacheKey key, Object bean) {
   }

   public void postInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[2];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         InstrumentationSupport.createDynamicJoinPoint(var10);
         InstrumentationSupport.process(var10);
         var10.resetPostBegin();
      }

      Object pk = wrap.getPrimaryKey();
      CacheKey key = new CacheKey(pk, this);
      EntityBean bean = (EntityBean)this.cache.get(key);
      if (bean != null) {
         ((WLEntityBean)bean).__WL_setBusy(false);
      }

      boolean var15 = false;

      try {
         var15 = true;
         if (this.shouldStoreAfterMethod(wrap)) {
            try {
               if (bean != null) {
                  if (this.shouldStore(bean)) {
                     bean.ejbStore();
                     var15 = false;
                  } else {
                     var15 = false;
                  }
               } else {
                  var15 = false;
               }
            } catch (Throwable var18) {
               EJBLogger.logExcepInStore(var18);
               this.cache.removeOnError(key);
               EJBRuntimeUtils.throwInternalException("Exception in ejbStore:", var18);
               var15 = false;
            }
         } else {
            var15 = false;
         }
      } finally {
         if (var15) {
            if (bean != null && ((WLEntityBean)bean).__WL_needsRemove()) {
               if (debugLogger.isDebugEnabled()) {
                  debug("releasing the bean inside postInvoke");
               }

               ((WLEntityBean)bean).__WL_setNeedsRemove(false);
               this.lockManager.unlock(pk, wrap.getInvokeTxOrThread());
            } else if (bean == null) {
               Transaction tx = wrap.getInvokeTx();
               if (tx != null) {
                  try {
                     if (tx.getStatus() == 4) {
                        this.lockManager.unlock(pk, wrap.getInvokeTxOrThread());
                     }
                  } catch (Exception var16) {
                     debugLogger.debug("Unexpected Exception when calling Transaction.getStatus()", var16);
                  }
               }
            }

         }
      }

      if (bean != null && ((WLEntityBean)bean).__WL_needsRemove()) {
         if (debugLogger.isDebugEnabled()) {
            debug("releasing the bean inside postInvoke");
         }

         ((WLEntityBean)bean).__WL_setNeedsRemove(false);
         this.lockManager.unlock(pk, wrap.getInvokeTxOrThread());
      } else if (bean == null) {
         Transaction tx = wrap.getInvokeTx();
         if (tx != null) {
            try {
               if (tx.getStatus() == 4) {
                  this.lockManager.unlock(pk, wrap.getInvokeTxOrThread());
               }
            } catch (Exception var17) {
               debugLogger.debug("Unexpected Exception when calling Transaction.getStatus()", var17);
            }
         }
      }

   }

   public EnterpriseBean preHomeInvoke(InvocationWrapper wrap) throws InternalException {
      return this.getBeanFromPool();
   }

   public void postHomeInvoke(InvocationWrapper wrap) throws InternalException {
      if (wrap.hasSystemExceptionOccured()) {
         this.destroyPooledBean((EntityBean)wrap.getBean());
      } else {
         this.releaseBeanToPool((EntityBean)wrap.getBean());
      }

   }

   public void destroyInstance(InvocationWrapper wrap, Throwable ee) {
      Object pk = wrap.getPrimaryKey();
      if (!$assertionsDisabled && pk == null) {
         throw new AssertionError();
      } else {
         this.cache.removeOnError(new CacheKey(pk, this));
      }
   }

   protected void prepareVerificationForBatch(Collection primaryKeys, Transaction tx) throws InternalException {
   }

   protected List pkListToBeanList(Collection keys, Transaction tx, boolean isRemove) {
      List beanList = new ArrayList();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         Object pk = it.next();
         CacheKey key = new CacheKey(pk, this);
         EntityBean bean = (EntityBean)this.cache.get(key);
         if (isRemove) {
            if (bean != null) {
               beanList.add(bean);
            }
         } else if (bean != null && !((CMPBean)bean).__WL_getIsRemoved()) {
            beanList.add(bean);
         }
      }

      return beanList;
   }

   protected Map pkListToPkBeanMap(Collection keys, Transaction tx, boolean isRemove) {
      Map keyBeanMap = new LinkedHashMap();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         Object pk = it.next();
         CacheKey key = new CacheKey(pk, this);
         EntityBean bean = (EntityBean)this.cache.get(key);
         if (isRemove) {
            if (bean != null) {
               keyBeanMap.put(pk, bean);
            }
         } else if (bean != null && !((CMPBean)bean).__WL_getIsRemoved()) {
            keyBeanMap.put(pk, bean);
         }
      }

      return keyBeanMap;
   }

   public void beforeCompletion(Collection primaryKeys, Transaction tx) throws InternalException {
      this.beforeCompletion(primaryKeys, (Object)tx);
   }

   public void beforeCompletion(Collection primaryKeys, Object txOrThread) throws InternalException {
      if (!$assertionsDisabled && primaryKeys == null) {
         throw new AssertionError();
      } else {
         Iterator it = primaryKeys.iterator();

         while(it.hasNext()) {
            Object pk = it.next();
            CacheKey key = new CacheKey(pk, this);
            EntityBean bean = (EntityBean)this.cache.get(key);

            try {
               if (bean != null && this.shouldStore(bean)) {
                  Transaction tx = null;
                  if (txOrThread instanceof Transaction) {
                     tx = (Transaction)txOrThread;
                  }

                  if (this.orderDatabaseOperations && tx != null) {
                     ((CMPBean)bean).__WL_superEjbStore();
                  } else {
                     bean.ejbStore();
                  }
               }
            } catch (Throwable var8) {
               EJBLogger.logExcepInStore1(var8);
               this.cache.removeOnError(key);
               EJBRuntimeUtils.throwInternalException("Exception in ejbStore:", var8);
            }
         }

      }
   }

   public void flushModified(Collection modifiedKeys, Transaction tx) throws InternalException {
      if (!$assertionsDisabled && modifiedKeys == null) {
         throw new AssertionError();
      } else {
         Iterator it = modifiedKeys.iterator();

         while(true) {
            while(it.hasNext()) {
               Object pk = it.next();
               CacheKey key = new CacheKey(pk, this);
               EntityBean bean = (EntityBean)this.cache.get(key);
               if (this.isBeanManagedPersistence) {
                  try {
                     if (bean != null && this.shouldStore(bean)) {
                        bean.ejbStore();
                     }
                  } catch (Throwable var8) {
                     this.cache.removeOnError(key);
                     EJBRuntimeUtils.throwInternalException("Error writing from flushModified", var8);
                  }
               } else {
                  try {
                     if (bean != null && this.shouldStore(bean)) {
                        if (this.orderDatabaseOperations && tx != null) {
                           ((CMPBean)bean).__WL_superEjbStore();
                        } else {
                           ((CMPBean)bean).__WL_store(false);
                        }
                     }
                  } catch (Throwable var9) {
                     EJBLogger.logExcepInStore1(var9);
                     this.cache.removeOnError(key);
                     EJBRuntimeUtils.throwInternalException("Error calling ejbStore.", var9);
                  }
               }
            }

            return;
         }
      }
   }

   public void afterCompletion(Collection primaryKeys, Transaction tx, int result, Object ignore) {
      this.afterCompletion(primaryKeys, (Object)tx, result, ignore);
   }

   public void afterCompletion(Collection primaryKeys, Object txOrThread, int result, Object ignore) {
      if (!$assertionsDisabled && primaryKeys == null) {
         throw new AssertionError();
      } else if (!$assertionsDisabled && txOrThread == null) {
         throw new AssertionError();
      } else if (!$assertionsDisabled && result != 3 && result != 4) {
         throw new AssertionError();
      } else {
         Iterator it = primaryKeys.iterator();

         while(it.hasNext()) {
            Object pk = it.next();
            CacheKey key = new CacheKey(pk, this);
            WLEntityBean wlbean = (WLEntityBean)this.cache.get(key);

            try {
               if (this.cacheBetweenTransactions && result == 4) {
                  Object bean = this.cache.get(key);
                  if (bean != null) {
                     try {
                        this.cacheRTMBean.incrementPassivationCount();
                        ((EntityBean)bean).ejbPassivate();
                     } catch (Exception var14) {
                        EJBLogger.logErrorDuringPassivation(StackTraceUtils.throwable2StackTrace(var14));
                        this.cache.removeOnError(key);
                     }

                     this.cache.remove(key);
                  }
               } else if (this.remoteHome == null || wlbean != null && !wlbean.__WL_isCreatorOfTx()) {
                  WLEntityBean bean = (WLEntityBean)this.cache.get(key);
                  if (bean != null) {
                     bean.__WL_setOperationsComplete(false);
                  }

                  this.cache.release(key);
               }
            } finally {
               if (wlbean == null) {
                  this.lockManager.unlock(pk, txOrThread);
               } else if (!wlbean.__WL_isCreatorOfTx()) {
                  if (!wlbean.__WL_isBusy()) {
                     this.lockManager.unlock(pk, txOrThread);
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debug("afterCompletion called before postInvoke");
                     }

                     wlbean.__WL_setNeedsRemove(true);
                  }
               }

            }
         }

      }
   }

   public EJBObject remoteCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      return (EJBObject)this.create(wrap, createMethod, postCreateMethod, args);
   }

   public EJBLocalObject localCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      return (EJBLocalObject)this.create(wrap, createMethod, postCreateMethod, args);
   }

   private Object create(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      LocalHolder var16;
      if ((var16 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var16.argsCapture) {
            var16.args = new Object[5];
            Object[] var10000 = var16.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = createMethod;
            var10000[3] = postCreateMethod;
            var10000[4] = args;
         }

         InstrumentationSupport.createDynamicJoinPoint(var16);
         InstrumentationSupport.preProcess(var16);
         var16.resetPostBegin();
      }

      Object var38;
      try {
         EntityBean bean = this.getBeanFromPool();
         ((WLEntityBean)bean).__WL_setBusy(true);
         boolean postCreateDone = false;
         Object pk = null;
         EJBObject eo = null;
         EJBLocalObject elo = null;

         Throwable t;
         try {
            pk = createMethod.invoke(bean, args);
         } catch (InvocationTargetException var33) {
            this.destroyPooledBean(bean);
            t = var33.getTargetException();
            EJBRuntimeUtils.throwInternalException("Exception in ejbCreate()", t);
         } catch (Throwable var34) {
            this.destroyPooledBean(bean);
            this.handleMethodException(createMethod, (Class[])null, var34);
         }

         try {
            if (pk == null) {
               if (!(bean instanceof CMPBean)) {
                  throw new RuntimeCheckerException("Your BMP ejbCreate should not be returning null.");
               }

               postCreateMethod.invoke(bean, args);
               postCreateDone = true;
               pk = ((CMPBean)bean).__WL_getPrimaryKey();
            }
         } catch (InvocationTargetException var31) {
            this.destroyPooledBean(bean);
            t = var31.getTargetException();
            EJBRuntimeUtils.throwInternalException("Exception in ejbCreate()", t);
         } catch (Throwable var32) {
            this.destroyPooledBean(bean);
            this.handleMethodException(postCreateMethod, this.extraPostCreateExceptions, var32);
         }

         EntityEJBContextImpl ctx = (EntityEJBContextImpl)((WLEntityBean)bean).__WL_getEJBContext();
         ctx.__WL_setPrimaryKey(pk);
         Object txOrThread = wrap.getInvokeTxOrThread();
         CacheKey key = new CacheKey(pk, this);

         try {
            this.acquireLock(wrap, pk);
         } catch (InternalException var30) {
            this.destroyPooledBean(bean);
            throw var30;
         }

         boolean createAfterRemove = false;
         if (this.orderDatabaseOperations && wrap.getInvokeTx() != null) {
            CMPBean beanFromCache = (CMPBean)this.cache.get(key);
            if (beanFromCache != null && beanFromCache.__WL_getIsRemoved()) {
               createAfterRemove = true;
               beanFromCache.__WL_setBusy(true);
               beanFromCache.__WL_initialize(false);
               beanFromCache.__WL_setIsRemoved(false);
               if (debugLogger.isDebugEnabled()) {
                  debug("collision occurred, __WL_copyFrom, pk=" + pk + ", txOrThread=" + txOrThread);
               }

               beanFromCache.__WL_copyFrom((CMPBean)bean, false);
               ((WLEntityBean)bean).__WL_setBusy(false);
               this.releaseBeanToPool(bean);
               bean = (EntityBean)beanFromCache;
            }
         }

         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(pk);
         }

         if (this.localHome != null) {
            elo = this.localHome.allocateELO(pk);
         }

         ctx.setEJBObject(eo);
         ctx.setEJBLocalObject(elo);
         if (!createAfterRemove) {
            try {
               this.cache.put(key, bean);
               this.cacheRTMBean.incrementCachedBeansCurrentCount();
            } catch (CacheFullException var29) {
               this.lockManager.unlock(pk, txOrThread);
               this.destroyPooledBean(bean);
               throw new InternalException("Error during create.", var29);
            }
         }

         try {
            if (createAfterRemove) {
               this.registerInsertDeletedBeanAndTxUser(pk, wrap.getInvokeTx(), (WLEntityBean)bean);
            } else if (this.orderDatabaseOperations && wrap.getInvokeTx() != null) {
               this.registerInsertBeanAndTxUser(pk, wrap.getInvokeTx(), (WLEntityBean)bean);
            } else {
               this.setupTxListenerAndTxUser(pk, txOrThread, (WLEntityBean)bean);
            }
         } catch (InternalException var35) {
            this.cache.removeOnError(key);
            this.lockManager.unlock(pk, txOrThread);
            throw var35;
         }

         if (!postCreateDone) {
            try {
               postCreateMethod.invoke(bean, args);
            } catch (IllegalAccessException var27) {
               throw new AssertionError(var27);
            } catch (InvocationTargetException var28) {
               this.cache.removeOnError(key);
               Throwable t = var28.getTargetException();
               this.handleMethodException(postCreateMethod, this.extraPostCreateExceptions, t);
            }
         }

         ((WLEntityBean)bean).__WL_setBusy(false);
         var38 = wrap.isLocal() ? elo : eo;
      } catch (Throwable var36) {
         if (var16 != null) {
            var16.th = var36;
            var16.ret = null;
            InstrumentationSupport.postProcess(var16);
         }

         throw var36;
      }

      if (var16 != null) {
         var16.ret = var38;
         InstrumentationSupport.postProcess(var16);
      }

      return var38;
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      Object pk = wrap.getPrimaryKey();
      CacheKey key = new CacheKey(pk, this);
      EntityBean bean = null;
      Iterator restIterCascadeDelBeans = null;

      try {
         try {
            bean = (EntityBean)this.preInvoke(wrap);
         } catch (Exception var7) {
            throw new NoSuchObjectException("Bean with key: " + wrap.getPrimaryKey() + " could not be removed." + PlatformConstants.EOL + "The underlying exception was:" + StackTraceUtils.throwable2StackTrace(var7));
         }

         restIterCascadeDelBeans = this.cascadeDeleteRemove(wrap, bean);
         if (this.orderDatabaseOperations && wrap.getInvokeTx() != null) {
            ((CMPBean)bean).__WL_superEjbRemove(false);
            ((CMPBean)bean).__WL_setIsRemoved(true);
            if (!this.registerDeleteBean(pk, wrap.getInvokeTx())) {
               if (((CMPBean)bean).__WL_getIsRemoved()) {
                  ((CMPBean)bean).__WL_initialize();
                  ((CMPBean)bean).__WL_setIsRemoved(false);
               }

               this.cache.remove(key);
            }
         } else {
            bean.ejbRemove();
            this.cache.remove(key);
         }

         if (this.timerManager != null) {
            this.timerManager.removeTimersForPK(pk);
         }
      } catch (Throwable var8) {
         this.cache.removeOnError(key);
         EJBRuntimeUtils.throwInternalException("Exception during remove.", var8);
      }

      this.cascadeDeleteRemove(wrap, bean, restIterCascadeDelBeans);
   }

   public void remove(InvocationWrapper wrap, EntityBean bean, boolean withoutDBUpdate) throws InternalException {
      Object pk = ((CMPBean)bean).__WL_getPrimaryKey();
      CacheKey key = new CacheKey(pk, this);
      this.checkForReentrant(bean, pk);
      ((WLEntityBean)bean).__WL_setBusy(true);

      try {
         if (withoutDBUpdate) {
            ((CMPBean)bean).__WL_superEjbRemove(true);
            this.cache.remove(key);
         } else if (this.orderDatabaseOperations && wrap.getInvokeTx() != null) {
            ((CMPBean)bean).__WL_superEjbRemove(false);
            ((CMPBean)bean).__WL_setIsRemoved(true);
            if (!this.registerDeleteBean(pk, wrap.getInvokeTx())) {
               if (((CMPBean)bean).__WL_getIsRemoved()) {
                  ((CMPBean)bean).__WL_initialize();
                  ((CMPBean)bean).__WL_setIsRemoved(false);
               }

               this.cache.remove(key);
            }
         } else {
            bean.ejbRemove();
            this.cache.remove(key);
         }

         if (this.timerManager != null) {
            this.timerManager.removeTimersForPK(pk);
         }
      } catch (Throwable var7) {
         this.cache.removeOnError(key);
         EJBRuntimeUtils.throwInternalException("Exception in remove:", var7);
      }

   }

   public EnterpriseBean lookup(Object pk) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("lookup called(pk=" + pk + ")");
      }

      if (!$assertionsDisabled && pk == null) {
         throw new AssertionError();
      } else {
         EntityBean var3;
         try {
            this.pushEnvironment();
            int timeoutMillis = 5000;
            var3 = this.getReadyBean(pk, this.getInvokeTxOrThread(), timeoutMillis / 1000);
         } finally {
            this.popEnvironment();
         }

         return var3;
      }
   }

   public EntityBean getBeanFromRS(Object txOrThread, Object pk, RSInfo rsInfo) throws InternalException {
      EntityBean bean = null;
      bean = this.getBeanFromCache(pk, txOrThread, 0);
      if (bean != null) {
         this.cacheRTMBean.incrementCacheHitCount();
      } else {
         bean = this.getBeanFromPool();
         ((CMPBean)bean).__WL_initialize();
         this.persistence.loadBeanFromRS(bean, rsInfo);
         if (!this.finderCacheInsert(bean)) {
            return null;
         }
      }

      return bean;
   }

   private EntityBean getBeanFromCache(Object pk, Object txOrThread, int timeout) {
      return null;
   }

   protected boolean finderCacheInsert(Object txOrThread, Object pk, EJBObject eo, EJBLocalObject elo, EntityBean beanFromFinder) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("called finderCacheInsert...");
      }

      if (!$assertionsDisabled && eo == null && elo == null) {
         throw new AssertionError();
      } else {
         boolean alreadyLoaded = false;

         try {
            if (debugLogger.isDebugEnabled()) {
               debug("\tprimary key=" + pk);
            }

            if (!$assertionsDisabled && pk == null) {
               throw new AssertionError();
            }

            alreadyLoaded = this.acquireLock(pk, txOrThread, 0);
            if (debugLogger.isDebugEnabled()) {
               debug("\tafter acquireLock: alreadyLoaded=" + (alreadyLoaded ? "true" : "false"));
            }
         } catch (Exception var26) {
            this.releaseBeanToPool(beanFromFinder);
            return false;
         }

         CacheKey key = new CacheKey(pk, this);

         boolean shouldLoad;
         try {
            this.pushEnvironment();
            EntityBean beanFromCache = (EntityBean)this.cache.get(key);
            if (beanFromCache == null) {
               if (debugLogger.isDebugEnabled()) {
                  debug("beanFromCache == null, pk=" + pk + ", txOrThread=" + txOrThread);
               }

               EJBContext ctx = ((WLEntityBean)beanFromFinder).__WL_getEJBContext();
               ((WLEJBContext)ctx).setEJBObject(eo);
               ((WLEJBContext)ctx).setEJBLocalObject(elo);

               try {
                  this.cache.put(key, beanFromFinder);
               } catch (CacheFullException var23) {
                  this.releaseBeanToPool(beanFromFinder);
                  this.lockManager.unlock(pk, txOrThread);
                  boolean var11 = false;
                  return var11;
               }

               this.cacheRTMBean.incrementCachedBeansCurrentCount();
               this.initLastRead(pk);

               try {
                  beanFromFinder.ejbActivate();
                  this.cacheRTMBean.incrementActivationCount();
               } catch (Throwable var22) {
                  EJBLogger.logErrorDuringActivate(StackTraceUtils.throwable2StackTrace(var22));
                  this.destroyPooledBean(beanFromFinder);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbActivate:", var22);
                  throw new AssertionError("does not reach");
               }

               try {
                  ((CMPBean)beanFromFinder).__WL_superEjbLoad();
               } catch (Throwable var21) {
                  EJBLogger.logErrorFromLoad(var21);
                  this.destroyPooledBean(beanFromFinder);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var21);
                  throw new AssertionError("does not reach");
               }

               if (!alreadyLoaded) {
                  this.setupTxListenerAndTxUser(pk, txOrThread, (WLEntityBean)beanFromFinder);
               }
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debug("__WL_copyFrom, pk=" + pk + ", txOrThread=" + txOrThread);
               }

               shouldLoad = this.shouldLoad(pk, this.cacheBetweenTransactions, alreadyLoaded);
               if (shouldLoad) {
                  ((CMPBean)beanFromCache).__WL_initialize();
               }

               if (this.uses20CMP || shouldLoad) {
                  ((CMPBean)beanFromCache).__WL_copyFrom((CMPBean)beanFromFinder, true);
               }

               this.releaseBeanToPool(beanFromFinder);
               if (shouldLoad) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("\tloading cached bean");
                  }

                  try {
                     ((CMPBean)beanFromCache).__WL_superEjbLoad();
                  } catch (Throwable var20) {
                     EJBLogger.logErrorFromLoad(var20);
                     EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var20);
                     throw new AssertionError("does not reach");
                  }
               }

               if (!alreadyLoaded) {
                  this.setupTxListenerAndTxUser(pk, txOrThread, (WLEntityBean)beanFromCache);
               }
            }

            shouldLoad = beanFromCache == null;
            return shouldLoad;
         } catch (Throwable var24) {
            if (debugLogger.isDebugEnabled()) {
               debug("\texception thrown in finderCacheInsert: ", var24);
            }

            this.cache.removeOnError(key);
            this.lockManager.unlock(pk, txOrThread);
            shouldLoad = false;
         } finally {
            this.popEnvironment();
         }

         return shouldLoad;
      }
   }

   protected void cacheRemoveBean(Transaction tx, Object pk) {
      this.cache.remove(new CacheKey(pk, this));
   }

   protected void cacheRemoveBeanOnError(Transaction tx, Object pk) {
      this.cache.removeOnError(new CacheKey(pk, this));
   }

   protected EntityBean alreadyCached(Object txOrThread, Object pk) throws InternalException {
      EntityBean bean = null;
      Object lockClient = this.lockManager.getOwner(pk);
      if (this.cacheBetweenTransactions || lockClient == txOrThread) {
         bean = (EntityBean)this.cache.get(new CacheKey(pk, this), false);
         if (bean != null && !this.isBeanManagedPersistence && ((CMPBean)bean).__WL_getIsRemoved()) {
            Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(pk.toString());
            EJBRuntimeUtils.throwInternalException("EJB Exception: ", new ObjectNotFoundException(l.getMessageText()));
            bean = null;
         }
      }

      return bean;
   }

   protected void initLastRead(Object pk) {
   }

   public void beanImplClassChangeNotification() {
      super.beanImplClassChangeNotification();
      this.cache.beanImplClassChangeNotification();
   }

   public void updateMaxBeansInCache(int max) {
      this.cache.updateMaxBeansInCache(max);
   }

   public void updateIdleTimeoutSecondsCache(int idleTimeoutSeconds) {
      this.cache.updateIdleTimeoutSeconds(idleTimeoutSeconds);
   }

   public void releaseBean(InvocationWrapper wrap) {
      Object pk = wrap.getPrimaryKey();
      CacheKey key = new CacheKey(pk, this);
      this.cache.release(key);
      this.lockManager.unlock(pk, wrap.getInvokeTxOrThread());
   }

   public int getIdleTimeoutSeconds() {
      return this.idleTimeoutSeconds;
   }

   public boolean getVerifyReads() {
      return false;
   }

   public void passivateAndRelease(CacheKey key, EntityBean bean) {
   }

   public boolean hasBeansEnrolledInTx(Transaction tx) {
      return false;
   }

   public boolean isFlushPending(Transaction tx, Object pk) {
      return false;
   }

   public boolean isPassivatible(Transaction tx, Object pk, boolean queuedForFlush, boolean flushSuccess) {
      return false;
   }

   public int cachePassivateModifiedBean(Transaction tx, Object pk, boolean flushSuccess) {
      return 0;
   }

   public int cachePassivateUnModifiedBean(Transaction tx, Object pk) {
      return 0;
   }

   public boolean passivateModifiedBean(Transaction tx, Object pk, boolean flushSuccess, EntityBean bean) {
      return false;
   }

   public boolean passivateUnModifiedBean(Transaction tx, Object pk, EntityBean bean) {
      return false;
   }

   public void postFinderCleanup(Object pkOrEo, Collection pksOrEos, boolean isPk, boolean isLocal) {
   }

   public void unpin(Object txOrThread, Object pk) {
   }

   public void flushModified(Collection modifiedKeys, Transaction tx, boolean internalFlush, Collection flushedModifiedKeys) {
   }

   public void operationsComplete(Transaction tx, Object pk) {
      EntityBean bean = null;

      try {
         bean = this.getReadyBean(pk, tx, 0);
      } catch (InternalException var5) {
      }

      if (bean != null) {
         ((WLEntityBean)bean).__WL_setOperationsComplete(true);
      }

   }

   public boolean beanIsOpsComplete(Transaction tx, Object pk) {
      EntityBean bean = null;

      try {
         bean = this.getReadyBean(pk, tx, 0);
      } catch (InternalException var5) {
      }

      return bean == null ? true : ((WLEntityBean)bean).__WL_getOperationsComplete();
   }

   private static void debug(String s) {
      debugLogger.debug("[ExclusiveEntityManager] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[ExclusiveEntityManager] " + s, th);
   }

   public void undeploy() {
      super.undeploy();
      if (this.scrubberStarted) {
         this.cache.stopScrubber();
      }

   }

   public void passivateAndBacktoPool(CacheKey key, EntityBean bean) {
   }

   public void doEjbRemove(Object bean) {
      throw new AssertionError("Should not be called");
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Create_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ExclusiveEntityManager.java", "weblogic.ejb.container.manager.ExclusiveEntityManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljavax/ejb/EnterpriseBean;", 207, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ExclusiveEntityManager.java", "weblogic.ejb.container.manager.ExclusiveEntityManager", "postInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)V", 410, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ExclusiveEntityManager.java", "weblogic.ejb.container.manager.ExclusiveEntityManager", "create", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", 734, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Create_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ExclusiveEntityManager.java", "weblogic.ejb.container.manager.ExclusiveEntityManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljava/lang/Object;", 67, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium};
      $assertionsDisabled = !ExclusiveEntityManager.class.desiredAssertionStatus();
   }
}
