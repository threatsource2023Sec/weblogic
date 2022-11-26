package org.python.bouncycastle.jcajce;

import org.python.bouncycastle.crypto.CharToByteConverter;
import org.python.bouncycastle.util.Arrays;

public class PBKDF2Key implements PBKDFKey {
   private final char[] password;
   private final CharToByteConverter converter;

   public PBKDF2Key(char[] var1, CharToByteConverter var2) {
      this.password = Arrays.clone(var1);
      this.converter = var2;
   }

   public char[] getPassword() {
      return this.password;
   }

   public String getAlgorithm() {
      return "PBKDF2";
   }

   public String getFormat() {
      return this.converter.getType();
   }

   public byte[] getEncoded() {
      return this.converter.convert(this.password);
   }
}
