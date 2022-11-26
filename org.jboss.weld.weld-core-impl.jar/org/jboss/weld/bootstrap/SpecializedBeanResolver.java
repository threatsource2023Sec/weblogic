package org.jboss.weld.bootstrap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.util.collections.Iterables;

public class SpecializedBeanResolver {
   private final Set accessibleEnvironments;
   private static final String IS_NOT_A_SPECIALIZING_BEAN = " is not a specializing bean";
   private static final BootstrapTransform CLASS_BEAN_TRANSFORM = (bean, environment) -> {
      return environment.getClassBeans(bean.getBeanClass().getSuperclass());
   };
   private static final BootstrapTransform PRODUCER_METHOD_TRANSFORM = (bean, environment) -> {
      return environment.getProducerMethod(bean.getBeanClass().getSuperclass(), bean.getEnhancedAnnotated().getSignature());
   };

   public SpecializedBeanResolver(Set accessibleEnvironments) {
      this.accessibleEnvironments = accessibleEnvironments;
   }

   private Set getSpecializedBeans(Bean bean, BootstrapTransform transform) {
      Set beans = new HashSet();
      Iterator var4 = this.accessibleEnvironments.iterator();

      while(var4.hasNext()) {
         BeanDeployerEnvironment environment = (BeanDeployerEnvironment)var4.next();
         Iterables.addAll(beans, transform.transform(bean, environment));
      }

      return beans;
   }

   protected Set resolveSpecializedBeans(AbstractClassBean bean) {
      if (!bean.isSpecializing()) {
         throw new IllegalArgumentException(bean + " is not a specializing bean");
      } else {
         return this.getSpecializedBeans(bean, CLASS_BEAN_TRANSFORM);
      }
   }

   protected Set resolveSpecializedBeans(ProducerMethod bean) {
      if (!bean.isSpecializing()) {
         throw new IllegalArgumentException(bean + " is not a specializing bean");
      } else {
         return this.getSpecializedBeans(bean, PRODUCER_METHOD_TRANSFORM);
      }
   }

   private interface BootstrapTransform {
      Iterable transform(Bean var1, BeanDeployerEnvironment var2);
   }
}
