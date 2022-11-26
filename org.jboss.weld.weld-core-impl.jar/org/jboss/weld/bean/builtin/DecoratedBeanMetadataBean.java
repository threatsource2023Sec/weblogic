package org.jboss.weld.bean.builtin;

import java.util.Collections;
import java.util.Set;
import javax.enterprise.inject.Decorated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.literal.DecoratedLiteral;
import org.jboss.weld.manager.BeanManagerImpl;

public class DecoratedBeanMetadataBean extends InterceptedBeanMetadataBean {
   public DecoratedBeanMetadataBean(BeanManagerImpl beanManager) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, Bean.class, Decorated.class.getSimpleName())), beanManager);
   }

   protected void checkInjectionPoint(InjectionPoint ip) {
      if (!(ip.getBean() instanceof Decorator)) {
         throw new IllegalArgumentException("@Decorated Bean<?> can only be injected into a decorator.");
      }
   }

   public Set getQualifiers() {
      return Collections.singleton(DecoratedLiteral.INSTANCE);
   }

   public String toString() {
      return "Implicit Bean [javax.enterprise.inject.spi.Bean] with qualifiers [@Decorated]";
   }
}
