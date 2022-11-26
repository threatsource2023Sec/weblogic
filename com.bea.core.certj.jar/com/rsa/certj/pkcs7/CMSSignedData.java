package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.certj.CertJ;
import com.rsa.certj.spi.path.CertPathCtx;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class CMSSignedData extends SignedData {
   /** @deprecated */
   public CMSSignedData(String var1, CertJ var2, CertPathCtx var3) throws PKCS7Exception {
      super(var2, var3);
      this.oid = Pkcs7Util.a(var1);
      this.version = 3;
   }

   /** @deprecated */
   protected ContentInfo buildContentContainer(byte[] var1, int var2, int var3) throws PKCS7Exception {
      return new CMSData(this.oid);
   }

   /** @deprecated */
   protected byte[] initOctets(ASN1Container var1, ASN1Container var2) throws ASN_Exception {
      return this.initDataOctets(var1, var2);
   }

   /** @deprecated */
   public void setVersionNumber(int var1) {
      this.version = 3;
   }

   /** @deprecated */
   protected byte[] getOidType() {
      return this.oid;
   }

   /** @deprecated */
   protected void assignDetachedMessageContentInfoSeq() {
      int var1 = this.oid.length;
      this.contentEncoding = new byte[var1 + 4];
      this.contentEncoding[0] = 48;
      this.contentEncoding[1] = (byte)(var1 + 2);
      this.contentEncoding[2] = 6;
      this.contentEncoding[3] = (byte)var1;
      System.arraycopy(this.oid, 0, this.contentEncoding, 4, var1);
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CMSSignedData var1 = (CMSSignedData)super.clone();
      var1.oid = this.oid;
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof CMSSignedData)) {
         return false;
      } else {
         CMSSignedData var2 = (CMSSignedData)var1;
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
   public void clearSensitiveData() {
      super.clearSensitiveData();
      this.version = 3;
      this.digestIDs = new Vector();
      this.digestNames = new Vector();
      this.digests = new Vector();
      this.certs = new Vector();
      this.crls = new Vector();
      this.signers = new Vector();
   }
}
