package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.MessageExpirationEvent;

public class MessageExpirationEventImpl extends MessageRemoveEventImpl implements MessageExpirationEvent {
   private long expirationTime;

   public MessageExpirationEventImpl(String subjectName, Destination destination, Message message, Xid xid, int deliveryCount, long expirationTime) {
      super(subjectName, destination, message, xid, deliveryCount);
      this.expirationTime = expirationTime;
   }

   public long getExpirationTime() {
      return this.expirationTime;
   }
}
