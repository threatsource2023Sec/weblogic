package com.bea.xbean.values;

public class XmlValueDisconnectedException extends RuntimeException {
   XmlValueDisconnectedException() {
   }

   XmlValueDisconnectedException(String message) {
      super(message);
   }

   XmlValueDisconnectedException(String message, Throwable cause) {
      super(message, cause);
   }
}
