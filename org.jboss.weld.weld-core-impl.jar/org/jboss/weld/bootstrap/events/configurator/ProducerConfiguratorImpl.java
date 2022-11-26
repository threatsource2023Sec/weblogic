package org.jboss.weld.bootstrap.events.configurator;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.inject.spi.configurator.ProducerConfigurator;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.reflection.Reflections;

public class ProducerConfiguratorImpl implements ProducerConfigurator, Configurator {
   private Function produceCallback;
   private Consumer disposeCallback;
   private final Set injectionPoints;

   public ProducerConfiguratorImpl(Producer producer) {
      this.produceCallback = (c) -> {
         return producer.produce(c);
      };
      this.disposeCallback = (i) -> {
         producer.dispose(i);
      };
      this.injectionPoints = producer.getInjectionPoints();
   }

   public ProducerConfigurator produceWith(Function callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.produceCallback = (Function)Reflections.cast(callback);
      return this;
   }

   public ProducerConfigurator disposeWith(Consumer callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.disposeCallback = (Consumer)Reflections.cast(callback);
      return this;
   }

   public Producer complete() {
      return new ProducerImpl(this);
   }

   static class ProducerImpl implements Producer {
      private final Function produceCallback;
      private final Consumer disposeCallback;
      private final Set injectionPoints;

      ProducerImpl(ProducerConfiguratorImpl configurator) {
         this.injectionPoints = configurator.injectionPoints;
         this.produceCallback = configurator.produceCallback;
         this.disposeCallback = configurator.disposeCallback;
      }

      public Object produce(CreationalContext ctx) {
         return this.produceCallback.apply(ctx);
      }

      public void dispose(Object instance) {
         this.disposeCallback.accept(instance);
      }

      public Set getInjectionPoints() {
         return this.injectionPoints;
      }
   }
}
