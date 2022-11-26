package org.python.bouncycastle.operator.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;

public class BcDigestCalculatorProvider implements DigestCalculatorProvider {
   private BcDigestProvider digestProvider;

   public BcDigestCalculatorProvider() {
      this.digestProvider = BcDefaultDigestProvider.INSTANCE;
   }

   public DigestCalculator get(final AlgorithmIdentifier var1) throws OperatorCreationException {
      ExtendedDigest var2 = this.digestProvider.get(var1);
      final DigestOutputStream var3 = new DigestOutputStream(var2);
      return new DigestCalculator() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var1;
         }

         public OutputStream getOutputStream() {
            return var3;
         }

         public byte[] getDigest() {
            return var3.getDigest();
         }
      };
   }

   private class DigestOutputStream extends OutputStream {
      private Digest dig;

      DigestOutputStream(Digest var2) {
         this.dig = var2;
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.dig.update(var1, var2, var3);
      }

      public void write(byte[] var1) throws IOException {
         this.dig.update(var1, 0, var1.length);
      }

      public void write(int var1) throws IOException {
         this.dig.update((byte)var1);
      }

      byte[] getDigest() {
         byte[] var1 = new byte[this.dig.getDigestSize()];
         this.dig.doFinal(var1, 0);
         return var1;
      }
   }
}
