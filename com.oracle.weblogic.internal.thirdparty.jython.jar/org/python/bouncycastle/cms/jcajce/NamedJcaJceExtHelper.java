package org.python.bouncycastle.cms.jcajce;

import java.security.PrivateKey;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.operator.SymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceKTSKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceSymmetricKeyUnwrapper;

class NamedJcaJceExtHelper extends NamedJcaJceHelper implements JcaJceExtHelper {
   public NamedJcaJceExtHelper(String var1) {
      super(var1);
   }

   public JceAsymmetricKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier var1, PrivateKey var2) {
      return (new JceAsymmetricKeyUnwrapper(var1, var2)).setProvider(this.providerName);
   }

   public JceKTSKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier var1, PrivateKey var2, byte[] var3, byte[] var4) {
      return (new JceKTSKeyUnwrapper(var1, var2, var3, var4)).setProvider(this.providerName);
   }

   public SymmetricKeyUnwrapper createSymmetricUnwrapper(AlgorithmIdentifier var1, SecretKey var2) {
      return (new JceSymmetricKeyUnwrapper(var1, var2)).setProvider(this.providerName);
   }
}
