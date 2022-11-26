package weblogic.ejb.container.internal;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.internal.EJBMetaDataImpl;
import weblogic.logging.Loggable;

public abstract class StatelessEJBHome extends BaseEJBHome {
   private StatelessEJBObject eo;
   static final long serialVersionUID = 2743850200177791670L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.StatelessEJBHome");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public StatelessEJBHome(Class eoClass) {
      super(eoClass);
   }

   public void activate() throws WLDeploymentException {
      super.activate();
      if (this.beanInfo.hasDeclaredRemoteHome()) {
         try {
            this.eo = (StatelessEJBObject)this.eoClass.newInstance();
            this.eo.setEJBHome(this);
            this.eo.setBeanManager(this.getBeanManager());
            this.eo.setBeanInfo(this.getBeanInfo());
         } catch (IllegalAccessException | InstantiationException var2) {
            throw new AssertionError(var2);
         }
      }
   }

   protected EJBMetaData getEJBMetaDataInstance() {
      return new EJBMetaDataImpl(this, this.beanInfo.getHomeInterfaceClass(), this.beanInfo.getRemoteInterfaceClass(), (Class)null, true, true);
   }

   public EJBObject create(MethodDescriptor md) throws Exception {
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

      EJBObject var7;
      try {
         if (!InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(EJBContextHandler.EMPTY)) {
            Loggable l = EJBLogger.loginsufficientPermissionToUserLoggable(SecurityHelper.getCurrentPrincipal().getName(), "create");
            SecurityException se = new SecurityException(l.getMessageText());
            throw new AccessException(se.getMessage(), se);
         }

         var7 = this.allocateEO();
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

   public void remove(MethodDescriptor md, Handle h) throws RemoteException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[3];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = h;
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.preProcess(var5);
         var5.resetPostBegin();
      }

      try {
         InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, new Object[]{h}));

         try {
            this.validateHandleFromHome(h);
         } finally {
            this.postHomeInvoke(wrap, (Throwable)null);
         }
      } catch (Throwable var11) {
         if (var5 != null) {
            var5.th = var11;
            InstrumentationSupport.postProcess(var5);
         }

         throw var11;
      }

      if (var5 != null) {
         InstrumentationSupport.postProcess(var5);
      }

   }

   public void remove(MethodDescriptor var1, Object var2) throws RemoveException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
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
         throw new RemoveException(EJBLogger.loginvalidRemoveCallLoggable().getMessageText());
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            InstrumentationSupport.postProcess(var3);
         }

         throw var5;
      }
   }

   public EJBObject allocateEO(Object pk) {
      throw new AssertionError("No pk for stateless beans");
   }

   public EJBObject allocateEO() {
      return this.eo;
   }

   public void undeploy() {
      if (this.eo != null) {
         this.unexport(this.eo);
      }

      super.undeploy();
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Remove_Around_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Create_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessEJBHome.java", "weblogic.ejb.container.internal.StatelessEJBHome", "create", "(Lweblogic/ejb/container/internal/MethodDescriptor;)Ljavax/ejb/EJBObject;", 52, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Create_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Create_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessEJBHome.java", "weblogic.ejb.container.internal.StatelessEJBHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljavax/ejb/Handle;)V", 63, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatelessEJBHome.java", "weblogic.ejb.container.internal.StatelessEJBHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)V", 74, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
   }
}
