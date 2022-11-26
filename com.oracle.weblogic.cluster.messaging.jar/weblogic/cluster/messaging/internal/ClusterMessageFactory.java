package weblogic.cluster.messaging.internal;

import java.util.ArrayList;
import java.util.Iterator;

public class ClusterMessageFactory {
   private final ArrayList messageReceivers = new ArrayList();
   private ClusterMessageSenderWrapper defaultSender;

   public static ClusterMessageFactory getInstance() {
      return ClusterMessageFactory.Factory.THE_ONE;
   }

   public synchronized ClusterMessageSender getDefaultMessageSender() {
      if (this.defaultSender == null) {
         this.defaultSender = new ClusterMessageSenderWrapper(RMIClusterMessageSenderImpl.getInstance());
      }

      return this.defaultSender;
   }

   public ClusterMessageSender getOneWayMessageSender() {
      return RMIClusterMessageSenderImpl.getOneWay();
   }

   public synchronized void registerReceiver(ClusterMessageReceiver receiver) {
      this.messageReceivers.add(receiver);
   }

   public synchronized ClusterMessageReceiver getMessageReceiver(ClusterMessage message) {
      Iterator iter = this.messageReceivers.iterator();

      ClusterMessageReceiver receiver;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         receiver = (ClusterMessageReceiver)iter.next();
      } while(!receiver.accept(message));

      return receiver;
   }

   public void registerMessageDeliveryFailureListener(MessageDeliveryFailureListener listener) {
      this.getDefaultMessageSender();
      this.defaultSender.addMessageDeliveryFailureListener(listener);
   }

   public void removeMessageDeliveryFailureListener(MessageDeliveryFailureListener listener) {
      this.getDefaultMessageSender();
      this.defaultSender.removeMessageDeliveryFailureListener(listener);
   }

   private static final class Factory {
      static final ClusterMessageFactory THE_ONE = new ClusterMessageFactory();
   }
}
