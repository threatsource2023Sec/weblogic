package org.jboss.weld.bootstrap.spi;

import java.util.Collection;
import org.jboss.weld.bootstrap.api.ServiceRegistry;

public interface Deployment {
   Collection getBeanDeploymentArchives();

   BeanDeploymentArchive loadBeanDeploymentArchive(Class var1);

   ServiceRegistry getServices();

   Iterable getExtensions();
}
