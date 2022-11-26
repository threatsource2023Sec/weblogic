package weblogic.iiop.ior;

import weblogic.corba.cos.security.GSSUtil;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.protocol.ServerIdentity;

public class CompoundSecMechList extends TaggedComponent {
   private boolean statefulContext = true;
   private int numSecMechs = 0;
   private CompoundSecMech[] secMechs;

   public CompoundSecMechList(boolean statefulContext, CompoundSecMech... secMechs) {
      super(33);
      this.secMechs = secMechs;
      this.statefulContext = statefulContext;
      this.numSecMechs = this.secMechs.length;
   }

   public CompoundSecMechList(CorbaInputStream in, ServerIdentity target) {
      super(33);
      this.read(in, target);
   }

   public final CompoundSecMech[] getCompoundSecMechs() {
      return this.secMechs;
   }

   public final boolean useSAS() {
      for(int i = 0; i < this.numSecMechs; ++i) {
         if (this.secMechs[i].useSAS()) {
            return true;
         }
      }

      return false;
   }

   public final boolean hasGSSUP() {
      for(int i = 0; i < this.numSecMechs; ++i) {
         if (this.secMechs[i].hasGSSUP()) {
            return true;
         }
      }

      return false;
   }

   public final boolean hasGSSUPIdentity() {
      for(int i = 0; i < this.numSecMechs; ++i) {
         if (this.secMechs[i].hasGSSUPIdentity()) {
            return true;
         }
      }

      return false;
   }

   public final byte[] getGSSUPTarget() {
      for(int i = 0; i < this.numSecMechs; ++i) {
         if (this.secMechs[i].hasGSSUP()) {
            byte[] target = this.secMechs[i].getGSSUPTarget();
            if (target != null) {
               return target;
            }
         }
      }

      return null;
   }

   public final boolean isGSSUPTargetStateful() {
      return this.statefulContext;
   }

   public final String getSecureHost() {
      for(int i = 0; i < this.numSecMechs; ++i) {
         String host = this.secMechs[i].getSecureHost();
         if (host != null) {
            return host;
         }
      }

      return null;
   }

   public final int getSecurePort() {
      for(int i = 0; i < this.numSecMechs; ++i) {
         int port = this.secMechs[i].getSecurePort();
         if (port != -1) {
            return port;
         }
      }

      return -1;
   }

   public final void read(CorbaInputStream in, ServerIdentity target) {
      long handle = in.startEncapsulation();
      this.statefulContext = in.read_boolean();
      this.numSecMechs = in.read_long();
      this.secMechs = new CompoundSecMech[this.numSecMechs];

      for(int i = 0; i < this.numSecMechs; ++i) {
         this.secMechs[i] = new CompoundSecMech(in, target);
      }

      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_boolean(this.statefulContext);
      out.write_long(this.numSecMechs);

      for(int i = 0; i < this.numSecMechs; ++i) {
         this.secMechs[i].write(out);
      }

      out.endEncapsulation(handle);
   }

   public String toString() {
      String str = "CompoundSecMechList (stateful = " + this.statefulContext + ",numSecMechs = " + this.numSecMechs + ", secMechs = ";

      for(int i = 0; i < this.numSecMechs; ++i) {
         str = str + this.secMechs[i];
      }

      return str + ")";
   }

   public String getTargetName() {
      CompoundSecMech[] var1 = this.secMechs;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         CompoundSecMech secMech = var1[var3];
         if (secMech.getGSSUPTarget() != null) {
            return GSSUtil.extractGSSUPGSSNTExportedName(secMech.getGSSUPTarget());
         }
      }

      return null;
   }
}
