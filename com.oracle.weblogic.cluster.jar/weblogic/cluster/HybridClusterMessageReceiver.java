package weblogic.cluster;

import weblogic.rmi.spi.HostID;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class HybridClusterMessageReceiver extends ClusterMessageReceiver {
   private boolean httpReqDispatched;
   private MulticastSessionId multicastSessionId;
   private final HostID memberID;

   HybridClusterMessageReceiver(HostID memberID, MulticastSessionId multicastSessionId, WorkManager workManager) {
      super(memberID, multicastSessionId, workManager);
      this.multicastSessionId = multicastSessionId;
      this.memberID = memberID;
   }

   void processLastSeqNum(long lastSeqNum) {
      boolean isInActive = PartitionAwareSenderManager.theOne().isMulticastSessionInactive(this.multicastSessionId);
      if (!isInActive) {
         this.LogMessage("Received seqNum: " + lastSeqNum + ", currentSeqNum: " + this.currentSeqNum + " for " + this.multicastSessionId + " from memberID: " + this.memberID);
         if (lastSeqNum >= this.currentSeqNum) {
            this.fetchStateDumpOverHttp(lastSeqNum);
         }

      }
   }

   void setInSync(long lastSeqNum) {
      synchronized(this) {
         this.httpReqDispatched = false;
         super.setInSync(lastSeqNum);
      }
   }

   protected void setOutOfSync() {
   }

   void setHttpRequestDispatched(boolean b) {
      synchronized(this) {
         this.httpReqDispatched = b;
      }
   }

   private void fetchStateDumpOverHttp(long lastSeqNum) {
      synchronized(this) {
         if (this.httpReqDispatched) {
            return;
         }

         this.httpReqDispatched = true;
      }

      HTTPExecuteRequest request = new HTTPExecuteRequest(lastSeqNum, this.multicastSessionId, this.memberID);
      WorkManagerFactory.getInstance().getSystem().schedule(request);
   }

   synchronized void dispatch(long seqNum, int fragNum, int size, int offset, boolean isRecover, boolean retryEnabled, byte[] payload) {
      if (!this.httpReqDispatched) {
         super.dispatch(seqNum, fragNum, size, offset, isRecover, retryEnabled, payload);
      }
   }

   void LogMessage(String str) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("[HybridClusterMessageReceiver]: " + str);
      }

   }
}
