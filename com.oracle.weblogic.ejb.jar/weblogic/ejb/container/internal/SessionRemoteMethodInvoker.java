package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.Invokable;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;

public final class SessionRemoteMethodInvoker {
   static final long serialVersionUID = -840151919662818736L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.SessionRemoteMethodInvoker");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   private SessionRemoteMethodInvoker() {
   }

   public static Object invoke(Invokable inv, BaseRemoteObject bro, InvocationWrapper wrap, Object[] args, int idx) throws Throwable {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[5];
            Object[] var10000 = var6.args;
            var10000[0] = inv;
            var10000[1] = bro;
            var10000[2] = wrap;
            var10000[3] = args;
            var10000[4] = InstrumentationSupport.convertToObject(idx);
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      Object var11;
      try {
         try {
            var11 = invokeInternal(inv, bro, wrap, args, idx);
         } catch (Throwable var9) {
            if (bro.canThrowRemoteEJBInvokeException(wrap)) {
               if (var9 instanceof RemoteEJBInvokeException) {
                  throw var9;
               }

               throw new RemoteEJBInvokeException(var9.getMessage(), var9);
            }

            if (var9 instanceof RemoteEJBInvokeException) {
               throw var9.getCause();
            }

            throw var9;
         }
      } catch (Throwable var10) {
         if (var6 != null) {
            var6.th = var10;
            var6.ret = null;
            InstrumentationSupport.postProcess(var6);
         }

         throw var10;
      }

      if (var6 != null) {
         var6.ret = var11;
         InstrumentationSupport.postProcess(var6);
      }

      return var11;
   }

   public static Object invokeInternal(Invokable inv, BaseRemoteObject bro, InvocationWrapper wrap, Object[] args, int idx) throws Throwable {
      Object result = null;
      Throwable ee = null;
      bro.__WL_preInvoke(wrap, new EJBContextHandler(wrap.getMethodDescriptor(), args));
      boolean doTxRetry = false;

      do {
         WLEnterpriseBean bean = (WLEnterpriseBean)wrap.getBean();
         int oldState = bean.__WL_getMethodState();
         InvocationContextStack.push(wrap);
         AllowedMethodsHelper.pushBean(bean);
         EJBContextManager.pushEjbContext(bean.__WL_getEJBContext());
         bean.__WL_setMethodState(128);
         wrap.perhapsPushXPCWrappers();

         try {
            result = inv.__WL_invoke(bean, args, idx);
         } catch (Throwable var17) {
            ee = var17;
         } finally {
            wrap.popXPCWrappers();
            bean.__WL_setMethodState(oldState);
            EJBContextManager.popEjbContext();
            AllowedMethodsHelper.popBean();
            InvocationContextStack.pop();
         }

         try {
            doTxRetry = bro.__WL_postInvokeTxRetry(wrap, ee);
         } catch (Throwable var16) {
            ee = var16;
            doTxRetry = false;
         }
      } while(doTxRetry);

      try {
         bro.__WL_postInvokeCleanup(wrap, ee);
         return result;
      } catch (Exception var19) {
         if (var19 instanceof RemoteException) {
            throw var19;
         } else {
            inv.__WL_handleException(idx, var19);
            BeanInfo bi = wrap.getMethodDescriptor().getClientViewDescriptor().getBeanInfo();
            if (bi.getDeploymentInfo().getExceptionInfo(wrap.getMethodDescriptor().getMethod(), var19).isAppException()) {
               throw var19;
            } else {
               throw new UnexpectedException("Unexpected exception in " + wrap.getInvocationDetail() + PlatformConstants.EOL + StackTraceUtilsClient.throwable2StackTrace(var19), var19);
            }
         }
      }
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "SessionRemoteMethodInvoker.java", "weblogic.ejb.container.internal.SessionRemoteMethodInvoker", "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;", 21, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{null, null, InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null, null})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low};
   }
}
