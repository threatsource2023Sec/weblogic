package org.python.bouncycastle.cms.jcajce;

import java.security.PrivateKey;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.operator.SymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.jcajce.JceKTSKeyUnwrapper;

interface JcaJceExtHelper extends JcaJceHelper {
   JceAsymmetricKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier var1, PrivateKey var2);

   JceKTSKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier var1, PrivateKey var2, byte[] var3, byte[] var4);

   SymmetricKeyUnwrapper createSymmetricUnwrapper(AlgorithmIdentifier var1, SecretKey var2);
}
