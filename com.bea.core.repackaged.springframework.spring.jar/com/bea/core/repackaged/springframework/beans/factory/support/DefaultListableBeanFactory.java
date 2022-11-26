package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCurrentlyInCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanNotOfRequiredTypeException;
import com.bea.core.repackaged.springframework.beans.factory.CannotLoadBeanClassException;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InjectionPoint;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.ObjectFactory;
import com.bea.core.repackaged.springframework.beans.factory.ObjectProvider;
import com.bea.core.repackaged.springframework.beans.factory.SmartFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;
import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.beans.factory.config.NamedBeanHolder;
import com.bea.core.repackaged.springframework.core.OrderComparator;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CompositeIterator;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.inject.Provider;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
   @Nullable
   private static Class javaxInjectProviderClass;
   private static final Map serializableFactories;
   @Nullable
   private String serializationId;
   private boolean allowBeanDefinitionOverriding = true;
   private boolean allowEagerClassLoading = true;
   @Nullable
   private Comparator dependencyComparator;
   private AutowireCandidateResolver autowireCandidateResolver = new SimpleAutowireCandidateResolver();
   private final Map resolvableDependencies = new ConcurrentHashMap(16);
   private final Map beanDefinitionMap = new ConcurrentHashMap(256);
   private final Map allBeanNamesByType = new ConcurrentHashMap(64);
   private final Map singletonBeanNamesByType = new ConcurrentHashMap(64);
   private volatile List beanDefinitionNames = new ArrayList(256);
   private volatile Set manualSingletonNames = new LinkedHashSet(16);
   @Nullable
   private volatile String[] frozenBeanDefinitionNames;
   private volatile boolean configurationFrozen = false;

   public DefaultListableBeanFactory() {
   }

   public DefaultListableBeanFactory(@Nullable BeanFactory parentBeanFactory) {
      super(parentBeanFactory);
   }

   public void setSerializationId(@Nullable String serializationId) {
      if (serializationId != null) {
         serializableFactories.put(serializationId, new WeakReference(this));
      } else if (this.serializationId != null) {
         serializableFactories.remove(this.serializationId);
      }

      this.serializationId = serializationId;
   }

   @Nullable
   public String getSerializationId() {
      return this.serializationId;
   }

   public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
      this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
   }

   public boolean isAllowBeanDefinitionOverriding() {
      return this.allowBeanDefinitionOverriding;
   }

   public void setAllowEagerClassLoading(boolean allowEagerClassLoading) {
      this.allowEagerClassLoading = allowEagerClassLoading;
   }

   public boolean isAllowEagerClassLoading() {
      return this.allowEagerClassLoading;
   }

   public void setDependencyComparator(@Nullable Comparator dependencyComparator) {
      this.dependencyComparator = dependencyComparator;
   }

   @Nullable
   public Comparator getDependencyComparator() {
      return this.dependencyComparator;
   }

   public void setAutowireCandidateResolver(AutowireCandidateResolver autowireCandidateResolver) {
      Assert.notNull(autowireCandidateResolver, (String)"AutowireCandidateResolver must not be null");
      if (autowireCandidateResolver instanceof BeanFactoryAware) {
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(() -> {
               ((BeanFactoryAware)autowireCandidateResolver).setBeanFactory(this);
               return null;
            }, this.getAccessControlContext());
         } else {
            ((BeanFactoryAware)autowireCandidateResolver).setBeanFactory(this);
         }
      }

      this.autowireCandidateResolver = autowireCandidateResolver;
   }

   public AutowireCandidateResolver getAutowireCandidateResolver() {
      return this.autowireCandidateResolver;
   }

   public void copyConfigurationFrom(ConfigurableBeanFactory otherFactory) {
      super.copyConfigurationFrom(otherFactory);
      if (otherFactory instanceof DefaultListableBeanFactory) {
         DefaultListableBeanFactory otherListableFactory = (DefaultListableBeanFactory)otherFactory;
         this.allowBeanDefinitionOverriding = otherListableFactory.allowBeanDefinitionOverriding;
         this.allowEagerClassLoading = otherListableFactory.allowEagerClassLoading;
         this.dependencyComparator = otherListableFactory.dependencyComparator;
         this.setAutowireCandidateResolver((AutowireCandidateResolver)BeanUtils.instantiateClass(otherListableFactory.getAutowireCandidateResolver().getClass()));
         this.resolvableDependencies.putAll(otherListableFactory.resolvableDependencies);
      }

   }

   public Object getBean(Class requiredType) throws BeansException {
      return this.getBean(requiredType, (Object[])null);
   }

   public Object getBean(Class requiredType, @Nullable Object... args) throws BeansException {
      Assert.notNull(requiredType, (String)"Required type must not be null");
      Object resolved = this.resolveBean(ResolvableType.forRawClass(requiredType), args, false);
      if (resolved == null) {
         throw new NoSuchBeanDefinitionException(requiredType);
      } else {
         return resolved;
      }
   }

   public ObjectProvider getBeanProvider(Class requiredType) throws BeansException {
      Assert.notNull(requiredType, (String)"Required type must not be null");
      return this.getBeanProvider(ResolvableType.forRawClass(requiredType));
   }

   public ObjectProvider getBeanProvider(final ResolvableType requiredType) {
      return new BeanObjectProvider() {
         public Object getObject() throws BeansException {
            Object resolved = DefaultListableBeanFactory.this.resolveBean(requiredType, (Object[])null, false);
            if (resolved == null) {
               throw new NoSuchBeanDefinitionException(requiredType);
            } else {
               return resolved;
            }
         }

         public Object getObject(Object... args) throws BeansException {
            Object resolved = DefaultListableBeanFactory.this.resolveBean(requiredType, args, false);
            if (resolved == null) {
               throw new NoSuchBeanDefinitionException(requiredType);
            } else {
               return resolved;
            }
         }

         @Nullable
         public Object getIfAvailable() throws BeansException {
            return DefaultListableBeanFactory.this.resolveBean(requiredType, (Object[])null, false);
         }

         @Nullable
         public Object getIfUnique() throws BeansException {
            return DefaultListableBeanFactory.this.resolveBean(requiredType, (Object[])null, true);
         }

         public Stream stream() {
            return Arrays.stream(DefaultListableBeanFactory.this.getBeanNamesForTypedStream(requiredType)).map((name) -> {
               return DefaultListableBeanFactory.this.getBean(name);
            }).filter((bean) -> {
               return !(bean instanceof NullBean);
            });
         }

         public Stream orderedStream() {
            String[] beanNames = DefaultListableBeanFactory.this.getBeanNamesForTypedStream(requiredType);
            Map matchingBeans = new LinkedHashMap(beanNames.length);
            String[] var3 = beanNames;
            int var4 = beanNames.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String beanName = var3[var5];
               Object beanInstance = DefaultListableBeanFactory.this.getBean(beanName);
               if (!(beanInstance instanceof NullBean)) {
                  matchingBeans.put(beanName, beanInstance);
               }
            }

            Stream stream = matchingBeans.values().stream();
            return stream.sorted(DefaultListableBeanFactory.this.adaptOrderComparator(matchingBeans));
         }
      };
   }

   @Nullable
   private Object resolveBean(ResolvableType requiredType, @Nullable Object[] args, boolean nonUniqueAsNull) {
      NamedBeanHolder namedBean = this.resolveNamedBean(requiredType, args, nonUniqueAsNull);
      if (namedBean != null) {
         return namedBean.getBeanInstance();
      } else {
         BeanFactory parent = this.getParentBeanFactory();
         if (parent instanceof DefaultListableBeanFactory) {
            return ((DefaultListableBeanFactory)parent).resolveBean(requiredType, args, nonUniqueAsNull);
         } else if (parent != null) {
            ObjectProvider parentProvider = parent.getBeanProvider(requiredType);
            if (args != null) {
               return parentProvider.getObject(args);
            } else {
               return nonUniqueAsNull ? parentProvider.getIfUnique() : parentProvider.getIfAvailable();
            }
         } else {
            return null;
         }
      }
   }

   private String[] getBeanNamesForTypedStream(ResolvableType requiredType) {
      return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this, (ResolvableType)requiredType);
   }

   public boolean containsBeanDefinition(String beanName) {
      Assert.notNull(beanName, (String)"Bean name must not be null");
      return this.beanDefinitionMap.containsKey(beanName);
   }

   public int getBeanDefinitionCount() {
      return this.beanDefinitionMap.size();
   }

   public String[] getBeanDefinitionNames() {
      String[] frozenNames = this.frozenBeanDefinitionNames;
      return frozenNames != null ? (String[])frozenNames.clone() : StringUtils.toStringArray((Collection)this.beanDefinitionNames);
   }

   public String[] getBeanNamesForType(ResolvableType type) {
      Class resolved = type.resolve();
      return resolved != null && !type.hasGenerics() ? this.getBeanNamesForType(resolved, true, true) : this.doGetBeanNamesForType(type, true, true);
   }

   public String[] getBeanNamesForType(@Nullable Class type) {
      return this.getBeanNamesForType(type, true, true);
   }

   public String[] getBeanNamesForType(@Nullable Class type, boolean includeNonSingletons, boolean allowEagerInit) {
      if (this.isConfigurationFrozen() && type != null && allowEagerInit) {
         Map cache = includeNonSingletons ? this.allBeanNamesByType : this.singletonBeanNamesByType;
         String[] resolvedBeanNames = (String[])cache.get(type);
         if (resolvedBeanNames != null) {
            return resolvedBeanNames;
         } else {
            resolvedBeanNames = this.doGetBeanNamesForType(ResolvableType.forRawClass(type), includeNonSingletons, true);
            if (ClassUtils.isCacheSafe(type, this.getBeanClassLoader())) {
               cache.put(type, resolvedBeanNames);
            }

            return resolvedBeanNames;
         }
      } else {
         return this.doGetBeanNamesForType(ResolvableType.forRawClass(type), includeNonSingletons, allowEagerInit);
      }
   }

   private String[] doGetBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {
      List result = new ArrayList();
      Iterator var5 = this.beanDefinitionNames.iterator();

      while(true) {
         String beanName;
         do {
            if (!var5.hasNext()) {
               var5 = this.manualSingletonNames.iterator();

               while(var5.hasNext()) {
                  beanName = (String)var5.next();

                  try {
                     if (this.isFactoryBean(beanName)) {
                        if ((includeNonSingletons || this.isSingleton(beanName)) && this.isTypeMatch(beanName, type)) {
                           result.add(beanName);
                           continue;
                        }

                        beanName = "&" + beanName;
                     }

                     if (this.isTypeMatch(beanName, type)) {
                        result.add(beanName);
                     }
                  } catch (NoSuchBeanDefinitionException var11) {
                     if (this.logger.isTraceEnabled()) {
                        this.logger.trace("Failed to check manually registered singleton with name '" + beanName + "'", var11);
                     }
                  }
               }

               return StringUtils.toStringArray((Collection)result);
            }

            beanName = (String)var5.next();
         } while(this.isAlias(beanName));

         try {
            RootBeanDefinition mbd = this.getMergedLocalBeanDefinition(beanName);
            if (!mbd.isAbstract() && (allowEagerInit || (mbd.hasBeanClass() || !mbd.isLazyInit() || this.isAllowEagerClassLoading()) && !this.requiresEagerInitForType(mbd.getFactoryBeanName()))) {
               boolean isFactoryBean;
               boolean var10000;
               label161: {
                  isFactoryBean = this.isFactoryBean(beanName, mbd);
                  BeanDefinitionHolder dbd = mbd.getDecoratedDefinition();
                  if (allowEagerInit || !isFactoryBean || dbd != null && !mbd.isLazyInit() || this.containsSingleton(beanName)) {
                     label157: {
                        if (!includeNonSingletons) {
                           if (dbd != null) {
                              if (!mbd.isSingleton()) {
                                 break label157;
                              }
                           } else if (!this.isSingleton(beanName)) {
                              break label157;
                           }
                        }

                        if (this.isTypeMatch(beanName, type)) {
                           var10000 = true;
                           break label161;
                        }
                     }
                  }

                  var10000 = false;
               }

               boolean matchFound = var10000;
               if (!matchFound && isFactoryBean) {
                  beanName = "&" + beanName;
                  matchFound = (includeNonSingletons || mbd.isSingleton()) && this.isTypeMatch(beanName, type);
               }

               if (matchFound) {
                  result.add(beanName);
               }
            }
         } catch (CannotLoadBeanClassException var12) {
            if (allowEagerInit) {
               throw var12;
            }

            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Ignoring bean class loading failure for bean '" + beanName + "'", var12);
            }

            this.onSuppressedException(var12);
         } catch (BeanDefinitionStoreException var13) {
            if (allowEagerInit) {
               throw var13;
            }

            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Ignoring unresolvable metadata in bean definition '" + beanName + "'", var13);
            }

            this.onSuppressedException(var13);
         }
      }
   }

   private boolean requiresEagerInitForType(@Nullable String factoryBeanName) {
      return factoryBeanName != null && this.isFactoryBean(factoryBeanName) && !this.containsSingleton(factoryBeanName);
   }

   public Map getBeansOfType(@Nullable Class type) throws BeansException {
      return this.getBeansOfType(type, true, true);
   }

   public Map getBeansOfType(@Nullable Class type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
      String[] beanNames = this.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
      Map result = new LinkedHashMap(beanNames.length);
      String[] var6 = beanNames;
      int var7 = beanNames.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String beanName = var6[var8];

         try {
            Object beanInstance = this.getBean(beanName);
            if (!(beanInstance instanceof NullBean)) {
               result.put(beanName, beanInstance);
            }
         } catch (BeanCreationException var14) {
            Throwable rootCause = var14.getMostSpecificCause();
            if (rootCause instanceof BeanCurrentlyInCreationException) {
               BeanCreationException bce = (BeanCreationException)rootCause;
               String exBeanName = bce.getBeanName();
               if (exBeanName != null && this.isCurrentlyInCreation(exBeanName)) {
                  if (this.logger.isTraceEnabled()) {
                     this.logger.trace("Ignoring match to currently created bean '" + exBeanName + "': " + var14.getMessage());
                  }

                  this.onSuppressedException(var14);
                  continue;
               }
            }

            throw var14;
         }
      }

      return result;
   }

   public String[] getBeanNamesForAnnotation(Class annotationType) {
      List result = new ArrayList();
      Iterator var3 = this.beanDefinitionNames.iterator();

      String beanName;
      while(var3.hasNext()) {
         beanName = (String)var3.next();
         BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
         if (!beanDefinition.isAbstract() && this.findAnnotationOnBean(beanName, annotationType) != null) {
            result.add(beanName);
         }
      }

      var3 = this.manualSingletonNames.iterator();

      while(var3.hasNext()) {
         beanName = (String)var3.next();
         if (!result.contains(beanName) && this.findAnnotationOnBean(beanName, annotationType) != null) {
            result.add(beanName);
         }
      }

      return StringUtils.toStringArray((Collection)result);
   }

   public Map getBeansWithAnnotation(Class annotationType) {
      String[] beanNames = this.getBeanNamesForAnnotation(annotationType);
      Map result = new LinkedHashMap(beanNames.length);
      String[] var4 = beanNames;
      int var5 = beanNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String beanName = var4[var6];
         Object beanInstance = this.getBean(beanName);
         if (!(beanInstance instanceof NullBean)) {
            result.put(beanName, beanInstance);
         }
      }

      return result;
   }

   @Nullable
   public Annotation findAnnotationOnBean(String beanName, Class annotationType) throws NoSuchBeanDefinitionException {
      Annotation ann = null;
      Class beanType = this.getType(beanName);
      if (beanType != null) {
         ann = AnnotationUtils.findAnnotation(beanType, annotationType);
      }

      if (ann == null && this.containsBeanDefinition(beanName)) {
         RootBeanDefinition bd = this.getMergedLocalBeanDefinition(beanName);
         if (bd.hasBeanClass()) {
            Class beanClass = bd.getBeanClass();
            if (beanClass != beanType) {
               ann = AnnotationUtils.findAnnotation(beanClass, annotationType);
            }
         }
      }

      return ann;
   }

   public void registerResolvableDependency(Class dependencyType, @Nullable Object autowiredValue) {
      Assert.notNull(dependencyType, (String)"Dependency type must not be null");
      if (autowiredValue != null) {
         if (!(autowiredValue instanceof ObjectFactory) && !dependencyType.isInstance(autowiredValue)) {
            throw new IllegalArgumentException("Value [" + autowiredValue + "] does not implement specified dependency type [" + dependencyType.getName() + "]");
         }

         this.resolvableDependencies.put(dependencyType, autowiredValue);
      }

   }

   public boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor) throws NoSuchBeanDefinitionException {
      return this.isAutowireCandidate(beanName, descriptor, this.getAutowireCandidateResolver());
   }

   protected boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor, AutowireCandidateResolver resolver) throws NoSuchBeanDefinitionException {
      String beanDefinitionName = BeanFactoryUtils.transformedBeanName(beanName);
      if (this.containsBeanDefinition(beanDefinitionName)) {
         return this.isAutowireCandidate(beanName, this.getMergedLocalBeanDefinition(beanDefinitionName), descriptor, resolver);
      } else if (this.containsSingleton(beanName)) {
         return this.isAutowireCandidate(beanName, new RootBeanDefinition(this.getType(beanName)), descriptor, resolver);
      } else {
         BeanFactory parent = this.getParentBeanFactory();
         if (parent instanceof DefaultListableBeanFactory) {
            return ((DefaultListableBeanFactory)parent).isAutowireCandidate(beanName, descriptor, resolver);
         } else {
            return parent instanceof ConfigurableListableBeanFactory ? ((ConfigurableListableBeanFactory)parent).isAutowireCandidate(beanName, descriptor) : true;
         }
      }
   }

   protected boolean isAutowireCandidate(String beanName, RootBeanDefinition mbd, DependencyDescriptor descriptor, AutowireCandidateResolver resolver) {
      String beanDefinitionName = BeanFactoryUtils.transformedBeanName(beanName);
      this.resolveBeanClass(mbd, beanDefinitionName, new Class[0]);
      if (mbd.isFactoryMethodUnique && mbd.factoryMethodToIntrospect == null) {
         (new ConstructorResolver(this)).resolveFactoryMethodIfPossible(mbd);
      }

      return resolver.isAutowireCandidate(new BeanDefinitionHolder(mbd, beanName, this.getAliases(beanDefinitionName)), descriptor);
   }

   public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
      BeanDefinition bd = (BeanDefinition)this.beanDefinitionMap.get(beanName);
      if (bd == null) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("No bean named '" + beanName + "' found in " + this);
         }

         throw new NoSuchBeanDefinitionException(beanName);
      } else {
         return bd;
      }
   }

   public Iterator getBeanNamesIterator() {
      CompositeIterator iterator = new CompositeIterator();
      iterator.add(this.beanDefinitionNames.iterator());
      iterator.add(this.manualSingletonNames.iterator());
      return iterator;
   }

   public void clearMetadataCache() {
      super.clearMetadataCache();
      this.clearByTypeCache();
   }

   public void freezeConfiguration() {
      this.configurationFrozen = true;
      this.frozenBeanDefinitionNames = StringUtils.toStringArray((Collection)this.beanDefinitionNames);
   }

   public boolean isConfigurationFrozen() {
      return this.configurationFrozen;
   }

   protected boolean isBeanEligibleForMetadataCaching(String beanName) {
      return this.configurationFrozen || super.isBeanEligibleForMetadataCaching(beanName);
   }

   public void preInstantiateSingletons() throws BeansException {
      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Pre-instantiating singletons in " + this);
      }

      List beanNames = new ArrayList(this.beanDefinitionNames);
      Iterator var2 = beanNames.iterator();

      while(true) {
         String beanName;
         Object bean;
         do {
            while(true) {
               RootBeanDefinition bd;
               do {
                  do {
                     do {
                        if (!var2.hasNext()) {
                           var2 = beanNames.iterator();

                           while(var2.hasNext()) {
                              beanName = (String)var2.next();
                              Object singletonInstance = this.getSingleton(beanName);
                              if (singletonInstance instanceof SmartInitializingSingleton) {
                                 SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton)singletonInstance;
                                 if (System.getSecurityManager() != null) {
                                    AccessController.doPrivileged(() -> {
                                       smartSingleton.afterSingletonsInstantiated();
                                       return null;
                                    }, this.getAccessControlContext());
                                 } else {
                                    smartSingleton.afterSingletonsInstantiated();
                                 }
                              }
                           }

                           return;
                        }

                        beanName = (String)var2.next();
                        bd = this.getMergedLocalBeanDefinition(beanName);
                     } while(bd.isAbstract());
                  } while(!bd.isSingleton());
               } while(bd.isLazyInit());

               if (this.isFactoryBean(beanName)) {
                  bean = this.getBean("&" + beanName);
                  break;
               }

               this.getBean(beanName);
            }
         } while(!(bean instanceof FactoryBean));

         FactoryBean factory = (FactoryBean)bean;
         boolean isEagerInit;
         if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
            SmartFactoryBean var10000 = (SmartFactoryBean)factory;
            ((SmartFactoryBean)factory).getClass();
            isEagerInit = (Boolean)AccessController.doPrivileged(var10000::isEagerInit, this.getAccessControlContext());
         } else {
            isEagerInit = factory instanceof SmartFactoryBean && ((SmartFactoryBean)factory).isEagerInit();
         }

         if (isEagerInit) {
            this.getBean(beanName);
         }
      }
   }

   public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
      Assert.hasText(beanName, "Bean name must not be empty");
      Assert.notNull(beanDefinition, (String)"BeanDefinition must not be null");
      if (beanDefinition instanceof AbstractBeanDefinition) {
         try {
            ((AbstractBeanDefinition)beanDefinition).validate();
         } catch (BeanDefinitionValidationException var8) {
            throw new BeanDefinitionStoreException(beanDefinition.getResourceDescription(), beanName, "Validation of bean definition failed", var8);
         }
      }

      BeanDefinition existingDefinition = (BeanDefinition)this.beanDefinitionMap.get(beanName);
      if (existingDefinition != null) {
         if (!this.isAllowBeanDefinitionOverriding()) {
            throw new BeanDefinitionOverrideException(beanName, beanDefinition, existingDefinition);
         }

         if (existingDefinition.getRole() < beanDefinition.getRole()) {
            if (this.logger.isInfoEnabled()) {
               this.logger.info("Overriding user-defined bean definition for bean '" + beanName + "' with a framework-generated bean definition: replacing [" + existingDefinition + "] with [" + beanDefinition + "]");
            }
         } else if (!beanDefinition.equals(existingDefinition)) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Overriding bean definition for bean '" + beanName + "' with a different definition: replacing [" + existingDefinition + "] with [" + beanDefinition + "]");
            }
         } else if (this.logger.isTraceEnabled()) {
            this.logger.trace("Overriding bean definition for bean '" + beanName + "' with an equivalent definition: replacing [" + existingDefinition + "] with [" + beanDefinition + "]");
         }

         this.beanDefinitionMap.put(beanName, beanDefinition);
      } else {
         if (this.hasBeanCreationStarted()) {
            synchronized(this.beanDefinitionMap) {
               this.beanDefinitionMap.put(beanName, beanDefinition);
               List updatedDefinitions = new ArrayList(this.beanDefinitionNames.size() + 1);
               updatedDefinitions.addAll(this.beanDefinitionNames);
               updatedDefinitions.add(beanName);
               this.beanDefinitionNames = updatedDefinitions;
               this.removeManualSingletonName(beanName);
            }
         } else {
            this.beanDefinitionMap.put(beanName, beanDefinition);
            this.beanDefinitionNames.add(beanName);
            this.removeManualSingletonName(beanName);
         }

         this.frozenBeanDefinitionNames = null;
      }

      if (existingDefinition != null || this.containsSingleton(beanName)) {
         this.resetBeanDefinition(beanName);
      }

   }

   public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
      Assert.hasText(beanName, "'beanName' must not be empty");
      BeanDefinition bd = (BeanDefinition)this.beanDefinitionMap.remove(beanName);
      if (bd == null) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("No bean named '" + beanName + "' found in " + this);
         }

         throw new NoSuchBeanDefinitionException(beanName);
      } else {
         if (this.hasBeanCreationStarted()) {
            synchronized(this.beanDefinitionMap) {
               List updatedDefinitions = new ArrayList(this.beanDefinitionNames);
               updatedDefinitions.remove(beanName);
               this.beanDefinitionNames = updatedDefinitions;
            }
         } else {
            this.beanDefinitionNames.remove(beanName);
         }

         this.frozenBeanDefinitionNames = null;
         this.resetBeanDefinition(beanName);
      }
   }

   protected void resetBeanDefinition(String beanName) {
      this.clearMergedBeanDefinition(beanName);
      this.destroySingleton(beanName);
      Iterator var2 = this.getBeanPostProcessors().iterator();

      while(var2.hasNext()) {
         BeanPostProcessor processor = (BeanPostProcessor)var2.next();
         if (processor instanceof MergedBeanDefinitionPostProcessor) {
            ((MergedBeanDefinitionPostProcessor)processor).resetBeanDefinition(beanName);
         }
      }

      var2 = this.beanDefinitionNames.iterator();

      while(var2.hasNext()) {
         String bdName = (String)var2.next();
         if (!beanName.equals(bdName)) {
            BeanDefinition bd = (BeanDefinition)this.beanDefinitionMap.get(bdName);
            if (bd != null && beanName.equals(bd.getParentName())) {
               this.resetBeanDefinition(bdName);
            }
         }
      }

   }

   protected boolean allowAliasOverriding() {
      return this.isAllowBeanDefinitionOverriding();
   }

   public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
      super.registerSingleton(beanName, singletonObject);
      this.updateManualSingletonNames((set) -> {
         set.add(beanName);
      }, (set) -> {
         return !this.beanDefinitionMap.containsKey(beanName);
      });
      this.clearByTypeCache();
   }

   public void destroySingletons() {
      super.destroySingletons();
      this.updateManualSingletonNames(Set::clear, (set) -> {
         return !set.isEmpty();
      });
      this.clearByTypeCache();
   }

   public void destroySingleton(String beanName) {
      super.destroySingleton(beanName);
      this.removeManualSingletonName(beanName);
      this.clearByTypeCache();
   }

   private void removeManualSingletonName(String beanName) {
      this.updateManualSingletonNames((set) -> {
         set.remove(beanName);
      }, (set) -> {
         return set.contains(beanName);
      });
   }

   private void updateManualSingletonNames(Consumer action, Predicate condition) {
      if (this.hasBeanCreationStarted()) {
         synchronized(this.beanDefinitionMap) {
            if (condition.test(this.manualSingletonNames)) {
               Set updatedSingletons = new LinkedHashSet(this.manualSingletonNames);
               action.accept(updatedSingletons);
               this.manualSingletonNames = updatedSingletons;
            }
         }
      } else if (condition.test(this.manualSingletonNames)) {
         action.accept(this.manualSingletonNames);
      }

   }

   private void clearByTypeCache() {
      this.allBeanNamesByType.clear();
      this.singletonBeanNamesByType.clear();
   }

   public NamedBeanHolder resolveNamedBean(Class requiredType) throws BeansException {
      Assert.notNull(requiredType, (String)"Required type must not be null");
      NamedBeanHolder namedBean = this.resolveNamedBean(ResolvableType.forRawClass(requiredType), (Object[])null, false);
      if (namedBean != null) {
         return namedBean;
      } else {
         BeanFactory parent = this.getParentBeanFactory();
         if (parent instanceof AutowireCapableBeanFactory) {
            return ((AutowireCapableBeanFactory)parent).resolveNamedBean(requiredType);
         } else {
            throw new NoSuchBeanDefinitionException(requiredType);
         }
      }
   }

   @Nullable
   private NamedBeanHolder resolveNamedBean(ResolvableType requiredType, @Nullable Object[] args, boolean nonUniqueAsNull) throws BeansException {
      Assert.notNull(requiredType, (String)"Required type must not be null");
      String[] candidateNames = this.getBeanNamesForType(requiredType);
      String[] var6;
      int var7;
      int var8;
      String beanName;
      if (candidateNames.length > 1) {
         List autowireCandidates = new ArrayList(candidateNames.length);
         var6 = candidateNames;
         var7 = candidateNames.length;

         for(var8 = 0; var8 < var7; ++var8) {
            beanName = var6[var8];
            if (!this.containsBeanDefinition(beanName) || this.getBeanDefinition(beanName).isAutowireCandidate()) {
               autowireCandidates.add(beanName);
            }
         }

         if (!autowireCandidates.isEmpty()) {
            candidateNames = StringUtils.toStringArray((Collection)autowireCandidates);
         }
      }

      if (candidateNames.length == 1) {
         String beanName = candidateNames[0];
         return new NamedBeanHolder(beanName, this.getBean(beanName, requiredType.toClass(), args));
      } else {
         if (candidateNames.length > 1) {
            Map candidates = new LinkedHashMap(candidateNames.length);
            var6 = candidateNames;
            var7 = candidateNames.length;

            for(var8 = 0; var8 < var7; ++var8) {
               beanName = var6[var8];
               if (this.containsSingleton(beanName) && args == null) {
                  Object beanInstance = this.getBean(beanName);
                  candidates.put(beanName, beanInstance instanceof NullBean ? null : beanInstance);
               } else {
                  candidates.put(beanName, this.getType(beanName));
               }
            }

            String candidateName = this.determinePrimaryCandidate(candidates, requiredType.toClass());
            if (candidateName == null) {
               candidateName = this.determineHighestPriorityCandidate(candidates, requiredType.toClass());
            }

            if (candidateName != null) {
               Object beanInstance = candidates.get(candidateName);
               if (beanInstance == null || beanInstance instanceof Class) {
                  beanInstance = this.getBean(candidateName, requiredType.toClass(), args);
               }

               return new NamedBeanHolder(candidateName, beanInstance);
            }

            if (!nonUniqueAsNull) {
               throw new NoUniqueBeanDefinitionException(requiredType, candidates.keySet());
            }
         }

         return null;
      }
   }

   @Nullable
   public Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName, @Nullable Set autowiredBeanNames, @Nullable TypeConverter typeConverter) throws BeansException {
      descriptor.initParameterNameDiscovery(this.getParameterNameDiscoverer());
      if (Optional.class == descriptor.getDependencyType()) {
         return this.createOptionalDependency(descriptor, requestingBeanName);
      } else if (ObjectFactory.class != descriptor.getDependencyType() && ObjectProvider.class != descriptor.getDependencyType()) {
         if (javaxInjectProviderClass == descriptor.getDependencyType()) {
            return (new Jsr330Factory()).createDependencyProvider(descriptor, requestingBeanName);
         } else {
            Object result = this.getAutowireCandidateResolver().getLazyResolutionProxyIfNecessary(descriptor, requestingBeanName);
            if (result == null) {
               result = this.doResolveDependency(descriptor, requestingBeanName, autowiredBeanNames, typeConverter);
            }

            return result;
         }
      } else {
         return new DependencyObjectProvider(descriptor, requestingBeanName);
      }
   }

   @Nullable
   public Object doResolveDependency(DependencyDescriptor descriptor, @Nullable String beanName, @Nullable Set autowiredBeanNames, @Nullable TypeConverter typeConverter) throws BeansException {
      InjectionPoint previousInjectionPoint = ConstructorResolver.setCurrentInjectionPoint(descriptor);

      Object var14;
      try {
         Object shortcut = descriptor.resolveShortcut(this);
         if (shortcut != null) {
            Object var20 = shortcut;
            return var20;
         }

         Class type = descriptor.getDependencyType();
         Object value = this.getAutowireCandidateResolver().getSuggestedValue(descriptor);
         Object var23;
         if (value != null) {
            if (value instanceof String) {
               String strVal = this.resolveEmbeddedValue((String)value);
               BeanDefinition bd = beanName != null && this.containsBean(beanName) ? this.getMergedBeanDefinition(beanName) : null;
               value = this.evaluateBeanDefinitionString(strVal, bd);
            }

            TypeConverter converter = typeConverter != null ? typeConverter : this.getTypeConverter();

            try {
               var23 = converter.convertIfNecessary(value, type, descriptor.getTypeDescriptor());
               return var23;
            } catch (UnsupportedOperationException var18) {
               Object var25 = descriptor.getField() != null ? converter.convertIfNecessary(value, type, descriptor.getField()) : converter.convertIfNecessary(value, type, descriptor.getMethodParameter());
               return var25;
            }
         }

         Object multipleBeans = this.resolveMultipleBeans(descriptor, beanName, autowiredBeanNames, typeConverter);
         if (multipleBeans != null) {
            var23 = multipleBeans;
            return var23;
         }

         Map matchingBeans = this.findAutowireCandidates(beanName, type, descriptor);
         String autowiredBeanName;
         if (matchingBeans.isEmpty()) {
            if (this.isRequired(descriptor)) {
               this.raiseNoMatchingBeanFound(type, descriptor.getResolvableType(), descriptor);
            }

            autowiredBeanName = null;
            return autowiredBeanName;
         }

         Object instanceCandidate;
         Object result;
         if (matchingBeans.size() > 1) {
            autowiredBeanName = this.determineAutowireCandidate(matchingBeans, descriptor);
            if (autowiredBeanName == null) {
               if (!this.isRequired(descriptor) && this.indicatesMultipleBeans(type)) {
                  result = null;
                  return result;
               }

               result = descriptor.resolveNotUnique(descriptor.getResolvableType(), matchingBeans);
               return result;
            }

            instanceCandidate = matchingBeans.get(autowiredBeanName);
         } else {
            Map.Entry entry = (Map.Entry)matchingBeans.entrySet().iterator().next();
            autowiredBeanName = (String)entry.getKey();
            instanceCandidate = entry.getValue();
         }

         if (autowiredBeanNames != null) {
            autowiredBeanNames.add(autowiredBeanName);
         }

         if (instanceCandidate instanceof Class) {
            instanceCandidate = descriptor.resolveCandidate(autowiredBeanName, type, this);
         }

         result = instanceCandidate;
         if (instanceCandidate instanceof NullBean) {
            if (this.isRequired(descriptor)) {
               this.raiseNoMatchingBeanFound(type, descriptor.getResolvableType(), descriptor);
            }

            result = null;
         }

         if (!ClassUtils.isAssignableValue(type, result)) {
            throw new BeanNotOfRequiredTypeException(autowiredBeanName, type, instanceCandidate.getClass());
         }

         var14 = result;
      } finally {
         ConstructorResolver.setCurrentInjectionPoint(previousInjectionPoint);
      }

      return var14;
   }

   @Nullable
   private Object resolveMultipleBeans(DependencyDescriptor descriptor, @Nullable String beanName, @Nullable Set autowiredBeanNames, @Nullable TypeConverter typeConverter) {
      Class type = descriptor.getDependencyType();
      if (descriptor instanceof StreamDependencyDescriptor) {
         Map matchingBeans = this.findAutowireCandidates(beanName, type, descriptor);
         if (autowiredBeanNames != null) {
            autowiredBeanNames.addAll(matchingBeans.keySet());
         }

         Stream stream = matchingBeans.keySet().stream().map((name) -> {
            return descriptor.resolveCandidate(name, type, this);
         }).filter((bean) -> {
            return !(bean instanceof NullBean);
         });
         if (((StreamDependencyDescriptor)descriptor).isOrdered()) {
            stream = stream.sorted(this.adaptOrderComparator(matchingBeans));
         }

         return stream;
      } else {
         Class valueType;
         Map matchingBeans;
         Class elementType;
         if (type.isArray()) {
            elementType = type.getComponentType();
            ResolvableType resolvableType = descriptor.getResolvableType();
            valueType = resolvableType.resolve(type);
            if (valueType != type) {
               elementType = resolvableType.getComponentType().resolve();
            }

            if (elementType == null) {
               return null;
            } else {
               matchingBeans = this.findAutowireCandidates(beanName, elementType, new MultiElementDescriptor(descriptor));
               if (matchingBeans.isEmpty()) {
                  return null;
               } else {
                  if (autowiredBeanNames != null) {
                     autowiredBeanNames.addAll(matchingBeans.keySet());
                  }

                  TypeConverter converter = typeConverter != null ? typeConverter : this.getTypeConverter();
                  Object result = converter.convertIfNecessary(matchingBeans.values(), valueType);
                  if (result instanceof Object[]) {
                     Comparator comparator = this.adaptDependencyComparator(matchingBeans);
                     if (comparator != null) {
                        Arrays.sort((Object[])((Object[])result), comparator);
                     }
                  }

                  return result;
               }
            }
         } else if (Collection.class.isAssignableFrom(type) && type.isInterface()) {
            elementType = descriptor.getResolvableType().asCollection().resolveGeneric();
            if (elementType == null) {
               return null;
            } else {
               Map matchingBeans = this.findAutowireCandidates(beanName, elementType, new MultiElementDescriptor(descriptor));
               if (matchingBeans.isEmpty()) {
                  return null;
               } else {
                  if (autowiredBeanNames != null) {
                     autowiredBeanNames.addAll(matchingBeans.keySet());
                  }

                  TypeConverter converter = typeConverter != null ? typeConverter : this.getTypeConverter();
                  Object result = converter.convertIfNecessary(matchingBeans.values(), type);
                  if (result instanceof List) {
                     Comparator comparator = this.adaptDependencyComparator(matchingBeans);
                     if (comparator != null) {
                        ((List)result).sort(comparator);
                     }
                  }

                  return result;
               }
            }
         } else if (Map.class == type) {
            ResolvableType mapType = descriptor.getResolvableType().asMap();
            Class keyType = mapType.resolveGeneric(0);
            if (String.class != keyType) {
               return null;
            } else {
               valueType = mapType.resolveGeneric(1);
               if (valueType == null) {
                  return null;
               } else {
                  matchingBeans = this.findAutowireCandidates(beanName, valueType, new MultiElementDescriptor(descriptor));
                  if (matchingBeans.isEmpty()) {
                     return null;
                  } else {
                     if (autowiredBeanNames != null) {
                        autowiredBeanNames.addAll(matchingBeans.keySet());
                     }

                     return matchingBeans;
                  }
               }
            }
         } else {
            return null;
         }
      }
   }

   private boolean isRequired(DependencyDescriptor descriptor) {
      return this.getAutowireCandidateResolver().isRequired(descriptor);
   }

   private boolean indicatesMultipleBeans(Class type) {
      return type.isArray() || type.isInterface() && (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type));
   }

   @Nullable
   private Comparator adaptDependencyComparator(Map matchingBeans) {
      Comparator comparator = this.getDependencyComparator();
      return comparator instanceof OrderComparator ? ((OrderComparator)comparator).withSourceProvider(this.createFactoryAwareOrderSourceProvider(matchingBeans)) : comparator;
   }

   private Comparator adaptOrderComparator(Map matchingBeans) {
      Comparator dependencyComparator = this.getDependencyComparator();
      OrderComparator comparator = dependencyComparator instanceof OrderComparator ? (OrderComparator)dependencyComparator : OrderComparator.INSTANCE;
      return comparator.withSourceProvider(this.createFactoryAwareOrderSourceProvider(matchingBeans));
   }

   private OrderComparator.OrderSourceProvider createFactoryAwareOrderSourceProvider(Map beans) {
      IdentityHashMap instancesToBeanNames = new IdentityHashMap();
      beans.forEach((beanName, instance) -> {
         String var10000 = (String)instancesToBeanNames.put(instance, beanName);
      });
      return new FactoryAwareOrderSourceProvider(instancesToBeanNames);
   }

   protected Map findAutowireCandidates(@Nullable String beanName, Class requiredType, DependencyDescriptor descriptor) {
      String[] candidateNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this, requiredType, true, descriptor.isEager());
      Map result = new LinkedHashMap(candidateNames.length);
      Iterator var6 = this.resolvableDependencies.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry classObjectEntry = (Map.Entry)var6.next();
         Class autowiringType = (Class)classObjectEntry.getKey();
         if (autowiringType.isAssignableFrom(requiredType)) {
            Object autowiringValue = classObjectEntry.getValue();
            autowiringValue = AutowireUtils.resolveAutowiringValue(autowiringValue, requiredType);
            if (requiredType.isInstance(autowiringValue)) {
               result.put(ObjectUtils.identityToString(autowiringValue), autowiringValue);
               break;
            }
         }
      }

      String[] var12 = candidateNames;
      int var14 = candidateNames.length;

      for(int var16 = 0; var16 < var14; ++var16) {
         String candidate = var12[var16];
         if (!this.isSelfReference(beanName, candidate) && this.isAutowireCandidate(candidate, descriptor)) {
            this.addCandidateEntry(result, candidate, descriptor, requiredType);
         }
      }

      if (result.isEmpty()) {
         boolean multiple = this.indicatesMultipleBeans(requiredType);
         DependencyDescriptor fallbackDescriptor = descriptor.forFallbackMatch();
         String[] var17 = candidateNames;
         int var19 = candidateNames.length;

         int var10;
         String candidate;
         for(var10 = 0; var10 < var19; ++var10) {
            candidate = var17[var10];
            if (!this.isSelfReference(beanName, candidate) && this.isAutowireCandidate(candidate, fallbackDescriptor) && (!multiple || this.getAutowireCandidateResolver().hasQualifier(descriptor))) {
               this.addCandidateEntry(result, candidate, descriptor, requiredType);
            }
         }

         if (result.isEmpty() && !multiple) {
            var17 = candidateNames;
            var19 = candidateNames.length;

            for(var10 = 0; var10 < var19; ++var10) {
               candidate = var17[var10];
               if (this.isSelfReference(beanName, candidate) && (!(descriptor instanceof MultiElementDescriptor) || !beanName.equals(candidate)) && this.isAutowireCandidate(candidate, fallbackDescriptor)) {
                  this.addCandidateEntry(result, candidate, descriptor, requiredType);
               }
            }
         }
      }

      return result;
   }

   private void addCandidateEntry(Map candidates, String candidateName, DependencyDescriptor descriptor, Class requiredType) {
      Object beanInstance;
      if (descriptor instanceof MultiElementDescriptor) {
         beanInstance = descriptor.resolveCandidate(candidateName, requiredType, this);
         if (!(beanInstance instanceof NullBean)) {
            candidates.put(candidateName, beanInstance);
         }
      } else if (!this.containsSingleton(candidateName) && (!(descriptor instanceof StreamDependencyDescriptor) || !((StreamDependencyDescriptor)descriptor).isOrdered())) {
         candidates.put(candidateName, this.getType(candidateName));
      } else {
         beanInstance = descriptor.resolveCandidate(candidateName, requiredType, this);
         candidates.put(candidateName, beanInstance instanceof NullBean ? null : beanInstance);
      }

   }

   @Nullable
   protected String determineAutowireCandidate(Map candidates, DependencyDescriptor descriptor) {
      Class requiredType = descriptor.getDependencyType();
      String primaryCandidate = this.determinePrimaryCandidate(candidates, requiredType);
      if (primaryCandidate != null) {
         return primaryCandidate;
      } else {
         String priorityCandidate = this.determineHighestPriorityCandidate(candidates, requiredType);
         if (priorityCandidate != null) {
            return priorityCandidate;
         } else {
            Iterator var6 = candidates.entrySet().iterator();

            String candidateName;
            Object beanInstance;
            do {
               if (!var6.hasNext()) {
                  return null;
               }

               Map.Entry entry = (Map.Entry)var6.next();
               candidateName = (String)entry.getKey();
               beanInstance = entry.getValue();
            } while((beanInstance == null || !this.resolvableDependencies.containsValue(beanInstance)) && !this.matchesBeanName(candidateName, descriptor.getDependencyName()));

            return candidateName;
         }
      }
   }

   @Nullable
   protected String determinePrimaryCandidate(Map candidates, Class requiredType) {
      String primaryBeanName = null;
      Iterator var4 = candidates.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         String candidateBeanName = (String)entry.getKey();
         Object beanInstance = entry.getValue();
         if (this.isPrimary(candidateBeanName, beanInstance)) {
            if (primaryBeanName != null) {
               boolean candidateLocal = this.containsBeanDefinition(candidateBeanName);
               boolean primaryLocal = this.containsBeanDefinition(primaryBeanName);
               if (candidateLocal && primaryLocal) {
                  throw new NoUniqueBeanDefinitionException(requiredType, candidates.size(), "more than one 'primary' bean found among candidates: " + candidates.keySet());
               }

               if (candidateLocal) {
                  primaryBeanName = candidateBeanName;
               }
            } else {
               primaryBeanName = candidateBeanName;
            }
         }
      }

      return primaryBeanName;
   }

   @Nullable
   protected String determineHighestPriorityCandidate(Map candidates, Class requiredType) {
      String highestPriorityBeanName = null;
      Integer highestPriority = null;
      Iterator var5 = candidates.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         String candidateBeanName = (String)entry.getKey();
         Object beanInstance = entry.getValue();
         if (beanInstance != null) {
            Integer candidatePriority = this.getPriority(beanInstance);
            if (candidatePriority != null) {
               if (highestPriorityBeanName != null) {
                  if (candidatePriority.equals(highestPriority)) {
                     throw new NoUniqueBeanDefinitionException(requiredType, candidates.size(), "Multiple beans found with the same priority ('" + highestPriority + "') among candidates: " + candidates.keySet());
                  }

                  if (candidatePriority < highestPriority) {
                     highestPriorityBeanName = candidateBeanName;
                     highestPriority = candidatePriority;
                  }
               } else {
                  highestPriorityBeanName = candidateBeanName;
                  highestPriority = candidatePriority;
               }
            }
         }
      }

      return highestPriorityBeanName;
   }

   protected boolean isPrimary(String beanName, Object beanInstance) {
      if (this.containsBeanDefinition(beanName)) {
         return this.getMergedLocalBeanDefinition(beanName).isPrimary();
      } else {
         BeanFactory parent = this.getParentBeanFactory();
         return parent instanceof DefaultListableBeanFactory && ((DefaultListableBeanFactory)parent).isPrimary(beanName, beanInstance);
      }
   }

   @Nullable
   protected Integer getPriority(Object beanInstance) {
      Comparator comparator = this.getDependencyComparator();
      return comparator instanceof OrderComparator ? ((OrderComparator)comparator).getPriority(beanInstance) : null;
   }

   protected boolean matchesBeanName(String beanName, @Nullable String candidateName) {
      return candidateName != null && (candidateName.equals(beanName) || ObjectUtils.containsElement(this.getAliases(beanName), candidateName));
   }

   private boolean isSelfReference(@Nullable String beanName, @Nullable String candidateName) {
      return beanName != null && candidateName != null && (beanName.equals(candidateName) || this.containsBeanDefinition(candidateName) && beanName.equals(this.getMergedLocalBeanDefinition(candidateName).getFactoryBeanName()));
   }

   private void raiseNoMatchingBeanFound(Class type, ResolvableType resolvableType, DependencyDescriptor descriptor) throws BeansException {
      this.checkBeanNotOfRequiredType(type, descriptor);
      throw new NoSuchBeanDefinitionException(resolvableType, "expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: " + ObjectUtils.nullSafeToString((Object[])descriptor.getAnnotations()));
   }

   private void checkBeanNotOfRequiredType(Class type, DependencyDescriptor descriptor) {
      Iterator var3 = this.beanDefinitionNames.iterator();

      String beanName;
      Class beanType;
      do {
         RootBeanDefinition mbd;
         Class targetType;
         do {
            do {
               do {
                  if (!var3.hasNext()) {
                     BeanFactory parent = this.getParentBeanFactory();
                     if (parent instanceof DefaultListableBeanFactory) {
                        ((DefaultListableBeanFactory)parent).checkBeanNotOfRequiredType(type, descriptor);
                     }

                     return;
                  }

                  beanName = (String)var3.next();
                  mbd = this.getMergedLocalBeanDefinition(beanName);
                  targetType = mbd.getTargetType();
               } while(targetType == null);
            } while(!type.isAssignableFrom(targetType));
         } while(!this.isAutowireCandidate(beanName, mbd, descriptor, this.getAutowireCandidateResolver()));

         Object beanInstance = this.getSingleton(beanName, false);
         beanType = beanInstance != null && beanInstance.getClass() != NullBean.class ? beanInstance.getClass() : this.predictBeanType(beanName, mbd, new Class[0]);
      } while(beanType == null || type.isAssignableFrom(beanType));

      throw new BeanNotOfRequiredTypeException(beanName, type, beanType);
   }

   private Optional createOptionalDependency(DependencyDescriptor descriptor, @Nullable String beanName, final Object... args) {
      DependencyDescriptor descriptorToUse = new NestedDependencyDescriptor(descriptor) {
         public boolean isRequired() {
            return false;
         }

         public Object resolveCandidate(String beanName, Class requiredType, BeanFactory beanFactory) {
            return !ObjectUtils.isEmpty(args) ? beanFactory.getBean(beanName, args) : super.resolveCandidate(beanName, requiredType, beanFactory);
         }
      };
      Object result = this.doResolveDependency(descriptorToUse, beanName, (Set)null, (TypeConverter)null);
      return result instanceof Optional ? (Optional)result : Optional.ofNullable(result);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(ObjectUtils.identityToString(this));
      sb.append(": defining beans [");
      sb.append(StringUtils.collectionToCommaDelimitedString(this.beanDefinitionNames));
      sb.append("]; ");
      BeanFactory parent = this.getParentBeanFactory();
      if (parent == null) {
         sb.append("root of factory hierarchy");
      } else {
         sb.append("parent: ").append(ObjectUtils.identityToString(parent));
      }

      return sb.toString();
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      throw new NotSerializableException("DefaultListableBeanFactory itself is not deserializable - just a SerializedBeanFactoryReference is");
   }

   protected Object writeReplace() throws ObjectStreamException {
      if (this.serializationId != null) {
         return new SerializedBeanFactoryReference(this.serializationId);
      } else {
         throw new NotSerializableException("DefaultListableBeanFactory has no serialization id");
      }
   }

   static {
      try {
         javaxInjectProviderClass = ClassUtils.forName("javax.inject.Provider", DefaultListableBeanFactory.class.getClassLoader());
      } catch (ClassNotFoundException var1) {
         javaxInjectProviderClass = null;
      }

      serializableFactories = new ConcurrentHashMap(8);
   }

   private class FactoryAwareOrderSourceProvider implements OrderComparator.OrderSourceProvider {
      private final Map instancesToBeanNames;

      public FactoryAwareOrderSourceProvider(Map instancesToBeanNames) {
         this.instancesToBeanNames = instancesToBeanNames;
      }

      @Nullable
      public Object getOrderSource(Object obj) {
         String beanName = (String)this.instancesToBeanNames.get(obj);
         if (beanName != null && DefaultListableBeanFactory.this.containsBeanDefinition(beanName)) {
            RootBeanDefinition beanDefinition = DefaultListableBeanFactory.this.getMergedLocalBeanDefinition(beanName);
            List sources = new ArrayList(2);
            Method factoryMethod = beanDefinition.getResolvedFactoryMethod();
            if (factoryMethod != null) {
               sources.add(factoryMethod);
            }

            Class targetType = beanDefinition.getTargetType();
            if (targetType != null && targetType != obj.getClass()) {
               sources.add(targetType);
            }

            return sources.toArray();
         } else {
            return null;
         }
      }
   }

   private class Jsr330Factory implements Serializable {
      private Jsr330Factory() {
      }

      public Object createDependencyProvider(DependencyDescriptor descriptor, @Nullable String beanName) {
         return new Jsr330Provider(descriptor, beanName);
      }

      // $FF: synthetic method
      Jsr330Factory(Object x1) {
         this();
      }

      private class Jsr330Provider extends DependencyObjectProvider implements Provider {
         public Jsr330Provider(DependencyDescriptor descriptor, @Nullable String beanName) {
            super(descriptor, beanName);
         }

         @Nullable
         public Object get() throws BeansException {
            return this.getValue();
         }
      }
   }

   private class DependencyObjectProvider implements BeanObjectProvider {
      private final DependencyDescriptor descriptor;
      private final boolean optional;
      @Nullable
      private final String beanName;

      public DependencyObjectProvider(DependencyDescriptor descriptor, @Nullable String beanName) {
         this.descriptor = new NestedDependencyDescriptor(descriptor);
         this.optional = this.descriptor.getDependencyType() == Optional.class;
         this.beanName = beanName;
      }

      public Object getObject() throws BeansException {
         if (this.optional) {
            return DefaultListableBeanFactory.this.createOptionalDependency(this.descriptor, this.beanName);
         } else {
            Object result = DefaultListableBeanFactory.this.doResolveDependency(this.descriptor, this.beanName, (Set)null, (TypeConverter)null);
            if (result == null) {
               throw new NoSuchBeanDefinitionException(this.descriptor.getResolvableType());
            } else {
               return result;
            }
         }
      }

      public Object getObject(final Object... args) throws BeansException {
         if (this.optional) {
            return DefaultListableBeanFactory.this.createOptionalDependency(this.descriptor, this.beanName, args);
         } else {
            DependencyDescriptor descriptorToUse = new DependencyDescriptor(this.descriptor) {
               public Object resolveCandidate(String beanName, Class requiredType, BeanFactory beanFactory) {
                  return beanFactory.getBean(beanName, args);
               }
            };
            Object result = DefaultListableBeanFactory.this.doResolveDependency(descriptorToUse, this.beanName, (Set)null, (TypeConverter)null);
            if (result == null) {
               throw new NoSuchBeanDefinitionException(this.descriptor.getResolvableType());
            } else {
               return result;
            }
         }
      }

      @Nullable
      public Object getIfAvailable() throws BeansException {
         if (this.optional) {
            return DefaultListableBeanFactory.this.createOptionalDependency(this.descriptor, this.beanName);
         } else {
            DependencyDescriptor descriptorToUse = new DependencyDescriptor(this.descriptor) {
               public boolean isRequired() {
                  return false;
               }
            };
            return DefaultListableBeanFactory.this.doResolveDependency(descriptorToUse, this.beanName, (Set)null, (TypeConverter)null);
         }
      }

      @Nullable
      public Object getIfUnique() throws BeansException {
         DependencyDescriptor descriptorToUse = new DependencyDescriptor(this.descriptor) {
            public boolean isRequired() {
               return false;
            }

            @Nullable
            public Object resolveNotUnique(ResolvableType type, Map matchingBeans) {
               return null;
            }
         };
         return this.optional ? DefaultListableBeanFactory.this.createOptionalDependency(descriptorToUse, this.beanName) : DefaultListableBeanFactory.this.doResolveDependency(descriptorToUse, this.beanName, (Set)null, (TypeConverter)null);
      }

      @Nullable
      protected Object getValue() throws BeansException {
         return this.optional ? DefaultListableBeanFactory.this.createOptionalDependency(this.descriptor, this.beanName) : DefaultListableBeanFactory.this.doResolveDependency(this.descriptor, this.beanName, (Set)null, (TypeConverter)null);
      }

      public Stream stream() {
         return this.resolveStream(false);
      }

      public Stream orderedStream() {
         return this.resolveStream(true);
      }

      private Stream resolveStream(boolean ordered) {
         DependencyDescriptor descriptorToUse = new StreamDependencyDescriptor(this.descriptor, ordered);
         Object result = DefaultListableBeanFactory.this.doResolveDependency(descriptorToUse, this.beanName, (Set)null, (TypeConverter)null);
         return result instanceof Stream ? (Stream)result : Stream.of(result);
      }
   }

   private interface BeanObjectProvider extends ObjectProvider, Serializable {
   }

   private static class StreamDependencyDescriptor extends DependencyDescriptor {
      private final boolean ordered;

      public StreamDependencyDescriptor(DependencyDescriptor original, boolean ordered) {
         super(original);
         this.ordered = ordered;
      }

      public boolean isOrdered() {
         return this.ordered;
      }
   }

   private static class MultiElementDescriptor extends NestedDependencyDescriptor {
      public MultiElementDescriptor(DependencyDescriptor original) {
         super(original);
      }
   }

   private static class NestedDependencyDescriptor extends DependencyDescriptor {
      public NestedDependencyDescriptor(DependencyDescriptor original) {
         super(original);
         this.increaseNestingLevel();
      }
   }

   private static class SerializedBeanFactoryReference implements Serializable {
      private final String id;

      public SerializedBeanFactoryReference(String id) {
         this.id = id;
      }

      private Object readResolve() {
         Reference ref = (Reference)DefaultListableBeanFactory.serializableFactories.get(this.id);
         if (ref != null) {
            Object result = ref.get();
            if (result != null) {
               return result;
            }
         }

         DefaultListableBeanFactory dummyFactory = new DefaultListableBeanFactory();
         dummyFactory.serializationId = this.id;
         return dummyFactory;
      }
   }
}
