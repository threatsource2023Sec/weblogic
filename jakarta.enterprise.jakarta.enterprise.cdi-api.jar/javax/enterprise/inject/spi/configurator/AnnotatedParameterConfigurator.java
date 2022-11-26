package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;
import javax.enterprise.inject.spi.AnnotatedParameter;

public interface AnnotatedParameterConfigurator {
   AnnotatedParameter getAnnotated();

   AnnotatedParameterConfigurator add(Annotation var1);

   AnnotatedParameterConfigurator remove(Predicate var1);

   default AnnotatedParameterConfigurator removeAll() {
      return this.remove((a) -> {
         return true;
      });
   }
}
