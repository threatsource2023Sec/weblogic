package org.jboss.weld.bean;

import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.PassivationCapable;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public abstract class RIBean extends CommonBean implements PassivationCapable {
   protected final BeanManagerImpl beanManager;
   private boolean initialized;
   private volatile Set qualifiers;
   private ContextualInstanceStrategy contextualInstanceStrategy;

   protected RIBean(BeanAttributes attributes, BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(attributes, identifier);
      this.beanManager = beanManager;
      this.contextualInstanceStrategy = ContextualInstanceStrategy.create(attributes, beanManager);
   }

   public BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   public abstract Class getType();

   public Class getBeanClass() {
      return this.getType();
   }

   public abstract void preInitialize();

   public final synchronized void initialize(BeanDeployerEnvironment environment) {
      if (!this.initialized) {
         this.internalInitialize(environment);
         this.initialized = true;
      }

   }

   protected abstract void internalInitialize(BeanDeployerEnvironment var1);

   public abstract void cleanupAfterBoot();

   public abstract void initializeAfterBeanDiscovery();

   public boolean isDependent() {
      return this.getScope().equals(Dependent.class);
   }

   public boolean isNormalScoped() {
      return this.getBeanManager().isNormalScope(this.getScope());
   }

   public abstract boolean isProxyable();

   public abstract boolean isPassivationCapableBean();

   public abstract boolean isPassivationCapableDependency();

   public abstract boolean isProxyRequired();

   public Set getQualifierInstances() {
      if (this.qualifiers == null) {
         this.qualifiers = ((MetaAnnotationStore)this.beanManager.getServices().get(MetaAnnotationStore.class)).getQualifierInstances(this.getQualifiers());
      }

      return this.qualifiers;
   }

   public ContextualInstanceStrategy getContextualInstanceStrategy() {
      return this.contextualInstanceStrategy;
   }

   public void setAttributes(BeanAttributes attributes) {
      super.setAttributes(attributes);
      this.contextualInstanceStrategy = ContextualInstanceStrategy.create(attributes, this.beanManager);
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      this.contextualInstanceStrategy.destroy(this);
   }
}
