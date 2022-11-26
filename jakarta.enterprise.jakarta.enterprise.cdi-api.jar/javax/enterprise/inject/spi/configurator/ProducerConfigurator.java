package javax.enterprise.inject.spi.configurator;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ProducerConfigurator {
   ProducerConfigurator produceWith(Function var1);

   ProducerConfigurator disposeWith(Consumer var1);
}
