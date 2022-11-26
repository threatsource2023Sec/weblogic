package weblogic.iiop.contexts;

import weblogic.iiop.EndPoint;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;

public final class UnknownExceptionInfo extends ServiceContext {
   private transient Throwable t;
   private byte[] encapsulation;

   public UnknownExceptionInfo(Throwable t) {
      super(9);
      this.t = t;
   }

   UnknownExceptionInfo(CorbaInputStream in) {
      super(9);
      this.encapsulation = in.read_octet_sequence();
   }

   public Throwable getNestedThrowable(EndPoint endPoint) {
      if (this.t == null) {
         this.readEncapsulation(IiopProtocolFacade.createInputStream(endPoint, this.encapsulation));
      }

      return this.t;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_value(this.t);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      in.consumeEndian();
      this.t = (Throwable)in.read_value();
   }

   public String toString() {
      return "UnknownExceptionInfo Context (" + this.t + ")";
   }
}
