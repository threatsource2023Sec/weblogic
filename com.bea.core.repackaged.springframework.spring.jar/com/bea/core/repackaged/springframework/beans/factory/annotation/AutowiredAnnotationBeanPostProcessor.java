package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.InjectionPoint;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.UnsatisfiedDependencyException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import com.bea.core.repackaged.springframework.beans.factory.support.LookupOverride;
import com.bea.core.repackaged.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AutowiredAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, PriorityOrdered, BeanFactoryAware {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Set autowiredAnnotationTypes = new LinkedHashSet(4);
   private String requiredParameterName = "required";
   private boolean requiredParameterValue = true;
   private int order = 2147483645;
   @Nullable
   private ConfigurableListableBeanFactory beanFactory;
   private final Set lookupMethodsChecked = Collections.newSetFromMap(new ConcurrentHashMap(256));
   private final Map candidateConstructorsCache = new ConcurrentHashMap(256);
   private final Map injectionMetadataCache = new ConcurrentHashMap(256);

   public AutowiredAnnotationBeanPostProcessor() {
      this.autowiredAnnotationTypes.add(Autowired.class);
      this.autowiredAnnotationTypes.add(Value.class);

      try {
         this.autowiredAnnotationTypes.add(ClassUtils.forName("javax.inject.Inject", AutowiredAnnotationBeanPostProcessor.class.getClassLoader()));
         this.logger.trace("JSR-330 'javax.inject.Inject' annotation found and supported for autowiring");
      } catch (ClassNotFoundException var2) {
      }

   }

   public void setAutowiredAnnotationType(Class autowiredAnnotationType) {
      Assert.notNull(autowiredAnnotationType, (String)"'autowiredAnnotationType' must not be null");
      this.autowiredAnnotationTypes.clear();
      this.autowiredAnnotationTypes.add(autowiredAnnotationType);
   }

   public void setAutowiredAnnotationTypes(Set autowiredAnnotationTypes) {
      Assert.notEmpty((Collection)autowiredAnnotationTypes, (String)"'autowiredAnnotationTypes' must not be empty");
      this.autowiredAnnotationTypes.clear();
      this.autowiredAnnotationTypes.addAll(autowiredAnnotationTypes);
   }

   public void setRequiredParameterName(String requiredParameterName) {
      this.requiredParameterName = requiredParameterName;
   }

   public void setRequiredParameterValue(boolean requiredParameterValue) {
      this.requiredParameterValue = requiredParameterValue;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
         throw new IllegalArgumentException("AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
      } else {
         this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
      }
   }

   public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class beanType, String beanName) {
      InjectionMetadata metadata = this.findAutowiringMetadata(beanName, beanType, (PropertyValues)null);
      metadata.checkConfigMembers(beanDefinition);
   }

   public void resetBeanDefinition(String beanName) {
      this.lookupMethodsChecked.remove(beanName);
      this.injectionMetadataCache.remove(beanName);
   }

   @Nullable
   public Constructor[] determineCandidateConstructors(Class beanClass, String beanName) throws BeanCreationException {
      if (!this.lookupMethodsChecked.contains(beanName)) {
         try {
            ReflectionUtils.doWithMethods(beanClass, (method) -> {
               Lookup lookup = (Lookup)method.getAnnotation(Lookup.class);
               if (lookup != null) {
                  Assert.state(this.beanFactory != null, "No BeanFactory available");
                  LookupOverride override = new LookupOverride(method, lookup.value());

                  try {
                     RootBeanDefinition mbd = (RootBeanDefinition)this.beanFactory.getMergedBeanDefinition(beanName);
                     mbd.getMethodOverrides().addOverride(override);
                  } catch (NoSuchBeanDefinitionException var6) {
                     throw new BeanCreationException(beanName, "Cannot apply @Lookup to beans without corresponding bean definition");
                  }
               }

            });
         } catch (IllegalStateException var21) {
            throw new BeanCreationException(beanName, "Lookup method resolution failed", var21);
         }

         this.lookupMethodsChecked.add(beanName);
      }

      Constructor[] candidateConstructors = (Constructor[])this.candidateConstructorsCache.get(beanClass);
      if (candidateConstructors == null) {
         synchronized(this.candidateConstructorsCache) {
            candidateConstructors = (Constructor[])this.candidateConstructorsCache.get(beanClass);
            if (candidateConstructors == null) {
               Constructor[] rawCandidates;
               try {
                  rawCandidates = beanClass.getDeclaredConstructors();
               } catch (Throwable var20) {
                  throw new BeanCreationException(beanName, "Resolution of declared constructors on bean Class [" + beanClass.getName() + "] from ClassLoader [" + beanClass.getClassLoader() + "] failed", var20);
               }

               List candidates = new ArrayList(rawCandidates.length);
               Constructor requiredConstructor = null;
               Constructor defaultConstructor = null;
               Constructor primaryConstructor = BeanUtils.findPrimaryConstructor(beanClass);
               int nonSyntheticConstructors = 0;
               Constructor[] var11 = rawCandidates;
               int var12 = rawCandidates.length;
               int var13 = 0;

               while(true) {
                  if (var13 >= var12) {
                     if (!candidates.isEmpty()) {
                        if (requiredConstructor == null) {
                           if (defaultConstructor != null) {
                              candidates.add(defaultConstructor);
                           } else if (candidates.size() == 1 && this.logger.isInfoEnabled()) {
                              this.logger.info("Inconsistent constructor declaration on bean with name '" + beanName + "': single autowire-marked constructor flagged as optional - this constructor is effectively required since there is no default constructor to fall back to: " + candidates.get(0));
                           }
                        }

                        candidateConstructors = (Constructor[])candidates.toArray(new Constructor[0]);
                     } else if (rawCandidates.length == 1 && rawCandidates[0].getParameterCount() > 0) {
                        candidateConstructors = new Constructor[]{rawCandidates[0]};
                     } else if (nonSyntheticConstructors == 2 && primaryConstructor != null && defaultConstructor != null && !primaryConstructor.equals(defaultConstructor)) {
                        candidateConstructors = new Constructor[]{primaryConstructor, defaultConstructor};
                     } else if (nonSyntheticConstructors == 1 && primaryConstructor != null) {
                        candidateConstructors = new Constructor[]{primaryConstructor};
                     } else {
                        candidateConstructors = new Constructor[0];
                     }

                     this.candidateConstructorsCache.put(beanClass, candidateConstructors);
                     break;
                  }

                  label133: {
                     Constructor candidate = var11[var13];
                     if (!candidate.isSynthetic()) {
                        ++nonSyntheticConstructors;
                     } else if (primaryConstructor != null) {
                        break label133;
                     }

                     AnnotationAttributes ann = this.findAutowiredAnnotation(candidate);
                     if (ann == null) {
                        Class userClass = ClassUtils.getUserClass(beanClass);
                        if (userClass != beanClass) {
                           try {
                              Constructor superCtor = userClass.getDeclaredConstructor(candidate.getParameterTypes());
                              ann = this.findAutowiredAnnotation(superCtor);
                           } catch (NoSuchMethodException var19) {
                           }
                        }
                     }

                     if (ann != null) {
                        if (requiredConstructor != null) {
                           throw new BeanCreationException(beanName, "Invalid autowire-marked constructor: " + candidate + ". Found constructor with 'required' Autowired annotation already: " + requiredConstructor);
                        }

                        boolean required = this.determineRequiredStatus(ann);
                        if (required) {
                           if (!candidates.isEmpty()) {
                              throw new BeanCreationException(beanName, "Invalid autowire-marked constructors: " + candidates + ". Found constructor with 'required' Autowired annotation: " + candidate);
                           }

                           requiredConstructor = candidate;
                        }

                        candidates.add(candidate);
                     } else if (candidate.getParameterCount() == 0) {
                        defaultConstructor = candidate;
                     }
                  }

                  ++var13;
               }
            }
         }
      }

      return candidateConstructors.length > 0 ? candidateConstructors : null;
   }

   public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
      InjectionMetadata metadata = this.findAutowiringMetadata(beanName, bean.getClass(), pvs);

      try {
         metadata.inject(bean, beanName, pvs);
         return pvs;
      } catch (BeanCreationException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", var7);
      }
   }

   /** @deprecated */
   @Deprecated
   public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) {
      return this.postProcessProperties(pvs, bean, beanName);
   }

   public void processInjection(Object bean) throws BeanCreationException {
      Class clazz = bean.getClass();
      InjectionMetadata metadata = this.findAutowiringMetadata(clazz.getName(), clazz, (PropertyValues)null);

      try {
         metadata.inject(bean, (String)null, (PropertyValues)null);
      } catch (BeanCreationException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new BeanCreationException("Injection of autowired dependencies failed for class [" + clazz + "]", var6);
      }
   }

   private InjectionMetadata findAutowiringMetadata(String beanName, Class clazz, @Nullable PropertyValues pvs) {
      String cacheKey = StringUtils.hasLength(beanName) ? beanName : clazz.getName();
      InjectionMetadata metadata = (InjectionMetadata)this.injectionMetadataCache.get(cacheKey);
      if (InjectionMetadata.needsRefresh(metadata, clazz)) {
         synchronized(this.injectionMetadataCache) {
            metadata = (InjectionMetadata)this.injectionMetadataCache.get(cacheKey);
            if (InjectionMetadata.needsRefresh(metadata, clazz)) {
               if (metadata != null) {
                  metadata.clear(pvs);
               }

               metadata = this.buildAutowiringMetadata(clazz);
               this.injectionMetadataCache.put(cacheKey, metadata);
            }
         }
      }

      return metadata;
   }

   private InjectionMetadata buildAutowiringMetadata(Class clazz) {
      List elements = new ArrayList();
      Class targetClass = clazz;

      do {
         List currElements = new ArrayList();
         ReflectionUtils.doWithLocalFields(targetClass, (field) -> {
            AnnotationAttributes ann = this.findAutowiredAnnotation(field);
            if (ann != null) {
               if (Modifier.isStatic(field.getModifiers())) {
                  if (this.logger.isInfoEnabled()) {
                     this.logger.info("Autowired annotation is not supported on static fields: " + field);
                  }

                  return;
               }

               boolean required = this.determineRequiredStatus(ann);
               currElements.add(new AutowiredFieldElement(field, required));
            }

         });
         ReflectionUtils.doWithLocalMethods(targetClass, (method) -> {
            Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
            if (BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
               AnnotationAttributes ann = this.findAutowiredAnnotation(bridgedMethod);
               if (ann != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
                  if (Modifier.isStatic(method.getModifiers())) {
                     if (this.logger.isInfoEnabled()) {
                        this.logger.info("Autowired annotation is not supported on static methods: " + method);
                     }

                     return;
                  }

                  if (method.getParameterCount() == 0 && this.logger.isInfoEnabled()) {
                     this.logger.info("Autowired annotation should only be used on methods with parameters: " + method);
                  }

                  boolean required = this.determineRequiredStatus(ann);
                  PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
                  currElements.add(new AutowiredMethodElement(method, required, pd));
               }

            }
         });
         elements.addAll(0, currElements);
         targetClass = targetClass.getSuperclass();
      } while(targetClass != null && targetClass != Object.class);

      return new InjectionMetadata(clazz, elements);
   }

   @Nullable
   private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
      if (ao.getAnnotations().length > 0) {
         Iterator var2 = this.autowiredAnnotationTypes.iterator();

         while(var2.hasNext()) {
            Class type = (Class)var2.next();
            AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, (Class)type);
            if (attributes != null) {
               return attributes;
            }
         }
      }

      return null;
   }

   protected boolean determineRequiredStatus(AnnotationAttributes ann) {
      return !ann.containsKey(this.requiredParameterName) || this.requiredParameterValue == ann.getBoolean(this.requiredParameterName);
   }

   protected Map findAutowireCandidates(Class type) throws BeansException {
      if (this.beanFactory == null) {
         throw new IllegalStateException("No BeanFactory configured - override the getBeanOfType method or specify the 'beanFactory' property");
      } else {
         return BeanFactoryUtils.beansOfTypeIncludingAncestors(this.beanFactory, type);
      }
   }

   private void registerDependentBeans(@Nullable String beanName, Set autowiredBeanNames) {
      if (beanName != null) {
         Iterator var3 = autowiredBeanNames.iterator();

         while(var3.hasNext()) {
            String autowiredBeanName = (String)var3.next();
            if (this.beanFactory != null && this.beanFactory.containsBean(autowiredBeanName)) {
               this.beanFactory.registerDependentBean(autowiredBeanName, beanName);
            }

            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Autowiring by type from bean name '" + beanName + "' to bean named '" + autowiredBeanName + "'");
            }
         }
      }

   }

   @Nullable
   private Object resolvedCachedArgument(@Nullable String beanName, @Nullable Object cachedArgument) {
      if (cachedArgument instanceof DependencyDescriptor) {
         DependencyDescriptor descriptor = (DependencyDescriptor)cachedArgument;
         Assert.state(this.beanFactory != null, "No BeanFactory available");
         return this.beanFactory.resolveDependency(descriptor, beanName, (Set)null, (TypeConverter)null);
      } else {
         return cachedArgument;
      }
   }

   private static class ShortcutDependencyDescriptor extends DependencyDescriptor {
      private final String shortcut;
      private final Class requiredType;

      public ShortcutDependencyDescriptor(DependencyDescriptor original, String shortcut, Class requiredType) {
         super(original);
         this.shortcut = shortcut;
         this.requiredType = requiredType;
      }

      public Object resolveShortcut(BeanFactory beanFactory) {
         return beanFactory.getBean(this.shortcut, this.requiredType);
      }
   }

   private class AutowiredMethodElement extends InjectionMetadata.InjectedElement {
      private final boolean required;
      private volatile boolean cached = false;
      @Nullable
      private volatile Object[] cachedMethodArguments;

      public AutowiredMethodElement(Method method, boolean required, @Nullable PropertyDescriptor pd) {
         super(method, pd);
         this.required = required;
      }

      protected void inject(Object bean, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
         if (!this.checkPropertySkipping(pvs)) {
            Method method = (Method)this.member;
            Object[] arguments;
            if (this.cached) {
               arguments = this.resolveCachedArguments(beanName);
            } else {
               Class[] paramTypes = method.getParameterTypes();
               arguments = new Object[paramTypes.length];
               DependencyDescriptor[] descriptors = new DependencyDescriptor[paramTypes.length];
               Set autowiredBeans = new LinkedHashSet(paramTypes.length);
               Assert.state(AutowiredAnnotationBeanPostProcessor.this.beanFactory != null, "No BeanFactory available");
               TypeConverter typeConverter = AutowiredAnnotationBeanPostProcessor.this.beanFactory.getTypeConverter();

               for(int i = 0; i < arguments.length; ++i) {
                  MethodParameter methodParam = new MethodParameter(method, i);
                  DependencyDescriptor currDesc = new DependencyDescriptor(methodParam, this.required);
                  currDesc.setContainingClass(bean.getClass());
                  descriptors[i] = currDesc;

                  try {
                     Object arg = AutowiredAnnotationBeanPostProcessor.this.beanFactory.resolveDependency(currDesc, beanName, autowiredBeans, typeConverter);
                     if (arg == null && !this.required) {
                        arguments = null;
                        break;
                     }

                     arguments[i] = arg;
                  } catch (BeansException var18) {
                     throw new UnsatisfiedDependencyException((String)null, beanName, new InjectionPoint(methodParam), var18);
                  }
               }

               synchronized(this) {
                  if (!this.cached) {
                     if (arguments != null) {
                        Object[] cachedMethodArguments = new Object[paramTypes.length];
                        System.arraycopy(descriptors, 0, cachedMethodArguments, 0, arguments.length);
                        AutowiredAnnotationBeanPostProcessor.this.registerDependentBeans(beanName, autowiredBeans);
                        if (autowiredBeans.size() == paramTypes.length) {
                           Iterator it = autowiredBeans.iterator();

                           for(int ix = 0; ix < paramTypes.length; ++ix) {
                              String autowiredBeanName = (String)it.next();
                              if (AutowiredAnnotationBeanPostProcessor.this.beanFactory.containsBean(autowiredBeanName) && AutowiredAnnotationBeanPostProcessor.this.beanFactory.isTypeMatch(autowiredBeanName, paramTypes[ix])) {
                                 cachedMethodArguments[ix] = new ShortcutDependencyDescriptor(descriptors[ix], autowiredBeanName, paramTypes[ix]);
                              }
                           }
                        }

                        this.cachedMethodArguments = cachedMethodArguments;
                     } else {
                        this.cachedMethodArguments = null;
                     }

                     this.cached = true;
                  }
               }
            }

            if (arguments != null) {
               try {
                  ReflectionUtils.makeAccessible(method);
                  method.invoke(bean, arguments);
               } catch (InvocationTargetException var16) {
                  throw var16.getTargetException();
               }
            }

         }
      }

      @Nullable
      private Object[] resolveCachedArguments(@Nullable String beanName) {
         Object[] cachedMethodArguments = this.cachedMethodArguments;
         if (cachedMethodArguments == null) {
            return null;
         } else {
            Object[] arguments = new Object[cachedMethodArguments.length];

            for(int i = 0; i < arguments.length; ++i) {
               arguments[i] = AutowiredAnnotationBeanPostProcessor.this.resolvedCachedArgument(beanName, cachedMethodArguments[i]);
            }

            return arguments;
         }
      }
   }

   private class AutowiredFieldElement extends InjectionMetadata.InjectedElement {
      private final boolean required;
      private volatile boolean cached = false;
      @Nullable
      private volatile Object cachedFieldValue;

      public AutowiredFieldElement(Field field, boolean required) {
         super(field, (PropertyDescriptor)null);
         this.required = required;
      }

      protected void inject(Object bean, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
         Field field = (Field)this.member;
         Object value;
         if (this.cached) {
            value = AutowiredAnnotationBeanPostProcessor.this.resolvedCachedArgument(beanName, this.cachedFieldValue);
         } else {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            desc.setContainingClass(bean.getClass());
            Set autowiredBeanNames = new LinkedHashSet(1);
            Assert.state(AutowiredAnnotationBeanPostProcessor.this.beanFactory != null, "No BeanFactory available");
            TypeConverter typeConverter = AutowiredAnnotationBeanPostProcessor.this.beanFactory.getTypeConverter();

            try {
               value = AutowiredAnnotationBeanPostProcessor.this.beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);
            } catch (BeansException var12) {
               throw new UnsatisfiedDependencyException((String)null, beanName, new InjectionPoint(field), var12);
            }

            synchronized(this) {
               if (!this.cached) {
                  if (value == null && !this.required) {
                     this.cachedFieldValue = null;
                  } else {
                     this.cachedFieldValue = desc;
                     AutowiredAnnotationBeanPostProcessor.this.registerDependentBeans(beanName, autowiredBeanNames);
                     if (autowiredBeanNames.size() == 1) {
                        String autowiredBeanName = (String)autowiredBeanNames.iterator().next();
                        if (AutowiredAnnotationBeanPostProcessor.this.beanFactory.containsBean(autowiredBeanName) && AutowiredAnnotationBeanPostProcessor.this.beanFactory.isTypeMatch(autowiredBeanName, field.getType())) {
                           this.cachedFieldValue = new ShortcutDependencyDescriptor(desc, autowiredBeanName, field.getType());
                        }
                     }
                  }

                  this.cached = true;
               }
            }
         }

         if (value != null) {
            ReflectionUtils.makeAccessible(field);
            field.set(bean, value);
         }

      }
   }
}
