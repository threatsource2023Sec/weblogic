package weblogic.ejb.container.manager;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.ConcurrentAccessTimeoutException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.IllegalLoopbackException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.transaction.Transaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.deployer.SingletonDependencyResolver;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.SingletonLockStatisticsProvider;
import weblogic.ejb.container.interfaces.SingletonSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.internal.SessionEJBContextImpl;
import weblogic.ejb.container.internal.SingletonEJBHomeImpl;
import weblogic.ejb.container.internal.SingletonEJBLocalHomeImpl;
import weblogic.ejb.container.internal.TxManagerImpl;
import weblogic.ejb.container.monitoring.SingletonEJBRuntimeMBeanImpl;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.SingletonEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;

public final class SingletonSessionManager extends BaseEJBManager {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private SingletonSessionBeanInfo sbi;
   private SingletonDependencyResolver.SingletonDependencyInfo dependencyInfo;
   private LockManager lockManager;
   private SingletonLifecycleManager lifecycleManager;
   private AuthenticatedSubject filePtr = null;
   private AuthenticatedSubject fileDesc = null;

   public SingletonSessionManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo info, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("In setup for :" + this);
      }

      super.setup(remoteHome, localHome, info, environmentContext, sh);
      this.sbi = (SingletonSessionBeanInfo)info;
      this.beanClass = this.sbi.getGeneratedBeanClass();
      if (this.sbi.getSingletonDependencyResolver() != null) {
         this.dependencyInfo = this.sbi.getSingletonDependencyResolver().get();
      }

      try {
         if (this.sbi.getCreateAsPrincipalName() != null || this.sbi.getRunAsPrincipalName() != null) {
            if (this.sbi.getCreateAsPrincipalName() != null) {
               this.filePtr = sh.getSubjectForPrincipal(this.sbi.getCreateAsPrincipalName());
            }

            if (this.sbi.getRunAsPrincipalName() != null) {
               this.fileDesc = sh.getSubjectForPrincipal(this.sbi.getRunAsPrincipalName());
            }
         }

         if (this.sbi.usesContainerManagedConcurrency()) {
            this.lockManager = new CMCLockManager(this.sbi);
         }

         SingletonEJBRuntimeMBean rtMBean = new SingletonEJBRuntimeMBeanImpl(info, this.getEJBComponentRuntime(), this.getTimerManager(), this.lockManager);
         this.setEJBRuntimeMBean(rtMBean);
         this.addEJBRuntimeMBean(rtMBean);
         this.lifecycleManager = new SingletonLifecycleManager();
         this.perhapsSetupTimerManager(rtMBean.getTimerRuntime());
         this.txManager = new TxManagerImpl(this);
      } catch (ManagementException var8) {
         Loggable l = EJBLogger.logFailedToCreateRuntimeMBeanLoggable();
         throw new WLDeploymentException(l.getMessageText(), var8);
      } catch (PrincipalNotFoundException var9) {
         throw new WLDeploymentException(var9.getMessage(), var9);
      }
   }

   public Object preInvoke(InvocationWrapper wrap) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In preInvoke on " + this);
      }

      super.preInvoke();
      if (wrap.getInvokeTx() != null) {
         this.setupTxListener(wrap);
      }

      return this.getBeanFor(wrap);
   }

   public void postInvoke(InvocationWrapper wrap) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In postInvoke on " + this);
      }

      this.ensureBMTCommitted(wrap.getMethodDescriptor().getMethodInfo().getSignature(), "Singleton session");
      this.releaseBean(wrap);
   }

   public void destroyInstance(InvocationWrapper wrap, Throwable ee) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In destroyInstance for manager: " + this);
      }

      this.releaseBean(wrap);
   }

   public void beforeCompletion(InvocationWrapper wrap) {
      if (debugLogger.isDebugEnabled()) {
         debug("In beforeCompletion(InvocationWrapper) for manager: " + this);
      }

   }

   public void beforeCompletion(Collection pks, Transaction tx) {
      if (debugLogger.isDebugEnabled()) {
         debug("In beforeCompletion(Collection, Transaction) for manager: " + this);
      }

   }

   public void afterCompletion(InvocationWrapper wrap) {
      if (debugLogger.isDebugEnabled()) {
         debug("In afterCompletion(InvocationWrapper) for manager: " + this);
      }

   }

   public void afterCompletion(Collection pks, Transaction tx, int result, Object o) {
      if (debugLogger.isDebugEnabled()) {
         debug("In afterCompletion(Collection, Transaction ..) for manager: " + this);
      }

   }

   public EJBObject remoteCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) {
      throw new UnsupportedOperationException("Not expected to be invoked");
   }

   public EJBLocalObject localCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) {
      throw new UnsupportedOperationException("Not expected to be invoked");
   }

   public void remove(InvocationWrapper wrap) {
      throw new UnsupportedOperationException("Not expected to be invoked");
   }

   public void undeploy() {
      super.undeploy();
      if (this.lifecycleManager != null) {
         this.lifecycleManager.perhapsDestroy();
      }

   }

   public void beanImplClassChangeNotification() {
      throw new UnsupportedOperationException();
   }

   public void releaseBean(InvocationWrapper wrap) {
      if (this.lockManager != null) {
         this.lockManager.release(wrap);
      }

      this.lifecycleManager.relinquishBean();
   }

   public void reInitializePool() {
   }

   public void perhapsInit() throws NoSuchEJBException {
      if (this.sbi.initOnStartup()) {
         this.init();
      }

   }

   void init() throws NoSuchEJBException {
      this.lifecycleManager.init();
   }

   private Object getBeanFor(InvocationWrapper wrap) throws InternalException {
      try {
         Object bean = this.lifecycleManager.getBean();
         boolean noLockAquisitionErrors = false;

         try {
            if (this.lockManager != null) {
               this.lockManager.acquire(wrap);
            }

            noLockAquisitionErrors = true;
         } finally {
            if (!noLockAquisitionErrors) {
               this.lifecycleManager.relinquishBean();
            }

         }

         return bean;
      } catch (NoSuchEJBException var8) {
         throw new InternalException("Exception during invoke.", var8);
      }
   }

   private void invokePreDestroy(WLEnterpriseBean bean) throws InternalException {
      this.pushEnvironment();
      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.beanInfo.getClassLoader());
      int oldState = this.preLifecycleInvoke(bean, 262144);
      InvocationWrapper wrap = InvocationWrapper.newInstance(this.sbi.getPreDestroyMethodDescriptor());
      Throwable th = null;

      try {
         wrap.enforceTransactionPolicy(this.getTransactionPolicy(wrap, "preDestroy()"));
         this.ejbComponentCreator.invokePreDestroy(bean, this.beanInfo.getEJBName());
      } catch (InternalException var11) {
         th = var11;
         throw var11;
      } catch (RuntimeException var12) {
         th = var12;
         throw var12;
      } finally {
         this.postLifecycleInvoke(bean, oldState);
         Thread.currentThread().setContextClassLoader(clSave);
         this.popEnvironment();
         this.handleLifecycleCallbackTx(wrap, "preDestroy()", "Singleton session", (Throwable)th);
      }

   }

   private WLEnterpriseBean constructAndInitBean() throws InternalException {
      MethodInvocationHelper.pushMethodObject(this.beanInfo);
      SecurityHelper.pushCallerPrincipal(KERNEL_ID);
      int sizeBeforePush = SubjectManager.getSubjectManager().getSize();
      boolean fileSet = SecurityHelper.pushSpecificRunAsMaybe(KERNEL_ID, this.filePtr, this.fileDesc);
      this.pushEnvironment();
      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.beanInfo.getModuleClassLoader());
      SessionContext ctx = new SessionEJBContextImpl((Object)null, this, (SingletonEJBHomeImpl)this.remoteHome, (SingletonEJBLocalHomeImpl)this.localHome, (EJBObject)null, (EJBLocalObject)null);
      EJBContextManager.pushEjbContext(ctx);
      WLEnterpriseBean bean = null;
      boolean var22 = false;

      int sizeBeforePop;
      try {
         var22 = true;
         AllowedMethodsHelper.pushMethodInvocationState(1);

         try {
            bean = (WLEnterpriseBean)this.createNewBeanInstance();
         } finally {
            AllowedMethodsHelper.popMethodInvocationState();
         }

         ((WLEJBContext)ctx).setBean(bean);
         bean.__WL_setEJBContext(ctx);
         sizeBeforePop = this.preLifecycleInvoke(bean, 262144);
         InvocationWrapper wrap = InvocationWrapper.newInstance(this.sbi.getPostConstructMethodDescriptor());
         Throwable th = null;

         try {
            wrap.enforceTransactionPolicy(this.getTransactionPolicy(wrap, "postConstruct()"));
            this.ejbComponentCreator.invokePostConstruct(bean, this.beanInfo.getEJBName());
         } catch (InternalException var43) {
            th = var43;
            throw var43;
         } catch (RuntimeException var44) {
            th = var44;
            throw var44;
         } finally {
            this.postLifecycleInvoke(bean, sizeBeforePop);
            this.handleLifecycleCallbackTx(wrap, "postConstruct()", "Singleton session", (Throwable)th);
         }

         var22 = false;
      } catch (IllegalAccessException var47) {
         throw new AssertionError(var47);
      } catch (InstantiationException var48) {
         throw new InternalException("Error calling bean's constructor: ", var48);
      } finally {
         if (var22) {
            EJBContextManager.popEjbContext();
            Thread.currentThread().setContextClassLoader(clSave);
            this.popEnvironment();
            if (fileSet) {
               int sizeBeforePop = SubjectManager.getSubjectManager().getSize();

               while(sizeBeforePop-- > sizeBeforePush) {
                  SecurityHelper.popRunAsSubject(KERNEL_ID);
               }
            }

            try {
               SecurityHelper.popCallerPrincipal(KERNEL_ID);
            } catch (PrincipalNotFoundException var41) {
               EJBLogger.logErrorPoppingCallerPrincipal(var41);
            }

            MethodInvocationHelper.popMethodObject(this.beanInfo);
         }
      }

      EJBContextManager.popEjbContext();
      Thread.currentThread().setContextClassLoader(clSave);
      this.popEnvironment();
      if (fileSet) {
         sizeBeforePop = SubjectManager.getSubjectManager().getSize();

         while(sizeBeforePop-- > sizeBeforePush) {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }
      }

      try {
         SecurityHelper.popCallerPrincipal(KERNEL_ID);
      } catch (PrincipalNotFoundException var42) {
         EJBLogger.logErrorPoppingCallerPrincipal(var42);
      }

      MethodInvocationHelper.popMethodObject(this.beanInfo);
      return bean;
   }

   public String toString() {
      BeanInfo bi = this.getBeanInfo();
      return bi == null ? "SingletonManager for null" : "SingletonManager for " + bi.getDisplayName();
   }

   private static void debug(String s) {
      debugLogger.debug("[SingletonManager] " + s);
   }

   private final class SingletonLifecycleManager {
      private static final int NOT_INITED = 1;
      private static final int INIT_IN_PROGRESS = 2;
      private static final int INITED_SUCCESSFULLY = 4;
      private static final int INIT_FAILED = 8;
      private static final int DESTROY_REQUESTED = 16;
      private static final int DESTROY_IN_PROGRESS = 32;
      private static final int DESTROYED = 64;
      private final Lock stateLock = new ReentrantLock();
      private final Condition initCompleted;
      private final Condition inFlightWorkCompleted;
      private volatile int state;
      private volatile int inFlightCount;
      private volatile WLEnterpriseBean bean;

      SingletonLifecycleManager() {
         this.initCompleted = this.stateLock.newCondition();
         this.inFlightWorkCompleted = this.stateLock.newCondition();
         this.state = 1;
         this.inFlightCount = 0;
      }

      void init() throws NoSuchEJBException {
         this.initInternal(false);
      }

      WLEnterpriseBean getBean() throws NoSuchEJBException {
         this.stateLock.lock();

         WLEnterpriseBean var1;
         try {
            if (this.state != 4) {
               return this.initInternal(true);
            }

            ++this.inFlightCount;
            var1 = this.bean;
         } finally {
            this.stateLock.unlock();
         }

         return var1;
      }

      void relinquishBean() {
         this.stateLock.lock();

         try {
            --this.inFlightCount;
            if (this.state == 32 && this.inFlightCount == 0) {
               this.inFlightWorkCompleted.signal();
            }
         } finally {
            this.stateLock.unlock();
         }

      }

      void perhapsDestroy() {
         boolean doDestroy = false;
         this.stateLock.lock();

         try {
            if (this.state == 2) {
               this.state = 16;

               while(this.state == 16) {
                  try {
                     this.initCompleted.await();
                  } catch (InterruptedException var17) {
                  }
               }

               if (this.state == 32) {
                  doDestroy = true;
               } else {
                  if (this.state != 8) {
                     throw new IllegalStateException("Unexpected status - " + this.state);
                  }

                  this.state = 64;
               }
            } else if ((this.state & 9) != 0) {
               this.state = 64;
            } else if (this.state == 4) {
               this.state = 32;

               try {
                  while(this.inFlightCount > 0) {
                     this.inFlightWorkCompleted.await();
                  }
               } catch (InterruptedException var18) {
               }

               doDestroy = true;
            } else if (this.state != 64) {
               throw new IllegalStateException("Unexpected status - " + this.state);
            }
         } finally {
            this.stateLock.unlock();
         }

         if (doDestroy) {
            if (SingletonSessionManager.this.dependencyInfo != null) {
               Iterator var2 = SingletonSessionManager.this.dependencyInfo.getDependents().iterator();

               while(var2.hasNext()) {
                  SingletonSessionBeanInfo ssbi = (SingletonSessionBeanInfo)var2.next();
                  ((SingletonSessionManager)ssbi.getBeanManager()).undeploy();
               }
            }

            try {
               SingletonSessionManager.this.invokePreDestroy(this.bean);
            } catch (InternalException var16) {
               EJBLogger.logSingletonSessionBeanPreDestroyException(SingletonSessionManager.this.sbi.getDisplayName(), var16.getMessage());
            }

            this.bean = null;
            this.stateLock.lock();

            try {
               this.state = 64;
            } finally {
               this.stateLock.unlock();
            }
         }

      }

      private WLEnterpriseBean initInternal(boolean incInFlightCount) throws NoSuchEJBException {
         boolean doInit = false;
         this.stateLock.lock();

         try {
            if (this.state == 1) {
               this.state = 2;
               doInit = true;
            }
         } finally {
            this.stateLock.unlock();
         }

         NoSuchEJBException origException = null;
         if (doInit) {
            try {
               this.doActualInit();
            } catch (NoSuchEJBException var23) {
               origException = var23;
            }
         } else {
            this.stateLock.lock();

            try {
               while(this.state == 2) {
                  try {
                     this.initCompleted.await();
                  } catch (InterruptedException var22) {
                  }
               }
            } finally {
               this.stateLock.unlock();
            }
         }

         this.stateLock.lock();

         WLEnterpriseBean var4;
         try {
            if (this.state != 4) {
               if (this.state == 8) {
                  if (origException != null) {
                     throw origException;
                  }

                  throw new NoSuchEJBException("Singleton " + SingletonSessionManager.this.sbi.getDisplayName() + " failed to initialize.");
               }

               if ((this.state & 112) != 0) {
                  throw new NoSuchEJBException("Singleton " + SingletonSessionManager.this.sbi.getDisplayName() + " is already undeployed.");
               }

               throw new IllegalStateException("Unexpected status - " + this.state);
            }

            if (incInFlightCount) {
               ++this.inFlightCount;
            }

            var4 = this.bean;
         } finally {
            this.stateLock.unlock();
         }

         return var4;
      }

      private void doActualInit() throws NoSuchEJBException {
         try {
            if (SingletonSessionManager.this.dependencyInfo != null) {
               Iterator var1 = SingletonSessionManager.this.dependencyInfo.getDependencies().iterator();

               while(var1.hasNext()) {
                  SingletonSessionBeanInfo ssbi = (SingletonSessionBeanInfo)var1.next();

                  try {
                     ((SingletonSessionManager)ssbi.getBeanManager()).init();
                  } catch (NoSuchEJBException var19) {
                     Loggable l = EJBLogger.dependedOnSingletonFailedInitLoggable(SingletonSessionManager.this.sbi.getDisplayName(), ssbi.getDisplayName());
                     throw new NoSuchEJBException(l.getMessage());
                  }
               }
            }

            try {
               this.bean = SingletonSessionManager.this.constructAndInitBean();
            } catch (Exception var18) {
               throw new NoSuchEJBException("Singleton " + SingletonSessionManager.this.sbi.getDisplayName() + " failed to initialize.", var18);
            }

            this.stateLock.lock();

            try {
               if (this.state == 2) {
                  this.state = 4;
                  this.initCompleted.signalAll();
               } else {
                  if (this.state != 16) {
                     throw new IllegalStateException("Unexpected status - " + this.state);
                  }

                  this.state = 32;
                  this.initCompleted.signalAll();
               }
            } finally {
               this.stateLock.unlock();
            }

         } catch (NoSuchEJBException var21) {
            this.stateLock.lock();

            try {
               if ((this.state & 18) == 0) {
                  throw new IllegalStateException("Unexpected status - " + this.state);
               }

               this.state = 8;
               this.initCompleted.signalAll();
            } finally {
               this.stateLock.unlock();
            }

            throw var21;
         }
      }
   }

   private static class CMCLockManager implements LockManager {
      private final SingletonSessionBeanInfo sbi;
      private final ReentrantReadWriteLock reentrantRWLock;
      private final ReentrantReadWriteLock.ReadLock readLock;
      private final ReentrantReadWriteLock.WriteLock writeLock;
      private final AtomicInteger readLockTimeoutTotalCount = new AtomicInteger(0);
      private final AtomicLong readLockTotalCount = new AtomicLong(0L);
      private final AtomicInteger writeLockTimeoutTotalCount = new AtomicInteger(0);
      private final AtomicLong writeLockTotalCount = new AtomicLong(0L);

      CMCLockManager(SingletonSessionBeanInfo sbi) {
         assert sbi.usesContainerManagedConcurrency();

         this.sbi = sbi;
         this.reentrantRWLock = new ReentrantReadWriteLock(true);
         this.readLock = this.reentrantRWLock.readLock();
         this.writeLock = this.reentrantRWLock.writeLock();
      }

      public void acquire(InvocationWrapper wrap) {
         Lock lockToUse = null;
         ConcurrencyInfo ci = wrap.getMethodDescriptor().getConcurrencySettings();
         if (ci != null && "Read".equals(ci.getConcurrentLockType())) {
            lockToUse = this.readLock;
            this.readLockTotalCount.incrementAndGet();
         } else {
            if (this.reentrantRWLock.getReadHoldCount() != 0 && !this.reentrantRWLock.isWriteLockedByCurrentThread()) {
               Loggable l = EJBLogger.illegalSingletonLoopbackCallLoggable();
               throw new IllegalLoopbackException(l.getMessage());
            }

            lockToUse = this.writeLock;
            this.writeLockTotalCount.incrementAndGet();
         }

         if (ci != null && ci.getTimeout() != -1L) {
            boolean lockAcquired = false;

            try {
               lockAcquired = ((Lock)lockToUse).tryLock(ci.getTimeout(), ci.getTimeoutUnit());
               wrap.setAcquiredLock((Lock)lockToUse);
            } catch (InterruptedException var6) {
               throw new AssertionError("Thread interrupted while waiting for the lock.");
            }

            if (!lockAcquired) {
               if ("Read".equals(ci.getConcurrentLockType())) {
                  this.readLockTimeoutTotalCount.incrementAndGet();
               } else {
                  this.writeLockTimeoutTotalCount.incrementAndGet();
               }

               Loggable l;
               if (ci.getTimeout() == 0L) {
                  l = EJBLogger.singletonBeanInUseLoggable(this.sbi.getDisplayName());
                  throw new ConcurrentAccessException(l.getMessage());
               }

               l = EJBLogger.singletonConcurrentAccessTimeoutLoggable(this.sbi.getDisplayName(), wrap.getMethodDescriptor().getMethodName());
               throw new ConcurrentAccessTimeoutException(l.getMessage());
            }
         } else {
            ((Lock)lockToUse).lock();
            wrap.setAcquiredLock((Lock)lockToUse);
         }

      }

      public void release(InvocationWrapper wrap) {
         if (wrap.getAcquiredLock() != null) {
            wrap.getAcquiredLock().unlock();
            wrap.setAcquiredLock((Lock)null);
         }

      }

      public int getReadLockTimeoutTotalCount() {
         return this.readLockTimeoutTotalCount.get();
      }

      public long getReadLockTotalCount() {
         return this.readLockTotalCount.get();
      }

      public int getWriteLockTimeoutTotalCount() {
         return this.writeLockTimeoutTotalCount.get();
      }

      public long getWriteLockTotalCount() {
         return this.writeLockTotalCount.get();
      }

      public int getWaiterCurrentCount() {
         return this.reentrantRWLock.getQueueLength();
      }
   }

   private interface LockManager extends SingletonLockStatisticsProvider {
      void acquire(InvocationWrapper var1);

      void release(InvocationWrapper var1);
   }
}
