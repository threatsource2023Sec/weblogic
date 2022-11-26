package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.OIDContainer;
import java.util.Arrays;

/** @deprecated */
public class CMSData extends Data {
   /** @deprecated */
   public CMSData(String var1) throws PKCS7Exception {
      this.oid = Pkcs7Util.a(var1);
   }

   /** @deprecated */
   public CMSData(byte[] var1) throws PKCS7Exception {
      this.oid = var1;
   }

   /** @deprecated */
   public byte[] getOID() {
      return this.oid;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!super.equals(var1)) {
         return false;
      } else {
         CMSData var2 = (CMSData)var1;
         if (this.oid.length != var2.oid.length) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.oid.length; ++var3) {
               if (this.oid[var3] != var2.oid[var3]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.oid);
      return var2;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CMSData var1 = (CMSData)super.clone();
      var1.oid = this.oid;
      return var1;
   }

   /** @deprecated */
   protected OIDContainer buildOIDContainer() throws ASN_Exception {
      return new OIDContainer(16777216, true, 0, this.oid, 0, this.oid.length);
   }
}
