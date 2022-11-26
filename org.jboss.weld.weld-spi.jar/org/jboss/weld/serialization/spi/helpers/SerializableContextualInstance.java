package org.jboss.weld.serialization.spi.helpers;

import java.io.Serializable;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.context.api.ContextualInstance;

public interface SerializableContextualInstance extends ContextualInstance, Serializable {
   SerializableContextual getContextual();

   Object getInstance();

   CreationalContext getCreationalContext();
}
