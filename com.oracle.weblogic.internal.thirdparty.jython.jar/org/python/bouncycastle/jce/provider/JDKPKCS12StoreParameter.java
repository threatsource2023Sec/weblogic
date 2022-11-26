package org.python.bouncycastle.jce.provider;

import java.io.OutputStream;
import java.security.KeyStore;

/** @deprecated */
public class JDKPKCS12StoreParameter implements KeyStore.LoadStoreParameter {
   private OutputStream outputStream;
   private KeyStore.ProtectionParameter protectionParameter;
   private boolean useDEREncoding;

   public OutputStream getOutputStream() {
      return this.outputStream;
   }

   public KeyStore.ProtectionParameter getProtectionParameter() {
      return this.protectionParameter;
   }

   public boolean isUseDEREncoding() {
      return this.useDEREncoding;
   }

   public void setOutputStream(OutputStream var1) {
      this.outputStream = var1;
   }

   public void setPassword(char[] var1) {
      this.protectionParameter = new KeyStore.PasswordProtection(var1);
   }

   public void setProtectionParameter(KeyStore.ProtectionParameter var1) {
      this.protectionParameter = var1;
   }

   public void setUseDEREncoding(boolean var1) {
      this.useDEREncoding = var1;
   }
}
