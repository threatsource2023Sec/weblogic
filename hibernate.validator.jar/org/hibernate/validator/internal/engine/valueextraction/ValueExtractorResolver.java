package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.TypeVariableBindings;
import org.hibernate.validator.internal.util.TypeVariables;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ValueExtractorResolver {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Set registeredValueExtractors;
   private final ConcurrentHashMap possibleValueExtractorsByRuntimeTypeAndTypeParameter = new ConcurrentHashMap();
   private final ConcurrentHashMap possibleValueExtractorsByRuntimeType = new ConcurrentHashMap();
   private final Set nonContainerTypes = Collections.newSetFromMap(new ConcurrentHashMap());

   ValueExtractorResolver(Set valueExtractors) {
      this.registeredValueExtractors = CollectionHelper.toImmutableSet(valueExtractors);
   }

   public Set getMaximallySpecificValueExtractors(Class declaredType) {
      return this.getRuntimeCompliantValueExtractors(declaredType, this.registeredValueExtractors);
   }

   public ValueExtractorDescriptor getMaximallySpecificAndContainerElementCompliantValueExtractor(Class declaredType, TypeVariable typeParameter) {
      return this.getUniqueValueExtractorOrThrowException(declaredType, this.getRuntimeAndContainerElementCompliantValueExtractorsFromPossibleCandidates(declaredType, typeParameter, declaredType, this.registeredValueExtractors));
   }

   public ValueExtractorDescriptor getMaximallySpecificAndRuntimeContainerElementCompliantValueExtractor(Type declaredType, TypeVariable typeParameter, Class runtimeType, Collection valueExtractorCandidates) {
      Contracts.assertNotEmpty(valueExtractorCandidates, "Value extractor candidates cannot be empty");
      return valueExtractorCandidates.size() == 1 ? (ValueExtractorDescriptor)valueExtractorCandidates.iterator().next() : this.getUniqueValueExtractorOrThrowException(runtimeType, this.getRuntimeAndContainerElementCompliantValueExtractorsFromPossibleCandidates(declaredType, typeParameter, runtimeType, valueExtractorCandidates));
   }

   public ValueExtractorDescriptor getMaximallySpecificValueExtractorForAllContainerElements(Class runtimeType, Set potentialValueExtractorDescriptors) {
      return TypeHelper.isAssignable(Map.class, runtimeType) ? MapValueExtractor.DESCRIPTOR : this.getUniqueValueExtractorOrThrowException(runtimeType, this.getRuntimeCompliantValueExtractors(runtimeType, potentialValueExtractorDescriptors));
   }

   public Set getValueExtractorCandidatesForCascadedValidation(Type declaredType, TypeVariable typeParameter) {
      Set valueExtractorDescriptors = new HashSet();
      valueExtractorDescriptors.addAll(this.getRuntimeAndContainerElementCompliantValueExtractorsFromPossibleCandidates(declaredType, typeParameter, TypeHelper.getErasedReferenceType(declaredType), this.registeredValueExtractors));
      valueExtractorDescriptors.addAll(this.getPotentiallyRuntimeTypeCompliantAndContainerElementCompliantValueExtractors(declaredType, typeParameter));
      return CollectionHelper.toImmutableSet(valueExtractorDescriptors);
   }

   public Set getValueExtractorCandidatesForContainerDetectionOfGlobalCascadedValidation(Type enclosingType) {
      boolean mapAssignable = TypeHelper.isAssignable(Map.class, enclosingType);
      Class enclosingClass = ReflectionHelper.getClassFromType(enclosingType);
      return (Set)this.getRuntimeCompliantValueExtractors(enclosingClass, this.registeredValueExtractors).stream().filter((ved) -> {
         return !mapAssignable || !ved.equals(MapKeyExtractor.DESCRIPTOR);
      }).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionHelper::toImmutableSet));
   }

   public Set getPotentialValueExtractorCandidatesForCascadedValidation(Type declaredType) {
      return (Set)this.registeredValueExtractors.stream().filter((e) -> {
         return TypeHelper.isAssignable(declaredType, e.getContainerType());
      }).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionHelper::toImmutableSet));
   }

   public void clear() {
      this.nonContainerTypes.clear();
      this.possibleValueExtractorsByRuntimeType.clear();
      this.possibleValueExtractorsByRuntimeTypeAndTypeParameter.clear();
   }

   private Set getPotentiallyRuntimeTypeCompliantAndContainerElementCompliantValueExtractors(Type declaredType, TypeVariable typeParameter) {
      boolean isInternal = TypeVariables.isInternal(typeParameter);
      Type erasedDeclaredType = TypeHelper.getErasedReferenceType(declaredType);
      Set typeCompatibleExtractors = (Set)this.registeredValueExtractors.stream().filter((e) -> {
         return TypeHelper.isAssignable(erasedDeclaredType, e.getContainerType());
      }).collect(Collectors.toSet());
      Set containerElementCompliantExtractors = new HashSet();
      Iterator var7 = typeCompatibleExtractors.iterator();

      while(var7.hasNext()) {
         ValueExtractorDescriptor extractorDescriptor = (ValueExtractorDescriptor)var7.next();
         TypeVariable typeParameterBoundToExtractorType;
         if (!isInternal) {
            Map allBindings = TypeVariableBindings.getTypeVariableBindings(extractorDescriptor.getContainerType());
            Map bindingsForExtractorType = (Map)allBindings.get(erasedDeclaredType);
            typeParameterBoundToExtractorType = this.bind(extractorDescriptor.getExtractedTypeParameter(), bindingsForExtractorType);
         } else {
            typeParameterBoundToExtractorType = typeParameter;
         }

         if (Objects.equals(typeParameter, typeParameterBoundToExtractorType)) {
            containerElementCompliantExtractors.add(extractorDescriptor);
         }
      }

      return containerElementCompliantExtractors;
   }

   private ValueExtractorDescriptor getUniqueValueExtractorOrThrowException(Class runtimeType, Set maximallySpecificContainerElementCompliantValueExtractors) {
      if (maximallySpecificContainerElementCompliantValueExtractors.size() == 1) {
         return (ValueExtractorDescriptor)maximallySpecificContainerElementCompliantValueExtractors.iterator().next();
      } else if (maximallySpecificContainerElementCompliantValueExtractors.isEmpty()) {
         return null;
      } else {
         throw LOG.getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException(runtimeType, ValueExtractorHelper.toValueExtractorClasses(maximallySpecificContainerElementCompliantValueExtractors));
      }
   }

   private Set getMaximallySpecificValueExtractors(Set possibleValueExtractors) {
      Set valueExtractorDescriptors = CollectionHelper.newHashSet(possibleValueExtractors.size());
      Iterator var3 = possibleValueExtractors.iterator();

      while(true) {
         while(var3.hasNext()) {
            ValueExtractorDescriptor descriptor = (ValueExtractorDescriptor)var3.next();
            if (valueExtractorDescriptors.isEmpty()) {
               valueExtractorDescriptors.add(descriptor);
            } else {
               Iterator candidatesIterator = valueExtractorDescriptors.iterator();
               boolean isNewRoot = true;

               while(candidatesIterator.hasNext()) {
                  ValueExtractorDescriptor candidate = (ValueExtractorDescriptor)candidatesIterator.next();
                  if (!candidate.getContainerType().equals(descriptor.getContainerType())) {
                     if (TypeHelper.isAssignable(candidate.getContainerType(), descriptor.getContainerType())) {
                        candidatesIterator.remove();
                     } else if (TypeHelper.isAssignable(descriptor.getContainerType(), candidate.getContainerType())) {
                        isNewRoot = false;
                     }
                  }
               }

               if (isNewRoot) {
                  valueExtractorDescriptors.add(descriptor);
               }
            }
         }

         return valueExtractorDescriptors;
      }
   }

   private Set getRuntimeCompliantValueExtractors(Class runtimeType, Set potentialValueExtractorDescriptors) {
      if (this.nonContainerTypes.contains(runtimeType)) {
         return Collections.emptySet();
      } else {
         Set valueExtractorDescriptors = (Set)this.possibleValueExtractorsByRuntimeType.get(runtimeType);
         if (valueExtractorDescriptors != null) {
            return valueExtractorDescriptors;
         } else {
            Set possibleValueExtractors = (Set)potentialValueExtractorDescriptors.stream().filter((e) -> {
               return TypeHelper.isAssignable(e.getContainerType(), runtimeType);
            }).collect(Collectors.toSet());
            valueExtractorDescriptors = this.getMaximallySpecificValueExtractors(possibleValueExtractors);
            if (valueExtractorDescriptors.isEmpty()) {
               this.nonContainerTypes.add(runtimeType);
               return Collections.emptySet();
            } else {
               Set valueExtractorDescriptorsToCache = CollectionHelper.toImmutableSet(valueExtractorDescriptors);
               Set cachedValueExtractorDescriptors = (Set)this.possibleValueExtractorsByRuntimeType.putIfAbsent(runtimeType, valueExtractorDescriptorsToCache);
               return cachedValueExtractorDescriptors != null ? cachedValueExtractorDescriptors : valueExtractorDescriptorsToCache;
            }
         }
      }
   }

   private Set getRuntimeAndContainerElementCompliantValueExtractorsFromPossibleCandidates(Type declaredType, TypeVariable typeParameter, Class runtimeType, Collection valueExtractorCandidates) {
      if (this.nonContainerTypes.contains(runtimeType)) {
         return Collections.emptySet();
      } else {
         ValueExtractorCacheKey cacheKey = new ValueExtractorCacheKey(runtimeType, typeParameter);
         Set valueExtractorDescriptors = (Set)this.possibleValueExtractorsByRuntimeTypeAndTypeParameter.get(cacheKey);
         if (valueExtractorDescriptors != null) {
            return valueExtractorDescriptors;
         } else {
            boolean isInternal = TypeVariables.isInternal(typeParameter);
            Class erasedDeclaredType = TypeHelper.getErasedReferenceType(declaredType);
            Set possibleValueExtractors = (Set)valueExtractorCandidates.stream().filter((e) -> {
               return TypeHelper.isAssignable(e.getContainerType(), runtimeType);
            }).filter((extractorDescriptor) -> {
               return this.checkValueExtractorTypeCompatibility(typeParameter, isInternal, erasedDeclaredType, extractorDescriptor);
            }).collect(Collectors.toSet());
            valueExtractorDescriptors = this.getMaximallySpecificValueExtractors(possibleValueExtractors);
            if (valueExtractorDescriptors.isEmpty()) {
               this.nonContainerTypes.add(runtimeType);
               return Collections.emptySet();
            } else {
               Set valueExtractorDescriptorsToCache = CollectionHelper.toImmutableSet(valueExtractorDescriptors);
               Set cachedValueExtractorDescriptors = (Set)this.possibleValueExtractorsByRuntimeTypeAndTypeParameter.putIfAbsent(cacheKey, valueExtractorDescriptorsToCache);
               return cachedValueExtractorDescriptors != null ? cachedValueExtractorDescriptors : valueExtractorDescriptorsToCache;
            }
         }
      }
   }

   private boolean checkValueExtractorTypeCompatibility(TypeVariable typeParameter, boolean isInternal, Class erasedDeclaredType, ValueExtractorDescriptor extractorDescriptor) {
      return TypeHelper.isAssignable(extractorDescriptor.getContainerType(), erasedDeclaredType) ? this.validateValueExtractorCompatibility(isInternal, erasedDeclaredType, extractorDescriptor.getContainerType(), typeParameter, extractorDescriptor.getExtractedTypeParameter()) : this.validateValueExtractorCompatibility(isInternal, extractorDescriptor.getContainerType(), erasedDeclaredType, extractorDescriptor.getExtractedTypeParameter(), typeParameter);
   }

   private boolean validateValueExtractorCompatibility(boolean isInternal, Class typeForBinding, Class typeToBind, TypeVariable typeParameterForBinding, TypeVariable typeParameterToCompare) {
      TypeVariable typeParameterBoundToExtractorType;
      if (!isInternal) {
         Map allBindings = TypeVariableBindings.getTypeVariableBindings(typeForBinding);
         Map bindingsForExtractorType = (Map)allBindings.get(typeToBind);
         typeParameterBoundToExtractorType = this.bind(typeParameterForBinding, bindingsForExtractorType);
      } else {
         typeParameterBoundToExtractorType = typeParameterForBinding;
      }

      return Objects.equals(typeParameterToCompare, typeParameterBoundToExtractorType);
   }

   private TypeVariable bind(TypeVariable typeParameter, Map bindings) {
      return bindings != null ? (TypeVariable)bindings.get(typeParameter) : null;
   }

   private static class ValueExtractorCacheKey {
      private Class type;
      private TypeVariable typeParameter;
      private int hashCode;

      ValueExtractorCacheKey(Class type, TypeVariable typeParameter) {
         this.type = type;
         this.typeParameter = typeParameter;
         this.hashCode = this.buildHashCode();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o == null) {
            return false;
         } else {
            ValueExtractorCacheKey that = (ValueExtractorCacheKey)o;
            return Objects.equals(this.type, that.type) && Objects.equals(this.typeParameter, that.typeParameter);
         }
      }

      public int hashCode() {
         return this.hashCode;
      }

      private int buildHashCode() {
         int result = this.type.hashCode();
         result = 31 * result + (this.typeParameter != null ? this.typeParameter.hashCode() : 0);
         return result;
      }
   }
}
