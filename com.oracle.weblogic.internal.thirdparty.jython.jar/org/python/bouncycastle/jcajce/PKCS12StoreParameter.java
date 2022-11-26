package org.python.bouncycastle.jcajce;

import java.io.OutputStream;
import java.security.KeyStore;

public class PKCS12StoreParameter implements KeyStore.LoadStoreParameter {
   private final OutputStream out;
   private final KeyStore.ProtectionParameter protectionParameter;
   private final boolean forDEREncoding;

   public PKCS12StoreParameter(OutputStream var1, char[] var2) {
      this(var1, var2, false);
   }

   public PKCS12StoreParameter(OutputStream var1, KeyStore.ProtectionParameter var2) {
      this(var1, var2, false);
   }

   public PKCS12StoreParameter(OutputStream var1, char[] var2, boolean var3) {
      this(var1, (KeyStore.ProtectionParameter)(new KeyStore.PasswordProtection(var2)), var3);
   }

   public PKCS12StoreParameter(OutputStream var1, KeyStore.ProtectionParameter var2, boolean var3) {
      this.out = var1;
      this.protectionParameter = var2;
      this.forDEREncoding = var3;
   }

   public OutputStream getOutputStream() {
      return this.out;
   }

   public KeyStore.ProtectionParameter getProtectionParameter() {
      return this.protectionParameter;
   }

   public boolean isForDEREncoding() {
      return this.forDEREncoding;
   }
}
