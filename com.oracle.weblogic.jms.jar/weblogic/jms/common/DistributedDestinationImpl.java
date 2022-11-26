package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSEnvironment;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.messaging.common.MessagingUtilities;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.store.common.PersistentStoreOutputStream;

@CrossPartitionAware
public final class DistributedDestinationImpl extends DestinationImpl implements InteropWriteReplaceable, Externalizable {
   private static final int DDVERSION1 = 1;
   static final long serialVersionUID = 6099783323740404732L;
   private static final int BE_DESTINATION_NOT_TEMPORARY = 1;
   private int weight;
   private int loadBalancePolicy;
   private int messageForwardingPolicy;
   private String ddJNDIName;
   private String distributedConfigMbeanName;
   private boolean hasStore;
   private boolean stale;
   private boolean serverAffinityEnabled;
   private boolean isQueueForward;
   private boolean isLocal;
   private boolean isConsumptionPaused;
   private boolean isInsertionPaused;
   private boolean isProductionPaused;
   private int nonSystemSubscriberConsumers;
   private int order;
   private transient String wlsServerName;
   private transient boolean clusterTargeted;
   private transient int distributionPolicy;
   private transient boolean onDynamicNonUPS;
   private transient String jmsServerSortingName;
   private transient String wlsServerSortingName;
   private static final int _DDVERSIONMASK = 3840;
   private static final int _DDVERSIONSHIFT = 8;
   private static final int _ISFORWARDING_POLICY_PARTITIONED = 1;
   private static final int _HASDDJNDINAME = 2;
   private static final int _HASSTORE = 4;
   private static final int _HASNONSYSSUBCNT = 8;
   private static final int _ISDURABLE = 16;
   private static final int _ISBOUNDBYINTERNALNAME = 32;
   private static final int _ISQUEUEFORWARD = 64;
   private static final int _ISSERVERAFFINITY = 128;
   private static final int _DONOTUSE_RESERVED = 4096;

   public DistributedDestinationImpl() {
      this.weight = 1;
      this.serverAffinityEnabled = true;
      this.isLocal = false;
      this.wlsServerName = null;
      this.clusterTargeted = false;
      this.distributionPolicy = -1;
      this.onDynamicNonUPS = false;
   }

   public DistributedDestinationImpl(int ddType, String ddJMSServerInstanceName, String ddJMSServerConfigName, String ddName, String applicationName, String moduleName, int lbPolicy, int forwardingPolicy, String ddInstanceName, String vdName, JMSServerId beId, JMSID destId, DispatcherId dispId, boolean dHasStore, String persistentStoreName, String safExportPolicy, boolean isLocal) {
      this(ddType, ddJMSServerInstanceName, ddJMSServerConfigName, ddName, applicationName, moduleName, lbPolicy, forwardingPolicy, ddInstanceName, vdName, beId, destId, dispId, dHasStore, persistentStoreName, safExportPolicy, isLocal, PartitionUtils.getPartitionName());
   }

   public DistributedDestinationImpl(int ddType, String ddJMSServerInstanceName, String ddJMSServerConfigName, String ddName, String applicationName, String moduleName, int lbPolicy, int forwardingPolicy, String ddInstanceName, String vdName, JMSServerId beId, JMSID destId, DispatcherId dispId, boolean dHasStore, String persistentStoreName, String safExportPolicy, boolean isLocal, String partitionName) {
      super(ddType, ddJMSServerInstanceName, ddJMSServerConfigName, ddInstanceName, applicationName, moduleName, beId, destId, safExportPolicy, persistentStoreName, partitionName);
      this.weight = 1;
      this.serverAffinityEnabled = true;
      this.isLocal = false;
      this.wlsServerName = null;
      this.clusterTargeted = false;
      this.distributionPolicy = -1;
      this.onDynamicNonUPS = false;
      this.ddJNDIName = vdName;
      this.distributedConfigMbeanName = ddName;
      this.loadBalancePolicy = lbPolicy;
      this.messageForwardingPolicy = forwardingPolicy;
      this.dispatcherId = dispId;
      this.hasStore = dHasStore;
      this.isLocal = isLocal;
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug(" ------- Distributed Destination ------------------------------\n member name                = " + this.getMemberName() + "\n jmsServerInstanceName      = " + this.getServerName() + "\n jmsServerConfigName        = " + this.getJMSServerConfigName() + "\n distributedConfigMbeanName = " + this.distributedConfigMbeanName + "\n ddJNDIName                 = " + this.ddJNDIName + "\n dispatcherId               = " + this.dispatcherId + "\n ---------------------------------------------------------------\n");
      }

   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public String toString() {
      return this.getName();
   }

   public String getWLSServerSortingName() {
      if (this.wlsServerSortingName == null) {
         this.wlsServerSortingName = MessagingUtilities.getSortingString(this.wlsServerName);
      }

      return this.wlsServerSortingName;
   }

   public String getJMSServerSortingName() {
      if (this.jmsServerSortingName == null) {
         this.jmsServerSortingName = MessagingUtilities.getSortingString(this.getServerName());
      }

      return this.jmsServerSortingName;
   }

   public int getLoadBalancingPolicy() {
      return this.loadBalancePolicy;
   }

   public int getMessageForwardingPolicy() {
      return this.messageForwardingPolicy;
   }

   public int getWeight() {
      return this.weight;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   public synchronized int getOrder() {
      return this.order;
   }

   public synchronized void setOrder(int order) {
      this.order = order;
   }

   public boolean isPersistent() {
      return this.hasStore;
   }

   public byte getDestinationInstanceType() {
      return 2;
   }

   public boolean isLocal() {
      return this.isLocal;
   }

   public void setStale(boolean stale) {
      this.stale = stale;
   }

   public boolean isStale() {
      return this.stale;
   }

   public void setQueueForward(boolean isQueueForward) {
      this.isQueueForward = isQueueForward;
   }

   public boolean isQueueForward() {
      return this.isQueueForward;
   }

   public void setNonSystemSubscriberConsumers(int nonSystemSubscriberConsumers) {
      this.nonSystemSubscriberConsumers = nonSystemSubscriberConsumers;
   }

   public int getNonSystemSubscriberConsumers() {
      return this.nonSystemSubscriberConsumers;
   }

   public boolean hasConsumer() {
      return this.nonSystemSubscriberConsumers > 0;
   }

   public String getInstanceName() {
      return this.getName();
   }

   public String getCreateDestinationArgument() {
      String value = this.distributedConfigMbeanName;
      if (value.startsWith("/")) {
         value = value.substring(2, value.length() - 1).intern();
      }

      return value;
   }

   public boolean equals(Object o) {
      return o instanceof String ? o.equals(this.getInstanceName()) : super.equals(o);
   }

   public boolean same(String name) {
      return name.equals(this.getName());
   }

   public String getDDJNDIName() {
      return this.ddJNDIName;
   }

   public String getGlobalJNDIName() {
      return this.ddJNDIName;
   }

   public String getName() {
      return this.distributedConfigMbeanName;
   }

   public void setName(String mBeanName) {
      this.distributedConfigMbeanName = mBeanName;
   }

   String getDestinationName() {
      return this.distributedConfigMbeanName;
   }

   public boolean isConsumptionPaused() {
      return this.isConsumptionPaused;
   }

   public void setIsConsumptionPaused(boolean isConsumptionPaused) {
      this.isConsumptionPaused = isConsumptionPaused;
   }

   public boolean isInsertionPaused() {
      return this.isInsertionPaused;
   }

   public void setIsInsertionPaused(boolean isInsertionPaused) {
      this.isInsertionPaused = isInsertionPaused;
   }

   public boolean isProductionPaused() {
      return this.isProductionPaused;
   }

   public void setIsProductionPaused(boolean isProductionPaused) {
      this.isProductionPaused = isProductionPaused;
   }

   public void setWLSServerName(String wlssName) {
      this.wlsServerName = wlssName;
   }

   public void setClusterTargeted(boolean b) {
      this.clusterTargeted = b;
   }

   public boolean isClusterTargeted() {
      return this.clusterTargeted;
   }

   public void setDistributionPolicy(int p) {
      this.distributionPolicy = p;
   }

   public void setOnDynamicNonUPS(boolean b) {
      this.onDynamicNonUPS = b;
   }

   public boolean isOnDynamicNonUPS() {
      return this.onDynamicNonUPS;
   }

   public String debugString() {
      return !JMSDebug.JMSCommon.isDebugEnabled() ? "Distributed Destination Impl" : new String(this.getInstanceName() + " " + this.getMemberName() + " | pers-" + this.isPersistent() + " | cons-" + this.hasConsumer() + " | weit-" + this.getWeight() + " | locl-" + this.isLocal() + " | wlsServer-" + this.wlsServerName + " | distributionPolicy-" + this.distributionPolicy + " | onDynamicNonUPS-" + this.isOnDynamicNonUPS() + " | dispId-" + this.dispatcherId);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      if (peerInfo.compareTo(PeerInfo.VERSION_60) < 0) {
         throw new IOException(JMSClientExceptionLogger.logInvalidPeerLoggable(1).getMessage());
      } else if (peerInfo.compareTo(PeerInfo.VERSION_70) < 0) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("\n   *** Peer > 6 & < 7 WriteReplace DDImpl with DImpl ***\n      serverName = " + this.getServerName() + "      qname      = " + this.distributedConfigMbeanName);
         }

         return new DestinationImpl(this.type, this.getServerName(), (String)null, this.distributedConfigMbeanName, this.getApplicationName(), this.getModuleName(), this.backEndId, this.destinationId);
      } else {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("\n   *** Peer > 7 WriteReplace return this ***\n");
         }

         return this;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof PersistentStoreOutputStream) {
         this.writeDestinationImpl(out, this.distributedConfigMbeanName);
      } else {
         PeerInfo peerInfo = out instanceof PeerInfoable ? ((PeerInfoable)out).getPeerInfo() : null;
         if (peerInfo != null) {
            Object obj = this.interopWriteReplace(peerInfo);
            if (!(obj instanceof DistributedDestinationImpl)) {
               this.writeDestinationImpl(out);
               return;
            }
         }

         this.writeDistributedDestinationImpl(out, peerInfo);
      }
   }

   private void writeDistributedDestinationImpl(ObjectOutput out, PeerInfo peerInfo) throws IOException {
      int flags = 0;
      int peerVersion = 1;
      flags |= peerVersion << 8;
      flags |= 32;
      flags |= 16;
      if (this.ddJNDIName != null) {
         flags |= 2;
      }

      if (this.hasStore) {
         flags |= 4;
      }

      if (this.serverAffinityEnabled) {
         flags |= 128;
      }

      int localNonSystemSubscriberConsumers = this.nonSystemSubscriberConsumers;
      if (localNonSystemSubscriberConsumers != 0) {
         flags |= 8;
      }

      if (this.isQueueForward) {
         flags |= 64;
      }

      flags |= 4096;
      if (this.messageForwardingPolicy == 0) {
         flags |= 1;
      }

      out.writeShort(flags);
      this.writeDestinationImpl(out);
      if (out instanceof WLObjectOutput) {
         ((WLObjectOutput)out).writeAbbrevString(this.distributedConfigMbeanName);
         if ((flags & 2) != 0) {
            ((WLObjectOutput)out).writeAbbrevString(this.ddJNDIName);
         }
      } else {
         out.writeUTF(this.distributedConfigMbeanName);
         if ((flags & 2) != 0) {
            out.writeUTF(this.ddJNDIName);
         }
      }

      out.writeInt(this.loadBalancePolicy);
      out.writeInt(this.weight);
      if (localNonSystemSubscriberConsumers != 0) {
         out.writeLong((long)localNonSystemSubscriberConsumers);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int destinationImplFlags = false;
      int flags = in.readShort();
      short destinationImplFlags;
      if ((flags & 4096) == 0) {
         destinationImplFlags = flags;
      } else {
         byte vrsn = (byte)((flags & 3840) >>> 8 & 255);
         if (vrsn != 1) {
            throw JMSUtilities.versionIOException(vrsn, 1, 1);
         }

         destinationImplFlags = in.readShort();
      }

      this.readDestinationImpl(in, destinationImplFlags);
      this.isLocal = JMSEnvironment.getJMSEnvironment().getLocalDispatcherId().equals(this.dispatcherId);
      if ((flags & 4096) == 0) {
         this.distributedConfigMbeanName = super.getDestinationName();
      } else {
         if (in instanceof WLObjectInput) {
            this.distributedConfigMbeanName = ((WLObjectInput)in).readAbbrevString();
            if ((flags & 2) != 0) {
               this.ddJNDIName = ((WLObjectInput)in).readAbbrevString();
            }
         } else {
            this.distributedConfigMbeanName = in.readUTF();
            if ((flags & 2) != 0) {
               this.ddJNDIName = in.readUTF();
            }
         }

         this.loadBalancePolicy = in.readInt();
         if ((flags & 1) != 0) {
            this.messageForwardingPolicy = 0;
         } else {
            this.messageForwardingPolicy = 1;
         }

         this.weight = in.readInt();
         if ((flags & 4) != 0) {
            this.hasStore = true;
         }

         if ((flags & 128) != 0) {
            this.serverAffinityEnabled = true;
         } else {
            this.serverAffinityEnabled = false;
         }

         if ((flags & 72) != 0) {
            if ((flags & 8) != 0) {
               this.nonSystemSubscriberConsumers = (int)in.readLong();
            }

            if ((flags & 64) != 0) {
               this.isQueueForward = true;
            }
         }

      }
   }
}
