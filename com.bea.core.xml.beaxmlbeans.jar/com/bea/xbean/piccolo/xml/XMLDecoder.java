package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.io.CharsetDecoder;
import java.io.CharConversionException;

public interface XMLDecoder extends CharsetDecoder {
   void decodeXMLDecl(byte[] var1, int var2, int var3, char[] var4, int var5, int var6, int[] var7) throws CharConversionException;

   XMLDecoder newXMLDecoder();
}
