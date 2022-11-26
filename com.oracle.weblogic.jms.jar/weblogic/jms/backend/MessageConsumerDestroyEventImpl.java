package weblogic.jms.backend;

import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.internal.events.DestinationEventImpl;

public class MessageConsumerDestroyEventImpl extends DestinationEventImpl implements MessageConsumerDestroyEvent {
   private String userBlob = null;

   public MessageConsumerDestroyEventImpl(String subjectName, Destination destination, String userBlob) {
      super(subjectName, destination, (Xid)null);
      this.userBlob = userBlob;
   }

   public String getUserBlob() {
      return this.userBlob;
   }

   public String getSelector() {
      return null;
   }
}
