package weblogic.iiop.ior;

import weblogic.corba.cos.security.GSSUtil;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.utils.Hex;

public class ASContextSec {
   private short supports;
   private short requires;
   private byte[] clientAuthenticationMech;
   private byte[] targetName;

   public ASContextSec(RequirementType authenticationRequirement, String securityRealmName) {
      this.setFlags(authenticationRequirement);
      this.clientAuthenticationMech = GSSUtil.getGSSUPMech();
      this.targetName = GSSUtil.createGSSUPGSSNTExportedName(securityRealmName);
   }

   private void setFlags(RequirementType authenticationRequirement) {
      switch (authenticationRequirement) {
         case REQUIRED:
            this.requires = 64;
         case SUPPORTED:
            this.supports = 64;
         case NONE:
         default:
      }
   }

   public ASContextSec(CorbaInputStream in) {
      this.read(in);
   }

   public final void read(CorbaInputStream in) {
      this.supports = in.read_short();
      this.requires = in.read_short();
      this.clientAuthenticationMech = in.read_octet_sequence();
      this.targetName = in.read_octet_sequence();
   }

   public final void write(CorbaOutputStream out) {
      out.write_short(this.supports);
      out.write_short(this.requires);
      out.write_octet_sequence(this.clientAuthenticationMech);
      out.write_octet_sequence(this.targetName);
   }

   public final boolean hasGSSUP() {
      return this.supports != 0 && GSSUtil.isGSSUPMech(this.clientAuthenticationMech);
   }

   public final byte[] getGSSUPTarget() {
      return this.targetName;
   }

   public short getSupports() {
      return this.supports;
   }

   public short getRequires() {
      return this.requires;
   }

   public String toString() {
      return "ASContextSec (supports = " + this.supports + ",requires = " + this.requires + ",clientAuthMech = " + Hex.dump(this.clientAuthenticationMech) + ",targetName = " + Hex.dump(this.targetName) + ")";
   }
}
