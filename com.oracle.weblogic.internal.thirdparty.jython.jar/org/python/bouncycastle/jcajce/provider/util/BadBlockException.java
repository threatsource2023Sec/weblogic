package org.python.bouncycastle.jcajce.provider.util;

import javax.crypto.BadPaddingException;

public class BadBlockException extends BadPaddingException {
   private final Throwable cause;

   public BadBlockException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
