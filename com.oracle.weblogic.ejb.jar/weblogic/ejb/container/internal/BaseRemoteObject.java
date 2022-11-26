package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.NoSuchEJBException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.Transaction;
import javax.transaction.TransactionRolledbackException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.WLSessionBean;
import weblogic.ejb.container.manager.BaseEJBManager;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.ReplicatedStatefulSessionManager;
import weblogic.ejb.container.manager.SingletonSessionManager;
import weblogic.ejb.container.manager.StatelessManager;
import weblogic.kernel.ThreadLocalStack;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.RemoteEJBPreInvokeException;
import weblogic.rmi.SupportsInterfaceBasedCallByReference;
import weblogic.security.service.ContextHandler;
import weblogic.transaction.RollbackException;
import weblogic.utils.StackTraceUtilsClient;

public abstract class BaseRemoteObject implements SupportsInterfaceBasedCallByReference {
   protected static final DebugLogger debugLogger;
   protected static final ThreadLocalStack currentInvocationWrapper;
   protected ClientDrivenBeanInfo beanInfo;
   protected DeploymentInfo deploymentInfo;
   protected BaseEJBHome ejbHome;
   private BeanManager beanManager;
   private boolean implementsRemote = true;
   static final long serialVersionUID = 3017517117033590964L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.BaseRemoteObject");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public String toString() {
      return "BaseRemoteObject for EJB: '" + (this.beanInfo != null ? this.beanInfo.getEJBName() : "") + "'";
   }

   void setImplementsRemote(boolean implementsRemote) {
      this.implementsRemote = implementsRemote;
   }

   boolean getImplementsRemote() {
      return this.implementsRemote;
   }

   void setBeanManager(BeanManager bm) {
      this.beanManager = bm;
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   void setBeanInfo(BeanInfo bi) {
      this.beanInfo = (ClientDrivenBeanInfo)bi;
      this.deploymentInfo = this.beanInfo.getDeploymentInfo();
   }

   void setEJBHome(BaseEJBHome h) {
      this.ejbHome = h;
   }

   boolean canThrowRemoteEJBInvokeException(InvocationWrapper wrap) {
      if (this.getImplementsRemote() && !wrap.isRemoteInvocation()) {
         return false;
      } else if (this.beanManager instanceof ReplicatedStatefulSessionManager && ((ReplicatedStatefulSessionManager)this.beanManager).isInCluster()) {
         return true;
      } else {
         return (this.beanManager instanceof StatelessManager || this.beanManager instanceof SingletonSessionManager || this.beanManager instanceof BaseEntityManager) && ((BaseEJBManager)this.beanManager).isHomeClusterableAndInCluster();
      }
   }

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws RemoteException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[3];
            Object[] var10000 = var6.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = ch;
         }

         var6.resetPostBegin();
      }

      try {
         try {
            this.preInvokeInternal(wrap, ch);
         } catch (RuntimeException | RemoteException var9) {
            if (this.canThrowRemoteEJBInvokeException(wrap)) {
               if (var9 instanceof RemoteEJBInvokeException) {
                  throw var9;
               }

               if (wrap.isCallerTxCaptured() && !wrap.runningInCallerTx()) {
                  Method m = wrap.getMethodDescriptor() == null ? null : wrap.getMethodDescriptor().getMethod();
                  if (m != null && !EJBRuntimeUtils.isAppException(m, var9)) {
                     if (var9 instanceof RemoteException) {
                        RemoteException re = (RemoteException)var9;
                        if (re.detail != null && re.detail instanceof NoSuchEJBException) {
                           throw new RemoteEJBPreInvokeException(var9.getMessage(), var9);
                        }
                     }

                     throw var9;
                  }

                  throw var9;
               }

               throw var9;
            }

            if (var9 instanceof RemoteEJBInvokeException) {
               throw (RemoteException)var9.getCause();
            }

            throw var9;
         }
      } catch (Throwable var10) {
         if (var6 != null) {
            var6.th = var10;
            InstrumentationSupport.process(var6);
         }

         throw var10;
      }

      if (var6 != null) {
         InstrumentationSupport.process(var6);
      }

   }

   private void preInvokeInternal(InvocationWrapper wrap, ContextHandler ch) throws RemoteException {
      if (debugLogger.isDebugEnabled()) {
         debug("preInvoke called with: " + wrap + " on remote interface of EJB: " + this.beanInfo.getEJBName());
      }

      wrap.setCIC(this.beanInfo);
      if (!this.beanManager.getIsDeployed() && this.canThrowRemoteEJBInvokeException(wrap)) {
         wrap.unsetCIC();
         String msg;
         if (this.beanManager.isServerShuttingDown()) {
            msg = EJBLogger.logServerShuttingDownRejectEJBInvocationLoggable(this.beanInfo.getDisplayName()).getMessageText();
         } else if (this.beanManager.isPartitionShuttingDown()) {
            msg = EJBLogger.logPartitionShuttingDownRejectEJBInvocationLoggable(this.beanInfo.getDisplayName(), this.beanManager.getPartitionName()).getMessageText();
         } else {
            msg = EJBLogger.logBeanUndeployedRejectEJBInvocationLoggable(this.beanInfo.getDisplayName()).getMessageText();
         }

         throw new RemoteEJBPreInvokeException(msg, EJBRuntimeUtils.asRemoteException(msg, (Throwable)null));
      } else {
         try {
            wrap.enforceTransactionPolicy();
         } catch (InternalException var8) {
            wrap.unsetCIC();
            RemoteException re = EJBRuntimeUtils.asRemoteException("EJB Exception: ", var8);
            if (!(var8.getCause() instanceof InvalidTransactionException) || !var8.getCause().getMessage().startsWith(EJBLogger.logCannotStartTransactionLoggable("").getMessageText()) || !this.beanManager.isServerShuttingDown() && !this.beanManager.isPartitionShuttingDown()) {
               throw re;
            }

            throw new RemoteEJBPreInvokeException(var8.getCause().getMessage(), re);
         }

         wrap.setContextClassLoader(this.beanInfo.getClassLoader());
         wrap.pushEnvironment(this.beanManager.getEnvironmentContext());
         wrap.pushMethodObject(this.beanInfo);
         wrap.pushCallerPrincipal();

         try {
            this.pushInvocationWrapperInThreadLocal(wrap);
            Object bean = this.getBeanInstance(wrap);
            wrap.setBean(bean);
            this.setTxCreateAttributeOnBean(wrap);
            ((EJBContextHandler)ch).setEjb(bean);
            if (!this.implementsRemote) {
               wrap.checkMethodPermissionsBusiness(ch);
            } else {
               wrap.checkMethodPermissionsRemote(ch);
            }

            wrap.pushSecurityContext(ch);
            wrap.pushRunAsIdentity();
         } catch (AccessException | EJBAccessException var6) {
            this.handleAccessExceptions(var6, wrap);
            throw new AssertionError("Should never be reached");
         } catch (RuntimeException | RemoteException var7) {
            this.popInvocationWrapperInThreadLocalOnError(wrap);
            wrap.popRunAsIdentity();
            wrap.popCallerPrincipal();
            wrap.popSecurityContext();
            wrap.popMethodObject(this.beanInfo);
            wrap.resetContextClassLoader();
            wrap.popEnvironment();

            try {
               wrap.resumeCallersTransaction();
            } catch (InternalException var5) {
            }

            wrap.unsetCIC();
            throw var7;
         }
      }
   }

   private Object getBeanInstance(InvocationWrapper wrap) throws RemoteException {
      try {
         try {
            Object eb = this.beanManager.preInvoke(wrap);
            if (debugLogger.isDebugEnabled()) {
               debug("Manager returned a bean:" + eb);
            }

            return eb;
         } catch (InternalException var4) {
            if (this.implementsRemote) {
               if (var4.detail instanceof NoSuchEJBException) {
                  Exception ee = new NoSuchObjectException(var4.getMessage());
                  EJBRuntimeUtils.throwInternalException(var4.getMessage(), ee);
               } else if (var4.detail instanceof ConcurrentAccessException) {
                  EJBRuntimeUtils.throwInternalException(var4.getMessage(), var4.detail.getCause());
               }
            }

            throw var4;
         }
      } catch (InternalException var5) {
         if (debugLogger.isDebugEnabled()) {
            debug("Manager's preInvoke threw " + var5);
         }

         this.handleSystemException(wrap, var5);
         throw new AssertionError("Should never reach here");
      } catch (Throwable var6) {
         if (debugLogger.isDebugEnabled()) {
            debug("Manager's preInvoke threw " + var6);
         }

         this.handleSystemException(wrap, var6);
         throw new AssertionError("Should never reach here");
      }
   }

   private void handleAccessExceptions(Exception ex, InvocationWrapper wrap) throws RemoteException {
      if (debugLogger.isDebugEnabled()) {
         debug("Method permission check failed. Do postInvoke sequence. " + ex);
      }

      Throwable throwable = null;

      try {
         this.postInvoke1(0, wrap, ex);
      } catch (Throwable var6) {
         throwable = var6;
      }

      try {
         this.__WL_postInvokeCleanup(wrap, throwable);
      } catch (Exception var5) {
         throw EJBRuntimeUtils.asRemoteException("EJB Exception after method permission failure: ", var5);
      }
   }

   protected abstract void pushInvocationWrapperInThreadLocal(InvocationWrapper var1);

   protected abstract void popInvocationWrapperInThreadLocalOnError(InvocationWrapper var1);

   protected abstract void notifyRemoteCallBegin();

   protected void setTxCreateAttributeOnBean(InvocationWrapper wrap) {
   }

   protected boolean __WL_postInvokeTxRetry(InvocationWrapper wrap, Throwable ee) throws Exception {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[3];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = ee;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.process(var4);
         var4.resetPostBegin();
      }

      boolean retry = this.postInvoke1(wrap.getNextTxRetryCount(), wrap, ee);
      if (retry) {
         wrap.enforceTransactionPolicy();
         wrap.setBean(this.getBeanInstance(wrap));
      }

      if (debugLogger.isDebugEnabled()) {
         debug("__WL_postInvokeTxRetry returning with retry = " + retry);
      }

      return retry;
   }

   public boolean postInvoke1(int retryCount, InvocationWrapper wrap, Throwable ee) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug("postInvoke1 called with txRetryCount: " + retryCount + " wrap: " + wrap + " Exception: " + ee + " on remote interface of EJB: " + this.beanInfo.getEJBName());
         if (ee != null) {
            ((Throwable)ee).printStackTrace();
         }
      }

      Transaction invokeTx = wrap.getInvokeTx();
      Transaction callerTx = wrap.getCallerTx();
      MethodDescriptor md = wrap.getMethodDescriptor();
      Method m = md.getMethod();
      boolean systemExceptionOccurred = false;
      if (ee != null && !this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isAppException() && !(ee instanceof AccessException) && !(ee instanceof EJBAccessException)) {
         systemExceptionOccurred = true;
      } else {
         try {
            this.beanManager.postInvoke(wrap);
         } catch (InternalException var13) {
            ee = var13;
            systemExceptionOccurred = true;
         }
      }

      if (systemExceptionOccurred) {
         if (RemoteException.class.equals(ee.getClass())) {
            ee = new RemoteException("EJB Exception:", (Throwable)ee);
         }

         wrap.setSystemExceptionOccured();
         this.beanManager.destroyInstance(wrap, (Throwable)ee);
         this.handleSystemException(wrap, (Throwable)ee);
         throw new AssertionError("Should never be reached");
      } else if (!(ee instanceof AccessException) && !(ee instanceof EJBAccessException)) {
         if (wrap.getInvokeTx() == null) {
            try {
               this.getBeanManager().beforeCompletion(wrap);
               this.getBeanManager().afterCompletion(wrap);
            } catch (InternalException var15) {
               if (EJBRuntimeUtils.isAppException(m, var15)) {
                  throw (Exception)var15.detail;
               }

               this.handleSystemException(wrap, var15);
               throw new AssertionError("Should never be reached");
            }
         } else if (md.isRemoveMethod() && (ee == null || !md.isRetainIfException() && this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isAppException())) {
            ((WLSessionBean)wrap.getBean()).__WL_setNeedsSessionSynchronization(false);
         }

         if (wrap.runningInOurTx()) {
            if (wrap.hasRolledBack()) {
               if (debugLogger.isDebugEnabled()) {
                  debug(" our tx marked for Rollback, attempt to rollback ");
               }

               try {
                  invokeTx.rollback();
                  if (debugLogger.isDebugEnabled()) {
                     debug("Rollback succeeded.");
                  }
               } catch (Exception var10) {
                  EJBLogger.logErrorDuringRollback1(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var10));
               }

               if (wrap.isSystemRollback()) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("SystemRollback, throw exception");
                  }

                  Throwable th = ((weblogic.transaction.Transaction)invokeTx).getRollbackReason();
                  throw EJBRuntimeUtils.asRemoteException("Transaction Rolledback.", th);
               }

               return this.isTxRetry(retryCount);
            }

            try {
               if (ee != null && this.beanInfo.isEJB30() && this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isRollback()) {
                  if (debugLogger.isDebugEnabled()) {
                     debug(ee.getClass().getName() + " is thrown, attempt to rollback");
                  }

                  try {
                     invokeTx.rollback();
                     if (debugLogger.isDebugEnabled()) {
                        debug("Rollback succeeded.");
                     }
                  } catch (Exception var11) {
                     EJBLogger.logErrorDuringRollback1(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var11));
                  }

                  return this.isTxRetry(retryCount);
               }

               if (debugLogger.isDebugEnabled()) {
                  debug("Committing our tx: " + invokeTx);
               }

               invokeTx.commit();
               if (debugLogger.isDebugEnabled()) {
                  debug("Committing our tx: SUCCESS");
               }
            } catch (Exception var14) {
               if (var14 instanceof RollbackException) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("Committing our tx: ROLLBACK");
                  }

                  if (!EJBRuntimeUtils.isOptimisticLockException(((RollbackException)var14).getNested())) {
                     EJBLogger.logErrorDuringCommit(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var14));
                  }

                  if (retryCount > 0) {
                     return this.isTxRetry(retryCount);
                  }
               }

               throw EJBRuntimeUtils.asRemoteException(((weblogic.transaction.Transaction)invokeTx).getXid().toString(), var14);
            }
         } else if (wrap.runningInCallerTx() && ee != null && this.beanInfo.isEJB30() && this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isRollback()) {
            if (debugLogger.isDebugEnabled()) {
               debug(" caller tx marked for Rollback, attempt to rollback ");
            }

            try {
               callerTx.setRollbackOnly();
               if (debugLogger.isDebugEnabled()) {
                  debug("SetRollbackOnly succeeded.");
               }
            } catch (Exception var12) {
               EJBLogger.logExcepDuringSetRollbackOnly(var12);
            }
         }

         return false;
      } else {
         this.handleSystemException(wrap, (Throwable)ee);
         throw new AssertionError("Should never be reached");
      }
   }

   public void __WL_postInvokeCleanup(InvocationWrapper wrap, Throwable ee) throws Exception {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[3];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = ee;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      try {
         try {
            wrap.resetContextClassLoader();
            wrap.popEnvironment();
            wrap.popRunAsIdentity();
            wrap.popCallerPrincipal();
            wrap.popSecurityContext();
            if (wrap.popMethodObject(this.beanInfo)) {
               this.beanManager.handleUncommittedLocalTransaction(wrap);
            }

            if (ee != null) {
               if (!EJBRuntimeUtils.isAppException(wrap.getMethodDescriptor().getMethod(), ee)) {
                  if (ee instanceof Exception) {
                     throw (Exception)ee;
                  }

                  throw EJBRuntimeUtils.asEJBException("EJB encountered System Exception: ", ee);
               }

               EJBRuntimeUtils.throwException(ee);
            }
         } finally {
            wrap.resumeCallersTransaction();
            wrap.unsetCIC();
         }
      } catch (Throwable var10) {
         if (var4 != null) {
            var4.th = var10;
            InstrumentationSupport.postProcess(var4);
         }

         throw var10;
      }

      if (var4 != null) {
         InstrumentationSupport.postProcess(var4);
      }

   }

   private boolean isTxRetry(int retryCount) {
      if (debugLogger.isDebugEnabled()) {
         debug(" RetryCount: " + retryCount + " return retry: " + (retryCount > 0));
      }

      return retryCount > 0;
   }

   protected void handleSystemException(InvocationWrapper wrap, Throwable ee) throws RemoteException {
      try {
         handleSystemException(wrap, this.beanInfo.usesBeanManagedTx(), ee);
      } catch (TransactionRolledbackException var5) {
         if (!this.implementsRemote) {
            EJBTransactionRolledbackException ete = null;
            if (var5.detail instanceof Exception) {
               ete = new EJBTransactionRolledbackException(var5.getMessage(), (Exception)var5.detail);
            } else {
               ete = new EJBTransactionRolledbackException(var5.getMessage(), var5);
            }

            throw EJBRuntimeUtils.asRemoteException(var5.getMessage(), ete);
         } else {
            throw var5;
         }
      }
   }

   public static void handleSystemException(InvocationWrapper wrap, boolean usesBeanManagedTx, Throwable ee) throws RemoteException {
      Transaction invokeTx = wrap.getInvokeTx();
      if (usesBeanManagedTx && TransactionService.getTransaction() != null) {
         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx.getStatus() == 0) {
               tx.rollback();
            }
         } catch (Exception var5) {
            EJBLogger.logStackTrace(var5);
            EJBLogger.logErrorOnRollback(var5);
         }

         throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", ee);
      } else if (invokeTx == null) {
         throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", ee);
      } else if (wrap.runningInOurTx()) {
         try {
            invokeTx.rollback();
         } catch (Exception var6) {
            EJBLogger.logErrorOnRollback(var6);
         }

         throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", ee);
      } else {
         try {
            if (invokeTx instanceof weblogic.transaction.Transaction) {
               ((weblogic.transaction.Transaction)invokeTx).setRollbackOnly(ee);
            } else {
               invokeTx.setRollbackOnly();
            }
         } catch (Exception var7) {
            EJBLogger.logErrorMarkingRollback(var7);
         }

         if (EJBRuntimeUtils.isSpecialSystemException(ee)) {
            throw EJBRuntimeUtils.asRemoteException("called setRollbackOnly", ee);
         } else {
            throw EJBRuntimeUtils.asTxRollbackException("EJB Exception: ", ee);
         }
      }
   }

   public Object getInstance() {
      return this.ejbHome;
   }

   private static void debug(String s) {
      debugLogger.debug("[BaseRemoteObject] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High");
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Before_Low");
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Preinvoke_After_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseRemoteObject.java", "weblogic.ejb.container.internal.BaseRemoteObject", "__WL_preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;Lweblogic/security/service/ContextHandler;)V", 119, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Preinvoke_After_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseRemoteObject.java", "weblogic.ejb.container.internal.BaseRemoteObject", "__WL_postInvokeTxRetry", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/Throwable;)Z", 307, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseRemoteObject.java", "weblogic.ejb.container.internal.BaseRemoteObject", "__WL_postInvokeCleanup", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/Throwable;)V", 494, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High};
      debugLogger = EJBDebugService.invokeLogger;
      currentInvocationWrapper = new ThreadLocalStack();
   }

   protected static class ThreadLocalObject {
      private final boolean isRemoteBean;
      private final BaseRemoteObject baseRemoteObject;

      protected ThreadLocalObject(boolean isRemote, BaseRemoteObject baseRemote) {
         this.isRemoteBean = isRemote;
         this.baseRemoteObject = baseRemote;
      }

      protected boolean isRemote() {
         return this.isRemoteBean;
      }

      protected BaseRemoteObject getBaseRemoteObject() {
         return this.baseRemoteObject;
      }
   }
}
