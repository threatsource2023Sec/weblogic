package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.EventContext;
import javax.enterprise.inject.spi.ObserverMethod;

public interface ObserverMethodConfigurator {
   ObserverMethodConfigurator read(Method var1);

   ObserverMethodConfigurator read(AnnotatedMethod var1);

   ObserverMethodConfigurator read(ObserverMethod var1);

   ObserverMethodConfigurator beanClass(Class var1);

   ObserverMethodConfigurator observedType(Type var1);

   ObserverMethodConfigurator addQualifier(Annotation var1);

   ObserverMethodConfigurator addQualifiers(Annotation... var1);

   ObserverMethodConfigurator addQualifiers(Set var1);

   ObserverMethodConfigurator qualifiers(Annotation... var1);

   ObserverMethodConfigurator qualifiers(Set var1);

   ObserverMethodConfigurator reception(Reception var1);

   ObserverMethodConfigurator transactionPhase(TransactionPhase var1);

   ObserverMethodConfigurator priority(int var1);

   ObserverMethodConfigurator notifyWith(EventConsumer var1);

   ObserverMethodConfigurator async(boolean var1);

   @FunctionalInterface
   public interface EventConsumer {
      void accept(EventContext var1) throws Exception;
   }
}
