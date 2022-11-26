package org.python.bouncycastle.jcajce.provider.asymmetric.util;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.jce.spec.IESParameterSpec;

public class IESUtil {
   public static IESParameterSpec guessParameterSpec(BufferedBlockCipher var0, byte[] var1) {
      if (var0 == null) {
         return new IESParameterSpec((byte[])null, (byte[])null, 128);
      } else {
         BlockCipher var2 = var0.getUnderlyingCipher();
         if (!var2.getAlgorithmName().equals("DES") && !var2.getAlgorithmName().equals("RC2") && !var2.getAlgorithmName().equals("RC5-32") && !var2.getAlgorithmName().equals("RC5-64")) {
            if (var2.getAlgorithmName().equals("SKIPJACK")) {
               return new IESParameterSpec((byte[])null, (byte[])null, 80, 80, var1);
            } else {
               return var2.getAlgorithmName().equals("GOST28147") ? new IESParameterSpec((byte[])null, (byte[])null, 256, 256, var1) : new IESParameterSpec((byte[])null, (byte[])null, 128, 128, var1);
            }
         } else {
            return new IESParameterSpec((byte[])null, (byte[])null, 64, 64, var1);
         }
      }
   }
}
