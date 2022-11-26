package weblogic.rjvm;

import java.rmi.UnmarshalException;

public final class PeerGoneException extends UnmarshalException {
   PeerGoneException(String message) {
      super(message);
   }

   PeerGoneException(String message, Exception cause) {
      super(message, cause);
   }
}
