package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.utils.Hex;

public class CompleteEstablishContext extends ContextBody {
   private long clientContextId;
   private boolean contextStateful;
   private byte[] finalContextToken;

   CompleteEstablishContext(long id, boolean stateful, byte[] finalToken) {
      this.clientContextId = id;
      this.contextStateful = stateful;
      this.finalContextToken = finalToken;
   }

   CompleteEstablishContext(CorbaInputStream in) {
      this.clientContextId = in.read_longlong();
      this.contextStateful = in.read_boolean();
      this.finalContextToken = in.read_octet_sequence();
   }

   public long getClientContextId() {
      return this.clientContextId;
   }

   public boolean shouldEstablishContext() {
      return this.contextStateful;
   }

   public boolean shouldDiscardContext() {
      return !this.contextStateful;
   }

   public void write(CorbaOutputStream out) {
      out.write_longlong(this.clientContextId);
      out.write_boolean(this.contextStateful);
      out.write_octet_sequence(this.finalContextToken);
   }

   public String toString() {
      return "CompleteEstablishContext (clientContext = " + this.clientContextId + ", contextStateful = " + this.contextStateful + ", finalContextToken = " + Hex.dump(this.finalContextToken) + ")";
   }
}
