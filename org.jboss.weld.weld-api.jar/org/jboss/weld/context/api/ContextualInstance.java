package org.jboss.weld.context.api;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public interface ContextualInstance {
   Object getInstance();

   CreationalContext getCreationalContext();

   Contextual getContextual();
}
