package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.BeanWrapper;
import com.bea.core.repackaged.springframework.beans.BeanWrapperImpl;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeMismatchException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.InjectionPoint;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.UnsatisfiedDependencyException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.core.CollectionFactory;
import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.MethodInvoker;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.ConstructorProperties;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ConstructorResolver {
   private static final Object[] EMPTY_ARGS = new Object[0];
   private static final NamedThreadLocal currentInjectionPoint = new NamedThreadLocal("Current injection point");
   private final AbstractAutowireCapableBeanFactory beanFactory;
   private final Log logger;

   public ConstructorResolver(AbstractAutowireCapableBeanFactory beanFactory) {
      this.beanFactory = beanFactory;
      this.logger = beanFactory.getLogger();
   }

   public BeanWrapper autowireConstructor(String beanName, RootBeanDefinition mbd, @Nullable Constructor[] chosenCtors, @Nullable Object[] explicitArgs) {
      BeanWrapperImpl bw = new BeanWrapperImpl();
      this.beanFactory.initBeanWrapper(bw);
      Constructor constructorToUse = null;
      ArgumentsHolder argsHolderToUse = null;
      Object[] argsToUse = null;
      if (explicitArgs != null) {
         argsToUse = explicitArgs;
      } else {
         Object[] argsToResolve = null;
         synchronized(mbd.constructorArgumentLock) {
            constructorToUse = (Constructor)mbd.resolvedConstructorOrFactoryMethod;
            if (constructorToUse != null && mbd.constructorArgumentsResolved) {
               argsToUse = mbd.resolvedConstructorArguments;
               if (argsToUse == null) {
                  argsToResolve = mbd.preparedConstructorArguments;
               }
            }
         }

         if (argsToResolve != null) {
            argsToUse = this.resolvePreparedArguments(beanName, mbd, bw, constructorToUse, argsToResolve, true);
         }
      }

      if (constructorToUse == null || argsToUse == null) {
         Constructor[] candidates = chosenCtors;
         if (chosenCtors == null) {
            Class beanClass = mbd.getBeanClass();

            try {
               candidates = mbd.isNonPublicAccessAllowed() ? beanClass.getDeclaredConstructors() : beanClass.getConstructors();
            } catch (Throwable var25) {
               throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Resolution of declared constructors on bean Class [" + beanClass.getName() + "] from ClassLoader [" + beanClass.getClassLoader() + "] failed", var25);
            }
         }

         if (candidates.length == 1 && explicitArgs == null && !mbd.hasConstructorArgumentValues()) {
            Constructor uniqueCandidate = candidates[0];
            if (uniqueCandidate.getParameterCount() == 0) {
               synchronized(mbd.constructorArgumentLock) {
                  mbd.resolvedConstructorOrFactoryMethod = uniqueCandidate;
                  mbd.constructorArgumentsResolved = true;
                  mbd.resolvedConstructorArguments = EMPTY_ARGS;
               }

               bw.setBeanInstance(this.instantiate(beanName, mbd, uniqueCandidate, EMPTY_ARGS));
               return bw;
            }
         }

         boolean autowiring = chosenCtors != null || mbd.getResolvedAutowireMode() == 3;
         ConstructorArgumentValues resolvedValues = null;
         int minNrOfArgs;
         if (explicitArgs != null) {
            minNrOfArgs = explicitArgs.length;
         } else {
            ConstructorArgumentValues cargs = mbd.getConstructorArgumentValues();
            resolvedValues = new ConstructorArgumentValues();
            minNrOfArgs = this.resolveConstructorArguments(beanName, mbd, bw, cargs, resolvedValues);
         }

         AutowireUtils.sortConstructors(candidates);
         int minTypeDiffWeight = Integer.MAX_VALUE;
         Set ambiguousConstructors = null;
         LinkedList causes = null;
         Constructor[] var16 = candidates;
         int var17 = candidates.length;

         for(int var18 = 0; var18 < var17; ++var18) {
            Constructor candidate = var16[var18];
            Class[] paramTypes = candidate.getParameterTypes();
            if (constructorToUse != null && argsToUse != null && argsToUse.length > paramTypes.length) {
               break;
            }

            if (paramTypes.length >= minNrOfArgs) {
               ArgumentsHolder argsHolder;
               if (resolvedValues != null) {
                  try {
                     String[] paramNames = ConstructorResolver.ConstructorPropertiesChecker.evaluate(candidate, paramTypes.length);
                     if (paramNames == null) {
                        ParameterNameDiscoverer pnd = this.beanFactory.getParameterNameDiscoverer();
                        if (pnd != null) {
                           paramNames = pnd.getParameterNames(candidate);
                        }
                     }

                     argsHolder = this.createArgumentArray(beanName, mbd, resolvedValues, bw, paramTypes, paramNames, this.getUserDeclaredConstructor(candidate), autowiring, candidates.length == 1);
                  } catch (UnsatisfiedDependencyException var26) {
                     if (this.logger.isTraceEnabled()) {
                        this.logger.trace("Ignoring constructor [" + candidate + "] of bean '" + beanName + "': " + var26);
                     }

                     if (causes == null) {
                        causes = new LinkedList();
                     }

                     causes.add(var26);
                     continue;
                  }
               } else {
                  if (paramTypes.length != explicitArgs.length) {
                     continue;
                  }

                  argsHolder = new ArgumentsHolder(explicitArgs);
               }

               int typeDiffWeight = mbd.isLenientConstructorResolution() ? argsHolder.getTypeDifferenceWeight(paramTypes) : argsHolder.getAssignabilityWeight(paramTypes);
               if (typeDiffWeight < minTypeDiffWeight) {
                  constructorToUse = candidate;
                  argsHolderToUse = argsHolder;
                  argsToUse = argsHolder.arguments;
                  minTypeDiffWeight = typeDiffWeight;
                  ambiguousConstructors = null;
               } else if (constructorToUse != null && typeDiffWeight == minTypeDiffWeight) {
                  if (ambiguousConstructors == null) {
                     ambiguousConstructors = new LinkedHashSet();
                     ambiguousConstructors.add(constructorToUse);
                  }

                  ambiguousConstructors.add(candidate);
               }
            }
         }

         if (constructorToUse == null) {
            if (causes == null) {
               throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Could not resolve matching constructor (hint: specify index/type/name arguments for simple parameters to avoid type ambiguities)");
            }

            UnsatisfiedDependencyException ex = (UnsatisfiedDependencyException)causes.removeLast();
            Iterator var33 = causes.iterator();

            while(var33.hasNext()) {
               Exception cause = (Exception)var33.next();
               this.beanFactory.onSuppressedException(cause);
            }

            throw ex;
         }

         if (ambiguousConstructors != null && !mbd.isLenientConstructorResolution()) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Ambiguous constructor matches found in bean '" + beanName + "' (hint: specify index/type/name arguments for simple parameters to avoid type ambiguities): " + ambiguousConstructors);
         }

         if (explicitArgs == null && argsHolderToUse != null) {
            argsHolderToUse.storeCache(mbd, constructorToUse);
         }
      }

      Assert.state(argsToUse != null, "Unresolved constructor arguments");
      bw.setBeanInstance(this.instantiate(beanName, mbd, constructorToUse, argsToUse));
      return bw;
   }

   private Object instantiate(String beanName, RootBeanDefinition mbd, Constructor constructorToUse, Object[] argsToUse) {
      try {
         InstantiationStrategy strategy = this.beanFactory.getInstantiationStrategy();
         return System.getSecurityManager() != null ? AccessController.doPrivileged(() -> {
            return strategy.instantiate(mbd, beanName, this.beanFactory, constructorToUse, argsToUse);
         }, this.beanFactory.getAccessControlContext()) : strategy.instantiate(mbd, beanName, this.beanFactory, constructorToUse, argsToUse);
      } catch (Throwable var6) {
         throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Bean instantiation via constructor failed", var6);
      }
   }

   public void resolveFactoryMethodIfPossible(RootBeanDefinition mbd) {
      Class factoryClass;
      boolean isStatic;
      if (mbd.getFactoryBeanName() != null) {
         factoryClass = this.beanFactory.getType(mbd.getFactoryBeanName());
         isStatic = false;
      } else {
         factoryClass = mbd.getBeanClass();
         isStatic = true;
      }

      Assert.state(factoryClass != null, "Unresolvable factory class");
      factoryClass = ClassUtils.getUserClass(factoryClass);
      Method[] candidates = this.getCandidateMethods(factoryClass, mbd);
      Method uniqueCandidate = null;
      Method[] var6 = candidates;
      int var7 = candidates.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Method candidate = var6[var8];
         if (Modifier.isStatic(candidate.getModifiers()) == isStatic && mbd.isFactoryMethod(candidate)) {
            if (uniqueCandidate == null) {
               uniqueCandidate = candidate;
            } else if (!Arrays.equals(uniqueCandidate.getParameterTypes(), candidate.getParameterTypes())) {
               uniqueCandidate = null;
               break;
            }
         }
      }

      mbd.factoryMethodToIntrospect = uniqueCandidate;
   }

   private Method[] getCandidateMethods(Class factoryClass, RootBeanDefinition mbd) {
      if (System.getSecurityManager() != null) {
         return (Method[])AccessController.doPrivileged(() -> {
            return mbd.isNonPublicAccessAllowed() ? ReflectionUtils.getAllDeclaredMethods(factoryClass) : factoryClass.getMethods();
         });
      } else {
         return mbd.isNonPublicAccessAllowed() ? ReflectionUtils.getAllDeclaredMethods(factoryClass) : factoryClass.getMethods();
      }
   }

   public BeanWrapper instantiateUsingFactoryMethod(String beanName, RootBeanDefinition mbd, @Nullable Object[] explicitArgs) {
      BeanWrapperImpl bw = new BeanWrapperImpl();
      this.beanFactory.initBeanWrapper(bw);
      String factoryBeanName = mbd.getFactoryBeanName();
      Object factoryBean;
      Class factoryClass;
      boolean isStatic;
      if (factoryBeanName != null) {
         if (factoryBeanName.equals(beanName)) {
            throw new BeanDefinitionStoreException(mbd.getResourceDescription(), beanName, "factory-bean reference points back to the same bean definition");
         }

         factoryBean = this.beanFactory.getBean(factoryBeanName);
         if (mbd.isSingleton() && this.beanFactory.containsSingleton(beanName)) {
            throw new ImplicitlyAppearedSingletonException();
         }

         factoryClass = factoryBean.getClass();
         isStatic = false;
      } else {
         if (!mbd.hasBeanClass()) {
            throw new BeanDefinitionStoreException(mbd.getResourceDescription(), beanName, "bean definition declares neither a bean class nor a factory-bean reference");
         }

         factoryBean = null;
         factoryClass = mbd.getBeanClass();
         isStatic = true;
      }

      Method factoryMethodToUse = null;
      ArgumentsHolder argsHolderToUse = null;
      Object[] argsToUse = null;
      if (explicitArgs != null) {
         argsToUse = explicitArgs;
      } else {
         Object[] argsToResolve = null;
         synchronized(mbd.constructorArgumentLock) {
            factoryMethodToUse = (Method)mbd.resolvedConstructorOrFactoryMethod;
            if (factoryMethodToUse != null && mbd.constructorArgumentsResolved) {
               argsToUse = mbd.resolvedConstructorArguments;
               if (argsToUse == null) {
                  argsToResolve = mbd.preparedConstructorArguments;
               }
            }
         }

         if (argsToResolve != null) {
            argsToUse = this.resolvePreparedArguments(beanName, mbd, bw, factoryMethodToUse, argsToResolve, true);
         }
      }

      if (factoryMethodToUse == null || argsToUse == null) {
         factoryClass = ClassUtils.getUserClass(factoryClass);
         Method[] rawCandidates = this.getCandidateMethods(factoryClass, mbd);
         List candidateList = new ArrayList();
         Method[] candidates = rawCandidates;
         int var15 = rawCandidates.length;

         for(int var16 = 0; var16 < var15; ++var16) {
            Method candidate = candidates[var16];
            if (Modifier.isStatic(candidate.getModifiers()) == isStatic && mbd.isFactoryMethod(candidate)) {
               candidateList.add(candidate);
            }
         }

         if (candidateList.size() == 1 && explicitArgs == null && !mbd.hasConstructorArgumentValues()) {
            Method uniqueCandidate = (Method)candidateList.get(0);
            if (uniqueCandidate.getParameterCount() == 0) {
               mbd.factoryMethodToIntrospect = uniqueCandidate;
               synchronized(mbd.constructorArgumentLock) {
                  mbd.resolvedConstructorOrFactoryMethod = uniqueCandidate;
                  mbd.constructorArgumentsResolved = true;
                  mbd.resolvedConstructorArguments = EMPTY_ARGS;
               }

               bw.setBeanInstance(this.instantiate(beanName, mbd, factoryBean, uniqueCandidate, EMPTY_ARGS));
               return bw;
            }
         }

         candidates = (Method[])candidateList.toArray(new Method[0]);
         AutowireUtils.sortFactoryMethods(candidates);
         ConstructorArgumentValues resolvedValues = null;
         boolean autowiring = mbd.getResolvedAutowireMode() == 3;
         int minTypeDiffWeight = Integer.MAX_VALUE;
         Set ambiguousFactoryMethods = null;
         int minNrOfArgs;
         if (explicitArgs != null) {
            minNrOfArgs = explicitArgs.length;
         } else if (mbd.hasConstructorArgumentValues()) {
            ConstructorArgumentValues cargs = mbd.getConstructorArgumentValues();
            resolvedValues = new ConstructorArgumentValues();
            minNrOfArgs = this.resolveConstructorArguments(beanName, mbd, bw, cargs, resolvedValues);
         } else {
            minNrOfArgs = 0;
         }

         LinkedList causes = null;
         Method[] var21 = candidates;
         int var22 = candidates.length;

         int var23;
         for(var23 = 0; var23 < var22; ++var23) {
            Method candidate = var21[var23];
            Class[] paramTypes = candidate.getParameterTypes();
            if (paramTypes.length >= minNrOfArgs) {
               ArgumentsHolder argsHolder;
               if (explicitArgs != null) {
                  if (paramTypes.length != explicitArgs.length) {
                     continue;
                  }

                  argsHolder = new ArgumentsHolder(explicitArgs);
               } else {
                  try {
                     String[] paramNames = null;
                     ParameterNameDiscoverer pnd = this.beanFactory.getParameterNameDiscoverer();
                     if (pnd != null) {
                        paramNames = pnd.getParameterNames(candidate);
                     }

                     argsHolder = this.createArgumentArray(beanName, mbd, resolvedValues, bw, paramTypes, paramNames, candidate, autowiring, candidates.length == 1);
                  } catch (UnsatisfiedDependencyException var30) {
                     if (this.logger.isTraceEnabled()) {
                        this.logger.trace("Ignoring factory method [" + candidate + "] of bean '" + beanName + "': " + var30);
                     }

                     if (causes == null) {
                        causes = new LinkedList();
                     }

                     causes.add(var30);
                     continue;
                  }
               }

               int typeDiffWeight = mbd.isLenientConstructorResolution() ? argsHolder.getTypeDifferenceWeight(paramTypes) : argsHolder.getAssignabilityWeight(paramTypes);
               if (typeDiffWeight < minTypeDiffWeight) {
                  factoryMethodToUse = candidate;
                  argsHolderToUse = argsHolder;
                  argsToUse = argsHolder.arguments;
                  minTypeDiffWeight = typeDiffWeight;
                  ambiguousFactoryMethods = null;
               } else if (factoryMethodToUse != null && typeDiffWeight == minTypeDiffWeight && !mbd.isLenientConstructorResolution() && paramTypes.length == factoryMethodToUse.getParameterCount() && !Arrays.equals(paramTypes, factoryMethodToUse.getParameterTypes())) {
                  if (ambiguousFactoryMethods == null) {
                     ambiguousFactoryMethods = new LinkedHashSet();
                     ambiguousFactoryMethods.add(factoryMethodToUse);
                  }

                  ambiguousFactoryMethods.add(candidate);
               }
            }
         }

         if (factoryMethodToUse == null) {
            if (causes != null) {
               UnsatisfiedDependencyException ex = (UnsatisfiedDependencyException)causes.removeLast();
               Iterator var43 = causes.iterator();

               while(var43.hasNext()) {
                  Exception cause = (Exception)var43.next();
                  this.beanFactory.onSuppressedException(cause);
               }

               throw ex;
            }

            List argTypes = new ArrayList(minNrOfArgs);
            if (explicitArgs != null) {
               Object[] var40 = explicitArgs;
               var23 = explicitArgs.length;

               for(int var45 = 0; var45 < var23; ++var45) {
                  Object arg = var40[var45];
                  argTypes.add(arg != null ? arg.getClass().getSimpleName() : "null");
               }
            } else if (resolvedValues != null) {
               Set valueHolders = new LinkedHashSet(resolvedValues.getArgumentCount());
               valueHolders.addAll(resolvedValues.getIndexedArgumentValues().values());
               valueHolders.addAll(resolvedValues.getGenericArgumentValues());
               Iterator var44 = valueHolders.iterator();

               while(var44.hasNext()) {
                  ConstructorArgumentValues.ValueHolder value = (ConstructorArgumentValues.ValueHolder)var44.next();
                  String argType = value.getType() != null ? ClassUtils.getShortName(value.getType()) : (value.getValue() != null ? value.getValue().getClass().getSimpleName() : "null");
                  argTypes.add(argType);
               }
            }

            String argDesc = StringUtils.collectionToCommaDelimitedString(argTypes);
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "No matching factory method found: " + (mbd.getFactoryBeanName() != null ? "factory bean '" + mbd.getFactoryBeanName() + "'; " : "") + "factory method '" + mbd.getFactoryMethodName() + "(" + argDesc + ")'. Check that a method with the specified name " + (minNrOfArgs > 0 ? "and arguments " : "") + "exists and that it is " + (isStatic ? "static" : "non-static") + ".");
         }

         if (Void.TYPE == factoryMethodToUse.getReturnType()) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Invalid factory method '" + mbd.getFactoryMethodName() + "': needs to have a non-void return type!");
         }

         if (ambiguousFactoryMethods != null) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Ambiguous factory method matches found in bean '" + beanName + "' (hint: specify index/type/name arguments for simple parameters to avoid type ambiguities): " + ambiguousFactoryMethods);
         }

         if (explicitArgs == null && argsHolderToUse != null) {
            mbd.factoryMethodToIntrospect = factoryMethodToUse;
            argsHolderToUse.storeCache(mbd, factoryMethodToUse);
         }
      }

      Assert.state(argsToUse != null, "Unresolved factory method arguments");
      bw.setBeanInstance(this.instantiate(beanName, mbd, factoryBean, factoryMethodToUse, argsToUse));
      return bw;
   }

   private Object instantiate(String beanName, RootBeanDefinition mbd, @Nullable Object factoryBean, Method factoryMethod, Object[] args) {
      try {
         return System.getSecurityManager() != null ? AccessController.doPrivileged(() -> {
            return this.beanFactory.getInstantiationStrategy().instantiate(mbd, beanName, this.beanFactory, factoryBean, factoryMethod, args);
         }, this.beanFactory.getAccessControlContext()) : this.beanFactory.getInstantiationStrategy().instantiate(mbd, beanName, this.beanFactory, factoryBean, factoryMethod, args);
      } catch (Throwable var7) {
         throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Bean instantiation via factory method failed", var7);
      }
   }

   private int resolveConstructorArguments(String beanName, RootBeanDefinition mbd, BeanWrapper bw, ConstructorArgumentValues cargs, ConstructorArgumentValues resolvedValues) {
      TypeConverter customConverter = this.beanFactory.getCustomTypeConverter();
      TypeConverter converter = customConverter != null ? customConverter : bw;
      BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory, beanName, mbd, (TypeConverter)converter);
      int minNrOfArgs = cargs.getArgumentCount();
      Iterator var10 = cargs.getIndexedArgumentValues().entrySet().iterator();

      ConstructorArgumentValues.ValueHolder valueHolder;
      while(var10.hasNext()) {
         Map.Entry entry = (Map.Entry)var10.next();
         int index = (Integer)entry.getKey();
         if (index < 0) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Invalid constructor argument index: " + index);
         }

         if (index > minNrOfArgs) {
            minNrOfArgs = index + 1;
         }

         valueHolder = (ConstructorArgumentValues.ValueHolder)entry.getValue();
         if (valueHolder.isConverted()) {
            resolvedValues.addIndexedArgumentValue(index, valueHolder);
         } else {
            Object resolvedValue = valueResolver.resolveValueIfNecessary("constructor argument", valueHolder.getValue());
            ConstructorArgumentValues.ValueHolder resolvedValueHolder = new ConstructorArgumentValues.ValueHolder(resolvedValue, valueHolder.getType(), valueHolder.getName());
            resolvedValueHolder.setSource(valueHolder);
            resolvedValues.addIndexedArgumentValue(index, resolvedValueHolder);
         }
      }

      var10 = cargs.getGenericArgumentValues().iterator();

      while(var10.hasNext()) {
         ConstructorArgumentValues.ValueHolder valueHolder = (ConstructorArgumentValues.ValueHolder)var10.next();
         if (valueHolder.isConverted()) {
            resolvedValues.addGenericArgumentValue(valueHolder);
         } else {
            Object resolvedValue = valueResolver.resolveValueIfNecessary("constructor argument", valueHolder.getValue());
            valueHolder = new ConstructorArgumentValues.ValueHolder(resolvedValue, valueHolder.getType(), valueHolder.getName());
            valueHolder.setSource(valueHolder);
            resolvedValues.addGenericArgumentValue(valueHolder);
         }
      }

      return minNrOfArgs;
   }

   private ArgumentsHolder createArgumentArray(String beanName, RootBeanDefinition mbd, @Nullable ConstructorArgumentValues resolvedValues, BeanWrapper bw, Class[] paramTypes, @Nullable String[] paramNames, Executable executable, boolean autowiring, boolean fallback) throws UnsatisfiedDependencyException {
      TypeConverter customConverter = this.beanFactory.getCustomTypeConverter();
      TypeConverter converter = customConverter != null ? customConverter : bw;
      ArgumentsHolder args = new ArgumentsHolder(paramTypes.length);
      Set usedValueHolders = new HashSet(paramTypes.length);
      Set autowiredBeanNames = new LinkedHashSet(4);

      for(int paramIndex = 0; paramIndex < paramTypes.length; ++paramIndex) {
         Class paramType = paramTypes[paramIndex];
         String paramName = paramNames != null ? paramNames[paramIndex] : "";
         ConstructorArgumentValues.ValueHolder valueHolder = null;
         if (resolvedValues != null) {
            valueHolder = resolvedValues.getArgumentValue(paramIndex, paramType, paramName, usedValueHolders);
            if (valueHolder == null && (!autowiring || paramTypes.length == resolvedValues.getArgumentCount())) {
               valueHolder = resolvedValues.getGenericArgumentValue((Class)null, (String)null, usedValueHolders);
            }
         }

         Object convertedValue;
         if (valueHolder != null) {
            usedValueHolders.add(valueHolder);
            Object originalValue = valueHolder.getValue();
            if (valueHolder.isConverted()) {
               convertedValue = valueHolder.getConvertedValue();
               args.preparedArguments[paramIndex] = convertedValue;
            } else {
               MethodParameter methodParam = MethodParameter.forExecutable(executable, paramIndex);

               try {
                  convertedValue = ((TypeConverter)converter).convertIfNecessary(originalValue, paramType, methodParam);
               } catch (TypeMismatchException var25) {
                  throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, new InjectionPoint(methodParam), "Could not convert argument value of type [" + ObjectUtils.nullSafeClassName(valueHolder.getValue()) + "] to required type [" + paramType.getName() + "]: " + var25.getMessage());
               }

               Object sourceHolder = valueHolder.getSource();
               if (sourceHolder instanceof ConstructorArgumentValues.ValueHolder) {
                  Object sourceValue = ((ConstructorArgumentValues.ValueHolder)sourceHolder).getValue();
                  args.resolveNecessary = true;
                  args.preparedArguments[paramIndex] = sourceValue;
               }
            }

            args.arguments[paramIndex] = convertedValue;
            args.rawArguments[paramIndex] = originalValue;
         } else {
            MethodParameter methodParam = MethodParameter.forExecutable(executable, paramIndex);
            if (!autowiring) {
               throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, new InjectionPoint(methodParam), "Ambiguous argument values for parameter of type [" + paramType.getName() + "] - did you specify the correct bean references as arguments?");
            }

            try {
               convertedValue = this.resolveAutowiredArgument(methodParam, beanName, autowiredBeanNames, (TypeConverter)converter, fallback);
               args.rawArguments[paramIndex] = convertedValue;
               args.arguments[paramIndex] = convertedValue;
               args.preparedArguments[paramIndex] = new AutowiredArgumentMarker();
               args.resolveNecessary = true;
            } catch (BeansException var24) {
               throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, new InjectionPoint(methodParam), var24);
            }
         }
      }

      Iterator var26 = autowiredBeanNames.iterator();

      while(var26.hasNext()) {
         String autowiredBeanName = (String)var26.next();
         this.beanFactory.registerDependentBean(autowiredBeanName, beanName);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Autowiring by type from bean name '" + beanName + "' via " + (executable instanceof Constructor ? "constructor" : "factory method") + " to bean named '" + autowiredBeanName + "'");
         }
      }

      return args;
   }

   private Object[] resolvePreparedArguments(String beanName, RootBeanDefinition mbd, BeanWrapper bw, Executable executable, Object[] argsToResolve, boolean fallback) {
      TypeConverter customConverter = this.beanFactory.getCustomTypeConverter();
      TypeConverter converter = customConverter != null ? customConverter : bw;
      BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory, beanName, mbd, (TypeConverter)converter);
      Class[] paramTypes = executable.getParameterTypes();
      Object[] resolvedArgs = new Object[argsToResolve.length];

      for(int argIndex = 0; argIndex < argsToResolve.length; ++argIndex) {
         Object argValue = argsToResolve[argIndex];
         MethodParameter methodParam = MethodParameter.forExecutable(executable, argIndex);
         GenericTypeResolver.resolveParameterType(methodParam, executable.getDeclaringClass());
         if (argValue instanceof AutowiredArgumentMarker) {
            argValue = this.resolveAutowiredArgument(methodParam, beanName, (Set)null, (TypeConverter)converter, fallback);
         } else if (argValue instanceof BeanMetadataElement) {
            argValue = valueResolver.resolveValueIfNecessary("constructor argument", argValue);
         } else if (argValue instanceof String) {
            argValue = this.beanFactory.evaluateBeanDefinitionString((String)argValue, mbd);
         }

         Class paramType = paramTypes[argIndex];

         try {
            resolvedArgs[argIndex] = ((TypeConverter)converter).convertIfNecessary(argValue, paramType, methodParam);
         } catch (TypeMismatchException var17) {
            throw new UnsatisfiedDependencyException(mbd.getResourceDescription(), beanName, new InjectionPoint(methodParam), "Could not convert argument value of type [" + ObjectUtils.nullSafeClassName(argValue) + "] to required type [" + paramType.getName() + "]: " + var17.getMessage());
         }
      }

      return resolvedArgs;
   }

   protected Constructor getUserDeclaredConstructor(Constructor constructor) {
      Class declaringClass = constructor.getDeclaringClass();
      Class userClass = ClassUtils.getUserClass(declaringClass);
      if (userClass != declaringClass) {
         try {
            return userClass.getDeclaredConstructor(constructor.getParameterTypes());
         } catch (NoSuchMethodException var5) {
         }
      }

      return constructor;
   }

   @Nullable
   protected Object resolveAutowiredArgument(MethodParameter param, String beanName, @Nullable Set autowiredBeanNames, TypeConverter typeConverter, boolean fallback) {
      Class paramType = param.getParameterType();
      if (InjectionPoint.class.isAssignableFrom(paramType)) {
         InjectionPoint injectionPoint = (InjectionPoint)currentInjectionPoint.get();
         if (injectionPoint == null) {
            throw new IllegalStateException("No current InjectionPoint available for " + param);
         } else {
            return injectionPoint;
         }
      } else {
         try {
            return this.beanFactory.resolveDependency(new DependencyDescriptor(param, true), beanName, autowiredBeanNames, typeConverter);
         } catch (NoUniqueBeanDefinitionException var8) {
            throw var8;
         } catch (NoSuchBeanDefinitionException var9) {
            if (fallback) {
               if (paramType.isArray()) {
                  return Array.newInstance(paramType.getComponentType(), 0);
               }

               if (CollectionFactory.isApproximableCollectionType(paramType)) {
                  return CollectionFactory.createCollection(paramType, 0);
               }

               if (CollectionFactory.isApproximableMapType(paramType)) {
                  return CollectionFactory.createMap(paramType, 0);
               }
            }

            throw var9;
         }
      }
   }

   static InjectionPoint setCurrentInjectionPoint(@Nullable InjectionPoint injectionPoint) {
      InjectionPoint old = (InjectionPoint)currentInjectionPoint.get();
      if (injectionPoint != null) {
         currentInjectionPoint.set(injectionPoint);
      } else {
         currentInjectionPoint.remove();
      }

      return old;
   }

   private static class ConstructorPropertiesChecker {
      @Nullable
      public static String[] evaluate(Constructor candidate, int paramCount) {
         ConstructorProperties cp = (ConstructorProperties)candidate.getAnnotation(ConstructorProperties.class);
         if (cp != null) {
            String[] names = cp.value();
            if (names.length != paramCount) {
               throw new IllegalStateException("Constructor annotated with @ConstructorProperties but not corresponding to actual number of parameters (" + paramCount + "): " + candidate);
            } else {
               return names;
            }
         } else {
            return null;
         }
      }
   }

   private static class AutowiredArgumentMarker {
      private AutowiredArgumentMarker() {
      }

      // $FF: synthetic method
      AutowiredArgumentMarker(Object x0) {
         this();
      }
   }

   private static class ArgumentsHolder {
      public final Object[] rawArguments;
      public final Object[] arguments;
      public final Object[] preparedArguments;
      public boolean resolveNecessary = false;

      public ArgumentsHolder(int size) {
         this.rawArguments = new Object[size];
         this.arguments = new Object[size];
         this.preparedArguments = new Object[size];
      }

      public ArgumentsHolder(Object[] args) {
         this.rawArguments = args;
         this.arguments = args;
         this.preparedArguments = args;
      }

      public int getTypeDifferenceWeight(Class[] paramTypes) {
         int typeDiffWeight = MethodInvoker.getTypeDifferenceWeight(paramTypes, this.arguments);
         int rawTypeDiffWeight = MethodInvoker.getTypeDifferenceWeight(paramTypes, this.rawArguments) - 1024;
         return rawTypeDiffWeight < typeDiffWeight ? rawTypeDiffWeight : typeDiffWeight;
      }

      public int getAssignabilityWeight(Class[] paramTypes) {
         int i;
         for(i = 0; i < paramTypes.length; ++i) {
            if (!ClassUtils.isAssignableValue(paramTypes[i], this.arguments[i])) {
               return Integer.MAX_VALUE;
            }
         }

         for(i = 0; i < paramTypes.length; ++i) {
            if (!ClassUtils.isAssignableValue(paramTypes[i], this.rawArguments[i])) {
               return 2147483135;
            }
         }

         return 2147482623;
      }

      public void storeCache(RootBeanDefinition mbd, Executable constructorOrFactoryMethod) {
         synchronized(mbd.constructorArgumentLock) {
            mbd.resolvedConstructorOrFactoryMethod = constructorOrFactoryMethod;
            mbd.constructorArgumentsResolved = true;
            if (this.resolveNecessary) {
               mbd.preparedConstructorArguments = this.preparedArguments;
            } else {
               mbd.resolvedConstructorArguments = this.arguments;
            }

         }
      }
   }
}
