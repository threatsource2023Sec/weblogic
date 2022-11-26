package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.DefinitionException;
import org.jboss.weld.annotated.AnnotatedTypeValidator;
import org.jboss.weld.bootstrap.BeanDeployment;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.events.configurator.AnnotatedTypeConfiguratorImpl;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;

public class AbstractAnnotatedTypeRegisteringEvent extends AbstractBeanDiscoveryEvent {
   protected final List additionalAnnotatedTypes = new LinkedList();

   protected AbstractAnnotatedTypeRegisteringEvent(BeanManagerImpl beanManager, Type rawType, BeanDeploymentArchiveMapping bdaMapping, Deployment deployment, Collection contexts) {
      super(beanManager, rawType, bdaMapping, deployment, contexts);
   }

   protected void addSyntheticAnnotatedType(AnnotatedType type, String id) {
      AnnotatedTypeValidator.validateAnnotatedType(type);
      if (!type.getJavaClass().isAnnotation() && !Beans.isVetoed(type)) {
         this.storeSyntheticAnnotatedType(this.getOrCreateBeanDeployment(type.getJavaClass()), type, id);
      }
   }

   protected void storeSyntheticAnnotatedType(BeanDeployment deployment, AnnotatedType type, String id) {
      deployment.getBeanDeployer().addSyntheticClass(type, this.getReceiver(), id);
   }

   protected void finish() {
      try {
         Iterator var1 = this.additionalAnnotatedTypes.iterator();

         while(var1.hasNext()) {
            AnnotatedTypeRegistration annotatedTypeRegistration = (AnnotatedTypeRegistration)var1.next();
            this.addSyntheticAnnotatedType(annotatedTypeRegistration.configurator.complete(), annotatedTypeRegistration.id);
         }

      } catch (Exception var3) {
         throw new DefinitionException(var3);
      }
   }

   protected static class AnnotatedTypeRegistration {
      private final AnnotatedTypeConfiguratorImpl configurator;
      private final String id;

      AnnotatedTypeRegistration(AnnotatedTypeConfiguratorImpl configurator, String id) {
         this.configurator = configurator;
         this.id = id;
      }
   }
}
