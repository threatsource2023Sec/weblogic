package org.python.bouncycastle.jcajce.util;

import java.security.Provider;
import java.security.Security;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;

public class BCJcaJceHelper extends ProviderJcaJceHelper {
   private static volatile Provider bcProvider;

   private static Provider getBouncyCastleProvider() {
      if (Security.getProvider("BC") != null) {
         return Security.getProvider("BC");
      } else if (bcProvider != null) {
         return bcProvider;
      } else {
         bcProvider = new BouncyCastleProvider();
         return bcProvider;
      }
   }

   public BCJcaJceHelper() {
      super(getBouncyCastleProvider());
   }
}
