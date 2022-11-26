package org.jboss.weld.contexts.beanstore;

import java.util.Iterator;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public interface BeanStore extends Iterable {
   ContextualInstance get(BeanIdentifier var1);

   boolean contains(BeanIdentifier var1);

   void clear();

   Iterator iterator();

   void put(BeanIdentifier var1, ContextualInstance var2);

   LockedBean lock(BeanIdentifier var1);

   ContextualInstance remove(BeanIdentifier var1);
}
