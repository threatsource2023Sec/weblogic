package org.python.bouncycastle.cms.bc;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.PasswordRecipientInfoGenerator;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.operator.GenericKey;

public class BcPasswordRecipientInfoGenerator extends PasswordRecipientInfoGenerator {
   public BcPasswordRecipientInfoGenerator(ASN1ObjectIdentifier var1, char[] var2) {
      super(var1, var2);
   }

   protected byte[] calculateDerivedKey(int var1, AlgorithmIdentifier var2, int var3) throws CMSException {
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

   public byte[] generateEncryptedBytes(AlgorithmIdentifier var1, byte[] var2, GenericKey var3) throws CMSException {
      byte[] var4 = ((KeyParameter)CMSUtils.getBcKey(var3)).getKey();
      Wrapper var5 = EnvelopedDataHelper.createRFC3211Wrapper(var1.getAlgorithm());
      var5.init(true, new ParametersWithIV(new KeyParameter(var2), ASN1OctetString.getInstance(var1.getParameters()).getOctets()));
      return var5.wrap(var4, 0, var4.length);
   }
}
