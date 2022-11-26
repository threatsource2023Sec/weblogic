package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.CharConversionException;
import org.apache.xmlbeans.impl.piccolo.io.CharsetDecoder;

public interface XMLDecoder extends CharsetDecoder {
   void decodeXMLDecl(byte[] var1, int var2, int var3, char[] var4, int var5, int var6, int[] var7) throws CharConversionException;

   XMLDecoder newXMLDecoder();
}
