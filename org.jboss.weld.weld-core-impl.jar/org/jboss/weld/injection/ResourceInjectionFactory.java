package org.jboss.weld.injection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.injection.spi.JaxwsInjectionServices;
import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.persistence.PersistenceApiAbstraction;
import org.jboss.weld.util.reflection.Reflections;
import org.jboss.weld.ws.WSApiAbstraction;

public final class ResourceInjectionFactory implements Service, Iterable {
   private final List resourceInjectionProcessors = new CopyOnWriteArrayList();

   public ResourceInjectionFactory() {
      this.initializeProcessors();
   }

   public void addResourceInjectionProcessor(ResourceInjectionProcessor processor) {
      this.resourceInjectionProcessors.add(processor);
   }

   public List getResourceInjections(Bean declaringBean, EnhancedAnnotatedType type, BeanManagerImpl manager) {
      List result = new ArrayList();

      for(EnhancedAnnotatedType actualType = type; actualType != null && !actualType.getJavaClass().equals(Object.class); actualType = actualType.getEnhancedSuperclass()) {
         Set resourceInjections = this.discoverType(declaringBean, actualType, manager);
         if (!resourceInjections.isEmpty()) {
            result.add(resourceInjections);
         }
      }

      Collections.reverse(result);
      return result;
   }

   public ResourceInjection getStaticProducerFieldResourceInjection(FieldInjectionPoint fieldInjectionPoint, BeanManagerImpl beanManager) {
      ResourceInjection resourceInjection = null;
      Iterator var4 = this.resourceInjectionProcessors.iterator();

      while(var4.hasNext()) {
         ResourceInjectionProcessor processor = (ResourceInjectionProcessor)var4.next();
         resourceInjection = processor.createStaticProducerFieldResourceInjection(fieldInjectionPoint, beanManager);
         if (resourceInjection != null) {
            break;
         }
      }

      return resourceInjection;
   }

   private void initializeProcessors() {
      this.resourceInjectionProcessors.add(new PersistenceUnitResourceInjectionProcessor());
      this.resourceInjectionProcessors.add(new PersistenceContextResourceInjectionProcessor());
      this.resourceInjectionProcessors.add(new ResourceResourceInjectionProcessor());
      this.resourceInjectionProcessors.add(new WebServiceResourceInjectionProcessor());
   }

   private Set discoverType(Bean bean, EnhancedAnnotatedType type, BeanManagerImpl manager) {
      Set resourceInjections = new HashSet();
      Iterator var5 = this.resourceInjectionProcessors.iterator();

      while(var5.hasNext()) {
         ResourceInjectionProcessor processor = (ResourceInjectionProcessor)var5.next();
         resourceInjections.addAll(processor.createResourceInjections(bean, type, manager));
      }

      return resourceInjections;
   }

   public void cleanup() {
   }

   public Iterator iterator() {
      return this.resourceInjectionProcessors.iterator();
   }

   private static class WebServiceResourceInjectionProcessor extends ResourceInjectionProcessor {
      private WebServiceResourceInjectionProcessor() {
      }

      protected ResourceReferenceFactory getResourceReferenceFactory(InjectionPoint injectionPoint, JaxwsInjectionServices injectionServices, WSApiAbstraction apiAbstraction) {
         return (ResourceReferenceFactory)Reflections.cast(injectionServices.registerWebServiceRefInjectionPoint(injectionPoint));
      }

      protected Class getMarkerAnnotation(WSApiAbstraction apiAbstraction) {
         return apiAbstraction.WEB_SERVICE_REF_ANNOTATION_CLASS;
      }

      protected WSApiAbstraction getProcessorContext(BeanManagerImpl manager) {
         return (WSApiAbstraction)manager.getServices().get(WSApiAbstraction.class);
      }

      protected JaxwsInjectionServices getInjectionServices(BeanManagerImpl manager) {
         return (JaxwsInjectionServices)manager.getServices().get(JaxwsInjectionServices.class);
      }

      // $FF: synthetic method
      WebServiceResourceInjectionProcessor(Object x0) {
         this();
      }
   }

   private static class ResourceResourceInjectionProcessor extends ResourceInjectionProcessor {
      private ResourceResourceInjectionProcessor() {
      }

      protected ResourceReferenceFactory getResourceReferenceFactory(InjectionPoint injectionPoint, ResourceInjectionServices injectionServices, Object processorContext) {
         return (ResourceReferenceFactory)Reflections.cast(injectionServices.registerResourceInjectionPoint(injectionPoint));
      }

      protected Class getMarkerAnnotation(Object processorContext) {
         return Resource.class;
      }

      protected Object getProcessorContext(BeanManagerImpl manager) {
         return null;
      }

      protected ResourceInjectionServices getInjectionServices(BeanManagerImpl manager) {
         return (ResourceInjectionServices)manager.getServices().get(ResourceInjectionServices.class);
      }

      // $FF: synthetic method
      ResourceResourceInjectionProcessor(Object x0) {
         this();
      }
   }

   private static class PersistenceContextResourceInjectionProcessor extends ResourceInjectionProcessor {
      private PersistenceContextResourceInjectionProcessor() {
      }

      protected ResourceReferenceFactory getResourceReferenceFactory(InjectionPoint injectionPoint, JpaInjectionServices injectionServices, PersistenceApiAbstraction apiAbstraction) {
         if (!injectionPoint.getType().equals(apiAbstraction.ENTITY_MANAGER_CLASS)) {
            throw BeanLogger.LOG.invalidResourceProducerType(injectionPoint.getAnnotated(), apiAbstraction.ENTITY_MANAGER_CLASS);
         } else {
            return (ResourceReferenceFactory)Reflections.cast(injectionServices.registerPersistenceContextInjectionPoint(injectionPoint));
         }
      }

      protected Class getMarkerAnnotation(PersistenceApiAbstraction apiAbstraction) {
         return apiAbstraction.PERSISTENCE_CONTEXT_ANNOTATION_CLASS;
      }

      protected PersistenceApiAbstraction getProcessorContext(BeanManagerImpl manager) {
         return (PersistenceApiAbstraction)manager.getServices().get(PersistenceApiAbstraction.class);
      }

      protected JpaInjectionServices getInjectionServices(BeanManagerImpl manager) {
         return (JpaInjectionServices)manager.getServices().get(JpaInjectionServices.class);
      }

      protected boolean accept(AnnotatedMember member, PersistenceApiAbstraction apiAbstraction) {
         apiAbstraction.getClass();
         return !"org.hibernate.Session".equals(Reflections.getRawType(this.getResourceInjectionPointType(member)).getName());
      }

      // $FF: synthetic method
      PersistenceContextResourceInjectionProcessor(Object x0) {
         this();
      }
   }

   private static class PersistenceUnitResourceInjectionProcessor extends ResourceInjectionProcessor {
      private PersistenceUnitResourceInjectionProcessor() {
      }

      protected ResourceReferenceFactory getResourceReferenceFactory(InjectionPoint injectionPoint, JpaInjectionServices injectionServices, PersistenceApiAbstraction apiAbstraction) {
         if (!injectionPoint.getType().equals(apiAbstraction.ENTITY_MANAGER_FACTORY_CLASS)) {
            throw BeanLogger.LOG.invalidResourceProducerType(injectionPoint.getAnnotated(), apiAbstraction.ENTITY_MANAGER_FACTORY_CLASS);
         } else {
            return (ResourceReferenceFactory)Reflections.cast(injectionServices.registerPersistenceUnitInjectionPoint(injectionPoint));
         }
      }

      protected Class getMarkerAnnotation(PersistenceApiAbstraction apiAbstraction) {
         return apiAbstraction.PERSISTENCE_UNIT_ANNOTATION_CLASS;
      }

      protected PersistenceApiAbstraction getProcessorContext(BeanManagerImpl manager) {
         return (PersistenceApiAbstraction)manager.getServices().get(PersistenceApiAbstraction.class);
      }

      protected JpaInjectionServices getInjectionServices(BeanManagerImpl manager) {
         return (JpaInjectionServices)manager.getServices().get(JpaInjectionServices.class);
      }

      protected boolean accept(AnnotatedMember member, PersistenceApiAbstraction apiAbstraction) {
         apiAbstraction.getClass();
         return !"org.hibernate.SessionFactory".equals(Reflections.getRawType(this.getResourceInjectionPointType(member)).getName());
      }

      // $FF: synthetic method
      PersistenceUnitResourceInjectionProcessor(Object x0) {
         this();
      }
   }
}
