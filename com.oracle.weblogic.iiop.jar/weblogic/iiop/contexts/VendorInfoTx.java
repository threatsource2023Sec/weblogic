package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.transaction.internal.PropagationContext;

public final class VendorInfoTx extends ServiceContext {
   private PropagationContext ctx;

   public VendorInfoTx() {
      super(1111834881);
   }

   public VendorInfoTx(PropagationContext pc) {
      super(1111834881);
      this.ctx = pc;
   }

   public PropagationContext getTxContext() {
      return this.ctx;
   }

   protected VendorInfoTx(CorbaInputStream in) {
      super(1111834881);
      this.readEncapsulatedContext(in);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.ctx = (PropagationContext)in.read_value();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_value(this.ctx);
   }

   public String toString() {
      return "VendorInfoTx Context: " + this.ctx;
   }
}
