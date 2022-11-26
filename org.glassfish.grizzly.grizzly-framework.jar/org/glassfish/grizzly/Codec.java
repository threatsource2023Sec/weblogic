package org.glassfish.grizzly;

public interface Codec {
   Transformer getDecoder();

   Transformer getEncoder();
}
