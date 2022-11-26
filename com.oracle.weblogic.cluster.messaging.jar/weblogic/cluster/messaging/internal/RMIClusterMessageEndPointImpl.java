package weblogic.cluster.messaging.internal;

import java.rmi.RemoteException;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.Work;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class RMIClusterMessageEndPointImpl implements ClusterMessageEndPoint {
   private static final DebugCategory debugMessageEndPoint = Debug.getCategory("weblogic.cluster.leasing.MessageEndPoint");
   private static final boolean DEBUG = debugEnabled();
   private PingMessageListener listener;

   public ClusterResponse process(ClusterMessage message) throws ClusterMessageProcessingException {
      if (message.getMessageType() == 9) {
         if (DEBUG) {
            debug("received PING from " + message.getSenderInformation());
         }

         this.invokeListener(message.getSenderInformation());
         return null;
      } else {
         ClusterMessageReceiver receiver = ClusterMessageFactory.getInstance().getMessageReceiver(message);
         if (DEBUG) {
            debug("dispatching " + message + " to " + receiver);
         }

         if (receiver == null) {
            throw new ClusterMessageProcessingException("leasing is not ready!");
         } else {
            return receiver.process(message);
         }
      }
   }

   public void processOneWay(ClusterMessage message) throws RemoteException {
      if (message.getMessageType() == 9) {
         if (DEBUG) {
            debug("received PING ONE_WAY from " + message.getSenderInformation());
         }

         this.invokeListener(message.getSenderInformation());
      } else {
         ClusterMessageReceiver receiver = ClusterMessageFactory.getInstance().getMessageReceiver(message);
         if (DEBUG) {
            debug("dispatching ONE_WAY " + message + " to " + receiver);
         }

         if (receiver == null) {
            throw new RemoteException("ClusterMessageProcessing: leasing is not ready!");
         } else {
            receiver.process(message);
         }
      }
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[RMIClusterMessageEndPointImpl] " + s);
   }

   public static RMIClusterMessageEndPointImpl getInstance() {
      return RMIClusterMessageEndPointImpl.Singleton.SINGLETON;
   }

   private static boolean debugEnabled() {
      return true;
   }

   public void registerPingMessageListener(PingMessageListener listener) {
      this.listener = listener;
   }

   private void invokeListener(final ServerInformation senderInformation) {
      debug("invoking listener: " + this.listener + " for sender: " + senderInformation.getServerName());
      if (this.listener != null) {
         final PingMessageListener callback = this.listener;
         Work work = new WorkAdapter() {
            public void run() {
               RMIClusterMessageEndPointImpl.debug(this.toString());
               callback.pingReceived(senderInformation);
            }

            public String toString() {
               return callback + ": Work Adapter ping Received from " + senderInformation.getServerName();
            }
         };
         WorkManagerFactory.getInstance().getSystem().schedule(work);
      }

   }

   private static class Singleton {
      private static final RMIClusterMessageEndPointImpl SINGLETON = new RMIClusterMessageEndPointImpl();
   }
}
