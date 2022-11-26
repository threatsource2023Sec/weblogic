package weblogic.jms.backend;

import weblogic.messaging.kernel.UOWCallback;
import weblogic.messaging.kernel.UOWCallbackCaller;
import weblogic.messaging.kernel.UOWCallbackFactory;

public class BEUOWCallbackFactory implements UOWCallbackFactory {
   public UOWCallback create(UOWCallbackCaller caller, String name) {
      return new BEUOWCallback(caller, name);
   }
}
