package org.python.bouncycastle.eac.jcajce;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

class DefaultEACHelper implements EACHelper {
   public KeyFactory createKeyFactory(String var1) throws NoSuchAlgorithmException {
      return KeyFactory.getInstance(var1);
   }
}
