package org.apache.xmlbeans.impl.piccolo.io;

import java.io.CharConversionException;

public interface CharsetDecoder {
   int minBytesPerChar();

   int maxBytesPerChar();

   void decode(byte[] var1, int var2, int var3, char[] var4, int var5, int var6, int[] var7) throws CharConversionException;

   CharsetDecoder newCharsetDecoder();

   void reset();
}
