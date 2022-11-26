package weblogic.ejb.container.internal;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.NoSuchEJBException;
import javax.ejb.RemoveException;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.StatefulEJBObjectIntf;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.replication.ReplicatedBeanManager;
import weblogic.ejb.container.replication.ReplicatedHome;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.internal.EJBMetaDataImpl;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.collections.SoftHashMap;

public abstract class StatefulEJBHome extends BaseEJBHome implements ReplicatedHome {
   private boolean isInMemoryReplication = false;
   private boolean isNoObjectActivation = false;
   private EJBActivator ejbActivator = new EJBActivator(this);
   private Map eoMap = Collections.synchronizedMap(new SoftHashMap());
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 6783109651085910516L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.StatefulEJBHome");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public StatefulEJBHome(Class eoClass) {
      super(eoClass);
   }

   public void setup(BeanInfo bi, BeanManager bm) throws WLDeploymentException {
      super.setup(bi, bm);
      this.isInMemoryReplication = ((StatefulSessionBeanInfo)bi).isReplicated();
      if (this.eoClass != null) {
         this.isNoObjectActivation = !Activatable.class.isAssignableFrom(this.eoClass);
      }

   }

   protected EJBMetaData getEJBMetaDataInstance() {
      return new EJBMetaDataImpl(this, this.beanInfo.getHomeInterfaceClass(), this.beanInfo.getRemoteInterfaceClass(), (Class)null, true, false);
   }

   protected EJBObject create(MethodDescriptor md, Method createMethod, Object[] args) throws Exception {
      EJBObject var7;
      try {
         EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
         InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, args));
         Throwable ee = null;

         try {
            if (!$assertionsDisabled && wrap.getInvokeTx() != null) {
               throw new AssertionError();
            }

            BeanManager manager = this.getBeanManager();
            var7 = manager.remoteCreate(wrap, createMethod, (Method)null, args);
         } catch (InternalException var18) {
            ee = var18.detail;
            if (this.deploymentInfo.getExceptionInfo(createMethod, ee).isAppException()) {
               throw (Exception)ee;
            }

            this.handleSystemException(wrap, var18);
            throw new AssertionError("Should never have reached here");
         } catch (Throwable var19) {
            ee = var19;
            this.handleSystemException(wrap, var19);
            throw new AssertionError("Should never reach here");
         } finally {
            this.postHomeInvoke(wrap, ee);
         }
      } finally {
         EJBRuntimeUtils.popEnvironment();
      }

      return var7;
   }

   public void remove(MethodDescriptor md, Handle h) throws RemoteException, RemoveException {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[3];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = md;
            var10000[2] = h;
         }

         InstrumentationSupport.createDynamicJoinPoint(var10);
         InstrumentationSupport.preProcess(var10);
         var10.resetPostBegin();
      }

      try {
         try {
            EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
            this.validateHandleFromHome(h);
            StatefulEJBObjectIntf eo = (StatefulEJBObjectIntf)PortableRemoteObject.narrow(h.getEJBObject(), StatefulEJBObjectIntf.class);
            Object pk = eo.getPK();
            InvocationWrapper wrap = this.preHomeInvoke(md, new EJBContextHandler(md, new Object[]{h}));
            Throwable ee = null;

            try {
               wrap.setPrimaryKey(pk);

               try {
                  this.getBeanManager().remove(wrap);
               } catch (InternalException var29) {
                  if (!(var29.getCause() instanceof NoSuchEJBException) && !(var29.getCause() instanceof ConcurrentAccessException)) {
                     throw var29;
                  }

                  throw new InternalException(var29.getMessage(), var29.getCause().getCause());
               }
            } catch (InternalException var30) {
               ee = var30.detail;
               if (this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isAppException()) {
                  if (ee instanceof RemoveException) {
                     throw (RemoveException)ee;
                  }

                  throw new AssertionError("Invalid Exception thrown from remove: " + StackTraceUtils.throwable2StackTrace(ee));
               }

               this.handleSystemException(wrap, var30);
               throw new AssertionError("Should never have reached here");
            } catch (Throwable var31) {
               ee = var31;
               this.handleSystemException(wrap, var31);
               throw new AssertionError("Should never reach here");
            } finally {
               if (this.getIsNoObjectActivation() || this.getIsInMemoryReplication()) {
                  this.releaseEO(pk);
                  if (this instanceof StatefulEJBHomeImpl) {
                     ((StatefulEJBHomeImpl)this).releaseBOs(pk);
                  }
               }

               this.postHomeInvoke(wrap, ee);
            }
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }
      } catch (Throwable var34) {
         if (var10 != null) {
            var10.th = var34;
            InstrumentationSupport.postProcess(var10);
         }

         throw var34;
      }

      if (var10 != null) {
         InstrumentationSupport.postProcess(var10);
      }

   }

   public void remove(MethodDescriptor var1, Object var2) throws RemoveException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
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
         throw new RemoveException("Cannot remove stateful session beans using EJBHome.remove(Object primaryKey)");
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            InstrumentationSupport.postProcess(var3);
         }

         throw var5;
      }
   }

   public EJBObject allocateEO() {
      try {
         StatefulEJBObject eo = (StatefulEJBObject)this.eoClass.newInstance();
         eo.setEJBHome(this);
         eo.setBeanManager(this.getBeanManager());
         eo.setBeanInfo(this.getBeanInfo());
         return eo;
      } catch (InstantiationException var2) {
         throw new AssertionError(var2);
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      }
   }

   public EJBObject getEJBObject(Object pk) {
      return (StatefulEJBObject)this.eoMap.get(pk);
   }

   public EJBObject allocateEO(Object pk) {
      StatefulEJBObject eo;
      if (!this.getIsNoObjectActivation() && !this.getIsInMemoryReplication()) {
         eo = (StatefulEJBObject)this.allocateEO();
         eo.setPrimaryKey(pk);
         eo.setActivator(this.ejbActivator);
         return eo;
      } else {
         eo = (StatefulEJBObject)this.eoMap.get(pk);
         if (eo == null) {
            eo = (StatefulEJBObject)this.allocateEO();
            eo.setPrimaryKey(pk);
            this.eoMap.put(pk, eo);
         }

         return eo;
      }
   }

   public void releaseEO(Object pk) {
      this.eoMap.remove(pk);
   }

   public void undeploy() {
      if (this.getIsNoObjectActivation() || this.getIsInMemoryReplication()) {
         Collection ejbObjects = this.eoMap.values();
         Iterator var2 = ejbObjects.iterator();

         while(var2.hasNext()) {
            EJBObject eo = (EJBObject)var2.next();
            this.unexport(eo);
         }
      }

      if (this.beanInfo.hasDeclaredRemoteHome()) {
         this.unexportEJBActivator(this.ejbActivator, this.eoClass);
      }

      super.undeploy();
   }

   public boolean getIsInMemoryReplication() {
      return this.isInMemoryReplication;
   }

   public boolean getIsNoObjectActivation() {
      return this.isNoObjectActivation;
   }

   public RemoteReference getPrimaryRemoteRef(Object key) throws RemoteException {
      return ((ReplicatedBeanManager)this.beanManager).getPrimaryRemoteRef(key);
   }

   public RemoteReference getSecondaryRemoteRef(Object key, boolean fromPrimary) throws RemoteException {
      return ((ReplicatedBeanManager)this.beanManager).getSecondaryRemoteRef(key, fromPrimary);
   }

   public void secondaryCreatedForFullState(Object key, ResourceGroupKey resourceGroupKey) {
      ((ReplicatedBeanManager)this.beanManager).secondaryCreatedForFullState(key, resourceGroupKey);
   }

   public void becomePrimary(Object key) throws RemoteException {
      ((ReplicatedBeanManager)this.beanManager).becomePrimary(key);
   }

   public Object createSecondary(Object key) throws RemoteException {
      return ((ReplicatedBeanManager)this.beanManager).createSecondary(key);
   }

   public Object createSecondaryForBI(Object key, String ifaceClassName) throws RemoteException {
      throw new AssertionError("this method should not be invoked");
   }

   public void removeSecondary(Object key) throws RemoteException {
      ((ReplicatedBeanManager)this.beanManager).removeSecondary(key);
      StatefulEJBObject eo = (StatefulEJBObject)this.eoMap.remove(key);
      if (eo != null) {
         this.unexport(eo, false);
      }

   }

   public void updateSecondary(Object key, Serializable change) throws RemoteException {
      ((ReplicatedBeanManager)this.beanManager).updateSecondary(key, change);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Home_Remove_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulEJBHome.java", "weblogic.ejb.container.internal.StatefulEJBHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljavax/ejb/Handle;)V", 99, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "StatefulEJBHome.java", "weblogic.ejb.container.internal.StatefulEJBHome", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)V", 159, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Home_Remove_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Home_Remove_Around_Medium};
      $assertionsDisabled = !StatefulEJBHome.class.desiredAssertionStatus();
   }
}
