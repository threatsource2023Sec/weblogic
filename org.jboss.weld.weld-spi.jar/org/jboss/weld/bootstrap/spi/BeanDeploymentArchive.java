package org.jboss.weld.bootstrap.spi;

import java.util.Collection;
import java.util.Collections;
import org.jboss.weld.bootstrap.api.ServiceRegistry;

public interface BeanDeploymentArchive {
   Collection getBeanDeploymentArchives();

   Collection getBeanClasses();

   default Collection getLoadedBeanClasses() {
      return Collections.emptySet();
   }

   default Collection getKnownClasses() {
      return this.getBeanClasses();
   }

   BeansXml getBeansXml();

   Collection getEjbs();

   ServiceRegistry getServices();

   String getId();
}
