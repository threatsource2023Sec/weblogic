package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.Extension;

public interface NotificationListener {
   void preNotify(Extension var1);

   void postNotify(Extension var1);
}
