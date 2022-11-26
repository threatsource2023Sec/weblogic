package org.jboss.weld.event;

import java.util.Collection;
import java.util.Collections;
import javax.enterprise.inject.spi.ObserverMethod;

public interface ContainerLifecycleEventObserverMethod extends ObserverMethod {
   default Collection getRequiredAnnotations() {
      return Collections.emptySet();
   }
}
