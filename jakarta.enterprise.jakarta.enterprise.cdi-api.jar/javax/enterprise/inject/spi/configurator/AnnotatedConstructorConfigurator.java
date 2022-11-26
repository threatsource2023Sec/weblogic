package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.enterprise.inject.spi.AnnotatedConstructor;

public interface AnnotatedConstructorConfigurator {
   AnnotatedConstructor getAnnotated();

   AnnotatedConstructorConfigurator add(Annotation var1);

   AnnotatedConstructorConfigurator remove(Predicate var1);

   default AnnotatedConstructorConfigurator removeAll() {
      return this.remove((a) -> {
         return true;
      });
   }

   List params();

   default Stream filterParams(Predicate predicate) {
      return this.params().stream().filter((p) -> {
         return predicate.test(p.getAnnotated());
      });
   }
}
