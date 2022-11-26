package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.MessageReceiveEvent;

public class MessageReceiveEventImpl extends MessageRemoveEventImpl implements MessageReceiveEvent {
   private String userBlob;

   public MessageReceiveEventImpl(String subjectName, Destination destination, Message message, Xid xid, String userBlob, int deliveryCount) {
      super(subjectName, destination, message, xid, deliveryCount);
      this.userBlob = userBlob;
   }

   public String getUserBlob() {
      return this.userBlob;
   }

   public String getSelector() {
      return null;
   }
}
