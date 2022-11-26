package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ConstraintValidatorManager {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   static ConstraintValidator DUMMY_CONSTRAINT_VALIDATOR = new ConstraintValidator() {
      public boolean isValid(Object value, ConstraintValidatorContext context) {
         return false;
      }
   };
   private final ConstraintValidatorFactory defaultConstraintValidatorFactory;
   private final HibernateConstraintValidatorInitializationContext defaultConstraintValidatorInitializationContext;
   private volatile ConstraintValidatorFactory mostRecentlyUsedNonDefaultConstraintValidatorFactory;
   private volatile HibernateConstraintValidatorInitializationContext mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext;
   private final Object mostRecentlyUsedNonDefaultConstraintValidatorFactoryAndInitializationContextMutex = new Object();
   private final ConcurrentHashMap constraintValidatorCache;

   public ConstraintValidatorManager(ConstraintValidatorFactory defaultConstraintValidatorFactory, HibernateConstraintValidatorInitializationContext defaultConstraintValidatorInitializationContext) {
      this.defaultConstraintValidatorFactory = defaultConstraintValidatorFactory;
      this.defaultConstraintValidatorInitializationContext = defaultConstraintValidatorInitializationContext;
      this.constraintValidatorCache = new ConcurrentHashMap();
   }

   public ConstraintValidator getInitializedValidator(Type validatedValueType, ConstraintDescriptorImpl descriptor, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext initializationContext) {
      Contracts.assertNotNull(validatedValueType);
      Contracts.assertNotNull(descriptor);
      Contracts.assertNotNull(constraintValidatorFactory);
      Contracts.assertNotNull(initializationContext);
      CacheKey key = new CacheKey(descriptor.getAnnotationDescriptor(), validatedValueType, constraintValidatorFactory, initializationContext);
      ConstraintValidator constraintValidator = (ConstraintValidator)this.constraintValidatorCache.get(key);
      if (constraintValidator == null) {
         constraintValidator = this.createAndInitializeValidator(validatedValueType, descriptor, constraintValidatorFactory, initializationContext);
         constraintValidator = this.cacheValidator(key, constraintValidator);
      } else {
         LOG.tracef("Constraint validator %s found in cache.", constraintValidator);
      }

      return DUMMY_CONSTRAINT_VALIDATOR == constraintValidator ? null : constraintValidator;
   }

   private ConstraintValidator cacheValidator(CacheKey key, ConstraintValidator constraintValidator) {
      if (key.getConstraintValidatorFactory() != this.defaultConstraintValidatorFactory && key.getConstraintValidatorFactory() != this.mostRecentlyUsedNonDefaultConstraintValidatorFactory || key.getConstraintValidatorInitializationContext() != this.defaultConstraintValidatorInitializationContext && key.getConstraintValidatorInitializationContext() != this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext) {
         synchronized(this.mostRecentlyUsedNonDefaultConstraintValidatorFactoryAndInitializationContextMutex) {
            if (key.constraintValidatorFactory != this.mostRecentlyUsedNonDefaultConstraintValidatorFactory || key.constraintValidatorInitializationContext != this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext) {
               this.clearEntries(this.mostRecentlyUsedNonDefaultConstraintValidatorFactory, this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext);
               this.mostRecentlyUsedNonDefaultConstraintValidatorFactory = key.getConstraintValidatorFactory();
               this.mostRecentlyUsedNonDefaultConstraintValidatorInitializationContext = key.getConstraintValidatorInitializationContext();
            }
         }
      }

      ConstraintValidator cached = (ConstraintValidator)this.constraintValidatorCache.putIfAbsent(key, constraintValidator);
      return cached != null ? cached : constraintValidator;
   }

   private ConstraintValidator createAndInitializeValidator(Type validatedValueType, ConstraintDescriptorImpl descriptor, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext initializationContext) {
      ConstraintValidatorDescriptor validatorDescriptor = this.findMatchingValidatorDescriptor(descriptor, validatedValueType);
      ConstraintValidator constraintValidator;
      if (validatorDescriptor == null) {
         constraintValidator = DUMMY_CONSTRAINT_VALIDATOR;
      } else {
         constraintValidator = validatorDescriptor.newInstance(constraintValidatorFactory);
         this.initializeValidator(descriptor, constraintValidator, initializationContext);
      }

      return constraintValidator;
   }

   private void clearEntries(ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
      Iterator cacheEntries = this.constraintValidatorCache.entrySet().iterator();

      while(cacheEntries.hasNext()) {
         Map.Entry cacheEntry = (Map.Entry)cacheEntries.next();
         if (((CacheKey)cacheEntry.getKey()).getConstraintValidatorFactory() == constraintValidatorFactory && ((CacheKey)cacheEntry.getKey()).getConstraintValidatorInitializationContext() == constraintValidatorInitializationContext) {
            constraintValidatorFactory.releaseInstance((ConstraintValidator)cacheEntry.getValue());
            cacheEntries.remove();
         }
      }

   }

   public void clear() {
      Iterator var1 = this.constraintValidatorCache.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         ((CacheKey)entry.getKey()).getConstraintValidatorFactory().releaseInstance((ConstraintValidator)entry.getValue());
      }

      this.constraintValidatorCache.clear();
   }

   public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
      return this.defaultConstraintValidatorFactory;
   }

   public HibernateConstraintValidatorInitializationContext getDefaultConstraintValidatorInitializationContext() {
      return this.defaultConstraintValidatorInitializationContext;
   }

   public int numberOfCachedConstraintValidatorInstances() {
      return this.constraintValidatorCache.size();
   }

   private ConstraintValidatorDescriptor findMatchingValidatorDescriptor(ConstraintDescriptorImpl descriptor, Type validatedValueType) {
      Map availableValidatorDescriptors = TypeHelper.getValidatorTypes(descriptor.getAnnotationType(), descriptor.getMatchingConstraintValidatorDescriptors());
      List discoveredSuitableTypes = this.findSuitableValidatorTypes(validatedValueType, availableValidatorDescriptors.keySet());
      this.resolveAssignableTypes(discoveredSuitableTypes);
      if (discoveredSuitableTypes.size() == 0) {
         return null;
      } else if (discoveredSuitableTypes.size() > 1) {
         throw LOG.getMoreThanOneValidatorFoundForTypeException(validatedValueType, discoveredSuitableTypes);
      } else {
         Type suitableType = (Type)discoveredSuitableTypes.get(0);
         return (ConstraintValidatorDescriptor)availableValidatorDescriptors.get(suitableType);
      }
   }

   private List findSuitableValidatorTypes(Type type, Iterable availableValidatorTypes) {
      List determinedSuitableTypes = CollectionHelper.newArrayList();
      Iterator var4 = availableValidatorTypes.iterator();

      while(var4.hasNext()) {
         Type validatorType = (Type)var4.next();
         if (TypeHelper.isAssignable(validatorType, type) && !determinedSuitableTypes.contains(validatorType)) {
            determinedSuitableTypes.add(validatorType);
         }
      }

      return determinedSuitableTypes;
   }

   private void initializeValidator(ConstraintDescriptor descriptor, ConstraintValidator constraintValidator, HibernateConstraintValidatorInitializationContext initializationContext) {
      try {
         if (constraintValidator instanceof HibernateConstraintValidator) {
            ((HibernateConstraintValidator)constraintValidator).initialize(descriptor, initializationContext);
         }

         constraintValidator.initialize(descriptor.getAnnotation());
      } catch (RuntimeException var5) {
         if (var5 instanceof ConstraintDeclarationException) {
            throw var5;
         } else {
            throw LOG.getUnableToInitializeConstraintValidatorException(constraintValidator.getClass(), var5);
         }
      }
   }

   private void resolveAssignableTypes(List assignableTypes) {
      if (assignableTypes.size() != 0 && assignableTypes.size() != 1) {
         List typesToRemove = new ArrayList();

         do {
            typesToRemove.clear();
            Type type = (Type)assignableTypes.get(0);

            for(int i = 1; i < assignableTypes.size(); ++i) {
               if (TypeHelper.isAssignable(type, (Type)assignableTypes.get(i))) {
                  typesToRemove.add(type);
               } else if (TypeHelper.isAssignable((Type)assignableTypes.get(i), type)) {
                  typesToRemove.add(assignableTypes.get(i));
               }
            }

            assignableTypes.removeAll(typesToRemove);
         } while(typesToRemove.size() > 0);

      }
   }

   private static final class CacheKey {
      private ConstraintAnnotationDescriptor annotationDescriptor;
      private Type validatedType;
      private ConstraintValidatorFactory constraintValidatorFactory;
      private HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;
      private int hashCode;

      private CacheKey(ConstraintAnnotationDescriptor annotationDescriptor, Type validatorType, ConstraintValidatorFactory constraintValidatorFactory, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
         this.annotationDescriptor = annotationDescriptor;
         this.validatedType = validatorType;
         this.constraintValidatorFactory = constraintValidatorFactory;
         this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
         this.hashCode = this.createHashCode();
      }

      public ConstraintValidatorFactory getConstraintValidatorFactory() {
         return this.constraintValidatorFactory;
      }

      public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext() {
         return this.constraintValidatorInitializationContext;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o == null) {
            return false;
         } else {
            CacheKey other = (CacheKey)o;
            if (!this.annotationDescriptor.equals(other.annotationDescriptor)) {
               return false;
            } else if (!this.validatedType.equals(other.validatedType)) {
               return false;
            } else if (!this.constraintValidatorFactory.equals(other.constraintValidatorFactory)) {
               return false;
            } else {
               return this.constraintValidatorInitializationContext.equals(other.constraintValidatorInitializationContext);
            }
         }
      }

      public int hashCode() {
         return this.hashCode;
      }

      private int createHashCode() {
         int result = this.annotationDescriptor.hashCode();
         result = 31 * result + this.validatedType.hashCode();
         result = 31 * result + this.constraintValidatorFactory.hashCode();
         result = 31 * result + this.constraintValidatorInitializationContext.hashCode();
         return result;
      }

      // $FF: synthetic method
      CacheKey(ConstraintAnnotationDescriptor x0, Type x1, ConstraintValidatorFactory x2, HibernateConstraintValidatorInitializationContext x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
