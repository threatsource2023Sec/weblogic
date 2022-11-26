package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.digests.SkeinEngine;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.SkeinParameters;

public class SkeinMac implements Mac {
   public static final int SKEIN_256 = 256;
   public static final int SKEIN_512 = 512;
   public static final int SKEIN_1024 = 1024;
   private SkeinEngine engine;

   public SkeinMac(int var1, int var2) {
      this.engine = new SkeinEngine(var1, var2);
   }

   public SkeinMac(SkeinMac var1) {
      this.engine = new SkeinEngine(var1.engine);
   }

   public String getAlgorithmName() {
      return "Skein-MAC-" + this.engine.getBlockSize() * 8 + "-" + this.engine.getOutputSize() * 8;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      SkeinParameters var2;
      if (var1 instanceof SkeinParameters) {
         var2 = (SkeinParameters)var1;
      } else {
         if (!(var1 instanceof KeyParameter)) {
            throw new IllegalArgumentException("Invalid parameter passed to Skein MAC init - " + var1.getClass().getName());
         }

         var2 = (new SkeinParameters.Builder()).setKey(((KeyParameter)var1).getKey()).build();
      }

      if (var2.getKey() == null) {
         throw new IllegalArgumentException("Skein MAC requires a key parameter.");
      } else {
         this.engine.init(var2);
      }
   }

   public int getMacSize() {
      return this.engine.getOutputSize();
   }

   public void reset() {
      this.engine.reset();
   }

   public void update(byte var1) {
      this.engine.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.engine.update(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      return this.engine.doFinal(var1, var2);
   }
}
