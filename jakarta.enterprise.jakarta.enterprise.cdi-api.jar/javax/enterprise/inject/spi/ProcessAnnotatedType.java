package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

public interface ProcessAnnotatedType {
   AnnotatedType getAnnotatedType();

   void setAnnotatedType(AnnotatedType var1);

   AnnotatedTypeConfigurator configureAnnotatedType();

   void veto();
}
