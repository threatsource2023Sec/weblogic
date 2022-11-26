package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public abstract class SequencedMessage extends Message {
   public int request_id;

   public SequencedMessage(MessageHeader msgHdr, CorbaInputStream inputStream) {
      super(msgHdr, inputStream);
   }

   public SequencedMessage(MessageHeader msgHdr) {
      super(msgHdr);
   }

   protected SequencedMessage(int messageType, int minorVersion, int request_id) {
      super(new MessageHeader(messageType, minorVersion));
      this.request_id = request_id;
   }

   public final int getRequestID() {
      return this.request_id;
   }

   public final int hashCode() {
      return this.request_id;
   }

   public final boolean equals(Object o) {
      if (o instanceof Message) {
         return o.hashCode() == this.hashCode();
      } else {
         return false;
      }
   }

   protected void readRequestId(CorbaInputStream is) {
      this.request_id = is.read_long();
   }

   protected void writeRequestId(CorbaOutputStream out) {
      out.write_long(this.request_id);
   }
}
