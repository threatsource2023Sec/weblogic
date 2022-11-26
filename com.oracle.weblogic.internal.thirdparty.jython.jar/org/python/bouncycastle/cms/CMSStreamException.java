package org.python.bouncycastle.cms;

import java.io.IOException;

public class CMSStreamException extends IOException {
   private final Throwable underlying;

   CMSStreamException(String var1) {
      super(var1);
      this.underlying = null;
   }

   CMSStreamException(String var1, Throwable var2) {
      super(var1);
      this.underlying = var2;
   }

   public Throwable getCause() {
      return this.underlying;
   }
}
