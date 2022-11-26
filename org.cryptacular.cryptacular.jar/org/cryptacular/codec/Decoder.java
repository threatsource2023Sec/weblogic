package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public interface Decoder {
   void decode(CharBuffer var1, ByteBuffer var2) throws EncodingException;

   void finalize(ByteBuffer var1) throws EncodingException;

   int outputSize(int var1);
}
