package weblogic.ejb.container.manager;

import com.oracle.pitchfork.interfaces.intercept.__ProxyControl;
import java.lang.reflect.Method;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.NoSuchEJBException;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TransactionRolledbackLocalException;
import javax.naming.Context;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.cluster.ClusterHelper;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.ReadConfig;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.EjbComponentCreator;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.SecurityRoleReference;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.container.interfaces.TxManager;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.BaseEJBHome;
import weblogic.ejb.container.internal.ClientViewDescriptor;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationContextStack;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.LifecycleInvocationContextImpl;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.TransactionPolicy;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.timer.EJBTimerManagerFactory;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.Injector;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.logging.Loggable;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtilsClient;

public abstract class BaseEJBManager implements BeanManager {
   protected static final DebugLogger debugLogger;
   private EJBRuntimeMBean runtimeMBean;
   private EJBRuntimeHolder componentRuntime;
   private ISecurityHelper helper;
   private Set injectors = Collections.emptySet();
   private Context environmentContext;
   protected BaseEJBRemoteHomeIntf remoteHome;
   protected BaseEJBLocalHomeIntf localHome;
   protected Class beanClass;
   protected BeanInfo beanInfo;
   protected EjbComponentCreator ejbComponentCreator;
   protected TimerManager timerManager;
   protected TxManager txManager;
   private boolean isDeployed = false;
   protected static final String METHOD_SIGNATURE_POSTCONSTRUCT = "postConstruct()";
   protected static final String METHOD_SIGNATURE_PREDESTROY = "preDestroy()";
   protected final boolean inCluster;

   public BaseEJBManager(EJBRuntimeHolder runtime) {
      this.componentRuntime = runtime;
      this.inCluster = ReadConfig.isClusteredServer();
   }

   public boolean isHomeClusterableAndInCluster() {
      if (this.remoteHome == null) {
         return false;
      } else {
         return ((BaseEJBHome)this.remoteHome).isHomeClusterable() && this.inCluster;
      }
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo info, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      this.beanInfo = info;
      this.remoteHome = remoteHome;
      this.localHome = localHome;
      this.environmentContext = environmentContext;
      this.createEJBTimerManager();
      this.ejbComponentCreator = info.getEjbComponentCreator();
      this.helper = sh;
   }

   public void setIsDeployed(boolean b) {
      this.isDeployed = b;
   }

   protected void createEJBTimerManager() {
      if (this.beanInfo.isTimerDriven()) {
         this.timerManager = EJBTimerManagerFactory.createEJBTimerManager(this, this.beanInfo.isClusteredTimers());
      }

   }

   public boolean getIsDeployed() {
      return this.isDeployed;
   }

   public String getDisplayName() {
      return this.beanInfo.getDisplayName();
   }

   public void pushEnvironment() {
      EJBRuntimeUtils.pushEnvironment(this.environmentContext);
   }

   public void popEnvironment() {
      EJBRuntimeUtils.popEnvironment();
   }

   protected TxManager getTxManager() {
      return this.txManager;
   }

   public void ensureDeployed() throws NoSuchEJBException {
      if (!this.isDeployed) {
         throw new NoSuchEJBException("Bean is already undeployed.");
      }
   }

   protected void preInvoke() throws InternalException {
      try {
         this.ensureDeployed();
      } catch (NoSuchEJBException var2) {
         EJBRuntimeUtils.throwInternalException("Exception during invoke.", var2);
      }

   }

   public void setEJBRuntimeMBean(EJBRuntimeMBean mb) {
      this.runtimeMBean = mb;
   }

   public EJBRuntimeMBean getEJBRuntimeMBean() {
      return this.runtimeMBean;
   }

   public Context getEnvironmentContext() {
      return this.environmentContext;
   }

   public BeanInfo getBeanInfo() {
      return this.beanInfo;
   }

   protected void perhapsSetupTimerManager(EJBTimerRuntimeMBean timerRT) throws WLDeploymentException {
      assert this.runtimeMBean != null;

      if (this.timerManager != null) {
         this.timerManager.setup(timerRT);
      }

   }

   public void perhapsStartTimerManager() {
      if (this.timerManager != null) {
         this.timerManager.perhapsStart();
      }

   }

   public TimerManager getTimerManager() {
      return this.timerManager;
   }

   public void invokeTimeoutMethod(Object bean, Timer timer, Method timeoutMethod) {
      if (this.beanInfo.isEJB30()) {
         this.ejbComponentCreator.invokeTimer(bean, timeoutMethod, timer, this.beanInfo.getEJBName());
      } else {
         ((TimedObject)bean).ejbTimeout(timer);
      }

   }

   public void clearInjectors() {
      this.injectors.clear();
   }

   public void registerInjector(Injector injector) {
      if (this.injectors.isEmpty()) {
         this.injectors = new HashSet();
      }

      this.injectors.add(injector);
   }

   protected Object createNewBeanInstance() throws IllegalAccessException, InstantiationException {
      Object bean;
      if (this.beanInfo.isEJB30()) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();
         currentThread.setContextClassLoader(this.beanInfo.getModuleClassLoader());

         try {
            bean = this.ejbComponentCreator.getBean(this.beanInfo.getEJBName(), this.beanClass, true);
         } finally {
            currentThread.setContextClassLoader(clSave);
         }
      } else {
         bean = this.beanClass.newInstance();
      }

      this.perhapsInvokeInjectors(bean);
      return bean;
   }

   protected void perhapsInvokeInjectors(Object bean) {
      Injector injector;
      Object beanInstance;
      for(Iterator var2 = this.injectors.iterator(); var2.hasNext(); injector.injectMembers(beanInstance)) {
         injector = (Injector)var2.next();
         beanInstance = bean;
         if (__ProxyControl.class.isAssignableFrom(bean.getClass())) {
            beanInstance = ((__ProxyControl)bean).__getTarget().getBeanTarget();
         }
      }

   }

   public final Object allocateBean() throws InternalException {
      try {
         return this.createNewBeanInstance();
      } catch (IllegalAccessException var2) {
         throw new AssertionError(var2);
      } catch (InstantiationException var3) {
         throw new InternalException("Error calling bean's constructor: ", var3);
      }
   }

   public void setupTxListener(InvocationWrapper wrap) throws InternalException {
      Transaction invokeTx = wrap.getInvokeTx();

      assert invokeTx != null;

      try {
         if (debugLogger.isDebugEnabled()) {
            debug("Setting up tx listener for tx: " + invokeTx);
         }

         if (invokeTx == wrap.getCallerTx() || invokeTx.getStatus() != 1) {
            this.getTxManager().registerSynchronization(wrap.getPrimaryKey(), invokeTx);
         }
      } catch (Exception var4) {
         this.handleSystemException(wrap, var4);
         throw new AssertionError("Should never reach here");
      }
   }

   public void setupTxListener(Object pk, Object txOrThread) throws InternalException {
      if (txOrThread instanceof Transaction) {
         if (debugLogger.isDebugEnabled()) {
            debug("Setting up tx listener for tx: " + txOrThread);
         }

         this.getTxManager().registerSynchronization(pk, (Transaction)txOrThread);
      }
   }

   void handleSystemException(InvocationWrapper wrap, Throwable ee) throws InternalException {
      Transaction invokeTx = wrap.getInvokeTx();
      if (invokeTx == null) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", ee);
      } else if (wrap.runningInOurTx()) {
         try {
            invokeTx.rollback();
         } catch (Exception var10) {
            EJBLogger.logStackTraceAndMessage(var10.getMessage(), var10);
            EJBLogger.logErrorOnRollback(var10);
         }

         EJBRuntimeUtils.throwInternalException("EJB Exception: ", ee);
      } else {
         int status = 0;

         try {
            status = invokeTx.getStatus();
         } catch (SystemException var9) {
         }

         if (status == 0 || status == 5) {
            try {
               invokeTx.setRollbackOnly();
            } catch (Exception var8) {
               EJBLogger.logErrorMarkingRollback(var8);
            }
         }

         if (!(ee instanceof NoSuchObjectException) && !(ee instanceof NoSuchEJBException)) {
            if (wrap.isLocal()) {
               try {
                  throw EJBRuntimeUtils.asTxRollbackLocalException("EJB Exception: ", ee);
               } catch (TransactionRolledbackLocalException var6) {
                  throw new InternalException("EJB Exception: ", var6);
               }
            }

            try {
               throw EJBRuntimeUtils.asTxRollbackException("EJB Exception: ", ee);
            } catch (RemoteException var7) {
               throw new InternalException("EJB Exception: ", var7);
            }
         }

         EJBRuntimeUtils.throwInternalException("EJB Exception: ", ee);
      }

   }

   protected void ensureBMTCommitted(String methodSignature, String beanType) throws InternalException {
      if (this.beanInfo.usesBeanManagedTx()) {
         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx != null && tx.getStatus() == 0) {
               try {
                  tx.rollback();
               } catch (Exception var5) {
                  EJBLogger.logErrorDuringRollback(tx.toString(), StackTraceUtilsClient.throwable2StackTrace(var5));
               }

               Loggable l = EJBLogger.logMethodDidNotCompleteTXLoggable(methodSignature, this.beanInfo.getEJBName(), beanType);
               EJBRuntimeUtils.throwInternalException("Error during postInvoke or postCallback.", new Exception(l.getMessage()));
            }
         } catch (SystemException var6) {
            EJBRuntimeUtils.throwInternalException("Error getting current tx: ", var6);
         }

      }
   }

   protected void handleLifecycleCallbackTx(InvocationWrapper wrap, String callbackSignature, String beanType, Throwable th) throws InternalException {
      try {
         this.ensureBMTCommitted(callbackSignature, beanType);
         if (wrap.runningInOurTx()) {
            Transaction invokeTx = wrap.getInvokeTx();
            int st = -1;

            try {
               st = invokeTx.getStatus();
            } catch (SystemException var19) {
            }

            if (0 != st) {
               try {
                  invokeTx.rollback();
               } catch (Exception var15) {
                  EJBLogger.logErrorDuringRollback(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var15));
               }

               throw new InternalException("Transaction marked rollback or not expected transaction status: " + st);
            }

            if (null != th && wrap.getMethodDescriptor() != null && this.getBeanInfo().getDeploymentInfo().getExceptionInfo(wrap.getMethodDescriptor().getMethod(), th).isRollback()) {
               try {
                  invokeTx.rollback();
               } catch (Exception var18) {
                  EJBLogger.logErrorDuringRollback(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var18));
               }
            } else {
               try {
                  invokeTx.commit();
               } catch (Exception var17) {
                  EJBLogger.logErrorDuringCommit(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var17));
                  EJBRuntimeUtils.throwInternalException("Error committing transaction:", var17);
               }
            }
         } else if (wrap.runningInCallerTx() && null != th && wrap.getMethodDescriptor() != null && this.getBeanInfo().getDeploymentInfo().getExceptionInfo(wrap.getMethodDescriptor().getMethod(), th).isRollback()) {
            try {
               wrap.getCallerTx().setRollbackOnly();
            } catch (Exception var16) {
               EJBLogger.logExcepDuringSetRollbackOnly(var16);
            }
         }
      } finally {
         wrap.resumeCallersTransaction();
      }

   }

   protected void handleMethodException(Method m, Class[] extraExceptions, Throwable th) throws InternalException {
      boolean isAppException;
      if (this.beanInfo.isEJB30()) {
         isAppException = this.getBeanInfo().getDeploymentInfo().getExceptionInfo(m, th).isAppException();
      } else {
         isAppException = EJBRuntimeUtils.isAppException(m, extraExceptions, th);
      }

      if (isAppException) {
         EJBRuntimeUtils.throwInternalException("EJB Exception:", th);
      } else {
         EJBLogger.logExcepInMethod1(m.getName(), th);
         EJBRuntimeUtils.throwInternalException("EJB Exception:", th);
      }

   }

   protected EJBRuntimeHolder getEJBComponentRuntime() {
      return this.componentRuntime;
   }

   protected void addEJBRuntimeMBean(EJBRuntimeMBean mb) {
      this.getEJBComponentRuntime().addEJBRuntimeMBean(mb.getEJBName(), mb);
   }

   protected void removeEJBRuntimeMBean(EJBRuntimeMBean mb) {
      this.getEJBComponentRuntime().removeEJBRuntimeMBean(mb.getEJBName());
   }

   public void undeploy() {
      if (this.localHome != null) {
         this.localHome.undeploy();
      }

      if (this.remoteHome != null) {
         this.remoteHome.undeploy();
      }

      this.undeployTimerManager();
      if (this.beanInfo != null) {
         this.beanInfo.onUndeploy();
      }

      if (this.getTxManager() != null) {
         this.getTxManager().undeploy();
      }

   }

   protected void undeployTimerManager() {
      if (this.timerManager != null) {
         this.timerManager.undeploy();
      }

   }

   public void handleUncommittedLocalTransaction(InvocationWrapper wrap) throws InternalException {
      Debug.assertion(false, "handleUncommitedLocalTransaction N/A on this bean");
   }

   public boolean checkWritable(String rName) {
      String derefRName = rName;
      SecurityRoleReference ref = this.beanInfo.getSecurityRoleReference(rName);
      if (ref != null) {
         String refRole = ref.getReferencedRole();
         if (debugLogger.isDebugEnabled()) {
            debug(" referenced role for roleName: '" + rName + "', is '" + refRole + "'");
         }

         derefRName = refRole;
      }

      return this.helper.isCallerInRole(this.beanInfo.getEJBName(), rName, derefRName);
   }

   protected final int preLifecycleInvoke(WLEnterpriseBean bean, int beanState) {
      return this.preLifecycleInvoke(bean, beanState, (InvocationWrapper)null);
   }

   protected final int preLifecycleInvoke(WLEnterpriseBean bean, int beanState, InvocationWrapper wrap) {
      AllowedMethodsHelper.pushBean(bean);
      EJBContextManager.pushEjbContext(bean.__WL_getEJBContext());
      int oldState = bean.__WL_getMethodState();
      bean.__WL_setMethodState(beanState);
      InvocationContextStack.push(new LifecycleInvocationContextImpl());
      if (wrap != null) {
         wrap.setBean(bean);
         wrap.perhapsPushXPCWrappers();
      }

      return oldState;
   }

   protected final void postLifecycleInvoke(WLEnterpriseBean bean, int beanState) {
      this.postLifecycleInvoke(bean, beanState, (InvocationWrapper)null);
   }

   protected final void postLifecycleInvoke(WLEnterpriseBean bean, int beanState, InvocationWrapper wrap) {
      InvocationContextStack.pop();
      bean.__WL_setMethodState(beanState);
      EJBContextManager.popEjbContext();
      AllowedMethodsHelper.popBean();
      if (wrap != null) {
         wrap.popXPCWrappers();
      }

   }

   protected TransactionPolicy getTransactionPolicy(InvocationWrapper wrap, String sig) {
      MethodDescriptor md = wrap.getMethodDescriptor();
      if (null != md) {
         return md.getTransactionPolicy();
      } else {
         short txAttribute = this.beanInfo.usesBeanManagedTx() ? 0 : 3;
         if (debugLogger.isDebugEnabled()) {
            debug("There is no callback method defined in bean class for EJB " + this.beanInfo.getEJBName() + ", but we need set a default tx attribute " + (String)DDConstants.TX_ATTRIBUTE_STRINGS.get(txAttribute) + " for the callbacks defind in super classes or interceptors.");
         }

         return new TransactionPolicy(sig, txAttribute, -1, 0, new ClientViewDescriptor(this.beanInfo.getBeanClass(), "LifecycleCallback", true, false, this.beanInfo));
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[BaseEJBManager] " + s);
   }

   public boolean isServerShuttingDown() {
      return ClusterHelper.isServerShuttingDown();
   }

   public boolean isPartitionShuttingDown() {
      String partitionName = this.getPartitionName();
      return ClusterHelper.isPartitionShuttingDown(partitionName);
   }

   public String getPartitionName() {
      ComponentInvocationContext cic = this.beanInfo.getCIC();
      String partitionName = null;
      if (cic != null) {
         partitionName = cic.getPartitionName();
      }

      if (partitionName == null || partitionName.isEmpty()) {
         partitionName = "DOMAIN";
      }

      return partitionName;
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
   }
}
