package org.python.bouncycastle.crypto.modes;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.StreamBlockCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.ParametersWithSBox;

public class GCFBBlockCipher extends StreamBlockCipher {
   private static final byte[] C = new byte[]{105, 0, 114, 34, 100, -55, 4, 35, -115, 58, -37, -106, 70, -23, 42, -60, 24, -2, -84, -108, 0, -19, 7, 18, -64, -122, -36, -62, -17, 76, -87, 43};
   private final CFBBlockCipher cfbEngine;
   private KeyParameter key;
   private long counter = 0L;
   private boolean forEncryption;

   public GCFBBlockCipher(BlockCipher var1) {
      super(var1);
      this.cfbEngine = new CFBBlockCipher(var1, var1.getBlockSize() * 8);
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.counter = 0L;
      this.cfbEngine.init(var1, var2);
      this.forEncryption = var1;
      if (var2 instanceof ParametersWithIV) {
         var2 = ((ParametersWithIV)var2).getParameters();
      }

      if (var2 instanceof ParametersWithRandom) {
         var2 = ((ParametersWithRandom)var2).getParameters();
      }

      if (var2 instanceof ParametersWithSBox) {
         var2 = ((ParametersWithSBox)var2).getParameters();
      }

      this.key = (KeyParameter)var2;
   }

   public String getAlgorithmName() {
      String var1 = this.cfbEngine.getAlgorithmName();
      return var1.substring(0, var1.indexOf(47)) + "/G" + var1.substring(var1.indexOf(47) + 1);
   }

   public int getBlockSize() {
      return this.cfbEngine.getBlockSize();
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      this.processBytes(var1, var2, this.cfbEngine.getBlockSize(), var3, var4);
      return this.cfbEngine.getBlockSize();
   }

   protected byte calculateByte(byte var1) {
      if (this.counter > 0L && this.counter % 1024L == 0L) {
         BlockCipher var2 = this.cfbEngine.getUnderlyingCipher();
         var2.init(false, this.key);
         byte[] var3 = new byte[32];
         var2.processBlock(C, 0, var3, 0);
         var2.processBlock(C, 8, var3, 8);
         var2.processBlock(C, 16, var3, 16);
         var2.processBlock(C, 24, var3, 24);
         this.key = new KeyParameter(var3);
         var2.init(true, this.key);
         byte[] var4 = this.cfbEngine.getCurrentIV();
         var2.processBlock(var4, 0, var4, 0);
         this.cfbEngine.init(this.forEncryption, new ParametersWithIV(this.key, var4));
      }

      ++this.counter;
      return this.cfbEngine.calculateByte(var1);
   }

   public void reset() {
      this.counter = 0L;
      this.cfbEngine.reset();
   }
}
