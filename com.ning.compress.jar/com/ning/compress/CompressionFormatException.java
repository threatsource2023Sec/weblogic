package com.ning.compress;

import java.io.IOException;

public class CompressionFormatException extends IOException {
   private static final long serialVersionUID = 1L;

   protected CompressionFormatException(String message) {
      super(message);
   }

   protected CompressionFormatException(Throwable t) {
      this.initCause(t);
   }

   protected CompressionFormatException(String message, Throwable t) {
      super(message);
      this.initCause(t);
   }
}
