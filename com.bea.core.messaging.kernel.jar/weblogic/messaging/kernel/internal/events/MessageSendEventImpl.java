package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.MessageSendEvent;

public class MessageSendEventImpl extends MessageEventImpl implements MessageSendEvent {
   public MessageSendEventImpl(String subjectName, Destination destination, Message message, Xid xid, int deliveryCount) {
      super(subjectName, destination, message, xid, deliveryCount);
   }
}
