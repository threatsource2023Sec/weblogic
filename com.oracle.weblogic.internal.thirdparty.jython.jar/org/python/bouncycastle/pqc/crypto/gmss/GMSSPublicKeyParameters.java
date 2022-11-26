package org.python.bouncycastle.pqc.crypto.gmss;

public class GMSSPublicKeyParameters extends GMSSKeyParameters {
   private byte[] gmssPublicKey;

   public GMSSPublicKeyParameters(byte[] var1, GMSSParameters var2) {
      super(false, var2);
      this.gmssPublicKey = var1;
   }

   public byte[] getPublicKey() {
      return this.gmssPublicKey;
   }
}
