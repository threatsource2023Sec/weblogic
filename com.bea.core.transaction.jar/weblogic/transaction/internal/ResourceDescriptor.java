package weblogic.transaction.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.transaction.SystemException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.transaction.TransactionLogger;

public abstract class ResourceDescriptor {
   static final int STATIC = 1;
   static final int DYNAMIC = 2;
   static final int OBJECT_ORIENTED = 3;
   protected final String name;
   protected int resourceType;
   private final HashSet scUrlList = new HashSet();
   protected boolean registered = false;
   protected boolean resourceMBeanRegistered = false;
   private byte[] bqual;
   private byte[] determinerbqual;
   protected final Object requestLock = new Object() {
   };
   private boolean threadAffinity = false;
   protected static final Object resourceDescriptorLock = new Object() {
   };
   protected static ArrayList resourceDescriptorList = new ArrayList(5);
   private boolean coordinatedLocally = false;
   private boolean assignableOnlyToEnlistingSCs = false;
   private boolean unregistering = false;
   private boolean isDB2 = false;
   private final TxRefCountLock txRefCountLock = new TxRefCountLock();
   private int txRefCount = 0;
   protected long lastAccessTimeMillis = -1L;
   private static ResourceCheckpoint latestResourceCheckpoint = null;
   protected static int purgeResourceFromCheckpointIntervalSeconds;
   private boolean isResourceCheckpointNeeded = false;
   private static long lastCheckpointTimeMillis = -1L;
   private static long lastCheckpointTransactionTotalCount;
   protected boolean checkpointed = false;
   private SystemException ResourceDescriptorNamingConflict = null;
   private volatile boolean isDeterminer = false;
   private volatile boolean isDeterminerFromCheckpoint = false;
   private boolean isFirstResourceCommit = false;
   static volatile boolean isDeterminersProcessed = false;
   private String determinerResourceType;
   private String partitionName = null;
   private ComponentInvocationContext componentInvocationContext = null;

   public String getDeterminerResourceType() {
      return this.determinerResourceType;
   }

   public void setDeterminerResourceType(String determinerResourceType) {
      this.determinerResourceType = determinerResourceType;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.componentInvocationContext;
   }

   public void setComponentInvocationContext(ComponentInvocationContext componentInvocationContext) {
      this.componentInvocationContext = componentInvocationContext;
   }

   protected ResourceDescriptor(String aName) {
      this.name = aName;
   }

   public String getSCsToString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append("scUrls =");
      List sclist = this.getSCUrlList();
      if (sclist != null) {
         for(int i = 0; i < sclist.size(); ++i) {
            CoordinatorDescriptor cd = (CoordinatorDescriptor)sclist.get(i);
            sb.append(" ").append(cd.getCoordinatorURL());
         }

         sb.append("\n");
      }

      return sb.toString();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append("name = ").append(this.name).append("\n").append("resourceType = ").append(this.resourceType).append("\n").append("registered = ").append(this.registered).append("\n");
      sb.append(this.getSCsToString());
      return sb.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof ResourceDescriptor)) {
         return false;
      } else {
         ResourceDescriptor that = (ResourceDescriptor)o;
         return this.getName().equals(that.getName());
      }
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   void setResourceDescriptorNamingConflict(SystemException se) {
      this.ResourceDescriptorNamingConflict = se;
   }

   public SystemException getResourceDescriptorNamingConflict() {
      return this.ResourceDescriptorNamingConflict;
   }

   public String getName() {
      return this.name;
   }

   byte[] getBranchQualifier(String name) {
      boolean isDeterminerBQual = false;
      if (this.isDeterminer()) {
         Object tx = getTM().getTransaction();
         isDeterminerBQual = this.isDeterminerOfGivenTransaction(tx);
         if (!isDeterminerBQual) {
            if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("Determiner resource enlisted, however, another determiner is already enlisted and has been nominated as the determiner for this transaction. The determiner for this transaction is " + (tx == null ? "" : ((ServerTransactionImpl)tx).getDeterminer()) + " and this subsequent resource name is :" + name + " getName():" + this.getName() + " for tx:" + tx);
            } else if (TxDebug.JTAXA.isDebugEnabled()) {
               TxDebug.JTAXA.debug("Determiner resource enlisted. The determiner for this transaction is " + (tx == null ? "" : ((ServerTransactionImpl)tx).getDeterminer()) + " and this resource name is :" + name + " getName():" + this.getName() + " for tx:" + tx);
            }
         }
      }

      if (name != this.getName()) {
         return XidImpl.getBranchQualifier(name, isDeterminerBQual);
      } else {
         if (isDeterminerBQual && this.determinerbqual == null) {
            this.determinerbqual = XidImpl.getBranchQualifier(name, true);
         } else if (this.bqual == null) {
            this.bqual = XidImpl.getBranchQualifier(name, false);
         }

         return isDeterminerBQual ? this.determinerbqual : this.bqual;
      }
   }

   boolean isDeterminerOfGivenTransaction(Object tx) {
      return tx != null && tx instanceof ServerTransactionImpl && this.getName() != null && !this.getName().equals("") && this.getName().equals(((ServerTransactionImpl)tx).getDeterminer());
   }

   void tallyCompletion(ServerResourceInfo aResourceInfo, Exception aReason) {
   }

   boolean needsStaticEnlistment(boolean enlistOOAlso) {
      return false;
   }

   boolean isStatic() {
      return this.resourceType == 1;
   }

   boolean isRegistered() {
      return this.registered;
   }

   boolean isResourceMBeanRegisterted() {
      return this.resourceMBeanRegistered;
   }

   int getResourceType() {
      return this.resourceType;
   }

   void setIsResourceCheckpointNeeded(boolean resCheckPointNeeded) {
      synchronized(resourceDescriptorLock) {
         this.isResourceCheckpointNeeded = resCheckPointNeeded;
      }
   }

   boolean getIsResourceCheckpointNeeded() {
      synchronized(resourceDescriptorLock) {
         return this.isResourceCheckpointNeeded;
      }
   }

   boolean isCheckpointed() {
      return this.checkpointed;
   }

   static void setLatestResourceCheckpoint(TransactionLogger tlog, ResourceCheckpoint rc) {
      JTARuntime runtime = getTM().getRuntime();
      long txTotalCount = runtime != null ? runtime.getTransactionTotalCount() : 0L;
      ResourceCheckpoint old;
      synchronized(resourceDescriptorLock) {
         old = latestResourceCheckpoint;
         latestResourceCheckpoint = rc;
         lastCheckpointTimeMillis = System.currentTimeMillis();
         lastCheckpointTransactionTotalCount = txTotalCount;
      }

      if (old != null) {
         tlog.release(old);
      }

   }

   static void setLatestResourceCheckpoint(ResourceCheckpoint rc) {
      synchronized(resourceDescriptorLock) {
         latestResourceCheckpoint = rc;
      }
   }

   protected abstract boolean includeInCheckpoint();

   protected static long getLastCheckpointTimeMillis() {
      synchronized(resourceDescriptorLock) {
         return lastCheckpointTimeMillis;
      }
   }

   static void setPurgeResourceFromCheckpointIntervalSeconds(int max) {
      purgeResourceFromCheckpointIntervalSeconds = max;
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("ResourceDescriptor.setPurgeResourceFromCheckpointIntervalSecs:" + max);
      }

   }

   static final void checkpointIfNecessary() {
      checkpointResourceDiscovery();
   }

   private static void checkpointResourceDiscovery() {
      ResourceCheckpoint cp = new ResourceCheckpoint();
      cp.blockingStore(getTM().getTransactionLogger());
   }

   static void refreshCheckpoint(long refreshInterval) {
      ResourceCheckpoint latest = getLatestResourceCheckpoint();
      if (latest != null) {
         long txTotalCount = getTM().getRuntime().getTransactionTotalCount();
         if (System.currentTimeMillis() - getLastCheckpointTimeMillis() > refreshInterval && txTotalCount - getLastCheckpointTransactionTotalCount() > 0L) {
            checkpointResourceDiscovery();
         }

      }
   }

   static long getLastCheckpointTransactionTotalCount() {
      synchronized(resourceDescriptorLock) {
         return lastCheckpointTransactionTotalCount;
      }
   }

   static ResourceCheckpoint getLatestResourceCheckpoint() {
      return latestResourceCheckpoint;
   }

   static List getAllCheckpointResources() {
      List rdList = getAllResources();
      List cpList = new ArrayList(3);
      Iterator i = rdList.iterator();

      while(i.hasNext()) {
         ResourceDescriptor rd = (ResourceDescriptor)i.next();
         if (rd.isCoordinatedLocally() && rd.includeInCheckpoint()) {
            cpList.add(rd);
         }
      }

      return cpList;
   }

   void addSC(CoordinatorDescriptor aCoDesc) {
      synchronized(this.scUrlList) {
         this.scUrlList.add(aCoDesc);
      }
   }

   final void removeSC(CoordinatorDescriptor aCoDesc) {
      synchronized(this.scUrlList) {
         this.scUrlList.remove(aCoDesc);
      }
   }

   boolean isAvailableAtSC(CoordinatorDescriptor aCoDesc) {
      synchronized(this.scUrlList) {
         return this.scUrlList.contains(aCoDesc);
      }
   }

   static void shunSC(CoordinatorDescriptor aCoDesc) {
      ArrayList rdList = getResourceDescriptorList();
      if (rdList != null) {
         int len = rdList.size();

         for(int i = 0; i < len; ++i) {
            ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
            rd.removeSC(aCoDesc);
         }

      }
   }

   abstract boolean isAccessibleAt(CoordinatorDescriptor var1);

   abstract boolean isAccessibleAt(CoordinatorDescriptor var1, boolean var2);

   static List getAllResources() {
      return resourceDescriptorList;
   }

   List getSCUrlList() {
      synchronized(this.scUrlList) {
         return new ArrayList(this.scUrlList);
      }
   }

   static ResourceDescriptor get(String name) {
      ArrayList rdList = getResourceDescriptorList();
      if (rdList == null) {
         return null;
      } else {
         int len = rdList.size();

         for(int i = 0; i < len; ++i) {
            ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
            if (rd.name.equals(name)) {
               return rd;
            }
         }

         return null;
      }
   }

   void setCoordinatedLocally() {
      this.coordinatedLocally = true;
   }

   static void setResourceHealthy(String name) {
      ResourceDescriptor rd = get(name);
      if (rd != null) {
         rd.setHealthy(true);
      }
   }

   static void unregister(String name) throws SystemException {
      unregister(name, false);
   }

   static void unregister(String name, boolean blocking) throws SystemException {
      ResourceDescriptor rd = get(name);
      if (rd == null) {
         throw new SystemException("Resource '" + name + "' not registered.");
      } else {
         if (rd.isDeterminer()) {
            unRegisterDeterminerDebug(name, rd);
            getTM().addToUnRegisteredDeterminerList(rd.getName());
         }

         doUnregister(name, blocking, rd);
         if (rd.isDeterminer() && rd.txRefCount == 0 && !rd.needsRecoveryOrIsStillRecovering() && "RUNNING".equals(PlatformHelper.getPlatformHelper().getServerState())) {
            checkpointResourceDiscovery();
         }

      }
   }

   private static void unRegisterDeterminerDebug(String name, ResourceDescriptor rd) {
      if (TxDebug.JTAXA.isDebugEnabled()) {
         String message = "\n Determiner is being unregistered serverName:" + getTM().getServerName() + " Determiner:" + name + " rd.txRefCount:" + rd.txRefCount + " PlatformHelper.getPlatformHelper().isServerRunning():" + PlatformHelper.getPlatformHelper().isServerRunning() + " PlatformHelper.getPlatformHelper().isTransactionServiceRunning():" + PlatformHelper.getPlatformHelper().isTransactionServiceRunning() + " PlatformHelper.getPlatformHelper().getServerState():" + PlatformHelper.getPlatformHelper().getServerState() + " rd.needsRecoveryOrIsStillRecovering():" + rd.needsRecoveryOrIsStillRecovering();
         ArrayList rdList = getResourceDescriptorList();
         ResourceDescriptor rdDebug;
         if (rdList != null) {
            for(Iterator var4 = rdList.iterator(); var4.hasNext(); message = message + "\nrd.getName():" + rd.getName() + "txRefCount:" + rdDebug.txRefCount) {
               Object aRdList = var4.next();
               rdDebug = (ResourceDescriptor)aRdList;
            }
         }

         if (rd instanceof XAResourceDescriptor) {
            XAResourceDescriptor xard = (XAResourceDescriptor)rd;
            message = message + "\nrd.getName():" + rd.getName() + "xard.nondeterminersXids:" + Arrays.toString(xard.nondeterminersXids) + "xard.determinersXids:" + Arrays.toString(xard.determinersXids) + "xard.needsRecoveryOrIsStillRecovering():" + xard.needsRecoveryOrIsStillRecovering() + "xard.isRecoveredExceptForCommitsAndDeterminerCommits:" + xard.isRecoveredExceptForCommitsAndDeterminerCommits + "xard.isRecoveredExceptForDeterminerCommits:" + xard.isRecoveredExceptForDeterminerCommits;
            if (xard.nondeterminersXids != null) {
               message = message + "\nrd.getName():" + rd.getName() + "xard.nondeterminersXids.length:" + xard.nondeterminersXids.length;
            }

            if (xard.determinersXids != null) {
               message = message + "\nrd.getName():" + rd.getName() + "xard.determinersXids.length:" + xard.determinersXids.length;
            }

            if (rd.isDeterminer() && rd.txRefCount == 0 && !rd.needsRecoveryOrIsStillRecovering()) {
               message = message + "\nrd.getName():" + rd.getName() + " will be purged";
            } else {
               message = message + "\nrd.getName():" + rd.getName() + " will not be purged";
            }
         }

         TxDebug.JTAXA.debug(message);
      }

   }

   static void doUnregister(String name, boolean blocking, ResourceDescriptor rd) throws SystemException {
      if (blocking) {
         synchronized(rd) {
            if (!rd.isRegistered()) {
               return;
            }

            if (!rd.unregistering) {
               rd.unregistering = true;
            }
         }

         synchronized(rd.txRefCountLock) {
            if (rd.txRefCount > 0) {
               long gracePeriodMillis = getTM().getUnregisterResourceGracePeriodMillis();

               try {
                  rd.txRefCountLock.wait(gracePeriodMillis);
               } catch (InterruptedException var12) {
               }

               if (rd.txRefCount > 0) {
                  TXLogger.logBlockingUnregistrationTimedOut(name, gracePeriodMillis / 1000L);
                  debugXANonXA("Blocking unregisterResource operation timed out for " + name + " after " + gracePeriodMillis / 1000L + " seconds");
               }
            }
         }
      }

      synchronized(resourceDescriptorLock) {
         synchronized(rd) {
            if (!rd.isRegistered()) {
               return;
            }

            rd.unregister();
            rd.setRegistered(false);
            if (blocking) {
               rd.unregistering = false;
            }
         }

         synchronized(rd.requestLock) {
            rd.requestLock.notifyAll();
         }

         ArrayList clone = (ArrayList)resourceDescriptorList.clone();
         clone.remove(rd);
         resourceDescriptorList = clone;
      }
   }

   abstract void unregister() throws SystemException;

   void setThreadAffinity(boolean flag) {
      this.threadAffinity = flag;
   }

   boolean needThreadAffinity() {
      return this.threadAffinity;
   }

   abstract Map getProperties();

   abstract void setProperties(Map var1);

   void setLastAccessTimeMillis(long time) {
      this.lastAccessTimeMillis = time;
   }

   long getLastAccessTimeMillis() {
      return this.lastAccessTimeMillis;
   }

   protected static ServerSCInfo[] getServerSCInfos(ArrayList aCoDescs) {
      ServerSCInfo[] scis = new ServerSCInfo[aCoDescs.size()];

      for(int i = 0; i < scis.length; ++i) {
         ServerCoordinatorDescriptor coDesc = (ServerCoordinatorDescriptor)aCoDescs.get(i);
         scis[i] = new ServerSCInfo(coDesc);
         if (coDesc.equals(getTM().getLocalCoordinatorDescriptor()) && i > 0) {
            ServerSCInfo temp = scis[0];
            scis[0] = scis[i];
            scis[i] = temp;
         }
      }

      return scis;
   }

   protected boolean isCoordinatedLocally() {
      return this.coordinatedLocally;
   }

   public static ArrayList getResourceDescriptorList() {
      return resourceDescriptorList;
   }

   protected void setResourceType(int aResourceType) {
      this.resourceType = aResourceType;
   }

   protected static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }

   protected void setRegistered(boolean flag) {
      this.registered = flag;
   }

   protected void setAssignableOnlyToEnlistingSCs(boolean flag) {
      this.assignableOnlyToEnlistingSCs = flag;
   }

   protected boolean isAssignableOnlyToEnlistingSCs() {
      return this.assignableOnlyToEnlistingSCs;
   }

   protected void setDB2(boolean flag) {
      this.isDB2 = flag;
   }

   protected boolean isDB2() {
      return this.isDB2;
   }

   int getTxRefCount() {
      synchronized(this.txRefCountLock) {
         return this.txRefCount;
      }
   }

   void incrementTxRefCount() {
      synchronized(this.txRefCountLock) {
         ++this.txRefCount;
      }
   }

   void decrementTxRefCount() {
      synchronized(this.txRefCountLock) {
         if (this.txRefCount <= 0) {
            debugXANonXA("Transaction reference count for resource " + this.name + " was decremented when value already 0");
         } else {
            --this.txRefCount;
            if (this.txRefCount == 0) {
               this.txRefCountLock.notifyAll();
            }

         }
      }
   }

   boolean isUnregistering() {
      return this.unregistering;
   }

   public void setDeterminerFromCheckpoint() {
      this.isDeterminerFromCheckpoint = true;
   }

   public void setDeterminerFromCheckpointFalse() {
      this.isDeterminerFromCheckpoint = false;
   }

   boolean isDeterminerFromCheckpoint() {
      return this.isDeterminerFromCheckpoint;
   }

   public void setDeterminer(boolean isDeterminer) {
      this.isDeterminer = isDeterminer;
   }

   boolean isDeterminer() {
      return this.isDeterminer || this.isDeterminerFromCheckpoint;
   }

   public void setFirstResourceCommit(boolean isFirstResourceCommit) {
      this.isFirstResourceCommit = isFirstResourceCommit;
   }

   boolean isFirstResourceCommit() {
      return this.isFirstResourceCommit;
   }

   private static final void debugXANonXA(String msg) {
      if (TxDebug.JTAXA.isDebugEnabled()) {
         TxDebug.JTAXA.debug(msg);
      }

      if (TxDebug.JTANonXA.isDebugEnabled()) {
         TxDebug.JTANonXA.debug(msg);
      }

   }

   static void dumpResources(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      ArrayList rdList = (ArrayList)resourceDescriptorList.clone();
      xsw.writeStartElement("Resources");
      xsw.writeAttribute("currentCount", String.valueOf(rdList.size()));
      Iterator it = rdList.iterator();

      while(it.hasNext()) {
         ResourceDescriptor rd = (ResourceDescriptor)it.next();
         rd.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
   }

   void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeAttribute("name", this.getName());
      xsw.writeAttribute("enlistmentType", this.getEnlistmentTypeString());
      xsw.writeAttribute("registered", String.valueOf(this.registered));
      xsw.writeAttribute("coordinatedLocally", String.valueOf(this.coordinatedLocally));
      xsw.writeAttribute("unregistering", String.valueOf(this.unregistering));
      xsw.writeAttribute("transactionReferenceCount", String.valueOf(this.txRefCount));
      xsw.writeAttribute("assignableOnlyToEnlistingServers", String.valueOf(this.assignableOnlyToEnlistingSCs));
      xsw.writeStartElement("Servers");
      HashSet scs = (HashSet)this.scUrlList.clone();
      xsw.writeAttribute("currentCount", String.valueOf(scs.size()));
      Iterator it = scs.iterator();

      while(it.hasNext()) {
         CoordinatorDescriptor cd = (CoordinatorDescriptor)it.next();
         xsw.writeStartElement("Server");
         xsw.writeAttribute("url", cd.getCoordinatorURL());
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
   }

   protected void setResourceMBeanRegisterted(boolean flag) {
      this.resourceMBeanRegistered = flag;
   }

   protected String getEnlistmentTypeString() {
      switch (this.resourceType) {
         case 1:
            return "static";
         case 2:
            return "dynamic";
         case 3:
            return "standard";
         default:
            return "unknown";
      }
   }

   protected void setHealthy(boolean flag) {
   }

   boolean needsRecoveryOrIsStillRecovering() {
      return false;
   }

   private static final class TxRefCountLock {
      private TxRefCountLock() {
      }

      // $FF: synthetic method
      TxRefCountLock(Object x0) {
         this();
      }
   }
}
