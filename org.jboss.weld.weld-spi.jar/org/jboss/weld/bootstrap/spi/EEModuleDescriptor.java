package org.jboss.weld.bootstrap.spi;

import org.jboss.weld.bootstrap.api.Service;

public interface EEModuleDescriptor extends Service {
   String getId();

   ModuleType getType();

   public static enum ModuleType {
      EAR,
      WEB,
      EJB_JAR,
      APPLICATION_CLIENT,
      CONNECTOR;
   }
}
