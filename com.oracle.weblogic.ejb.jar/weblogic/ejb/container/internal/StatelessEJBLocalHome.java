package weblogic.ejb.container.internal;

import javax.ejb.AccessLocalException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;

public abstract class StatelessEJBLocalHome extends BaseEJBLocalHome {
   private StatelessEJBLocalObject elo;
   static final long serialVersionUID = -6362644980002060184L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.StatelessEJBLocalHome");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public StatelessEJBLocalHome(Class eloClass) {
      super(eloClass);
   }

   public void setup(BeanInfo bi, BeanManager bm) throws WLDeploymentException {
      super.setup(bi, bm);
      if (this.beanInfo.hasDeclaredLocalHome()) {
         try {
            this.elo = (StatelessEJBLocalObject)this.eloClass.newInstance();
            this.elo.setBeanInfo(this.getBeanInfo());
            this.elo.setEJBLocalHome(this);
            this.elo.setBeanManager(this.getBeanManager());
            this.elo.setIsEJB30ClientView(false);
         } catch (IllegalAccessException | InstantiationException var4) {
            throw new AssertionError(var4);
         }
      }
   }

   public BaseEJBLocalObjectIntf create(MethodDescriptor md) throws EJBException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[2];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = md;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      BaseEJBLocalObjectIntf var7;
      try {
         if (!InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(EJBContextHandler.EMPTY)) {
            Loggable l = EJBLogger.loginsufficientPermissionToUserLoggable(SecurityHelper.getCurrentPrincipal().getName(), "create");
            SecurityException se = new SecurityException(l.getMessageText());
            throw new AccessLocalException(se.getMessage(), se);
         }

         var7 = this.allocateELO();
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            var4.ret = null;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         var4.ret = var7;
         InstrumentationSupport.postProcess(var4);
      }

      return var7;
   }

   public void remove(MethodDescriptor var1, Object var2) throws RemoveException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[3];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = var1;
            var10000[2] = var2;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      try {
         Loggable l = EJBLogger.loginvalidRemoveCallLoggable();
         throw new RemoveException(l.getMessageText());
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }
   }

   public BaseEJBLocalObjectIntf allocateELO(Object pk) {
      throw new AssertionError("No pk for stateless beans");
   }

   public BaseEJBLocalObjectIntf allocateELO() {
      return this.elo;
   }

   public void undeploy() {
      super.undeploy();
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Remove_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Create_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessEJBLocalHome.java", "weblogic.ejb.container.internal.StatelessEJBLocalHome", "create", "(Lweblogic/ejb/container/internal/MethodDescriptor;)Lweblogic/ejb/container/interfaces/BaseEJBLocalObjectIntf;", 43, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Create_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessEJBLocalHome.java", "weblogic.ejb.container.internal.StatelessEJBLocalHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)V", 54, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
   }
}
