package weblogic.jms.extensions;

import javax.jms.Destination;

public class DestinationDetailImpl implements DestinationDetail {
   private String ddConfigName;
   private String ddJndiName;
   private int destinationType;
   private String memberConfigName;
   private String memberJndiName;
   private String memberLocalJndiName;
   private String createDestinationIdentifier;
   private String jmsServerInstanceName;
   private String jmsServerConfigName;
   private String persistentStoreName;
   private String serverName;
   private String migratableTargetName;
   private boolean isLocalWLSServer;
   private boolean isLocalCluster;
   private boolean isAdvancedTopicSupported;
   private Destination destination;
   private int deploymentMemberType;
   private String partitionName;

   public DestinationDetailImpl(String ddConfigName, String ddJndiName, int destinationType, String memberConfigName, String memberJndiName, String memberLocalJndiName, String createDestinationIdentifier, String jmsServerInstanceName, String jmsServerConfigName, String persistentStoreName, Destination destination, String serverName, String migratableTargetName, boolean isLocalWLSServer, boolean isLocalCluster, boolean isAdvancedTopicSupported, int deploymentMemberType, String partitionName) {
      this.ddConfigName = ddConfigName;
      this.ddJndiName = ddJndiName;
      this.destinationType = destinationType;
      this.destination = destination;
      this.memberConfigName = memberConfigName;
      this.memberJndiName = memberJndiName;
      this.memberLocalJndiName = memberLocalJndiName;
      this.createDestinationIdentifier = createDestinationIdentifier;
      this.jmsServerInstanceName = jmsServerInstanceName;
      this.jmsServerConfigName = jmsServerConfigName;
      this.persistentStoreName = persistentStoreName;
      this.serverName = serverName;
      this.migratableTargetName = migratableTargetName;
      this.isLocalWLSServer = isLocalWLSServer;
      this.isLocalCluster = isLocalCluster;
      this.isAdvancedTopicSupported = isAdvancedTopicSupported;
      this.deploymentMemberType = deploymentMemberType;
      this.partitionName = partitionName;
   }

   public String getJNDIName() {
      return this.memberJndiName;
   }

   public Destination getDestination() {
      return this.destination;
   }

   public int getType() {
      return this.destinationType;
   }

   public String getTypeAsString() {
      switch (this.destinationType) {
         case 0:
            return "DESTINATION_TYPE_PHYSICAL_QUEUE";
         case 1:
            return "DESTINATION_TYPE_PHYSICAL_TOPIC";
         case 2:
            return "DESTINATION_TYPE_FOREIGN_QUEUE";
         case 3:
            return "DESTINATION_TYPE_FOREIGN_TOPIC";
         case 4:
            return "DESTINATION_TYPE_DD_QUEUE";
         case 5:
            return "DESTINATION_TYPE_REPLICATED_DT";
         case 6:
            return "DESTINATION_TYPE_PARTITIONED_DT";
         default:
            throw new AssertionError();
      }
   }

   public String getWLSServerName() {
      return this.serverName;
   }

   public String getJMSServerName() {
      return this.jmsServerInstanceName;
   }

   public String getJMSServerConfigName() {
      return this.jmsServerConfigName;
   }

   public String getStoreName() {
      return this.persistentStoreName;
   }

   public String getMigratableTargetName() {
      return this.migratableTargetName;
   }

   public String getCreateDestinationArgument() {
      return this.createDestinationIdentifier;
   }

   public boolean isAdvancedTopicSupported() {
      return this.isAdvancedTopicSupported;
   }

   public int getMemberType() {
      return this.getDeploymentMemberType() & 7;
   }

   public int getDeploymentMemberType() {
      return this.deploymentMemberType;
   }

   public String getMemberTypeAsString() {
      switch (this.getMemberType()) {
         case 1:
            return "MEMBER_TYPE_CLUSTERED_DYNAMIC";
         case 2:
            return "MEMBER_TYPE_CLUSTERED_STATIC";
         case 3:
            return "MEMBER_TYPE_NON_CLUSTERED";
         default:
            return "MEMBER_TYPE_NON_DD";
      }
   }

   public boolean isLocalWLSServer() {
      return this.isLocalWLSServer;
   }

   public boolean isLocalCluster() {
      return this.isLocalCluster;
   }

   public String toString() {
      return this.memberJndiName;
   }

   private String getDDConfigName() {
      return this.ddConfigName;
   }

   private String getDdJndiName() {
      return this.ddJndiName;
   }

   public String getMemberConfigName() {
      return this.memberConfigName;
   }

   public String getMemberLocalJNDIName() {
      return this.memberLocalJndiName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
