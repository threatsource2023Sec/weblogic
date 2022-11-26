package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;

public class NullEngine implements BlockCipher {
   private boolean initialised;
   protected static final int DEFAULT_BLOCK_SIZE = 1;
   private final int blockSize;

   public NullEngine() {
      this(1);
   }

   public NullEngine(int var1) {
      this.blockSize = var1;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.initialised = true;
   }

   public String getAlgorithmName() {
      return "Null";
   }

   public int getBlockSize() {
      return this.blockSize;
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      if (!this.initialised) {
         throw new IllegalStateException("Null engine not initialised");
      } else if (var2 + this.blockSize > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var4 + this.blockSize > var3.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         for(int var5 = 0; var5 < this.blockSize; ++var5) {
            var3[var4 + var5] = var1[var2 + var5];
         }

         return this.blockSize;
      }
   }

   public void reset() {
   }
}
