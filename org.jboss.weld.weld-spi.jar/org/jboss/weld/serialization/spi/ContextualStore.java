package org.jboss.weld.serialization.spi;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;
import org.jboss.weld.serialization.spi.helpers.SerializableContextualInstance;

public interface ContextualStore extends Service {
   Contextual getContextual(String var1);

   Contextual getContextual(BeanIdentifier var1);

   BeanIdentifier putIfAbsent(Contextual var1);

   SerializableContextual getSerializableContextual(Contextual var1);

   SerializableContextualInstance getSerializableContextualInstance(Contextual var1, Object var2, CreationalContext var3);
}
