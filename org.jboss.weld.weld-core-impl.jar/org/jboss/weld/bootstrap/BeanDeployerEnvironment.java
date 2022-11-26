package org.jboss.weld.bootstrap;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.bean.InterceptorImpl;
import org.jboss.weld.bean.ManagedBean;
import org.jboss.weld.bean.NewBean;
import org.jboss.weld.bean.ProducerField;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bean.builtin.ExtensionBean;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resolution.ResolvableBuilder;
import org.jboss.weld.resolution.TypeSafeDisposerResolver;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.InjectionPoints;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.reflection.Reflections;

public class BeanDeployerEnvironment {
   private final Set annotatedTypes;
   private final Set vetoedClasses;
   private final SetMultimap classBeanMap;
   private final SetMultimap producerMethodBeanMap;
   private final Set producerFields;
   private final Set beans;
   private final Set observers;
   private final Set allDisposalBeans;
   private final Set resolvedDisposalBeans;
   private final Set decorators;
   private final Set interceptors;
   private final TypeSafeDisposerResolver disposalMethodResolver;
   private final Set newBeanTypes;
   private final BeanManagerImpl manager;

   protected BeanDeployerEnvironment(BeanManagerImpl manager) {
      this(new HashSet(), new HashSet(), SetMultimap.newConcurrentSetMultimap(), new HashSet(), SetMultimap.newConcurrentSetMultimap(), new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet(), manager);
   }

   protected BeanDeployerEnvironment(Set annotatedTypes, Set vetoedClasses, SetMultimap classBeanMap, Set producerFields, SetMultimap producerMethodBeanMap, Set beans, Set observers, Set allDisposalBeans, Set resolvedDisposalBeans, Set decorators, Set interceptors, Set newBeanTypes, BeanManagerImpl manager) {
      this.annotatedTypes = annotatedTypes;
      this.vetoedClasses = vetoedClasses;
      this.classBeanMap = classBeanMap;
      this.producerFields = producerFields;
      this.producerMethodBeanMap = producerMethodBeanMap;
      this.beans = beans;
      this.observers = observers;
      this.allDisposalBeans = allDisposalBeans;
      this.resolvedDisposalBeans = resolvedDisposalBeans;
      this.decorators = decorators;
      this.interceptors = interceptors;
      this.disposalMethodResolver = new TypeSafeDisposerResolver(allDisposalBeans, (WeldConfiguration)manager.getServices().get(WeldConfiguration.class));
      this.newBeanTypes = newBeanTypes;
      this.manager = manager;
   }

   public void addAnnotatedType(SlimAnnotatedTypeContext annotatedType) {
      this.annotatedTypes.add(annotatedType);
   }

   public void addAnnotatedTypes(Collection annotatedTypes) {
      this.annotatedTypes.addAll(annotatedTypes);
   }

   public void addSyntheticAnnotatedType(SlimAnnotatedType annotatedType, Extension extension) {
      this.addAnnotatedType(SlimAnnotatedTypeContext.of(annotatedType, extension));
   }

   public Set getAnnotatedTypes() {
      return Collections.unmodifiableSet(this.annotatedTypes);
   }

   public void removeAnnotatedType(SlimAnnotatedTypeContext annotatedType) {
      this.annotatedTypes.remove(annotatedType);
   }

   public void removeAnnotatedTypes(Collection annotatedTypes) {
      this.annotatedTypes.removeAll(annotatedTypes);
   }

   public void vetoJavaClass(Class javaClass) {
      this.vetoedClasses.add(javaClass);
   }

   public boolean isVetoed(Class clazz) {
      return this.vetoedClasses.contains(clazz);
   }

   public Set getProducerMethod(Class declaringClass, MethodSignature signature) {
      WeldMethodKey key = new WeldMethodKey(declaringClass, signature);
      Set beans = (Set)this.producerMethodBeanMap.get(key);
      Iterator var5 = beans.iterator();

      while(var5.hasNext()) {
         ProducerMethod producerMethod = (ProducerMethod)var5.next();
         producerMethod.initialize(this);
      }

      return beans;
   }

   public Set getClassBeans(Class clazz) {
      Set beans = (Set)this.classBeanMap.get(clazz);
      Iterator var3 = beans.iterator();

      while(var3.hasNext()) {
         AbstractClassBean bean = (AbstractClassBean)var3.next();
         bean.preInitialize();
      }

      return beans;
   }

   public void addProducerMethod(ProducerMethod bean) {
      ((Set)this.producerMethodBeanMap.get(BeanDeployerEnvironment.WeldMethodKey.of(bean))).add(bean);
      this.addAbstractBean(bean);
   }

   public void addProducerField(ProducerField bean) {
      this.producerFields.add(bean);
      this.addAbstractBean(bean);
   }

   public void addExtension(ExtensionBean bean) {
      this.beans.add(bean);
   }

   public void addBuiltInBean(AbstractBuiltInBean bean) {
      this.beans.add(bean);
   }

   protected void addAbstractClassBean(AbstractClassBean bean) {
      if (!(bean instanceof NewBean)) {
         ((Set)this.classBeanMap.get(bean.getBeanClass())).add(bean);
      }

      this.addAbstractBean(bean);
   }

   public void addManagedBean(ManagedBean bean) {
      this.addAbstractClassBean(bean);
   }

   public void addSessionBean(SessionBean bean) {
      Preconditions.checkArgument(bean instanceof AbstractClassBean, (Object)bean);
      this.addAbstractClassBean((AbstractClassBean)bean);
   }

   protected void addAbstractBean(AbstractBean bean) {
      this.beans.add(bean);
   }

   public void addDecorator(DecoratorImpl bean) {
      this.decorators.add(bean);
   }

   public void addInterceptor(InterceptorImpl bean) {
      this.interceptors.add(bean);
   }

   public void addDisposesMethod(DisposalMethod bean) {
      this.allDisposalBeans.add(bean);
      this.addNewBeansFromInjectionPoints(bean.getInjectionPoints());
   }

   public void addObserverMethod(ObserverInitializationContext observerInitializer) {
      this.observers.add(observerInitializer);
      this.addNewBeansFromInjectionPoints(observerInitializer.getObserver().getInjectionPoints());
   }

   public void addNewBeansFromInjectionPoints(AbstractBean bean) {
      this.addNewBeansFromInjectionPoints(bean.getInjectionPoints());
   }

   public void addNewBeansFromInjectionPoints(Set injectionPoints) {
      Iterator var2 = injectionPoints.iterator();

      while(var2.hasNext()) {
         InjectionPoint injectionPoint = (InjectionPoint)var2.next();
         WeldInjectionPointAttributes weldInjectionPoint = InjectionPoints.getWeldInjectionPoint(injectionPoint);
         if (weldInjectionPoint.getQualifier(New.class) != null) {
            Class rawType = Reflections.getRawType(weldInjectionPoint.getType());
            if (!Event.class.equals(rawType)) {
               New _new = (New)weldInjectionPoint.getQualifier(New.class);
               if (_new.value().equals(New.class)) {
                  if (rawType.equals(Instance.class)) {
                     Type typeParameter = Reflections.getActualTypeArguments(weldInjectionPoint.getType())[0];
                     this.addNewBeanFromInjectionPoint(typeParameter);
                  } else {
                     this.addNewBeanFromInjectionPoint(weldInjectionPoint.getType());
                  }
               } else {
                  this.addNewBeanFromInjectionPoint(_new.value());
               }
            }
         }
      }

   }

   private void addNewBeanFromInjectionPoint(Type baseType) {
      this.newBeanTypes.add(baseType);
   }

   public Set getBeans() {
      return Collections.unmodifiableSet(this.beans);
   }

   public Set getDecorators() {
      return Collections.unmodifiableSet(this.decorators);
   }

   public Set getInterceptors() {
      return Collections.unmodifiableSet(this.interceptors);
   }

   public Set getObservers() {
      return Collections.unmodifiableSet(this.observers);
   }

   public Set getUnresolvedDisposalBeans() {
      Set beans = new HashSet(this.allDisposalBeans);
      beans.removeAll(this.resolvedDisposalBeans);
      return Collections.unmodifiableSet(beans);
   }

   public Set resolveDisposalBeans(Set types, Set qualifiers, AbstractClassBean declaringBean) {
      Set beans = (Set)Reflections.cast(this.disposalMethodResolver.resolve((new ResolvableBuilder(this.manager)).addTypes(types).addQualifiers((Collection)qualifiers).setDeclaringBean(declaringBean).create(), true));
      this.resolvedDisposalBeans.addAll(beans);
      return Collections.unmodifiableSet(beans);
   }

   public void vetoBean(AbstractBean bean) {
      this.beans.remove(bean);
      if (bean instanceof AbstractClassBean) {
         ((Set)this.classBeanMap.get(bean.getBeanClass())).remove(bean);
         if (bean instanceof InterceptorImpl) {
            this.interceptors.remove(bean);
         }

         if (bean instanceof DecoratorImpl) {
            this.decorators.remove(bean);
         }
      }

      if (bean instanceof ProducerMethod) {
         ProducerMethod producerMethod = (ProducerMethod)Reflections.cast(bean);
         ((Set)this.producerMethodBeanMap.get(BeanDeployerEnvironment.WeldMethodKey.of(producerMethod))).remove(producerMethod);
      }

      if (bean instanceof ProducerField) {
         this.producerFields.remove(bean);
      }

   }

   public Iterable getClassBeans() {
      return this.classBeanMap.values();
   }

   public Iterable getProducerMethodBeans() {
      return this.producerMethodBeanMap.values();
   }

   public Set getProducerFields() {
      return Collections.unmodifiableSet(this.producerFields);
   }

   public void cleanup() {
      this.annotatedTypes.clear();
      this.vetoedClasses.clear();
      this.classBeanMap.clear();
      this.producerMethodBeanMap.clear();
      this.producerFields.clear();
      this.allDisposalBeans.clear();
      this.resolvedDisposalBeans.clear();
      this.beans.clear();
      this.decorators.clear();
      this.interceptors.clear();
      this.observers.clear();
      this.disposalMethodResolver.clear();
      this.newBeanTypes.clear();
   }

   public Set getNewBeanTypes() {
      return Collections.unmodifiableSet(this.newBeanTypes);
   }

   public void trim() {
      Iterator iterator = this.annotatedTypes.iterator();

      while(iterator.hasNext()) {
         if (!AnnotatedTypes.hasBeanDefiningAnnotation(((SlimAnnotatedTypeContext)iterator.next()).getAnnotatedType(), AnnotatedTypes.TRIM_META_ANNOTATIONS)) {
            iterator.remove();
         }
      }

   }

   protected static class WeldMethodKey {
      private final Class declaringClass;
      private final MethodSignature signature;

      static WeldMethodKey of(ProducerMethod producerMethod) {
         return new WeldMethodKey(producerMethod.getBeanClass(), producerMethod.getEnhancedAnnotated().getSignature());
      }

      WeldMethodKey(Class clazz, MethodSignature signature) {
         this.declaringClass = clazz;
         this.signature = signature;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.declaringClass == null ? 0 : this.declaringClass.hashCode());
         result = 31 * result + (this.signature == null ? 0 : this.signature.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            WeldMethodKey other = (WeldMethodKey)obj;
            if (this.declaringClass == null) {
               if (other.declaringClass != null) {
                  return false;
               }
            } else if (!this.declaringClass.equals(other.declaringClass)) {
               return false;
            }

            if (this.signature == null) {
               if (other.signature != null) {
                  return false;
               }
            } else if (!this.signature.equals(other.signature)) {
               return false;
            }

            return true;
         }
      }
   }
}
