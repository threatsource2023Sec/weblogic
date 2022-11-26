package weblogic.ejb.container.manager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import weblogic.cluster.GroupMessage;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.OptimisticConcurrencyException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.ReadConfig;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.cache.EntityCache;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.EntityEJBContextImpl;
import weblogic.ejb.container.internal.EntityEJBLocalObject;
import weblogic.ejb.container.internal.EntityEJBObject;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.CMPBeanManager;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.InvalidationMessage;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.RuntimeCheckerException;
import weblogic.ejb20.cache.CacheFullException;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;

public class DBManager extends BaseEntityManager implements BeanManager, CMPBeanManager, CachingManager, InvalidationBeanManager, RecoverListener {
   protected EntityCache publicCache;
   private boolean scrubberStarted = false;
   private EntityBeanInfo info;
   private boolean delayUpdatesUntilEndOfTx = true;
   private int beanSize;
   private int idleTimeoutSeconds = 0;
   protected InvalidationBeanManager invalidationTargetBM = null;
   private boolean cacheBetweenTransactions;
   protected boolean clusterInvalidationDisabled;
   protected MulticastSession multicastSession;
   private boolean verifyReads = false;
   private static final Object DUMMY_PK;
   private static int VERIFY_THRESHHOLD;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 6024120747260288861L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.DBManager");
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

   public DBManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      throw new AssertionError("BeanManager.setup() should never be called on DBManager.");
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, EJBCache cache, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, i, environmentContext, sh);
      this.info = (EntityBeanInfo)i;
      if (cache == null) {
         this.publicCache = new EntityCache(this.info.getEJBName(), this.info.getCachingDescriptor().getMaxBeansInCache());
         this.beanSize = 1;
         this.idleTimeoutSeconds = this.info.getCachingDescriptor().getIdleTimeoutSecondsCache();
         this.publicCache.setScrubInterval(this.idleTimeoutSeconds);
         this.publicCache.startScrubber();
         this.scrubberStarted = true;
      } else {
         if (!(cache instanceof EntityCache)) {
            Loggable l = EJBLogger.lognotAMultiVersionCacheLoggable(this.info.getEJBName(), this.info.getCacheName());
            throw new WLDeploymentException(l.getMessage());
         }

         this.publicCache = (EntityCache)cache;
         if (this.publicCache.usesMaxBeansInCache()) {
            this.beanSize = 1;
         } else {
            this.beanSize = this.info.getEstimatedBeanSize();
         }

         this.idleTimeoutSeconds = this.info.getCachingDescriptor().getIdleTimeoutSecondsCache();
         this.publicCache.setScrubInterval(this.idleTimeoutSeconds);
      }

      this.publicCache.register(this);
      this.getEJBCacheRuntimeMBeanImpl().setReInitializableCache(this.publicCache);
      this.cacheBetweenTransactions = this.info.getCacheBetweenTransactions();
      String concurrencyStrategy = this.info.getCachingDescriptor().getConcurrencyStrategy();
      if (concurrencyStrategy == null || "Database".equalsIgnoreCase(concurrencyStrategy)) {
         this.cacheBetweenTransactions = false;
      }

      if (!this.cacheBetweenTransactions && (this.info.getConcurrencyStrategy() == 2 || this.info.getConcurrencyStrategy() == 6)) {
         this.publicCache.setDisableReadyCache(this.info.getDisableReadyInstances());
      }

      this.delayUpdatesUntilEndOfTx = this.info.getBoxCarUpdates();
      this.invalidationTargetBM = this.info.getInvalidationTargetBeanManager();
      boolean inCluster = ReadConfig.isClusteredServer();
      if (inCluster && concurrencyStrategy != null && (this.rdbmsPersistence == null || !this.rdbmsPersistence.getRDBMSBean().isClusterInvalidationDisabled())) {
         if ((!concurrencyStrategy.equalsIgnoreCase("Optimistic") || !this.cacheBetweenTransactions) && !concurrencyStrategy.equalsIgnoreCase("ReadOnly")) {
            this.clusterInvalidationDisabled = true;
         } else {
            this.multicastSession = Locator.locate().createMulticastSession(this, -1);
            this.clusterInvalidationDisabled = false;
         }
      } else {
         this.clusterInvalidationDisabled = true;
      }

      if (this.rdbmsPersistence != null) {
         this.verifyReads = this.rdbmsPersistence.getVerifyReads();
      }

   }

   protected EntityCache getCache() {
      return this.publicCache;
   }

   public EnterpriseBean preInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[2];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         var5.resetPostBegin();
      }

      EntityBean var8;
      try {
         super.preInvoke();
         Object txOrThread = wrap.getInvokeTxOrThread();
         Object pk = wrap.getPrimaryKey();
         EntityBean bean = this.getReadyBean(txOrThread, pk, true);
         this.checkForReentrant(bean, pk);
         ((WLEntityBean)bean).__WL_setBusy(true);
         var8 = bean;
      } catch (Throwable var7) {
         if (var5 != null) {
            var5.th = var7;
            var5.ret = null;
            InstrumentationSupport.process(var5);
         }

         throw var7;
      }

      if (var5 != null) {
         var5.ret = var8;
         InstrumentationSupport.process(var5);
      }

      return var8;
   }

   private EntityBean getReadyBean(Object txOrThread, Object pk, boolean pinCache) throws InternalException {
      if (!$assertionsDisabled && txOrThread == null) {
         throw new AssertionError();
      } else {
         EntityBean bean = this.getCache().get(txOrThread, new CacheKey(pk, this), pinCache);
         this.cacheRTMBean.incrementCacheAccessCount();
         if (bean != null) {
            if (txOrThread instanceof Thread) {
               try {
                  this.loadBean(pk, bean, (RSInfo)null, false);
               } catch (Throwable var9) {
                  EJBLogger.logErrorFromLoad(var9);
                  this.getCache().removeOnError(txOrThread, new CacheKey(pk, this));
                  EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var9);
                  throw new AssertionError("cannot reach");
               }
            }

            this.cacheRTMBean.incrementCacheHitCount();
            return bean;
         } else {
            bean = this.getBeanFromPool();
            if (!$assertionsDisabled && ((WLEntityBean)bean).__WL_isBusy()) {
               throw new AssertionError();
            } else {
               EJBContext ctx = ((WLEntityBean)bean).__WL_getEJBContext();
               ((EntityEJBContextImpl)ctx).__WL_setPrimaryKey(pk);
               EJBObject eo = null;
               EJBLocalObject elo = null;
               if (this.remoteHome != null) {
                  eo = this.remoteHome.allocateEO(pk);
               }

               ((WLEJBContext)ctx).setEJBObject(eo);
               if (this.localHome != null) {
                  elo = this.localHome.allocateELO(pk);
               }

               ((WLEJBContext)ctx).setEJBLocalObject(elo);

               try {
                  bean.ejbActivate();
                  this.cacheRTMBean.incrementActivationCount();
               } catch (Throwable var12) {
                  EJBLogger.logErrorDuringActivate(StackTraceUtils.throwable2StackTrace(var12));
                  this.destroyPooledBean(bean);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbActivate", var12);
                  throw new AssertionError("will not reach");
               }

               if (this.supportsCopy()) {
                  this.perhapsCopy(pk, bean);
               }

               try {
                  this.loadBean(pk, bean, (RSInfo)null, true);
               } catch (Throwable var11) {
                  EJBLogger.logErrorFromLoad(var11);
                  this.destroyPooledBean(bean);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var11);
                  throw new AssertionError("cannot reach");
               }

               try {
                  this.setupTxListenerAndTxUser(pk, txOrThread, (WLEntityBean)bean);
                  this.getCache().put(txOrThread, new CacheKey(pk, this), bean, this, pinCache);
                  this.cacheRTMBean.incrementCachedBeansCurrentCount();
                  return bean;
               } catch (Throwable var10) {
                  EJBLogger.logErrorFromLoad(var10);
                  this.destroyPooledBean(bean);
                  EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var10);
                  throw new AssertionError("cannot reach");
               }
            }
         }
      }
   }

   public int getBeanSize() {
      return this.beanSize;
   }

   public void enrollInTransaction(Transaction tx, CacheKey key, EntityBean bean, RSInfo rsInfo) throws InternalException {
      try {
         Object pk = key.getPrimaryKey();
         this.loadBean(pk, bean, rsInfo, false);
         this.setupTxListenerAndTxUser(pk, tx, (WLEntityBean)bean);
      } catch (Throwable var6) {
         EJBLogger.logErrorFromLoad(var6);
         this.getCache().removeOnError(tx, key);
         EJBRuntimeUtils.throwInternalException("Exception in ejbLoad:", var6);
         throw new AssertionError("cannot reach");
      }
   }

   public int getIdleTimeoutSeconds() {
      return this.idleTimeoutSeconds;
   }

   public boolean getVerifyReads() {
      return this.verifyReads;
   }

   public void selectedForReplacement(CacheKey key, EntityBean bean) {
      this.passivateAndRelease(key, bean);
      this.cacheRTMBean.decrementCachedBeansCurrentCount();
   }

   public void passivateAndRelease(CacheKey key, EntityBean bean) {
      try {
         bean.ejbPassivate();
         this.releaseBeanToPool(bean);
      } catch (Throwable var7) {
         EJBLogger.logErrorPassivating(StackTraceUtils.throwable2StackTrace(var7));
         this.destroyPooledBean(bean);
      } finally {
         this.cacheRTMBean.incrementPassivationCount();
      }

   }

   public void loadBeanFromRS(CacheKey key, EntityBean bean, RSInfo rsInfo) throws InternalException {
      if (this.uses20CMP) {
         ((RDBMSPersistenceManager)this.persistence).loadBeanFromRS(bean, rsInfo);
      }

   }

   public void removedOnError(CacheKey key, Object bean) {
      this.cacheRTMBean.decrementCachedBeansCurrentCount();
      this.destroyPooledBean((EntityBean)bean);
   }

   public void removedFromCache(CacheKey key, Object bean) {
      ((WLEntityBean)bean).__WL_setBusy(false);
      this.releaseBeanToPool((EntityBean)bean);
      this.cacheRTMBean.decrementCachedBeansCurrentCount();
   }

   public void swapIn(CacheKey key, Object bean) {
      throw new AssertionError("method 'swapIn' not valid for DBManager");
   }

   public void swapOut(CacheKey key, Object bean, long timeLastTouched) {
      throw new AssertionError("method 'swapOut' not valid for DBManager");
   }

   public void replicate(CacheKey key, Object bean) {
      throw new AssertionError("method 'replicate' not valid for DBManager");
   }

   protected void loadBean(Object pk, EntityBean bean, RSInfo rsInfo, boolean forceLoad) throws Throwable {
      if (rsInfo == null) {
         if (forceLoad || !this.cacheBetweenTransactions || !((CMPBean)bean).__WL_isBeanStateValid()) {
            bean.ejbLoad();
            if (!this.isBeanManagedPersistence && this.uses20CMP) {
               ((CMPBean)bean).__WL_setBeanStateValid(true);
            }
         }
      } else {
         CMPBean beanFromCache = (CMPBean)bean;
         if (!this.cacheBetweenTransactions || !((CMPBean)bean).__WL_isBeanStateValid()) {
            beanFromCache.__WL_initialize();
            if (!this.isBeanManagedPersistence && this.uses20CMP) {
               beanFromCache.__WL_setBeanStateValid(true);
            }
         }

         this.persistence.loadBeanFromRS(bean, rsInfo);
         beanFromCache.__WL_superEjbLoad();
      }

   }

   protected void storeBean(EntityBean bean, Object pk) throws Throwable {
      if (this.shouldStore(bean)) {
         bean.ejbStore();
      }

   }

   public void postInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[2];
            Object[] var10000 = var11.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.process(var11);
         var11.resetPostBegin();
      }

      EntityBean bean = (EntityBean)wrap.getBean();
      WLEntityBean wlBean = (WLEntityBean)bean;
      Object pk = wrap.getPrimaryKey();
      CacheKey key = new CacheKey(pk, this);
      Throwable th = null;
      boolean release = false;
      synchronized(bean) {
         wlBean.__WL_setBusy(false);
         if (wlBean.__WL_needsRemove()) {
            if (debugLogger.isDebugEnabled()) {
               debug("postInvoke: setNeedsRemove, txOrThread= " + wrap.getInvokeTxOrThread() + ", ejb- " + this.info.getEJBName() + ", pk- " + pk);
            }

            wlBean.__WL_setLoadUser((Object)null);
            if (this.cacheBetweenTransactions) {
               try {
                  this.storeBean(bean, pk);
               } catch (Throwable var13) {
                  EJBLogger.logErrorFromStore(var13);
                  th = var13;
               }

               if (th == null) {
                  throw new AssertionError("storeBean was expected to fail!");
               }
            } else if (this.remoteHome == null || !wlBean.__WL_isCreatorOfTx() || wrap.getInvokeTx() == null) {
               release = true;
            }
         } else if (!this.delayUpdatesUntilEndOfTx || wrap.getInvokeTx() == null) {
            try {
               this.storeBean(bean, pk);
            } catch (Throwable var12) {
               EJBLogger.logErrorFromStore(var12);
               th = var12;
            }
         }
      }

      if (release) {
         this.cacheReleaseBean((EntityBean)null, wrap.getInvokeTxOrThread(), key, this.getCache());
      } else {
         if (th != null) {
            this.getCache().removeOnError(wrap.getInvokeTxOrThread(), key);
            if (debugLogger.isDebugEnabled()) {
               debug("postInvoke: ejbStore failed, txOrThread= " + wrap.getInvokeTxOrThread() + ", ejb- " + this.info.getEJBName() + ", pk- " + pk);
            }

            EJBRuntimeUtils.throwInternalException("Exception in ejbStore:", th);
         }

         this.getCache().unpin(wrap.getInvokeTxOrThread(), key);
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
      Object txOrThread = wrap.getInvokeTxOrThread();
      Object pk = wrap.getPrimaryKey();
      if (pk != null) {
         if (!$assertionsDisabled && txOrThread == null) {
            throw new AssertionError();
         } else {
            if (wrap.shouldLogException()) {
               EJBLogger.logErrorDuringBeanInvocation(this.getDisplayName(), pk.toString(), ee);
            }

            EntityBean bean = (EntityBean)wrap.getBean();
            if (!$assertionsDisabled && bean == null) {
               throw new AssertionError();
            } else if (!$assertionsDisabled && pk == null) {
               throw new AssertionError();
            } else {
               this.getCache().removeOnError(txOrThread, new CacheKey(pk, this));
            }
         }
      }
   }

   private void doVerificationForBatch(List verifyPk, StringBuffer[] verifySql, int[] verifyCount, Transaction tx, boolean requireXLock) throws SQLException {
      Connection con = null;
      PreparedStatement[] verifyStmt = null;
      ResultSet[] verifyResult = null;

      try {
         con = this.rdbmsPersistence.getConnection();
         verifyStmt = this.rdbmsPersistence.prepareStatement(con, verifySql, verifyCount, requireXLock);
         if (debugLogger.isDebugEnabled()) {
            for(int i = 0; i < verifySql.length; ++i) {
               debug("verifySql[" + i + "]: " + verifySql[i]);
            }
         }

         int[] verifyCur = this.rdbmsPersistence.getVerifyCur();
         Iterator iter = verifyPk.iterator();

         while(iter.hasNext()) {
            Object pk = iter.next();
            CMPBean cmpBean = (CMPBean)this.getCache().getActive(tx, new CacheKey(pk, this), false);
            if (cmpBean == null) {
               throw new AssertionError("no bean found for pk: " + pk);
            }

            cmpBean.__WL_setVerifyParamsForBatch(con, verifyStmt, verifyCur);
         }

         verifyResult = this.rdbmsPersistence.executeQuery(verifyStmt);
         this.rdbmsPersistence.checkResults(verifyResult, verifyCount);
      } finally {
         this.rdbmsPersistence.releaseArrayResources(con, verifyStmt, verifyResult);
      }

   }

   protected void prepareVerificationForBatch(Collection primaryKeys, Transaction tx) throws InternalException {
      if (this.rdbmsPersistence.needsBatchOperationsWorkaround()) {
         List verifyPk = new ArrayList();
         StringBuffer[] verifySql = null;
         Integer isolationLevel = (Integer)((weblogic.transaction.Transaction)tx).getProperty("ISOLATION LEVEL");
         boolean requireXLock = isolationLevel == null || isolationLevel != 4 && isolationLevel != 8;
         if (debugLogger.isDebugEnabled()) {
            debug("Require exclusive lock for batch? " + requireXLock);
         }

         verifySql = this.rdbmsPersistence.getVerifySql(requireXLock);
         int[] verifyCount = this.rdbmsPersistence.getVerifyCount();
         int verifyMax = 0;
         Iterator it;
         if (debugLogger.isDebugEnabled()) {
            for(int i = 0; i < verifySql.length; ++i) {
               debug("sql[" + i + "] = " + verifySql[i]);
               debug("count[" + i + "] = " + verifyCount[i]);
               debug("verifyMax = " + verifyMax);
            }

            it = verifyPk.iterator();

            while(it.hasNext()) {
               debug("pk- " + it.next());
            }

            debug("*************************************************");
         }

         it = primaryKeys.iterator();

         try {
            while(true) {
               CMPBean cmpBean;
               do {
                  if (!it.hasNext()) {
                     if (verifyMax > 0) {
                        this.doVerificationForBatch(verifyPk, verifySql, verifyCount, tx, requireXLock);
                     }

                     return;
                  }

                  Object pk = it.next();
                  cmpBean = (CMPBean)this.getCache().getActive(tx, new CacheKey(pk, this), false);
               } while(cmpBean == null);

               verifyMax = cmpBean.__WL_appendVerifySqlForBatch(verifyPk, verifySql, verifyCount, verifyMax);
               if (debugLogger.isDebugEnabled()) {
                  for(int i = 0; i < verifySql.length; ++i) {
                     debug("sql[" + i + "] = " + verifySql[i]);
                     debug("count[" + i + "] = " + verifyCount[i]);
                     debug("verifyMax = " + verifyMax);
                  }

                  Iterator itt = verifyPk.iterator();

                  while(itt.hasNext()) {
                     debug("pk- " + itt.next());
                  }

                  debug("------------------------------------------");
               }

               if (verifyMax >= VERIFY_THRESHHOLD) {
                  this.doVerificationForBatch(verifyPk, verifySql, verifyCount, tx, requireXLock);
                  verifyPk.clear();
                  verifySql = this.rdbmsPersistence.getVerifySql(requireXLock);
                  verifyCount = this.rdbmsPersistence.getVerifyCount();
                  verifyMax = 0;
               }
            }
         } catch (Throwable var13) {
            if (!(var13 instanceof OptimisticConcurrencyException)) {
               EJBLogger.logExcepInBeforeCompletion(StackTraceUtils.throwable2StackTrace(var13));
            }

            EJBRuntimeUtils.throwInternalException("Exception during before completion:", var13);
            throw new AssertionError("cannot reach");
         }
      }
   }

   private void doVerification(List verifyPk, StringBuffer[] verifySql, int[] verifyCount, Transaction tx, boolean requireXLock) throws SQLException {
      Connection con = null;

      try {
         con = this.rdbmsPersistence.getConnection();
         PreparedStatement[] verifyStmt = null;
         verifyStmt = this.rdbmsPersistence.prepareStatement(con, verifySql, verifyCount, requireXLock);
         if (debugLogger.isDebugEnabled()) {
            for(int i = 0; i < verifySql.length; ++i) {
               debug("verifySql[" + i + "]: " + verifySql[i]);
            }
         }

         int[] verifyCur = this.rdbmsPersistence.getVerifyCur();
         Iterator iter = verifyPk.iterator();

         while(iter.hasNext()) {
            Object pk = iter.next();
            CMPBean cmpBean = (CMPBean)this.getCache().getActive(tx, new CacheKey(pk, this), false);
            if (cmpBean == null) {
               throw new AssertionError("no bean found for pk: " + pk);
            }

            cmpBean.__WL_setVerifyParams(con, verifyStmt, verifyCur);
         }

         ResultSet[] verifyResult = this.rdbmsPersistence.executeQuery(verifyStmt);
         this.rdbmsPersistence.checkResults(verifyResult, verifyCount);
      } finally {
         this.rdbmsPersistence.releaseResources(con, (Statement)null, (ResultSet)null);
      }

   }

   protected List pkListToBeanList(Collection keys, Transaction tx, boolean isRemove) {
      List beanList = new ArrayList();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         Object pk = it.next();
         CacheKey key = new CacheKey(pk, this);
         EntityBean bean = this.getCache().getActive(tx, key, false);
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
      Map beanMap = new LinkedHashMap();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         Object pk = it.next();
         CacheKey key = new CacheKey(pk, this);
         EntityBean bean = this.getCache().getActive(tx, key, false);
         if (isRemove) {
            if (bean != null) {
               beanMap.put(pk, bean);
            }
         } else if (bean != null && !((CMPBean)bean).__WL_getIsRemoved()) {
            beanMap.put(pk, bean);
         }
      }

      return beanMap;
   }

   public void beforeCompletion(Collection primaryKeys, Transaction tx) throws InternalException {
      this.beforeCompletion(primaryKeys, (Object)tx);
   }

   public void beforeCompletion(Collection primaryKeys, Object txOrThread) throws InternalException {
      if (!$assertionsDisabled && txOrThread == null) {
         throw new AssertionError();
      } else if (!$assertionsDisabled && primaryKeys == null) {
         throw new AssertionError();
      } else {
         if (this.verifyReads) {
            Transaction tx = (Transaction)txOrThread;
            List verifyPk = new ArrayList();
            StringBuffer[] verifySql = null;
            Integer isolationLevel = (Integer)((weblogic.transaction.Transaction)tx).getProperty("ISOLATION LEVEL");
            boolean requireXLock = isolationLevel == null || isolationLevel != 4 && isolationLevel != 8;
            if (debugLogger.isDebugEnabled()) {
               debug("require exclusive lock? " + requireXLock);
            }

            verifySql = this.rdbmsPersistence.getVerifySql(requireXLock);
            int[] verifyCount = this.rdbmsPersistence.getVerifyCount();
            int verifyMax = 0;
            Iterator it;
            if (debugLogger.isDebugEnabled()) {
               for(int i = 0; i < verifySql.length; ++i) {
                  debug("sql[" + i + "] = " + verifySql[i]);
                  debug("count[" + i + "] = " + verifyCount[i]);
                  debug("verifyMax = " + verifyMax);
               }

               it = verifyPk.iterator();

               while(it.hasNext()) {
                  debug("pk- " + it.next());
               }

               debug("************************************************");
            }

            it = primaryKeys.iterator();

            try {
               label128:
               while(true) {
                  CMPBean cmpBean;
                  do {
                     if (!it.hasNext()) {
                        if (verifyMax > 0) {
                           this.doVerification(verifyPk, verifySql, verifyCount, tx, requireXLock);
                        }
                        break label128;
                     }

                     Object pk = it.next();
                     cmpBean = (CMPBean)this.getCache().getActive(tx, new CacheKey(pk, this), false);
                  } while(cmpBean == null);

                  verifyMax = cmpBean.__WL_appendVerifySql(verifyPk, verifySql, verifyCount, verifyMax);
                  if (debugLogger.isDebugEnabled()) {
                     for(int i = 0; i < verifySql.length; ++i) {
                        debug("sql[" + i + "] = " + verifySql[i]);
                        debug("count[" + i + "] = " + verifyCount[i]);
                        debug("verifyMax = " + verifyMax);
                     }

                     Iterator itt = verifyPk.iterator();

                     while(itt.hasNext()) {
                        debug("pk- " + itt.next());
                     }

                     debug("------------------------------------------");
                  }

                  if (verifyMax >= VERIFY_THRESHHOLD) {
                     this.doVerification(verifyPk, verifySql, verifyCount, tx, requireXLock);
                     verifyPk.clear();
                     verifySql = this.rdbmsPersistence.getVerifySql(requireXLock);
                     verifyCount = this.rdbmsPersistence.getVerifyCount();
                     verifyMax = 0;
                  }
               }
            } catch (Throwable var16) {
               if (!(var16 instanceof OptimisticConcurrencyException)) {
                  EJBLogger.logExcepInBeforeCompletion(StackTraceUtils.throwable2StackTrace(var16));
               } else {
                  Iterator iterator = verifyPk.iterator();

                  while(iterator.hasNext()) {
                     CacheKey ck = new CacheKey(iterator.next(), this);
                     CMPBean bean = (CMPBean)this.getCache().getActive(txOrThread, ck, false);
                     bean.__WL_setBeanStateValid(false);
                  }
               }

               EJBRuntimeUtils.throwInternalException("Exception during before completion:", var16);
               throw new AssertionError("cannot reach");
            }
         }

         Iterator it = primaryKeys.iterator();

         while(it.hasNext()) {
            Object pk = it.next();
            CacheKey key = new CacheKey(pk, this);
            EntityBean bean = this.getCache().getActive(txOrThread, key, true);

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
            } catch (Throwable var15) {
               if (!(var15 instanceof OptimisticConcurrencyException)) {
                  EJBLogger.logExcepFromStore(var15);
               }

               this.getCache().removeOnError(txOrThread, new CacheKey(pk, this));
               EJBRuntimeUtils.throwInternalException("Exception from ejbStore:", var15);
               throw new AssertionError("cannot reach");
            }
         }

      }
   }

   public void flushModified(Collection modifiedKeys, Transaction tx, boolean internalFlush, Collection flushedModifiedKeys) throws InternalException {
      Iterator it = modifiedKeys.iterator();

      while(true) {
         while(it.hasNext()) {
            Object pk = it.next();
            EntityBean bean = this.getCache().getActive(tx, new CacheKey(pk, this), false);
            if (this.isBeanManagedPersistence) {
               try {
                  if (bean != null && this.shouldStore(bean)) {
                     bean.ejbStore();
                     if (internalFlush) {
                        flushedModifiedKeys.add(pk);
                     }
                  }
               } catch (Throwable var9) {
                  this.getCache().removeOnError(tx, new CacheKey(pk, this));
                  EJBRuntimeUtils.throwInternalException("Error writing from flushModified", var9);
               }
            } else {
               try {
                  if (bean != null && this.shouldStore(bean)) {
                     if (this.orderDatabaseOperations && tx != null) {
                        ((CMPBean)bean).__WL_superEjbStore();
                     } else {
                        ((CMPBean)bean).__WL_store(false);
                        if (internalFlush) {
                           flushedModifiedKeys.add(pk);
                        }
                     }
                  }
               } catch (Throwable var10) {
                  EJBLogger.logExcepFromStore(var10);
                  this.getCache().removeOnError(tx, new CacheKey(pk, this));
                  EJBRuntimeUtils.throwInternalException("Error writing from flushModified", var10);
               }
            }
         }

         return;
      }
   }

   public void afterCompletion(Collection primaryKeys, Transaction tx, int result, Object conversationCache) {
      this.afterCompletion(primaryKeys, (Object)tx, result, conversationCache);
   }

   public void afterCompletion(Collection primaryKeys, Object txOrThread, int result, Object conversationCache) {
      Iterator it = primaryKeys.iterator();

      while(true) {
         Object pk;
         CacheKey key;
         EntityCache entityCache;
         EntityBean bean;
         do {
            if (!it.hasNext()) {
               return;
            }

            pk = it.next();
            key = new CacheKey(pk, this);
            entityCache = conversationCache != null ? (EntityCache)conversationCache : this.publicCache;
            bean = entityCache.getActive(txOrThread, key, true);
         } while(bean == null);

         boolean releaseBean = false;
         synchronized(bean) {
            WLEntityBean wlBean = (WLEntityBean)bean;
            if (!wlBean.__WL_isBusy()) {
               wlBean.__WL_setLoadUser((Object)null);
               CMPBean cmpBean;
               if (this.cacheBetweenTransactions && result != 3) {
                  if (!this.isBeanManagedPersistence && this.uses20CMP) {
                     cmpBean = (CMPBean)bean;
                     if (!this.isOptimistic) {
                        cmpBean.__WL_setBeanStateValid(false);
                     } else if (cmpBean.__WL_isModified()) {
                        cmpBean.__WL_setBeanStateValid(false);
                     } else {
                        cmpBean.__WL_clearCMRFields();
                     }
                  }

                  releaseBean = true;
               } else if (this.remoteHome == null || !wlBean.__WL_isCreatorOfTx()) {
                  if ((result == 4 || result == 1 || result == 9) && !this.isBeanManagedPersistence && this.uses20CMP) {
                     cmpBean = (CMPBean)bean;
                     if (!this.isOptimistic) {
                        cmpBean.__WL_setBeanStateValid(false);
                     } else if (cmpBean.__WL_isModified()) {
                        cmpBean.__WL_setBeanStateValid(false);
                     } else {
                        cmpBean.__WL_clearCMRFields();
                     }

                     cmpBean.__WL_initialize();
                  }

                  releaseBean = true;
                  if (debugLogger.isDebugEnabled()) {
                     debug("afterCompletion: txOrThread- " + txOrThread + ", ejb- " + this.info.getEJBName() + ", pk- " + pk + " released from cache.");
                  }
               }
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debug("afterCompletion: setNeedsRemove, txOrThread= " + txOrThread + ", ejb- " + this.info.getEJBName() + ", pk- " + pk);
               }

               wlBean.__WL_setNeedsRemove(true);
            }
         }

         if (releaseBean) {
            this.cacheReleaseBean(bean, txOrThread, key, entityCache);
         } else {
            entityCache.unpin(txOrThread, key);
         }
      }
   }

   public EJBObject remoteCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      return (EJBObject)this.create(wrap, createMethod, postCreateMethod, args);
   }

   public EJBLocalObject localCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      return (EJBLocalObject)this.create(wrap, createMethod, postCreateMethod, args);
   }

   public Object create(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      LocalHolder var19;
      if ((var19 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var19.argsCapture) {
            var19.args = new Object[5];
            Object[] var10000 = var19.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = createMethod;
            var10000[3] = postCreateMethod;
            var10000[4] = args;
         }

         InstrumentationSupport.createDynamicJoinPoint(var19);
         InstrumentationSupport.preProcess(var19);
         var19.resetPostBegin();
      }

      Object var51;
      try {
         EntityBean bean = this.getBeanFromPool();
         ((WLEntityBean)bean).__WL_setBusy(true);
         boolean postCreateDone = false;
         Object pk = null;
         CacheKey key = null;
         EJBObject eo = null;
         EJBLocalObject elo = null;
         EJBContext ctx = ((WLEntityBean)bean).__WL_getEJBContext();
         if (this.remoteHome != null) {
            eo = this.remoteHome.allocateEO(DUMMY_PK);
         }

         ((WLEJBContext)ctx).setEJBObject(eo);
         if (this.localHome != null) {
            elo = this.localHome.allocateELO(DUMMY_PK);
         }

         ((WLEJBContext)ctx).setEJBLocalObject((EJBLocalObject)elo);

         Throwable t;
         try {
            pk = createMethod.invoke(bean, args);
         } catch (IllegalAccessException var44) {
            throw new AssertionError(var44);
         } catch (InvocationTargetException var45) {
            t = var45.getTargetException();
            if (debugLogger.isDebugEnabled()) {
               debug("Error during create: ", t);
            }

            this.destroyPooledBean(bean);
            this.handleMethodException(createMethod, (Class[])null, t);
         }

         try {
            if (pk == null) {
               if (!(bean instanceof CMPBean)) {
                  throw new InternalException("Error during create.", new RuntimeCheckerException("Your BMP ejbCreate should not be returning null."));
               }

               postCreateMethod.invoke(bean, args);
               postCreateDone = true;
               pk = ((CMPBean)bean).__WL_getPrimaryKey();
            }

            if (this.remoteHome != null) {
               eo = ((EntityEJBContextImpl)ctx).__WL_getEJBObject();
               ((EntityEJBObject)eo).setPrimaryKey(pk);
            }

            if (this.localHome != null) {
               elo = ((EntityEJBContextImpl)ctx).__WL_getEJBLocalObject();
               ((EntityEJBLocalObject)elo).setPrimaryKey(pk);
            }
         } catch (IllegalAccessException var46) {
            throw new AssertionError(var46);
         } catch (InvocationTargetException var47) {
            t = var47.getTargetException();
            if (debugLogger.isDebugEnabled()) {
               debug("Error during postCreate: " + t);
            }

            this.destroyPooledBean(bean);
            this.handleMethodException(postCreateMethod, this.extraPostCreateExceptions, t);
         }

         EntityEJBContextImpl entityCtx = (EntityEJBContextImpl)((WLEntityBean)bean).__WL_getEJBContext();
         entityCtx.__WL_setPrimaryKey(pk);
         key = new CacheKey(pk, this);
         Object txOrThread = wrap.getInvokeTxOrThread();
         if (!$assertionsDisabled && txOrThread == null) {
            throw new AssertionError();
         }

         boolean createAfterRemove = false;
         boolean beanAlreadyCached = false;
         if (!this.isBeanManagedPersistence) {
            CMPBean beanFromCache = (CMPBean)this.getCache().getActive(txOrThread, key, true);
            if (beanFromCache != null) {
               beanAlreadyCached = true;
               if (this.orderDatabaseOperations && beanFromCache.__WL_getIsRemoved()) {
                  createAfterRemove = true;
                  beanFromCache.__WL_setIsRemoved(false);
               }

               beanFromCache.__WL_setBusy(true);
               beanFromCache.__WL_initialize(false);
               if (debugLogger.isDebugEnabled()) {
                  debug("collision occurred, __WL_copyFrom, pk=" + pk + ", txOrThread=" + txOrThread);
               }

               beanFromCache.__WL_copyFrom((CMPBean)bean, false);
               ((WLEntityBean)bean).__WL_setBusy(false);
               this.releaseBeanToPool(bean);
               bean = (EntityBean)beanFromCache;
               ((WLEntityBean)bean).__WL_setOperationsComplete(false);
            }
         }

         if (!beanAlreadyCached) {
            try {
               this.getCache().put(txOrThread, key, bean, this, true);
               this.cacheRTMBean.incrementCachedBeansCurrentCount();
               this.initLastLoad(pk, bean);
            } catch (CacheFullException var42) {
               this.destroyPooledBean(bean);
               throw new InternalException("Error during create.", var42);
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
         } catch (InternalException var48) {
            this.getCache().removeOnError(txOrThread, new CacheKey(pk, this));
            throw var48;
         }

         if (!postCreateDone) {
            try {
               Debug.assertion(args != null);
               if (createAfterRemove) {
                  ((CMPBean)bean).__WL_setCreateAfterRemove(true);
               }

               postCreateMethod.invoke(bean, args);
            } catch (IllegalAccessException var40) {
               throw new AssertionError(var40);
            } catch (InvocationTargetException var41) {
               Throwable t = var41.getTargetException();
               this.getCache().removeOnError(txOrThread, key);
               this.handleMethodException(postCreateMethod, this.extraPostCreateExceptions, t);
            } finally {
               if (createAfterRemove) {
                  ((CMPBean)bean).__WL_setCreateAfterRemove(false);
               }

            }
         }

         ((WLEntityBean)bean).__WL_setBusy(false);
         this.getCache().unpin(txOrThread, key);
         var51 = wrap.isLocal() ? elo : eo;
      } catch (Throwable var49) {
         if (var19 != null) {
            var19.th = var49;
            var19.ret = null;
            InstrumentationSupport.postProcess(var19);
         }

         throw var49;
      }

      if (var19 != null) {
         var19.ret = var51;
         InstrumentationSupport.postProcess(var19);
      }

      return var51;
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      EntityBean bean = (EntityBean)this.preInvoke(wrap);
      Object txOrThread = wrap.getInvokeTxOrThread();
      Object pk = wrap.getPrimaryKey();
      Iterator restIterCascadeDelBeans = null;

      try {
         restIterCascadeDelBeans = this.cascadeDeleteRemove(wrap, bean);
         if (this.orderDatabaseOperations && wrap.getInvokeTx() != null) {
            ((CMPBean)bean).__WL_superEjbRemove(false);
            ((CMPBean)bean).__WL_setIsRemoved(true);
            if (!this.registerDeleteBean(pk, wrap.getInvokeTx())) {
               if (((CMPBean)bean).__WL_getIsRemoved()) {
                  ((CMPBean)bean).__WL_initialize();
                  ((CMPBean)bean).__WL_setIsRemoved(false);
               }

               this.getCache().remove(txOrThread, new CacheKey(pk, this));
            }
         } else {
            bean.ejbRemove();
            this.getCache().remove(txOrThread, new CacheKey(pk, this));
         }

         if (this.timerManager != null) {
            this.timerManager.removeTimersForPK(pk);
         }
      } catch (Throwable var7) {
         ((WLEntityBean)bean).__WL_setBusy(false);
         this.getCache().removeOnError(txOrThread, new CacheKey(pk, this));
         this.handleMethodException(wrap.getMethodDescriptor().getMethod(), (Class[])null, var7);
      }

      this.cascadeDeleteRemove(wrap, bean, restIterCascadeDelBeans);
      ((WLEntityBean)bean).__WL_setBusy(false);
   }

   public void remove(InvocationWrapper wrap, EntityBean bean, boolean withoutDBUpdate) throws InternalException {
      Object txOrThread = wrap.getInvokeTxOrThread();
      CMPBean cmpBean = (CMPBean)bean;
      EntityEJBContextImpl ctx = (EntityEJBContextImpl)cmpBean.__WL_getEntityContext();
      Object pk = ctx.__WL_getPrimaryKey();
      this.checkForReentrant(bean, pk);
      ((WLEntityBean)bean).__WL_setBusy(true);

      try {
         if (withoutDBUpdate) {
            ((CMPBean)bean).__WL_superEjbRemove(true);
            this.getCache().remove(txOrThread, new CacheKey(pk, this));
         } else if (this.orderDatabaseOperations && wrap.getInvokeTx() != null) {
            ((CMPBean)bean).__WL_superEjbRemove(false);
            ((CMPBean)bean).__WL_setIsRemoved(true);
            if (!this.registerDeleteBean(pk, wrap.getInvokeTx())) {
               if (((CMPBean)bean).__WL_getIsRemoved()) {
                  ((CMPBean)bean).__WL_initialize();
                  ((CMPBean)bean).__WL_setIsRemoved(false);
               }

               this.getCache().remove(txOrThread, new CacheKey(pk, this));
            }
         } else {
            bean.ejbRemove();
            this.getCache().remove(txOrThread, new CacheKey(pk, this));
         }

         if (this.timerManager != null) {
            this.timerManager.removeTimersForPK(pk);
         }
      } catch (Throwable var9) {
         this.getCache().removeOnError(txOrThread, new CacheKey(pk, this));
         this.handleMethodException(wrap.getMethodDescriptor().getMethod(), (Class[])null, var9);
      }

   }

   public EnterpriseBean lookup(Object pk) throws InternalException {
      return this.getReadyBean(this.getInvokeTxOrThread(), pk, false);
   }

   public EntityBean getBeanFromRS(Object txOrThread, Object pk, RSInfo rsInfo) throws InternalException {
      EntityBean bean = null;
      if (!$assertionsDisabled && txOrThread == null) {
         throw new AssertionError();
      } else {
         bean = this.getCache().get(txOrThread, new CacheKey(pk, this), rsInfo, true);
         this.cacheRTMBean.incrementCacheAccessCount();
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
   }

   protected boolean finderCacheInsert(Object txOrThread, Object pk, EJBObject eo, EJBLocalObject elo, EntityBean beanFromPool) throws InternalException {
      if (!$assertionsDisabled && eo == null && elo == null) {
         throw new AssertionError();
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("called finderCacheInsert...");
         }

         try {
            this.pushEnvironment();
            EJBContext ctx = ((WLEntityBean)beanFromPool).__WL_getEJBContext();
            ((WLEJBContext)ctx).setEJBObject(eo);
            ((WLEJBContext)ctx).setEJBLocalObject(elo);

            try {
               beanFromPool.ejbActivate();
               this.cacheRTMBean.incrementActivationCount();
            } catch (Throwable var16) {
               EJBLogger.logErrorDuringActivate(StackTraceUtils.throwable2StackTrace(var16));
               this.destroyPooledBean(beanFromPool);
               EJBRuntimeUtils.throwInternalException("Exception in ejbActivate", var16);
               throw new AssertionError("will not reach");
            }

            try {
               ((CMPBean)beanFromPool).__WL_superEjbLoad();
            } catch (Throwable var15) {
               EJBLogger.logExcepFromSuperLoad(var15);
               this.destroyPooledBean(beanFromPool);
               EJBRuntimeUtils.throwInternalException("Exception in superEjbLoad:", var15);
               throw new AssertionError("cannot reach");
            }
         } finally {
            this.popEnvironment();
         }

         try {
            Transaction tx = null;
            if (txOrThread instanceof Transaction) {
               tx = (Transaction)txOrThread;
            }

            this.setupTxListenerAndTxUser(pk, tx, (WLEntityBean)beanFromPool);
         } catch (InternalException var18) {
            this.destroyPooledBean(beanFromPool);
            throw var18;
         }

         try {
            this.getCache().put(txOrThread, new CacheKey(pk, this), beanFromPool, this, true);
            this.cacheRTMBean.incrementCachedBeansCurrentCount();
            this.initLastLoad(pk, beanFromPool);
            return true;
         } catch (CacheFullException var14) {
            this.passivateAndRelease(new CacheKey(pk, this), beanFromPool);
            return false;
         }
      }
   }

   public void postFinderCleanup(Object pkOrEo, Collection pksOrEos, boolean isPk, boolean isLocal) {
      if (this.findersLoadBean) {
         Object txOrThread = null;
         txOrThread = TransactionService.getTransaction();
         if (txOrThread == null) {
            txOrThread = Thread.currentThread();
         }

         Object pk = null;
         if (pkOrEo != null) {
            if (isPk) {
               pk = pkOrEo;
            } else if (isLocal) {
               pk = ((EJBLocalObject)pkOrEo).getPrimaryKey();
            } else {
               try {
                  pk = ((EJBObject)pkOrEo).getPrimaryKey();
               } catch (RemoteException var11) {
               }
            }

            if (pk != null) {
               this.getCache().unpin(txOrThread, new CacheKey(pk, this));
            }
         } else if (pksOrEos != null) {
            Iterator it;
            if (isPk) {
               it = pksOrEos.iterator();

               while(it.hasNext()) {
                  pk = it.next();
                  if (pk != null) {
                     this.getCache().unpin(txOrThread, new CacheKey(pk, this));
                  }
               }
            } else {
               it = pksOrEos.iterator();

               while(it.hasNext()) {
                  pk = null;
                  if (isLocal) {
                     EJBLocalObject elo = (EJBLocalObject)it.next();
                     if (elo != null) {
                        pk = elo.getPrimaryKey();
                     }
                  } else {
                     EJBObject eo = (EJBObject)it.next();
                     if (eo != null) {
                        try {
                           pk = eo.getPrimaryKey();
                        } catch (RemoteException var10) {
                        }
                     }
                  }

                  if (pk != null) {
                     this.getCache().unpin(txOrThread, new CacheKey(pk, this));
                  }
               }
            }
         }

      }
   }

   public void unpin(Object txOrThread, Object pk) {
      this.getCache().unpin(txOrThread, new CacheKey(pk, this));
   }

   protected void initLastLoad(Object pk, EntityBean bean) {
   }

   protected boolean supportsCopy() {
      return false;
   }

   protected void perhapsCopy(Object pk, EntityBean bean) throws InternalException {
   }

   protected void cacheRemoveBean(Transaction tx, Object pk) {
      this.getCache().remove(tx, new CacheKey(pk, this));
   }

   protected void cacheRemoveBeanOnError(Transaction tx, Object pk) {
      this.getCache().removeOnError(tx, new CacheKey(pk, this));
   }

   protected EntityBean alreadyCached(Object txOrThread, Object pk) throws InternalException {
      EntityBean bean = null;
      if (this.cacheBetweenTransactions) {
         bean = this.getCache().getValid(txOrThread, new CacheKey(pk, this), false);
      } else {
         bean = this.getCache().getActive(txOrThread, new CacheKey(pk, this), false);
      }

      if (bean != null && !this.isBeanManagedPersistence && ((CMPBean)bean).__WL_getIsRemoved()) {
         Loggable l = EJBLogger.lognoSuchEntityExceptionLoggable(pk.toString());
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", new ObjectNotFoundException(l.getMessageText()));
         bean = null;
      }

      return bean;
   }

   public void setMaxBeansInCache(int m) {
      this.getCache().setMaxBeansInCache(m);
   }

   public GroupMessage createRecoverMessage() {
      return new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName());
   }

   protected void sendInvalidate(Object pk) throws InternalException {
      InvalidationMessage msg;
      if (pk == null) {
         msg = new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName());
      } else if (pk instanceof Collection) {
         msg = new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName(), (Collection)pk);
      } else {
         msg = new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName(), pk);
      }

      try {
         this.multicastSession.send(msg);
      } catch (IOException var5) {
         Loggable l = EJBLogger.logErrorWhileMulticastingInvalidationLoggable(this.getDisplayName());
         throw new InternalException(l.getMessageText(), var5);
      }
   }

   public void invalidate(Object txOrThread, Object pk) throws InternalException {
      this.invalidateLocalServer(txOrThread, pk);
      if (!this.clusterInvalidationDisabled) {
         this.sendInvalidate(pk);
      }

   }

   public void invalidate(Object txOrThread, Collection pks) throws InternalException {
      this.invalidateLocalServer(txOrThread, pks);
      if (!this.clusterInvalidationDisabled) {
         this.sendInvalidate(pks);
      }

      this.resetInvalidationFlag(txOrThread, pks);
   }

   public void invalidateAll(Object txOrThread) throws InternalException {
      this.invalidateAllLocalServer(txOrThread);
      if (!this.clusterInvalidationDisabled) {
         this.sendInvalidate((Object)null);
      }

   }

   public void invalidateLocalServer(Object txOrThread, Object pk) {
      this.getCache().invalidate(txOrThread, new CacheKey(pk, this));
      if (this.invalidationTargetBM != null) {
         this.invalidationTargetBM.invalidateLocalServer(txOrThread, pk);
      }

   }

   public void invalidateLocalServer(Object txOrThread, Collection pks) {
      Collection keyCollection = new ArrayList();
      Iterator iter = pks.iterator();

      while(iter.hasNext()) {
         Object pk = iter.next();
         keyCollection.add(new CacheKey(pk, this));
      }

      this.getCache().invalidate(txOrThread, (Collection)keyCollection);
      if (this.invalidationTargetBM != null) {
         this.invalidationTargetBM.invalidateLocalServer(txOrThread, pks);
      }

   }

   private void resetInvalidationFlag(Object txOrThread, Collection pks) {
      if (txOrThread != null) {
         Iterator iter = pks.iterator();

         while(iter.hasNext()) {
            EntityBean bean = this.getCache().getActive(txOrThread, new CacheKey(iter.next(), this), false);
            if (bean != null) {
               ((CMPBean)bean).__WL_setInvalidatedBeanIsRegistered(false);
            }
         }

      }
   }

   public void invalidateAllLocalServer(Object txOrThread) {
      this.getCache().invalidateAll(txOrThread);
      if (this.invalidationTargetBM != null) {
         this.invalidationTargetBM.invalidateAllLocalServer(txOrThread);
      }

   }

   public void beanImplClassChangeNotification() {
      super.beanImplClassChangeNotification();
      this.getCache().beanImplClassChangeNotification();
   }

   public void updateMaxBeansInCache(int max) {
      this.getCache().updateMaxBeansInCache(max);
   }

   public void releaseBean(InvocationWrapper wrap) {
      try {
         Object txOrThread = wrap.getInvokeTxOrThread();
         Object pk = wrap.getPrimaryKey();
         CacheKey key = new CacheKey(pk, this);
         this.cacheReleaseBean((EntityBean)null, txOrThread, key, this.getCache());
      } catch (Exception var5) {
      }

   }

   private void cacheReleaseBean(EntityBean bean, Object txOrThread, CacheKey key, EntityCache entityCache) {
      if (bean == null) {
         bean = entityCache.getActive(txOrThread, key, false);
         if (bean == null) {
            return;
         }
      }

      ((WLEntityBean)bean).__WL_setOperationsComplete(false);
      if (bean instanceof CMPBean) {
         ((CMPBean)bean).__WL_setNonFKHolderRelationChange(false);
         ((CMPBean)bean).__WL_setM2NInsert(false);
      }

      entityCache.release(txOrThread, key);
   }

   public int cachePassivateModifiedBean(Transaction tx, Object pk, boolean flushSuccess) {
      return this.getCache().passivateModifiedBean(tx, new CacheKey(pk, this), flushSuccess);
   }

   public int cachePassivateUnModifiedBean(Transaction tx, Object pk) {
      int result = this.getCache().passivateUnModifiedBean(tx, new CacheKey(pk, this));

      for(int i = 0; i < result; ++i) {
         this.cacheRTMBean.decrementCachedBeansCurrentCount();
      }

      return result;
   }

   public void operationsComplete(Transaction tx, Object pk) {
      EntityBean bean = this.getCache().getActive(tx, new CacheKey(pk, this), false);
      if (bean != null) {
         ((WLEntityBean)bean).__WL_setOperationsComplete(true);
      }
   }

   public boolean beanIsOpsComplete(Transaction tx, Object pk) {
      EntityBean bean = this.getCache().getActive(tx, new CacheKey(pk, this), false);
      return bean == null ? true : ((WLEntityBean)bean).__WL_getOperationsComplete();
   }

   private static void debug(String s) {
      debugLogger.debug("[DBManager] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[DBManager] " + s, th);
   }

   public void updateIdleTimeoutSecondsCache(int idleTimeoutSeconds) {
      this.getCache().updateIdleTimeoutSeconds(idleTimeoutSeconds);
   }

   public void undeploy() {
      super.undeploy();
      if (this.scrubberStarted) {
         this.publicCache.stopScrubber();
      }

   }

   public void passivateAndBacktoPool(CacheKey key, EntityBean bean) {
      try {
         bean.ejbPassivate();
         ((WLEntityBean)bean).__WL_setBusy(false);
         this.releaseBeanToPool(bean);
      } catch (Throwable var7) {
         EJBLogger.logErrorPassivating(StackTraceUtils.throwable2StackTrace(var7));
         this.destroyPooledBean(bean);
      } finally {
         this.cacheRTMBean.incrementPassivationCount();
         this.cacheRTMBean.decrementCachedBeansCurrentCount();
      }

   }

   public void doEjbRemove(Object bean) {
      throw new AssertionError("Should not be called");
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Create_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DBManager.java", "weblogic.ejb.container.manager.DBManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljavax/ejb/EnterpriseBean;", 254, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DBManager.java", "weblogic.ejb.container.manager.DBManager", "postInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)V", 514, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Entity_Before_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DBManager.java", "weblogic.ejb.container.manager.DBManager", "create", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", 1375, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Create_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DBManager.java", "weblogic.ejb.container.manager.DBManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljava/lang/Object;", 94, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_Entity_After_Medium};
      $assertionsDisabled = !DBManager.class.desiredAssertionStatus();
      DUMMY_PK = new Object();
      VERIFY_THRESHHOLD = 50;
   }
}
