package weblogic.rmi.extensions;

import java.rmi.UnmarshalException;

public final class DisconnectMonitorException extends UnmarshalException {
   public DisconnectMonitorException(String message) {
      super(message);
   }

   public DisconnectMonitorException(String message, Exception ex) {
      super(message, ex);
   }
}
