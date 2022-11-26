package org.cryptacular.codec;

public interface Codec {
   Encoder getEncoder();

   Decoder getDecoder();

   Encoder newEncoder();

   Decoder newDecoder();
}
