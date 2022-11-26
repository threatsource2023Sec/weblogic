package weblogic.ejb.container.manager;

import com.oracle.pitchfork.interfaces.intercept.__ProxyControl;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.ConcurrentAccessTimeoutException;
import javax.ejb.EJBContext;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.NoSuchEJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttributeType;
import javax.naming.Context;
import javax.persistence.SynchronizationType;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionSynchronizationRegistry;
import weblogic.application.naming.PersistenceUnitRegistry;
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
import weblogic.ejb.container.cache.LRUCache;
import weblogic.ejb.container.cache.NRUCache;
import weblogic.ejb.container.deployer.StatefulTimeoutConfiguration;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CachingDescriptor;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.SingleInstanceCache;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLSessionBean;
import weblogic.ejb.container.interfaces.WLSessionSynchronization;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.internal.SessionEJBContextImpl;
import weblogic.ejb.container.internal.StatefulEJBHome;
import weblogic.ejb.container.internal.StatefulEJBHomeImpl;
import weblogic.ejb.container.internal.StatefulEJBLocalHome;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.internal.TxManagerImpl;
import weblogic.ejb.container.locks.ExclusiveLockManager;
import weblogic.ejb.container.locks.LockManager;
import weblogic.ejb.container.monitoring.EJBCacheRuntimeMBeanImpl;
import weblogic.ejb.container.monitoring.StatefulEJBRuntimeMBeanImpl;
import weblogic.ejb.container.swap.DiskSwap;
import weblogic.ejb.container.swap.EJBSwap;
import weblogic.ejb.container.swap.ReplicatedMemorySwap;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.cache.CacheFullException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.locks.LockTimedOutException;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.StatefulEJBRuntimeMBean;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.ExtendedPersistenceContextManager;
import weblogic.persistence.ExtendedPersistenceContextWrapper;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.PartitionedStackPool;

public class StatefulSessionManager extends BaseEJBManager implements CachingManager {
   private static final boolean ENABLE_PROXY_POOL;
   private static final AuthenticatedSubject KERNEL_ID;
   private static final String METHOD_SIGNATURE_POSTACTIVATE = "postActivate()";
   private static final String METHOD_SIGNATURE_PREPASSIVATE = "prePassivate()";
   private SingleInstanceCache cache;
   private LockManager lockManager;
   private boolean implementsSessionBeanIntf;
   private boolean implementsSessionSynchronization;
   private int idleTimeoutSeconds;
   private StatefulEJBRuntimeMBean runtimeMBean;
   private EJBCacheRuntimeMBeanImpl cacheRTMBean;
   private AuthenticatedSubject fileDesc;
   private AuthenticatedSubject fileSector;
   private Map extendedPersistenceContextMap;
   private Map extendedPersistenceContextSyncTypeMap;
   private PartitionedStackPool proxyPool;
   protected EJBSwap swapper;
   protected KeyGenerator keyGenerator;
   private final Object bmTxLockClient = new Object();
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 5462537638525770353L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.StatefulSessionManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Remove_Around_High;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;

   public StatefulSessionManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      throw new AssertionError("Should not be called on StatefulSessionManager");
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo info, Context environmentContext, EJBCache cache, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, info, environmentContext, sh);
      this.beanClass = this.getBeanInfo().getGeneratedBeanClass();
      this.implementsSessionBeanIntf = SessionBean.class.isAssignableFrom(this.beanClass);

      try {
         this.runtimeMBean = new StatefulEJBRuntimeMBeanImpl(info, this.getEJBComponentRuntime());
         this.setEJBRuntimeMBean(this.runtimeMBean);
         this.addEJBRuntimeMBean(this.runtimeMBean);
      } catch (ManagementException var10) {
         Loggable l = EJBLogger.logFailedToCreateRuntimeMBeanLoggable();
         throw new WLDeploymentException(l.getMessageText(), var10);
      }

      this.txManager = new TxManagerImpl(this);
      this.cacheRTMBean = (EJBCacheRuntimeMBeanImpl)this.runtimeMBean.getCacheRuntime();
      this.keyGenerator = new SimpleKeyGenerator();
      this.keyGenerator.setup(info);
      this.swapper = this.createEJBSwap();
      this.initializeCache();
      if (ENABLE_PROXY_POOL) {
         this.proxyPool = new PartitionedStackPool(info.getCachingDescriptor().getMaxBeansInCache(), 20);
      }

      this.implementsSessionSynchronization = this.getBeanInfo().implementsSessionSynchronization();
      this.lockManager = new ExclusiveLockManager(this.runtimeMBean.getLockingRuntime());
      this.lockManager.setup(info);
      if (info.getPassivateAsPrincipalName() != null || info.getRunAsPrincipalName() != null) {
         try {
            if (info.getPassivateAsPrincipalName() != null) {
               this.fileSector = sh.getSubjectForPrincipal(info.getPassivateAsPrincipalName());
            }

            if (info.getRunAsPrincipalName() != null) {
               this.fileDesc = sh.getSubjectForPrincipal(info.getRunAsPrincipalName());
            }
         } catch (PrincipalNotFoundException var9) {
            throw new WLDeploymentException(var9.getMessage(), var9);
         }
      }

   }

   public void perhapsCallSetContext(Object o, EJBContext ctx) throws RemoteException {
      if (this.implementsSessionBeanIntf) {
         WLEnterpriseBean bean = (WLEnterpriseBean)o;
         AllowedMethodsHelper.pushBean(bean);
         int oldState = bean.__WL_getMethodState();
         bean.__WL_setMethodState(1);

         try {
            ((SessionBean)bean).setSessionContext((SessionContext)ctx);
         } finally {
            bean.__WL_setMethodState(oldState);
            AllowedMethodsHelper.popBean();
         }

      }
   }

   protected EJBSwap createEJBSwap() {
      return new DiskSwap(new File(this.getSwapDirectoryName()), this, this.getBeanInfo());
   }

   private void initializeCache() {
      CachingDescriptor cd = this.getBeanInfo().getCachingDescriptor();
      int scrubTimeoutSeconds;
      if (this.getBeanInfo().isStatefulTimeoutConfigured()) {
         StatefulTimeoutConfiguration config = this.getBeanInfo().getStatefulTimeoutConfiguration();
         this.idleTimeoutSeconds = (int)config.getStatefulTimeout(TimeUnit.SECONDS);
         if (this.idleTimeoutSeconds < 0) {
            this.idleTimeoutSeconds = 0;
         }

         if (this.idleTimeoutSeconds == 0) {
            this.idleTimeoutSeconds = 1;
         }

         scrubTimeoutSeconds = (int)config.getScrubberDelay(TimeUnit.SECONDS);
         if (scrubTimeoutSeconds < 0) {
            scrubTimeoutSeconds = 0;
         }
      } else {
         this.idleTimeoutSeconds = cd.getIdleTimeoutSecondsCache();
         scrubTimeoutSeconds = cd.getIdleTimeoutSecondsCache();
      }

      if (cd.getCacheType().equalsIgnoreCase("LRU") && !this.getBeanInfo().isStatefulTimeoutConfigured()) {
         this.cache = new LRUCache(this.getDisplayName(), cd.getMaxBeansInCache(), scrubTimeoutSeconds, this);
      } else {
         this.cache = new NRUCache(this.getDisplayName(), cd.getMaxBeansInCache(), this.getBeanInfo().isStatefulTimeoutConfigured(), this.idleTimeoutSeconds, scrubTimeoutSeconds, this);
      }

      this.cacheRTMBean.setReInitializableCache(this.cache);
      this.cache.startScrubber();
   }

   public void setupExtendedPCSupport(PersistenceUnitRegistry puReg, PersistenceContextRefBean[] xpcRefs) {
      if (xpcRefs.length != 0) {
         this.extendedPersistenceContextSyncTypeMap = new HashMap();
         PersistenceContextRefBean[] var3 = xpcRefs;
         int var4 = xpcRefs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PersistenceContextRefBean epcRef = var3[var5];
            this.extendedPersistenceContextSyncTypeMap.put(epcRef.getPersistenceUnitName(), SynchronizationType.valueOf(epcRef.getSynchronizationType().toUpperCase()));
         }

         this.extendedPersistenceContextMap = new HashMap();
         Iterator var7 = this.extendedPersistenceContextSyncTypeMap.keySet().iterator();

         while(var7.hasNext()) {
            String puName = (String)var7.next();
            this.extendedPersistenceContextMap.put(puName, puReg.getPersistenceUnit(puName));
         }

      }
   }

   public boolean allExtendedPCSerializable() {
      if (this.extendedPersistenceContextMap == null) {
         return true;
      } else {
         Iterator var1 = this.extendedPersistenceContextMap.values().iterator();

         PersistenceUnitInfo puInfo;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            puInfo = (PersistenceUnitInfo)var1.next();
         } while(((BasePersistenceUnitInfo)puInfo).isPersistenceContextSerializable());

         return false;
      }
   }

   public StatefulSessionBeanInfo getBeanInfo() {
      return (StatefulSessionBeanInfo)super.getBeanInfo();
   }

   private Object getLockClient(Object tx) {
      if (tx == null) {
         return this.getBeanInfo().usesBeanManagedTx() ? this.bmTxLockClient : Thread.currentThread();
      } else {
         return tx;
      }
   }

   private long getLockTimeoutNanos(InvocationWrapper wrap) {
      MethodDescriptor md = wrap.getMethodDescriptor();
      ConcurrencyInfo ci = md != null ? md.getConcurrencySettings() : null;
      if (null == ci) {
         return -1L;
      } else {
         return ci.getTimeout() > 0L ? TimeUnit.NANOSECONDS.convert(ci.getTimeout(), ci.getTimeoutUnit()) : ci.getTimeout();
      }
   }

   private boolean acquireLock(InvocationWrapper wrap, Object pk) throws InternalException {
      Object lockClient = this.getLockClient(wrap.getInvokeTx());

      try {
         return this.lockManager.fineLock(pk, lockClient, 0L);
      } catch (LockTimedOutException var10) {
         Loggable l;
         if (this.isReentrant(wrap.getCallerTx(), pk)) {
            l = EJBLogger.logIllegalMakeReentrantCallSFSBLoggable(this.getDisplayName());
            throw new InternalException(l.getMessageText(), var10);
         } else {
            long timeout;
            if ((timeout = this.getLockTimeoutNanos(wrap)) == 0L) {
               l = EJBLogger.logConcurrentAccessTimeoutOnSFSBMethodLoggable();
               throw new InternalException(l.getMessage(), new ConcurrentAccessException(l.getMessageText(), var10));
            } else {
               try {
                  return this.lockManager.fineLock(pk, lockClient, timeout);
               } catch (LockTimedOutException var9) {
                  Loggable l = EJBLogger.logConcurrentAccessTimeoutOnSFSBMethodLoggable();
                  throw new InternalException(l.getMessage(), new ConcurrentAccessTimeoutException(l.getMessageText()));
               }
            }
         }
      }
   }

   private boolean isReentrant(Transaction tx, Object pk) {
      Object owner = this.lockManager.getOwner(pk);
      if (null == owner) {
         return false;
      } else if (owner instanceof Thread) {
         return owner.equals(Thread.currentThread());
      } else if (owner instanceof Transaction) {
         return owner.equals(tx);
      } else {
         throw new AssertionError("Should never reach here, the lock's owner is an instance of " + owner.getClass());
      }
   }

   protected Object getBean(Object pk, Class iface) throws InternalException {
      CacheKey key = new CacheKey(pk, this);
      this.cacheRTMBean.incrementCacheAccessCount();
      Object bean = this.cache.get(key);
      if (bean == null) {
         bean = this.swapper.read(pk, iface);
         if (bean != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("Found key: " + pk + " in swap");
            }

            try {
               this.cache.put(key, bean);
               this.cacheRTMBean.incrementCachedBeansCurrentCount();
            } catch (CacheFullException var7) {
               EJBRuntimeUtils.throwInternalException("Exception in remote create", var7);
            }

            try {
               this.doEjbActivate((WLEnterpriseBean)bean);
               this.cacheRTMBean.incrementActivationCount();
            } catch (Exception var6) {
               EJBLogger.logExcepInActivate(StackTraceUtilsClient.throwable2StackTrace(var6));
               this.removeBean(key, bean);
               EJBRuntimeUtils.throwInternalException("Exception in ejbActivate:", var6);
            }
         } else {
            EJBRuntimeUtils.throwInternalException("Error calling get bean.", new NoSuchEJBException("Stateful session bean has been deleted."));
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("Found key: " + pk + " in the EJB Cache");
         }

         this.cacheRTMBean.incrementCacheHitCount();
      }

      return bean;
   }

   private boolean hasTxAttributeConfigured(MethodDescriptor md) {
      return md != null && md.getMethodInfo().getTransactionAttribute() != -1;
   }

   protected void doEjbActivate(WLEnterpriseBean bean) throws RemoteException, InternalException {
      Throwable th = null;
      MethodDescriptor md = this.getBeanInfo().getPostActivateMethodDescriptor();
      boolean isTransactional = this.hasTxAttributeConfigured(md);
      InvocationWrapper iw = InvocationWrapper.newInstance(md);
      int oldState = this.preLifecycleInvoke(bean, 524288, iw);

      try {
         if (isTransactional) {
            iw.enforceTransactionPolicy(this.getTransactionPolicy(iw, "postActivate()"));
         }

         if (this.implementsSessionBeanIntf) {
            ((SessionBean)bean).ejbActivate();
         } else {
            this.ejbComponentCreator.invokePostActivate(bean, this.getBeanInfo().getEJBName());
         }
      } catch (RuntimeException | InternalException var15) {
         th = var15;
         throw var15;
      } finally {
         this.postLifecycleInvoke(bean, oldState, iw);
         if (isTransactional) {
            try {
               this.handleLifecycleCallbackTx(iw, "postActivate()", "Stateful session", th);
            } catch (InternalException var14) {
               EJBLogger.logStatefulSessionBeanLifecycleCallbackException(this.getBeanInfo().getDisplayName(), "PostActivate", var14.getMessage());
               throw var14;
            }
         }

      }

   }

   protected void doEjbPassivate(WLEnterpriseBean bean) throws RemoteException, InternalException {
      Throwable th = null;
      MethodDescriptor md = this.getBeanInfo().getPrePassivateMethodDescriptor();
      boolean isTransactional = this.hasTxAttributeConfigured(md);
      InvocationWrapper iw = InvocationWrapper.newInstance(md);
      int oldState = this.preLifecycleInvoke(bean, 524288, iw);

      try {
         if (isTransactional) {
            iw.enforceTransactionPolicy(this.getTransactionPolicy(iw, "prePassivate()"));
         }

         if (this.implementsSessionBeanIntf) {
            ((SessionBean)bean).ejbPassivate();
         } else {
            this.ejbComponentCreator.invokePrePassivate(bean, this.getBeanInfo().getEJBName());
         }
      } catch (RuntimeException | InternalException var15) {
         th = var15;
         throw var15;
      } finally {
         this.postLifecycleInvoke(bean, oldState, iw);
         if (isTransactional) {
            try {
               this.handleLifecycleCallbackTx(iw, "prePassivate()", "Stateful session", th);
            } catch (InternalException var14) {
               EJBLogger.logStatefulSessionBeanLifecycleCallbackException(this.getBeanInfo().getDisplayName(), "PrePassivate", var14.getMessage());
               throw var14;
            }
         }

      }

   }

   private void enlistInTx(Set epcws) throws InternalException {
      Iterator var2 = epcws.iterator();

      while(true) {
         ExtendedPersistenceContextWrapper epcw;
         Object o;
         label26:
         do {
            while(var2.hasNext()) {
               epcw = (ExtendedPersistenceContextWrapper)var2.next();
               TransactionSynchronizationRegistry txRegistry = (TransactionSynchronizationRegistry)TransactionService.getTransactionManager();
               o = txRegistry.getResource(epcw.getPersistenceUnitName());
               if (o != null) {
                  continue label26;
               }

               txRegistry.putResource(epcw.getPersistenceUnitName(), epcw);
               if (epcw.getSynchronizationType() == SynchronizationType.SYNCHRONIZED) {
                  epcw.getEntityManager().joinTransaction();
               }
            }

            return;
         } while(o instanceof ExtendedPersistenceContextWrapper && ((ExtendedPersistenceContextWrapper)o).getEntityManager().equals(epcw.getEntityManager()));

         EJBRuntimeUtils.throwInternalException("Error invoking EJB:", new EJBException("Error, the EJB " + this.getDisplayName() + " has an Extended Persistence Context and was invoked  in the context of a transaction that is already  associated with a different Persistence Context"));
      }
   }

   public Object preInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[2];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         var10.resetPostBegin();
      }

      WLSessionBean var38;
      try {
         super.preInvoke();
         Object pk = wrap.getPrimaryKey();
         Transaction tx = wrap.getInvokeTx();
         WLSessionBean bean = null;
         boolean callAfterBegin = !this.acquireLock(wrap, wrap.getPrimaryKey());

         try {
            bean = (WLSessionBean)this.getBean(pk, wrap.getMethodDescriptor().getClientViewDescriptor().getViewClass());
            if (this.extendedPersistenceContextMap != null && tx != null && !this.getBeanInfo().usesBeanManagedTx()) {
               this.enlistInTx(bean.__WL_getExtendedPersistenceContexts());
            }

            if (this.getBeanInfo().usesBeanManagedTx()) {
               if (callAfterBegin) {
                  bean.__WL_setBeanManagedTransaction((Transaction)null);
                  if (this.extendedPersistenceContextMap != null) {
                     this.enlistInTx(bean.__WL_getExtendedPersistenceContexts());
                  }
               } else {
                  Transaction savedTx = bean.__WL_getBeanManagedTransaction();
                  if (savedTx != null) {
                     if (debugLogger.isDebugEnabled()) {
                        debug("** Resuming transaction on key: " + pk);
                     }

                     try {
                        TransactionService.getTransactionManager().resume(savedTx);
                     } catch (InvalidTransactionException var31) {
                        bean.__WL_setBeanManagedTransaction((Transaction)null);
                        this.cache.remove(new CacheKey(pk, this));
                        this.lockManager.unlock(pk, this.getLockClient((Object)null));
                        throw var31;
                     } catch (SystemException var32) {
                        SystemException se = var32;
                        EJBLogger.logExcepResumingTx(var32);
                        bean.__WL_setBeanManagedTransaction((Transaction)null);
                        this.removeBean(new CacheKey(pk, this), bean);

                        try {
                           if (tx instanceof weblogic.transaction.Transaction) {
                              ((weblogic.transaction.Transaction)tx).setRollbackOnly("Couldn't resume transaction " + savedTx, se);
                           } else {
                              tx.setRollbackOnly();
                           }
                        } catch (SystemException var27) {
                        }

                        this.lockManager.unlock(pk, this.getLockClient((Object)null));
                        throw new InternalException("Exception trying to resume transaction", var32);
                     }
                  }
               }
            }

            if (tx != null && callAfterBegin) {
               try {
                  this.setupTxListener(wrap);
               } catch (InternalException var30) {
                  this.removeBean(new CacheKey(pk, this), bean);
                  throw var30;
               }
            }

            if (callAfterBegin && this.implementsSessionSynchronization && tx != null) {
               AllowedMethodsHelper.pushBean(bean);
               EJBContextManager.pushEjbContext(bean.__WL_getEJBContext());
               int oldState = bean.__WL_getMethodState();
               bean.__WL_setMethodState(256);

               try {
                  ((WLSessionSynchronization)bean).__WL_afterBegin();
               } catch (Throwable var28) {
                  EJBLogger.logExcepInAfterBegin(StackTraceUtilsClient.throwable2StackTrace(var28));
                  this.removeBean(new CacheKey(pk, this), bean);
                  EJBRuntimeUtils.throwInternalException("Exception in afterBegin", var28);
               } finally {
                  AllowedMethodsHelper.popBean();
                  EJBContextManager.popEjbContext();
                  bean.__WL_setMethodState(oldState);
               }
            }
         } catch (Throwable var33) {
            if (callAfterBegin) {
               this.lockManager.unlock(pk, this.getLockClient(tx));
            }

            EJBRuntimeUtils.throwInternalException("Stateful Session Exception:", var33);
         }

         if (bean.__WL_isBusy()) {
            Loggable l = EJBLogger.logIllegalMakeReentrantCallSFSBLoggable(this.getDisplayName());
            ConcurrentAccessException cae = new ConcurrentAccessException(l.getMessageText());
            l = EJBLogger.logIllegalMakeReentrantCallSFSBFromHomeLoggable(this.getDisplayName());
            throw new InternalException(l.getMessageText(), cae);
         }

         bean.__WL_setBusy(true);
         var38 = bean;
      } catch (Throwable var34) {
         if (var10 != null) {
            var10.th = var34;
            var10.ret = null;
            InstrumentationSupport.process(var10);
         }

         throw var34;
      }

      if (var10 != null) {
         var10.ret = var38;
         InstrumentationSupport.process(var10);
      }

      return var38;
   }

   public void postInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var13;
      if ((var13 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var13.argsCapture) {
            var13.args = new Object[2];
            Object[] var10000 = var13.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         InstrumentationSupport.createDynamicJoinPoint(var13);
         InstrumentationSupport.process(var13);
         var13.resetPostBegin();
      }

      if (!$assertionsDisabled && wrap.getPrimaryKey() == null) {
         throw new AssertionError();
      } else {
         Transaction tx = wrap.getInvokeTx();
         Object pk = wrap.getPrimaryKey();
         CacheKey key = new CacheKey(pk, this);
         WLSessionBean bean = (WLSessionBean)wrap.getBean();
         bean.__WL_setBusy(false);
         String skey;
         if (this.getBeanInfo().usesBeanManagedTx()) {
            Transaction tx = TransactionService.getTransaction();
            if (tx == null) {
               if (debugLogger.isDebugEnabled()) {
                  debug("** releasing key because BM has no associated tx:" + pk);
               }

               Transaction txSaved = bean.__WL_getBeanManagedTransaction();
               bean.__WL_setBeanManagedTransaction((Transaction)null);
               if (txSaved instanceof weblogic.transaction.Transaction) {
                  weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)txSaved;
                  String skey = this.getBeanKeyString(pk);
                  this.setSkipReplicationFlagInTx(wrap, wtx, skey);
                  if (Boolean.FALSE == wtx.getLocalProperty(skey)) {
                     this.replicateAndRelease(key, bean);
                  }
               } else if (txSaved != null) {
                  this.replicateAndRelease(key, bean);
               } else if (!this.skipStateReplication(wrap)) {
                  this.replicateAndRelease(key, bean);
               }

               this.lockManager.unlock(pk, this.getLockClient((Object)null));
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debug("** keeping lock and associating tx for:" + pk);
               }

               try {
                  if (tx.getStatus() == 0) {
                     if (tx instanceof weblogic.transaction.Transaction) {
                        String skey = this.getBeanKeyString(pk);
                        this.setSkipReplicationFlagInTx(wrap, (weblogic.transaction.Transaction)tx, skey);
                     }

                     TransactionService.getTransactionManager().suspend();
                     bean.__WL_setBeanManagedTransaction(tx);
                  } else if (tx.getStatus() == 1 && tx instanceof weblogic.transaction.Transaction) {
                     weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
                     skey = this.getBeanKeyString(pk);
                     this.setSkipReplicationFlagInTx(wrap, wtx, skey);
                     wtx.setProperty("DISABLE_TX_STATUS_CHECK", "true");
                     TransactionService.getTransactionManager().suspend();
                     bean.__WL_setBeanManagedTransaction(tx);
                  } else {
                     bean.__WL_setBeanManagedTransaction((Transaction)null);
                  }
               } catch (SystemException var28) {
                  bean.__WL_setBeanManagedTransaction((Transaction)null);
               }
            }
         } else if (tx == null) {
            if (debugLogger.isDebugEnabled()) {
               debug("** releasing non BM tx: " + pk);
            }

            if (!this.skipStateReplication(wrap)) {
               this.replicateAndRelease(key, bean);
            }

            this.lockManager.unlock(pk, this.getLockClient((Object)null));
         } else {
            synchronized(bean) {
               try {
                  if (bean.__WL_needsRemove() && this.implementsSessionSynchronization) {
                     if (debugLogger.isDebugEnabled()) {
                        debug("*** postInvoke called after afterCompletion****");
                     }

                     WLEnterpriseBean ssbean = (WLEnterpriseBean)this.cache.get(key);
                     if (bean.__WL_needsSessionSynchronization()) {
                        this.pushEnvironment();
                        AllowedMethodsHelper.pushBean(ssbean);
                        EJBContextManager.pushEjbContext(ssbean.__WL_getEJBContext());
                        int oldState = ssbean.__WL_getMethodState();
                        ssbean.__WL_setMethodState(1024);

                        try {
                           ((WLSessionSynchronization)ssbean).__WL_afterCompletion(tx.getStatus() == 3);
                        } catch (Throwable var24) {
                           EJBLogger.logExcepInAfterCompletion(StackTraceUtilsClient.throwable2StackTrace(var24));
                           this.removeBean(key, ssbean);
                        } finally {
                           EJBContextManager.popEjbContext();
                           AllowedMethodsHelper.popBean();
                           ssbean.__WL_setMethodState(oldState);
                           this.popEnvironment();
                        }
                     }
                  }

                  if (tx instanceof weblogic.transaction.Transaction) {
                     skey = this.getBeanKeyString(pk);
                     this.setSkipReplicationFlagInTx(wrap, (weblogic.transaction.Transaction)tx, skey);
                  }
               } finally {
                  if (debugLogger.isDebugEnabled()) {
                     debug("****releasing the bean inside postInvoke****");
                  }

                  if (bean.__WL_needsRemove()) {
                     bean.__WL_setNeedsRemove(false);
                     this.replicateAndRelease(key, bean);
                     this.lockManager.unlock(pk, this.getLockClient(tx));
                  }

               }
            }
         }

      }
   }

   private boolean skipStateReplication(InvocationWrapper wrapper) {
      return wrapper.getMethodDescriptor().getMethodInfo().getSkipStateReplication();
   }

   private String getBeanKeyString(Object pk) {
      return pk.toString() + "_" + this.getBeanInfo().getFullyQualifiedName();
   }

   private void setSkipReplicationFlagInTx(InvocationWrapper wrap, weblogic.transaction.Transaction wtx, String skey) {
      Boolean skipReplication = (Boolean)wtx.getLocalProperty(skey);
      if (Boolean.FALSE != skipReplication) {
         if (this.skipStateReplication(wrap)) {
            if (null == skipReplication) {
               wtx.setLocalProperty(skey, Boolean.TRUE);
            }
         } else {
            wtx.setLocalProperty(skey, Boolean.FALSE);
         }
      }

   }

   public void destroyInstance(InvocationWrapper wrap, Throwable var2) {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[3];
            Object[] var10000 = var6.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = var2;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      try {
         Object pk = wrap.getPrimaryKey();
         CacheKey key = new CacheKey(pk, this);
         if (!$assertionsDisabled && pk == null) {
            throw new AssertionError();
         }

         WLSessionBean bean = (WLSessionBean)wrap.getBean();
         bean.__WL_setBusy(false);
         this.removeBean(key, bean);
         if (wrap.getInvokeTx() == null || bean.__WL_needsRemove()) {
            this.lockManager.unlock(pk, this.getLockClient((Object)null));
         }
      } catch (Throwable var8) {
         if (var6 != null) {
            var6.th = var8;
            InstrumentationSupport.postProcess(var6);
         }

         throw var8;
      }

      if (var6 != null) {
         InstrumentationSupport.postProcess(var6);
      }

   }

   protected void removeBean(CacheKey key, Object bean) {
      this.cache.remove(key);
      if (bean != null) {
         this.removeXPC(bean);
         this.ejbComponentCreator.destroyBean(bean);
      }

   }

   protected void removeXPC(Object bean) {
      Set epcws = ((WLSessionBean)bean).__WL_getExtendedPersistenceContexts();
      if (epcws != null) {
         Iterator var3 = epcws.iterator();

         while(var3.hasNext()) {
            ExtendedPersistenceContextWrapper epcw = (ExtendedPersistenceContextWrapper)var3.next();
            epcw.decrementReferenceCount();
         }

         epcws.clear();
      }

   }

   private void releaseProxy(Object bean) {
      if (ENABLE_PROXY_POOL && this.getBeanInfo().isEJB30() && bean instanceof __ProxyControl) {
         ((__ProxyControl)bean).__getTarget().removeTarget();
         this.proxyPool.add(bean);
      }

   }

   public void beforeCompletion(InvocationWrapper wrap) throws InternalException {
   }

   public void beforeCompletion(Collection primaryKeys, Transaction tx) throws InternalException {
      Iterator it = primaryKeys.iterator();

      while(it.hasNext()) {
         CacheKey key = new CacheKey(it.next(), this);
         if (this.implementsSessionSynchronization) {
            WLSessionBean bean = (WLSessionBean)this.cache.get(key);
            if (bean != null && bean.__WL_needsSessionSynchronization()) {
               this.pushEnvironment();
               AllowedMethodsHelper.pushBean(bean);
               EJBContextManager.pushEjbContext(bean.__WL_getEJBContext());
               int oldState = bean.__WL_getMethodState();
               bean.__WL_setMethodState(512);

               try {
                  ((WLSessionSynchronization)bean).__WL_beforeCompletion();
               } catch (Throwable var14) {
                  Throwable t = var14;
                  EJBLogger.logExcepInBeforeCompletion(StackTraceUtilsClient.throwable2StackTrace(var14));
                  this.removeBean(key, bean);

                  try {
                     if (tx instanceof weblogic.transaction.Transaction) {
                        ((weblogic.transaction.Transaction)tx).setRollbackOnly("beforeCompletion() threw an exception", t);
                     } else {
                        tx.setRollbackOnly();
                     }
                  } catch (SystemException var13) {
                     EJBLogger.logExcepDuringSetRollbackOnly(var13);
                  }

                  EJBRuntimeUtils.throwInternalException("Exception in beforeCompletion:", var14);
               } finally {
                  bean.__WL_setMethodState(oldState);
                  EJBContextManager.popEjbContext();
                  AllowedMethodsHelper.popBean();
                  this.popEnvironment();
               }
            }
         }
      }

   }

   public void afterCompletion(InvocationWrapper wrap) {
   }

   public void afterCompletion(Collection primaryKeys, Transaction tx, int result, Object ignore) {
      Iterator it = primaryKeys.iterator();

      while(it.hasNext()) {
         Object pk = it.next();
         CacheKey key = new CacheKey(pk, this);
         boolean shouldUnlock = true;
         WLSessionBean wlbean = null;
         boolean var21 = false;

         try {
            var21 = true;
            wlbean = (WLSessionBean)this.cache.get(key);
            if (wlbean != null) {
               synchronized(wlbean) {
                  if (!wlbean.__WL_isBusy()) {
                     if (this.implementsSessionSynchronization && wlbean.__WL_needsSessionSynchronization()) {
                        this.pushEnvironment();
                        AllowedMethodsHelper.pushBean(wlbean);
                        EJBContextManager.pushEjbContext(wlbean.__WL_getEJBContext());
                        int oldState = wlbean.__WL_getMethodState();
                        wlbean.__WL_setMethodState(1024);

                        try {
                           ((WLSessionSynchronization)wlbean).__WL_afterCompletion(result == 3);
                        } catch (Throwable var26) {
                           EJBLogger.logExcepInAfterCompletion(StackTraceUtilsClient.throwable2StackTrace(var26));
                           this.removeBean(key, wlbean);
                        } finally {
                           AllowedMethodsHelper.popBean();
                           EJBContextManager.popEjbContext();
                           wlbean.__WL_setMethodState(oldState);
                           this.popEnvironment();
                        }
                     }
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debug("******afterCompletion called before postInvoke ******");
                     }

                     shouldUnlock = false;
                     wlbean.__WL_setNeedsRemove(true);
                  }

                  var21 = false;
               }
            } else {
               var21 = false;
            }
         } finally {
            if (var21) {
               if (shouldUnlock) {
                  if (tx instanceof weblogic.transaction.Transaction) {
                     weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
                     if (Boolean.FALSE == wtx.getLocalProperty(this.getBeanKeyString(pk))) {
                        this.replicateAndRelease(key, wlbean);
                     }
                  } else {
                     this.replicateAndRelease(key, wlbean);
                  }

                  this.lockManager.unlock(pk, this.getLockClient(tx));
               }

            }
         }

         if (shouldUnlock) {
            if (tx instanceof weblogic.transaction.Transaction) {
               weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
               if (Boolean.FALSE == wtx.getLocalProperty(this.getBeanKeyString(pk))) {
                  this.replicateAndRelease(key, wlbean);
               }
            } else {
               this.replicateAndRelease(key, wlbean);
            }

            this.lockManager.unlock(pk, this.getLockClient(tx));
         }
      }

   }

   private SessionEJBContextImpl allocateContext(Object b, EJBObject eo, EJBLocalObject elo) {
      return new SessionEJBContextImpl(b, this, (StatefulEJBHome)this.remoteHome, (StatefulEJBLocalHome)this.localHome, eo, elo);
   }

   public EJBContext allocateContext(Object b, Object key) {
      EJBObject eo = this.getBeanInfo().hasDeclaredRemoteHome() ? this.remoteHome.allocateEO(key) : null;
      EJBLocalObject elo = this.getBeanInfo().hasDeclaredLocalHome() ? this.localHome.allocateELO(key) : null;
      return this.allocateContext(b, eo, elo);
   }

   protected Object createNewBeanInstance() throws IllegalAccessException, InstantiationException {
      if (ENABLE_PROXY_POOL && this.getBeanInfo().isEJB30()) {
         __ProxyControl proxy = (__ProxyControl)this.proxyPool.remove();
         if (proxy != null) {
            Thread currentThread = Thread.currentThread();
            ClassLoader clSave = currentThread.getContextClassLoader();

            __ProxyControl var5;
            try {
               currentThread.setContextClassLoader(this.getBeanInfo().getClassLoader());
               Object bean = this.ejbComponentCreator.getBean(this.getBeanInfo().getEJBName(), this.beanClass, false);
               this.perhapsInvokeInjectors(bean);
               proxy.__getTarget().resetTarget(bean);
               var5 = proxy;
            } finally {
               currentThread.setContextClassLoader(clSave);
            }

            return var5;
         }
      }

      return super.createNewBeanInstance();
   }

   protected void create(EJBObject eo, EJBLocalObject elo, Object pk, InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      wrap.pushEnvironment(this.getEnvironmentContext());
      Set pushedPersistenceContextNames = null;
      Set persistenceContextsOwnedByThisBean = null;
      if (this.extendedPersistenceContextMap != null) {
         pushedPersistenceContextNames = new HashSet();
         persistenceContextsOwnedByThisBean = new HashSet();

         ExtendedPersistenceContextWrapper epcWrap;
         for(Iterator var10 = this.extendedPersistenceContextMap.keySet().iterator(); var10.hasNext(); persistenceContextsOwnedByThisBean.add(epcWrap)) {
            String puName = (String)var10.next();
            BasePersistenceUnitInfo puInfo = (BasePersistenceUnitInfo)this.extendedPersistenceContextMap.get(puName);
            String qualifiedPUName = puInfo.getPersistenceUnitId();
            epcWrap = ExtendedPersistenceContextManager.getExtendedPersistenceContext(qualifiedPUName);
            SynchronizationType syncType;
            if (epcWrap != null) {
               syncType = (SynchronizationType)this.extendedPersistenceContextSyncTypeMap.get(puName);
               if (epcWrap.getSynchronizationType() != syncType) {
                  EJBRuntimeUtils.throwInternalException("Error creating EJB:", new EJBException("Error, the EJB " + this.getDisplayName() + " defining an " + syncType.toString() + " Extended Persistence Context for persistence unit " + puName + " is inheriting an Extended Persistence Context of type " + epcWrap.getSynchronizationType().toString()));
               }

               epcWrap.incrementReferenceCount();
            } else {
               syncType = (SynchronizationType)this.extendedPersistenceContextSyncTypeMap.get(puName);
               epcWrap = new ExtendedPersistenceContextWrapper(puInfo, syncType);
               ExtendedPersistenceContextManager.setExtendedPersistenceContext(qualifiedPUName, epcWrap);
               pushedPersistenceContextNames.add(qualifiedPUName);
            }
         }
      }

      WLSessionBean bean = null;
      boolean var59 = false;

      try {
         var59 = true;
         bean = (WLSessionBean)this.createBean(pk, eo, elo);
         var59 = false;
      } finally {
         if (var59) {
            if (this.extendedPersistenceContextMap != null && bean != null) {
               bean.__WL_getExtendedPersistenceContexts().addAll(persistenceContextsOwnedByThisBean);
               Iterator var17 = pushedPersistenceContextNames.iterator();

               while(var17.hasNext()) {
                  String name = (String)var17.next();
                  ExtendedPersistenceContextManager.removeExtendedPersistenceContext(name);
               }
            }

            wrap.popEnvironment();
         }
      }

      if (this.extendedPersistenceContextMap != null && bean != null) {
         bean.__WL_getExtendedPersistenceContexts().addAll(persistenceContextsOwnedByThisBean);
         Iterator var70 = pushedPersistenceContextNames.iterator();

         while(var70.hasNext()) {
            String name = (String)var70.next();
            ExtendedPersistenceContextManager.removeExtendedPersistenceContext(name);
         }
      }

      wrap.popEnvironment();
      if (!$assertionsDisabled && wrap.getInvokeTx() != null) {
         throw new AssertionError();
      } else {
         this.acquireLock(wrap, pk);
         CacheKey key = new CacheKey(pk, this);

         try {
            try {
               MethodDescriptor md = this.getBeanInfo().getPostConstructMethodDescriptor();
               boolean isTransactional = this.hasTxAttributeConfigured(md);
               InvocationWrapper iw = InvocationWrapper.newInstance(md);
               Throwable th = null;
               if (this.getBeanInfo().isEJB30() && createMethod == null) {
                  Thread currentThread = Thread.currentThread();
                  ClassLoader clSave = currentThread.getContextClassLoader();
                  this.pushEnvironment();
                  currentThread.setContextClassLoader(this.getBeanInfo().getClassLoader());
                  int oldState = this.preLifecycleInvoke(bean, 524288, iw);

                  try {
                     if (isTransactional) {
                        iw.enforceTransactionPolicy(this.getTransactionPolicy(iw, "postConstruct()"));
                     }

                     this.ejbComponentCreator.invokePostConstruct(bean, this.getBeanInfo().getEJBName());
                  } catch (RuntimeException | InternalException var62) {
                     th = var62;
                     throw var62;
                  } finally {
                     this.postLifecycleInvoke(bean, oldState, iw);
                     currentThread.setContextClassLoader(clSave);
                     this.popEnvironment();
                     if (isTransactional) {
                        this.handleLifecycleCallbackTx(iw, "postConstruct()", "Stateful session", th);
                     }

                  }
               } else if (createMethod != null) {
                  int oldState = this.preLifecycleInvoke(bean, 4);

                  try {
                     if (isTransactional) {
                        iw.enforceTransactionPolicy(this.getTransactionPolicy(iw, "postConstruct()"));
                     }

                     createMethod.invoke(bean, args);
                  } catch (RuntimeException | InternalException var61) {
                     th = var61;
                     throw var61;
                  } finally {
                     this.postLifecycleInvoke(bean, oldState);
                     if (isTransactional) {
                        this.handleLifecycleCallbackTx(iw, "postConstruct()", "Stateful session", th);
                     }

                  }
               }
            } catch (IllegalAccessException var66) {
               throw new AssertionError(var66);
            } catch (InvocationTargetException var67) {
               Throwable t = var67.getTargetException();
               if (debugLogger.isDebugEnabled()) {
                  debug("Error during create: ", t);
               }

               this.handleMethodException(createMethod, (Class[])null, t);
            }

            try {
               this.cache.put(key, bean);
               this.cacheRTMBean.incrementCachedBeansCurrentCount();
            } catch (CacheFullException var60) {
               EJBRuntimeUtils.throwInternalException("Exception in remote create", var60);
            }

            this.replicateAndRelease(key, bean);
         } finally {
            this.lockManager.unlock(pk, this.getLockClient((Object)null));
         }

      }
   }

   private Object createBean(Object pk, EJBObject eo, EJBLocalObject elo) throws InternalException {
      SessionEJBContextImpl ctx = this.allocateContext((Object)null, eo, elo);
      if (this.getBeanInfo().isEJB30()) {
         ctx.setPrimaryKey(pk);
      }

      Object bean;
      try {
         EJBContextManager.pushEjbContext(ctx);
         AllowedMethodsHelper.pushMethodInvocationState(1);
         bean = this.allocateBean();
      } finally {
         EJBContextManager.popEjbContext();
         AllowedMethodsHelper.popMethodInvocationState();
      }

      ctx.setBean(bean);

      try {
         this.perhapsCallSetContext(bean, ctx);
      } catch (Exception var9) {
         throw new InternalException("Error during setSessionContext: ", var9);
      }

      ((WLSessionBean)bean).__WL_setEJBContext(ctx);
      return bean;
   }

   public Object createBean() throws InternalException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(1);
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      Object var10000;
      try {
         Object pk = this.keyGenerator.nextKey();
         EJBObject eo = this.getBeanInfo().hasDeclaredRemoteHome() ? this.remoteHome.allocateEO(pk) : null;
         EJBLocalObject elo = this.getBeanInfo().hasDeclaredLocalHome() ? this.localHome.allocateELO(pk) : null;
         this.create(eo, elo, pk, InvocationWrapper.newInstance(), (Method)null, (Method)null, (Object[])null);
         var10000 = pk;
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            var4.ret = null;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         var4.ret = var10000;
         InstrumentationSupport.postProcess(var4);
      }

      return var10000;
   }

   public Remote remoteCreateForBI(Object pk, Class implClass, Activator activator, Class iface) throws InternalException {
      if (activator == null) {
         throw new AssertionError("Activator instance is null");
      } else {
         if (pk == null) {
            pk = this.createBean();
         }

         return ((StatefulEJBHomeImpl)this.remoteHome).allocateBI(pk, implClass, iface, activator);
      }
   }

   public EJBObject remoteCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      Object pk = this.keyGenerator.nextKey();
      EJBObject eo = null;
      EJBLocalObject elo = null;
      if (this.remoteHome != null) {
         eo = this.remoteHome.allocateEO(pk);
      }

      if (this.localHome != null && this.getBeanInfo().hasDeclaredLocalHome()) {
         elo = this.localHome.allocateELO(pk);
      }

      this.create(eo, elo, pk, wrap, createMethod, postCreateMethod, args);
      return eo;
   }

   public EJBLocalObject localCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      Object pk = this.keyGenerator.nextKey();
      EJBObject eo = null;
      EJBLocalObject elo = null;
      if (this.remoteHome != null && this.getBeanInfo().hasDeclaredRemoteHome()) {
         eo = this.remoteHome.allocateEO(pk);
      }

      if (this.localHome != null) {
         elo = this.localHome.allocateELO(pk);
      }

      this.create(eo, elo, pk, wrap, createMethod, postCreateMethod, args);
      return elo;
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[2];
            Object[] var10000 = var11.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      try {
         if (!$assertionsDisabled && wrap.getMethodDescriptor().getTransactionPolicy().getTxAttribute() != TransactionAttributeType.SUPPORTS && wrap.getInvokeTx() != null) {
            throw new AssertionError();
         }

         Object pk = wrap.getPrimaryKey();
         CacheKey key = new CacheKey(pk, this);
         if (!$assertionsDisabled && pk == null) {
            throw new AssertionError();
         }

         if (!this.getBeanInfo().isAllowRemoveDuringTx()) {
            Object lockOwner = this.lockManager.getOwner(pk);
            if (lockOwner != null && lockOwner instanceof Transaction) {
               throw new InternalException("", new RemoveException("Illegal attempt to remove a stateful session bean while it is participating in a transaction"));
            }
         }

         boolean isRemoveCalledWithinTx = false;
         Transaction tx = wrap.getInvokeTx();
         if (tx != null && !this.getBeanInfo().usesBeanManagedTx()) {
            isRemoveCalledWithinTx = true;
         }

         Object bean = null;
         boolean releaseLock = false;
         boolean errorOccurred = false;

         try {
            bean = this.preInvoke(wrap);
            releaseLock = true;
            if (!$assertionsDisabled && bean == null) {
               throw new AssertionError();
            }

            this.cache.remove(key);
            this.doEjbRemove(bean);
         } catch (Throwable var18) {
            errorOccurred = true;
            this.handleMethodException(wrap.getMethodDescriptor().getMethod(), (Class[])null, var18);
            throw new AssertionError("Should not reach");
         } finally {
            if (bean != null) {
               ((WLSessionBean)bean).__WL_setBusy(false);
               if (!errorOccurred) {
                  this.releaseProxy(bean);
               }
            }

            if (releaseLock && !isRemoveCalledWithinTx) {
               this.lockManager.unlock(pk, this.getLockClient((Object)null));
            }

         }
      } catch (Throwable var20) {
         if (var11 != null) {
            var11.th = var20;
            InstrumentationSupport.postProcess(var11);
         }

         throw var20;
      }

      if (var11 != null) {
         InstrumentationSupport.postProcess(var11);
      }

   }

   public void removeForRemoveAnnotation(InvocationWrapper wrap) throws InternalException {
      boolean shouldLock = false;

      try {
         shouldLock = wrap.getInvokeTx() == null || wrap.getInvokeTx().getStatus() != 0 && wrap.getInvokeTx().getStatus() != 1;
      } catch (SystemException var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception:", var4);
      }

      this.remove(wrap.getPrimaryKey(), wrap.getMethodDescriptor().getClientViewDescriptor().getViewClass(), shouldLock, this.getLockTimeoutNanos(wrap));
   }

   private void remove(Object pk, Class iface, boolean shouldLock, long lockTimeoutNanos) throws InternalException {
      CacheKey key = new CacheKey(pk, this);
      if (!$assertionsDisabled && pk == null) {
         throw new AssertionError();
      } else {
         Object bean = null;
         boolean var18 = false;

         StatefulEJBHome eh;
         EJBObject ejbObject;
         label230: {
            try {
               var18 = true;
               if (shouldLock) {
                  this.lockManager.fineLock(pk, this.getLockClient((Object)null), lockTimeoutNanos);
               }

               bean = this.getBean(pk, iface);
               if (!$assertionsDisabled && bean == null) {
                  throw new AssertionError();
               }

               this.cache.remove(key);
               this.doEjbRemove(bean);
               this.releaseProxy(bean);
               var18 = false;
               break label230;
            } catch (Throwable var22) {
               EJBLogger.logStackTrace(var22);
               EJBRuntimeUtils.throwInternalException("EJB Exception:", var22);
               var18 = false;
            } finally {
               if (var18) {
                  try {
                     if (shouldLock) {
                        this.lockManager.unlock(pk, this.getLockClient((Object)null));
                     }

                     if (this.remoteHome != null) {
                        StatefulEJBHome eh = (StatefulEJBHome)this.remoteHome;
                        if (eh.getIsNoObjectActivation() || eh.getIsInMemoryReplication()) {
                           EJBObject ejbObject = eh.getEJBObject(pk);
                           if (ejbObject != null) {
                              eh.releaseEO(pk);
                           }

                           if (eh instanceof StatefulEJBHomeImpl) {
                              ((StatefulEJBHomeImpl)this.remoteHome).releaseBOs(pk);
                           }
                        }
                     }
                  } catch (Throwable var19) {
                     EJBRuntimeUtils.throwInternalException("EJB Exception:", var19);
                  }

               }
            }

            try {
               if (shouldLock) {
                  this.lockManager.unlock(pk, this.getLockClient((Object)null));
               }

               if (this.remoteHome != null) {
                  eh = (StatefulEJBHome)this.remoteHome;
                  if (eh.getIsNoObjectActivation() || eh.getIsInMemoryReplication()) {
                     ejbObject = eh.getEJBObject(pk);
                     if (ejbObject != null) {
                        eh.releaseEO(pk);
                     }

                     if (eh instanceof StatefulEJBHomeImpl) {
                        ((StatefulEJBHomeImpl)this.remoteHome).releaseBOs(pk);
                     }

                     return;
                  }
               }
            } catch (Throwable var20) {
               EJBRuntimeUtils.throwInternalException("EJB Exception:", var20);
            }

            return;
         }

         try {
            if (shouldLock) {
               this.lockManager.unlock(pk, this.getLockClient((Object)null));
            }

            if (this.remoteHome != null) {
               eh = (StatefulEJBHome)this.remoteHome;
               if (eh.getIsNoObjectActivation() || eh.getIsInMemoryReplication()) {
                  ejbObject = eh.getEJBObject(pk);
                  if (ejbObject != null) {
                     eh.releaseEO(pk);
                  }

                  if (eh instanceof StatefulEJBHomeImpl) {
                     ((StatefulEJBHomeImpl)this.remoteHome).releaseBOs(pk);
                  }
               }
            }
         } catch (Throwable var21) {
            EJBRuntimeUtils.throwInternalException("EJB Exception:", var21);
         }

      }
   }

   public void remove(Object pk) throws InternalException {
      this.remove(pk, (Class)null, true, 0L);
   }

   public boolean isRemoved(Object pk) {
      try {
         return this.getBean(pk, (Class)null) == null;
      } catch (Exception var3) {
         return true;
      }
   }

   public int getBeanSize() {
      return 1;
   }

   public void removedFromCache(CacheKey key, Object bean) {
      this.cacheRTMBean.decrementCachedBeansCurrentCount();
   }

   public void removedOnError(CacheKey key, Object bean) {
      throw new AssertionError("removedOnError in StatefulSessionManager");
   }

   public void swapIn(CacheKey key, Object bean) {
      if (debugLogger.isDebugEnabled()) {
         debug("swapIn key: " + key);
      }

      if (!(this.swapper instanceof ReplicatedMemorySwap)) {
         this.swapper.remove(key.getPrimaryKey());
      }

      try {
         this.doEjbActivate((WLEnterpriseBean)bean);
         this.cacheRTMBean.incrementActivationCount();
      } catch (Exception var4) {
         this.removeBean(key, bean);
         this.cacheRTMBean.decrementCachedBeansCurrentCount();
         EJBLogger.logExceptionDuringEJBActivate(var4);
      }

   }

   public void swapOut(CacheKey key, Object bean, long timeLastTouched) {
      if (debugLogger.isDebugEnabled()) {
         debug("swapOut key: " + key);
      }

      Object pk = key.getPrimaryKey();
      boolean fileSet = SecurityHelper.pushSpecificRunAsMaybe(KERNEL_ID, this.fileSector, this.fileDesc);

      try {
         this.cacheRTMBean.incrementPassivationCount();
         this.doEjbPassivate((WLEnterpriseBean)bean);
         this.swapper.write(pk, bean, timeLastTouched);
      } catch (Exception var11) {
         EJBLogger.logErrorDuringPassivation(StackTraceUtilsClient.throwable2StackTrace(var11));
      } finally {
         if (fileSet) {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

      }

   }

   public boolean needsRemoval(Object bean) {
      return false;
   }

   private String getServerRelativePath() {
      return DomainDir.getTempDirForServer(ManagementService.getRuntimeAccess(KERNEL_ID).getServerName());
   }

   private String getSwapDirectoryName() {
      return this.getServerRelativePath() + File.separatorChar + this.getBeanInfo().getSwapDirectoryName() + File.separatorChar + StringUtils.mangle(this.getBeanInfo().getFullyQualifiedName());
   }

   public void undeploy() {
      super.undeploy();
      if (this.cache != null) {
         this.cache.clear();
         this.cache.stopScrubber();
      }

      if (this.swapper != null) {
         this.swapper.destroy();
      }

   }

   public synchronized void beanImplClassChangeNotification() {
      this.beanClass = this.getBeanInfo().getGeneratedBeanClass();
      this.implementsSessionBeanIntf = SessionBean.class.isAssignableFrom(this.beanClass);
      this.swapper.updateClassLoader(this.getBeanInfo().getClassLoader());
   }

   public void updateMaxBeansInCache(int max) {
      this.cache.updateMaxBeansInCache(max);
   }

   public void updateIdleTimeoutSecondsCache(int newIdleTimeoutSeconds) {
      this.swapper.updateIdleTimeoutMS((long)newIdleTimeoutSeconds * 1000L);
      this.cache.updateIdleTimeoutSeconds(newIdleTimeoutSeconds);
   }

   public void releaseBean(InvocationWrapper wrap) {
   }

   public boolean isInMemoryReplication() {
      return this.getBeanInfo().isReplicated();
   }

   private void replicateAndRelease(CacheKey key, WLEnterpriseBean bean) {
      if (bean != null) {
         this.replicate(key, bean);
      }

      this.cache.release(key);
   }

   protected void replicate(CacheKey key, WLEnterpriseBean bean) {
   }

   public int getIdleTimeoutSeconds() {
      return this.idleTimeoutSeconds;
   }

   public void reInitializePool() {
   }

   public Object assembleEJB3Proxy(Object bean) {
      if (this.getBeanInfo().isEJB30()) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         Object var4;
         try {
            currentThread.setContextClassLoader(this.getBeanInfo().getClassLoader());
            var4 = this.ejbComponentCreator.assembleEJB3Proxy(bean, this.getBeanInfo().getEJBName());
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

         return var4;
      } else {
         return bean;
      }
   }

   public void doEjbRemove(Object o) throws RemoteException, InternalException {
      WLEnterpriseBean bean = (WLEnterpriseBean)o;
      Throwable th = null;
      MethodDescriptor md = this.getBeanInfo().getPreDestroyMethodDescriptor();
      boolean isTransactional = this.hasTxAttributeConfigured(md);
      InvocationWrapper iw = InvocationWrapper.newInstance(md);
      int oldState = this.preLifecycleInvoke(bean, 524288, iw);

      try {
         if (isTransactional) {
            iw.enforceTransactionPolicy(this.getTransactionPolicy(iw, "preDestroy()"));
         }

         if (this.implementsSessionBeanIntf) {
            ((SessionBean)bean).ejbRemove();
         } else {
            this.ejbComponentCreator.invokePreDestroy(bean, this.getBeanInfo().getEJBName());
         }
      } catch (RuntimeException | InternalException var16) {
         th = var16;
         throw var16;
      } finally {
         this.postLifecycleInvoke(bean, oldState, iw);
         this.removeXPC(bean);
         if (isTransactional) {
            try {
               this.handleLifecycleCallbackTx(iw, "preDestroy()", "Stateful session", th);
            } catch (InternalException var15) {
               EJBLogger.logStatefulSessionBeanLifecycleCallbackException(this.getBeanInfo().getDisplayName(), "PreDestroy", var15.getMessage());
               throw var15;
            }
         }

      }

   }

   public void removeRegisteredROIDs(Object pk) {
   }

   private static void debug(String s) {
      debugLogger.debug("[StatefulSessionManager] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[StatefulSessionManager] " + s, th);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Remove_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Remove_Around_High");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Create_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulSessionManager.java", "weblogic.ejb.container.manager.StatefulSessionManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljava/lang/Object;", 497, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulSessionManager.java", "weblogic.ejb.container.manager.StatefulSessionManager", "postInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)V", 609, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulSessionManager.java", "weblogic.ejb.container.manager.StatefulSessionManager", "destroyInstance", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/Throwable;)V", 792, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Remove_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Remove_Around_High};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulSessionManager.java", "weblogic.ejb.container.manager.StatefulSessionManager", "createBean", "()Ljava/lang/Object;", 1157, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulSessionManager.java", "weblogic.ejb.container.manager.StatefulSessionManager", "remove", "(Lweblogic/ejb/container/internal/InvocationWrapper;)V", 1211, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Remove_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Remove_Around_High};
      $assertionsDisabled = !StatefulSessionManager.class.desiredAssertionStatus();
      ENABLE_PROXY_POOL = Boolean.getBoolean("weblogic.ejb30.enableproxypool");
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
