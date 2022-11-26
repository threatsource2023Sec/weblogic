package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import javax.ejb.RemoveException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;

public abstract class StatefulEJBLocalHome extends BaseEJBLocalHome {
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = -4426624450052789611L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.StatefulEJBLocalHome");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public StatefulEJBLocalHome(Class eloClass) {
      super(eloClass);
   }

   protected BaseEJBLocalObjectIntf create(MethodDescriptor md, Method createMethod, Object[] args) throws Exception {
      Object th;
      try {
         EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
         InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, args));
         Throwable ee = null;

         try {
            if (!$assertionsDisabled && wrap.getInvokeTx() != null) {
               throw new AssertionError();
            }

            th = (BaseEJBLocalObjectIntf)this.getBeanManager().localCreate(wrap, createMethod, (Method)null, args);
         } catch (InternalException var17) {
            th = var17;
            ee = var17.detail;
            if (this.deploymentInfo.getExceptionInfo(createMethod, ee).isAppException()) {
               throw (Exception)ee;
            }

            this.handleSystemException(wrap, var17);
            throw new AssertionError("Should never have reached here");
         } catch (Throwable var18) {
            th = var18;
            ee = var18;
            this.handleSystemException(wrap, var18);
            throw new AssertionError("Should never have reached here");
         } finally {
            this.postHomeInvoke(wrap, ee);
         }
      } finally {
         EJBRuntimeUtils.popEnvironment();
      }

      return (BaseEJBLocalObjectIntf)th;
   }

   public void remove(MethodDescriptor var1, Object var2) throws RemoveException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[3];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = var1;
            var10000[2] = var2;
         }

         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.preProcess(var3);
         var3.resetPostBegin();
      }

      try {
         throw new RemoveException("Cannot remove stateful session beans using EJBLocalHome.remove(Object primaryKey)");
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            InstrumentationSupport.postProcess(var3);
         }

         throw var5;
      }
   }

   public BaseEJBLocalObjectIntf allocateELO(Object pk) {
      StatefulEJBLocalObject elo = (StatefulEJBLocalObject)this.allocateELO();
      elo.setPrimaryKey(pk);
      elo.setBeanManager(this.getBeanManager());
      elo.setBeanInfo(this.getBeanInfo());
      elo.setIsEJB30ClientView(false);
      return elo;
   }

   public BaseEJBLocalObjectIntf allocateELO() {
      try {
         StatefulEJBLocalObject elo = (StatefulEJBLocalObject)this.eloClass.newInstance();
         elo.setBeanInfo(this.getBeanInfo());
         elo.setEJBLocalHome(this);
         elo.setBeanManager(this.getBeanManager());
         elo.setIsEJB30ClientView(false);
         return elo;
      } catch (InstantiationException var2) {
         throw new AssertionError(var2);
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      }
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Remove_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulEJBLocalHome.java", "weblogic.ejb.container.internal.StatefulEJBLocalHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)V", 56, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      $assertionsDisabled = !StatefulEJBLocalHome.class.desiredAssertionStatus();
   }
}
