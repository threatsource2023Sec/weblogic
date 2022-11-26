package org.python.bouncycastle.jcajce.provider.config;

import java.io.OutputStream;
import java.security.KeyStore;

/** @deprecated */
public class PKCS12StoreParameter extends org.python.bouncycastle.jcajce.PKCS12StoreParameter {
   public PKCS12StoreParameter(OutputStream var1, char[] var2) {
      super(var1, var2, false);
   }

   public PKCS12StoreParameter(OutputStream var1, KeyStore.ProtectionParameter var2) {
      super(var1, var2, false);
   }

   public PKCS12StoreParameter(OutputStream var1, char[] var2, boolean var3) {
      super(var1, (KeyStore.ProtectionParameter)(new KeyStore.PasswordProtection(var2)), var3);
   }

   public PKCS12StoreParameter(OutputStream var1, KeyStore.ProtectionParameter var2, boolean var3) {
      super(var1, var2, var3);
   }
}
