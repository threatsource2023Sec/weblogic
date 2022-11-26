package weblogic.iiop.ior;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.corba.cos.security.GSSUtil;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class SASContextSec {
   private static final int SUPPORTED_TYPES = 15;
   private short supports = 0;
   private short requires = 0;
   private int supportedIdentityTypes = 0;
   private List oidSequences = new ArrayList();

   public SASContextSec(RequirementType assertionRequirement, int supportedIdentityTypes) {
      this.supportedIdentityTypes = supportedIdentityTypes;
      if (supportedIdentityTypes != 0) {
         this.setFlags(assertionRequirement);
      }

      this.oidSequences.add(GSSUtil.getGSSUPMech());
   }

   private void setFlags(RequirementType assertionRequirement) {
      switch (assertionRequirement) {
         case REQUIRED:
            this.requires = 1024;
         case SUPPORTED:
            this.supports = 1024;
         case NONE:
         default:
      }
   }

   public SASContextSec(CorbaInputStream in) {
      this.read(in);
   }

   public final void read(CorbaInputStream in) {
      this.supports = in.read_short();
      this.requires = in.read_short();
      this.readAndIgnoreServiceConfigurationList(in);
      this.oidSequences = new ArrayList();
      int numOIDs = in.read_long();

      for(int i = 0; i < numOIDs; ++i) {
         this.oidSequences.add(in.read_octet_sequence());
      }

      this.supportedIdentityTypes = in.read_long();
   }

   private void readAndIgnoreServiceConfigurationList(CorbaInputStream in) {
      int numServiceConfigurations = in.read_long();

      for(int i = 0; i < numServiceConfigurations; ++i) {
         in.read_long();
         in.read_octet_sequence();
      }

   }

   public final void write(CorbaOutputStream out) {
      out.write_short(this.supports);
      out.write_short(this.requires);
      out.write_long(0);
      if (this.supports != 0) {
         out.write_long(this.oidSequences.size());
         Iterator var2 = this.oidSequences.iterator();

         while(var2.hasNext()) {
            byte[] oidSequence = (byte[])var2.next();
            out.write_octet_sequence(oidSequence);
         }

         out.write_long(this.supportedIdentityTypes);
      } else {
         out.write_long(0);
         out.write_long(0);
      }

   }

   public short getSupports() {
      return this.supports;
   }

   public short getRequires() {
      return this.requires;
   }

   public final boolean hasGSSUPIdentity() {
      if ((this.supports & 1024) == 0) {
         return false;
      } else if ((this.supportedIdentityTypes & 15) == 0) {
         return false;
      } else {
         Iterator var1 = this.oidSequences.iterator();

         byte[] oidSequence;
         do {
            if (!var1.hasNext()) {
               return false;
            }

            oidSequence = (byte[])var1.next();
         } while(!GSSUtil.isGSSUPMech(oidSequence));

         return true;
      }
   }

   public String toString() {
      return "SASContextSec (supports = " + this.supports + ",requires = " + this.requires + ")";
   }

   public int getSupportedIdentityTypes() {
      return this.supportedIdentityTypes;
   }
}
