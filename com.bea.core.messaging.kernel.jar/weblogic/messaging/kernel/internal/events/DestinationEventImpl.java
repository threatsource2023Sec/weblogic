package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.DestinationEvent;

public class DestinationEventImpl extends EventImpl implements DestinationEvent {
   private Destination destination;

   public DestinationEventImpl(String subjectName, Destination destination, Xid xid) {
      super(subjectName, xid);
      this.destination = destination;
   }

   public Destination getDestination() {
      return this.destination;
   }
}
