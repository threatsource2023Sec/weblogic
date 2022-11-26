package org.python.bouncycastle.jcajce;

import javax.crypto.interfaces.PBEKey;
import org.python.bouncycastle.crypto.CharToByteConverter;
import org.python.bouncycastle.util.Arrays;

public class PBKDF2KeyWithParameters extends PBKDF2Key implements PBEKey {
   private final byte[] salt;
   private final int iterationCount;

   public PBKDF2KeyWithParameters(char[] var1, CharToByteConverter var2, byte[] var3, int var4) {
      super(var1, var2);
      this.salt = Arrays.clone(var3);
      this.iterationCount = var4;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public int getIterationCount() {
      return this.iterationCount;
   }
}
