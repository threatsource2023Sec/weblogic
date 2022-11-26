package weblogic.iiop.contexts;

import java.util.Arrays;
import org.omg.CORBA.MARSHAL;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.utils.ArrayUtils;
import weblogic.utils.Hex;

public class IdentityToken {
   private int identityType;
   private boolean absent;
   private boolean anonymous;
   private byte[] principalName;
   private byte[] certChain;
   private byte[] distinguishedName;
   private int hash;

   public IdentityToken() {
   }

   public IdentityToken(int type, boolean initBool, byte[] initByte) {
      this.identityType = type;
      switch (this.identityType) {
         case 0:
            this.absent = initBool;
            break;
         case 1:
            this.anonymous = initBool;
            break;
         case 2:
            this.principalName = initByte;
            break;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new MARSHAL("Unsupported Identity Type.");
         case 4:
            this.certChain = initByte;
            break;
         case 8:
            this.distinguishedName = initByte;
      }

      this.hash = type ^ ArrayUtils.hashCode(initByte);
   }

   protected IdentityToken(CorbaInputStream in) {
      this.identityType = in.read_long();
      long handle;
      switch (this.identityType) {
         case 0:
            this.absent = in.read_boolean();
            this.hash = this.identityType ^ 0;
            break;
         case 1:
            this.anonymous = in.read_boolean();
            this.hash = this.identityType ^ 0;
            break;
         case 2:
            handle = in.startEncapsulation();
            this.principalName = in.read_octet_sequence();
            in.endEncapsulation(handle);
            this.hash = this.identityType ^ ArrayUtils.hashCode(this.principalName);
            break;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new MARSHAL("Unsupported Identity Type.");
         case 4:
            handle = in.startEncapsulation();
            this.certChain = in.read_octet_sequence();
            in.endEncapsulation(handle);
            this.hash = this.identityType ^ ArrayUtils.hashCode(this.certChain);
            break;
         case 8:
            handle = in.startEncapsulation();
            this.distinguishedName = in.read_octet_sequence();
            in.endEncapsulation(handle);
            this.hash = this.identityType ^ ArrayUtils.hashCode(this.distinguishedName);
      }

   }

   public int getIdentityType() {
      return this.identityType;
   }

   public boolean getAbsent() {
      return this.absent;
   }

   public boolean getAnonymous() {
      return this.anonymous;
   }

   public byte[] getPrincipalName() {
      return this.principalName;
   }

   public byte[] getCertChain() {
      return this.certChain;
   }

   public byte[] getDistinguishedName() {
      return this.distinguishedName;
   }

   public void write(CorbaOutputStream out) {
      out.write_long(this.identityType);
      long handle;
      switch (this.identityType) {
         case 0:
            out.write_boolean(this.absent);
            break;
         case 1:
            out.write_boolean(this.anonymous);
            break;
         case 2:
            handle = out.startEncapsulation();
            out.write_octet_sequence(this.principalName);
            out.endEncapsulation(handle);
            break;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new MARSHAL("Unsupported Identity Type.");
         case 4:
            handle = out.startEncapsulation();
            out.write_octet_sequence(this.certChain);
            out.endEncapsulation(handle);
            break;
         case 8:
            handle = out.startEncapsulation();
            out.write_octet_sequence(this.distinguishedName);
            out.endEncapsulation(handle);
      }

   }

   public boolean equals(Object o) {
      if (o instanceof IdentityToken) {
         IdentityToken other = (IdentityToken)o;
         if (this.identityType != other.identityType) {
            return false;
         } else {
            switch (this.identityType) {
               case 0:
               case 1:
                  return true;
               case 2:
                  return Arrays.equals(this.principalName, other.principalName);
               case 3:
               case 5:
               case 6:
               case 7:
               default:
                  return false;
               case 4:
                  return Arrays.equals(this.certChain, other.certChain);
               case 8:
                  return Arrays.equals(this.distinguishedName, other.distinguishedName);
            }
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public String toString() {
      String str = "IdentityToken (IdentityType = " + this.identityType;
      switch (this.identityType) {
         case 0:
            str = str + ", absent = " + this.absent;
            break;
         case 1:
            str = str + ", anonymous = " + this.anonymous;
            break;
         case 2:
            str = str + ", principal = " + Hex.dump(this.principalName);
            break;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            str = str + "Unsupported Identity Type.";
            break;
         case 4:
            str = str + ", certChain = " + Hex.dump(this.certChain);
            break;
         case 8:
            str = str + ", distinguished = " + Hex.dump(this.distinguishedName);
      }

      return str;
   }
}
