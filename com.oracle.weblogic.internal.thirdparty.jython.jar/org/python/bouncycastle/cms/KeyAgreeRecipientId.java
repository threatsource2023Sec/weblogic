package org.python.bouncycastle.cms;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.cert.selector.X509CertificateHolderSelector;

public class KeyAgreeRecipientId extends RecipientId {
   private X509CertificateHolderSelector baseSelector;

   private KeyAgreeRecipientId(X509CertificateHolderSelector var1) {
      super(2);
      this.baseSelector = var1;
   }

   public KeyAgreeRecipientId(byte[] var1) {
      this((X500Name)null, (BigInteger)null, var1);
   }

   public KeyAgreeRecipientId(X500Name var1, BigInteger var2) {
      this(var1, var2, (byte[])null);
   }

   public KeyAgreeRecipientId(X500Name var1, BigInteger var2, byte[] var3) {
      this(new X509CertificateHolderSelector(var1, var2, var3));
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
      if (!(var1 instanceof KeyAgreeRecipientId)) {
         return false;
      } else {
         KeyAgreeRecipientId var2 = (KeyAgreeRecipientId)var1;
         return this.baseSelector.equals(var2.baseSelector);
      }
   }

   public Object clone() {
      return new KeyAgreeRecipientId(this.baseSelector);
   }

   public boolean match(Object var1) {
      return var1 instanceof KeyAgreeRecipientInformation ? ((KeyAgreeRecipientInformation)var1).getRID().equals(this) : this.baseSelector.match(var1);
   }
}
