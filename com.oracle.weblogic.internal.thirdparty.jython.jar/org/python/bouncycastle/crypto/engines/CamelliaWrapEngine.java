package org.python.bouncycastle.crypto.engines;

public class CamelliaWrapEngine extends RFC3394WrapEngine {
   public CamelliaWrapEngine() {
      super(new CamelliaEngine());
   }
}
