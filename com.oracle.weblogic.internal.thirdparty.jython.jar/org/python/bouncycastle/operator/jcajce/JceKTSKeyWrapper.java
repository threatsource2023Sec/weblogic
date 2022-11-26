package org.python.bouncycastle.operator.jcajce;

import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.crypto.Cipher;
import org.python.bouncycastle.asn1.cms.GenericHybridParameters;
import org.python.bouncycastle.asn1.cms.RsaKemParameters;
import org.python.bouncycastle.asn1.iso.ISOIECObjectIdentifiers;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.util.DEROtherInfo;
import org.python.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.AsymmetricKeyWrapper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.util.Arrays;

public class JceKTSKeyWrapper extends AsymmetricKeyWrapper {
   private final String symmetricWrappingAlg;
   private final int keySizeInBits;
   private final byte[] partyUInfo;
   private final byte[] partyVInfo;
   private OperatorHelper helper;
   private PublicKey publicKey;
   private SecureRandom random;

   public JceKTSKeyWrapper(PublicKey var1, String var2, int var3, byte[] var4, byte[] var5) {
      super(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_rsa_KEM, new GenericHybridParameters(new AlgorithmIdentifier(ISOIECObjectIdentifiers.id_kem_rsa, new RsaKemParameters(new AlgorithmIdentifier(X9ObjectIdentifiers.id_kdf_kdf3, new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256)), (var3 + 7) / 8)), JceSymmetricKeyWrapper.determineKeyEncAlg(var2, var3))));
      this.helper = new OperatorHelper(new DefaultJcaJceHelper());
      this.publicKey = var1;
      this.symmetricWrappingAlg = var2;
      this.keySizeInBits = var3;
      this.partyUInfo = Arrays.clone(var4);
      this.partyVInfo = Arrays.clone(var5);
   }

   public JceKTSKeyWrapper(X509Certificate var1, String var2, int var3, byte[] var4, byte[] var5) {
      this(var1.getPublicKey(), var2, var3, var4, var5);
   }

   public JceKTSKeyWrapper setProvider(Provider var1) {
      this.helper = new OperatorHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JceKTSKeyWrapper setProvider(String var1) {
      this.helper = new OperatorHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public JceKTSKeyWrapper setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public byte[] generateWrappedKey(GenericKey var1) throws OperatorException {
      Cipher var2 = this.helper.createAsymmetricWrapper(this.getAlgorithmIdentifier().getAlgorithm(), new HashMap());

      try {
         DEROtherInfo var3 = (new DEROtherInfo.Builder(JceSymmetricKeyWrapper.determineKeyEncAlg(this.symmetricWrappingAlg, this.keySizeInBits), this.partyUInfo, this.partyVInfo)).build();
         KTSParameterSpec var4 = (new KTSParameterSpec.Builder(this.symmetricWrappingAlg, this.keySizeInBits, var3.getEncoded())).build();
         var2.init(3, this.publicKey, var4, this.random);
         return var2.wrap(OperatorUtils.getJceKey(var1));
      } catch (Exception var5) {
         throw new OperatorException("Unable to wrap contents key: " + var5.getMessage(), var5);
      }
   }
}
