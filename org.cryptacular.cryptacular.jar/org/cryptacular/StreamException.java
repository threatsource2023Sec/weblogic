package org.cryptacular;

import java.io.IOException;

public class StreamException extends RuntimeException {
   public StreamException(String message) {
      super(message);
   }

   public StreamException(IOException cause) {
      super("IO error", cause);
   }
}
