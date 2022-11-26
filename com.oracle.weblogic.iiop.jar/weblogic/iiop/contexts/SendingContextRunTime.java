package weblogic.iiop.contexts;

import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class SendingContextRunTime extends ServiceContext {
   private IOR codebase;

   public SendingContextRunTime(IOR codebase) {
      super(6);
      this.codebase = codebase;
   }

   public final IOR getCodeBase() {
      return this.codebase;
   }

   SendingContextRunTime(CorbaInputStream in) {
      super(6);
      this.readEncapsulatedContext(in);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      this.codebase.write(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.codebase = new IOR(in);
   }

   public String toString() {
      return "SendingContextRunTime Context (" + this.codebase + ")";
   }
}
