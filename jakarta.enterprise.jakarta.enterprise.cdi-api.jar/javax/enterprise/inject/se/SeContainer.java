package javax.enterprise.inject.se;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;

public interface SeContainer extends Instance, AutoCloseable {
   void close();

   boolean isRunning();

   BeanManager getBeanManager();
}
