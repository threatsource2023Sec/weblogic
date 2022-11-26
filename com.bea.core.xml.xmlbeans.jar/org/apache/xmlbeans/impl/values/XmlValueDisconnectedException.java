package org.apache.xmlbeans.impl.values;

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
