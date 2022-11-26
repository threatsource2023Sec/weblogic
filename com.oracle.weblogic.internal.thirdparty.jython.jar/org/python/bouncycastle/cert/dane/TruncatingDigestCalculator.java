package org.python.bouncycastle.cert.dane;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculator;

public class TruncatingDigestCalculator implements DigestCalculator {
   private final DigestCalculator baseCalculator;
   private final int length;

   public TruncatingDigestCalculator(DigestCalculator var1) {
      this(var1, 28);
   }

   public TruncatingDigestCalculator(DigestCalculator var1, int var2) {
      this.baseCalculator = var1;
      this.length = var2;
   }

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return this.baseCalculator.getAlgorithmIdentifier();
   }

   public OutputStream getOutputStream() {
      return this.baseCalculator.getOutputStream();
   }

   public byte[] getDigest() {
      byte[] var1 = new byte[this.length];
      byte[] var2 = this.baseCalculator.getDigest();
      System.arraycopy(var2, 0, var1, 0, var1.length);
      return var1;
   }
}
