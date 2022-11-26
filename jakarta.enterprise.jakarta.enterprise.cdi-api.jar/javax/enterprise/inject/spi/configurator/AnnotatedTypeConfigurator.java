package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.enterprise.inject.spi.AnnotatedType;

public interface AnnotatedTypeConfigurator {
   AnnotatedType getAnnotated();

   AnnotatedTypeConfigurator add(Annotation var1);

   AnnotatedTypeConfigurator remove(Predicate var1);

   default AnnotatedTypeConfigurator removeAll() {
      return this.remove((a) -> {
         return true;
      });
   }

   Set methods();

   default Stream filterMethods(Predicate predicate) {
      return this.methods().stream().filter((c) -> {
         return predicate.test(c.getAnnotated());
      });
   }

   Set fields();

   default Stream filterFields(Predicate predicate) {
      return this.fields().stream().filter((f) -> {
         return predicate.test(f.getAnnotated());
      });
   }

   Set constructors();

   default Stream filterConstructors(Predicate predicate) {
      return this.constructors().stream().filter((c) -> {
         return predicate.test(c.getAnnotated());
      });
   }
}
