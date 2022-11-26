package weblogic.transaction.internal;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public final class ServerCoordinatorDescriptor extends CoordinatorDescriptor {
   private Set xaResources = PlatformHelper.getPlatformHelper().newArraySet();
   private Set nonXAResources = PlatformHelper.getPlatformHelper().newArraySet();
   private boolean refreshScheduled = false;
   private boolean refreshInProgress = false;
   private long lastRefreshTime;
   private boolean sslOnly;
   private int coordinatorRefCount = 0;
   private long lastAccessTimeMillis = -1L;
   private boolean checkpointed = false;
   private final RefCountLock refCountLock = new RefCountLock();
   private volatile boolean channelBased;

   ServerCoordinatorDescriptor() {
   }

   ServerCoordinatorDescriptor(String aCoURL) {
      super(aCoURL);
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptor(" + aCoURL + ")");
      }

   }

   ServerCoordinatorDescriptor(String aCoURL, String aAdminPort) {
      super(aCoURL, aAdminPort);
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptor(" + aCoURL + ", " + aAdminPort + ")");
      }

   }

   public ServerCoordinatorDescriptor(String domainName, String serverName, URI primaryURL, URI publicURL, URI secureURL, URI publicSecureURL) {
      super(domainName, serverName, primaryURL, publicURL, secureURL, publicSecureURL);
      this.channelBased = true;
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptor(" + domainName + ", " + serverName + "," + primaryURL + "," + publicURL + "," + secureURL + "," + publicSecureURL + ")");
      }

   }

   Set getXAResources() {
      return this.xaResources;
   }

   Set getNonXAResources() {
      return this.nonXAResources;
   }

   static CoordinatorDescriptor getOrCreate(String aCoURL) {
      return ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getOrCreate(aCoURL);
   }

   public void incrementCoordinatorRefCount() {
      boolean requireCheckpoint = false;
      synchronized(this.refCountLock) {
         ++this.coordinatorRefCount;
         if (!this.checkpointed) {
            requireCheckpoint = true;
         }

         this.lastAccessTimeMillis = System.currentTimeMillis();
      }

      if (requireCheckpoint) {
         ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setServerCheckpointNeeded(true);
      }

   }

   public void decrementCoordinatorRefCount() {
      synchronized(this.refCountLock) {
         if (this.coordinatorRefCount <= 0) {
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug("ServerCoordinatorDescriptor reference count decrement attempt when already 0");
            }

         } else {
            --this.coordinatorRefCount;
         }
      }
   }

   public boolean isSSLOnly() {
      return this.sslOnly;
   }

   public void setSSLOnly(boolean flag) {
      this.sslOnly = flag;
   }

   boolean isChannelBased() {
      return this.channelBased;
   }

   public synchronized String[] getRegisteredXAResourceNames() {
      return this.extractResourceNames(this.xaResources);
   }

   public synchronized String[] getRegisteredNonXAResourceNames() {
      return this.extractResourceNames(this.nonXAResources);
   }

   public boolean isRefreshScheduled() {
      return this.refreshScheduled;
   }

   public void setRefreshScheduled(boolean flag) {
      this.refreshScheduled = flag;
   }

   public boolean isRefreshInProgress() {
      return this.refreshInProgress;
   }

   public void setRefreshInProgress(boolean flag) {
      this.refreshInProgress = flag;
   }

   public long getLastRefreshTime() {
      return this.lastRefreshTime;
   }

   public void setLastRefreshTime(long time) {
      this.lastRefreshTime = time;
   }

   private long getLastAccessTimeMillis() {
      synchronized(this.refCountLock) {
         return this.lastAccessTimeMillis;
      }
   }

   public boolean includeInCheckpoint() {
      if (this == ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorDescriptor()) {
         return false;
      } else {
         long lastAccess = this.getLastAccessTimeMillis();
         if (lastAccess == -1L) {
            return false;
         } else {
            this.checkpointed = System.currentTimeMillis() - lastAccess < (long)(((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getPurgeFromCheckpointIntervalSeconds() * 1000);
            return this.checkpointed;
         }
      }
   }

   public void addXAResourceDescriptor(XAResourceDescriptor rd) {
      String[] oldValues = null;
      String[] newValues = null;
      synchronized(this) {
         oldValues = this.extractResourceNames(this.xaResources);
         this.xaResources.add(rd);
         newValues = this.extractResourceNames(this.xaResources);
      }

      NotificationBroadcasterManager.getInstance().xaResourceRegistrationChange(oldValues, newValues);
   }

   public void removeXAResourceDescriptor(XAResourceDescriptor rd) {
      String[] oldValues = null;
      String[] newValues = null;
      synchronized(this) {
         oldValues = this.extractResourceNames(this.xaResources);
         this.xaResources.remove(rd);
         newValues = this.extractResourceNames(this.xaResources);
      }

      NotificationBroadcasterManager.getInstance().xaResourceRegistrationChange(oldValues, newValues);
   }

   public void addNonXAResourceDescriptor(NonXAResourceDescriptor rd) {
      String[] oldValues = null;
      String[] newValues = null;
      synchronized(this) {
         oldValues = this.extractResourceNames(this.nonXAResources);
         this.nonXAResources.add(rd);
         newValues = this.extractResourceNames(this.nonXAResources);
      }

      NotificationBroadcasterManager.getInstance().nonXAResourceRegistrationChange(oldValues, newValues);
   }

   public void removeNonXAResourceDescriptor(NonXAResourceDescriptor rd) {
      String[] oldValues = null;
      String[] newValues = null;
      synchronized(this) {
         oldValues = this.extractResourceNames(this.nonXAResources);
         this.nonXAResources.remove(rd);
         newValues = this.extractResourceNames(this.nonXAResources);
      }

      NotificationBroadcasterManager.getInstance().nonXAResourceRegistrationChange(oldValues, newValues);
   }

   protected static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer("ServerCoordinatorDescriptor=(");
      sb.append("CoordinatorURL=");
      sb.append(this.coordinatorURL);
      sb.append(" CoordinatorNonSecureURL=");
      sb.append(this.coordinatorNonSecureURL);
      sb.append(" coordinatorSecureURL=");
      sb.append(this.coordinatorSecureURL);
      sb.append(", ");
      sb.append("XAResources={");
      Iterator it;
      ResourceDescriptor rd;
      synchronized(this) {
         it = this.xaResources.iterator();

         while(it.hasNext()) {
            rd = (ResourceDescriptor)it.next();
            sb.append(rd.getName());
            if (it.hasNext()) {
               sb.append(", ");
            }
         }
      }

      sb.append("},");
      sb.append("NonXAResources={");
      synchronized(this) {
         it = this.nonXAResources.iterator();

         while(it.hasNext()) {
            rd = (ResourceDescriptor)it.next();
            sb.append(rd.getName());
            if (it.hasNext()) {
               sb.append(", ");
            }
         }
      }

      sb.append("}");
      sb.append(")");
      return sb.toString();
   }

   private void setXAResources(Set aResources) {
      this.xaResources = aResources;
   }

   private void setNonXAResources(Set aResources) {
      this.nonXAResources = aResources;
   }

   private String[] extractResourceNames(Set resourceDescriptors) {
      ArrayList list = new ArrayList();
      Iterator var3 = resourceDescriptors.iterator();

      while(var3.hasNext()) {
         Object resourceDescriptor = var3.next();
         ResourceDescriptor rd = (ResourceDescriptor)resourceDescriptor;
         list.add(rd.getName());
      }

      return (String[])list.toArray(new String[list.size()]);
   }

   synchronized Set updateXAResources(Set aNewResourceDescriptors) {
      Set old = this.xaResources;
      this.setXAResources(aNewResourceDescriptors);
      return old;
   }

   synchronized Set updateNonXAResources(Set aNewResourceDescriptors) {
      Set old = this.nonXAResources;
      this.setNonXAResources(aNewResourceDescriptors);
      return old;
   }

   void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Server");
      xsw.writeAttribute("url", this.coordinatorURL);
      xsw.writeStartElement("XAResources");
      Set xa = this.copySet(this.xaResources);
      xsw.writeAttribute("currentCount", String.valueOf(xa.size()));
      Iterator it = xa.iterator();

      while(it.hasNext()) {
         XAResourceDescriptor xard = (XAResourceDescriptor)it.next();
         xsw.writeStartElement("XAResource");
         xsw.writeAttribute("name", xard.getName());
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeStartElement("NonXAResources");
      Set nonxa = this.copySet(this.nonXAResources);
      xsw.writeAttribute("currentCount", String.valueOf(nonxa.size()));
      it = nonxa.iterator();

      while(it.hasNext()) {
         NonXAResourceDescriptor nonxard = (NonXAResourceDescriptor)it.next();
         xsw.writeStartElement("NonXAResource");
         xsw.writeAttribute("name", nonxard.getName());
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   private synchronized Set copySet(Set sourceSet) {
      PlatformHelper.ArraySet newArraySet = PlatformHelper.getPlatformHelper().newArraySet();
      if (sourceSet != null) {
         newArraySet.addAll(sourceSet);
      }

      return newArraySet;
   }

   static final class Barrier {
      int count;
      long maxWaitTime;

      Barrier(int aCount) {
         this.count = aCount;
      }

      synchronized void await() {
         if (this.count > 0) {
            try {
               this.wait(this.maxWaitTime);
            } catch (InterruptedException var2) {
            }
         }

      }

      synchronized void signal() {
         if (this.count > 0) {
            --this.count;
         }

         if (this.count == 0) {
            this.notifyAll();
         }

      }
   }

   private static final class RefCountLock {
      private RefCountLock() {
      }

      // $FF: synthetic method
      RefCountLock(Object x0) {
         this();
      }
   }
}
