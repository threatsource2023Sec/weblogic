package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import javax.ejb.AccessLocalException;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.TransactionRolledbackLocalException;
import javax.transaction.Transaction;
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
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.WLSessionBean;
import weblogic.security.service.ContextHandler;
import weblogic.transaction.RollbackException;
import weblogic.utils.StackTraceUtilsClient;

public abstract class BaseLocalObject {
   protected static final DebugLogger debugLogger;
   protected BeanInfo beanInfo;
   protected DeploymentInfo deploymentInfo;
   private BeanManager beanManager;
   private boolean isEJB30ClientView = true;
   static final long serialVersionUID = -6713064852456675069L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.BaseLocalObject");
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
      return "BaseLocalObject for EJB: '" + (this.beanInfo != null ? this.beanInfo.getEJBName() : "") + "'";
   }

   void setIsEJB30ClientView(boolean is30View) {
      this.isEJB30ClientView = is30View;
   }

   public void setBeanManager(BeanManager bm) {
      this.beanManager = bm;
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public void setBeanInfo(BeanInfo bi) {
      this.beanInfo = bi;
      this.deploymentInfo = this.beanInfo.getDeploymentInfo();
   }

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws EJBException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[3];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = ch;
         }

         var3.resetPostBegin();
      }

      try {
         this.preInvoke(wrap, ch, false, true);
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            InstrumentationSupport.process(var3);
         }

         throw var5;
      }

      if (var3 != null) {
         InstrumentationSupport.process(var3);
      }

   }

   protected void preInvoke(InvocationWrapper wrap, ContextHandler ch, boolean preInvokeLite, boolean enforceTxPolicy) throws EJBException {
      if (debugLogger.isDebugEnabled()) {
         debug("preInvoke called with: " + wrap + " on local interface of EJB: " + this.beanInfo.getEJBName());
      }

      wrap.setCIC(this.beanInfo);
      if (enforceTxPolicy) {
         try {
            wrap.enforceTransactionPolicy();
         } catch (InternalException var8) {
            wrap.unsetCIC();
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var8);
         }
      }

      if (!preInvokeLite) {
         wrap.pushEnvironment(this.beanManager.getEnvironmentContext());
         wrap.pushCallerPrincipal();
      }

      wrap.pushMethodObject(this.beanInfo);

      try {
         Object bean = this.getBeanInstance(wrap);
         wrap.setBean(bean);
         ((EJBContextHandler)ch).setEjb(bean);
         if (this.isEJB30ClientView) {
            wrap.checkMethodPermissionsBusiness(ch);
         } else {
            wrap.checkMethodPermissionsLocal(ch);
         }

         wrap.pushSecurityContext(ch);
         if (!preInvokeLite) {
            wrap.pushRunAsIdentity();
         }

      } catch (AccessLocalException | EJBAccessException var9) {
         this.handleAccessExceptions(var9, wrap);
         throw new AssertionError("Should never be reached");
      } catch (RuntimeException var10) {
         if (!preInvokeLite) {
            wrap.popEnvironment();
            wrap.popRunAsIdentity();
            wrap.popCallerPrincipal();
         }

         wrap.popMethodObject(this.beanInfo);
         wrap.popSecurityContext();

         try {
            wrap.resumeCallersTransaction();
         } catch (InternalException var7) {
         }

         wrap.unsetCIC();
         throw var10;
      }
   }

   private Object getBeanInstance(InvocationWrapper wrap) {
      try {
         try {
            Object eb = this.beanManager.preInvoke(wrap);
            if (debugLogger.isDebugEnabled()) {
               debug("Manager returned a bean:" + eb);
            }

            return eb;
         } catch (InternalException var4) {
            if (!this.isEJB30ClientView) {
               if (var4.detail instanceof NoSuchEJBException) {
                  Exception ee = new NoSuchObjectLocalException(var4.getMessage());
                  EJBRuntimeUtils.throwInternalException(var4.getMessage(), ee);
               } else if (var4.detail instanceof ConcurrentAccessException) {
                  throw new InternalException(var4.getMessage());
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

   private void handleAccessExceptions(EJBException ex, InvocationWrapper wrap) throws EJBException {
      if (debugLogger.isDebugEnabled()) {
         debug("Method permission check failed. Do postInvoke sequence. " + ex);
      }

      Throwable throwable = null;

      try {
         this.postInvoke1(0, wrap, ex);
      } catch (Throwable var5) {
         throwable = var5;
      }

      try {
         this.__WL_postInvokeCleanup(wrap, throwable);
      } catch (Exception var6) {
         if (var6 instanceof EJBException) {
            throw (EJBException)var6;
         }

         throw EJBRuntimeUtils.asEJBException("EJB Exception after method permission failure: ", var6);
      }

      throw ex;
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
         debug("postInvoke1 called with txRetryCount: " + retryCount + " wrap: " + wrap + " Exception: " + ee + " on local interface of EJB: " + this.beanInfo.getEJBName());
         if (ee != null) {
            ((Throwable)ee).printStackTrace();
         }
      }

      Transaction invokeTx = wrap.getInvokeTx();
      Transaction callerTx = wrap.getCallerTx();
      MethodDescriptor md = wrap.getMethodDescriptor();
      Method m = md.getMethod();
      boolean systemExceptionOccurred = false;
      if (ee != null && !this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isAppException() && !(ee instanceof AccessLocalException) && !(ee instanceof EJBAccessException)) {
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
         this.beanManager.destroyInstance(wrap, (Throwable)ee);
         this.handleSystemException(wrap, (Throwable)ee);
         throw new AssertionError("Should never be reached");
      } else if (!(ee instanceof AccessLocalException) && !(ee instanceof EJBAccessException)) {
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
                  throw EJBRuntimeUtils.asEJBException("Transaction Rolledback.", th);
               }

               return this.isTxRetry(retryCount);
            }

            try {
               if (ee != null && this.isEJB30ClientView && this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isRollback()) {
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

               throw EJBRuntimeUtils.asEJBException("Error committing transaction:", var14);
            }
         } else if (wrap.runningInCallerTx() && ee != null && this.isEJB30ClientView && this.deploymentInfo.getExceptionInfo(m, (Throwable)ee).isRollback()) {
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
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[3];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = wrap;
            var10000[2] = ee;
         }

         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.preProcess(var3);
         var3.resetPostBegin();
      }

      try {
         this.__WL_postInvokeCleanup(wrap, ee, true);
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            InstrumentationSupport.postProcess(var3);
         }

         throw var5;
      }

      if (var3 != null) {
         InstrumentationSupport.postProcess(var3);
      }

   }

   public void __WL_postInvokeCleanup(InvocationWrapper wrap, Throwable ee, boolean resumeCallerTransaction) throws Exception {
      try {
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
         if (resumeCallerTransaction) {
            wrap.resumeCallersTransaction();
         }

         wrap.unsetCIC();
      }

   }

   private boolean isTxRetry(int retryCount) {
      if (debugLogger.isDebugEnabled()) {
         debug(" RetryCount: " + retryCount + " return retry: " + (retryCount > 0));
      }

      return retryCount > 0;
   }

   protected void handleSystemException(InvocationWrapper wrap, Throwable ee) {
      try {
         handleSystemException(wrap, this.beanInfo.usesBeanManagedTx(), ee);
      } catch (TransactionRolledbackLocalException var6) {
         if (this.isEJB30ClientView) {
            Throwable eCause = var6.getCause();
            if (eCause != null && !(eCause instanceof Exception)) {
               EJBTransactionRolledbackException etre = new EJBTransactionRolledbackException(var6.getMessage());
               etre.initCause(eCause);
               throw etre;
            } else {
               throw new EJBTransactionRolledbackException(var6.getMessage(), (Exception)eCause);
            }
         } else {
            throw var6;
         }
      }
   }

   public static void handleSystemException(InvocationWrapper wrap, boolean usesBeanManagedTx, Throwable ee) throws EJBException {
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

         if (ee instanceof AccessLocalException) {
            throw (AccessLocalException)ee;
         } else {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", ee);
         }
      } else if (invokeTx == null) {
         if (ee instanceof AccessLocalException) {
            throw (AccessLocalException)ee;
         } else {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", ee);
         }
      } else if (wrap.runningInOurTx()) {
         try {
            invokeTx.rollback();
         } catch (Exception var6) {
            EJBLogger.logStackTrace(var6);
            EJBLogger.logErrorOnRollback(var6);
         }

         if (ee instanceof AccessLocalException) {
            throw (AccessLocalException)ee;
         } else {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", ee);
         }
      } else {
         try {
            invokeTx.setRollbackOnly();
         } catch (Exception var7) {
            EJBLogger.logErrorMarkingRollback(var7);
         }

         if (EJBRuntimeUtils.isSpecialSystemException(ee)) {
            throw EJBRuntimeUtils.asEJBException("called setRollbackOnly", ee);
         } else {
            throw EJBRuntimeUtils.asTxRollbackLocalException("EJB Exception: ", ee);
         }
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[BaseLocalObject] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High");
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Before_Low");
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Preinvoke_After_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseLocalObject.java", "weblogic.ejb.container.internal.BaseLocalObject", "__WL_preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;Lweblogic/security/service/ContextHandler;)V", 69, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Preinvoke_After_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseLocalObject.java", "weblogic.ejb.container.internal.BaseLocalObject", "__WL_postInvokeTxRetry", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/Throwable;)Z", 195, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "BaseLocalObject.java", "weblogic.ejb.container.internal.BaseLocalObject", "__WL_postInvokeCleanup", "(Lweblogic/ejb/container/internal/InvocationWrapper;Ljava/lang/Throwable;)V", 364, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High};
      debugLogger = EJBDebugService.invokeLogger;
   }
}
