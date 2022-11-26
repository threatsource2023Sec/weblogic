package org.jboss.weld.bean;

import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public class NewManagedBean extends ManagedBean implements NewBean {
   public static NewManagedBean of(BeanAttributes attributes, EnhancedAnnotatedType clazz, BeanManagerImpl beanManager) {
      return new NewManagedBean(attributes, clazz, new StringBeanIdentifier(BeanIdentifiers.forNewManagedBean(clazz)), beanManager);
   }

   protected NewManagedBean(BeanAttributes attributes, EnhancedAnnotatedType type, BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(attributes, type, identifier, beanManager);
   }

   public boolean isSpecializing() {
      return false;
   }

   public String toString() {
      return "@New " + super.toString();
   }
}
