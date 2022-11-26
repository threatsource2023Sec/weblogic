package org.python.bouncycastle.crypto.engines;

public class AESWrapEngine extends RFC3394WrapEngine {
   public AESWrapEngine() {
      super(new AESEngine());
   }

   public AESWrapEngine(boolean var1) {
      super(new AESEngine(), var1);
   }
}
