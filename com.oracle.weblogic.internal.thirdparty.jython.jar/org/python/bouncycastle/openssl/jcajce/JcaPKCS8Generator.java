package org.python.bouncycastle.openssl.jcajce;

import java.security.PrivateKey;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.openssl.PKCS8Generator;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.util.io.pem.PemGenerationException;

public class JcaPKCS8Generator extends PKCS8Generator {
   public JcaPKCS8Generator(PrivateKey var1, OutputEncryptor var2) throws PemGenerationException {
      super(PrivateKeyInfo.getInstance(var1.getEncoded()), var2);
   }
}
