package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.cache.CacheEntry;
import weblogic.messaging.dispatcher.DispatcherId;

public class DDMemberInformation implements CacheEntry, Externalizable {
   static final long serialVersionUID = 7307718114460216150L;
   private static final int EXTVERSIONDIABLO = 1;
   private static final int EXTVERSIONDANTE = 2;
   private static final int EXTVERSIONCORDELL = 3;
   private static final int EXTVERSION1212 = 4;
   private static final int EXTVERSION1221 = 5;
   private static final int VERSION_MASK = 255;
   private static final int _HAS_DD_TYPE = 256;
   private static final int _HAS_DD_JNDI_NAME = 512;
   private static final int _HAS_DD_MEMBER_JNDI_NAME = 1024;
   private static final int _HAS_DD_MEMBER_LOCAL_JNDI_NAME = 2048;
   private static final int _HAS_DD_MEMBER_CLUSTER_NAME = 4096;
   private static final int _HAS_MIGRATABLE_TARGET_NAME = 8192;
   private static final int _HAS_APPLICATION_NAME = 16384;
   private static final int _HAS_MODULE_NAME = 32768;
   private static final int _HAS_DESTINATION_ID = 65536;
   private static final int _IS_INSERTION_PAUSED = 131072;
   private static final int _IS_PRODUCTION_PAUSED = 262144;
   private static final int _IS_CONSUMPTION_PAUSED = 524288;
   private static final int _HAS_DOMAIN_NAME = 1048576;
   private static final int _HAS_FORWARDING_POLICY = 2097152;
   private static final int _HAS_WLS_SERVER_NAME = 4194304;
   private static final int _HAS_PERSISTENT_STORE_NAME = 8388608;
   private static final int _IS_10_3_4_OR_LATER = 16777216;
   private static final int _HAS_JMS_SERVER_CONFIG_NAME = 33554432;
   private static final int _HAS_DD_MEMBER_TYPE_1212 = 67108864;
   private static final int _HAS_PARTITION_NAME = 134217728;
   private static final int _HAS_DD_MEMBER_TYPE_1221 = 268435456;
   private static final int _HAS_ReservedForMoreFlags = 1073741824;
   private static final int _HAS_negativeValueDoNotUse = Integer.MIN_VALUE;
   private String ddConfigName;
   private String ddType;
   private int ddDeploymentMemberType;
   private String ddJNDIName;
   private String ddMemberJndiName;
   private String ddMemberServerName;
   private String ddMemberLocalJndiName;
   private String ddMemberClusterName;
   private String ddMemberMigratableTargetName;
   private DestinationImpl dImpl;
   boolean isConsumptionPaused;
   boolean isInsertionPaused;
   boolean isProductionPaused;
   private String ddMemberDomainName;
   private boolean fromWire;
   private transient boolean isLocalDD;
   private int forwardingPolicy;
   private boolean isPre10_3_4;
   private String partitionName;

   public DDMemberInformation(String ddConfigName, String ddType, int ddDeploymentMemberType, String ddJNDIName, DestinationImpl dImpl, String ddMemberServerName, String ddMemberJndiName, String ddMemberLocalJndiName, String ddMemberClusterName, String ddMemberMigratableTargetName, String partitionName) {
      this(ddConfigName, ddType, ddDeploymentMemberType, ddJNDIName, dImpl, ddMemberServerName, ddMemberJndiName, ddMemberLocalJndiName, ddMemberClusterName, ddMemberMigratableTargetName, false);
      this.partitionName = partitionName;
   }

   public DDMemberInformation(String ddConfigName, String ddType, int ddDeploymentMemberType, String ddJNDIName, DestinationImpl dImpl, String ddMemberServerName, String ddMemberJndiName, String ddMemberLocalJndiName, String ddMemberClusterName, String ddMemberMigratableTargetName, boolean isLocalDD) {
      this(ddConfigName, ddType, ddDeploymentMemberType, ddJNDIName, 1, dImpl, ddMemberServerName, ddMemberJndiName, ddMemberLocalJndiName, ddMemberClusterName, ddMemberMigratableTargetName, false, false, false, isLocalDD);
   }

   public DDMemberInformation(String ddConfigName, String ddType, int ddDeploymentMemberType, String ddJNDIName, int forwardingPolicy, DestinationImpl dImpl, String ddMemberServerName, String ddMemberJndiName, String ddMemberLocalJndiName, String ddMemberClusterName, String ddMemberMigratableTargetName, boolean isConsumptionPaused, boolean isInsertionPaused, boolean isProductionPaused, boolean isLocalDD) {
      this(ddConfigName, ddType, ddDeploymentMemberType, ddJNDIName, forwardingPolicy, dImpl, ddMemberServerName, ddMemberJndiName, ddMemberLocalJndiName, ddMemberClusterName, ddMemberMigratableTargetName, (String)null, isConsumptionPaused, isInsertionPaused, isProductionPaused, isLocalDD);
   }

   public DDMemberInformation(String ddConfigName, String ddType, int ddDeploymentMemberType, String ddJNDIName, int forwardingPolicy, DestinationImpl dImpl, String ddMemberServerName, String ddMemberJndiName, String ddMemberLocalJndiName, String ddMemberClusterName, String ddMemberMigratableTargetName, String ddMemberDomainName, boolean isConsumptionPaused, boolean isInsertionPaused, boolean isProductionPaused, boolean isLocalDD) {
      this.ddConfigName = null;
      this.ddType = null;
      this.ddDeploymentMemberType = 4;
      this.ddJNDIName = null;
      this.ddMemberJndiName = null;
      this.ddMemberLocalJndiName = null;
      this.ddMemberClusterName = null;
      this.ddMemberMigratableTargetName = null;
      this.isConsumptionPaused = false;
      this.isInsertionPaused = false;
      this.isProductionPaused = false;
      this.ddMemberDomainName = null;
      this.fromWire = false;
      this.isLocalDD = false;
      this.forwardingPolicy = 1;
      this.isPre10_3_4 = true;
      this.partitionName = null;
      this.ddConfigName = ddConfigName;
      this.ddType = ddType;
      this.ddDeploymentMemberType = ddDeploymentMemberType;
      this.ddJNDIName = ddJNDIName;
      this.forwardingPolicy = forwardingPolicy;
      this.dImpl = dImpl;
      this.ddMemberServerName = ddMemberServerName;
      this.ddMemberJndiName = ddMemberJndiName;
      this.ddMemberLocalJndiName = ddMemberLocalJndiName;
      this.ddMemberClusterName = ddMemberClusterName;
      this.ddMemberMigratableTargetName = ddMemberMigratableTargetName;
      this.ddMemberDomainName = ddMemberDomainName;
      this.isConsumptionPaused = isConsumptionPaused;
      this.isInsertionPaused = isInsertionPaused;
      this.isProductionPaused = isProductionPaused;
      this.isLocalDD = isLocalDD;
      if (dImpl != null) {
         this.isPre10_3_4 = dImpl.isPre10_3_4();
      }

   }

   public DDMemberInformation(String ddConfigName, String ddType, int ddDeploymentMemberType, String ddJNDIName, int forwardingPolicy, DestinationImpl dImpl, String ddMemberServerName, String ddMemberJndiName, String ddMemberLocalJndiName, String ddMemberClusterName, String ddMemberMigratableTargetName, String ddMemberDomainName, boolean isConsumptionPaused, boolean isInsertionPaused, boolean isProductionPaused, boolean isLocalDD, String partitionName) {
      this(ddConfigName, ddType, ddDeploymentMemberType, ddJNDIName, forwardingPolicy, dImpl, ddMemberServerName, ddMemberJndiName, ddMemberLocalJndiName, ddMemberClusterName, ddMemberMigratableTargetName, ddMemberDomainName, isConsumptionPaused, isInsertionPaused, isProductionPaused, isLocalDD);
      this.partitionName = partitionName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DDMemberInformation)) {
         return false;
      } else {
         DDMemberInformation ddMemberInformation;
         label44: {
            ddMemberInformation = (DDMemberInformation)o;
            if (this.dImpl != null) {
               if (this.dImpl.equals(ddMemberInformation.dImpl)) {
                  break label44;
               }
            } else if (ddMemberInformation.dImpl == null) {
               break label44;
            }

            return false;
         }

         if (this.ddConfigName != null) {
            if (!this.ddConfigName.equals(ddMemberInformation.ddConfigName)) {
               return false;
            }
         } else if (ddMemberInformation.ddConfigName != null) {
            return false;
         }

         if (this.ddType != null) {
            if (!this.ddType.equals(ddMemberInformation.ddType)) {
               return false;
            }
         } else if (ddMemberInformation.ddType != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.ddConfigName != null ? this.ddConfigName.hashCode() : 0;
      result = 29 * result + (this.ddType != null ? this.ddType.hashCode() : 0);
      result = 29 * result + (this.dImpl != null ? this.dImpl.hashCode() : 0);
      return result;
   }

   public boolean isConsumptionPaused() {
      return this.isConsumptionPaused;
   }

   public boolean isInsertionPaused() {
      return this.isInsertionPaused;
   }

   public boolean isProductionPaused() {
      return this.isProductionPaused;
   }

   public boolean isDD() {
      if (this.fromWire) {
         return true;
      } else {
         return this.isLocalDD;
      }
   }

   public String getName() {
      return this.getDDJNDIName();
   }

   public String getMemberName() {
      return this.dImpl == null ? null : this.dImpl.getName();
   }

   private DispatcherId getDispatcherId() {
      return this.dImpl == null ? null : this.dImpl.getDispatcherId();
   }

   public String getDDType() {
      return this.ddType;
   }

   public int getDDMemberType1212() {
      return this.getDeploymentDDMemberType() & 7;
   }

   public int getDeploymentDDMemberType() {
      return this.ddDeploymentMemberType;
   }

   public String getDDMemberTypeAsString() {
      switch (this.getDDMemberType1212()) {
         case 1:
            return "MEMBER_TYPE_CLUSTERED_DYNAMIC";
         case 2:
            return "MEMBER_TYPE_CLUSTERED_STATIC";
         case 3:
            return "MEMBER_TYPE_NON_CLUSTERED";
         case 4:
            return "MEMBER_TYPE_NON_DD";
         default:
            throw new AssertionError("can never happen, contact Oracle support");
      }
   }

   public String getDDConfigName() {
      return this.ddConfigName;
   }

   public String getDDJNDIName() {
      return this.ddJNDIName;
   }

   public String getDDMemberJndiName() {
      return this.ddMemberJndiName;
   }

   public Destination getDestination() {
      return this.dImpl;
   }

   public String getDDMemberLocalJndiName() {
      return this.ddMemberLocalJndiName;
   }

   public String getDDMemberServerName() {
      return this.ddMemberServerName;
   }

   public String getDDMemberClusterName() {
      return this.ddMemberClusterName;
   }

   public String getDDMemberMigratableTargetName() {
      return this.ddMemberMigratableTargetName;
   }

   public String getDDMemberDomainName() {
      return this.ddMemberDomainName;
   }

   public int getForwardingPolicy() {
      return this.forwardingPolicy;
   }

   public boolean isAdvancedTopicSupported() {
      return !this.isPre10_3_4;
   }

   public DDMemberInformation() {
      this.ddConfigName = null;
      this.ddType = null;
      this.ddDeploymentMemberType = 4;
      this.ddJNDIName = null;
      this.ddMemberJndiName = null;
      this.ddMemberLocalJndiName = null;
      this.ddMemberClusterName = null;
      this.ddMemberMigratableTargetName = null;
      this.isConsumptionPaused = false;
      this.isInsertionPaused = false;
      this.isProductionPaused = false;
      this.ddMemberDomainName = null;
      this.fromWire = false;
      this.isLocalDD = false;
      this.forwardingPolicy = 1;
      this.isPre10_3_4 = true;
      this.partitionName = null;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getVersion(out);
      int flags = version;
      if (this.ddType != null) {
         flags = version | 256;
      }

      if (this.ddJNDIName != null) {
         flags |= 512;
      }

      if (this.ddMemberJndiName != null) {
         flags |= 1024;
      }

      if (this.ddMemberLocalJndiName != null) {
         flags |= 2048;
      }

      if (this.ddMemberClusterName != null) {
         flags |= 4096;
      }

      if (this.ddMemberMigratableTargetName != null) {
         flags |= 8192;
      }

      if (this.dImpl.getId() != null) {
         flags |= 65536;
      }

      if (this.isConsumptionPaused) {
         flags |= 524288;
      }

      if (this.isInsertionPaused) {
         flags |= 131072;
      }

      if (this.isProductionPaused) {
         flags |= 262144;
      }

      if (version > 1 && this.ddMemberDomainName != null) {
         flags |= 1048576;
      }

      if (version >= 3) {
         flags |= 2097152;
         if (this.ddMemberServerName != null) {
            flags |= 4194304;
         }

         if (this.dImpl.getApplicationName() != null) {
            flags |= 16384;
         }

         if (this.dImpl.getModuleName() != null) {
            flags |= 32768;
         }

         if (this.dImpl.getApplicationName() != null) {
            flags |= 16384;
         }

         if (this.dImpl.getPersistentStoreName() != null) {
            flags |= 8388608;
         }
      }

      if (!this.isPre10_3_4) {
         flags |= 16777216;
      }

      if (version >= 4) {
         if (this.dImpl.getJMSServerConfigName() != null && this.dImpl.getJMSServerConfigName().length() != 0) {
            flags |= 33554432;
         }

         if (version < 5) {
            flags |= 67108864;
         }
      }

      if (version >= 5) {
         flags |= 268435456;
         if (this.partitionName != null && this.partitionName.length() != 0 && !this.partitionName.equals("DOMAIN")) {
            flags |= 134217728;
         }
      }

      out.writeInt(flags);
      out.writeUTF(this.ddConfigName);
      if ((flags & 256) != 0) {
         out.writeUTF(this.ddType);
      }

      if ((flags & 512) != 0) {
         out.writeUTF(this.ddJNDIName);
      }

      if ((flags & 1024) != 0) {
         out.writeUTF(this.ddMemberJndiName);
      }

      if ((flags & 2048) != 0) {
         out.writeUTF(this.ddMemberLocalJndiName);
      }

      if ((flags & 4096) != 0) {
         out.writeUTF(this.ddMemberClusterName);
      }

      if ((flags & 8192) != 0) {
         out.writeUTF(this.ddMemberMigratableTargetName);
      }

      if ((flags & 1048576) != 0) {
         out.writeUTF(this.ddMemberDomainName);
      }

      if ((flags & 2097152) != 0) {
         out.writeInt(this.forwardingPolicy);
      }

      out.writeInt(this.dImpl.getType());
      out.writeUTF(this.dImpl.getServerName());
      out.writeUTF(this.getMemberName());
      if ((flags & 16384) != 0) {
         out.writeUTF(this.dImpl.getApplicationName());
      }

      if ((flags & '耀') != 0) {
         out.writeUTF(this.dImpl.getModuleName());
      }

      this.dImpl.getBackEndId().writeExternal(out);
      if ((flags & 65536) != 0) {
         this.dImpl.getId().writeExternal(out);
      }

      this.dImpl.getDispatcherId().writeExternal(out);
      if ((flags & 4194304) != 0) {
         out.writeUTF(this.ddMemberServerName);
      }

      if ((flags & 8388608) != 0) {
         out.writeUTF(this.dImpl.getPersistentStoreName());
      }

      if ((flags & 33554432) != 0) {
         out.writeUTF(this.dImpl.getJMSServerConfigName());
      }

      int wireMemberType;
      boolean doWrite;
      if ((flags & 268435456) != 0) {
         doWrite = true;
         wireMemberType = this.getDeploymentDDMemberType();
      } else if ((flags & 67108864) != 0) {
         doWrite = true;
         wireMemberType = this.getDDMemberType1212();
      } else {
         doWrite = false;
         wireMemberType = -1;
      }

      if (doWrite) {
         out.writeInt(wireMemberType);
      }

      if ((flags & 134217728) != 0) {
         out.writeUTF(this.partitionName);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1 && version != 2 && version != 3 && version != 4 && version != 5) {
         throw JMSUtilities.versionIOException(version, 1, 5);
      } else {
         if ((flags & 524288) != 0) {
            this.isConsumptionPaused = true;
         }

         if ((flags & 131072) != 0) {
            this.isInsertionPaused = true;
         }

         if ((flags & 262144) != 0) {
            this.isProductionPaused = true;
         }

         this.ddConfigName = in.readUTF();
         if ((flags & 256) != 0) {
            this.ddType = in.readUTF();
         }

         if ((flags & 512) != 0) {
            this.ddJNDIName = in.readUTF();
         }

         if ((flags & 1024) != 0) {
            this.ddMemberJndiName = in.readUTF();
         }

         if ((flags & 2048) != 0) {
            this.ddMemberLocalJndiName = in.readUTF();
         }

         if ((flags & 4096) != 0) {
            this.ddMemberClusterName = in.readUTF();
         }

         if ((flags & 8192) != 0) {
            this.ddMemberMigratableTargetName = in.readUTF();
         }

         if ((flags & 1048576) != 0) {
            this.ddMemberDomainName = in.readUTF();
         }

         if ((flags & 2097152) != 0) {
            this.forwardingPolicy = in.readInt();
         }

         int type = in.readInt();
         String jmsServerInstanceName = in.readUTF();
         String name = in.readUTF();
         String applicationName = null;
         if ((flags & 16384) != 0) {
            applicationName = in.readUTF();
         }

         String moduleName = null;
         if ((flags & '耀') != 0) {
            moduleName = in.readUTF();
         }

         JMSServerId backEndId = new JMSServerId();
         backEndId.readExternal(in);
         JMSID destinationId = null;
         if ((flags & 65536) != 0) {
            destinationId = new JMSID();
            destinationId.readExternal(in);
         }

         DispatcherId dispatcherId = new DispatcherId();
         dispatcherId.readExternal(in);
         if ((flags & 4194304) != 0) {
            this.ddMemberServerName = in.readUTF();
         }

         String persistentStoreName = null;
         if ((flags & 8388608) != 0) {
            persistentStoreName = in.readUTF();
         }

         if ((flags & 16777216) != 0) {
            this.isPre10_3_4 = false;
         }

         String jmsServerConfigName = null;
         if ((flags & 33554432) != 0) {
            jmsServerConfigName = in.readUTF();
         }

         if ((flags & 335544320) != 0) {
            this.ddDeploymentMemberType = in.readInt();
         }

         if ((flags & 134217728) != 0) {
            this.partitionName = in.readUTF();
         }

         this.dImpl = new DestinationImpl(type, jmsServerInstanceName, jmsServerConfigName, persistentStoreName, name, applicationName, moduleName, backEndId, destinationId, dispatcherId, this.partitionName);
         this.fromWire = true;
      }
   }

   public String toString() {
      return new String("\nDDMemberInformation : \n  DD Type                          = " + this.ddType + "\n" + (this.ddType.equals("javax.jms.Topic") ? " DD ForwardingPolicy              = " + this.getForwardingPolicy() : "") + "\n  DD Config Name                   = " + this.getDDConfigName() + "\n  DD JNDI Name                     = " + this.ddJNDIName + "\n  DD Member JNDI Name              = " + this.ddMemberJndiName + "\n  DD Member Name                   = " + this.getMemberName() + "\n  DD Member Consumption Paused     = " + this.isConsumptionPaused + "\n  DD Member Insertion Paused       = " + this.isInsertionPaused + "\n  DD Member Production Paused      = " + this.isProductionPaused + "\n  DD Member Local JNDI Name        = " + this.ddMemberLocalJndiName + "\n  DD Member Server Name            = " + this.ddMemberServerName + "\n  DD Member Cluster Name           = " + this.ddMemberClusterName + "\n  DD Member Migratable Target Name = " + this.ddMemberMigratableTargetName + "\n  DD Member Domain Name            = " + this.ddMemberDomainName + "\n  DD Member AdvancedTopicSupported = " + this.isAdvancedTopicSupported() + "\n  DD Partition Name                = " + this.partitionName + "\n  DD Dispatcher Id                 = " + this.getDispatcherId());
   }

   private int getVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_DIABLO) < 0) {
            throw JMSUtilities.versionIOException(0, 1, 5);
         }

         if (pi.compareTo(PeerInfo.VERSION_1000) < 0) {
            return 1;
         }

         if (pi.compareTo(PeerInfo.VERSION_1033) < 0) {
            return 2;
         }

         if (pi.compareTo(PeerInfo.VERSION_1212) < 0) {
            return 3;
         }

         if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
            return 4;
         }
      }

      return 5;
   }
}
