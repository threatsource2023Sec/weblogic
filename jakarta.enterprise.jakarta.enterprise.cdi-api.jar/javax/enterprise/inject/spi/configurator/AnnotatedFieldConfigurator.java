package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;
import javax.enterprise.inject.spi.AnnotatedField;

public interface AnnotatedFieldConfigurator {
   AnnotatedField getAnnotated();

   AnnotatedFieldConfigurator add(Annotation var1);

   AnnotatedFieldConfigurator remove(Predicate var1);

   default AnnotatedFieldConfigurator removeAll() {
      return this.remove((a) -> {
         return true;
      });
   }
}
