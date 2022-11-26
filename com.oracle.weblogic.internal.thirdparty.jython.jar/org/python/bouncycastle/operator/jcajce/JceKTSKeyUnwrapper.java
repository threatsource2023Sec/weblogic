package org.python.bouncycastle.operator.jcajce;

import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.python.bouncycastle.asn1.cms.GenericHybridParameters;
import org.python.bouncycastle.asn1.cms.RsaKemParameters;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.util.DEROtherInfo;
import org.python.bouncycastle.jcajce.spec.KTSParameterSpec;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.AsymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.util.Arrays;

public class JceKTSKeyUnwrapper extends AsymmetricKeyUnwrapper {
   private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
   private Map extraMappings = new HashMap();
   private PrivateKey privKey;
   private byte[] partyUInfo;
   private byte[] partyVInfo;

   public JceKTSKeyUnwrapper(AlgorithmIdentifier var1, PrivateKey var2, byte[] var3, byte[] var4) {
      super(var1);
      this.privKey = var2;
      this.partyUInfo = Arrays.clone(var3);
      this.partyVInfo = Arrays.clone(var4);
   }

   public JceKTSKeyUnwrapper setProvider(Provider var1) {
      this.helper = new OperatorHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JceKTSKeyUnwrapper setProvider(String var1) {
      this.helper = new OperatorHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public GenericKey generateUnwrappedKey(AlgorithmIdentifier var1, byte[] var2) throws OperatorException {
      GenericHybridParameters var3 = GenericHybridParameters.getInstance(this.getAlgorithmIdentifier().getParameters());
      Cipher var4 = this.helper.createAsymmetricWrapper(this.getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
      String var5 = this.helper.getWrappingAlgorithmName(var3.getDem().getAlgorithm());
      RsaKemParameters var6 = RsaKemParameters.getInstance(var3.getKem().getParameters());
      int var7 = var6.getKeyLength().intValue() * 8;

      Key var10;
      try {
         DEROtherInfo var8 = (new DEROtherInfo.Builder(var3.getDem(), this.partyUInfo, this.partyVInfo)).build();
         KTSParameterSpec var9 = (new KTSParameterSpec.Builder(var5, var7, var8.getEncoded())).withKdfAlgorithm(var6.getKeyDerivationFunction()).build();
         var4.init(4, this.privKey, var9);
         var10 = var4.unwrap(var2, this.helper.getKeyAlgorithmName(var1.getAlgorithm()), 3);
      } catch (Exception var11) {
         throw new OperatorException("Unable to unwrap contents key: " + var11.getMessage(), var11);
      }

      return new JceGenericKey(var1, var10);
   }
}
