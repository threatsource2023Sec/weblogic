package weblogic.ejb.container.manager;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.ROInfo;
import weblogic.cluster.replication.Replicatable;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.interfaces.StatefulEJBObjectIntf;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.StatefulEJBHomeImpl;
import weblogic.ejb.container.replication.ReplicatedBean;
import weblogic.ejb.container.replication.ReplicatedBeanManager;
import weblogic.ejb.container.replication.ReplicatedEJB3ViewBean;
import weblogic.ejb.container.swap.DiskSwap;
import weblogic.ejb.container.swap.EJBSwap;
import weblogic.ejb.container.swap.ReplicatedMemorySwap;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.logging.Loggable;
import weblogic.rmi.cluster.PrimarySecondaryRemoteObject;
import weblogic.rmi.cluster.ejb.ReplicaIDImpl;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StackTraceUtilsClient;

public final class ReplicatedStatefulSessionManager extends StatefulSessionManager implements ReplicatedBeanManager {
   private final ReplicationServices repServ;
   private final ConcurrentMap registeredROIDs;
   static final long serialVersionUID = -3036320162480570960L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.ReplicatedStatefulSessionManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;

   public ReplicatedStatefulSessionManager(EJBRuntimeHolder runtime) {
      super(runtime);
      this.repServ = Locator.locate().getReplicationService(ServiceType.SYNC);
      this.registeredROIDs = new ConcurrentHashMap();
   }

   protected EJBSwap createEJBSwap() {
      DiskSwap ds = (DiskSwap)super.createEJBSwap();
      return (EJBSwap)(!this.inCluster ? ds : new ReplicatedMemorySwap(this, this.getBeanInfo(), ds));
   }

   public Remote remoteCreateForBI(Object origPK, Class implClass, Activator var3, Class iface) throws InternalException {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         InstrumentationSupport.preProcess(var10);
      }

      Remote var10000;
      try {
         Remote bo = null;
         Object pk = origPK;

         try {
            if (this.inCluster) {
               try {
                  String jndiName = this.remoteHome.getJNDINameAsString();
                  if (jndiName == null) {
                     jndiName = this.getBeanInfo().getIsIdenticalKey();
                  }

                  ROInfo roInfo = null;
                  if (pk == null) {
                     roInfo = this.repServ.register(new ReplicatedEJB3ViewBean(jndiName, iface));
                     pk = roInfo.getROID();
                  } else {
                     roInfo = this.repServ.add((ROID)pk, new ReplicatedEJB3ViewBean(jndiName, iface));
                  }

                  bo = ((StatefulEJBHomeImpl)this.remoteHome).allocateBI(pk, implClass, iface, (Activator)null);
                  PrimarySecondaryRemoteObject repObject = new PrimarySecondaryRemoteObject(bo, (Remote)roInfo.getSecondaryROInfo(), new ReplicaIDImpl(ArrayUtils.objectToByteArray(pk)));
                  ((ReplicatedMemorySwap)this.swapper).savePrimaryRO(pk, repObject);
               } catch (Exception var15) {
                  EJBRuntimeUtils.throwInternalException("Exception registering bean", var15);
               }
            } else {
               if (pk == null) {
                  pk = this.keyGenerator.nextKey();
               }

               bo = ((StatefulEJBHomeImpl)this.remoteHome).allocateBI(pk, implClass, iface, (Activator)null);
            }

            if (origPK == null) {
               EJBObject eo = null;
               if (!this.inCluster && this.getBeanInfo().hasDeclaredRemoteHome()) {
                  eo = this.remoteHome.allocateEO(pk);
               }

               EJBLocalObject elo = this.getBeanInfo().hasDeclaredLocalHome() ? this.localHome.allocateELO(pk) : null;

               try {
                  super.create(eo, elo, pk, InvocationWrapper.newInstance(), (Method)null, (Method)null, (Object[])null);
               } catch (Exception var16) {
                  if (this.inCluster) {
                     ((ReplicatedMemorySwap)this.swapper).remove(pk);
                  }

                  EJBRuntimeUtils.throwInternalException("Exception in create", var16);
               }
            }
         } catch (Exception var17) {
            EJBRuntimeUtils.throwInternalException("Exception in remote create", var17);
         }

         if (this.inCluster) {
            this.registeredROIDs.putIfAbsent((ROID)pk, pk);
         }

         var10000 = bo;
      } catch (Throwable var18) {
         if (var10 != null) {
            var10.th = var18;
            var10.ret = null;
            InstrumentationSupport.postProcess(var10);
         }

         throw var18;
      }

      if (var10 != null) {
         var10.ret = var10000;
         InstrumentationSupport.postProcess(var10);
      }

      return var10000;
   }

   public EJBObject remoteCreate(InvocationWrapper wrap, Method createMethod, Method postCreateMethod, Object[] args) throws InternalException {
      Object pk = null;
      StatefulEJBObjectIntf eo = null;

      try {
         if (this.inCluster) {
            ROInfo roInfo = this.repServ.register(new ReplicatedBean(this.remoteHome.getJNDINameAsString()));
            pk = roInfo.getROID();
            eo = (StatefulEJBObjectIntf)this.remoteHome.allocateEO(pk);
            EJBObject secondaryEO = (EJBObject)roInfo.getSecondaryROInfo();
            PrimarySecondaryRemoteObject repObject = new PrimarySecondaryRemoteObject(eo, secondaryEO, new ReplicaIDImpl(ArrayUtils.objectToByteArray(pk)));
            ((ReplicatedMemorySwap)this.swapper).savePrimaryRO(pk, repObject);
         } else {
            pk = this.keyGenerator.nextKey();
            eo = (StatefulEJBObjectIntf)this.remoteHome.allocateEO(pk);
            new PrimarySecondaryRemoteObject(eo, (Remote)null, new ReplicaIDImpl(ArrayUtils.objectToByteArray(pk)));
         }

         eo.setPrimaryKey(pk);
         EJBLocalObject elo = this.localHome != null ? this.localHome.allocateELO(pk) : null;

         try {
            super.create(eo, elo, pk, wrap, createMethod, postCreateMethod, args);
         } catch (Exception var10) {
            if (this.inCluster) {
               ((ReplicatedMemorySwap)this.swapper).remove(pk);
            }

            EJBRuntimeUtils.throwInternalException("Exception in create", var10);
         }
      } catch (Exception var11) {
         EJBRuntimeUtils.throwInternalException("Exception in remote create", var11);
      }

      if (this.inCluster) {
         this.registeredROIDs.putIfAbsent((ROID)pk, pk);
      }

      return eo;
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      if (this.inCluster) {
         this.getBean(wrap.getPrimaryKey(), wrap.getMethodDescriptor().getClientViewDescriptor().getViewClass());
         this.swapper.remove(wrap.getPrimaryKey());
         this.registeredROIDs.remove(wrap.getPrimaryKey());
      }

      super.remove(wrap);
   }

   public void removeForRemoveAnnotation(InvocationWrapper wrap) throws InternalException {
      if (this.inCluster) {
         this.getBean(wrap.getPrimaryKey(), wrap.getMethodDescriptor().getClientViewDescriptor().getViewClass());
         this.swapper.remove(wrap.getPrimaryKey());
         this.registeredROIDs.remove(wrap.getPrimaryKey());
      }

      super.removeForRemoveAnnotation(wrap);
   }

   public RemoteReference getPrimaryRemoteRef(Object key) throws RemoteException {
      if (debugLogger.isDebugEnabled()) {
         debug("getPrimaryRemoteRef called on key: " + key);
      }

      PrimarySecondaryRemoteObject psro = ((ReplicatedMemorySwap)this.swapper).findPrimary(key);
      if (psro == null) {
         if (debugLogger.isDebugEnabled()) {
            debug("findPrimary for key: " + key + " returned null");
         }

         return null;
      } else {
         return psro.getPrimaryRef();
      }
   }

   public RemoteReference getSecondaryRemoteRef(Object key, boolean fromPrimary) throws RemoteException {
      if (debugLogger.isDebugEnabled()) {
         debug("getSecondaryRemoteRef called on key: " + key + ", fromPrimary=" + fromPrimary);
      }

      PrimarySecondaryRemoteObject psro = null;
      if (fromPrimary) {
         psro = ((ReplicatedMemorySwap)this.swapper).findPrimary(key);
      } else {
         psro = ((ReplicatedMemorySwap)this.swapper).findSecondary(key);
      }

      if (psro == null) {
         if (debugLogger.isDebugEnabled()) {
            debug(fromPrimary ? "findPrimary for key: " + key + " returned null" : "findSecondary for key: " + key + " returned null");
         }

         return null;
      } else {
         return fromPrimary ? psro.getSecondaryRef() : psro.getPrimaryRef();
      }
   }

   public void becomePrimary(Object key) {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         InstrumentationSupport.preProcess(var3);
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            Loggable l = EJBLogger.logBecomePrimaryLoggable(this.getDisplayName(), String.valueOf(key));
            debug(l.getMessage());
         }

         Object k = ((ReplicatedMemorySwap)this.swapper).becomePrimary(key);
         if (k != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("becomePrimary, add to registered for " + key);
            }

            this.registeredROIDs.putIfAbsent((ROID)k, k);
         }
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

   public Remote createSecondary(Object key) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         InstrumentationSupport.preProcess(var4);
      }

      Remote var10000;
      try {
         if (debugLogger.isDebugEnabled()) {
            Loggable l = EJBLogger.logBecomeSecondaryLoggable(this.getDisplayName(), String.valueOf(key));
            debug(l.getMessage());
         }

         EJBObject eo = this.remoteHome.allocateEO(key);

         try {
            ((ReplicatedMemorySwap)this.swapper).saveSecondaryRO(key, new PrimarySecondaryRemoteObject(eo, (Remote)null, new ReplicaIDImpl(ArrayUtils.objectToByteArray(key))));
            var10000 = ServerHelper.exportObject(eo, "");
         } catch (Exception var7) {
            EJBLogger.logFailedToCreateCopy(this.getDisplayName(), StackTraceUtilsClient.throwable2StackTrace(var7));
            throw new EJBException(var7);
         }
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            var4.ret = null;
            InstrumentationSupport.postProcess(var4);
         }

         throw var8;
      }

      if (var4 != null) {
         var4.ret = var10000;
         InstrumentationSupport.postProcess(var4);
      }

      return var10000;
   }

   public void secondaryCreatedForFullState(Object key, ResourceGroupKey resourceGroupKey) {
      if (debugLogger.isDebugEnabled()) {
         debug("secondaryCreatedForFullState called on key: " + key + ", resourceGroupKey=" + resourceGroupKey);
      }

      try {
         ((ReplicatedMemorySwap)this.swapper).sendUpdateCompleteDiff(key, resourceGroupKey);
      } catch (Exception var4) {
         EJBLogger.logErrorDuringFullStateReplication(this.getDisplayName(), key == null ? "null" : key.toString(), resourceGroupKey == null ? "null" : resourceGroupKey.toString(), StackTraceUtilsClient.throwable2StackTrace(var4));
      }

   }

   public Remote createSecondaryForBI(Object key, Class iface) {
      if (debugLogger.isDebugEnabled()) {
         Loggable l = EJBLogger.logBecomeSecondaryBILoggable(this.getDisplayName(), key + "[" + iface + "]");
         debug(l.getMessage());
      }

      Class implClass = this.getBeanInfo().getGeneratedRemoteBusinessImplClass(iface);
      Remote sro = ((StatefulEJBHomeImpl)this.remoteHome).allocateBI(key, implClass, iface, (Activator)null);

      try {
         ((ReplicatedMemorySwap)this.swapper).saveSecondaryRO(key, new PrimarySecondaryRemoteObject(sro, (Remote)null, new ReplicaIDImpl(ArrayUtils.objectToByteArray(key))));
         return ServerHelper.exportObject(sro, "");
      } catch (Exception var6) {
         EJBLogger.logFailedToCreateCopy(this.getDisplayName(), StackTraceUtilsClient.throwable2StackTrace(var6));
         throw new EJBException(var6);
      }
   }

   protected void replicate(CacheKey key, WLEnterpriseBean bean) {
      if (this.inCluster) {
         boolean invokeCallbacks = this.getBeanInfo().getPassivateDuringReplication();

         try {
            if (invokeCallbacks) {
               this.doEjbPassivate(bean);
            }

            ((ReplicatedMemorySwap)this.swapper).sendUpdate(key.getPrimaryKey(), bean);
         } catch (Exception var6) {
            EJBLogger.logErrorDuringPassivation(StackTraceUtilsClient.throwable2StackTrace(var6));
         }

         try {
            if (invokeCallbacks) {
               this.doEjbActivate(bean);
            }
         } catch (InternalException | RemoteException var5) {
            this.removeBean(key, bean);
            EJBLogger.logExceptionDuringEJBActivate(var5);
         }

      }
   }

   public void undeploy() {
      if (debugLogger.isDebugEnabled()) {
         debug("unreploy in ReplicatedStatefulSessionManager@" + this.hashCode() + ", inCluster=" + this.inCluster);
      }

      boolean isPartitionShuttingDown = false;
      if (this.inCluster) {
         isPartitionShuttingDown = ((ReplicatedMemorySwap)this.swapper).handleReplicateOnShutdownIfNeeded();
      }

      super.undeploy();
      if (this.inCluster && isPartitionShuttingDown) {
         ((ReplicatedMemorySwap)this.swapper).handleCleanupOnPartitionShutdown();
      }

   }

   public void unregisterROIDs() {
      if (debugLogger.isDebugEnabled()) {
         debug("unregisterROIDs in ReplicatedStatefulSessionManager@" + this.hashCode() + ", inCluster=" + this.inCluster);
      }

      int cnt = 0;
      Iterator var2 = this.registeredROIDs.keySet().iterator();

      while(var2.hasNext()) {
         ROID tmp = (ROID)var2.next();
         if (debugLogger.isDebugEnabled()) {
            debug("unregisterROIDs in ReplicatedStatefulSessionManager@" + this.hashCode() + ", roid=" + tmp + ", total count=" + cnt);
         }

         if (tmp != null) {
            this.repServ.unregister(tmp, Replicatable.DEFAULT_KEY);
            ++cnt;
         }
      }

      if (cnt > 0) {
         EJBLogger.logUnregisteredStatefulEJBReplicas(this.getDisplayName());
      }

      this.registeredROIDs.clear();
   }

   public void removeSecondary(Object key) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         InstrumentationSupport.preProcess(var2);
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            debug("removeSecondary with key: " + key);
         }

         this.swapper.remove(key);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            InstrumentationSupport.postProcess(var2);
         }

         throw var4;
      }

      if (var2 != null) {
         InstrumentationSupport.postProcess(var2);
      }

   }

   public void updateSecondary(Object key, Serializable bean) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         InstrumentationSupport.preProcess(var4);
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            debug("updateSecondary with key: " + key);
         }

         try {
            ((ReplicatedMemorySwap)this.swapper).receiveUpdate(key, bean);
         } catch (Exception var7) {
            EJBLogger.logFailedToUpdateSecondaryCopy(StackTraceUtilsClient.throwable2StackTrace(var7));
         }
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            InstrumentationSupport.postProcess(var4);
         }

         throw var8;
      }

      if (var4 != null) {
         InstrumentationSupport.postProcess(var4);
      }

   }

   public void destroyInstance(InvocationWrapper wrap, Throwable ee) {
      super.destroyInstance(wrap, ee);
      if (this.inCluster) {
         this.repServ.unregister((ROID)wrap.getPrimaryKey(), Replicatable.DEFAULT_KEY);
      }

   }

   public StatefulEJBObjectIntf registerReplicatedObject(Object pk) throws InternalException {
      StatefulEJBObjectIntf eo = null;

      try {
         ROInfo roInfo = null;
         if (pk == null) {
            roInfo = this.repServ.register(new ReplicatedBean(this.remoteHome.getJNDINameAsString()));
         } else {
            roInfo = this.repServ.add((ROID)pk, new ReplicatedBean(this.remoteHome.getJNDINameAsString()));
         }

         eo = (StatefulEJBObjectIntf)this.remoteHome.allocateEO(roInfo.getROID());
         PrimarySecondaryRemoteObject repObject = new PrimarySecondaryRemoteObject(eo, (Remote)roInfo.getSecondaryROInfo(), new ReplicaIDImpl(ArrayUtils.objectToByteArray(roInfo.getROID())));
         ((ReplicatedMemorySwap)this.swapper).savePrimaryRO(roInfo.getROID(), repObject);
         eo.setPrimaryKey(roInfo.getROID());
      } catch (Exception var5) {
         EJBRuntimeUtils.throwInternalException("Exception registering bean", var5);
      }

      if (this.inCluster) {
         this.registeredROIDs.putIfAbsent((ROID)pk, pk);
      }

      return eo;
   }

   public void removeRegisteredROIDs(Object pk) {
      this.registeredROIDs.remove(pk);
   }

   public boolean isInCluster() {
      return this.inCluster;
   }

   private static void debug(String s) {
      debugLogger.debug("[ReplicatedStatefulSessionManager] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Replicated_Session_Manager_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ReplicatedStatefulSessionManager.java", "weblogic.ejb.container.manager.ReplicatedStatefulSessionManager", "remoteCreateForBI", "(Ljava/lang/Object;Ljava/lang/Class;Lweblogic/rmi/extensions/activation/Activator;Ljava/lang/Class;)Ljava/rmi/Remote;", 72, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ReplicatedStatefulSessionManager.java", "weblogic.ejb.container.manager.ReplicatedStatefulSessionManager", "becomePrimary", "(Ljava/lang/Object;)V", 221, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ReplicatedStatefulSessionManager.java", "weblogic.ejb.container.manager.ReplicatedStatefulSessionManager", "createSecondary", "(Ljava/lang/Object;)Ljava/rmi/Remote;", 233, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ReplicatedStatefulSessionManager.java", "weblogic.ejb.container.manager.ReplicatedStatefulSessionManager", "removeSecondary", "(Ljava/lang/Object;)V", 346, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ReplicatedStatefulSessionManager.java", "weblogic.ejb.container.manager.ReplicatedStatefulSessionManager", "updateSecondary", "(Ljava/lang/Object;Ljava/io/Serializable;)V", 351, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Replicated_Session_Manager_Around_High};
   }
}
