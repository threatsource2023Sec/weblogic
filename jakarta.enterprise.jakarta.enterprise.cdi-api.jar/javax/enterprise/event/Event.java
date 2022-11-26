package javax.enterprise.event;

import java.lang.annotation.Annotation;
import java.util.concurrent.CompletionStage;
import javax.enterprise.util.TypeLiteral;

public interface Event {
   void fire(Object var1);

   CompletionStage fireAsync(Object var1);

   CompletionStage fireAsync(Object var1, NotificationOptions var2);

   Event select(Annotation... var1);

   Event select(Class var1, Annotation... var2);

   Event select(TypeLiteral var1, Annotation... var2);
}
