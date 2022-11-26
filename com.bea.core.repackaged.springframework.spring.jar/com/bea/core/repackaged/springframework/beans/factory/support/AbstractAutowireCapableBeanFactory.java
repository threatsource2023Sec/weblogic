package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.BeanWrapper;
import com.bea.core.repackaged.springframework.beans.BeanWrapperImpl;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorUtils;
import com.bea.core.repackaged.springframework.beans.PropertyValue;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCurrentlyInCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.InjectionPoint;
import com.bea.core.repackaged.springframework.beans.factory.UnsatisfiedDependencyException;
import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.core.DefaultParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
   private InstantiationStrategy instantiationStrategy;
   @Nullable
   private ParameterNameDiscoverer parameterNameDiscoverer;
   private boolean allowCircularReferences;
   private boolean allowRawInjectionDespiteWrapping;
   private final Set ignoredDependencyTypes;
   private final Set ignoredDependencyInterfaces;
   private final NamedThreadLocal currentlyCreatedBean;
   private final ConcurrentMap factoryBeanInstanceCache;
   private final ConcurrentMap factoryMethodCandidateCache;
   private final ConcurrentMap filteredPropertyDescriptorsCache;

   public AbstractAutowireCapableBeanFactory() {
      this.instantiationStrategy = new CglibSubclassingInstantiationStrategy();
      this.parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
      this.allowCircularReferences = true;
      this.allowRawInjectionDespiteWrapping = false;
      this.ignoredDependencyTypes = new HashSet();
      this.ignoredDependencyInterfaces = new HashSet();
      this.currentlyCreatedBean = new NamedThreadLocal("Currently created bean");
      this.factoryBeanInstanceCache = new ConcurrentHashMap();
      this.factoryMethodCandidateCache = new ConcurrentHashMap();
      this.filteredPropertyDescriptorsCache = new ConcurrentHashMap();
      this.ignoreDependencyInterface(BeanNameAware.class);
      this.ignoreDependencyInterface(BeanFactoryAware.class);
      this.ignoreDependencyInterface(BeanClassLoaderAware.class);
   }

   public AbstractAutowireCapableBeanFactory(@Nullable BeanFactory parentBeanFactory) {
      this();
      this.setParentBeanFactory(parentBeanFactory);
   }

   public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
      this.instantiationStrategy = instantiationStrategy;
   }

   protected InstantiationStrategy getInstantiationStrategy() {
      return this.instantiationStrategy;
   }

   public void setParameterNameDiscoverer(@Nullable ParameterNameDiscoverer parameterNameDiscoverer) {
      this.parameterNameDiscoverer = parameterNameDiscoverer;
   }

   @Nullable
   protected ParameterNameDiscoverer getParameterNameDiscoverer() {
      return this.parameterNameDiscoverer;
   }

   public void setAllowCircularReferences(boolean allowCircularReferences) {
      this.allowCircularReferences = allowCircularReferences;
   }

   public void setAllowRawInjectionDespiteWrapping(boolean allowRawInjectionDespiteWrapping) {
      this.allowRawInjectionDespiteWrapping = allowRawInjectionDespiteWrapping;
   }

   public void ignoreDependencyType(Class type) {
      this.ignoredDependencyTypes.add(type);
   }

   public void ignoreDependencyInterface(Class ifc) {
      this.ignoredDependencyInterfaces.add(ifc);
   }

   public void copyConfigurationFrom(ConfigurableBeanFactory otherFactory) {
      super.copyConfigurationFrom(otherFactory);
      if (otherFactory instanceof AbstractAutowireCapableBeanFactory) {
         AbstractAutowireCapableBeanFactory otherAutowireFactory = (AbstractAutowireCapableBeanFactory)otherFactory;
         this.instantiationStrategy = otherAutowireFactory.instantiationStrategy;
         this.allowCircularReferences = otherAutowireFactory.allowCircularReferences;
         this.ignoredDependencyTypes.addAll(otherAutowireFactory.ignoredDependencyTypes);
         this.ignoredDependencyInterfaces.addAll(otherAutowireFactory.ignoredDependencyInterfaces);
      }

   }

   public Object createBean(Class beanClass) throws BeansException {
      RootBeanDefinition bd = new RootBeanDefinition(beanClass);
      bd.setScope("prototype");
      bd.allowCaching = ClassUtils.isCacheSafe(beanClass, this.getBeanClassLoader());
      return this.createBean(beanClass.getName(), bd, (Object[])null);
   }

   public void autowireBean(Object existingBean) {
      RootBeanDefinition bd = new RootBeanDefinition(ClassUtils.getUserClass(existingBean));
      bd.setScope("prototype");
      bd.allowCaching = ClassUtils.isCacheSafe(bd.getBeanClass(), this.getBeanClassLoader());
      BeanWrapper bw = new BeanWrapperImpl(existingBean);
      this.initBeanWrapper(bw);
      this.populateBean(bd.getBeanClass().getName(), bd, bw);
   }

   public Object configureBean(Object existingBean, String beanName) throws BeansException {
      this.markBeanAsCreated(beanName);
      BeanDefinition mbd = this.getMergedBeanDefinition(beanName);
      RootBeanDefinition bd = null;
      if (mbd instanceof RootBeanDefinition) {
         RootBeanDefinition rbd = (RootBeanDefinition)mbd;
         bd = rbd.isPrototype() ? rbd : rbd.cloneBeanDefinition();
      }

      if (bd == null) {
         bd = new RootBeanDefinition(mbd);
      }

      if (!bd.isPrototype()) {
         bd.setScope("prototype");
         bd.allowCaching = ClassUtils.isCacheSafe(ClassUtils.getUserClass(existingBean), this.getBeanClassLoader());
      }

      BeanWrapper bw = new BeanWrapperImpl(existingBean);
      this.initBeanWrapper(bw);
      this.populateBean(beanName, bd, bw);
      return this.initializeBean(beanName, existingBean, bd);
   }

   public Object createBean(Class beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
      RootBeanDefinition bd = new RootBeanDefinition(beanClass, autowireMode, dependencyCheck);
      bd.setScope("prototype");
      return this.createBean(beanClass.getName(), bd, (Object[])null);
   }

   public Object autowire(Class beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
      RootBeanDefinition bd = new RootBeanDefinition(beanClass, autowireMode, dependencyCheck);
      bd.setScope("prototype");
      if (bd.getResolvedAutowireMode() == 3) {
         return this.autowireConstructor(beanClass.getName(), bd, (Constructor[])null, (Object[])null).getWrappedInstance();
      } else {
         Object bean;
         if (System.getSecurityManager() != null) {
            bean = AccessController.doPrivileged(() -> {
               return thisx.getInstantiationStrategy().instantiate(bd, (String)null, this);
            }, this.getAccessControlContext());
         } else {
            bean = this.getInstantiationStrategy().instantiate(bd, (String)null, this);
         }

         this.populateBean(beanClass.getName(), bd, new BeanWrapperImpl(bean));
         return bean;
      }
   }

   public void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck) throws BeansException {
      if (autowireMode == 3) {
         throw new IllegalArgumentException("AUTOWIRE_CONSTRUCTOR not supported for existing bean instance");
      } else {
         RootBeanDefinition bd = new RootBeanDefinition(ClassUtils.getUserClass(existingBean), autowireMode, dependencyCheck);
         bd.setScope("prototype");
         BeanWrapper bw = new BeanWrapperImpl(existingBean);
         this.initBeanWrapper(bw);
         this.populateBean(bd.getBeanClass().getName(), bd, bw);
      }
   }

   public void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException {
      this.markBeanAsCreated(beanName);
      BeanDefinition bd = this.getMergedBeanDefinition(beanName);
      BeanWrapper bw = new BeanWrapperImpl(existingBean);
      this.initBeanWrapper(bw);
      this.applyPropertyValues(beanName, bd, bw, bd.getPropertyValues());
   }

   public Object initializeBean(Object existingBean, String beanName) {
      return this.initializeBean(beanName, existingBean, (RootBeanDefinition)null);
   }

   public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
      Object result = existingBean;

      Object current;
      for(Iterator var4 = this.getBeanPostProcessors().iterator(); var4.hasNext(); result = current) {
         BeanPostProcessor processor = (BeanPostProcessor)var4.next();
         current = processor.postProcessBeforeInitialization(result, beanName);
         if (current == null) {
            return result;
         }
      }

      return result;
   }

   public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
      Object result = existingBean;

      Object current;
      for(Iterator var4 = this.getBeanPostProcessors().iterator(); var4.hasNext(); result = current) {
         BeanPostProcessor processor = (BeanPostProcessor)var4.next();
         current = processor.postProcessAfterInitialization(result, beanName);
         if (current == null) {
            return result;
         }
      }

      return result;
   }

   public void destroyBean(Object existingBean) {
      (new DisposableBeanAdapter(existingBean, this.getBeanPostProcessors(), this.getAccessControlContext())).destroy();
   }

   public Object resolveBeanByName(String name, DependencyDescriptor descriptor) {
      InjectionPoint previousInjectionPoint = ConstructorResolver.setCurrentInjectionPoint(descriptor);

      Object var4;
      try {
         var4 = this.getBean(name, descriptor.getDependencyType());
      } finally {
         ConstructorResolver.setCurrentInjectionPoint(previousInjectionPoint);
      }

      return var4;
   }

   @Nullable
   public Object resolveDependency(DependencyDescriptor descriptor, @Nullable String requestingBeanName) throws BeansException {
      return this.resolveDependency(descriptor, requestingBeanName, (Set)null, (TypeConverter)null);
   }

   protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Creating instance of bean '" + beanName + "'");
      }

      RootBeanDefinition mbdToUse = mbd;
      Class resolvedClass = this.resolveBeanClass(mbd, beanName, new Class[0]);
      if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
         mbdToUse = new RootBeanDefinition(mbd);
         mbdToUse.setBeanClass(resolvedClass);
      }

      try {
         mbdToUse.prepareMethodOverrides();
      } catch (BeanDefinitionValidationException var9) {
         throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(), beanName, "Validation of method overrides failed", var9);
      }

      Object beanInstance;
      try {
         beanInstance = this.resolveBeforeInstantiation(beanName, mbdToUse);
         if (beanInstance != null) {
            return beanInstance;
         }
      } catch (Throwable var10) {
         throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName, "BeanPostProcessor before instantiation of bean failed", var10);
      }

      try {
         beanInstance = this.doCreateBean(beanName, mbdToUse, args);
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Finished creating instance of bean '" + beanName + "'");
         }

         return beanInstance;
      } catch (ImplicitlyAppearedSingletonException | BeanCreationException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName, "Unexpected exception during bean creation", var8);
      }
   }

   protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
      BeanWrapper instanceWrapper = null;
      if (mbd.isSingleton()) {
         instanceWrapper = (BeanWrapper)this.factoryBeanInstanceCache.remove(beanName);
      }

      if (instanceWrapper == null) {
         instanceWrapper = this.createBeanInstance(beanName, mbd, args);
      }

      Object bean = instanceWrapper.getWrappedInstance();
      Class beanType = instanceWrapper.getWrappedClass();
      if (beanType != NullBean.class) {
         mbd.resolvedTargetType = beanType;
      }

      synchronized(mbd.postProcessingLock) {
         if (!mbd.postProcessed) {
            try {
               this.applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
            } catch (Throwable var17) {
               throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Post-processing of merged bean definition failed", var17);
            }

            mbd.postProcessed = true;
         }
      }

      boolean earlySingletonExposure = mbd.isSingleton() && this.allowCircularReferences && this.isSingletonCurrentlyInCreation(beanName);
      if (earlySingletonExposure) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Eagerly caching bean '" + beanName + "' to allow for resolving potential circular references");
         }

         this.addSingletonFactory(beanName, () -> {
            return this.getEarlyBeanReference(beanName, mbd, bean);
         });
      }

      Object exposedObject = bean;

      try {
         this.populateBean(beanName, mbd, instanceWrapper);
         exposedObject = this.initializeBean(beanName, exposedObject, mbd);
      } catch (Throwable var18) {
         if (var18 instanceof BeanCreationException && beanName.equals(((BeanCreationException)var18).getBeanName())) {
            throw (BeanCreationException)var18;
         }

         throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Initialization of bean failed", var18);
      }

      if (earlySingletonExposure) {
         Object earlySingletonReference = this.getSingleton(beanName, false);
         if (earlySingletonReference != null) {
            if (exposedObject == bean) {
               exposedObject = earlySingletonReference;
            } else if (!this.allowRawInjectionDespiteWrapping && this.hasDependentBean(beanName)) {
               String[] dependentBeans = this.getDependentBeans(beanName);
               Set actualDependentBeans = new LinkedHashSet(dependentBeans.length);
               String[] var12 = dependentBeans;
               int var13 = dependentBeans.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  String dependentBean = var12[var14];
                  if (!this.removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
                     actualDependentBeans.add(dependentBean);
                  }
               }

               if (!actualDependentBeans.isEmpty()) {
                  throw new BeanCurrentlyInCreationException(beanName, "Bean with name '" + beanName + "' has been injected into other beans [" + StringUtils.collectionToCommaDelimitedString(actualDependentBeans) + "] in its raw version as part of a circular reference, but has eventually been wrapped. This means that said other beans do not use the final version of the bean. This is often the result of over-eager type matching - consider using 'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.");
               }
            }
         }
      }

      try {
         this.registerDisposableBeanIfNecessary(beanName, bean, mbd);
         return exposedObject;
      } catch (BeanDefinitionValidationException var16) {
         throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Invalid destruction signature", var16);
      }
   }

   @Nullable
   protected Class predictBeanType(String beanName, RootBeanDefinition mbd, Class... typesToMatch) {
      Class targetType = this.determineTargetType(beanName, mbd, typesToMatch);
      if (targetType != null && !mbd.isSynthetic() && this.hasInstantiationAwareBeanPostProcessors()) {
         Iterator var5 = this.getBeanPostProcessors().iterator();

         while(var5.hasNext()) {
            BeanPostProcessor bp = (BeanPostProcessor)var5.next();
            if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
               SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor)bp;
               Class predicted = ibp.predictBeanType(targetType, beanName);
               if (predicted != null && (typesToMatch.length != 1 || FactoryBean.class != typesToMatch[0] || FactoryBean.class.isAssignableFrom(predicted))) {
                  return predicted;
               }
            }
         }
      }

      return targetType;
   }

   @Nullable
   protected Class determineTargetType(String beanName, RootBeanDefinition mbd, Class... typesToMatch) {
      Class targetType = mbd.getTargetType();
      if (targetType == null) {
         targetType = mbd.getFactoryMethodName() != null ? this.getTypeForFactoryMethod(beanName, mbd, typesToMatch) : this.resolveBeanClass(mbd, beanName, typesToMatch);
         if (ObjectUtils.isEmpty((Object[])typesToMatch) || this.getTempClassLoader() == null) {
            mbd.resolvedTargetType = targetType;
         }
      }

      return targetType;
   }

   @Nullable
   protected Class getTypeForFactoryMethod(String beanName, RootBeanDefinition mbd, Class... typesToMatch) {
      ResolvableType cachedReturnType = mbd.factoryMethodReturnType;
      if (cachedReturnType != null) {
         return cachedReturnType.resolve();
      } else {
         boolean isStatic = true;
         String factoryBeanName = mbd.getFactoryBeanName();
         Class factoryClass;
         if (factoryBeanName != null) {
            if (factoryBeanName.equals(beanName)) {
               throw new BeanDefinitionStoreException(mbd.getResourceDescription(), beanName, "factory-bean reference points back to the same bean definition");
            }

            factoryClass = this.getType(factoryBeanName);
            isStatic = false;
         } else {
            factoryClass = this.resolveBeanClass(mbd, beanName, typesToMatch);
         }

         if (factoryClass == null) {
            return null;
         } else {
            factoryClass = ClassUtils.getUserClass(factoryClass);
            Class commonType = null;
            Method uniqueCandidate = null;
            int minNrOfArgs = mbd.hasConstructorArgumentValues() ? mbd.getConstructorArgumentValues().getArgumentCount() : 0;
            Method[] candidates = (Method[])this.factoryMethodCandidateCache.computeIfAbsent(factoryClass, ReflectionUtils::getUniqueDeclaredMethods);
            Method[] var12 = candidates;
            int var13 = candidates.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               Method candidate = var12[var14];
               if (Modifier.isStatic(candidate.getModifiers()) == isStatic && mbd.isFactoryMethod(candidate) && candidate.getParameterCount() >= minNrOfArgs) {
                  if (candidate.getTypeParameters().length > 0) {
                     try {
                        Class[] paramTypes = candidate.getParameterTypes();
                        String[] paramNames = null;
                        ParameterNameDiscoverer pnd = this.getParameterNameDiscoverer();
                        if (pnd != null) {
                           paramNames = pnd.getParameterNames(candidate);
                        }

                        ConstructorArgumentValues cav = mbd.getConstructorArgumentValues();
                        Set usedValueHolders = new HashSet(paramTypes.length);
                        Object[] args = new Object[paramTypes.length];

                        for(int i = 0; i < args.length; ++i) {
                           ConstructorArgumentValues.ValueHolder valueHolder = cav.getArgumentValue(i, paramTypes[i], paramNames != null ? paramNames[i] : null, usedValueHolders);
                           if (valueHolder == null) {
                              valueHolder = cav.getGenericArgumentValue((Class)null, (String)null, usedValueHolders);
                           }

                           if (valueHolder != null) {
                              args[i] = valueHolder.getValue();
                              usedValueHolders.add(valueHolder);
                           }
                        }

                        Class returnType = AutowireUtils.resolveReturnTypeForFactoryMethod(candidate, args, this.getBeanClassLoader());
                        uniqueCandidate = commonType == null && returnType == candidate.getReturnType() ? candidate : null;
                        commonType = ClassUtils.determineCommonAncestor(returnType, commonType);
                        if (commonType == null) {
                           return null;
                        }
                     } catch (Throwable var24) {
                        if (this.logger.isDebugEnabled()) {
                           this.logger.debug("Failed to resolve generic return type for factory method: " + var24);
                        }
                     }
                  } else {
                     uniqueCandidate = commonType == null ? candidate : null;
                     commonType = ClassUtils.determineCommonAncestor(candidate.getReturnType(), commonType);
                     if (commonType == null) {
                        return null;
                     }
                  }
               }
            }

            mbd.factoryMethodToIntrospect = uniqueCandidate;
            if (commonType == null) {
               return null;
            } else {
               cachedReturnType = uniqueCandidate != null ? ResolvableType.forMethodReturnType(uniqueCandidate) : ResolvableType.forClass(commonType);
               mbd.factoryMethodReturnType = cachedReturnType;
               return cachedReturnType.resolve();
            }
         }
      }
   }

   @Nullable
   protected Class getTypeForFactoryBean(String beanName, RootBeanDefinition mbd) {
      if (mbd.getInstanceSupplier() != null) {
         ResolvableType targetType = mbd.targetType;
         Class result;
         if (targetType != null) {
            result = targetType.as(FactoryBean.class).getGeneric().resolve();
            if (result != null) {
               return result;
            }
         }

         if (mbd.hasBeanClass()) {
            result = GenericTypeResolver.resolveTypeArgument(mbd.getBeanClass(), FactoryBean.class);
            if (result != null) {
               return result;
            }
         }
      }

      String factoryBeanName = mbd.getFactoryBeanName();
      String factoryMethodName = mbd.getFactoryMethodName();
      if (factoryBeanName != null) {
         if (factoryMethodName != null) {
            BeanDefinition fbDef = this.getBeanDefinition(factoryBeanName);
            if (fbDef instanceof AbstractBeanDefinition) {
               AbstractBeanDefinition afbDef = (AbstractBeanDefinition)fbDef;
               if (afbDef.hasBeanClass()) {
                  Class result = this.getTypeForFactoryBeanFromMethod(afbDef.getBeanClass(), factoryMethodName);
                  if (result != null) {
                     return result;
                  }
               }
            }
         }

         if (!this.isBeanEligibleForMetadataCaching(factoryBeanName)) {
            return null;
         }
      }

      FactoryBean fb = mbd.isSingleton() ? this.getSingletonFactoryBeanForTypeCheck(beanName, mbd) : this.getNonSingletonFactoryBeanForTypeCheck(beanName, mbd);
      if (fb != null) {
         Class result = this.getTypeForFactoryBean(fb);
         return result != null ? result : super.getTypeForFactoryBean(beanName, mbd);
      } else if (factoryBeanName == null && mbd.hasBeanClass()) {
         return factoryMethodName != null ? this.getTypeForFactoryBeanFromMethod(mbd.getBeanClass(), factoryMethodName) : GenericTypeResolver.resolveTypeArgument(mbd.getBeanClass(), FactoryBean.class);
      } else {
         return null;
      }
   }

   @Nullable
   private Class getTypeForFactoryBeanFromMethod(Class beanClass, String factoryMethodName) {
      class Holder {
         @Nullable
         Class value = null;
      }

      Holder objectType = new Holder();
      Class fbClass = ClassUtils.getUserClass(beanClass);
      ReflectionUtils.doWithMethods(fbClass, (method) -> {
         if (method.getName().equals(factoryMethodName) && FactoryBean.class.isAssignableFrom(method.getReturnType())) {
            Class currentType = GenericTypeResolver.resolveReturnTypeArgument(method, FactoryBean.class);
            if (currentType != null) {
               objectType.value = ClassUtils.determineCommonAncestor(currentType, objectType.value);
            }
         }

      });
      return objectType.value != null && Object.class != objectType.value ? objectType.value : null;
   }

   protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
      Object exposedObject = bean;
      if (!mbd.isSynthetic() && this.hasInstantiationAwareBeanPostProcessors()) {
         Iterator var5 = this.getBeanPostProcessors().iterator();

         while(var5.hasNext()) {
            BeanPostProcessor bp = (BeanPostProcessor)var5.next();
            if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
               SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor)bp;
               exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
            }
         }
      }

      return exposedObject;
   }

   @Nullable
   private FactoryBean getSingletonFactoryBeanForTypeCheck(String beanName, RootBeanDefinition mbd) {
      synchronized(this.getSingletonMutex()) {
         BeanWrapper bw = (BeanWrapper)this.factoryBeanInstanceCache.get(beanName);
         if (bw != null) {
            return (FactoryBean)bw.getWrappedInstance();
         } else {
            Object beanInstance = this.getSingleton(beanName, false);
            if (beanInstance instanceof FactoryBean) {
               return (FactoryBean)beanInstance;
            } else if (this.isSingletonCurrentlyInCreation(beanName) || mbd.getFactoryBeanName() != null && this.isSingletonCurrentlyInCreation(mbd.getFactoryBeanName())) {
               return null;
            } else {
               Object instance;
               label111: {
                  Object var8;
                  try {
                     this.beforeSingletonCreation(beanName);
                     instance = this.resolveBeforeInstantiation(beanName, mbd);
                     if (instance == null) {
                        bw = this.createBeanInstance(beanName, mbd, (Object[])null);
                        instance = bw.getWrappedInstance();
                     }
                     break label111;
                  } catch (UnsatisfiedDependencyException var15) {
                     throw var15;
                  } catch (BeanCreationException var16) {
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Bean creation exception on singleton FactoryBean type check: " + var16);
                     }

                     this.onSuppressedException(var16);
                     var8 = null;
                  } finally {
                     this.afterSingletonCreation(beanName);
                  }

                  return (FactoryBean)var8;
               }

               FactoryBean fb = this.getFactoryBean(beanName, instance);
               if (bw != null) {
                  this.factoryBeanInstanceCache.put(beanName, bw);
               }

               return fb;
            }
         }
      }
   }

   @Nullable
   private FactoryBean getNonSingletonFactoryBeanForTypeCheck(String beanName, RootBeanDefinition mbd) {
      if (this.isPrototypeCurrentlyInCreation(beanName)) {
         return null;
      } else {
         Object var5;
         try {
            this.beforePrototypeCreation(beanName);
            Object instance = this.resolveBeforeInstantiation(beanName, mbd);
            if (instance == null) {
               BeanWrapper bw = this.createBeanInstance(beanName, mbd, (Object[])null);
               instance = bw.getWrappedInstance();
            }

            return this.getFactoryBean(beanName, instance);
         } catch (UnsatisfiedDependencyException var10) {
            throw var10;
         } catch (BeanCreationException var11) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Bean creation exception on non-singleton FactoryBean type check: " + var11);
            }

            this.onSuppressedException(var11);
            var5 = null;
         } finally {
            this.afterPrototypeCreation(beanName);
         }

         return (FactoryBean)var5;
      }
   }

   protected void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class beanType, String beanName) {
      Iterator var4 = this.getBeanPostProcessors().iterator();

      while(var4.hasNext()) {
         BeanPostProcessor bp = (BeanPostProcessor)var4.next();
         if (bp instanceof MergedBeanDefinitionPostProcessor) {
            MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor)bp;
            bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
         }
      }

   }

   @Nullable
   protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
      Object bean = null;
      if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
         if (!mbd.isSynthetic() && this.hasInstantiationAwareBeanPostProcessors()) {
            Class targetType = this.determineTargetType(beanName, mbd);
            if (targetType != null) {
               bean = this.applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
               if (bean != null) {
                  bean = this.applyBeanPostProcessorsAfterInitialization(bean, beanName);
               }
            }
         }

         mbd.beforeInstantiationResolved = bean != null;
      }

      return bean;
   }

   @Nullable
   protected Object applyBeanPostProcessorsBeforeInstantiation(Class beanClass, String beanName) {
      Iterator var3 = this.getBeanPostProcessors().iterator();

      while(var3.hasNext()) {
         BeanPostProcessor bp = (BeanPostProcessor)var3.next();
         if (bp instanceof InstantiationAwareBeanPostProcessor) {
            InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor)bp;
            Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
            if (result != null) {
               return result;
            }
         }
      }

      return null;
   }

   protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) {
      Class beanClass = this.resolveBeanClass(mbd, beanName, new Class[0]);
      if (beanClass != null && !Modifier.isPublic(beanClass.getModifiers()) && !mbd.isNonPublicAccessAllowed()) {
         throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Bean class isn't public, and non-public access not allowed: " + beanClass.getName());
      } else {
         Supplier instanceSupplier = mbd.getInstanceSupplier();
         if (instanceSupplier != null) {
            return this.obtainFromSupplier(instanceSupplier, beanName);
         } else if (mbd.getFactoryMethodName() != null) {
            return this.instantiateUsingFactoryMethod(beanName, mbd, args);
         } else {
            boolean resolved = false;
            boolean autowireNecessary = false;
            if (args == null) {
               synchronized(mbd.constructorArgumentLock) {
                  if (mbd.resolvedConstructorOrFactoryMethod != null) {
                     resolved = true;
                     autowireNecessary = mbd.constructorArgumentsResolved;
                  }
               }
            }

            if (resolved) {
               return autowireNecessary ? this.autowireConstructor(beanName, mbd, (Constructor[])null, (Object[])null) : this.instantiateBean(beanName, mbd);
            } else {
               Constructor[] ctors = this.determineConstructorsFromBeanPostProcessors(beanClass, beanName);
               if (ctors == null && mbd.getResolvedAutowireMode() != 3 && !mbd.hasConstructorArgumentValues() && ObjectUtils.isEmpty(args)) {
                  ctors = mbd.getPreferredConstructors();
                  return ctors != null ? this.autowireConstructor(beanName, mbd, ctors, (Object[])null) : this.instantiateBean(beanName, mbd);
               } else {
                  return this.autowireConstructor(beanName, mbd, ctors, args);
               }
            }
         }
      }
   }

   protected BeanWrapper obtainFromSupplier(Supplier instanceSupplier, String beanName) {
      String outerBean = (String)this.currentlyCreatedBean.get();
      this.currentlyCreatedBean.set(beanName);

      Object instance;
      try {
         instance = instanceSupplier.get();
      } finally {
         if (outerBean != null) {
            this.currentlyCreatedBean.set(outerBean);
         } else {
            this.currentlyCreatedBean.remove();
         }

      }

      if (instance == null) {
         instance = new NullBean();
      }

      BeanWrapper bw = new BeanWrapperImpl(instance);
      this.initBeanWrapper(bw);
      return bw;
   }

   protected Object getObjectForBeanInstance(Object beanInstance, String name, String beanName, @Nullable RootBeanDefinition mbd) {
      String currentlyCreatedBean = (String)this.currentlyCreatedBean.get();
      if (currentlyCreatedBean != null) {
         this.registerDependentBean(beanName, currentlyCreatedBean);
      }

      return super.getObjectForBeanInstance(beanInstance, name, beanName, mbd);
   }

   @Nullable
   protected Constructor[] determineConstructorsFromBeanPostProcessors(@Nullable Class beanClass, String beanName) throws BeansException {
      if (beanClass != null && this.hasInstantiationAwareBeanPostProcessors()) {
         Iterator var3 = this.getBeanPostProcessors().iterator();

         while(var3.hasNext()) {
            BeanPostProcessor bp = (BeanPostProcessor)var3.next();
            if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
               SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor)bp;
               Constructor[] ctors = ibp.determineCandidateConstructors(beanClass, beanName);
               if (ctors != null) {
                  return ctors;
               }
            }
         }
      }

      return null;
   }

   protected BeanWrapper instantiateBean(String beanName, RootBeanDefinition mbd) {
      try {
         Object beanInstance;
         if (System.getSecurityManager() != null) {
            beanInstance = AccessController.doPrivileged(() -> {
               return thisx.getInstantiationStrategy().instantiate(mbd, beanName, this);
            }, this.getAccessControlContext());
         } else {
            beanInstance = this.getInstantiationStrategy().instantiate(mbd, beanName, this);
         }

         BeanWrapper bw = new BeanWrapperImpl(beanInstance);
         this.initBeanWrapper(bw);
         return bw;
      } catch (Throwable var6) {
         throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Instantiation of bean failed", var6);
      }
   }

   protected BeanWrapper instantiateUsingFactoryMethod(String beanName, RootBeanDefinition mbd, @Nullable Object[] explicitArgs) {
      return (new ConstructorResolver(this)).instantiateUsingFactoryMethod(beanName, mbd, explicitArgs);
   }

   protected BeanWrapper autowireConstructor(String beanName, RootBeanDefinition mbd, @Nullable Constructor[] ctors, @Nullable Object[] explicitArgs) {
      return (new ConstructorResolver(this)).autowireConstructor(beanName, mbd, ctors, explicitArgs);
   }

   protected void populateBean(String beanName, RootBeanDefinition mbd, @Nullable BeanWrapper bw) {
      if (bw == null) {
         if (mbd.hasPropertyValues()) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Cannot apply property values to null instance");
         }
      } else {
         if (!mbd.isSynthetic() && this.hasInstantiationAwareBeanPostProcessors()) {
            Iterator var4 = this.getBeanPostProcessors().iterator();

            while(var4.hasNext()) {
               BeanPostProcessor bp = (BeanPostProcessor)var4.next();
               if (bp instanceof InstantiationAwareBeanPostProcessor) {
                  InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor)bp;
                  if (!ibp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {
                     return;
                  }
               }
            }
         }

         PropertyValues pvs = mbd.hasPropertyValues() ? mbd.getPropertyValues() : null;
         int resolvedAutowireMode = mbd.getResolvedAutowireMode();
         if (resolvedAutowireMode == 1 || resolvedAutowireMode == 2) {
            MutablePropertyValues newPvs = new MutablePropertyValues((PropertyValues)pvs);
            if (resolvedAutowireMode == 1) {
               this.autowireByName(beanName, mbd, bw, newPvs);
            }

            if (resolvedAutowireMode == 2) {
               this.autowireByType(beanName, mbd, bw, newPvs);
            }

            pvs = newPvs;
         }

         boolean hasInstAwareBpps = this.hasInstantiationAwareBeanPostProcessors();
         boolean needsDepCheck = mbd.getDependencyCheck() != 0;
         PropertyDescriptor[] filteredPds = null;
         if (hasInstAwareBpps) {
            if (pvs == null) {
               pvs = mbd.getPropertyValues();
            }

            Iterator var9 = this.getBeanPostProcessors().iterator();

            while(var9.hasNext()) {
               BeanPostProcessor bp = (BeanPostProcessor)var9.next();
               if (bp instanceof InstantiationAwareBeanPostProcessor) {
                  InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor)bp;
                  PropertyValues pvsToUse = ibp.postProcessProperties((PropertyValues)pvs, bw.getWrappedInstance(), beanName);
                  if (pvsToUse == null) {
                     if (filteredPds == null) {
                        filteredPds = this.filterPropertyDescriptorsForDependencyCheck(bw, mbd.allowCaching);
                     }

                     pvsToUse = ibp.postProcessPropertyValues((PropertyValues)pvs, filteredPds, bw.getWrappedInstance(), beanName);
                     if (pvsToUse == null) {
                        return;
                     }
                  }

                  pvs = pvsToUse;
               }
            }
         }

         if (needsDepCheck) {
            if (filteredPds == null) {
               filteredPds = this.filterPropertyDescriptorsForDependencyCheck(bw, mbd.allowCaching);
            }

            this.checkDependencies(beanName, mbd, filteredPds, (PropertyValues)pvs);
         }

         if (pvs != null) {
            this.applyPropertyValues(beanName, mbd, bw, (PropertyValues)pvs);
         }

      }
   }

   protected void autowireByName(String beanName, AbstractBeanDefinition mbd, BeanWrapper bw, MutablePropertyValues pvs) {
      String[] propertyNames = this.unsatisfiedNonSimpleProperties(mbd, bw);
      String[] var6 = propertyNames;
      int var7 = propertyNames.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String propertyName = var6[var8];
         if (this.containsBean(propertyName)) {
            Object bean = this.getBean(propertyName);
            pvs.add(propertyName, bean);
            this.registerDependentBean(propertyName, beanName);
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Added autowiring by name from bean name '" + beanName + "' via property '" + propertyName + "' to bean named '" + propertyName + "'");
            }
         } else if (this.logger.isTraceEnabled()) {
            this.logger.trace("Not autowiring property '" + propertyName + "' of bean '" + beanName + "' by name: no matching bean found");
         }
      }

   }

   protected void autowireByType(String beanName, AbstractBeanDefinition mbd, BeanWrapper bw, MutablePropertyValues pvs) {
      TypeConverter converter = this.getCustomTypeConverter();
      if (converter == null) {
         converter = bw;
      }

      Set autowiredBeanNames = new LinkedHashSet(4);
      String[] propertyNames = this.unsatisfiedNonSimpleProperties(mbd, bw);
      String[] var8 = propertyNames;
      int var9 = propertyNames.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String propertyName = var8[var10];

         try {
            PropertyDescriptor pd = bw.getPropertyDescriptor(propertyName);
            if (Object.class != pd.getPropertyType()) {
               MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
               boolean eager = !(bw.getWrappedInstance() instanceof PriorityOrdered);
               DependencyDescriptor desc = new AutowireByTypeDependencyDescriptor(methodParam, eager);
               Object autowiredArgument = this.resolveDependency(desc, beanName, autowiredBeanNames, (TypeConverter)converter);
               if (autowiredArgument != null) {
                  pvs.add(propertyName, autowiredArgument);
               }

               Iterator var17 = autowiredBeanNames.iterator();

               while(var17.hasNext()) {
                  String autowiredBeanName = (String)var17.next();
                  this.registerDependentBean(autowiredBeanName, beanName);
                  if (this.logger.isTraceEnabled()) {
                     this.logger.trace("Autowiring by type from bean name '" + beanName + "' via property '" + propertyName + "' to bean named '" + autowiredBeanName + "'");
                  }
               }

               autowiredBeanNames.clear();
            }
         } catch (BeansException var19) {
            throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, propertyName, var19);
         }
      }

   }

   protected String[] unsatisfiedNonSimpleProperties(AbstractBeanDefinition mbd, BeanWrapper bw) {
      Set result = new TreeSet();
      PropertyValues pvs = mbd.getPropertyValues();
      PropertyDescriptor[] pds = bw.getPropertyDescriptors();
      PropertyDescriptor[] var6 = pds;
      int var7 = pds.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PropertyDescriptor pd = var6[var8];
         if (pd.getWriteMethod() != null && !this.isExcludedFromDependencyCheck(pd) && !pvs.contains(pd.getName()) && !BeanUtils.isSimpleProperty(pd.getPropertyType())) {
            result.add(pd.getName());
         }
      }

      return StringUtils.toStringArray((Collection)result);
   }

   protected PropertyDescriptor[] filterPropertyDescriptorsForDependencyCheck(BeanWrapper bw, boolean cache) {
      PropertyDescriptor[] filtered = (PropertyDescriptor[])this.filteredPropertyDescriptorsCache.get(bw.getWrappedClass());
      if (filtered == null) {
         filtered = this.filterPropertyDescriptorsForDependencyCheck(bw);
         if (cache) {
            PropertyDescriptor[] existing = (PropertyDescriptor[])this.filteredPropertyDescriptorsCache.putIfAbsent(bw.getWrappedClass(), filtered);
            if (existing != null) {
               filtered = existing;
            }
         }
      }

      return filtered;
   }

   protected PropertyDescriptor[] filterPropertyDescriptorsForDependencyCheck(BeanWrapper bw) {
      List pds = new ArrayList(Arrays.asList(bw.getPropertyDescriptors()));
      pds.removeIf(this::isExcludedFromDependencyCheck);
      return (PropertyDescriptor[])pds.toArray(new PropertyDescriptor[0]);
   }

   protected boolean isExcludedFromDependencyCheck(PropertyDescriptor pd) {
      return AutowireUtils.isExcludedFromDependencyCheck(pd) || this.ignoredDependencyTypes.contains(pd.getPropertyType()) || AutowireUtils.isSetterDefinedInInterface(pd, this.ignoredDependencyInterfaces);
   }

   protected void checkDependencies(String beanName, AbstractBeanDefinition mbd, PropertyDescriptor[] pds, @Nullable PropertyValues pvs) throws UnsatisfiedDependencyException {
      int dependencyCheck = mbd.getDependencyCheck();
      PropertyDescriptor[] var6 = pds;
      int var7 = pds.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PropertyDescriptor pd = var6[var8];
         if (pd.getWriteMethod() != null && (pvs == null || !pvs.contains(pd.getName()))) {
            boolean isSimple = BeanUtils.isSimpleProperty(pd.getPropertyType());
            boolean unsatisfied = dependencyCheck == 3 || isSimple && dependencyCheck == 2 || !isSimple && dependencyCheck == 1;
            if (unsatisfied) {
               throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, pd.getName(), "Set this property value or disable dependency checking for this bean.");
            }
         }
      }

   }

   protected void applyPropertyValues(String beanName, BeanDefinition mbd, BeanWrapper bw, PropertyValues pvs) {
      if (!pvs.isEmpty()) {
         if (System.getSecurityManager() != null && bw instanceof BeanWrapperImpl) {
            ((BeanWrapperImpl)bw).setSecurityContext(this.getAccessControlContext());
         }

         MutablePropertyValues mpvs = null;
         List original;
         if (pvs instanceof MutablePropertyValues) {
            mpvs = (MutablePropertyValues)pvs;
            if (mpvs.isConverted()) {
               try {
                  bw.setPropertyValues(mpvs);
                  return;
               } catch (BeansException var18) {
                  throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Error setting property values", var18);
               }
            }

            original = mpvs.getPropertyValueList();
         } else {
            original = Arrays.asList(pvs.getPropertyValues());
         }

         TypeConverter converter = this.getCustomTypeConverter();
         if (converter == null) {
            converter = bw;
         }

         BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this, beanName, mbd, (TypeConverter)converter);
         List deepCopy = new ArrayList(original.size());
         boolean resolveNecessary = false;
         Iterator var11 = original.iterator();

         while(true) {
            while(var11.hasNext()) {
               PropertyValue pv = (PropertyValue)var11.next();
               if (pv.isConverted()) {
                  deepCopy.add(pv);
               } else {
                  String propertyName = pv.getName();
                  Object originalValue = pv.getValue();
                  Object resolvedValue = valueResolver.resolveValueIfNecessary(pv, originalValue);
                  Object convertedValue = resolvedValue;
                  boolean convertible = bw.isWritableProperty(propertyName) && !PropertyAccessorUtils.isNestedOrIndexedProperty(propertyName);
                  if (convertible) {
                     convertedValue = this.convertForProperty(resolvedValue, propertyName, bw, (TypeConverter)converter);
                  }

                  if (resolvedValue == originalValue) {
                     if (convertible) {
                        pv.setConvertedValue(convertedValue);
                     }

                     deepCopy.add(pv);
                  } else if (convertible && originalValue instanceof TypedStringValue && !((TypedStringValue)originalValue).isDynamic() && !(convertedValue instanceof Collection) && !ObjectUtils.isArray(convertedValue)) {
                     pv.setConvertedValue(convertedValue);
                     deepCopy.add(pv);
                  } else {
                     resolveNecessary = true;
                     deepCopy.add(new PropertyValue(pv, convertedValue));
                  }
               }
            }

            if (mpvs != null && !resolveNecessary) {
               mpvs.setConverted();
            }

            try {
               bw.setPropertyValues(new MutablePropertyValues(deepCopy));
               return;
            } catch (BeansException var19) {
               throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Error setting property values", var19);
            }
         }
      }
   }

   @Nullable
   private Object convertForProperty(@Nullable Object value, String propertyName, BeanWrapper bw, TypeConverter converter) {
      if (converter instanceof BeanWrapperImpl) {
         return ((BeanWrapperImpl)converter).convertForProperty(value, propertyName);
      } else {
         PropertyDescriptor pd = bw.getPropertyDescriptor(propertyName);
         MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
         return converter.convertIfNecessary(value, pd.getPropertyType(), methodParam);
      }
   }

   protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd) {
      if (System.getSecurityManager() != null) {
         AccessController.doPrivileged(() -> {
            this.invokeAwareMethods(beanName, bean);
            return null;
         }, this.getAccessControlContext());
      } else {
         this.invokeAwareMethods(beanName, bean);
      }

      Object wrappedBean = bean;
      if (mbd == null || !mbd.isSynthetic()) {
         wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
      }

      try {
         this.invokeInitMethods(beanName, wrappedBean, mbd);
      } catch (Throwable var6) {
         throw new BeanCreationException(mbd != null ? mbd.getResourceDescription() : null, beanName, "Invocation of init method failed", var6);
      }

      if (mbd == null || !mbd.isSynthetic()) {
         wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
      }

      return wrappedBean;
   }

   private void invokeAwareMethods(String beanName, Object bean) {
      if (bean instanceof Aware) {
         if (bean instanceof BeanNameAware) {
            ((BeanNameAware)bean).setBeanName(beanName);
         }

         if (bean instanceof BeanClassLoaderAware) {
            ClassLoader bcl = this.getBeanClassLoader();
            if (bcl != null) {
               ((BeanClassLoaderAware)bean).setBeanClassLoader(bcl);
            }
         }

         if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware)bean).setBeanFactory(this);
         }
      }

   }

   protected void invokeInitMethods(String beanName, Object bean, @Nullable RootBeanDefinition mbd) throws Throwable {
      boolean isInitializingBean = bean instanceof InitializingBean;
      if (isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Invoking afterPropertiesSet() on bean with name '" + beanName + "'");
         }

         if (System.getSecurityManager() != null) {
            try {
               AccessController.doPrivileged(() -> {
                  ((InitializingBean)bean).afterPropertiesSet();
                  return null;
               }, this.getAccessControlContext());
            } catch (PrivilegedActionException var6) {
               throw var6.getException();
            }
         } else {
            ((InitializingBean)bean).afterPropertiesSet();
         }
      }

      if (mbd != null && bean.getClass() != NullBean.class) {
         String initMethodName = mbd.getInitMethodName();
         if (StringUtils.hasLength(initMethodName) && (!isInitializingBean || !"afterPropertiesSet".equals(initMethodName)) && !mbd.isExternallyManagedInitMethod(initMethodName)) {
            this.invokeCustomInitMethod(beanName, bean, mbd);
         }
      }

   }

   protected void invokeCustomInitMethod(String beanName, Object bean, RootBeanDefinition mbd) throws Throwable {
      String initMethodName = mbd.getInitMethodName();
      Assert.state(initMethodName != null, "No init method set");
      Method initMethod = mbd.isNonPublicAccessAllowed() ? BeanUtils.findMethod(bean.getClass(), initMethodName) : ClassUtils.getMethodIfAvailable(bean.getClass(), initMethodName);
      if (initMethod == null) {
         if (mbd.isEnforceInitMethod()) {
            throw new BeanDefinitionValidationException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
         } else {
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("No default init method named '" + initMethodName + "' found on bean with name '" + beanName + "'");
            }

         }
      } else {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Invoking init method  '" + initMethodName + "' on bean with name '" + beanName + "'");
         }

         Method methodToInvoke = ClassUtils.getInterfaceMethodIfPossible(initMethod);
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(() -> {
               ReflectionUtils.makeAccessible(methodToInvoke);
               return null;
            });

            try {
               AccessController.doPrivileged(() -> {
                  return methodToInvoke.invoke(bean);
               }, this.getAccessControlContext());
            } catch (PrivilegedActionException var10) {
               InvocationTargetException ex = (InvocationTargetException)var10.getException();
               throw ex.getTargetException();
            }
         } else {
            try {
               ReflectionUtils.makeAccessible(methodToInvoke);
               methodToInvoke.invoke(bean);
            } catch (InvocationTargetException var9) {
               throw var9.getTargetException();
            }
         }

      }
   }

   protected Object postProcessObjectFromFactoryBean(Object object, String beanName) {
      return this.applyBeanPostProcessorsAfterInitialization(object, beanName);
   }

   protected void removeSingleton(String beanName) {
      synchronized(this.getSingletonMutex()) {
         super.removeSingleton(beanName);
         this.factoryBeanInstanceCache.remove(beanName);
      }
   }

   protected void clearSingletonCache() {
      synchronized(this.getSingletonMutex()) {
         super.clearSingletonCache();
         this.factoryBeanInstanceCache.clear();
      }
   }

   Log getLogger() {
      return this.logger;
   }

   private static class AutowireByTypeDependencyDescriptor extends DependencyDescriptor {
      public AutowireByTypeDependencyDescriptor(MethodParameter methodParameter, boolean eager) {
         super(methodParameter, false, eager);
      }

      public String getDependencyName() {
         return null;
      }
   }
}
