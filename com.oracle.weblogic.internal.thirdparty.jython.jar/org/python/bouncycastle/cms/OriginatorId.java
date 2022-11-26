package org.python.bouncycastle.cms;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Selector;

class OriginatorId implements Selector {
   private byte[] subjectKeyId;
   private X500Name issuer;
   private BigInteger serialNumber;

   public OriginatorId(byte[] var1) {
      this.setSubjectKeyID(var1);
   }

   private void setSubjectKeyID(byte[] var1) {
      this.subjectKeyId = var1;
   }

   public OriginatorId(X500Name var1, BigInteger var2) {
      this.setIssuerAndSerial(var1, var2);
   }

   private void setIssuerAndSerial(X500Name var1, BigInteger var2) {
      this.issuer = var1;
      this.serialNumber = var2;
   }

   public OriginatorId(X500Name var1, BigInteger var2, byte[] var3) {
      this.setIssuerAndSerial(var1, var2);
      this.setSubjectKeyID(var3);
   }

   public X500Name getIssuer() {
      return this.issuer;
   }

   public Object clone() {
      return new OriginatorId(this.issuer, this.serialNumber, this.subjectKeyId);
   }

   public int hashCode() {
      int var1 = Arrays.hashCode(this.subjectKeyId);
      if (this.serialNumber != null) {
         var1 ^= this.serialNumber.hashCode();
      }

      if (this.issuer != null) {
         var1 ^= this.issuer.hashCode();
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof OriginatorId)) {
         return false;
      } else {
         OriginatorId var2 = (OriginatorId)var1;
         return Arrays.areEqual(this.subjectKeyId, var2.subjectKeyId) && this.equalsObj(this.serialNumber, var2.serialNumber) && this.equalsObj(this.issuer, var2.issuer);
      }
   }

   private boolean equalsObj(Object var1, Object var2) {
      return var1 != null ? var1.equals(var2) : var2 == null;
   }

   public boolean match(Object var1) {
      return false;
   }
}
