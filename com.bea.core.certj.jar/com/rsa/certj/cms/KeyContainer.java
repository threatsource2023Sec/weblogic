package com.rsa.certj.cms;

import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;

/** @deprecated */
public final class KeyContainer {
   private JSAFE_PrivateKey a;
   private JSAFE_PublicKey b;
   private char[] c;
   private JSAFE_SecretKey d;

   /** @deprecated */
   public KeyContainer(JSAFE_PrivateKey var1) {
      this.a = var1;
   }

   /** @deprecated */
   public KeyContainer(JSAFE_PrivateKey var1, JSAFE_PublicKey var2) {
      this.a = var1;
      this.b = var2;
   }

   /** @deprecated */
   public KeyContainer(char[] var1) {
      this.c = var1 == null ? null : (char[])var1.clone();
   }

   /** @deprecated */
   public KeyContainer(JSAFE_SecretKey var1) {
      this.d = var1;
   }

   /** @deprecated */
   public JSAFE_PrivateKey getPrivateKey() {
      return this.a;
   }

   /** @deprecated */
   public JSAFE_PublicKey getPublicKey() {
      return this.b;
   }

   /** @deprecated */
   public char[] getPassword() {
      return this.c == null ? null : (char[])this.c.clone();
   }

   /** @deprecated */
   public JSAFE_SecretKey getSecretKey() {
      return this.d;
   }
}
