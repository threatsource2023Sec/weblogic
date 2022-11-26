package org.jboss.weld.context;

import javax.enterprise.context.spi.AlterableContext;

public interface SingletonContext extends AlterableContext {
   void invalidate();
}
