package org.python.bouncycastle.cms;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.cert.selector.X509CertificateHolderSelector;

public class KeyTransRecipientId extends RecipientId {
   private X509CertificateHolderSelector baseSelector;

   private KeyTransRecipientId(X509CertificateHolderSelector var1) {
      super(0);
      this.baseSelector = var1;
   }

   public KeyTransRecipientId(byte[] var1) {
      this((X500Name)null, (BigInteger)null, var1);
   }

   public KeyTransRecipientId(X500Name var1, BigInteger var2) {
      this(var1, var2, (byte[])null);
   }

   public KeyTransRecipientId(X500Name var1, BigInteger var2, byte[] var3) {
      this(new X509CertificateHolderSelector(var1, var2, var3));
   }

   public X500Name getIssuer() {
      return this.baseSelector.getIssuer();
   }

   public BigInteger getSerialNumber() {
      return this.baseSelector.getSerialNumber();
   }

   public byte[] getSubjectKeyIdentifier() {
      return this.baseSelector.getSubjectKeyIdentifier();
   }

   public int hashCode() {
      return this.baseSelector.hashCode();
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof KeyTransRecipientId)) {
         return false;
      } else {
         KeyTransRecipientId var2 = (KeyTransRecipientId)var1;
         return this.baseSelector.equals(var2.baseSelector);
      }
   }

   public Object clone() {
      return new KeyTransRecipientId(this.baseSelector);
   }

   public boolean match(Object var1) {
      return var1 instanceof KeyTransRecipientInformation ? ((KeyTransRecipientInformation)var1).getRID().equals(this) : this.baseSelector.match(var1);
   }
}
