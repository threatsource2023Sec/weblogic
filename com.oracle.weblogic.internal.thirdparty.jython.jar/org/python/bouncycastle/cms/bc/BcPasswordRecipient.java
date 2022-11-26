package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.PasswordRecipient;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public abstract class BcPasswordRecipient implements PasswordRecipient {
   private final char[] password;
   private int schemeID = 1;

   BcPasswordRecipient(char[] var1) {
      this.password = var1;
   }

   public BcPasswordRecipient setPasswordConversionScheme(int var1) {
      this.schemeID = var1;
      return this;
   }

   protected KeyParameter extractSecretKey(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3, byte[] var4) throws CMSException {
      Wrapper var5 = EnvelopedDataHelper.createRFC3211Wrapper(var1.getAlgorithm());
      var5.init(false, new ParametersWithIV(new KeyParameter(var3), ASN1OctetString.getInstance(var1.getParameters()).getOctets()));

      try {
         return new KeyParameter(var5.unwrap(var4, 0, var4.length));
      } catch (InvalidCipherTextException var7) {
         throw new CMSException("unable to unwrap key: " + var7.getMessage(), var7);
      }
   }

   public byte[] calculateDerivedKey(int var1, AlgorithmIdentifier var2, int var3) throws CMSException {
      PBKDF2Params var4 = PBKDF2Params.getInstance(var2.getParameters());
      byte[] var5 = var1 == 0 ? PBEParametersGenerator.PKCS5PasswordToBytes(this.password) : PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.password);

      try {
         PKCS5S2ParametersGenerator var6 = new PKCS5S2ParametersGenerator(EnvelopedDataHelper.getPRF(var4.getPrf()));
         var6.init(var5, var4.getSalt(), var4.getIterationCount().intValue());
         return ((KeyParameter)var6.generateDerivedParameters(var3)).getKey();
      } catch (Exception var7) {
         throw new CMSException("exception creating derived key: " + var7.getMessage(), var7);
      }
   }

   public int getPasswordConversionScheme() {
      return this.schemeID;
   }

   public char[] getPassword() {
      return this.password;
   }
}
