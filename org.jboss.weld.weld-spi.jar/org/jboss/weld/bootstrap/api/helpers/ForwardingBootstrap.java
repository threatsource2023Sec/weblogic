package org.jboss.weld.bootstrap.api.helpers;

import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.manager.api.WeldManager;

public abstract class ForwardingBootstrap implements Bootstrap {
   protected abstract Bootstrap delegate();

   public WeldManager getManager(BeanDeploymentArchive beanDeploymentArchive) {
      return this.delegate().getManager(beanDeploymentArchive);
   }

   public Bootstrap startContainer(Environment environment, Deployment deployment) {
      return this.delegate().startContainer(environment, deployment);
   }

   public void shutdown() {
      this.delegate().shutdown();
   }

   public String toString() {
      return this.delegate().toString();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }

   public Bootstrap deployBeans() {
      return this.delegate().deployBeans();
   }

   public Bootstrap endInitialization() {
      return this.delegate().endInitialization();
   }

   public Bootstrap startInitialization() {
      return this.delegate().startInitialization();
   }

   public Bootstrap validateBeans() {
      return this.delegate().validateBeans();
   }
}
