package org.jboss.weld.bootstrap.spi.helpers;

import java.util.Collection;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeansXml;

public abstract class ForwardingBeanDeploymentArchive implements BeanDeploymentArchive {
   protected abstract BeanDeploymentArchive delegate();

   public Collection getBeanClasses() {
      return this.delegate().getBeanClasses();
   }

   public Collection getKnownClasses() {
      return this.delegate().getKnownClasses();
   }

   public Collection getLoadedBeanClasses() {
      return this.delegate().getLoadedBeanClasses();
   }

   public ServiceRegistry getServices() {
      return this.delegate().getServices();
   }

   public String getId() {
      return this.delegate().getId();
   }

   public Collection getBeanDeploymentArchives() {
      return this.delegate().getBeanDeploymentArchives();
   }

   public BeansXml getBeansXml() {
      return this.delegate().getBeansXml();
   }

   public Collection getEjbs() {
      return this.delegate().getEjbs();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }

   public String toString() {
      return this.delegate().toString();
   }
}
