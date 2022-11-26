package javax.enterprise.inject.spi;

import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;

public interface ObserverMethod extends Prioritized {
   int DEFAULT_PRIORITY = 2500;

   Class getBeanClass();

   Type getObservedType();

   Set getObservedQualifiers();

   Reception getReception();

   TransactionPhase getTransactionPhase();

   default int getPriority() {
      return 2500;
   }

   default void notify(Object event) {
   }

   default void notify(EventContext eventContext) {
      this.notify(eventContext.getEvent());
   }

   default boolean isAsync() {
      return false;
   }
}
