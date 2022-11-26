package javax.enterprise.inject;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Provider;

public interface Instance extends Iterable, Provider {
   Instance select(Annotation... var1);

   Instance select(Class var1, Annotation... var2);

   Instance select(TypeLiteral var1, Annotation... var2);

   default Stream stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   boolean isUnsatisfied();

   boolean isAmbiguous();

   default boolean isResolvable() {
      return !this.isUnsatisfied() && !this.isAmbiguous();
   }

   void destroy(Object var1);
}
