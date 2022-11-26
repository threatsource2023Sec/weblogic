package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import javax.ejb.EJBException;
import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.transaction.xa.XAResource;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.Invokable;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.manager.MessageDrivenManager;
import weblogic.logging.Loggable;
import weblogic.security.service.ContextHandler;
import weblogic.transaction.Transaction;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;

public class MessageDrivenLocalObject extends BaseLocalObject implements MessageEndpoint {
   private static final int ST_INIT = 1;
   private static final int ST_PREINVOKE_COMPLETE = 2;
   private static final int ST_BUSINESS = 4;
   private static final int ST_BEFOREDELIVERY = 8;
   private static final int ST_BUSINESS_DELIVERY = 16;
   private static final int ST_AFTERDELIVERY = 32;
   private static final int ST_RELEASE = 64;
   private int state = 1;
   private final XAResource xaResource;
   private ClassLoader clSave;
   private boolean isDeliveryTransacted;
   private Throwable theException;
   private InvocationWrapper invWrap;
   static final long serialVersionUID = 1109147093959302577L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.MessageDrivenLocalObject");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public MessageDrivenLocalObject(XAResource xaResource) {
      this.xaResource = xaResource;
   }

   private void __WL_preInvoke(MethodDescriptor md, ContextHandler ch, boolean isBeforeDeliveryInvoked) throws EJBException {
      try {
         this.invWrap = InvocationWrapper.newInstance(md);
         if (!isBeforeDeliveryInvoked) {
            super.__WL_preInvoke(this.invWrap, ch);
         } else {
            this.invWrap.enforceTransactionPolicy();
         }
      } catch (Throwable var7) {
         this.theException = var7;
         throw EJBRuntimeUtils.asEJBException("Exception in preInvoke.", var7);
      }

      this.isDeliveryTransacted = this.invWrap.getInvokeTx() != null;
      if (this.xaResource != null && this.invWrap.getCallerTx() == null && this.isDeliveryTransacted) {
         try {
            this.invWrap.getInvokeTx().enlistResource(this.xaResource);
         } catch (Exception var6) {
            this.theException = var6;
            EJBException ee = new EJBException(var6);
            ee.initCause(var6);
            throw ee;
         }
      }

   }

   private void setContextClassloader() {
      if (this.clSave == null) {
         Thread currentThread = Thread.currentThread();
         this.clSave = currentThread.getContextClassLoader();
         currentThread.setContextClassLoader(this.beanInfo.getModuleClassLoader());
      }

   }

   private void restoreContextClassloader() {
      if (this.clSave != null) {
         Thread.currentThread().setContextClassLoader(this.clSave);
         this.clSave = null;
      }

   }

   private Throwable postInvoke(InvocationWrapper wrap, Throwable ee) throws Exception {
      try {
         this.postInvoke1(0, wrap, ee);
         return ee;
      } catch (Throwable var4) {
         return var4;
      }
   }

   public Object invoke(Invokable inv, MethodDescriptor md, Object[] args, int idx, String methName) throws Throwable {
      boolean wasBeforeDeliveryInvoked = this.state == 8;
      boolean skipDoingInBeforeAfterMethodPair = wasBeforeDeliveryInvoked && (this.xaResource == null || !this.isDeliveryTransacted);
      if (!wasBeforeDeliveryInvoked) {
         this.__WL_setNextState(1);
         this.__WL_preInvoke(md, new EJBContextHandler(md, args), false);
         this.__WL_setNextState(2);
         this.__WL_setNextState(4);
      } else {
         if (skipDoingInBeforeAfterMethodPair) {
            this.__WL_preInvoke(md, new EJBContextHandler(md, args), false);
         } else {
            try {
               super.preInvoke(this.invWrap, new EJBContextHandler(md, args), false, false);
            } catch (Throwable var19) {
               throw EJBRuntimeUtils.asEJBException("Exception in preInvoke.", var19);
            }
         }

         this.__WL_setNextState(16);
      }

      boolean needSetCtxClassloader = this.clSave == null;
      if (needSetCtxClassloader) {
         this.setContextClassloader();
      }

      InvocationContextStack.push(this.invWrap);
      EJBContextManager.pushEjbContext(((MessageDrivenManager)this.getBeanManager()).getMessageDrivenContext());
      Object result = null;

      try {
         result = inv.__WL_invoke(this.invWrap.getBean(), args, idx);
      } catch (Throwable var18) {
         this.theException = var18;
      } finally {
         EJBContextManager.popEjbContext();
         InvocationContextStack.pop();
         if (needSetCtxClassloader) {
            this.restoreContextClassloader();
         }

      }

      try {
         if (wasBeforeDeliveryInvoked && !skipDoingInBeforeAfterMethodPair) {
            super.__WL_postInvokeCleanup(this.invWrap, this.theException, false);
         } else {
            try {
               Throwable th = this.postInvoke(this.invWrap, this.theException);
               this.__WL_postInvokeCleanup(this.invWrap, th, true);
            } catch (Exception var17) {
               this.theException = var17;
               throw var17;
            }
         }

         return result;
      } catch (Throwable var21) {
         if (!(var21 instanceof Exception)) {
            throw EJBRuntimeUtils.asEJBException("EJB Application Exception:", var21);
         } else {
            Exception e = (Exception)var21;
            if (e instanceof EJBException) {
               throw e;
            } else {
               inv.__WL_handleException(idx, e);
               throw new EJBException("Unexpected exception in " + methName + ":" + PlatformConstants.EOL + StackTraceUtilsClient.throwable2StackTrace(e), e);
            }
         }
      }
   }

   public void beforeDelivery(Method m) throws NoSuchMethodException, ResourceException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[2];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = m;
         }

         var5.resetPostBegin();
      }

      try {
         this.__WL_setNextState(1);
         this.isDeliveryTransacted = ((MessageDrivenBeanInfo)this.beanInfo).isDeliveryTransacted(m);
         this.setContextClassloader();
         boolean skipDoingInBeforeAfterMethodPair = this.xaResource == null || !this.isDeliveryTransacted;
         if (skipDoingInBeforeAfterMethodPair) {
            if (debugLogger.isDebugEnabled()) {
               debug("beforeDelivery(...) call has no effect");
            }
         } else {
            MethodDescriptor md = ((MessageDrivenBeanInfo)this.beanInfo).getMDBMethodDescriptor(m);
            Object[] params = new Object[md.getMethodInfo().getMethodParams().length];
            this.__WL_preInvoke(md, new EJBContextHandler(md, params), true);
         }

         this.__WL_setNextState(2);
         this.__WL_setNextState(8);
      } catch (Throwable var7) {
         if (var5 != null) {
            var5.th = var7;
            InstrumentationSupport.process(var5);
         }

         throw var7;
      }

      if (var5 != null) {
         InstrumentationSupport.process(var5);
      }

   }

   public void afterDelivery() throws ResourceException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[1];
            var9.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var9);
         InstrumentationSupport.process(var9);
         var9.resetPostBegin();
      }

      this.restoreContextClassloader();
      this.__WL_setNextState(32);
      boolean skipDoingInBeforeAfterMethodPair = this.xaResource == null || !this.isDeliveryTransacted;
      if (skipDoingInBeforeAfterMethodPair) {
         if (debugLogger.isDebugEnabled()) {
            debug("afterDelivery() call has no effect");
         }
      } else {
         boolean var15 = false;

         Loggable log;
         EJBException ee;
         try {
            var15 = true;
            Throwable th = this.postInvoke(this.invWrap, this.theException);
            if (th != null) {
               throw th;
            }

            var15 = false;
         } catch (EJBException var18) {
            throw var18;
         } catch (Throwable var19) {
            log = EJBLogger.logExceptionAferDeliveryLoggable(StackTraceUtilsClient.throwable2StackTrace(var19));
            ee = EJBRuntimeUtils.asEJBException(log.getMessage(), var19);
            ee.initCause(var19);
            throw ee;
         } finally {
            if (var15) {
               try {
                  this.invWrap.resumeCallersTransaction();
               } catch (Exception var16) {
                  weblogic.i18n.logging.Loggable log = EJBLogger.logErrorResumingTxLoggable(var16);
                  EJBException ee = EJBRuntimeUtils.asEJBException(log.getMessage(), var16);
                  ee.initCause(var16);
                  throw ee;
               }
            }
         }

         try {
            this.invWrap.resumeCallersTransaction();
         } catch (Exception var17) {
            log = EJBLogger.logErrorResumingTxLoggable(var17);
            ee = EJBRuntimeUtils.asEJBException(log.getMessage(), var17);
            ee.initCause(var17);
            throw ee;
         }
      }

   }

   public void release() {
      this.restoreContextClassloader();
      boolean isGoThruBeforeAfterMethodPair = (this.state & 24) != 0;
      boolean skipDoingInBeforeAfterMethodPair = this.xaResource == null || !this.isDeliveryTransacted;
      if (isGoThruBeforeAfterMethodPair && !skipDoingInBeforeAfterMethodPair) {
         if (this.invWrap.runningInOurTx()) {
            Transaction tx = this.invWrap.getInvokeTx();
            if (!TransactionService.isRolledback(tx)) {
               weblogic.i18n.logging.Loggable l = EJBLogger.logTransRolledbackAsReleaseCalledBetweenBeforeAndAfterDeliveryLoggable(this.beanInfo.getEJBName());
               tx.setRollbackOnly(l.getMessage());
            }
         }

         try {
            Throwable th = this.postInvoke(this.invWrap, this.theException);
            if (th != null) {
               throw th;
            }
         } catch (Throwable var13) {
            if (debugLogger.isDebugEnabled()) {
               debug("Error during release(): " + var13);
            }
         } finally {
            try {
               this.invWrap.resumeCallersTransaction();
            } catch (Exception var12) {
            }

         }
      }

      this.__WL_setNextState(64);
   }

   private void __WL_setNextState(int newState) throws IllegalStateException {
      if (this.allowedState(newState)) {
         this.state = newState;
      } else {
         weblogic.i18n.logging.Loggable l = EJBLogger.logIllegalStateTransactionLoggable(this.state + "", newState + "");
         throw new IllegalStateException(l.getMessage());
      }
   }

   private boolean allowedState(int newState) {
      if ((newState & this.state) != 0) {
         return true;
      } else {
         int test;
         switch (newState) {
            case 1:
               test = 37;
               break;
            case 2:
               test = 1;
               break;
            case 4:
               test = 2;
               break;
            case 8:
               test = 2;
               break;
            case 16:
               test = 8;
               break;
            case 32:
               test = this.getAllowedAfterDelivery();
               break;
            case 64:
               test = 125;
               break;
            default:
               throw new IllegalArgumentException("Unknown state value: " + newState);
         }

         return (test & this.state) != 0;
      }
   }

   private int getAllowedAfterDelivery() {
      String raVersion = ((MessageDrivenBeanInfo)this.beanInfo).getResourceAdapterVersion();
      return "1.5".equals(raVersion) ? 16 : 24;
   }

   private static void debug(String s) {
      debugLogger.debug("[MessageDrivenLocalObject] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Preinvoke_After_Low");
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Before_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "MessageDrivenLocalObject.java", "weblogic.ejb.container.internal.MessageDrivenLocalObject", "beforeDelivery", "(Ljava/lang/reflect/Method;)V", 165, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Preinvoke_After_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.ToStringRenderer", false, true), (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("method", (String)null, false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Preinvoke_After_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "MessageDrivenLocalObject.java", "weblogic.ejb.container.internal.MessageDrivenLocalObject", "afterDelivery", "()V", 190, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Before_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("this", "weblogic.diagnostics.instrumentation.gathering.ToStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Before_Low};
   }
}
