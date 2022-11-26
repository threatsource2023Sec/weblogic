package org.jboss.weld.bean.builtin;

import java.util.Collections;
import java.util.Set;
import javax.enterprise.context.Dependent;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.attributes.ImmutableBeanAttributes;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.collections.ImmutableSet;

public abstract class AbstractBuiltInBean extends RIBean {
   private boolean proxyRequired;
   private final Class type;

   protected AbstractBuiltInBean(BeanIdentifier identifier, BeanManagerImpl beanManager, Class type) {
      super(new BuiltInBeanAttributes(type), identifier, beanManager);
      this.type = type;
   }

   public void preInitialize() {
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      this.proxyRequired = this.getScope() != null && this.isNormalScoped();
   }

   public void cleanupAfterBoot() {
   }

   public void initializeAfterBeanDiscovery() {
   }

   public Set getInjectionPoints() {
      return Collections.emptySet();
   }

   public boolean isProxyable() {
      return true;
   }

   public boolean isPassivationCapableBean() {
      return true;
   }

   public boolean isPassivationCapableDependency() {
      return true;
   }

   public boolean isProxyRequired() {
      return this.proxyRequired;
   }

   public Class getType() {
      return this.type;
   }

   public boolean isDependentContextOptimizationAllowed() {
      return Dependent.class.equals(this.getScope());
   }

   protected static class BuiltInBeanAttributes extends ImmutableBeanAttributes {
      public BuiltInBeanAttributes(Class type) {
         super(Collections.emptySet(), false, (String)null, Bindings.DEFAULT_QUALIFIERS, ImmutableSet.of(Object.class, type), Dependent.class);
      }
   }
}
