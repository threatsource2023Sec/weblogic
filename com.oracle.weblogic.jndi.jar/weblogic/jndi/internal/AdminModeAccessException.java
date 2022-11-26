package weblogic.jndi.internal;

import javax.naming.NameNotFoundException;

public final class AdminModeAccessException extends NameNotFoundException {
   AdminModeAccessException(String message) {
      super(message);
   }
}
