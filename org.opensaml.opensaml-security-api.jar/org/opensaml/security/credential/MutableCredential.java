package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public interface MutableCredential extends Credential {
   void setEntityId(String var1);

   void setUsageType(UsageType var1);

   void setPublicKey(PublicKey var1);

   void setPrivateKey(PrivateKey var1);

   void setSecretKey(SecretKey var1);
}
