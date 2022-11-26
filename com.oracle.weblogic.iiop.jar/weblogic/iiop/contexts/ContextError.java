package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.utils.Hex;

public class ContextError extends ContextBody {
   private long clientContextId;
   private int majorStatus;
   private int minorStatus;
   private byte[] errorToken;

   public ContextError(long id, int major, int minor, byte[] errorToken) {
      this.clientContextId = id;
      this.majorStatus = major;
      this.minorStatus = minor;
      this.errorToken = errorToken;
   }

   ContextError(CorbaInputStream in) {
      this.clientContextId = in.read_longlong();
      this.majorStatus = in.read_long();
      this.minorStatus = in.read_long();
      this.errorToken = in.read_octet_sequence();
   }

   public long getClientContextId() {
      return this.clientContextId;
   }

   public boolean shouldDiscardContext() {
      return true;
   }

   public void write(CorbaOutputStream out) {
      out.write_longlong(this.clientContextId);
      out.write_long(this.majorStatus);
      out.write_long(this.minorStatus);
      out.write_octet_sequence(this.errorToken);
   }

   public String toString() {
      return "ContextError (clientContext = " + this.clientContextId + ", major = " + this.majorStatus + ", minor = " + this.minorStatus + ", errorToken = " + Hex.dump(this.errorToken) + ")";
   }
}
