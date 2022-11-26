package com.bea.security.utils.wss;

public class WSSThumbprintException extends Exception {
   public WSSThumbprintException() {
   }

   public WSSThumbprintException(String message) {
      super(message);
   }

   WSSThumbprintException(String message, Throwable cause) {
      super(message, cause);
   }

   WSSThumbprintException(Throwable cause) {
      super(cause);
   }
}
