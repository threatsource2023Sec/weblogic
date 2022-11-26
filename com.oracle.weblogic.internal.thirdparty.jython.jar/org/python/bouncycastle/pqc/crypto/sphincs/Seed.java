package org.python.bouncycastle.pqc.crypto.sphincs;

import org.python.bouncycastle.crypto.engines.ChaChaEngine;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.util.Pack;

class Seed {
   static void get_seed(HashFunctions var0, byte[] var1, int var2, byte[] var3, Tree.leafaddr var4) {
      byte[] var5 = new byte[40];

      for(int var6 = 0; var6 < 32; ++var6) {
         var5[var6] = var3[var6];
      }

      long var7 = (long)var4.level;
      var7 |= var4.subtree << 4;
      var7 |= var4.subleaf << 59;
      Pack.longToLittleEndian(var7, var5, 32);
      var0.varlen_hash(var1, var2, var5, var5.length);
   }

   static void prg(byte[] var0, int var1, long var2, byte[] var4, int var5) {
      byte[] var6 = new byte[8];
      ChaChaEngine var7 = new ChaChaEngine(12);
      var7.init(true, new ParametersWithIV(new KeyParameter(var4, var5, 32), var6));
      var7.processBytes(var0, var1, (int)var2, var0, var1);
   }
}
