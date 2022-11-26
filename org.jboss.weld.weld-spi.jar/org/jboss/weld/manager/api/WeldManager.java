package org.jboss.weld.manager.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.construction.api.WeldCreationalContext;
import org.jboss.weld.context.WeldAlterableContext;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public interface WeldManager extends BeanManager, Serializable {
   InjectionTarget createInjectionTarget(EjbDescriptor var1);

   Bean getBean(EjbDescriptor var1);

   EjbDescriptor getEjbDescriptor(String var1);

   ServiceRegistry getServices();

   InjectionTarget fireProcessInjectionTarget(AnnotatedType var1);

   InjectionTarget fireProcessInjectionTarget(AnnotatedType var1, InjectionTarget var2);

   String getId();

   Instance instance();

   WeldInjectionTargetFactory getInjectionTargetFactory(AnnotatedType var1);

   WeldCreationalContext createCreationalContext(Contextual var1);

   Bean getPassivationCapableBean(BeanIdentifier var1);

   WeldInjectionTargetBuilder createInjectionTargetBuilder(AnnotatedType var1);

   WeldManager unwrap();

   AnnotatedType createAnnotatedType(Class var1, String var2);

   void disposeAnnotatedType(Class var1, String var2);

   boolean isContextActive(Class var1);

   Collection getScopes();

   default Collection getActiveContexts() {
      return (Collection)this.getScopes().stream().filter(this::isContextActive).map(this::getContext).collect(Collectors.toSet());
   }

   default Collection getActiveWeldAlterableContexts() {
      return (Collection)this.getScopes().stream().filter(this::isContextActive).map(this::getContext).filter((t) -> {
         return t instanceof WeldAlterableContext;
      }).map((t) -> {
         return (WeldAlterableContext)t;
      }).collect(Collectors.toSet());
   }
}
