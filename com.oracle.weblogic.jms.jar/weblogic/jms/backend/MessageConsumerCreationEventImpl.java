package weblogic.jms.backend;

import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.internal.events.DestinationEventImpl;

public class MessageConsumerCreationEventImpl extends DestinationEventImpl implements MessageConsumerCreationEvent {
   private String selector = null;
   private String userBlob = null;

   public MessageConsumerCreationEventImpl(String subjectName, Destination destination, String selector, String userBlob) {
      super(subjectName, destination, (Xid)null);
      this.selector = selector;
      this.userBlob = userBlob;
   }

   public String getSelector() {
      return this.selector;
   }

   public String getUserBlob() {
      return this.userBlob;
   }
}
