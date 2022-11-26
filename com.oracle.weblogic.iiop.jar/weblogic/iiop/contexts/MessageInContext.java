package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class MessageInContext extends ContextBody {
   private long clientContextId;
   private boolean discardContext;

   MessageInContext(long id, boolean discard) {
      this.clientContextId = id;
      this.discardContext = discard;
   }

   MessageInContext(CorbaInputStream in) {
      this.clientContextId = in.read_longlong();
      this.discardContext = in.read_boolean();
   }

   public long getClientContextId() {
      return this.clientContextId;
   }

   public boolean isDiscardContext() {
      return this.discardContext;
   }

   public void write(CorbaOutputStream out) {
      out.write_longlong(this.clientContextId);
      out.write_boolean(this.discardContext);
   }

   public String toString() {
      return "MessageInContext (clientContext = " + this.clientContextId + ", discardContext = " + this.discardContext + ")";
   }
}
