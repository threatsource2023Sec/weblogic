package weblogic.ejb.container.internal;

import javax.ejb.EJBException;
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
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;

public final class SessionLocalMethodInvoker {
   static final long serialVersionUID = -1204406049272180459L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.SessionLocalMethodInvoker");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   private SessionLocalMethodInvoker() {
   }

   public static Object invoke(Invokable inv, BaseLocalObject blo, InvocationWrapper wrap, Object[] args, int idx) throws Throwable {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var12.argsCapture) {
            var12.args = new Object[5];
            Object[] var10000 = var12.args;
            var10000[0] = inv;
            var10000[1] = blo;
            var10000[2] = wrap;
            var10000[3] = args;
            var10000[4] = InstrumentationSupport.convertToObject(idx);
         }

         InstrumentationSupport.createDynamicJoinPoint(var12);
         InstrumentationSupport.preProcess(var12);
         var12.resetPostBegin();
      }

      Object var29;
      try {
         Object result = null;
         Throwable ee = null;
         blo.__WL_preInvoke(wrap, new EJBContextHandler(wrap.getMethodDescriptor(), args));
         boolean doTxRetry = false;

         while(true) {
            WLEnterpriseBean bean = (WLEnterpriseBean)wrap.getBean();
            int oldState = bean.__WL_getMethodState();
            InvocationContextStack.push(wrap);
            AllowedMethodsHelper.pushBean(bean);
            EJBContextManager.pushEjbContext(bean.__WL_getEJBContext());
            bean.__WL_setMethodState(128);
            wrap.perhapsPushXPCWrappers();

            try {
               result = inv.__WL_invoke(bean, args, idx);
            } catch (Throwable var24) {
               ee = var24;
            } finally {
               wrap.popXPCWrappers();
               bean.__WL_setMethodState(oldState);
               EJBContextManager.popEjbContext();
               AllowedMethodsHelper.popBean();
               InvocationContextStack.pop();
            }

            try {
               doTxRetry = blo.__WL_postInvokeTxRetry(wrap, ee);
            } catch (Throwable var23) {
               ee = var23;
               doTxRetry = false;
            }

            if (!doTxRetry) {
               try {
                  blo.__WL_postInvokeCleanup(wrap, ee);
               } catch (Exception var26) {
                  if (var26 instanceof EJBException) {
                     throw var26;
                  }

                  inv.__WL_handleException(idx, var26);
                  BeanInfo bi = wrap.getMethodDescriptor().getClientViewDescriptor().getBeanInfo();
                  if (bi.getDeploymentInfo().getExceptionInfo(wrap.getMethodDescriptor().getMethod(), var26).isAppException()) {
                     throw var26;
                  }

                  throw new EJBException("Unexpected exception in " + wrap.getInvocationDetail() + PlatformConstants.EOL + StackTraceUtilsClient.throwable2StackTrace(var26), var26);
               }

               var29 = result;
               break;
            }
         }
      } catch (Throwable var27) {
         if (var12 != null) {
            var12.th = var27;
            var12.ret = null;
            InstrumentationSupport.postProcess(var12);
         }

         throw var27;
      }

      if (var12 != null) {
         var12.ret = var29;
         InstrumentationSupport.postProcess(var12);
      }

      return var29;
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "SessionLocalMethodInvoker.java", "weblogic.ejb.container.internal.SessionLocalMethodInvoker", "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;", 19, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{null, null, InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true), null, null})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Invoke_Wrapper_Around_Low};
   }
}
