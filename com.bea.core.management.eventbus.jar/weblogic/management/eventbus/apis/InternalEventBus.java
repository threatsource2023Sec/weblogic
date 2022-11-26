package weblogic.management.eventbus.apis;

import java.util.concurrent.Future;

public interface InternalEventBus {
   Future send(InternalEvent var1);

   void registerListener(InternalEventListener var1, InternalEventFilter var2);

   void registerListener(InternalEventListener var1);

   void unregisterListener(InternalEventListener var1, InternalEventFilter var2);

   void unregisterListener(InternalEventListener var1);
}
