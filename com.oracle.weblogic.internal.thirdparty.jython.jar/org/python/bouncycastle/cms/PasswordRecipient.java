package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public interface PasswordRecipient extends Recipient {
   int PKCS5_SCHEME2 = 0;
   int PKCS5_SCHEME2_UTF8 = 1;

   byte[] calculateDerivedKey(int var1, AlgorithmIdentifier var2, int var3) throws CMSException;

   RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3, byte[] var4) throws CMSException;

   int getPasswordConversionScheme();

   char[] getPassword();

   public static final class PRF {
      public static final PRF HMacSHA1;
      public static final PRF HMacSHA224;
      public static final PRF HMacSHA256;
      public static final PRF HMacSHA384;
      public static final PRF HMacSHA512;
      private final String hmac;
      final AlgorithmIdentifier prfAlgID;

      private PRF(String var1, AlgorithmIdentifier var2) {
         this.hmac = var1;
         this.prfAlgID = var2;
      }

      public String getName() {
         return this.hmac;
      }

      public AlgorithmIdentifier getAlgorithmID() {
         return this.prfAlgID;
      }

      static {
         HMacSHA1 = new PRF("HMacSHA1", new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA1, DERNull.INSTANCE));
         HMacSHA224 = new PRF("HMacSHA224", new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA224, DERNull.INSTANCE));
         HMacSHA256 = new PRF("HMacSHA256", new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256, DERNull.INSTANCE));
         HMacSHA384 = new PRF("HMacSHA384", new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA384, DERNull.INSTANCE));
         HMacSHA512 = new PRF("HMacSHA512", new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA512, DERNull.INSTANCE));
      }
   }
}
