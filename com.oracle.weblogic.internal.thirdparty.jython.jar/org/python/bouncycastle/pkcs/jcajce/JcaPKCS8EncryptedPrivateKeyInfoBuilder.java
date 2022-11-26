package org.python.bouncycastle.pkcs.jcajce;

import java.security.PrivateKey;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfoBuilder;

public class JcaPKCS8EncryptedPrivateKeyInfoBuilder extends PKCS8EncryptedPrivateKeyInfoBuilder {
   public JcaPKCS8EncryptedPrivateKeyInfoBuilder(PrivateKey var1) {
      super(PrivateKeyInfo.getInstance(var1.getEncoded()));
   }
}
