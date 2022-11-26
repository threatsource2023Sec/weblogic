package weblogic.cluster.leasing.databaseless;

import java.util.HashMap;
import weblogic.cluster.messaging.internal.ClusterMessageProcessingException;
import weblogic.cluster.messaging.internal.ClusterResponse;

public class AssumedClusterLeaderMessageException extends ClusterMessageProcessingException {
   private static final long serialVersionUID = -510834080848939801L;

   public AssumedClusterLeaderMessageException(Exception reason) {
      super(reason);
   }

   public AssumedClusterLeaderMessageException(String reason) {
      super(reason);
   }

   public AssumedClusterLeaderMessageException(ClusterResponse[] responses, HashMap failedServers) {
      super(responses, failedServers);
   }
}
