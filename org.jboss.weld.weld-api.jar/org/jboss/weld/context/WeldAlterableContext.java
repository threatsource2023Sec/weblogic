package org.jboss.weld.context;

import java.util.Collection;
import javax.enterprise.context.spi.AlterableContext;

public interface WeldAlterableContext extends AlterableContext {
   default Collection getAllContextualInstances() {
      throw new UnsupportedOperationException("getAllContextualInstances() is not implemented for context " + this.getClass());
   }

   default void clearAndSet(Collection setOfInstances) {
      throw new UnsupportedOperationException("clearAndSet is not implemented for context " + this.getClass());
   }
}
