package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.MessageEvent;

public class MessageEventImpl extends DestinationEventImpl implements MessageEvent {
   private Message message;
   private int deliveryCount;

   public MessageEventImpl(String subjectName, Destination destination, Message message, Xid xid, int deliveryCount) {
      super(subjectName, destination, xid);
      this.message = message;
      this.deliveryCount = deliveryCount;
   }

   public Message getMessage() {
      return this.message;
   }

   public int getDeliveryCount() {
      return this.deliveryCount;
   }
}
