package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.ObserverMethodConfigurator;

public interface ProcessObserverMethod {
   AnnotatedMethod getAnnotatedMethod();

   ObserverMethod getObserverMethod();

   void addDefinitionError(Throwable var1);

   void setObserverMethod(ObserverMethod var1);

   ObserverMethodConfigurator configureObserverMethod();

   void veto();
}
