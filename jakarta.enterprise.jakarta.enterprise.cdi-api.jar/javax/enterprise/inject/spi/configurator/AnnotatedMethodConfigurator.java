package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.enterprise.inject.spi.AnnotatedMethod;

public interface AnnotatedMethodConfigurator {
   AnnotatedMethod getAnnotated();

   AnnotatedMethodConfigurator add(Annotation var1);

   AnnotatedMethodConfigurator remove(Predicate var1);

   default AnnotatedMethodConfigurator removeAll() {
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
