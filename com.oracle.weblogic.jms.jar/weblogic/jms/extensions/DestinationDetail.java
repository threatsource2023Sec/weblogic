package weblogic.jms.extensions;

import javax.jms.Destination;

public interface DestinationDetail {
   int DESTINATION_TYPE_PHYSICAL_QUEUE = 0;
   int DESTINATION_TYPE_PHYSICAL_TOPIC = 1;
   int DESTINATION_TYPE_FOREIGN_QUEUE = 2;
   int DESTINATION_TYPE_FOREIGN_TOPIC = 3;
   int DESTINATION_TYPE_DD_QUEUE = 4;
   int DESTINATION_TYPE_REPLICATED_DT = 5;
   int DESTINATION_TYPE_PARTITIONED_DT = 6;

   String getMemberConfigName();

   String getJNDIName();

   Destination getDestination();

   int getType();

   String getTypeAsString();

   String getWLSServerName();

   String getJMSServerName();

   String getJMSServerConfigName();

   String getStoreName();

   String getMigratableTargetName();

   String getCreateDestinationArgument();

   boolean isAdvancedTopicSupported();

   boolean isLocalWLSServer();

   boolean isLocalCluster();

   int getMemberType();

   String getMemberTypeAsString();

   String getPartitionName();

   String toString();
}
