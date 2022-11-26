package weblogic.transaction.internal;

import java.util.EventObject;

public class Notification extends EventObject {
   protected long sequenceNumber;
   protected long timeStamp;
   protected String message;

   public Notification(Object source, long sequenceNumber, long timeStamp, String message) {
      super(source);
      this.sequenceNumber = sequenceNumber;
      this.timeStamp = timeStamp;
      this.message = message;
   }

   public String toString() {
      return "Notification {sequenceNumber=" + this.sequenceNumber + ",timeStammp=" + this.timeStamp + ",message=" + this.message + "}";
   }
}
