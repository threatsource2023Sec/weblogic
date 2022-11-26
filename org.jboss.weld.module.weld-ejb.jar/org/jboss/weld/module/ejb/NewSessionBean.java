package org.jboss.weld.module.ejb;

import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.NewBean;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.serialization.spi.BeanIdentifier;

class NewSessionBean extends SessionBeanImpl implements NewBean {
   public static NewSessionBean of(BeanAttributes attributes, InternalEjbDescriptor ejbDescriptor, BeanManagerImpl beanManager) {
      EnhancedAnnotatedType type = ((ClassTransformer)beanManager.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(ejbDescriptor.getBeanClass(), beanManager.getId());
      return new NewSessionBean(attributes, type, ejbDescriptor, new StringBeanIdentifier(SessionBeans.createIdentifierForNew(ejbDescriptor)), beanManager);
   }

   NewSessionBean(BeanAttributes attributes, EnhancedAnnotatedType type, InternalEjbDescriptor ejbDescriptor, BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(attributes, type, ejbDescriptor, identifier, beanManager);
   }

   public boolean isSpecializing() {
      return false;
   }

   protected void checkScopeAllowed() {
   }

   public String toString() {
      return "@New " + super.toString();
   }
}
