package weblogic.iiop.contexts;

import java.util.Arrays;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.utils.Hex;

public final class VendorInfoTrace extends ServiceContext {
   private byte[] trace;

   public VendorInfoTrace() {
      super(1111834890);
   }

   public VendorInfoTrace(byte[] trace) {
      super(1111834890);
      this.trace = trace;
   }

   public byte[] getTrace() {
      return this.trace;
   }

   protected VendorInfoTrace(CorbaInputStream in) {
      super(1111834890);
      this.readEncapsulatedContext(in);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.trace = in.read_octet_sequence();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_octet_sequence(this.trace);
   }

   public String toString() {
      return "VendorInfoTrace buffer: " + Hex.asHex(this.trace);
   }

   public boolean equals(Object obj) {
      return obj instanceof VendorInfoTrace && Arrays.equals(this.trace, ((VendorInfoTrace)obj).trace);
   }

   public int hashCode() {
      return Arrays.hashCode(this.trace);
   }
}
