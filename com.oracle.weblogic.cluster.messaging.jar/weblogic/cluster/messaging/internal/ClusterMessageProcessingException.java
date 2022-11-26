package weblogic.cluster.messaging.internal;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClusterMessageProcessingException extends RemoteException {
   private final ClusterResponse[] responses;
   private final HashMap failedServers;

   public ClusterMessageProcessingException(Exception reason) {
      super(reason.getMessage(), reason);
      this.responses = null;
      this.failedServers = null;
   }

   public ClusterMessageProcessingException(String reason) {
      super(reason);
      this.responses = null;
      this.failedServers = null;
   }

   public ClusterMessageProcessingException(ClusterResponse[] responses, HashMap failedServers) {
      super(getReason(failedServers));
      this.responses = responses;
      this.failedServers = failedServers;
   }

   public ClusterResponse[] getResponses() {
      return this.responses;
   }

   public HashMap getFailedServers() {
      return this.failedServers;
   }

   private static String getReason(Map failedServers) {
      if (failedServers == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator iter = failedServers.keySet().iterator();

         while(iter.hasNext()) {
            ServerInformation serverInfo = (ServerInformation)iter.next();
            sb.append("Server '" + serverInfo.getServerName() + "' failed due to '" + failedServers.get(serverInfo) + "'\n");
         }

         return sb.toString();
      }
   }
}
