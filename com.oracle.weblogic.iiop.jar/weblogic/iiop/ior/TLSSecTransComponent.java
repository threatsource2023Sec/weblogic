package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.ServerIdentity;

public final class TLSSecTransComponent extends TaggedComponent {
   private ListenPoint[] addrs;
   private int supports;
   private int requires;
   private ServerIdentity target;

   public TLSSecTransComponent(ListenPoint listenPoint, ServerIdentity target, RequirementType certificateAuthentication, boolean confidentialityRequired, String... suiteNames) {
      super(36);
      this.setAddress(listenPoint);
      this.target = target;
      CipherSuites suites = CipherSuites.getFromSuiteNames(suiteNames);
      this.supports = suites.getTlsSupports();
      this.requires = suites.getTlsRequires();
      switch (certificateAuthentication) {
         case REQUIRED:
            this.requires |= 64;
         case SUPPORTED:
            this.supports |= 64;
         case NONE:
         default:
            if (confidentialityRequired) {
               this.requires |= 6;
            }

      }
   }

   private void setAddress(ListenPoint listenPoint) {
      this.addrs = new ListenPoint[]{listenPoint};
   }

   TLSSecTransComponent(CorbaInputStream in, ServerIdentity target) {
      super(36);
      this.target = target;
      this.read(in);
   }

   public final ListenPoint[] getAddresses() {
      return this.addrs;
   }

   public final short getSupports() {
      return (short)this.supports;
   }

   public final short getRequires() {
      return (short)this.requires;
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.supports = in.read_short();
      this.requires = in.read_short();
      int numAddr = in.read_long();
      this.addrs = new ListenPoint[numAddr];

      for(int i = 0; i < numAddr; ++i) {
         this.addrs[i] = new ListenPoint(in);
      }

      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_short((short)this.supports);
      out.write_short((short)this.requires);
      int numAddr = this.addrs != null ? this.addrs.length : 0;
      out.write_long(numAddr);

      for(int i = 0; i < numAddr; ++i) {
         if (out.isSecure() && this.target != null) {
            this.addrs[i].getReplacement(out, this.target).write(out);
         } else {
            this.addrs[i].write(out);
         }
      }

      out.endEncapsulation(handle);
   }

   public String toString() {
      String ret = "TLSSecTrans (supports = " + this.supports + ",requires = " + this.requires;
      if (this.addrs != null) {
         ret = ret + " addresses{ ";
         ListenPoint[] var2 = this.addrs;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ListenPoint addr = var2[var4];
            ret = ret + " " + addr.getAddress() + ":" + addr.getPort();
         }

         ret = ret + "} ";
      }

      return ret;
   }
}
