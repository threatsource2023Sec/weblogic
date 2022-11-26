package org.jboss.weld.module.ejb;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.Typed;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.attributes.BeanAttributesFactory;
import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;

class SessionBeans {
   private SessionBeans() {
   }

   public static EnhancedAnnotatedType getEjbImplementationClass(SessionBean bean) {
      return getEjbImplementationClass(bean.getEjbDescriptor(), bean.getBeanManager(), bean.getEnhancedAnnotated());
   }

   public static EnhancedAnnotatedType getEjbImplementationClass(EjbDescriptor descriptor, BeanManagerImpl manager, EnhancedAnnotatedType componentType) {
      InternalEjbDescriptor ejbDescriptor = InternalEjbDescriptor.of(descriptor);
      if (ejbDescriptor.getBeanClass().equals(ejbDescriptor.getImplementationClass())) {
         return componentType;
      } else {
         ClassTransformer transformer = (ClassTransformer)manager.getServices().get(ClassTransformer.class);
         EnhancedAnnotatedType implementationClass = (EnhancedAnnotatedType)Reflections.cast(transformer.getEnhancedAnnotatedType(ejbDescriptor.getImplementationClass(), manager.getId()));
         ((SlimAnnotatedTypeStore)manager.getServices().get(SlimAnnotatedTypeStore.class)).put(implementationClass.slim());
         return implementationClass;
      }
   }

   public static String createIdentifier(EnhancedAnnotatedType type, EjbDescriptor descriptor) {
      StringBuilder builder = BeanIdentifiers.getPrefix(SessionBean.class);
      appendEjbNameAndClass(builder, descriptor);
      if (!type.isDiscovered()) {
         builder.append("%").append(((AnnotatedTypeIdentifier)type.slim().getIdentifier()).asString());
      }

      return builder.toString();
   }

   public static String createIdentifierForNew(EjbDescriptor descriptor) {
      StringBuilder builder = BeanIdentifiers.getPrefix(NewSessionBean.class);
      return appendEjbNameAndClass(builder, descriptor).toString();
   }

   private static StringBuilder appendEjbNameAndClass(StringBuilder builder, EjbDescriptor descriptor) {
      return builder.append(descriptor.getEjbName()).append("%").append(descriptor.getBeanClass().getName());
   }

   public static BeanAttributes createBeanAttributes(EnhancedAnnotatedType annotated, InternalEjbDescriptor descriptor, BeanManagerImpl manager) {
      Set types = SharedObjectCache.instance(manager).getSharedSet(getSessionBeanTypes(annotated, (EjbDescriptor)Reflections.cast(descriptor)));
      return (new BeanAttributesFactory.BeanAttributesBuilder(annotated, types, manager)).build();
   }

   public static BeanAttributes createBeanAttributesForNew(EnhancedAnnotatedType annotated, InternalEjbDescriptor descriptor, BeanManagerImpl manager, Class javaClass) {
      BeanAttributes originalAttributes = createBeanAttributes(annotated, descriptor, manager);
      return BeanAttributesFactory.forNewBean(originalAttributes.getTypes(), javaClass);
   }

   private static Set getSessionBeanTypes(EnhancedAnnotated annotated, EjbDescriptor ejbDescriptor) {
      ImmutableSet.Builder types = ImmutableSet.builder();
      Map typeMap = new LinkedHashMap();
      HierarchyDiscovery beanClassDiscovery = HierarchyDiscovery.forNormalizedType(ejbDescriptor.getBeanClass());
      Iterator var5 = ejbDescriptor.getLocalBusinessInterfaces().iterator();

      while(true) {
         while(var5.hasNext()) {
            BusinessInterfaceDescriptor businessInterfaceDescriptor = (BusinessInterfaceDescriptor)var5.next();
            Type resolvedLocalInterface = beanClassDiscovery.resolveType(Types.getCanonicalType(businessInterfaceDescriptor.getInterface()));
            SessionBeanHierarchyDiscovery interfaceDiscovery = new SessionBeanHierarchyDiscovery(resolvedLocalInterface);
            if (beanClassDiscovery.getTypeMap().containsKey(businessInterfaceDescriptor.getInterface())) {
               Iterator var9 = interfaceDiscovery.getTypeMap().entrySet().iterator();

               while(var9.hasNext()) {
                  Map.Entry entry = (Map.Entry)var9.next();
                  if (annotated.getTypeClosure().contains(entry.getValue())) {
                     typeMap.put(entry.getKey(), entry.getValue());
                  }
               }
            } else {
               typeMap.putAll(interfaceDiscovery.getTypeMap());
            }
         }

         if (annotated.isAnnotationPresent(Typed.class)) {
            types.addAll(Beans.getTypedTypes(typeMap, annotated.getJavaClass(), (Typed)annotated.getAnnotation(Typed.class)));
         } else {
            typeMap.put(Object.class, Object.class);
            types.addAll(typeMap.values());
         }

         return Beans.getLegalBeanTypes(types.build(), annotated, new Type[0]);
      }
   }
}
