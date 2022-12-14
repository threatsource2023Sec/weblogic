package org.python.netty.handler.codec.compression;

import org.python.netty.handler.codec.DecoderException;

public class DecompressionException extends DecoderException {
   private static final long serialVersionUID = 3546272712208105199L;

   public DecompressionException() {
   }

   public DecompressionException(String message, Throwable cause) {
      super(message, cause);
   }

   public DecompressionException(String message) {
      super(message);
   }

   public DecompressionException(Throwable cause) {
      super(cause);
   }
}
