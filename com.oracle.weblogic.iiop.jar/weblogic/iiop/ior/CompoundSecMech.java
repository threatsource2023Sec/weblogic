package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.ServerIdentity;

public class CompoundSecMech {
   private short requires;
   private TaggedComponent transportMech;
   private ASContextSec asContextMech;
   private SASContextSec sasContextMech;
   private boolean foreign = false;
   private boolean alwaysWriteTransportMech = false;

   public CompoundSecMech(CorbaInputStream in, ServerIdentity target) {
      this.read(in, target);
      this.foreign = true;
   }

   public CompoundSecMech(ASContextSec asContextMech, SASContextSec sasContextMech) {
      this.asContextMech = asContextMech;
      this.sasContextMech = sasContextMech;
      this.requires = (short)(asContextMech.getRequires() | sasContextMech.getRequires());
   }

   public void addTransportMech(TLSSecTransComponent transportMech, boolean plainPortDisabled) {
      this.transportMech = transportMech;
      this.requires |= transportMech.getRequires();
      this.alwaysWriteTransportMech = plainPortDisabled;
   }

   public final TaggedComponent getTransportMech() {
      return this.transportMech;
   }

   public final ASContextSec getASContextMech() {
      return this.asContextMech;
   }

   public final SASContextSec getSASContextMech() {
      return this.sasContextMech;
   }

   public final boolean useSAS() {
      return this.asContextMech != null && this.asContextMech.hasGSSUP() || this.sasContextMech != null && this.sasContextMech.hasGSSUPIdentity();
   }

   public final boolean hasGSSUP() {
      return this.asContextMech != null && this.asContextMech.hasGSSUP();
   }

   public final boolean hasGSSUPIdentity() {
      return this.sasContextMech != null && this.sasContextMech.hasGSSUPIdentity();
   }

   public final byte[] getGSSUPTarget() {
      return this.asContextMech == null ? null : this.asContextMech.getGSSUPTarget();
   }

   public final String getSecureHost() {
      if (this.transportMech != null && this.transportMech instanceof TLSSecTransComponent) {
         TLSSecTransComponent tls = (TLSSecTransComponent)this.transportMech;
         ListenPoint[] addrs = tls.getAddresses();
         if (addrs != null) {
            return addrs[0].getAddress();
         }
      }

      return null;
   }

   public final int getSecurePort() {
      if (this.transportMech != null && this.transportMech instanceof TLSSecTransComponent) {
         TLSSecTransComponent tls = (TLSSecTransComponent)this.transportMech;
         ListenPoint[] addrs = tls.getAddresses();
         if (addrs != null) {
            return addrs[0].getPort();
         }
      }

      return -1;
   }

   public final void read(CorbaInputStream in, ServerIdentity target) {
      this.requires = in.read_short();
      int tag = in.read_long();
      switch (tag) {
         case 34:
            new TaggedComponent(tag, in);
            this.transportMech = null;
            break;
         case 36:
            this.transportMech = new TLSSecTransComponent(in, target);
            break;
         default:
            this.transportMech = new TaggedComponent(tag, in);
      }

      this.asContextMech = new ASContextSec(in);
      this.sasContextMech = new SASContextSec(in);
   }

   public final void write(CorbaOutputStream out) {
      out.write_short(this.requires);
      if (this.transportMech != null && this.mustWriteTransportMech(out)) {
         this.transportMech.write(out);
      } else {
         out.write_long(34);
         out.write_long(0);
      }

      this.asContextMech.write(out);
      this.sasContextMech.write(out);
   }

   boolean mustWriteTransportMech(CorbaOutputStream out) {
      return this.foreign || this.alwaysWriteTransportMech || out.supportsTLS();
   }

   public String toString() {
      return "CompoundSecMech (requires = " + this.requires + "\n  tranport = " + this.transportMech + "\n  ASContext = " + this.asContextMech + "\n  SASContext = " + this.sasContextMech + ")";
   }
}
