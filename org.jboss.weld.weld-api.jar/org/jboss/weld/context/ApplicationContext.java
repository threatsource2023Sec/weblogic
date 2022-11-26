package org.jboss.weld.context;

import javax.enterprise.context.spi.AlterableContext;

public interface ApplicationContext extends AlterableContext {
   void invalidate();
}
