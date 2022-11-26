package org.python.bouncycastle.crypto.engines;

public class ARIAWrapPadEngine extends RFC5649WrapEngine {
   public ARIAWrapPadEngine() {
      super(new ARIAEngine());
   }
}
