package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.MessageRemoveEvent;

public class MessageRemoveEventImpl extends MessageEventImpl implements MessageRemoveEvent {
   public MessageRemoveEventImpl(String subjectName, Destination destination, Message message, Xid xid, int deliveryCount) {
      super(subjectName, destination, message, xid, deliveryCount);
   }
}
