package weblogic.ejb.container.swap;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.EJBContext;
import javax.ejb.EJBObject;
import javax.ejb.NoSuchEJBException;
import weblogic.cluster.ClusterHelper;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.ROInfo;
import weblogic.cluster.replication.Replicatable;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLSessionEJBContext;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.manager.ReplicatedStatefulSessionManager;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.rmi.cluster.PrimarySecondaryRemoteObject;
import weblogic.rmi.cluster.ejb.PreInvokeDeserializationException;
import weblogic.rmi.cluster.ejb.ReplicaIDImpl;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.ArrayUtils;
import weblogic.utils.ByteArrayDiff;
import weblogic.utils.ByteArrayDiffChecker;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class ReplicatedMemorySwap implements EJBSwap {
   private static final DebugLogger DEBUG_LOGGER;
   private static final DebugLogger DEBUG_LOGGER_VERBOSE;
   private static AuthenticatedSubject KERNEL_ID;
   private static ComponentInvocationContextManager CICM;
   private final StatefulSessionManager beanManager;
   private final PassivationUtils passivationUtils;
   private final DiffChecker diffChecker;
   private final DiskSwap diskSwap;
   private final DiskSwap.BeanRemovalListener brListener = new SecondaryRemover();
   private final ReplicationServices repServ;
   private final ClusterServices clusterServices;
   private final Map primaryROMap;
   private final Map secondaryROMap;
   private final Map previousVersionMap;
   private final Map secondaryBeanMap;

   public ReplicatedMemorySwap(StatefulSessionManager beanManager, StatefulSessionBeanInfo beanInfo, DiskSwap diskSwap) {
      this.repServ = Locator.locate().getReplicationService(ServiceType.SYNC);
      this.clusterServices = weblogic.cluster.ClusterServices.Locator.locate();
      this.primaryROMap = new ConcurrentHashMap();
      this.secondaryROMap = new ConcurrentHashMap();
      this.previousVersionMap = new ConcurrentHashMap();
      this.secondaryBeanMap = new ConcurrentHashMap();
      this.beanManager = beanManager;
      this.diskSwap = diskSwap;
      this.passivationUtils = new PassivationUtils(beanInfo.getClassLoader());
      this.diffChecker = this.getDiffChecker(beanInfo);
      this.diskSwap.addBeanRemovalListener(this.brListener);
   }

   public synchronized Object read(Object pk, Class iface) throws InternalException {
      if (DEBUG_LOGGER_VERBOSE.isDebugEnabled()) {
         debugVerbose("read(pk=" + pk + ", iface=" + iface + ")");
      }

      Object bean = this.secondaryBeanMap.remove(pk);
      if (bean != null) {
         try {
            return this.diffChecker.readBeanState(pk, bean);
         } catch (IOException var6) {
            this.secondaryBeanMap.put(pk, bean);
            this.handleDeserializationException(pk, bean, var6, iface);
            throw new AssertionError("Should not reach.");
         }
      } else {
         bean = this.diskSwap.read(pk, iface);
         if (bean != null) {
            ReplicatedStatefulSessionManager repBeanManager = (ReplicatedStatefulSessionManager)this.beanManager;
            if (this.beanManager.getBeanInfo().isEJB30() && !EJBObject.class.isAssignableFrom(iface)) {
               Class implClass = repBeanManager.getBeanInfo().getGeneratedRemoteBusinessImplClass(iface);
               repBeanManager.remoteCreateForBI(pk, implClass, (Activator)null, iface);
            } else {
               repBeanManager.registerReplicatedObject(pk);
            }
         }

         return bean;
      }
   }

   private void handleDeserializationException(Object pk, Object bean, IOException ioe, Class iface) throws InternalException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Deserialization failure on reading bean state from secondary for " + pk + ": " + ioe);
      }

      String emsg = EJBLogger.logReadReplicatedBeanStateExceptionLoggable(iface + "[key=" + pk + "]", StackTraceUtilsClient.throwable2StackTrace(ioe)).getMessageText();
      if (!this.clusterServices.isZDTAppRolloutInProgress()) {
         EJBRuntimeUtils.throwInternalException(emsg, new NoSuchEJBException(emsg));
         throw new AssertionError("Should not reach.");
      } else {
         PreInvokeDeserializationException de = new PreInvokeDeserializationException(emsg, new RemoteException(StackTraceUtilsClient.throwable2StackTrace(new NoSuchEJBException(ioe.getMessage(), ioe))));
         RemoteReference ref = null;

         try {
            ref = this.sendCompleteDiffToNewSecondaryForZDT(pk);
            if (ref != null) {
               de.setFailoverRemoteRef(ref);
            }

            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("PreInvokeDeserializationException.setFailoverRef to " + ref + " for " + pk);
            }
         } catch (Exception var12) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("Failed to replicate state to a new secondary after deserialization failure: " + var12 + " for " + pk);
            }
         } finally {
            if (ref == null) {
               EJBRuntimeUtils.throwInternalException(emsg, new NoSuchEJBException(emsg));
               throw new AssertionError("Should not reach.");
            } else {
               throw new InternalException(de.getMessage(), de);
            }
         }
      }
   }

   private static String getPartitionNameFromThread() {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      String partitionName = null;
      if (cic != null) {
         partitionName = cic.getPartitionName();
      }

      if (partitionName == null || partitionName.isEmpty()) {
         partitionName = "DOMAIN";
      }

      return partitionName;
   }

   private static boolean isGlobalPartition(String partitionName) {
      return partitionName == null || partitionName.isEmpty() || partitionName.equals("DOMAIN");
   }

   public boolean handleReplicateOnShutdownIfNeeded() {
      boolean isPartitionShuttingdown = false;
      boolean isServerShuttingdown = false;

      try {
         String partitionName = this.beanManager.getPartitionName();
         if (this.beanManager.isPartitionShuttingDown()) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("handleReplicateOnShutdownIfNeeded(): partition " + partitionName + " is in shutting down, partition state=" + ClusterHelper.getPartitionState(partitionName));
            }

            isPartitionShuttingdown = true;
         } else if (this.beanManager.isServerShuttingDown()) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("handleReplicateOnShutdownIfNeeded server is shutting down,  server state=" + ClusterHelper.getServerState());
            }

            isServerShuttingdown = true;
         }

         boolean ensureReplicated = false;
         if (isPartitionShuttingdown && this.clusterServices.isSessionReplicationOnShutdownEnabled(partitionName)) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("handleReplicateOnShutdownIfNeeded(): partition shutting down , do ensureFullStateReplicated for partition " + partitionName);
            }

            ensureReplicated = true;
         } else if (isServerShuttingdown && this.clusterServices.isSessionReplicationOnShutdownEnabled()) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("handleReplicateOnShutdownIfNeeded server shutting down , do ensureFullStateReplicated for " + partitionName + " partition ");
            }

            ensureReplicated = true;
         }

         if (ensureReplicated) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("handleReplicateOnShutdownIfNeeded , do ensureFullStateReplicated primaries " + this.primaryROMap.size() + ", secondaris " + this.secondaryROMap.size());
            }

            EJBLogger.logReplicateOnShutdown(this.beanManager.getDisplayName(), partitionName);

            try {
               List roids = new ArrayList(this.primaryROMap.keySet());
               roids.addAll(this.secondaryROMap.keySet());
               this.repServ.ensureFullStateReplicated(roids);
            } catch (Throwable var6) {
               EJBLogger.logWarningReplicateOnShutdown(this.beanManager.getDisplayName(), partitionName, StackTraceUtilsClient.throwable2StackTrace(var6));
            }
         }
      } catch (Throwable var7) {
         EJBLogger.logErrorCheckServerStateOnDeactivation(this.beanManager.getDisplayName(), StackTraceUtilsClient.throwable2StackTrace(var7));
      }

      return isPartitionShuttingdown;
   }

   public void handleCleanupOnPartitionShutdown() {
      String partitionName = null;

      try {
         partitionName = getPartitionNameFromThread();
         List roids = new ArrayList(this.primaryROMap.keySet());
         roids.addAll(this.secondaryROMap.keySet());
         EJBLogger.logLocalCleanupReplicaOnPartitionShutdown(this.beanManager.getDisplayName(), partitionName);
         this.repServ.localCleanupOnPartitionShutdown(roids, Replicatable.DEFAULT_KEY);
      } catch (Throwable var3) {
         EJBLogger.logWarningLocalCleanupReplicaOnPartitionShutdown(this.beanManager.getDisplayName(), partitionName == null ? "" : partitionName, StackTraceUtilsClient.throwable2StackTrace(var3));
      }

   }

   public void remove(Object pk) {
      if (this.secondaryROMap.remove(pk) == null) {
         this.repServ.unregister((ROID)pk, Replicatable.DEFAULT_KEY);
      }

      this.secondaryBeanMap.remove(pk);
      this.primaryROMap.remove(pk);
      this.previousVersionMap.remove(pk);
   }

   public PrimarySecondaryRemoteObject findPrimary(Object pk) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("findPrimary called for pk: " + pk);
      }

      return (PrimarySecondaryRemoteObject)this.primaryROMap.get(pk);
   }

   public PrimarySecondaryRemoteObject findSecondary(Object pk) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("findSecondary called for pk: " + pk);
      }

      return (PrimarySecondaryRemoteObject)this.secondaryROMap.get(pk);
   }

   public void sendUpdateCompleteDiff(Object pk, ResourceGroupKey resourceGroupKey) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("sendUpdateCompleteDiff() called for pk: " + pk + ", resourceGroupKey=" + resourceGroupKey);
      }

      try {
         PrimarySecondaryRemoteObject psro = (PrimarySecondaryRemoteObject)this.primaryROMap.get(pk);
         if (psro == null) {
            throw new IllegalStateException("sendUpdateCompleteDiff() is called when not primary");
         }

         Serializable change = this.diffChecker.getStateAsCompleteDiff(pk, resourceGroupKey);
         ROInfo info = (ROInfo)this.repServ.copyUpdateSecondary((ROID)pk, change, Replicatable.DEFAULT_KEY, resourceGroupKey);
         Remote secondary = (Remote)info.getSecondaryROInfo();
         psro.changeSecondary(secondary, new ReplicaIDImpl(ArrayUtils.objectToByteArray(pk)));
      } catch (Exception var7) {
         var7.printStackTrace();
         if (this.beanManager.getBeanInfo().isEJB30()) {
            EJBLogger.logFailedToUpdateSecondaryFromBusiness(this.beanManager.getDisplayName() + var7);
         } else {
            EJBLogger.logFailedToUpdateSecondary(this.beanManager.getDisplayName() + var7);
         }
      }

   }

   private RemoteReference sendCompleteDiffToNewSecondaryForZDT(Object pk) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("sendCompleteDiffToNewSecondaryForZDT() called for pk: " + pk);
      }

      try {
         Serializable change = this.diffChecker.getStateAsCompleteDiff(pk, (ResourceGroupKey)null);
         ROInfo info = (ROInfo)this.repServ.copyUpdateSecondaryForZDT((ROID)pk, change, Replicatable.DEFAULT_KEY);
         Remote secondary = (Remote)info.getSecondaryROInfo();
         PrimarySecondaryRemoteObject psro = (PrimarySecondaryRemoteObject)this.primaryROMap.get(pk);
         psro.changeSecondary(secondary, new ReplicaIDImpl(ArrayUtils.objectToByteArray(pk)));
         return psro.getSecondaryRef();
      } catch (Exception var6) {
         var6.printStackTrace();
         if (this.beanManager.getBeanInfo().isEJB30()) {
            EJBLogger.logFailedToUpdateSecondaryFromBusiness(this.beanManager.getDisplayName() + var6);
         } else {
            EJBLogger.logFailedToUpdateSecondary(this.beanManager.getDisplayName() + var6);
         }

         return null;
      }
   }

   public Object becomePrimary(Object pk) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Become primary called for pk: " + pk);
      }

      if (this.secondaryROMap.get(pk) != null) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            debug("Become primary called on old secondary for pk: " + pk);
         }

         this.primaryROMap.put(pk, this.secondaryROMap.remove(pk));
         return pk;
      } else {
         return null;
      }
   }

   public void savePrimaryRO(Object pk, PrimarySecondaryRemoteObject value) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Save primary called for pk: " + pk);
      }

      this.primaryROMap.put(pk, value);
   }

   public void saveSecondaryRO(Object pk, PrimarySecondaryRemoteObject value) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Save secondary called for pk: " + pk);
      }

      this.secondaryROMap.put(pk, value);
   }

   public void receiveUpdate(Object pk, Object bd) throws InternalException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Received update for pk: " + pk);
      }

      this.diffChecker.receiveUpdate(pk, bd);
   }

   public void sendUpdate(Object pk, Object bean) throws InternalException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Sending update for pk: " + pk);
      }

      Serializable diff = this.diffChecker.getDiff(pk, bean);

      try {
         PrimarySecondaryRemoteObject psro = (PrimarySecondaryRemoteObject)this.primaryROMap.get(pk);
         Remote oldSecondary = psro != null ? psro.getSecondary() : null;
         ROInfo info1 = (ROInfo)this.repServ.updateSecondary((ROID)pk, diff, Replicatable.DEFAULT_KEY);
         int version1 = info1.getSecondaryROVersion();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            debug("sendUpdate: 1st updateSecondary returned version " + version1 + " for pk: " + pk + ", oldSecondary=" + oldSecondary);
         }

         if (version1 == -1) {
            throw new Exception();
         }

         Remote secondary1 = (Remote)info1.getSecondaryROInfo();
         if (oldSecondary != secondary1) {
            if (psro == null) {
               psro = (PrimarySecondaryRemoteObject)this.primaryROMap.get(pk);
            }

            psro.changeSecondary(secondary1, new ReplicaIDImpl(ArrayUtils.objectToByteArray(pk)));
            ROInfo info2 = (ROInfo)this.repServ.updateSecondary((ROID)pk, this.diffChecker.getCompleteDiff(pk, bean), Replicatable.DEFAULT_KEY);
            int version2 = info2.getSecondaryROVersion();
            if (DEBUG_LOGGER.isDebugEnabled()) {
               debug("sendUpdate: 2nd updateSecondary returned version " + version2 + " for pk: " + pk);
            }

            if (version2 == -1) {
               throw new Exception("AMYXXX");
            }

            psro.updateReplicaVersion(version2);
         } else {
            psro.updateReplicaVersion(version1);
         }
      } catch (Exception var11) {
         this.previousVersionMap.remove(pk);
         if (this.beanManager.getBeanInfo().isEJB30()) {
            EJBLogger.logFailedToUpdateSecondaryFromBusiness(this.beanManager.getDisplayName());
         } else {
            EJBLogger.logFailedToUpdateSecondary(this.beanManager.getDisplayName());
         }
      }

   }

   public void write(Object pk, Object bean, long timeLastTouched) throws InternalException {
      this.secondaryBeanMap.remove(pk);
      this.primaryROMap.remove(pk);
      this.secondaryROMap.remove(pk);
      this.previousVersionMap.remove(pk);
      this.diskSwap.write(pk, bean, timeLastTouched);
   }

   public void updateClassLoader(ClassLoader cl) {
      this.diskSwap.updateClassLoader(cl);
      this.passivationUtils.updateClassLoader(cl);
   }

   public void updateIdleTimeoutMS(long ms) {
      this.diskSwap.updateIdleTimeoutMS(ms);
   }

   public void destroy() {
      this.diskSwap.removeBeanRemovalListener(this.brListener);
      this.diskSwap.destroy();
   }

   private DiffChecker getDiffChecker(StatefulSessionBeanInfo info) {
      return (DiffChecker)(info.getCalculateDeltaUsingReflection() ? new BeanStateChecker(info.getBeanClass()) : new ByteLevelChecker());
   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[ReplicatedMemorySwap] " + s);
   }

   private static void debugVerbose(String s) {
      DEBUG_LOGGER_VERBOSE.debug("[ReplicatedMemorySwap] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.swappingLogger;
      DEBUG_LOGGER_VERBOSE = EJBDebugService.swappingLoggerVerbose;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }

   private interface DiffChecker {
      Object readBeanState(Object var1, Object var2) throws InternalException, IOException;

      void receiveUpdate(Object var1, Object var2) throws InternalException;

      Serializable getDiff(Object var1, Object var2) throws InternalException;

      Serializable getCompleteDiff(Object var1, Object var2) throws InternalException;

      Serializable getStateAsCompleteDiff(Object var1, ResourceGroupKey var2) throws InternalException;
   }

   private class ByteLevelChecker implements DiffChecker {
      private static final int DEFAULT_INITIAL_BUFSIZE = 1024;

      private ByteLevelChecker() {
      }

      public Object readBeanState(Object pk, Object bean) throws InternalException, IOException {
         if (ReplicatedMemorySwap.DEBUG_LOGGER_VERBOSE.isDebugEnabled()) {
            ReplicatedMemorySwap.debugVerbose("ByteLevelChecker.readBeanState(pk=" + pk + ", bean state=" + bean + ")");
         }

         UnsyncByteArrayInputStream bais = null;

         Object var4;
         try {
            bais = new UnsyncByteArrayInputStream((byte[])((byte[])bean));
            var4 = ReplicatedMemorySwap.this.passivationUtils.readBeanState(ReplicatedMemorySwap.this.beanManager, bais, pk);
         } finally {
            if (bais != null) {
               try {
                  bais.close();
               } catch (Exception var11) {
               }
            }

         }

         return var4;
      }

      public void receiveUpdate(Object pk, Object diff) {
         byte[] b = (byte[])((byte[])ReplicatedMemorySwap.this.secondaryBeanMap.get(pk));
         if (diff instanceof ByteArrayDiff) {
            b = ((ByteArrayDiff)diff).applyDiff(b);
         } else {
            b = (byte[])((byte[])diff);
         }

         ReplicatedMemorySwap.this.secondaryBeanMap.put(pk, b);
      }

      public Serializable getDiff(Object pk, Object bean) throws InternalException {
         byte[] old = (byte[])((byte[])ReplicatedMemorySwap.this.previousVersionMap.get(pk));
         int initialBufSize = old != null ? old.length : 1024;
         UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream(initialBufSize);
         ReplicatedMemorySwap.this.passivationUtils.write(ReplicatedMemorySwap.this.beanManager, baos, bean);
         byte[] b = baos.toRawBytes();
         ReplicatedMemorySwap.this.previousVersionMap.put(pk, b);
         return ByteArrayDiffChecker.diffByteArrays(old, b);
      }

      public Serializable getCompleteDiff(Object pk, Object bean) throws InternalException {
         UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
         ReplicatedMemorySwap.this.passivationUtils.write(ReplicatedMemorySwap.this.beanManager, baos, bean);
         return baos.toRawBytes();
      }

      public Serializable getStateAsCompleteDiff(Object pk, ResourceGroupKey resourceGroupKey) throws InternalException {
         Object bs = ReplicatedMemorySwap.this.previousVersionMap.get(pk);
         if (bs == null) {
            bs = ReplicatedMemorySwap.this.secondaryBeanMap.get(pk);
         }

         if (bs == null) {
            throw new InternalException("getStateAsCompleteDiff(): no bean state in map for key " + pk);
         } else {
            return (byte[])((byte[])bs);
         }
      }

      // $FF: synthetic method
      ByteLevelChecker(Object x1) {
         this();
      }
   }

   private class BeanStateChecker implements DiffChecker {
      private final BeanStateDiffChecker delegate;
      private Class bclazz = null;

      BeanStateChecker(Class clazz) {
         this.delegate = new BeanStateDiffChecker(clazz);
         this.bclazz = clazz;
      }

      public Object readBeanState(Object pk, Object beanState) throws InternalException, IOException {
         if (ReplicatedMemorySwap.DEBUG_LOGGER_VERBOSE.isDebugEnabled()) {
            ReplicatedMemorySwap.debugVerbose("BeanStateChecker.readBeanState(pk=" + pk + ", bean state=" + beanState + ", clazz=" + this.bclazz + ")");
         }

         Object bean = null;

         String emsg;
         try {
            EJBContext ctx = ReplicatedMemorySwap.this.beanManager.allocateContext((Object)null, pk);
            if (ReplicatedMemorySwap.this.beanManager.getBeanInfo().isEJB30()) {
               ((WLSessionEJBContext)ctx).setPrimaryKey(pk);
            }

            try {
               EJBContextManager.pushEjbContext(ctx);
               AllowedMethodsHelper.pushMethodInvocationState(1);
               bean = ReplicatedMemorySwap.this.beanManager.allocateBean();
            } finally {
               EJBContextManager.popEjbContext();
               AllowedMethodsHelper.popMethodInvocationState();
            }

            ((WLEJBContext)ctx).setBean(bean);
            ReplicatedMemorySwap.this.beanManager.perhapsCallSetContext(bean, ctx);
            ((WLEnterpriseBean)bean).__WL_setEJBContext(ctx);
         } catch (Exception var13) {
            emsg = EJBLogger.logCreateReplicatedBeanExceptionLoggable(this.bclazz + "[" + pk + "]", StackTraceUtilsClient.throwable2StackTrace(var13)).getMessageText();
            EJBRuntimeUtils.throwInternalException(emsg, new NoSuchEJBException(emsg));
            throw new AssertionError("Should not reach.");
         }

         try {
            this.delegate.applyBeanState(bean, (BeanState)beanState);
            return bean;
         } catch (IOException var10) {
            throw var10;
         } catch (Exception var11) {
            emsg = EJBLogger.logCreateReplicatedBeanExceptionLoggable(this.bclazz + "[" + pk + "]", StackTraceUtilsClient.throwable2StackTrace(var11)).getMessageText();
            EJBRuntimeUtils.throwInternalException(emsg, new NoSuchEJBException(emsg));
            throw new AssertionError("Should not reach.");
         }
      }

      public void receiveUpdate(Object pk, Object bd) throws InternalException {
         try {
            BeanState bs = (BeanState)ReplicatedMemorySwap.this.secondaryBeanMap.get(pk);
            if (bs == null) {
               bs = new BeanState();
               ReplicatedMemorySwap.this.secondaryBeanMap.put(pk, bs);
            }

            this.delegate.mergeDiff(bs, (ArrayList)bd);
         } catch (Exception var4) {
            throw new InternalException("Error while processing bean update", var4);
         }
      }

      public Serializable getDiff(Object pk, Object bean) throws InternalException {
         try {
            BeanState bs = (BeanState)ReplicatedMemorySwap.this.previousVersionMap.get(pk);
            if (bs == null) {
               bs = new BeanState();
               ReplicatedMemorySwap.this.previousVersionMap.put(pk, bs);
            }

            return this.delegate.calculateDiff(bean, bs);
         } catch (Exception var4) {
            throw new InternalException("Error while calculating diff", var4);
         }
      }

      public Serializable getCompleteDiff(Object pk, Object bean) throws InternalException {
         try {
            return this.delegate.calculateDiff(bean, new BeanState());
         } catch (Exception var4) {
            throw new InternalException("Error while calculating diff", var4);
         }
      }

      public Serializable getStateAsCompleteDiff(Object pk, ResourceGroupKey resourceGroupKey) throws InternalException {
         BeanState bs = (BeanState)ReplicatedMemorySwap.this.previousVersionMap.get(pk);
         if (bs == null) {
            bs = (BeanState)ReplicatedMemorySwap.this.secondaryBeanMap.get(pk);
         }

         if (bs == null) {
            throw new InternalException("getStateAsCompleteDiff(): no bean state in map for key " + pk);
         } else {
            return this.delegate.completeDiffFromState(bs, resourceGroupKey);
         }
      }
   }

   private class SecondaryRemover implements DiskSwap.BeanRemovalListener {
      private SecondaryRemover() {
      }

      public void beanRemovalOccured(ROID pk) {
         ReplicatedMemorySwap.this.repServ.unregister(pk, Replicatable.DEFAULT_KEY);
         ReplicatedMemorySwap.this.beanManager.removeRegisteredROIDs(pk);
      }

      // $FF: synthetic method
      SecondaryRemover(Object x1) {
         this();
      }
   }
}
