package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class SFVContext extends ServiceContext {
   private byte maxFormatVersion;

   public byte getMaxFormatVersion() {
      return this.maxFormatVersion;
   }

   public SFVContext(byte sfv) {
      super(17);
      this.maxFormatVersion = sfv;
   }

   public SFVContext(CorbaInputStream in) {
      super(17);
      this.readEncapsulatedContext(in);
   }

   protected final void readEncapsulation(CorbaInputStream in) {
      this.maxFormatVersion = in.read_octet();
   }

   public final void write(CorbaOutputStream out) {
      out.write_octet(this.maxFormatVersion);
   }

   public String toString() {
      return "SFVContext: " + this.maxFormatVersion;
   }
}
