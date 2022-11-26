package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class SFVComponent extends TaggedComponent {
   public static final TaggedComponent VERSION_2 = new SFVComponent((byte)2);
   private byte maxFormatVersion;

   public SFVComponent(byte sfv) {
      super(38);
      this.maxFormatVersion = sfv;
   }

   public byte getMaxFormatVersion() {
      return this.maxFormatVersion;
   }

   public SFVComponent(CorbaInputStream in) {
      super(38);
      this.read(in);
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.maxFormatVersion = in.read_octet();
      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_octet(this.maxFormatVersion);
      out.endEncapsulation(handle);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SFVComponent that = (SFVComponent)o;
         return this.maxFormatVersion == that.maxFormatVersion;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.maxFormatVersion;
   }

   public String toString() {
      return "SFVComponent: " + this.maxFormatVersion;
   }
}
