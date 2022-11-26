package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.MessageRedeliveryLimitEvent;

public class MessageRedeliveryLimitEventImpl extends MessageRemoveEventImpl implements MessageRedeliveryLimitEvent {
   private int redeliveryLimit;
   private int deliveryCount;

   public MessageRedeliveryLimitEventImpl(String subjectName, Destination destination, Message message, Xid xid, int redeliveryLimit, int deliveryCount) {
      super(subjectName, destination, message, xid, deliveryCount);
      this.redeliveryLimit = redeliveryLimit;
      this.deliveryCount = deliveryCount;
   }

   public int getRedeliveryLimit() {
      return this.redeliveryLimit;
   }

   public int getDeliveryCount() {
      return this.deliveryCount;
   }
}
