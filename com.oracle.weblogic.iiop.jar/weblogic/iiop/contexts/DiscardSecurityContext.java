package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class DiscardSecurityContext extends ServiceContext {
   private long securityContextId;

   public DiscardSecurityContext(long securityContextId) {
      super(1111834893);
      this.securityContextId = securityContextId;
   }

   protected DiscardSecurityContext(CorbaInputStream in) {
      super(1111834893);
      this.readEncapsulatedContext(in);
   }

   public long getSecurityContextId() {
      return this.securityContextId;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.securityContextId = in.read_longlong();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_longlong(this.securityContextId);
   }

   public String toString() {
      return "DiscardSecurityContext: securityContextid = " + this.securityContextId;
   }
}
