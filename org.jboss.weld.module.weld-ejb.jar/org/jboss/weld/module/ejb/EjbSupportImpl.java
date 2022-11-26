package org.jboss.weld.module.ejb;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.Timeout;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.interceptor.InterceptorBindingsAdapter;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.injection.producer.AbstractInstantiator;
import org.jboss.weld.injection.producer.BasicInjectionTarget;
import org.jboss.weld.injection.producer.ConstructorInterceptionInstantiator;
import org.jboss.weld.injection.producer.DefaultInstantiator;
import org.jboss.weld.injection.producer.Instantiator;
import org.jboss.weld.injection.producer.InterceptionModelInitializer;
import org.jboss.weld.injection.producer.InterceptorApplyingInstantiator;
import org.jboss.weld.injection.producer.SubclassedComponentInstantiator;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.reflection.Reflections;

class EjbSupportImpl implements EjbSupport {
   private final EjbServices ejbServices;
   private final EjbDescriptors ejbDescriptors;

   EjbSupportImpl(EjbServices ejbServices, Collection descriptors) {
      this.ejbServices = ejbServices;
      this.ejbDescriptors = new EjbDescriptors(descriptors);
   }

   public void cleanup() {
   }

   public BasicInjectionTarget createSessionBeanInjectionTarget(EnhancedAnnotatedType type, SessionBean bean, BeanManagerImpl beanManager) {
      return SessionBeanInjectionTarget.of(type, bean, beanManager);
   }

   public BasicInjectionTarget createMessageDrivenInjectionTarget(EnhancedAnnotatedType type, EjbDescriptor d, BeanManagerImpl manager) {
      InternalEjbDescriptor descriptor = InternalEjbDescriptor.of(d);
      EnhancedAnnotatedType implementationClass = SessionBeans.getEjbImplementationClass(descriptor, manager, type);
      Instantiator instantiator = null;
      if (type.equals(implementationClass)) {
         instantiator = new DefaultInstantiator(type, (Bean)null, manager);
      } else {
         instantiator = SubclassedComponentInstantiator.forSubclassedEjb(type, implementationClass, (Bean)null, manager);
      }

      InterceptionModel interceptionModel = (InterceptionModel)manager.getInterceptorModelRegistry().get(type.slim());
      if (interceptionModel != null) {
         if (interceptionModel.hasExternalNonConstructorInterceptors()) {
            Instantiator instantiator = SubclassedComponentInstantiator.forInterceptedDecoratedBean(implementationClass, (Bean)null, (AbstractInstantiator)instantiator, manager);
            instantiator = new InterceptorApplyingInstantiator(instantiator, interceptionModel, type.slim());
         }

         if (interceptionModel.hasExternalConstructorInterceptors()) {
            instantiator = new ConstructorInterceptionInstantiator((Instantiator)instantiator, interceptionModel, type.slim());
         }
      }

      return BasicInjectionTarget.createDefault(type, (Bean)null, manager, (Instantiator)instantiator);
   }

   public BeanAttributes createSessionBeanAttributes(EnhancedAnnotatedType annotated, BeanManagerImpl manager) {
      InternalEjbDescriptor descriptor = this.ejbDescriptors.getUnique(annotated.getJavaClass());
      Preconditions.checkArgument(descriptor != null, annotated.getJavaClass() + " is not an EJB.");
      return this.createSessionBeanAttributes(annotated, descriptor, manager);
   }

   private BeanAttributes createSessionBeanAttributes(EnhancedAnnotatedType annotated, InternalEjbDescriptor descriptor, BeanManagerImpl manager) {
      return SessionBeans.createBeanAttributes(annotated, descriptor, manager);
   }

   public void createSessionBeans(BeanDeployerEnvironment environment, SetMultimap types, BeanManagerImpl manager) {
      ClassTransformer transformer = (ClassTransformer)manager.getServices().get(ClassTransformer.class);
      Iterator var5 = this.getEjbDescriptors().iterator();

      while(true) {
         while(true) {
            InternalEjbDescriptor ejbDescriptor;
            do {
               do {
                  do {
                     if (!var5.hasNext()) {
                        return;
                     }

                     ejbDescriptor = (InternalEjbDescriptor)var5.next();
                  } while(environment.isVetoed(ejbDescriptor.getBeanClass()));
               } while(Beans.isVetoed(ejbDescriptor.getBeanClass()));
            } while(!ejbDescriptor.isSingleton() && !ejbDescriptor.isStateful() && !ejbDescriptor.isStateless());

            Set classes = (Set)types.get(ejbDescriptor.getBeanClass());
            if (!classes.isEmpty()) {
               Iterator var8 = classes.iterator();

               while(var8.hasNext()) {
                  SlimAnnotatedType annotatedType = (SlimAnnotatedType)var8.next();
                  this.createSessionBean(ejbDescriptor, annotatedType, environment, manager, transformer);
               }
            } else {
               this.createSessionBean(ejbDescriptor, environment, manager, transformer);
            }
         }
      }
   }

   private SessionBean createSessionBean(InternalEjbDescriptor descriptor, SlimAnnotatedType slimType, BeanDeployerEnvironment environment, BeanManagerImpl manager, ClassTransformer transformer) {
      EnhancedAnnotatedType type = transformer.getEnhancedAnnotatedType(slimType);
      BeanAttributes attributes = this.createSessionBeanAttributes(type, descriptor, manager);
      SessionBean bean = SessionBeanImpl.of(attributes, (InternalEjbDescriptor)Reflections.cast(descriptor), manager, type);
      environment.addSessionBean(bean);
      return bean;
   }

   protected SessionBean createSessionBean(InternalEjbDescriptor descriptor, BeanDeployerEnvironment environment, BeanManagerImpl manager, ClassTransformer transformer) {
      SlimAnnotatedType type = transformer.getBackedAnnotatedType(descriptor.getBeanClass(), manager.getId());
      ((SlimAnnotatedTypeStore)manager.getServices().get(SlimAnnotatedTypeStore.class)).put(type);
      return this.createSessionBean(descriptor, type, environment, manager, transformer);
   }

   public void createNewSessionBeans(BeanDeployerEnvironment environment, BeanManagerImpl manager) {
      SlimAnnotatedTypeStore store = (SlimAnnotatedTypeStore)manager.getServices().get(SlimAnnotatedTypeStore.class);
      ClassTransformer classTransformer = (ClassTransformer)manager.getServices().get(ClassTransformer.class);
      Iterator var5 = environment.getNewBeanTypes().iterator();

      while(var5.hasNext()) {
         Type type = (Type)var5.next();
         Class clazz = Reflections.getRawType(type);
         if (this.isEjb(clazz)) {
            EnhancedAnnotatedType enhancedType = classTransformer.getEnhancedAnnotatedType(clazz, type, manager.getId());
            InternalEjbDescriptor descriptor = this.ejbDescriptors.getUnique(clazz);
            environment.addSessionBean(this.createNewSessionBean(enhancedType, descriptor, manager, store));
         }
      }

   }

   private SessionBean createNewSessionBean(EnhancedAnnotatedType type, InternalEjbDescriptor ejbDescriptor, BeanManagerImpl beanManager, SlimAnnotatedTypeStore store) {
      store.put(type.slim());
      BeanAttributes attributes = (BeanAttributes)Reflections.cast(SessionBeans.createBeanAttributesForNew(type, ejbDescriptor, beanManager, type.getJavaClass()));
      return NewSessionBean.of(attributes, ejbDescriptor, beanManager);
   }

   public Class getTimeoutAnnotation() {
      return Timeout.class;
   }

   public void registerCdiInterceptorsForMessageDrivenBeans(BeanDeployerEnvironment environment, BeanManagerImpl manager) {
      Iterator var3 = this.getEjbDescriptors().iterator();

      while(var3.hasNext()) {
         InternalEjbDescriptor descriptor = (InternalEjbDescriptor)var3.next();
         if (descriptor.isMessageDriven()) {
            EnhancedAnnotatedType type = ((ClassTransformer)manager.getServices().getRequired(ClassTransformer.class)).getEnhancedAnnotatedType(descriptor.getBeanClass(), manager.getId());
            if (!manager.getInterceptorModelRegistry().containsKey(type.slim())) {
               InterceptionModelInitializer.of(manager, type, (Bean)null).init();
            }

            InterceptionModel model = (InterceptionModel)manager.getInterceptorModelRegistry().get(type.slim());
            if (model != null) {
               this.ejbServices.registerInterceptors(descriptor.delegate(), new InterceptorBindingsAdapter(model));
            }
         }
      }

   }

   public Collection getEjbDescriptors() {
      return this.ejbDescriptors.getAll();
   }

   public boolean isEjb(Class beanClass) {
      return this.ejbDescriptors.contains(beanClass);
   }

   public InternalEjbDescriptor getEjbDescriptor(String beanName) {
      return this.ejbDescriptors.get(beanName);
   }

   public boolean isSessionBeanProxy(Object instance) {
      return instance instanceof EnterpriseBeanInstance;
   }
}
