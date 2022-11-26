package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import java.util.Collection;
import org.jboss.weld.bootstrap.BeanDeployment;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.util.DeploymentStructures;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractBeanDiscoveryEvent extends AbstractDefinitionContainerEvent {
   private final BeanDeploymentArchiveMapping bdaMapping;
   private final Deployment deployment;
   private final Collection contexts;

   public AbstractBeanDiscoveryEvent(BeanManagerImpl beanManager, Type rawType, BeanDeploymentArchiveMapping bdaMapping, Deployment deployment, Collection contexts) {
      super(beanManager, rawType, Reflections.EMPTY_TYPES);
      this.bdaMapping = bdaMapping;
      this.deployment = deployment;
      this.contexts = contexts;
   }

   protected BeanDeploymentArchiveMapping getBeanDeployments() {
      return this.bdaMapping;
   }

   protected Deployment getDeployment() {
      return this.deployment;
   }

   protected Collection getContexts() {
      return this.contexts;
   }

   protected TypeStore getTypeStore() {
      return (TypeStore)this.getDeployment().getServices().get(TypeStore.class);
   }

   protected BeanDeployment getOrCreateBeanDeployment(Class clazz) {
      return DeploymentStructures.getOrCreateBeanDeployment(this.deployment, this.getBeanManager(), this.bdaMapping, this.contexts, clazz);
   }
}
