package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.ProducerConfigurator;

public interface ProcessProducer {
   AnnotatedMember getAnnotatedMember();

   Producer getProducer();

   void setProducer(Producer var1);

   ProducerConfigurator configureProducer();

   void addDefinitionError(Throwable var1);
}
