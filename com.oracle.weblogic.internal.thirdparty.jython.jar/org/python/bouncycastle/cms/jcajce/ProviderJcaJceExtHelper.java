package org.python.bouncycastle.cms.jcajce;

import java.security.PrivateKey;
import java.security.Provider;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.SymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceKTSKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceSymmetricKeyUnwrapper;

class ProviderJcaJceExtHelper extends ProviderJcaJceHelper implements JcaJceExtHelper {
   public ProviderJcaJceExtHelper(Provider var1) {
      super(var1);
   }

   public JceAsymmetricKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier var1, PrivateKey var2) {
      return (new JceAsymmetricKeyUnwrapper(var1, var2)).setProvider(this.provider);
   }

   public JceKTSKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier var1, PrivateKey var2, byte[] var3, byte[] var4) {
      return (new JceKTSKeyUnwrapper(var1, var2, var3, var4)).setProvider(this.provider);
   }

   public SymmetricKeyUnwrapper createSymmetricUnwrapper(AlgorithmIdentifier var1, SecretKey var2) {
      return (new JceSymmetricKeyUnwrapper(var1, var2)).setProvider(this.provider);
   }
}
