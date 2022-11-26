package weblogic.iiop;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.ValueHandler;

class ValueHandlerHolder {
   private static final ValueHandler valueHandler = (ValueHandler)AccessController.doPrivileged(new ValueHandlerCreation());

   static ValueHandler getValueHandler() {
      return valueHandler;
   }

   private static class ValueHandlerCreation implements PrivilegedAction {
      private ValueHandlerCreation() {
      }

      public ValueHandler run() {
         return Util.createValueHandler();
      }

      // $FF: synthetic method
      ValueHandlerCreation(Object x0) {
         this();
      }
   }
}
