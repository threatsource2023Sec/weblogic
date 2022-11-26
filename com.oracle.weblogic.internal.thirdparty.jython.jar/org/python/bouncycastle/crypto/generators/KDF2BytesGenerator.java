package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.Digest;

public class KDF2BytesGenerator extends BaseKDFBytesGenerator {
   public KDF2BytesGenerator(Digest var1) {
      super(1, var1);
   }
}
