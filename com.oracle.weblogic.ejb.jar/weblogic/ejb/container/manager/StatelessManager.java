package weblogic.ejb.container.manager;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.transaction.SystemException;
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
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EJBCreateInvoker;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.PoolIntf;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.internal.SessionEJBContextImpl;
import weblogic.ejb.container.internal.StatelessEJBHome;
import weblogic.ejb.container.internal.StatelessEJBLocalHome;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.internal.TxManagerImpl;
import weblogic.ejb.container.monitoring.StatelessEJBRuntimeMBeanImpl;
import weblogic.ejb.container.pool.StatelessSessionPool;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.StatelessEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.StackTraceUtilsClient;

public final class StatelessManager extends BaseEJBManager {
   private static final AuthenticatedSubject KERNEL_ID;
   private Class beanIntf;
   private boolean implementsSessionBeanIntf;
   private PoolIntf pool;
   private AuthenticatedSubject fileDesc;
   private AuthenticatedSubject filePtr;
   private AuthenticatedSubject fileSegment;
   static final long serialVersionUID = 7805301767998733528L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.StatelessManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public StatelessManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public String toString() {
      return "StatelessManager for " + (this.getBeanInfo() != null ? this.getBeanInfo().getDisplayName() : "null (Not initialized yet)");
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo info, Context environmentContext, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, info, environmentContext, sh);
      SessionBeanInfo sbi = (SessionBeanInfo)info;
      this.beanClass = sbi.getGeneratedBeanClass();
      this.beanIntf = sbi.getGeneratedBeanInterface();
      this.implementsSessionBeanIntf = SessionBean.class.isAssignableFrom(this.beanClass);

      try {
         StatelessEJBRuntimeMBean rtMBean = new StatelessEJBRuntimeMBeanImpl(info, this.getEJBComponentRuntime(), this.getTimerManager());
         this.setEJBRuntimeMBean(rtMBean);
         this.addEJBRuntimeMBean(rtMBean);
         this.perhapsSetupTimerManager(rtMBean.getTimerRuntime());
         if (info.getRunAsPrincipalName() != null || info.getCreateAsPrincipalName() != null || info.getRemoveAsPrincipalName() != null) {
            if (info.getRunAsPrincipalName() != null) {
               this.fileDesc = sh.getSubjectForPrincipal(info.getRunAsPrincipalName());
            }

            if (info.getCreateAsPrincipalName() != null) {
               this.filePtr = sh.getSubjectForPrincipal(info.getCreateAsPrincipalName());
            }

            if (info.getRemoveAsPrincipalName() != null) {
               this.fileSegment = sh.getSubjectForPrincipal(info.getRemoveAsPrincipalName());
            }
         }

         this.pool = new StatelessSessionPool((StatelessEJBHome)remoteHome, (StatelessEJBLocalHome)localHome, this, info, rtMBean.getPoolRuntime());
         this.txManager = new TxManagerImpl(this);
         if (debugLogger.isDebugEnabled()) {
            debug("In setup for :" + this);
         }

      } catch (PrincipalNotFoundException var9) {
         throw new WLDeploymentException(var9.getMessage(), var9);
      } catch (ManagementException var10) {
         Loggable l = EJBLogger.logFailedToCreateRuntimeMBeanLoggable();
         throw new WLDeploymentException(l.getMessageText(), var10);
      }
   }

   public void doEjbRemove(Object o) throws RemoteException {
      boolean isSet = SecurityHelper.pushSpecificRunAsMaybe(KERNEL_ID, this.fileSegment, this.fileDesc);
      WLEnterpriseBean bean = (WLEnterpriseBean)o;
      int oldState = this.preLifecycleInvoke(bean, 16);

      try {
         if (this.implementsSessionBeanIntf) {
            ((SessionBean)bean).ejbRemove();
         } else {
            this.ejbComponentCreator.invokePreDestroy(bean, this.beanInfo.getEJBName());
         }
      } finally {
         this.postLifecycleInvoke(bean, oldState);
         if (isSet) {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

      }

   }

   public PoolIntf getPool() {
      return this.pool;
   }

   public Object preInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[2];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         var3.resetPostBegin();
      }

      Object var6;
      try {
         super.preInvoke();
         int txTimeoutSeconds = wrap.getMethodDescriptor().getTxTimeoutSeconds();
         if (debugLogger.isDebugEnabled()) {
            debug("In preInvoke with timeout in seconds:" + txTimeoutSeconds + " on manager: " + this);
         }

         if (wrap.getInvokeTx() != null) {
            this.setupTxListener(wrap);
         }

         var6 = this.pool.getBean((long)txTimeoutSeconds * 1000L);
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            var3.ret = null;
            InstrumentationSupport.process(var3);
         }

         throw var5;
      }

      if (var3 != null) {
         var3.ret = var6;
         InstrumentationSupport.process(var3);
      }

      return var6;
   }

   public void postInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[2];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.process(var4);
         var4.resetPostBegin();
      }

      if (debugLogger.isDebugEnabled()) {
         debug("In postInvoke on " + this);
      }

      if (this.getBeanInfo().usesBeanManagedTx()) {
         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx != null && tx.getStatus() == 0) {
               EJBLogger.logMustCommit();
               if (tx instanceof weblogic.transaction.Transaction) {
                  ((weblogic.transaction.Transaction)tx).setRollbackOnly("Bean-managed stateless session beans cannot have a transaction that spans multiple methods", (Throwable)null);
               } else {
                  tx.setRollbackOnly();
               }

               this.destroyBean(wrap.getBean());
               Loggable l = EJBLogger.logSLSBMethodDidNotCompleteTXLoggable(wrap.getMethodDescriptor().getMethodInfo().getSignature(), this.beanInfo.getEJBName());
               EJBRuntimeUtils.throwInternalException("Error during postInvoke.", new Exception(l.getMessage()));
            }
         } catch (SystemException var5) {
            this.destroyBean(wrap.getBean());
            EJBRuntimeUtils.throwInternalException("Error getting current tx: ", var5);
         }
      }

      if (wrap.isLocal()) {
         this.pool.releaseBean(wrap.getBean());
      }

   }

   public void destroyInstance(InvocationWrapper wrap, Throwable ee) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In destroyInstance for manager: " + this);
      }

      if (wrap == null || wrap.shouldLogException()) {
         EJBLogger.logExcepDuringInvocFromHomeOrBusiness(this.beanIntf.getName(), this.getBeanInfo().getDisplayName(), StackTraceUtilsClient.throwable2StackTrace(ee));
      }

      this.destroyBean(wrap.getBean());
   }

   private void destroyBean(Object bean) {
      this.ejbComponentCreator.destroyBean(bean);
      this.pool.destroyBean((Object)null);
   }

   public void beforeCompletion(InvocationWrapper wrap) throws InternalException {
   }

   public void beforeCompletion(Collection primaryKeys, Transaction tx) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In beforeCompletion for manager: " + this);
      }

   }

   public void afterCompletion(InvocationWrapper wrap) {
   }

   public void afterCompletion(Collection primaryKeys, Transaction tx, int result, Object ignore) {
      if (debugLogger.isDebugEnabled()) {
         debug("In afterCompletion for manager: " + this);
      }

   }

   public Object createBean(EJBObject eo, EJBLocalObject elo) throws InternalException {
      LocalHolder var16;
      if ((var16 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var16.argsCapture) {
            var16.args = InstrumentationSupport.toSensitive(3);
         }

         InstrumentationSupport.createDynamicJoinPoint(var16);
         InstrumentationSupport.preProcess(var16);
         var16.resetPostBegin();
      }

      WLEnterpriseBean var88;
      try {
         SessionContext ctx = new SessionEJBContextImpl((Object)null, this, (StatelessEJBHome)this.remoteHome, (StatelessEJBLocalHome)this.localHome, eo, elo);
         Thread thread = Thread.currentThread();
         ClassLoader clSave = thread.getContextClassLoader();
         MethodInvocationHelper.pushMethodObject(this.beanInfo);
         SecurityHelper.pushCallerPrincipal(KERNEL_ID);
         int sizeBeforePush = SubjectManager.getSubjectManager().getSize();
         boolean isSet = SecurityHelper.pushSpecificRunAsMaybe(KERNEL_ID, this.filePtr, this.fileDesc);
         this.pushEnvironment();
         thread.setContextClassLoader(this.beanInfo.getModuleClassLoader());
         WLEnterpriseBean bean = null;
         boolean var37 = false;

         int sizeBeforePop;
         try {
            try {
               var37 = true;
               EJBContextManager.pushEjbContext(ctx);
               AllowedMethodsHelper.pushMethodInvocationState(1);
               bean = (WLEnterpriseBean)this.allocateBean();
            } finally {
               EJBContextManager.popEjbContext();
               AllowedMethodsHelper.popMethodInvocationState();
            }

            ((WLEJBContext)ctx).setBean(bean);
            if (this.implementsSessionBeanIntf) {
               AllowedMethodsHelper.pushBean(bean);
               sizeBeforePop = bean.__WL_getMethodState();
               bean.__WL_setMethodState(1);

               try {
                  ((SessionBean)bean).setSessionContext(ctx);
               } catch (Throwable var81) {
                  EJBRuntimeUtils.throwInternalException("Error during setSessionContext", var81);
               } finally {
                  bean.__WL_setMethodState(sizeBeforePop);
                  AllowedMethodsHelper.popBean();
               }
            }

            bean.__WL_setEJBContext(ctx);
            if (this.beanInfo.isEJB30()) {
               sizeBeforePop = this.preLifecycleInvoke(bean, 4);

               try {
                  this.ejbComponentCreator.invokePostConstruct(bean, this.beanInfo.getEJBName());
               } finally {
                  this.postLifecycleInvoke(bean, sizeBeforePop);
               }

               var37 = false;
            } else {
               label896: {
                  sizeBeforePop = this.preLifecycleInvoke(bean, 4);

                  label863: {
                     try {
                        ((EJBCreateInvoker)bean).ejbCreate();
                        break label863;
                     } catch (Throwable var84) {
                        EJBRuntimeUtils.throwInternalException("Error in ejbCreate:", var84);
                     } finally {
                        this.postLifecycleInvoke(bean, sizeBeforePop);
                     }

                     var37 = false;
                     break label896;
                  }

                  var37 = false;
               }
            }
         } finally {
            if (var37) {
               this.popEnvironment();
               thread.setContextClassLoader(clSave);
               if (isSet) {
                  int sizeBeforePop = SubjectManager.getSubjectManager().getSize();

                  while(sizeBeforePop-- > sizeBeforePush) {
                     SecurityHelper.popRunAsSubject(KERNEL_ID);
                  }
               }

               try {
                  SecurityHelper.popCallerPrincipal(KERNEL_ID);
               } catch (Exception var78) {
                  EJBLogger.logErrorPoppingCallerPrincipal(var78);
               }

               if (MethodInvocationHelper.popMethodObject(this.beanInfo)) {
                  this.handleUncommittedLocalTransaction("ejbCreate()", bean);
               }

            }
         }

         this.popEnvironment();
         thread.setContextClassLoader(clSave);
         if (isSet) {
            sizeBeforePop = SubjectManager.getSubjectManager().getSize();

            while(sizeBeforePop-- > sizeBeforePush) {
               SecurityHelper.popRunAsSubject(KERNEL_ID);
            }
         }

         try {
            SecurityHelper.popCallerPrincipal(KERNEL_ID);
         } catch (Exception var79) {
            EJBLogger.logErrorPoppingCallerPrincipal(var79);
         }

         if (MethodInvocationHelper.popMethodObject(this.beanInfo)) {
            this.handleUncommittedLocalTransaction("ejbCreate()", bean);
         }

         var88 = bean;
      } catch (Throwable var87) {
         if (var16 != null) {
            var16.th = var87;
            var16.ret = null;
            InstrumentationSupport.postProcess(var16);
         }

         throw var87;
      }

      if (var16 != null) {
         var16.ret = var88;
         InstrumentationSupport.postProcess(var16);
      }

      return var88;
   }

   public EJBObject remoteCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In create for manager: " + this);
      }

      return this.remoteHome.allocateEO();
   }

   public EJBLocalObject localCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In create for manager: " + this);
      }

      return this.localHome.allocateELO();
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("In remove for manager: " + this);
      }

   }

   public void handleUncommittedLocalTransaction(InvocationWrapper wrap) throws InternalException {
      this.handleUncommittedLocalTransaction(wrap.getMethodDescriptor().getMethodInfo().getSignature(), wrap.getBean());
   }

   private void handleUncommittedLocalTransaction(String methodName, Object bean) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("There is local transaction in progess.");
      }

      EJBLogger.logMustCommit();
      this.destroyBean(bean);
      Loggable l = EJBLogger.logSLSBMethodDidNotCompleteTXLoggable(methodName, this.beanInfo.getEJBName());
      EJBRuntimeUtils.throwInternalException("Error during postInvoke.", new Exception(l.getMessage()));
   }

   public void undeploy() {
      super.undeploy();
      if (this.pool != null) {
         this.pool.cleanup();
      }

   }

   public void initializePool() throws WLDeploymentException {
      this.pool.createInitialBeans();
   }

   public void beanImplClassChangeNotification() {
      this.beanClass = ((SessionBeanInfo)this.beanInfo).getGeneratedBeanClass();
      this.implementsSessionBeanIntf = SessionBean.class.isAssignableFrom(this.beanClass);
      this.pool.reset();
   }

   public void releaseBean(InvocationWrapper wrap) {
      this.pool.releaseBean(wrap.getBean());
   }

   public void reInitializePool() {
      this.pool.reInitializePool();
   }

   private static void debug(String s) {
      debugLogger.debug("[StatelessManager] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Create_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessManager.java", "weblogic.ejb.container.manager.StatelessManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljava/lang/Object;", 143, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessManager.java", "weblogic.ejb.container.manager.StatelessManager", "postInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)V", 158, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessManager.java", "weblogic.ejb.container.manager.StatelessManager", "createBean", "(Ljavax/ejb/EJBObject;Ljavax/ejb/EJBLocalObject;)Ljava/lang/Object;", 244, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Create_Around_Medium};
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
