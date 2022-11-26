package weblogic.transaction.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.naming.NameAlreadyBoundException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.transaction.XIDFactory;

final class XAResourceDescriptor extends ResourceDescriptor {
   private XAResource xar;
   private boolean healthy = true;
   private int numOfActiveRequests = 0;
   private final ArrayList activeRequests = new ArrayList();
   private long lastAliveTimeMillis = -1L;
   private long lastDeadTimeMillis = -1L;
   private TransactionResourceRuntime runtimeMBean;
   private TransactionResourceRuntime partitionRuntimeMBean;
   private byte recoveryState = 3;
   private final Object resourceHealthCheckLock = new Object() {
   };
   private boolean resourceHealthCheckInProgress = false;
   private long lastResourceHealthCheckStartTimeMillis = -1L;
   private long lastResourceHealthCheckEndTimeMillis = -1L;
   private static final byte STATE_NEEDS_RECOVERY = 1;
   private static final byte STATE_RECOVERING = 2;
   private static final byte STATE_RECOVERED = 3;
   private static final int DEFAULT_MAX_REQUESTS_PER_RESOURCE = 50;
   private static int maxNumberOfActiveRequests = 50;
   private static int numOfActiveRequestsOnServer = 0;
   private static int hwmActiveRequestsOnServer = 0;
   private final HashMap migratedRDs = new HashMap(2);
   volatile Xid[] determinersXids;
   private static final Object determinersXidsLock = new Object();
   volatile Xid[] nondeterminersXids;
   private static boolean m_isResourceWithNoURLRegistered;
   private static final long DEFAULT_MAX_XA_CALL_MILLIS = 120000L;
   private static final long DEFAULT_MAX_DEAD_MILLIS = 1800000L;
   private static long maxXACallMillis = 120000L;
   private static long maxDeadMillis = 1800000L;
   private boolean serializedEnlistmentsEnabled = false;
   private static boolean performSerializedEnlistmentsGC = false;
   private static long serializedEnlistmentsGCIntervalMillis = 10000L;
   private static long lastSerializedEnlistmentsGCMillis = 0L;
   private static boolean serializedEnlistmentsGCInProgress = false;
   private final HashMap activeEnlistments = new HashMap();
   private boolean callSetTransactionTimeout = false;
   private boolean asyncTimeoutDelist = true;
   private boolean delistTMSUCCESSAlways = false;
   private boolean delistMQTMSUCCESSAlways = false;
   private boolean delistTMSUCCESSInsteadOfTMSUSPEND = false;
   private boolean supportsSetTransactionTimeout = true;
   private long recoverRetryDurationMillis = 0L;
   private long recoverRetryIntervalMillis = 30000L;
   private long recoveryInitiationTime;
   private long lastRecoverTime;
   boolean isRecoveredExceptForCommitsAndDeterminerCommits = false;
   boolean isRecoveredExceptForDeterminerCommits = false;
   private static final String RECOVER_RETRY_DURATION_MILLIS = "RecoverRetryDurationMillis";
   private static final String RECOVER_RETRY_INTERVAL_MILLIS = "RecoverRetryIntervalMillis";
   private static boolean mqSeriesWorkArounds = false;
   private static boolean localResAssignmentWA = false;
   private static boolean regTypeDynamic = false;
   private static final String LOCALLY_ASSIGNED_RESOURCE = System.getProperty("weblogic.transaction.locally.assigned.resource", "");
   private static boolean enableAsyncTimeoutDelist = Boolean.getBoolean("weblogic.transaction.enableAsyncTimeoutDelist");
   Xid[] testGetXidsToBeRolledBackReturn;
   private static volatile boolean enableResourceChecks = Boolean.getBoolean("weblogic.transaction.enableResourceChecks");
   private static final Object registerMBeanLock = new Object();

   private XAResourceDescriptor(String aName) {
      super(aName);
   }

   private void initXAResource(XAResource aXar, int resourceType) {
      boolean newResource = false;
      synchronized(resourceDescriptorLock) {
         synchronized(this) {
            if (aXar != null && !this.isRegistered()) {
               this.setXAResource(aXar);
               this.setResourceType(resourceType);
               this.setRegistered(true);
               this.setHealthy(true);
               newResource = true;
               ServerTransactionManagerImpl tm = getTM();
               Iterator txs = tm.getTransactions();

               label93:
               while(true) {
                  ArrayList resourceList;
                  do {
                     if (!txs.hasNext()) {
                        break label93;
                     }

                     ServerTransactionImpl tx = (ServerTransactionImpl)txs.next();
                     resourceList = tx.getResourceInfoList();
                  } while(resourceList == null);

                  Iterator var10 = resourceList.iterator();

                  while(var10.hasNext()) {
                     Object aResourceList = var10.next();
                     ServerResourceInfo ri = (ServerResourceInfo)aResourceList;
                     if (ri.rd.getName().equals(this.getName())) {
                        if (TxDebug.JTAXAStackTrace.isDebugEnabled()) {
                           TxDebug.debugStack(TxDebug.JTAXAStackTrace, "initXAResource  resource has been re registerd " + this.name);
                        }

                        if (ri instanceof XAServerResourceInfo) {
                           XAServerResourceInfo xri = (XAServerResourceInfo)ri;
                           xri.setReRegistered(true);
                        }

                        ri.rd = this;
                     }
                  }
               }
            }

            if (!this.checkpointed) {
               this.setIsResourceCheckpointNeeded(true);
            }

            if (aXar instanceof OptimisticPrepare) {
               this.setNeedsRecovery(getTM().getLocalCoordinatorDescriptor());
            }
         }

         if (newResource) {
            if (TxDebug.JTAXAStackTrace.isDebugEnabled()) {
               TxDebug.debugStack(TxDebug.JTAXAStackTrace, "initXAResource(aXar=" + aXar + "), xar=" + this.xar);
            }

            if ("weblogic.jdbc.wrapper.JTSXAResourceImpl".equals(aXar.getClass().getName())) {
               this.setAssignableOnlyToEnlistingSCs(true);
            }

            try {
               if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
                  JNDIAdvertiser.getInstance();
                  JNDIAdvertiser.advertiseResource(this.getName());
               }
            } catch (NameAlreadyBoundException var16) {
            } catch (Exception var17) {
               TXLogger.logAdvertiseResourceError(this.getName(), var17);
            }

            if (PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext() != null) {
               this.setComponentInvocationContext(PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext());
            }

            if (PlatformHelper.getPlatformHelper().getPartitionName() != null) {
               this.setPartitionName(PlatformHelper.getPlatformHelper().getPartitionName());
            }

            this.registerMBean();
            this.addToLocalCoordinatorDescriptor();
         }

      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.toString()).append("xar = ").append(this.xar).append("\n").append("healthy = ").append(this.healthy).append("\n").append("isDeterminer = ").append(this.isDeterminer()).append("\n").append("isDeterminerFromCheckpoint = ").append(this.isDeterminerFromCheckpoint()).append("\n").append("lastAliveTimeMillis = ").append(this.lastAliveTimeMillis).append("\n").append("numActiveRequests = ").append(numOfActiveRequestsOnServer).append("\n").append("recoveryState = ").append(this.getRecoveryStateStringForByteConstant(this.recoveryState)).append("\n");
      return sb.toString();
   }

   public String getRecoveryStateStringForByteConstant(byte recoveryState) {
      switch (recoveryState) {
         case 1:
            return "STATE_NEEDS_RECOVERY(1)";
         case 2:
            return "STATE_RECOVERING(2)";
         case 3:
            return "STATE_RECOVERED(3)";
         default:
            return "statie unknown:" + recoveryState;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof XAResourceDescriptor)) {
         return false;
      } else {
         XAResourceDescriptor that = (XAResourceDescriptor)o;
         return this.getName().equals(that.getName());
      }
   }

   static void registerResource(String name, XAResource aXar, int resourceType) {
      registerResource(name, aXar, resourceType, false);
   }

   static void registerResource(String name, XAResource aXar, int resourceType, boolean localResourceAssignment) {
      doRegisterResource(name, aXar, resourceType, localResourceAssignment);
      String[] determiners = getTM().getDeterminers();
      String[] var5 = determiners;
      int var6 = determiners.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String determiner = var5[var7];
         if (determiner != null && determiner.equals(name)) {
            getTM().removeFromUnRegisteredDeterminerList(name);
         }
      }

   }

   private static void doRegisterResource(String name, XAResource aXar, int resourceType, boolean localResourceAssignment) {
      if (!(aXar instanceof IgnoreXAResource)) {
         vendorWorkarounds(name, aXar);
         if (regTypeDynamic) {
            resourceType = 2;
         }

         XAResourceDescriptor xard = null;
         synchronized(resourceDescriptorLock) {
            ResourceDescriptor rd = get(name);
            if (rd != null && !(rd instanceof XAResourceDescriptor)) {
               TXLogger.logLookingForResourceDescriptorFailure(name, rd.getSCsToString());
               String msg = "Registration of resource " + name + " failed";
               String cause = "This resource is trying to be registered as a XAResource and was already registered as a NonXAResource, there is a naming conflict.";
               SystemException se = new SystemException(msg);
               se.initCause(new ClassCastException(cause));
               rd.setResourceDescriptorNamingConflict(se);
               return;
            }

            xard = (XAResourceDescriptor)rd;
            if (xard != null) {
               xard.initXAResource(aXar, resourceType);
            } else {
               xard = (XAResourceDescriptor)create(name, aXar, resourceType);
            }
         }

         if (aXar != null) {
            xard.addSC(getTM().getLocalCoordinatorDescriptor());
         }

         if (TxDebug.JTAXAStackTrace.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTAXA, "ResourceDescriptor[" + xard.getName() + "]register");
         }

         if (localResourceAssignment || localResAssignmentWA) {
            xard.setAssignableOnlyToEnlistingSCs(true);
         }

         String xarName = aXar.getClass().getName();
         if (mqSeriesWorkArounds) {
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("Register found MQSeries Resource: " + xarName + " enableAsyncTimeoutDelist:" + enableAsyncTimeoutDelist + " and DelistTMSUCCESSInsteadOfTMSUSPEND");
            }

            if (enableAsyncTimeoutDelist) {
               xard.setAsyncTimeoutDelist(true);
            }

            xard.setDelistTMSUCCESSInsteadOfTMSUSPEND(true);
         }

      }
   }

   static ResourceDescriptor getOrCreate(XAResource aXar) {
      XAResourceDescriptor rd = (XAResourceDescriptor)get(aXar);
      if (rd != null) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("ResourceDescriptor[" + rd.getName() + "]: getOrCreate gets rd: " + rd);
         }

         return rd;
      } else {
         if (aXar != null) {
            String xarName = aXar.getClass().getName();
            rd = (XAResourceDescriptor)get(xarName);
            if (rd != null) {
               if (TxDebug.JTAXA.isDebugEnabled()) {
                  TxDebug.JTAXA.debug("ResourceDescriptor[" + rd.getName() + "]: getOrCreate gets rd: " + rd);
               }

               rd.initXAResource(aXar, 2);
               return rd;
            }

            rd = (XAResourceDescriptor)create(xarName, aXar, 2);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("ResourceDescriptor[" + rd.getName() + "]: getOrCreate creates rd: " + rd);
            }
         }

         return rd;
      }
   }

   static ResourceDescriptor getOrCreate(String name) {
      ResourceDescriptor rd = get(name);
      if (rd != null) {
         return rd;
      } else {
         rd = create(name, (XAResource)null, 2);
         return rd;
      }
   }

   static ResourceDescriptor getOrCreateForRecovery(String name) {
      ResourceDescriptor rd = get(name);
      if (rd != null) {
         return rd;
      } else {
         rd = create(name, (XAResource)null, 2);
         return rd;
      }
   }

   static ResourceDescriptor getOrCreateForMigratedTLog(String name, CoordinatorDescriptor cd) {
      XAResourceDescriptor rd = (XAResourceDescriptor)getOrCreateForRecovery(name);
      rd.addMigratedCoordinatorDescriptor(cd);
      return rd;
   }

   static void getOrCreateForJMSMigratedTLog(CoordinatorDescriptor cd) {
      ArrayList rdList = ResourceDescriptor.getResourceDescriptorList();
      if (rdList != null) {
         for(int i = 0; i < rdList.size(); ++i) {
            ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
            TxDebug.JTAMigration.debug("getOrCreateForJMSMigratedTLog rd = [" + rd + "]");
            String name = rd.getName();
            if (name != null && name.startsWith("JMS_")) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("getOrCreateForJMSMigratedTLog name=" + name + " rd=" + rd);
               }

               XAResourceDescriptor xard = (XAResourceDescriptor)rd;
               xard.addMigratedCoordinatorDescriptor(cd);
            }
         }

      }
   }

   void addMigratedCoordinatorDescriptor(CoordinatorDescriptor cd) {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("addMigratedCD(cd=" + cd + ")");
      }

      synchronized(this.migratedRDs) {
         MigratedRDState state = (MigratedRDState)this.migratedRDs.get(cd);
         if (state == null) {
            this.migratedRDs.put(cd, new MigratedRDState());
         }
      }
   }

   static void releaseMigratedTLog(CoordinatorDescriptor cd) {
      ArrayList rdList = getResourceDescriptorList();
      if (rdList != null) {
         int len = rdList.size();

         for(int i = 0; i < len; ++i) {
            ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
            if (rd instanceof XAResourceDescriptor) {
               XAResourceDescriptor xard = (XAResourceDescriptor)rd;
               xard.removeMigratedCoordinatorDescriptor(cd);
            }
         }

      }
   }

   private void removeMigratedCoordinatorDescriptor(CoordinatorDescriptor cd) {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("removeMigratedCD(cd=" + cd + ")");
      }

      synchronized(this.migratedRDs) {
         this.migratedRDs.remove(cd);
      }
   }

   XAResource getXAResource() {
      return this.xar;
   }

   TransactionResourceRuntime getRuntimeMBean() {
      return this.runtimeMBean;
   }

   boolean needsStaticEnlistment(boolean enlistOOAlso) {
      return (this.resourceType == 1 || enlistOOAlso && this.resourceType == 3) && this.isRegistered();
   }

   boolean isHealthy() {
      return this.healthy;
   }

   boolean isRegistered() {
      return this.registered;
   }

   int getNumberOfActiveRequests() {
      return this.numOfActiveRequests;
   }

   long getLastAliveTimeMillis() {
      return this.lastAliveTimeMillis;
   }

   long getLastDeadTimeMillis() {
      return this.lastDeadTimeMillis;
   }

   int getResourceType() {
      return this.resourceType;
   }

   boolean isAccessibleAt(CoordinatorDescriptor aCoDesc) {
      return this.isAccessibleAt(aCoDesc, false);
   }

   boolean isAccessibleAt(CoordinatorDescriptor aCoDesc, boolean isResourceNotFound) {
      if (aCoDesc.equals(getTM().getLocalCoordinatorDescriptor())) {
         if (this.getXAResource() == null) {
            return false;
         } else {
            return !this.isRegistered() ? false : this.isHealthy();
         }
      } else if (isResourceNotFound && enableResourceChecks) {
         String serverURL = PlatformHelper.getPlatformHelper().getTargetChannelURL(aCoDesc);
         if (serverURL == null) {
            serverURL = CoordinatorDescriptor.getServerURL(aCoDesc.getCoordinatorURL());
         }

         return this.isAvailableAtSC(aCoDesc) && PlatformHelper.getPlatformHelper().resourceCheck(this.getName(), aCoDesc.getServerName(), serverURL);
      } else {
         return this.isAvailableAtSC(aCoDesc);
      }
   }

   static int getHWMActiveRequestsOnServer() {
      return hwmActiveRequestsOnServer;
   }

   static int getNumberOfActiveRequestsOnServer() {
      return numOfActiveRequestsOnServer;
   }

   final ServerResourceInfo createServerResourceInfo() {
      return new XAServerResourceInfo(this);
   }

   XAResource startResourceUse(String reqInfo) throws XAException {
      return this.startResourceUse((TransactionImpl)null, reqInfo);
   }

   XAResource startResourceUse(TransactionImpl tx, String reqInfo) throws XAException {
      boolean timeout = false;
      long ttl = maxXACallMillis;
      if (tx != null) {
         ttl = tx.getTimeToLiveMillis();
         timeout = ttl <= 0L;
         if (ttl < 0L) {
            ttl = 0L;
         }
      }

      this.checkResource(tx);
      synchronized(this.requestLock) {
         if (this.numOfActiveRequests >= maxNumberOfActiveRequests && !timeout) {
            try {
               this.requestLock.wait(ttl);
            } catch (InterruptedException var11) {
            }
         }

         if (tx != null) {
            timeout = tx.getTimeToLiveMillis() <= 0L;
         }

         if (this.isHealthy() && this.isRegistered() && !timeout) {
            if (this.numOfActiveRequests == 0) {
               this.lastAliveTimeMillis = System.currentTimeMillis();
            }

            ++this.numOfActiveRequests;
            if (this.numOfActiveRequests > hwmActiveRequestsOnServer) {
               hwmActiveRequestsOnServer = this.numOfActiveRequests;
            }
         }
      }

      if (reqInfo != null && TxDebug.JTAResourceHealth.isDebugEnabled()) {
         synchronized(this.activeRequests) {
            this.activeRequests.add(reqInfo);
         }
      }

      if (TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.JTAXA.debug("startResourceUse, Number of active requests:" + this.numOfActiveRequests + ", last alive time:" + (System.currentTimeMillis() - this.lastAliveTimeMillis) + " ms ago.");
      }

      if (timeout) {
         String msg = "Transaction has timed out when making request to XAResource '" + this.getName() + "'.";
         XAException ex = new ResourceAccessException(msg);
         ex.initCause(new TimedOutException(msg));
         ex.errorCode = -3;
         if (tx != null) {
            tx.setRollbackReason(new TimedOutException(msg));
         }

         throw ex;
      } else {
         return this.xar;
      }
   }

   void endResourceUse(XAException xae, String reqInfo) {
      boolean available = xae == null || xae.errorCode != -7;
      synchronized(this.requestLock) {
         --this.numOfActiveRequests;
         this.requestLock.notify();
      }

      if (available) {
         this.lastAliveTimeMillis = System.currentTimeMillis();
      }

      if (TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.JTAXA.debug("endResourceUse, Number of active requests:" + this.numOfActiveRequests);
      }

      if (reqInfo != null && TxDebug.JTAResourceHealth.isDebugEnabled()) {
         synchronized(this.activeRequests) {
            this.activeRequests.remove(reqInfo);
         }
      }

      if (this.healthy && !available && this.asyncCheckResourceHealth()) {
         this.setHealthy(false);
         TXLogger.logResourceUnavailable(this.name);
      }

   }

   void unregister() {
      if (TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.debugStack(TxDebug.JTAXA, "unregister");
      }

      JNDIAdvertiser.unAdvertiseResource(this.getName());
      this.unregisterMBean();
      this.removeFromLocalCoordinatorDescriptor();
      if (this.hasPendingUsages() && TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.JTAXA.debug("unregisterMBean for resource: " + this.getName() + " numOfActiveRequests: " + this.numOfActiveRequests);
      }

   }

   static ResourceDescriptor get(XAResource aXar) {
      if (aXar == null) {
         return null;
      } else {
         ArrayList rdList = getResourceDescriptorList();
         if (rdList == null) {
            return null;
         } else {
            int len = rdList.size();

            for(int i = 0; i < len; ++i) {
               ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
               if (rd instanceof XAResourceDescriptor) {
                  XAResourceDescriptor xard = (XAResourceDescriptor)rd;
                  XAResource rdxar = xard.xar;

                  try {
                     if (rdxar != null && (rdxar.equals(aXar) || rdxar.isSameRM(aXar))) {
                        return xard;
                     }
                  } catch (XAException var8) {
                  }
               }
            }

            return null;
         }
      }
   }

   static ResourceDescriptor get(String name) {
      ArrayList rdList = getResourceDescriptorList();
      if (rdList == null) {
         return null;
      } else {
         ResourceDescriptor rd = null;
         int len = rdList.size();

         for(int i = 0; i < len; ++i) {
            rd = (ResourceDescriptor)rdList.get(i);
            if (rd.name.equals(name)) {
               return rd;
            }
         }

         return null;
      }
   }

   static void checkAllResourceHealth() {
      ResourceDescriptor rd = null;
      ArrayList rdList = getResourceDescriptorList();
      if (rdList != null) {
         int len = rdList.size();

         for(int i = 0; i < len; ++i) {
            rd = (ResourceDescriptor)rdList.get(i);
            if (rd instanceof XAResourceDescriptor) {
               XAResourceDescriptor xard = (XAResourceDescriptor)rd;
               if (!xard.checkResourceHealth()) {
                  synchronized(xard.requestLock) {
                     xard.requestLock.notifyAll();
                  }
               }
            }
         }

      }
   }

   static void checkRecovery() throws IOException {
      ArrayList rdList = ResourceDescriptor.getResourceDescriptorList();
      if (rdList != null) {
         if (!isDeterminersProcessed) {
            getTM().setDeterminers(getTM().getDeterminers());
            isDeterminersProcessed = true;
         }

         String[] determiners = getTM().getDeterminers();
         boolean areDeterminersConfigured = determiners != null && determiners.length > 0;
         if (!areDeterminersConfigured) {
            checkRecoveryWithoutDeterminers(rdList);
         } else {
            TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecovery, " + determiners.length + " determiner(s) configured");
            checkRecoveryWithDeterminers(rdList);
         }

      }
   }

   private static void checkRecoveryWithoutDeterminers(ArrayList rdList) {
      Iterator var1 = rdList.iterator();

      while(var1.hasNext()) {
         Object aRdList = var1.next();
         ResourceDescriptor rd = (ResourceDescriptor)aRdList;
         if (rd instanceof XAResourceDescriptor) {
            XAResourceDescriptor xaResourceDescriptor = (XAResourceDescriptor)rd;
            xaResourceDescriptor.checkRecoveryForLocalCoordinator();
            xaResourceDescriptor.checkRecoveryForMigratedCoordinators();
         }
      }

   }

   private static void checkRecoveryWithDeterminers(ArrayList rdList) throws IOException {
      List[] nonDeterminer0AndDeterminer1ListArray = processDeterminersAndGetCommitXids(rdList);
      HashMap determinerToCommitXidMap = createDeterminerNameToCommitXidMap(nonDeterminer0AndDeterminer1ListArray[1]);
      Iterator iterator = nonDeterminer0AndDeterminer1ListArray[0].iterator();
      TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecoveryWithDeterminers migrated non-determiner size:" + nonDeterminer0AndDeterminer1ListArray[0].size());

      while(iterator.hasNext()) {
         Object next = iterator.next();
         TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecoveryWithDeterminers migrated non-determiner added:" + next);
         synchronized(resourceDescriptorLock) {
            ArrayList clone = (ArrayList)rdList.clone();
            clone.add(next);
            rdList = clone;
         }
      }

      processAllResourcesPassingCommitXids(rdList, determinerToCommitXidMap);
      boolean isReadyToCallCommitDeterminers = true;
      Iterator var5 = rdList.iterator();

      while(var5.hasNext()) {
         Object aRdList = var5.next();
         ResourceDescriptor rd = (ResourceDescriptor)aRdList;
         if (rd instanceof XAResourceDescriptor) {
            XAResourceDescriptor xard = (XAResourceDescriptor)rd;
            if (!xard.isRecoveredExceptForDeterminerCommits && xard.needsRecoveryOrIsStillRecovering()) {
               isReadyToCallCommitDeterminers = false;
            }
         }
      }

      if (isReadyToCallCommitDeterminers) {
         commitDeterminers(nonDeterminer0AndDeterminer1ListArray[1], determinerToCommitXidMap);
      }

   }

   private static List[] processDeterminersAndGetCommitXids(ArrayList rdList) {
      ArrayList determinerList = new ArrayList();
      ArrayList nonDeterminerList = new ArrayList();
      Iterator var3 = rdList.iterator();

      while(true) {
         while(true) {
            Object aRdList;
            ResourceDescriptor rd;
            XAResourceDescriptor xard;
            boolean isMigrationRecoveryNecessary;
            do {
               do {
                  if (!var3.hasNext()) {
                     return new ArrayList[]{nonDeterminerList, determinerList};
                  }

                  aRdList = var3.next();
               } while(!(aRdList instanceof XAResourceDescriptor));

               rd = (ResourceDescriptor)aRdList;
               xard = (XAResourceDescriptor)rd;
               isMigrationRecoveryNecessary = xard.checkRecoveryForMigratedCoordinatorsDeterminer();
               if (isMigrationRecoveryNecessary) {
                  TxDebug.JTARecovery.debug("XAResourceDcescriptor.processDeterminersAndGetCommitXids isMigrationRecoveryNecessary:" + isMigrationRecoveryNecessary + " for xard:" + xard);
               }
            } while(!isMigrationRecoveryNecessary && !((XAResourceDescriptor)aRdList).needsRecoveryOrIsStillRecovering());

            boolean isDeterminerFromCheckpointAndStillInDeterminerConfig = false;
            if (rd.isDeterminerFromCheckpoint()) {
               String[] determiners = getTM().getDeterminers();
               String[] var10 = determiners;
               int var11 = determiners.length;
               int var12 = 0;

               while(true) {
                  if (var12 >= var11) {
                     if (!isDeterminerFromCheckpointAndStillInDeterminerConfig) {
                        rd.setDeterminer(false);
                        rd.setDeterminerFromCheckpointFalse();
                        rd.setIsResourceCheckpointNeeded(true);
                        getTM().removeFromUnRegisteredDeterminerList(rd.getName());
                     }
                     break;
                  }

                  String determiner = var10[var12];
                  if (rd.getName().equals(determiner) || "WebLogic_JMS".equals(determiner)) {
                     isDeterminerFromCheckpointAndStillInDeterminerConfig = true;
                  }

                  ++var12;
               }
            }

            if (!rd.isDeterminer() && !isDeterminerFromCheckpointAndStillInDeterminerConfig) {
               TxDebug.JTARecovery.debug("XAResourceDescriptor isDeterminer rd:" + rd);
               if (isMigrationRecoveryNecessary) {
                  TxDebug.JTARecovery.debug("XAResourceDescriptor STATE_NEEDS_RECOVERY and add to migrated non-determiner list rd.getName:" + rd.getName());
                  xard.setRecoveryState((byte)1);
                  nonDeterminerList.add(xard);
               }
            } else {
               TxDebug.JTARecovery.debug("XAResourceDcescriptor.processDeterminersAndGetCommitXids determinerList.add xard:" + xard);
               determinerList.add(xard);
               xard.checkRecoveryForLocalCoordinatorDeterminer();
            }
         }
      }
   }

   private static HashMap createDeterminerNameToCommitXidMap(List determinerList) throws IOException {
      HashMap determinerToCommitXidMap = new HashMap(determinerList.size());
      int intervalsSpentWaitingForCheckRecoveryForLocalCoordinatorDeterminer = 0;

      for(long endTimeForMaxRetrySecondsBeforeDeterminerFail = System.currentTimeMillis() + (long)(getTM().getMaxRetrySecondsBeforeDeterminerFail() * 1000); determinerList != null && determinerToCommitXidMap.size() != determinerList.size(); ++intervalsSpentWaitingForCheckRecoveryForLocalCoordinatorDeterminer) {
         if (intervalsSpentWaitingForCheckRecoveryForLocalCoordinatorDeterminer > 0) {
            TxDebug.JTARecovery.debug("createDeterminerNameToCommitXidMap: Sleeping for 3 seconds before checking if recovery for local coordinator determiner is finished for " + getTM().getLocalCoordinatorURL() + "determinerToCommitXidMap.size()=" + determinerToCommitXidMap.size() + "determinerList.size()=" + determinerList.size() + ". This is wait interval number " + intervalsSpentWaitingForCheckRecoveryForLocalCoordinatorDeterminer + " getTM().getMaxRetrySecondsBeforeDeterminerFail():" + getTM().getMaxRetrySecondsBeforeDeterminerFail());
            processPotentiallyMigratedJMS(determinerList);
            if (System.currentTimeMillis() > endTimeForMaxRetrySecondsBeforeDeterminerFail) {
               String[] determiners = getTM().getDeterminers();
               String determinersString = "";
               String determinerXAResourceDescriptorString = "";
               String[] var8 = determiners;
               int var9 = determiners.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  String determiner = var8[var10];
                  determinersString = determinersString + determiner + " ";
               }

               XAResourceDescriptor determinerXAResourceDescriptor;
               for(Iterator var15 = determinerList.iterator(); var15.hasNext(); determinerXAResourceDescriptorString = determinerXAResourceDescriptorString + determinerXAResourceDescriptor.xar + " ") {
                  determinerXAResourceDescriptor = (XAResourceDescriptor)var15.next();
                  determinerXAResourceDescriptorString = determinerXAResourceDescriptorString + determinerXAResourceDescriptor + " xar:";
               }

               IOException ioException = new IOException("Unable to process all determiners configured." + determinerToCommitXidMap.size() + " out of " + determinerList.size() + " determiners processed, all determiners:" + determinersString + " all determinerXAResourceDescriptors:" + determinerXAResourceDescriptorString + "isResourceWithNoURLRegistered:" + m_isResourceWithNoURLRegistered);
               getTM().registerFailedPrimaryStore(ioException);
               TXLogger.logUnableToProcessDeterminer(ioException);
               throw ioException;
            }

            try {
               Thread.sleep(3000L);
            } catch (InterruptedException var12) {
            }
         }

         Iterator var5 = determinerList.iterator();

         while(var5.hasNext()) {
            XAResourceDescriptor xaResourceDescriptor = (XAResourceDescriptor)var5.next();
            if (xaResourceDescriptor.determinersXids != null) {
               determinerToCommitXidMap.put(xaResourceDescriptor.getName(), xaResourceDescriptor.determinersXids);
            }
         }
      }

      return determinerToCommitXidMap;
   }

   private static void processPotentiallyMigratedJMS(List determinerList) {
      Iterator var1 = determinerList.iterator();

      while(true) {
         XAResourceDescriptor determinerXAResourceDescriptor;
         do {
            if (!var1.hasNext()) {
               return;
            }

            determinerXAResourceDescriptor = (XAResourceDescriptor)var1.next();
            if (determinerXAResourceDescriptor.xar == null) {
               determinerXAResourceDescriptor.xar = PlatformHelper.getPlatformHelper().findXAResourceInClusterByRemoteJNDI(determinerXAResourceDescriptor.getName(), (Collection)null);
            }

            TxDebug.JTARecovery.debug("processPotentiallyMigratedJMSTEST determinerXAResourceDescriptor:" + determinerXAResourceDescriptor + " determinerXAResourceDescriptor.xar:" + determinerXAResourceDescriptor.xar);
         } while(determinerXAResourceDescriptor.xar == null);

         try {
            Xid[] xids = determinerXAResourceDescriptor.xar.recover(16777216);
            if (xids != null) {
               Set determinerXidsSet = new HashSet();
               Xid[] determinerXids = xids;
               int arrayIndex = xids.length;

               Xid xid;
               for(int var7 = 0; var7 < arrayIndex; ++var7) {
                  xid = determinerXids[var7];
                  TxDebug.JTARecovery.debug("processPotentiallyMigratedJMS determinerXAResourceDescriptor:" + determinerXAResourceDescriptor + " determinerXAResourceDescriptor.xar:" + determinerXAResourceDescriptor.xar + " xid from recover:" + xid);
                  if (isXidDeterminer(xid)) {
                     determinerXidsSet.add(xid);
                  }
               }

               determinerXids = new Xid[determinerXidsSet.size()];
               arrayIndex = 0;

               for(Iterator var10 = determinerXidsSet.iterator(); var10.hasNext(); determinerXids[arrayIndex++] = xid) {
                  xid = (Xid)var10.next();
               }

               if (determinerXAResourceDescriptor.determinersXids == null) {
                  determinerXAResourceDescriptor.setDeterminerXids(determinerXids);
               } else {
                  determinerXAResourceDescriptor.setOrAddToDeterminerXids(determinerXids);
               }
            }
         } catch (XAException var9) {
            var9.printStackTrace();
         }
      }
   }

   private static void processAllResourcesPassingCommitXids(ArrayList resourceDescriptorList, HashMap determinerToCommitXidMap) {
      TxDebug.JTARecovery.debug("processAllResourcesPassingCommitXids: Processing all resources (determiners and non-determiners) passing in HashMap<String, Xid[]> determinerToCommitXidMap for " + getTM().getLocalCoordinatorURL() + " resourceDescriptorList.size():" + resourceDescriptorList.size() + " determinerToCommitXidMap.size():" + (determinerToCommitXidMap == null ? "null" : determinerToCommitXidMap.size()));
      Iterator var2 = resourceDescriptorList.iterator();

      while(true) {
         XAResourceDescriptor xard;
         boolean isMigratedRDsNeedsRecovery;
         do {
            ResourceDescriptor rd;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               Object aRdList = var2.next();
               rd = (ResourceDescriptor)aRdList;
            } while(!(rd instanceof XAResourceDescriptor));

            xard = null;
            xard = (XAResourceDescriptor)rd;
            isMigratedRDsNeedsRecovery = isMigratedRDSNeedsRecovery(xard);
         } while(!xard.needsRecovery() && !isMigratedRDsNeedsRecovery);

         TxDebug.JTARecovery.debug("processAllResourcesPassingCommitXids: xard: " + xard + " xard.needsRecovery():" + xard.needsRecovery() + " isMigratedRDsNeedsRecovery:" + isMigratedRDsNeedsRecovery + " xard.determinersXids.length:" + (xard.determinersXids == null ? "null" : xard.determinersXids.length));
         xard.checkRecoveryForLocalCoordinator(determinerToCommitXidMap, false);
         xard.checkRecoveryForMigratedCoordinators(determinerToCommitXidMap, false);
      }
   }

   static boolean isMigratedRDSNeedsRecovery(XAResourceDescriptor xard) {
      if (xard.migratedRDs != null && xard.migratedRDs.size() >= 1) {
         boolean isMigratedRDsNeedsRecovery = false;
         synchronized(xard.migratedRDs) {
            MigratedRDState state;
            for(Iterator var3 = xard.migratedRDs.entrySet().iterator(); var3.hasNext(); TxDebug.JTARecovery.debug("isMigratedRDSNeedsRecovery state:" + state + " state.needsRecovery() isMigratedRDsNeedsRecovery:" + state.needsRecovery() + " isMigratedRDsNeedsRecovery:" + isMigratedRDsNeedsRecovery + " xard:" + xard)) {
               Map.Entry entry = (Map.Entry)var3.next();
               state = (MigratedRDState)entry.getValue();
               if (state.needsRecovery()) {
                  isMigratedRDsNeedsRecovery = true;
               }
            }

            return isMigratedRDsNeedsRecovery;
         }
      } else {
         return false;
      }
   }

   private static void commitDeterminers(List determinerList, HashMap determinerToCommitXidMap) {
      Iterator var2 = determinerList.iterator();

      while(var2.hasNext()) {
         Object aRdList = var2.next();
         ResourceDescriptor determinerResourceDescriptor = (ResourceDescriptor)aRdList;
         TxDebug.JTARecovery.debug("XAResourceDescriptor.commitDeterminers determinerList = [" + determinerList + "], determinerToCommitXidMap = [" + determinerToCommitXidMap + "] determinerResourceDescriptor instanceof XAResourceDescriptor:" + (determinerResourceDescriptor instanceof XAResourceDescriptor) + (determinerResourceDescriptor instanceof XAResourceDescriptor ? "((XAResourceDescriptor)determinerResourceDescriptor).needsRecovery() (XAResourceDescriptor)determinerResourceDescriptor).needsRecovery():" + ((XAResourceDescriptor)determinerResourceDescriptor).needsRecovery() : "!((XAResourceDescriptor)determinerResourceDescriptor).needsRecovery()"));
         if (determinerResourceDescriptor instanceof XAResourceDescriptor) {
            XAResourceDescriptor xard = (XAResourceDescriptor)determinerResourceDescriptor;
            if (determinerResourceDescriptor.getName().equals(xard.getName())) {
               TxDebug.JTARecovery.debug("XAResourceDescriptor.commitDeterminers determinerResourceDescriptor.getName().equals(xard.getName()) about to checkRecoveryForLocalCoordinator");
               xard.checkRecoveryForLocalCoordinator(determinerToCommitXidMap, true);
               xard.setRecovered(getTM().getLocalCoordinatorDescriptor());
            } else {
               TxDebug.JTARecovery.debug("XAResourceDescriptor.commitDeterminers determinerResourceDescriptor.getName(): " + determinerResourceDescriptor.getName() + "does not equal xard.getName():" + xard.getName() + "about to checkRecoveryForLocalCoordinator");
            }
         }
      }

   }

   private void checkRecoveryForLocalCoordinator(final HashMap determinerToCommitXidMap, boolean isCommitDeterminers) {
      this.setRecovering();
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         try {
            TxDebug.JTARecovery.debug("checkRecoveryForLocalCoordinator: Schedule execute request for " + getTM().getLocalCoordinatorURL() + this.getName());
         } catch (Exception var4) {
         }
      }

      if (isCommitDeterminers) {
         this.recover(getTM().getLocalCoordinatorDescriptor(), determinerToCommitXidMap, true);
      } else {
         Runnable recoverRunnable = new Runnable() {
            public void run() {
               XAResourceDescriptor.this.recover(ResourceDescriptor.getTM().getLocalCoordinatorDescriptor(), determinerToCommitXidMap, false);
            }
         };
         PlatformHelper.getPlatformHelper().scheduleWork(recoverRunnable);
      }

   }

   private void checkRecoveryForLocalCoordinator() {
      if (this.needsRecovery()) {
         this.setRecovering();
         if (TxDebug.JTARecovery.isDebugEnabled()) {
            try {
               TxDebug.JTARecovery.debug("recover: Schedule execute request for " + getTM().getLocalCoordinatorURL() + this.getName());
            } catch (Exception var2) {
            }
         }

         PlatformHelper.getPlatformHelper().scheduleWork(new Runnable() {
            public void run() {
               XAResourceDescriptor.this.recover(ResourceDescriptor.getTM().getLocalCoordinatorDescriptor());
            }
         });
      }
   }

   private Xid[] checkRecoveryForLocalCoordinatorDeterminer() {
      if (!this.needsRecovery()) {
         return null;
      } else {
         this.setRecovering();
         if (TxDebug.JTARecovery.isDebugEnabled()) {
            try {
               TxDebug.JTARecovery.debug("checkRecoveryForLocalCoordinatorDeterminer: Schedule execute request for " + getTM().getLocalCoordinatorURL() + this.getName());
            } catch (Exception var2) {
            }
         }

         return this.processDeterminerForRecovery(getTM().getLocalCoordinatorDescriptor());
      }
   }

   private void checkRecoveryForMigratedCoordinators() {
      this.checkRecoveryForMigratedCoordinators((HashMap)null, false);
   }

   private void checkRecoveryForMigratedCoordinators(final HashMap determinerToCommitXidMap, final boolean isCommitDeterminers) {
      synchronized(this.migratedRDs) {
         Iterator iter = this.migratedRDs.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            final CoordinatorDescriptor cd = (CoordinatorDescriptor)entry.getKey();
            MigratedRDState state = (MigratedRDState)entry.getValue();
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug("checkRecoveryForMigratedCoordinators: cd=" + cd + ", state=" + state.getRecoveryState());
            }

            if (state.needsRecovery()) {
               state.setRecovering();
               if (TxDebug.JTARecovery.isDebugEnabled()) {
                  TxDebug.JTARecovery.debug("recover: Schedule execute request for " + cd.getCoordinatorURL() + this.getName());
               }

               PlatformHelper.getPlatformHelper().scheduleWork(new Runnable() {
                  public void run() {
                     XAResourceDescriptor.this.recover(cd, determinerToCommitXidMap, isCommitDeterminers);
                  }
               });
            }
         }

      }
   }

   private boolean checkRecoveryForMigratedCoordinatorsDeterminer() {
      boolean isMigrationRecoveryNecessary = false;
      if (this.migratedRDs.size() > 0) {
         TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecoveryForMigratedCoordinatorsDeterminer migratedRDs.size():" + this.migratedRDs.size());
      }

      synchronized(this.migratedRDs) {
         Iterator var3 = this.migratedRDs.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            CoordinatorDescriptor cd = (CoordinatorDescriptor)entry.getKey();
            TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecoveryForMigratedCoordinatorsDeterminer CoordinatorDescriptor cd:" + cd);
            MigratedRDState state = (MigratedRDState)entry.getValue();
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug("checkRecoveryForMigratedCoordinatorsDeterminer: cd=" + cd + ", state=" + state.getRecoveryState());
            }

            String serverName = cd.getServerName();
            JTARecoveryRuntime rtMBean = PlatformHelper.getPlatformHelper().getJTARecoveryRuntime(serverName);
            if (rtMBean != null && !state.needsRecovery() && rtMBean.getInitialRecoveredUnloggedTransactionTotalCount() > 0 && rtMBean.getRecoveredUnloggedTransactionCompletionPercent() != 100) {
               rtMBean.setFinalUnloggedTransactionCompletionCount(0);
            }

            if (state.needsRecovery()) {
               isMigrationRecoveryNecessary = true;
               state.setRecovering();
               if (TxDebug.JTARecovery.isDebugEnabled()) {
                  TxDebug.JTARecovery.debug("recover with determiner: Schedule execute request for " + cd.getCoordinatorURL() + this.getName());
               }

               TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecoveryForMigratedCoordinatorsDeterminer about to processDeterminerForRecovery for CoordinatorDescriptor cd:" + cd);
               this.processDeterminerForRecovery(cd);
               TxDebug.JTARecovery.debug("XAResourceDescriptor.checkRecoveryForMigratedCoordinatorsDeterminer finished processDeterminerForRecovery for CoordinatorDescriptor cd:" + cd);
               if (rtMBean != null) {
                  int unloggedTxCount = totalDeterminersXidsLengthForCoor(cd);
                  if (unloggedTxCount > rtMBean.getInitialRecoveredUnloggedTransactionTotalCount()) {
                     rtMBean.resetUnlogged(unloggedTxCount);
                  }
               }
            }
         }

         return isMigrationRecoveryNecessary;
      }
   }

   void setNeedsRecovery(CoordinatorDescriptor cd) {
      if (cd.equals(getTM().getLocalCoordinatorDescriptor())) {
         this.setRecoveryState((byte)1);
      } else {
         MigratedRDState state = (MigratedRDState)this.migratedRDs.get(cd);
         if (state == null) {
            TxDebug.JTARecovery.debug("XAResourceDescriptor.setNeedsRecovery MigratedRDState state is null for cd:" + cd + " migratedRDs:" + this.migratedRDs.size());
         } else {
            TxDebug.JTARecovery.debug("XAResourceDescriptor.setNeedsRecovery state is not null calling setNeedsRecovery on state :" + state + " migratedRDs.size():" + this.migratedRDs.size());
            state.setNeedsRecovery();
         }
      }

   }

   private void setRecoveryState(byte theRecoveryState) {
      this.recoveryState = theRecoveryState;
   }

   void setCoordinatedLocally() {
      super.setCoordinatedLocally();
      this.setLastAccessTimeMillis(System.currentTimeMillis());
   }

   static void setMaxXACallMillis(long max) {
      maxXACallMillis = max;
   }

   static void setMaxResourceUnavailableMillis(long max) {
      maxDeadMillis = max;
   }

   static void setMaxResourceRequestsOnServer(int max) {
      maxNumberOfActiveRequests = max;
   }

   void tallyCompletion(ServerResourceInfo aResourceInfo, Exception aReason) {
      if (this.runtimeMBean != null) {
         this.runtimeMBean.tallyCompletion(aResourceInfo, (XAException)aReason);
      }

      String partitionName = this.getPartitionName();
      if (partitionName != null && this.partitionRuntimeMBean != null) {
         this.partitionRuntimeMBean.tallyCompletion(aResourceInfo, (XAException)aReason);
      }

   }

   private static ResourceDescriptor create(String name, XAResource aXar, int resourceType) {
      XAResourceDescriptor rdnew = new XAResourceDescriptor(name);
      synchronized(resourceDescriptorLock) {
         XAResourceDescriptor rd = (XAResourceDescriptor)get(name);
         if (rd != null) {
            rd.initXAResource(aXar, resourceType);
            return rd;
         } else {
            ArrayList clone = (ArrayList)resourceDescriptorList.clone();
            clone.add(rdnew);
            resourceDescriptorList = clone;
            rdnew.initXAResource(aXar, resourceType);
            return rdnew;
         }
      }
   }

   protected boolean includeInCheckpoint() {
      this.checkpointed = this.lastAccessTimeMillis == -1L || this.needsRecovery() || System.currentTimeMillis() - this.lastAccessTimeMillis < (long)purgeResourceFromCheckpointIntervalSeconds * 1000L;
      return this.checkpointed;
   }

   private void checkResource(TransactionImpl tx) throws XAException {
      String msg = null;
      int errorCode = 0;
      if (this.xar == null) {
         msg = "Internal error: No XAResource object found for XA  resource '" + this.name + "'";
         errorCode = -7;
         if (tx != null) {
            tx.setProperty("weblogic.transaction.resourceNotFoundName", this.name);
            tx.setResourceNotFoundTrue();
         }
      } else if (!this.healthy) {
         msg = "Internal error: XAResource '" + this.name + "' is unavailable";
         errorCode = -7;
      } else if (!this.registered) {
         msg = "Internal error: XAResource '" + this.name + "' is unregistered";
         errorCode = -7;
         if (tx != null) {
            tx.setProperty("weblogic.transaction.resourceNotFoundName", this.name);
            tx.setResourceNotFoundTrue();
         }
      }

      if (msg != null) {
         XAException ex = new XAException(msg);
         ex.errorCode = errorCode;
         throw ex;
      }
   }

   private void setXAResource(XAResource axar) {
      this.xar = axar;
   }

   private boolean hasPendingUsages() {
      return this.numOfActiveRequests > 0;
   }

   private boolean checkResourceHealth() {
      if (this.healthy) {
         if (this.lastAliveTimeMillis > 0L && this.numOfActiveRequests > 0 && System.currentTimeMillis() - this.lastAliveTimeMillis > maxXACallMillis && this.lastResourceHealthCheckEndTimeMillis < this.lastAliveTimeMillis && this.asyncCheckResourceHealth()) {
            this.setHealthy(false);
            TXLogger.logResourceNotResponding(this.name, maxXACallMillis / 1000L);
            if (TxDebug.JTAResourceHealth.isDebugEnabled()) {
               synchronized(this.activeRequests) {
                  if (this.activeRequests.size() > 0) {
                     StringBuffer sb = (new StringBuffer(100)).append("Pending requests as follow:\n");
                     Iterator iter = this.activeRequests.iterator();

                     while(iter.hasNext()) {
                        sb.append((String)iter.next()).append("\n");
                     }

                     TxDebug.JTAResourceHealth.debug(sb.toString());
                  }
               }
            }
         }
      } else if (System.currentTimeMillis() - this.lastDeadTimeMillis > maxDeadMillis) {
         this.setHealthy(true);
      }

      return this.healthy;
   }

   private boolean asyncCheckResourceHealth() {
      if (!(this.xar instanceof weblogic.transaction.XAResource)) {
         return true;
      } else {
         synchronized(this.resourceHealthCheckLock) {
            if (this.resourceHealthCheckInProgress) {
               return this.lastResourceHealthCheckStartTimeMillis != -1L && System.currentTimeMillis() - this.lastResourceHealthCheckStartTimeMillis > maxXACallMillis;
            }

            this.resourceHealthCheckInProgress = true;
            this.lastResourceHealthCheckStartTimeMillis = -1L;
         }

         Runnable req = new Runnable() {
            public void run() {
               XAResourceDescriptor.this.lastResourceHealthCheckStartTimeMillis = System.currentTimeMillis();
               XAResourceDescriptor.this.setHealthy(((weblogic.transaction.XAResource)XAResourceDescriptor.this.xar).detectedUnavailable());
               XAResourceDescriptor.this.lastResourceHealthCheckEndTimeMillis = System.currentTimeMillis();
               synchronized(XAResourceDescriptor.this.resourceHealthCheckLock) {
                  XAResourceDescriptor.this.resourceHealthCheckInProgress = false;
               }
            }
         };
         PlatformHelper.getPlatformHelper().scheduleWork(req);
         return false;
      }
   }

   private static void vendorWorkarounds(String resourceName, XAResource aXar) {
      int vid = true;
      if (resourceName != null && resourceName.equals(LOCALLY_ASSIGNED_RESOURCE)) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("vendorWorkArounds resourceName.equals(LOCALLY_ASSIGNED_RESOURCE): " + resourceName);
         }

         localResAssignmentWA = true;
         regTypeDynamic = true;
      }

      String resourceClassName = aXar.getClass().getName();
      int vid;
      if (VendorId.get(resourceClassName) == 5) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug(" vendorWorkArounds resource is a JCA Adapter ");
         }

         vid = VendorId.get(resourceName);
      } else {
         vid = VendorId.get(resourceClassName);
      }

      if (TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.JTAXA.debug("vendorWorkArounds resource: " + VendorId.toString(vid));
      }

      if (vid != -1) {
         switch (vid) {
            case 1:
               mqSeriesWorkArounds = true;
               break;
            case 2:
            case 3:
            case 4:
            case 7:
               localResAssignmentWA = true;
               regTypeDynamic = true;
               break;
            case 5:
            default:
               localResAssignmentWA = true;
               regTypeDynamic = true;
               break;
            case 6:
               mqSeriesWorkArounds = true;
               localResAssignmentWA = true;
               regTypeDynamic = true;
               break;
            case 8:
               localResAssignmentWA = true;
               regTypeDynamic = true;
               break;
            case 9:
               mqSeriesWorkArounds = true;
               localResAssignmentWA = true;
               regTypeDynamic = true;
         }

      }
   }

   void recover(CoordinatorDescriptor cd) {
      this.recover(cd, (String)null);
   }

   void recover(CoordinatorDescriptor cd, String serverNameForCrossSiteRecovery) {
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("recover: Starting recovery for cd:" + cd + " serverNameForCrossSiteRecovery:" + serverNameForCrossSiteRecovery);
      }

      ArrayList urls = (ArrayList)this.getSCUrlList();
      if (urls != null && urls.size() != 0) {
         if (this.recoverRetryDurationMillis > 0L && this.recoveryInitiationTime > 0L && System.currentTimeMillis() - this.lastRecoverTime < this.recoverRetryIntervalMillis) {
            if (System.currentTimeMillis() - this.recoveryInitiationTime < this.recoverRetryDurationMillis) {
               this.setNeedsRecovery(cd);
            }

         } else {
            try {
               ServerSCInfo[] scis = getServerSCInfos(urls);
               Xid[] xidsToBeRolledBack = this.getXidsToBeRolledBack(cd, scis, serverNameForCrossSiteRecovery != null);
               boolean siteRecoveryRollbacksNeeded = false;
               if (xidsToBeRolledBack != null && serverNameForCrossSiteRecovery != null) {
                  List xidListForServer = new ArrayList();
                  String serverid = CoordinatorDescriptor.getServerID(PlatformHelper.getPlatformHelper().getDomainName(), serverNameForCrossSiteRecovery);
                  byte[] aCoURLHash = CoordinatorDescriptor.getURLHash(serverid);
                  String recoverySiteName = getTM().getRecoverySiteName();
                  byte[] recoverySiteNameHashBytes = CoordinatorDescriptor.getURLHash(recoverySiteName);
                  TxDebug.JTARecovery.debug("XAResourceDescriptor.recover: xidsToBeRolledBack!=null && serverNameForCrossSiteRecovery != null serverid:" + serverid + " recoverySiteName:" + recoverySiteName + " recoverySiteNameHashBytes:" + XAResourceHelper.byteArrayToString(recoverySiteNameHashBytes) + " aCoURLHash:" + XAResourceHelper.byteArrayToString(aCoURLHash));
                  if (xidsToBeRolledBack.length > 0 && this.isDeterminer()) {
                     if (TxDebug.JTARecovery.isDebugEnabled()) {
                        TxDebug.JTARecovery.debug("recover: removing determiner Xids from " + Arrays.toString(xidsToBeRolledBack));
                     }

                     xidsToBeRolledBack = this.getXidsWithDeterminerXidsRemoved(xidsToBeRolledBack);
                  }

                  int i;
                  for(i = 0; i < xidsToBeRolledBack.length; ++i) {
                     boolean isHashEqualsForRecoverySiteServerName = XAServerResourceInfo.hashEquals(xidsToBeRolledBack[i].getGlobalTransactionId(), aCoURLHash, recoverySiteNameHashBytes);
                     if (isHashEqualsForRecoverySiteServerName) {
                        xidListForServer.add(xidsToBeRolledBack[i]);
                     }
                  }

                  xidsToBeRolledBack = new Xid[xidListForServer.size()];
                  i = 0;

                  Xid xid;
                  for(Iterator var18 = xidListForServer.iterator(); var18.hasNext(); xidsToBeRolledBack[i++] = xid) {
                     xid = (Xid)var18.next();
                  }

                  if (xidListForServer.size() > 0) {
                     if (TxDebug.JTARecovery.isDebugEnabled()) {
                        TxDebug.JTARecovery.debug("recover: site recovery rollbacks needed cd=" + cd + ", site name serverID=" + serverid + ", scInfos=" + Arrays.toString(scis));
                     }

                     siteRecoveryRollbacksNeeded = true;
                  }
               }

               if (TxDebug.JTARecovery.isDebugEnabled() && xidsToBeRolledBack != null) {
                  TxDebug.JTARecovery.debug("recover: " + xidsToBeRolledBack.length + " XIDs marked for rollback: " + Arrays.toString(xidsToBeRolledBack));
               }

               if (this.recoverRetryDurationMillis > 0L && this.recoveryInitiationTime == 0L) {
                  this.recoveryInitiationTime = System.currentTimeMillis();
               }

               if (siteRecoveryRollbacksNeeded || !this.isRecovered(cd)) {
                  if (xidsToBeRolledBack == null) {
                     TxDebug.JTARecovery.debug("recover: recovery not completed");
                     return;
                  }

                  this.rollbackXids(cd, scis, xidsToBeRolledBack);
                  if (!this.isRecovered(cd)) {
                     if (TxDebug.JTARecovery.isDebugEnabled()) {
                        TxDebug.JTARecovery.debug("recover:  Not finished, will try later for cd=" + cd);
                     }

                     return;
                  }

                  if (TxDebug.JTARecovery.isDebugEnabled()) {
                     TxDebug.JTARecovery.debug("recover: recovery complete(2)");
                  }

                  return;
               }

               if (TxDebug.JTARecovery.isDebugEnabled()) {
                  TxDebug.JTARecovery.debug("recover: recovery complete(1) for cd=" + cd);
               }
            } finally {
               if (this.recoverRetryDurationMillis > 0L) {
                  this.lastRecoverTime = System.currentTimeMillis();
                  if (System.currentTimeMillis() - this.recoveryInitiationTime < this.recoverRetryDurationMillis) {
                     this.setNeedsRecovery(cd);
                  }
               }

            }

         }
      } else {
         this.setNeedsRecovery(cd);
         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug("recover: No URLs registered for resource " + this.name + ", serverNameForCrossSiteRecovery:" + serverNameForCrossSiteRecovery);
         }

         m_isResourceWithNoURLRegistered = true;
         if (this.isDeterminer()) {
            getTM().registerFailedPrimaryStore(new Exception("No URLs registered for determiner resource: " + this.getName() + " The resource may have been removed before the determiner was removed and/or it's transactions recovered."));
         }

      }
   }

   boolean recover(CoordinatorDescriptor cd, HashMap determinerToCommitXidMap, boolean isCommitDeterminers) {
      TxDebug.JTARecovery.debug("XAResourceDescriptor.recover cd = [" + cd + "], determinerToCommitXidMap.size() = [" + (determinerToCommitXidMap == null ? "null" : determinerToCommitXidMap.size()) + "], isCommitDeterminers = [" + isCommitDeterminers + "]");
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("recover: Starting recovery for cd=" + cd + " this:" + this);
      }

      ArrayList urls = (ArrayList)this.getSCUrlList();
      if (urls != null && urls.size() != 0) {
         if (this.recoverRetryDurationMillis > 0L && this.recoveryInitiationTime > 0L && System.currentTimeMillis() - this.lastRecoverTime < this.recoverRetryIntervalMillis) {
            if (System.currentTimeMillis() - this.recoveryInitiationTime < this.recoverRetryDurationMillis) {
               this.setNeedsRecovery(cd);
            }

            return false;
         } else {
            try {
               ServerSCInfo[] scis = getServerSCInfos(urls);
               Xid[] xidsToBeRolledBack = this.getXidsToBeRolledBack(cd, scis, false);
               if (determinerToCommitXidMap != null && determinerToCommitXidMap.size() > 0) {
                  TxDebug.JTARecovery.debug("XAResourceDescriptor.recover determinerToCommitXidMap.size():" + determinerToCommitXidMap.size() + " this:" + this);
                  Set determinerNameStringSet = determinerToCommitXidMap.keySet();
                  Iterator var8 = determinerNameStringSet.iterator();

                  while(true) {
                     if (!var8.hasNext()) {
                        if (this.isDeterminer()) {
                           this.isRecoveredExceptForCommitsAndDeterminerCommits = true;
                           this.isRecoveredExceptForDeterminerCommits = true;
                        }
                        break;
                     }

                     String determinerName = (String)var8.next();
                     if (!determinerName.equals(this.getName()) || isCommitDeterminers && determinerName.equals(this.getName())) {
                        TxDebug.JTARecovery.debug(Thread.currentThread() + ":XAResourceDescriptor.recover !determinerName.equals(getName())=" + !determinerName.equals(this.getName()) + " (isCommitDeterminers && determinerName.equals(getName()))=" + (isCommitDeterminers && determinerName.equals(this.getName())) + " isCommitDeterminers=" + isCommitDeterminers + " determinerName=" + determinerName + " getName()=" + this.getName());
                        Xid[] xidsToBeCommitted = isCommitDeterminers ? (Xid[])determinerToCommitXidMap.get(determinerName) : this.getXidsToBeCommittedAndDoNotIncludeActualDeterminerXids(xidsToBeRolledBack, (Xid[])determinerToCommitXidMap.get(determinerName));
                        if (!this.commitXids(scis, xidsToBeCommitted)) {
                           boolean var11 = false;
                           return var11;
                        }

                        if (isCommitDeterminers) {
                           this.setRecovered(cd);
                        }
                     }
                  }
               } else if (isCommitDeterminers) {
                  this.setRecovered(cd);
               }

               if (xidsToBeRolledBack != null) {
                  xidsToBeRolledBack = this.getXidsWithDeterminerXidsRemoved(xidsToBeRolledBack);
                  if (this.rollbackXids(scis, xidsToBeRolledBack) && (!this.isDeterminer() || isCommitDeterminers)) {
                     this.setRecovered(cd);
                  }
               }

               TxDebug.JTARecovery.debug("XAResourceDescriptor.recover cd = [" + cd + "], determinerToCommitXidMap.size() = [" + (determinerToCommitXidMap == null ? "null" : determinerToCommitXidMap.size()) + "], isCommitDeterminers = [" + isCommitDeterminers + "] " + (xidsToBeRolledBack == null ? "0/null" : xidsToBeRolledBack.length) + " Xid(s) marked for rollback");
               if (this.recoverRetryDurationMillis > 0L && this.recoveryInitiationTime == 0L) {
                  this.recoveryInitiationTime = System.currentTimeMillis();
               }

               boolean var15;
               if (this.isRecovered(cd)) {
                  if (TxDebug.JTARecovery.isDebugEnabled()) {
                     TxDebug.JTARecovery.debug("XAResourceDescriptor.recover: recovery completed for cd=" + cd);
                  }

                  var15 = true;
                  return var15;
               } else if (xidsToBeRolledBack == null) {
                  TxDebug.JTARecovery.debug("XAResourceDescriptor.recover: recovery not completed (xidsToBeRolledBack == null)");
                  var15 = true;
                  return var15;
               } else if (!this.isRecovered(cd)) {
                  if (TxDebug.JTARecovery.isDebugEnabled()) {
                     TxDebug.JTARecovery.debug("recover:  Not finished, will try later for cd=" + cd);
                  }

                  var15 = false;
                  return var15;
               } else {
                  if (TxDebug.JTARecovery.isDebugEnabled()) {
                     TxDebug.JTARecovery.debug("recover: recovery complete(2)");
                  }

                  return false;
               }
            } finally {
               if (this.recoverRetryDurationMillis > 0L) {
                  this.lastRecoverTime = System.currentTimeMillis();
                  if (System.currentTimeMillis() - this.recoveryInitiationTime < this.recoverRetryDurationMillis) {
                     this.setNeedsRecovery(cd);
                  }
               }

            }
         }
      } else {
         this.setNeedsRecovery(cd);
         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug("recover: No URLs registered for resource cd = [" + cd + "], determinerToCommitXidMap.size() = [" + (determinerToCommitXidMap == null ? "null" : determinerToCommitXidMap.size()) + "], isCommitDeterminers = [" + isCommitDeterminers + "] this:" + this);
         }

         return false;
      }
   }

   private Xid[] getXidsToBeCommittedAndDoNotIncludeActualDeterminerXids(Xid[] xidsToBeRolledBack, Xid[] xidsOfDeterminer) {
      if (xidsToBeRolledBack != null && xidsToBeRolledBack.length != 0 && xidsOfDeterminer != null && xidsOfDeterminer.length != 0) {
         List xidsToBeCommittedAndDoNotIncludeActualDeterminerXidsList = new ArrayList();
         Xid[] var4 = xidsToBeRolledBack;
         int var5 = xidsToBeRolledBack.length;

         int i;
         for(i = 0; i < var5; ++i) {
            Xid aXidsToBeRolledBack = var4[i];
            Xid[] var8 = xidsOfDeterminer;
            int var9 = xidsOfDeterminer.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Xid aXidsOfDeterminer = var8[var10];
               if (aXidsToBeRolledBack != null && aXidsOfDeterminer != null && Arrays.equals(aXidsToBeRolledBack.getGlobalTransactionId(), aXidsOfDeterminer.getGlobalTransactionId()) && !isXidDeterminer(aXidsToBeRolledBack)) {
                  xidsToBeCommittedAndDoNotIncludeActualDeterminerXidsList.add(aXidsToBeRolledBack);
               }
            }
         }

         Iterator iterator = xidsToBeCommittedAndDoNotIncludeActualDeterminerXidsList.iterator();
         Xid[] xidsToReturn = new Xid[xidsToBeCommittedAndDoNotIncludeActualDeterminerXidsList.size()];

         for(i = 0; iterator.hasNext(); xidsToReturn[i++] = (Xid)iterator.next()) {
         }

         return xidsToReturn;
      } else {
         return new Xid[0];
      }
   }

   synchronized Xid[] getXidsWithDeterminerXidsRemoved(Xid[] xidsToBeRolledBack) {
      boolean isPotentialDeterminerXidsToRemove = xidsToBeRolledBack != null && xidsToBeRolledBack.length > 0;
      if (!isPotentialDeterminerXidsToRemove) {
         return xidsToBeRolledBack;
      } else {
         Set xidsToBeRolledBackAfterDeterminerXidsRemoved = new HashSet();
         Collections.addAll(xidsToBeRolledBackAfterDeterminerXidsRemoved, xidsToBeRolledBack);
         Xid[] var4 = xidsToBeRolledBack;
         int var5 = xidsToBeRolledBack.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Xid aXidsToBeRolledBack = var4[var6];
            if (isXidDeterminer(aXidsToBeRolledBack)) {
               xidsToBeRolledBackAfterDeterminerXidsRemoved.remove(aXidsToBeRolledBack);
            } else if (this.determinersXids != null) {
               Xid[] var8 = this.determinersXids;
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Xid determinersXid = var8[var10];
                  if (determinersXid != null && aXidsToBeRolledBack != null && Arrays.equals(determinersXid.getGlobalTransactionId(), aXidsToBeRolledBack.getGlobalTransactionId())) {
                     xidsToBeRolledBackAfterDeterminerXidsRemoved.remove(aXidsToBeRolledBack);
                  }
               }
            }
         }

         if (xidsToBeRolledBackAfterDeterminerXidsRemoved.size() > 0) {
            return (Xid[])xidsToBeRolledBackAfterDeterminerXidsRemoved.toArray(new Xid[xidsToBeRolledBackAfterDeterminerXidsRemoved.size()]);
         } else {
            return new Xid[0];
         }
      }
   }

   private Xid[] processDeterminerForRecovery(CoordinatorDescriptor cd) {
      Xid[] var2;
      try {
         var2 = this.doProcessDeterminerForRecovery(cd);
      } finally {
         this.setNeedsRecovery(cd);
      }

      return var2;
   }

   private Xid[] doProcessDeterminerForRecovery(CoordinatorDescriptor cd) {
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("processDeterminerForRecovery: Starting recovery for cd=" + cd + " ...");
      }

      int intervalsSpentWaitingForResourceDiscovery = 0;

      ArrayList urls;
      for(urls = (ArrayList)this.getSCUrlList(); urls == null || urls.size() == 0; urls = (ArrayList)this.getSCUrlList()) {
         if (intervalsSpentWaitingForResourceDiscovery > 9) {
            this.setNeedsRecovery(cd);
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug("processDeterminerForRecovery: No URLs registered for resource");
            }

            return null;
         }

         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug("processDeterminerForRecovery: No URLs registered for resource, waiting for resource discovery, interval=" + intervalsSpentWaitingForResourceDiscovery);
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var14) {
         }

         ++intervalsSpentWaitingForResourceDiscovery;
      }

      ServerSCInfo[] scis = getServerSCInfos(urls);
      Xid[] xidsToBeRolledBack = this.getXidsToBeRolledBack(cd, scis, false);
      if (xidsToBeRolledBack == null) {
         xidsToBeRolledBack = new Xid[0];
      }

      Xid[] remainingXidsToRollback = new Xid[xidsToBeRolledBack.length];
      Xid[] determinersXids = new Xid[xidsToBeRolledBack.length];
      int determinerXidsSize = 0;
      int remainingXidsToRollbackSize = 0;
      Xid[] remainingNonDeterminerXidsToReturn = xidsToBeRolledBack;
      int var11 = xidsToBeRolledBack.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         Xid xid = remainingNonDeterminerXidsToReturn[var12];
         if (isXidDeterminer(xid)) {
            determinersXids[determinerXidsSize++] = xid;
         } else {
            remainingXidsToRollback[remainingXidsToRollbackSize++] = xid;
         }
      }

      if (this.determinersXids == null) {
         this.setDeterminerXids(determinersXids);
      } else {
         this.setOrAddToDeterminerXids(determinersXids);
      }

      remainingNonDeterminerXidsToReturn = new Xid[remainingXidsToRollbackSize];
      System.arraycopy(remainingXidsToRollback, 0, remainingNonDeterminerXidsToReturn, 0, remainingNonDeterminerXidsToReturn.length);
      this.nondeterminersXids = remainingXidsToRollback;
      return determinersXids;
   }

   void setOrAddToDeterminerXids(Xid[] determinersXids) {
      synchronized(determinersXidsLock) {
         TxDebug.JTARecovery.debug("XAResourceDescriptor.setOrAddToDeterminerXids this:" + this + " determinersXids:" + determinersXids.length + " will be added to this.determinersXids:" + this.determinersXids.length);
         Xid[] swapdeterminersXids = new Xid[determinersXids.length + this.determinersXids.length];
         System.arraycopy(this.determinersXids, 0, swapdeterminersXids, 0, this.determinersXids.length);
         System.arraycopy(determinersXids, 0, swapdeterminersXids, this.determinersXids.length, determinersXids.length);
         this.setDeterminerXids(swapdeterminersXids);
      }
   }

   void removeDeterminerXids(Set determinersXids) {
      if (this.determinersXids != null) {
         synchronized(determinersXidsLock) {
            TxDebug.JTARecovery.debug("XAResourceDescriptor.removeDeterminerXids this:" + this + " determinersXids:" + determinersXids.size() + " will be removed from this.determinersXids:" + this.determinersXids.length);
            HashSet determinersXidsSet = new HashSet();
            Collections.addAll(determinersXidsSet, this.determinersXids);
            Iterator var4 = determinersXids.iterator();

            while(var4.hasNext()) {
               Xid determinersXid = (Xid)var4.next();
               determinersXidsSet.remove(determinersXid);
            }

            Xid[] swapdeterminersXids = new Xid[determinersXidsSet.size()];
            int i = 0;

            Xid determinersXid;
            for(Iterator var6 = determinersXidsSet.iterator(); var6.hasNext(); swapdeterminersXids[i++] = determinersXid) {
               determinersXid = (Xid)var6.next();
            }

            this.setDeterminerXids(swapdeterminersXids);
         }
      }
   }

   void setDeterminerXids(Xid[] determinersXids) {
      synchronized(determinersXidsLock) {
         this.determinersXids = determinersXids;
         TxDebug.JTARecovery.debug("XAResourceDescriptor.setDeterminerXids this:" + this + " determinersXids:" + (this.determinersXids == null ? "null" : this.determinersXids.length));
      }
   }

   static boolean isXidDeterminer(Xid xid) {
      return xid.getBranchQualifier() != null && xid.getBranchQualifier().length >= 64 && xid.getBranchQualifier()[63] == 126;
   }

   static int totalDeterminersXidsLengthForCoor(CoordinatorDescriptor cd) {
      if (cd == null) {
         return 0;
      } else {
         byte[] coUrlHash = cd.getURLHash();
         int counter = 0;
         ArrayList rdList = getResourceDescriptorList();
         if (rdList == null) {
            return 0;
         } else {
            int len = rdList.size();

            for(int i = 0; i < len; ++i) {
               ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
               if (rd instanceof XAResourceDescriptor) {
                  XAResourceDescriptor xrd = (XAResourceDescriptor)rd;
                  if (xrd.determinersXids != null) {
                     synchronized(determinersXidsLock) {
                        for(int j = 0; j < xrd.determinersXids.length; ++j) {
                           if (xrd.determinersXids[j] != null) {
                              XidImpl xid = (XidImpl)xrd.determinersXids[j];
                              byte[] gtrid = xid.getGlobalTransactionId();
                              byte[] aUrlHash = new byte[4];
                              System.arraycopy(gtrid, 6, aUrlHash, 0, 4);
                              if (Arrays.equals(coUrlHash, aUrlHash)) {
                                 ++counter;
                              }

                              if (TxDebug.JTARecovery.isDebugEnabled()) {
                                 TxDebug.JTARecovery.debug("totalDeterminersXidsLengthForCoor xrd=" + xrd.getName() + " coUrlHash=" + Arrays.toString(coUrlHash) + " aUrlHash=" + Arrays.toString(aUrlHash) + " gtrid=" + Arrays.toString(gtrid) + " " + cd.getServerName() + " counter=" + counter + " XID=" + xid);
                              }
                           }
                        }
                     }
                  }
               }
            }

            return counter;
         }
      }
   }

   Xid[] getXidsToBeRolledBack(CoordinatorDescriptor cd, ServerSCInfo[] scis, boolean isCrossSiteRecovery) {
      if (this.testGetXidsToBeRolledBackReturn != null) {
         return this.testGetXidsToBeRolledBackReturn;
      } else {
         ServerTransactionManagerImpl tm = getTM();
         int var6 = scis.length;
         byte var7 = 0;
         if (var7 < var6) {
            ServerSCInfo sci = scis[var7];

            try {
               Xid[] xids = sci.recover(this.getName(), cd, this, isCrossSiteRecovery);
               this.isRecoveredExceptForCommitsAndDeterminerCommits = true;
               if (xids != null && xids.length != 0) {
                  int numToBeRolledback = 0;

                  for(int j = 0; j < xids.length; ++j) {
                     if (xids[j].getFormatId() == XIDFactory.getFormatId() && !(xids[j] instanceof XidImpl)) {
                        xids[j] = new XidImpl(xids[j].getGlobalTransactionId(), xids[j].getBranchQualifier());
                     }

                     if (!tm.isXidInTxMap(xids[j])) {
                        ++numToBeRolledback;
                     } else {
                        if (!tm.getLocalCoordinatorDescriptor().equals(cd)) {
                           ServerTransactionImpl tx = (ServerTransactionImpl)tm.getTransaction(xids[j]);
                           if (tx != null) {
                              CoordinatorDescriptor txcoordinator = tx.getCoordinatorDescriptor();
                              if (txcoordinator != null && txcoordinator.equals(cd) && (tx.isActive() || (tx.isPreparing() || tx.isPrepared()) && tx.getDeterminer() == null || tx.isCancelled())) {
                                 ++numToBeRolledback;
                                 continue;
                              }
                           }
                        }

                        xids[j] = null;
                     }
                  }

                  if (numToBeRolledback == 0) {
                     this.setRecovered(cd);
                     return new Xid[0];
                  } else {
                     Xid[] xidsToBeRolledBack = new Xid[numToBeRolledback];
                     int a = 0;

                     for(int b = 0; a < xids.length; ++a) {
                        if (xids[a] != null) {
                           xidsToBeRolledBack[b++] = xids[a];
                        }
                     }

                     return xidsToBeRolledBack;
                  }
               } else {
                  this.setRecovered(cd);
                  return new Xid[0];
               }
            } catch (Exception var14) {
               if (TxDebug.JTARecovery.isDebugEnabled()) {
                  TxDebug.JTARecovery.debug("getXidsToBeRolledBack Exception: " + var14);
               }

               return null;
            }
         } else {
            return null;
         }
      }
   }

   private void rollbackXids(CoordinatorDescriptor cd, ServerSCInfo[] scis, Xid[] xidsToBeRolledBack) {
      ServerSCInfo[] var4 = scis;
      int var5 = scis.length;
      int var6 = 0;

      while(var6 < var5) {
         ServerSCInfo sci1 = var4[var6];

         try {
            sci1.rollback(this.getName(), xidsToBeRolledBack);
            this.setRecovered(cd);
            return;
         } catch (Exception var9) {
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug((String)("rollbackXids Exception: " + var9), (Throwable)var9);
            }

            ++var6;
         }
      }

   }

   private boolean rollbackXids(ServerSCInfo[] scis, Xid[] xidsToBeRolledBack) {
      int var4 = scis.length;
      byte var5 = 0;
      if (var5 < var4) {
         ServerSCInfo sci1 = scis[var5];

         try {
            sci1.rollback(this.getName(), xidsToBeRolledBack);
            return true;
         } catch (Exception var8) {
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug((String)("rollbackXids Exception: " + var8), (Throwable)var8);
            }

            return false;
         }
      } else {
         return true;
      }
   }

   private boolean commitXids(ServerSCInfo[] scis, Xid[] xidsToBeCommitted) {
      int var4 = scis.length;
      byte var5 = 0;
      if (var5 < var4) {
         ServerSCInfo sci1 = scis[var5];

         try {
            sci1.commit(this.getName(), xidsToBeCommitted);
            if (this.isRecoveredExceptForCommitsAndDeterminerCommits) {
               this.isRecoveredExceptForDeterminerCommits = true;
            }

            return true;
         } catch (Exception var9) {
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug("commitXids Exception: " + var9 + " for this:" + this + " sci1:" + sci1);
            }

            Throwable cause = var9.getCause();
            if (cause != null && cause instanceof XAException && ((XAException)cause).errorCode == -7) {
               this.setRecoveryState((byte)1);
            }

            return false;
         }
      } else {
         return true;
      }
   }

   private void setRecovering() {
      this.setRecoveryState((byte)2);
   }

   private void setRecovered(CoordinatorDescriptor cd) {
      if (cd.equals(getTM().getLocalCoordinatorDescriptor())) {
         TxDebug.JTARecovery.debug("setRecovered cd = [" + cd + "] isDeterminer():" + this.isDeterminer() + " this:" + this + " cd.equals(getTM().getLocalCoordinatorDescriptor())");
         if (this.isDeterminer()) {
            if (this.determinersXids != null && this.determinersXids.length == 0) {
               this.setRecoveryState((byte)3);
            }
         } else {
            this.setRecoveryState((byte)3);
         }
      } else {
         TxDebug.JTARecovery.debug("setRecovered cd = [" + cd + "] isDeterminer():" + this.isDeterminer() + " this:" + this + " migratedRDs.remove(cd)");
         synchronized(this.migratedRDs) {
            this.migratedRDs.remove(cd);
         }
      }

      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("Set state to recovered for cd=" + cd);
      }

   }

   private boolean isRecovered(CoordinatorDescriptor cd) {
      if (cd.equals(getTM().getLocalCoordinatorDescriptor())) {
         return this.recoveryState == 3;
      } else {
         return this.migratedRDs.get(cd) == null;
      }
   }

   private boolean isRecovered(CoordinatorDescriptor cd, String serverNameForCrossSiteRecovery) {
      return this.isRecovered(cd);
   }

   boolean needsRecovery() {
      return this.recoveryState == 1;
   }

   boolean needsRecoveryOrIsStillRecovering() {
      return this.recoveryState == 1 || this.recoveryState == 2;
   }

   protected void setHealthy(boolean flag) {
      if (!this.healthy && flag) {
         TXLogger.logResourceNowAvailable(this.name);
      }

      this.healthy = flag;
      this.lastDeadTimeMillis = this.healthy ? -1L : System.currentTimeMillis();
      if (this.runtimeMBean != null) {
         this.runtimeMBean.setHealthy(flag);
      }

      String partitionName = this.getPartitionName();
      if (partitionName != null && this.partitionRuntimeMBean != null) {
         this.partitionRuntimeMBean.setHealthy(flag);
      }

   }

   private void registerMBean() {
      synchronized(registerMBeanLock) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("registerMBean  resource MBean isResourceRegisterMBean: " + this.isResourceMBeanRegisterted() + " for resource: " + this.getName());
         }

         if (!this.isResourceMBeanRegisterted()) {
            try {
               if (this.runtimeMBean != null) {
                  return;
               }

               this.runtimeMBean = getTM().getRuntime().registerResource(this.getName());
               this.setResourceMBeanRegisterted(true);
               String partitionName = this.getPartitionName();
               if (partitionName != null) {
                  JTAPartitionRuntime jtaPartitionRuntime = getTM().getPartitionRuntime(partitionName);
                  if (jtaPartitionRuntime != null) {
                     this.partitionRuntimeMBean = jtaPartitionRuntime.registerResource(this.getName());
                  }
               }
            } catch (Exception var5) {
               TXLogger.logResourceMBeanCreateFailed(this.getName(), var5);
            }
         } else if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("registerMBean  resource MBean is already registerd can not register again for: " + this.getName());
         }

      }
   }

   private void unregisterMBean() {
      synchronized(registerMBeanLock) {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("unregisterMBean  resource MBean isResourceRegisterMBean: " + this.isResourceMBeanRegisterted() + " for resource: " + this.getName());
         }

         if (this.isResourceMBeanRegisterted()) {
            try {
               if (this.runtimeMBean != null) {
                  getTM().getRuntime().unregisterResource(this.runtimeMBean);
                  this.runtimeMBean = null;
                  this.setResourceMBeanRegisterted(false);
                  if (this.partitionRuntimeMBean != null) {
                     String partitionName = this.getPartitionName();
                     if (partitionName != null) {
                        JTAPartitionRuntime jtaPartitionRuntime = getTM().getPartitionRuntime(partitionName);
                        if (jtaPartitionRuntime != null) {
                           jtaPartitionRuntime.unregisterResource(this.partitionRuntimeMBean);
                           this.partitionRuntimeMBean = null;
                        }
                     }
                  }
               }
            } catch (Exception var5) {
               TXLogger.logUnregisterResMBeanError(var5);
            }
         } else if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("unregisterMBean  resource MBean  is unregistered already can not unregisterMBean for: " + this.getName());
         }

      }
   }

   private void addToLocalCoordinatorDescriptor() {
      ServerCoordinatorDescriptor local = (ServerCoordinatorDescriptor)getTM().getLocalCoordinatorDescriptor();
      if (local != null) {
         local.addXAResourceDescriptor(this);
      }

   }

   private void removeFromLocalCoordinatorDescriptor() {
      ServerCoordinatorDescriptor local = (ServerCoordinatorDescriptor)getTM().getLocalCoordinatorDescriptor();
      if (local != null) {
         local.removeXAResourceDescriptor(this);
      }

   }

   void setCallSetTransactionTimeout(boolean flag) {
      this.callSetTransactionTimeout = flag;
   }

   boolean getCallSetTransactionTimeout() {
      return this.callSetTransactionTimeout;
   }

   void setAsyncTimeoutDelist(boolean flag) {
      this.asyncTimeoutDelist = flag;
   }

   boolean getAsyncTimeoutDelist() {
      return this.asyncTimeoutDelist;
   }

   void setMQDelistTMSUCCESSAlways(boolean flag) {
      this.delistMQTMSUCCESSAlways = flag;
   }

   boolean getMQDelistTMSUCCESSAlways() {
      return this.delistMQTMSUCCESSAlways;
   }

   void setDelistTMSUCCESSInsteadOfTMSUSPEND(boolean flag) {
      this.delistTMSUCCESSInsteadOfTMSUSPEND = flag;
   }

   boolean getDelistTMSUCCESSInsteadOfTMSUSPEND() {
      return this.delistTMSUCCESSInsteadOfTMSUSPEND;
   }

   void setDelistTMSUCCESSAlways(boolean flag) {
      this.delistTMSUCCESSAlways = flag;
   }

   boolean getDelistTMSUCCESSAlways() {
      return this.delistTMSUCCESSAlways;
   }

   void setRecoverRetryDurationMillis(long duration) {
      this.recoverRetryDurationMillis = duration;
   }

   void setRecoverRetryIntervalMillis(long interval) {
      this.recoverRetryIntervalMillis = interval;
   }

   boolean setXAResourceTransactionTimeoutIfAppropriate(XAResource aXAR, int timeout) throws XAException {
      if (this.callSetTransactionTimeout && this.supportsSetTransactionTimeout) {
         try {
            this.supportsSetTransactionTimeout = aXAR.setTransactionTimeout(timeout);
            if (TxDebug.JTAXA.isDebugEnabled()) {
               int actualResourceTimeout = aXAR.getTransactionTimeout();
               String detail = null;
               if (actualResourceTimeout != timeout) {
                  detail = ". However, the resource has retained its own pre-set tx timeout, " + actualResourceTimeout + ", which will be in effect for this resource independently of the JTA-level transaction timeout.";
               }

               TxDebug.JTAXA.debug("Resource '" + this.getName() + "' setTransactionTimeout(" + timeout + ") returned " + this.supportsSetTransactionTimeout + detail);
            }

            return this.supportsSetTransactionTimeout;
         } catch (XAException var5) {
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("Resource '" + this.getName() + "' setTransactionTimeout(" + timeout + ") XAException.errorCode: " + var5.errorCode + ", " + var5);
            }

            throw var5;
         }
      } else {
         if (TxDebug.JTAXA.isDebugEnabled()) {
            TxDebug.JTAXA.debug("Resource '" + this.getName() + "' setTransactionTimeout(" + timeout + ") not called. callSetTransactionTimeout=" + this.callSetTransactionTimeout + ", supportsSetTransactionTimeout=" + this.supportsSetTransactionTimeout);
         }

         return false;
      }
   }

   private static void enablePerformSerializedEnlistmentsGC() {
      performSerializedEnlistmentsGC = true;
   }

   void setSerializeEnlistmentsEnabled(boolean enable) {
      this.serializedEnlistmentsEnabled = enable;
      enablePerformSerializedEnlistmentsGC();
   }

   static void setSerializeEnlistmentsGCIntervalMillis(long interval) {
      serializedEnlistmentsGCIntervalMillis = interval;
   }

   static void checkSerializedEnlistmentsGC() {
      if (performSerializedEnlistmentsGC) {
         if (!serializedEnlistmentsGCInProgress && System.currentTimeMillis() - lastSerializedEnlistmentsGCMillis >= serializedEnlistmentsGCIntervalMillis) {
            final ArrayList rds = getResourceDescriptorList();
            if (rds != null && rds.size() > 0) {
               serializedEnlistmentsGCInProgress = true;
               PlatformHelper.getPlatformHelper().scheduleWork(new Runnable() {
                  public void run() {
                     for(int i = 0; i < rds.size(); ++i) {
                        ResourceDescriptor rd = (ResourceDescriptor)rds.get(i);
                        if (rd instanceof XAResourceDescriptor) {
                           ((XAResourceDescriptor)rd).serializedEnlistmentsGC();
                        }
                     }

                     XAResourceDescriptor.serializedEnlistmentsGCInProgress = false;
                     XAResourceDescriptor.lastSerializedEnlistmentsGCMillis = System.currentTimeMillis();
                  }
               });
            }

         }
      }
   }

   private void serializedEnlistmentsGC() {
      synchronized(this.activeEnlistments) {
         Set keys = this.activeEnlistments.keySet();
         if (keys != null && keys.size() != 0) {
            Iterator it = keys.iterator();

            while(it.hasNext()) {
               ActiveXAResource key = (ActiveXAResource)it.next();
               ActiveXAResource lock = (ActiveXAResource)this.activeEnlistments.get(key);
               synchronized(lock) {
                  if (lock.getOwner() == null) {
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        TxDebug.JTAXA.debug("XARD.serializedEnlistmentsGC: removing " + lock);
                     }

                     it.remove();
                  }
               }
            }

         }
      }
   }

   void serializedEnlist(TransactionImpl tx, XAResource xar) throws SystemException {
      if (this.serializedEnlistmentsEnabled) {
         if (tx != null && xar != null) {
            ActiveXAResource active = new ActiveXAResource(xar);
            ActiveXAResource lock = null;
            synchronized(this.activeEnlistments) {
               lock = (ActiveXAResource)this.activeEnlistments.get(active);
               if (lock == null) {
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     TxDebug.JTAXA.debug("XARD.serializedEnlist: xid=" + tx.getXid() + ", xar=" + xar + ", new entry");
                  }

                  active.setOwner(tx.getXid());
                  this.activeEnlistments.put(active, active);
                  return;
               }
            }

            synchronized(lock) {
               Xid ownerXid = lock.getOwner();
               if (ownerXid == null) {
                  lock.setOwner(tx.getXid());
                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     TxDebug.JTAXA.debug("XARD.serializedEnlist: xid=" + tx.getXid() + ", xar=" + xar + ", owner=" + lock.getOwner());
                  }
               } else if (!ownerXid.equals(tx.getXid())) {
                  try {
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        TxDebug.JTAXA.debug("XARD.serializedEnlist: xid=" + tx.getXid() + ", xar=" + xar + ", owner=" + lock.getOwner() + ", waiting...");
                     }

                     long ttl = tx.getTimeToLiveMillis();
                     if (ttl > 0L) {
                        lock.wait(ttl);
                     }
                  } catch (InterruptedException var10) {
                  }

                  if (tx.getTimeToLiveMillis() <= 0L) {
                     throw new SystemException("Transaction unable to enlist resource '" + this.getName() + "' due to contention");
                  }

                  if (TxDebug.JTAXA.isDebugEnabled()) {
                     TxDebug.JTAXA.debug("XARD.serializedEnlist: xid=" + tx.getXid() + ", xar=" + xar + ", signaled, new owner=" + tx.getXid());
                  }

                  lock.setOwner(tx.getXid());
               }

            }
         }
      }
   }

   void serializedDelist(TransactionImpl tx, XAResource xar) {
      if (this.serializedEnlistmentsEnabled) {
         if (tx != null && xar != null) {
            ActiveXAResource active = new ActiveXAResource(xar);
            ActiveXAResource lock = null;
            synchronized(this.activeEnlistments) {
               lock = (ActiveXAResource)this.activeEnlistments.get(active);
            }

            if (lock != null) {
               synchronized(lock) {
                  Xid owner = lock.getOwner();
                  if (owner != null && lock.getOwner().equals(tx.getXid())) {
                     if (TxDebug.JTAXA.isDebugEnabled()) {
                        TxDebug.JTAXA.debug("XARD.serializedDelist: xid=" + tx.getXid() + ", xar=" + xar + ", owner delist, notify");
                     }

                     lock.setOwner((Xid)null);
                     lock.notify();
                  }

               }
            }
         }
      }
   }

   Map getProperties() {
      Properties props = new Properties();
      props.setProperty("RecoverRetryDurationMillis", String.valueOf(this.recoverRetryDurationMillis));
      props.setProperty("RecoverRetryIntervalMillis", String.valueOf(this.recoverRetryIntervalMillis));
      return props;
   }

   void setProperties(Map properties) {
      Properties props = (Properties)properties;
      String recoverRetryDuration = props.getProperty("RecoverRetryDurationMillis");
      if (recoverRetryDuration != null) {
         long duration = Long.parseLong(recoverRetryDuration);
         if (duration > 0L) {
            this.recoverRetryDurationMillis = duration;
         }
      }

      String recoverRetryInterval = props.getProperty("RecoverRetryIntervalMillis");
      if (recoverRetryInterval != null) {
         this.recoverRetryIntervalMillis = Long.parseLong(recoverRetryInterval);
      }

   }

   void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("XAResource");
      super.dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   private static final class ActiveXAResource {
      private final XAResource xar;
      private Xid owner;

      ActiveXAResource(XAResource aXar) {
         this.xar = aXar;
      }

      XAResource getXAResource() {
         return this.xar;
      }

      Xid getOwner() {
         return this.owner;
      }

      void setOwner(Xid xid) {
         this.owner = xid;
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof ActiveXAResource)) {
            return false;
         } else {
            ActiveXAResource that = (ActiveXAResource)obj;
            return this.xar == that.xar;
         }
      }

      public int hashCode() {
         return this.xar.hashCode();
      }

      public String toString() {
         return "ActiveXAResource: owner=" + this.owner + ", xar=" + this.xar;
      }
   }

   private static final class MigratedRDState {
      private int recoveryState;

      private MigratedRDState() {
         this.recoveryState = 1;
      }

      private int getRecoveryState() {
         return this.recoveryState;
      }

      private void setNeedsRecovery() {
         this.recoveryState = 1;
      }

      private boolean needsRecovery() {
         return this.recoveryState == 1;
      }

      private void setRecovering() {
         this.recoveryState = 2;
      }

      // $FF: synthetic method
      MigratedRDState(Object x0) {
         this();
      }
   }
}
