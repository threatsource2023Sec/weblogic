package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public interface Encoder {
   void encode(ByteBuffer var1, CharBuffer var2) throws EncodingException;

   void finalize(CharBuffer var1) throws EncodingException;

   int outputSize(int var1);
}
