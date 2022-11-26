package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.GroupSequence;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorHelper;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.TypeVariableBindings;
import org.hibernate.validator.internal.util.TypeVariables;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class CascadingMetaDataBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final CascadingMetaDataBuilder NON_CASCADING = new CascadingMetaDataBuilder((Type)null, (TypeVariable)null, (Class)null, (TypeVariable)null, false, Collections.emptyMap(), Collections.emptyMap());
   private final Type enclosingType;
   private final TypeVariable typeParameter;
   private final Class declaredContainerClass;
   private final TypeVariable declaredTypeParameter;
   private final Map containerElementTypesCascadingMetaData;
   private final boolean cascading;
   private final Map groupConversions;
   private final boolean hasContainerElementsMarkedForCascading;
   private final boolean hasGroupConversionsOnAnnotatedObjectOrContainerElements;

   public CascadingMetaDataBuilder(Type enclosingType, TypeVariable typeParameter, boolean cascading, Map containerElementTypesCascadingMetaData, Map groupConversions) {
      this(enclosingType, typeParameter, TypeVariables.getContainerClass(typeParameter), TypeVariables.getActualTypeParameter(typeParameter), cascading, containerElementTypesCascadingMetaData, groupConversions);
   }

   private CascadingMetaDataBuilder(Type enclosingType, TypeVariable typeParameter, Class declaredContainerClass, TypeVariable declaredTypeParameter, boolean cascading, Map containerElementTypesCascadingMetaData, Map groupConversions) {
      this.enclosingType = enclosingType;
      this.typeParameter = typeParameter;
      this.declaredContainerClass = declaredContainerClass;
      this.declaredTypeParameter = declaredTypeParameter;
      this.cascading = cascading;
      this.groupConversions = CollectionHelper.toImmutableMap(groupConversions);
      this.containerElementTypesCascadingMetaData = CollectionHelper.toImmutableMap(containerElementTypesCascadingMetaData);
      boolean tmpHasContainerElementsMarkedForCascading = false;
      boolean tmpHasGroupConversionsOnAnnotatedObjectOrContainerElements = !groupConversions.isEmpty();

      CascadingMetaDataBuilder nestedCascadingTypeParameter;
      for(Iterator var10 = containerElementTypesCascadingMetaData.values().iterator(); var10.hasNext(); tmpHasGroupConversionsOnAnnotatedObjectOrContainerElements = tmpHasGroupConversionsOnAnnotatedObjectOrContainerElements || nestedCascadingTypeParameter.hasGroupConversionsOnAnnotatedObjectOrContainerElements) {
         nestedCascadingTypeParameter = (CascadingMetaDataBuilder)var10.next();
         tmpHasContainerElementsMarkedForCascading = tmpHasContainerElementsMarkedForCascading || nestedCascadingTypeParameter.cascading || nestedCascadingTypeParameter.hasContainerElementsMarkedForCascading;
      }

      this.hasContainerElementsMarkedForCascading = tmpHasContainerElementsMarkedForCascading;
      this.hasGroupConversionsOnAnnotatedObjectOrContainerElements = tmpHasGroupConversionsOnAnnotatedObjectOrContainerElements;
   }

   public static CascadingMetaDataBuilder nonCascading() {
      return NON_CASCADING;
   }

   public static CascadingMetaDataBuilder annotatedObject(Type cascadableType, boolean cascading, Map containerElementTypesCascadingMetaData, Map groupConversions) {
      return new CascadingMetaDataBuilder(cascadableType, AnnotatedObject.INSTANCE, cascading, containerElementTypesCascadingMetaData, groupConversions);
   }

   public TypeVariable getTypeParameter() {
      return this.typeParameter;
   }

   public Type getEnclosingType() {
      return this.enclosingType;
   }

   public Class getDeclaredContainerClass() {
      return this.declaredContainerClass;
   }

   public TypeVariable getDeclaredTypeParameter() {
      return this.declaredTypeParameter;
   }

   public boolean isCascading() {
      return this.cascading;
   }

   public Map getGroupConversions() {
      return this.groupConversions;
   }

   public boolean hasContainerElementsMarkedForCascading() {
      return this.hasContainerElementsMarkedForCascading;
   }

   public boolean isMarkedForCascadingOnAnnotatedObjectOrContainerElements() {
      return this.cascading || this.hasContainerElementsMarkedForCascading;
   }

   public boolean hasGroupConversionsOnAnnotatedObjectOrContainerElements() {
      return this.hasGroupConversionsOnAnnotatedObjectOrContainerElements;
   }

   public Map getContainerElementTypesCascadingMetaData() {
      return this.containerElementTypesCascadingMetaData;
   }

   public CascadingMetaDataBuilder merge(CascadingMetaDataBuilder otherCascadingTypeParameter) {
      if (this == NON_CASCADING) {
         return otherCascadingTypeParameter;
      } else if (otherCascadingTypeParameter == NON_CASCADING) {
         return this;
      } else {
         boolean cascading = this.cascading || otherCascadingTypeParameter.cascading;
         Map groupConversions = mergeGroupConversion(this.groupConversions, otherCascadingTypeParameter.groupConversions);
         Map nestedCascadingTypeParameterMap = (Map)Stream.concat(this.containerElementTypesCascadingMetaData.entrySet().stream(), otherCascadingTypeParameter.containerElementTypesCascadingMetaData.entrySet().stream()).collect(Collectors.toMap((entry) -> {
            return (TypeVariable)entry.getKey();
         }, (entry) -> {
            return (CascadingMetaDataBuilder)entry.getValue();
         }, (value1, value2) -> {
            return value1.merge(value2);
         }));
         return new CascadingMetaDataBuilder(this.enclosingType, this.typeParameter, cascading, nestedCascadingTypeParameterMap, groupConversions);
      }
   }

   public CascadingMetaData build(ValueExtractorManager valueExtractorManager, Object context) {
      this.validateGroupConversions(context);
      if (!this.cascading) {
         return (CascadingMetaData)(!this.containerElementTypesCascadingMetaData.isEmpty() && this.hasContainerElementsMarkedForCascading ? ContainerCascadingMetaData.of(valueExtractorManager, this, context) : NonContainerCascadingMetaData.of(this, context));
      } else {
         Set containerDetectionValueExtractorCandidates = valueExtractorManager.getResolver().getValueExtractorCandidatesForContainerDetectionOfGlobalCascadedValidation(this.enclosingType);
         if (!containerDetectionValueExtractorCandidates.isEmpty()) {
            if (containerDetectionValueExtractorCandidates.size() > 1) {
               throw LOG.getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException(ReflectionHelper.getClassFromType(this.enclosingType), ValueExtractorHelper.toValueExtractorClasses(containerDetectionValueExtractorCandidates));
            } else {
               return ContainerCascadingMetaData.of(valueExtractorManager, new CascadingMetaDataBuilder(this.enclosingType, this.typeParameter, this.cascading, addCascadingMetaDataBasedOnContainerDetection(this.enclosingType, this.containerElementTypesCascadingMetaData, this.groupConversions, (ValueExtractorDescriptor)containerDetectionValueExtractorCandidates.iterator().next()), this.groupConversions), context);
            }
         } else {
            Set potentialValueExtractorCandidates = valueExtractorManager.getResolver().getPotentialValueExtractorCandidatesForCascadedValidation(this.enclosingType);
            return (CascadingMetaData)(!potentialValueExtractorCandidates.isEmpty() ? PotentiallyContainerCascadingMetaData.of(this, potentialValueExtractorCandidates, context) : NonContainerCascadingMetaData.of(this, context));
         }
      }
   }

   private void validateGroupConversions(Object context) {
      if (!this.cascading && !this.groupConversions.isEmpty()) {
         throw LOG.getGroupConversionOnNonCascadingElementException(context);
      } else {
         Iterator var2 = this.groupConversions.keySet().iterator();

         while(var2.hasNext()) {
            Class group = (Class)var2.next();
            if (group.isAnnotationPresent(GroupSequence.class)) {
               throw LOG.getGroupConversionForSequenceException(group);
            }
         }

         var2 = this.containerElementTypesCascadingMetaData.values().iterator();

         while(var2.hasNext()) {
            CascadingMetaDataBuilder containerElementCascadingTypeParameter = (CascadingMetaDataBuilder)var2.next();
            containerElementCascadingTypeParameter.validateGroupConversions(context);
         }

      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getSimpleName());
      sb.append(" [");
      sb.append("enclosingType=").append(StringHelper.toShortString(this.enclosingType)).append(", ");
      sb.append("typeParameter=").append(this.typeParameter).append(", ");
      sb.append("cascading=").append(this.cascading).append(", ");
      sb.append("groupConversions=").append(this.groupConversions).append(", ");
      sb.append("containerElementTypesCascadingMetaData=").append(this.containerElementTypesCascadingMetaData);
      sb.append("]");
      return sb.toString();
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + this.typeParameter.hashCode();
      result = 31 * result + (this.cascading ? 1 : 0);
      result = 31 * result + this.groupConversions.hashCode();
      result = 31 * result + this.containerElementTypesCascadingMetaData.hashCode();
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
         CascadingMetaDataBuilder other = (CascadingMetaDataBuilder)obj;
         if (!this.typeParameter.equals(other.typeParameter)) {
            return false;
         } else if (this.cascading != other.cascading) {
            return false;
         } else if (!this.groupConversions.equals(other.groupConversions)) {
            return false;
         } else {
            return this.containerElementTypesCascadingMetaData.equals(other.containerElementTypesCascadingMetaData);
         }
      }
   }

   private static Map mergeGroupConversion(Map groupConversions, Map otherGroupConversions) {
      if (groupConversions.isEmpty() && otherGroupConversions.isEmpty()) {
         return Collections.emptyMap();
      } else {
         Map mergedGroupConversions = new HashMap(groupConversions.size() + otherGroupConversions.size());
         Iterator var3 = otherGroupConversions.entrySet().iterator();

         Map.Entry otherGroupConversionEntry;
         do {
            if (!var3.hasNext()) {
               mergedGroupConversions.putAll(groupConversions);
               mergedGroupConversions.putAll(otherGroupConversions);
               return mergedGroupConversions;
            }

            otherGroupConversionEntry = (Map.Entry)var3.next();
         } while(!groupConversions.containsKey(otherGroupConversionEntry.getKey()));

         throw LOG.getMultipleGroupConversionsForSameSourceException((Class)otherGroupConversionEntry.getKey(), CollectionHelper.asSet((Class)groupConversions.get(otherGroupConversionEntry.getKey()), (Class)otherGroupConversionEntry.getValue()));
      }
   }

   private static Map addCascadingMetaDataBasedOnContainerDetection(Type cascadableType, Map containerElementTypesCascadingMetaData, Map groupConversions, ValueExtractorDescriptor possibleValueExtractor) {
      Class cascadableClass = ReflectionHelper.getClassFromType(cascadableType);
      if (cascadableClass.isArray()) {
         return addArrayElementCascadingMetaData(cascadableClass, containerElementTypesCascadingMetaData, groupConversions);
      } else {
         Map cascadingMetaData = addCascadingMetaData(cascadableClass, possibleValueExtractor.getContainerType(), possibleValueExtractor.getExtractedTypeParameter(), containerElementTypesCascadingMetaData, groupConversions);
         return cascadingMetaData;
      }
   }

   private static Map addCascadingMetaData(Class enclosingType, Class referenceType, TypeVariable typeParameter, Map containerElementTypesCascadingMetaData, Map groupConversions) {
      Map typeVariableBindings = TypeVariableBindings.getTypeVariableBindings(enclosingType);
      TypeVariable correspondingTypeParameter = (TypeVariable)((Map)((Map)typeVariableBindings.get(referenceType)).entrySet().stream().filter((e) -> {
         return Objects.equals(((TypeVariable)e.getKey()).getGenericDeclaration(), enclosingType);
      }).collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))).get(typeParameter);
      Class cascadableClass;
      TypeVariable cascadableTypeParameter;
      if (correspondingTypeParameter != null) {
         cascadableClass = enclosingType;
         cascadableTypeParameter = correspondingTypeParameter;
      } else {
         cascadableClass = referenceType;
         cascadableTypeParameter = typeParameter;
      }

      Map amendedCascadingMetadata = CollectionHelper.newHashMap(containerElementTypesCascadingMetaData.size() + 1);
      amendedCascadingMetadata.putAll(containerElementTypesCascadingMetaData);
      if (containerElementTypesCascadingMetaData.containsKey(cascadableTypeParameter)) {
         amendedCascadingMetadata.put(cascadableTypeParameter, makeCascading((CascadingMetaDataBuilder)containerElementTypesCascadingMetaData.get(cascadableTypeParameter), groupConversions));
      } else {
         amendedCascadingMetadata.put(cascadableTypeParameter, new CascadingMetaDataBuilder(cascadableClass, cascadableTypeParameter, enclosingType, correspondingTypeParameter, true, Collections.emptyMap(), groupConversions));
      }

      return amendedCascadingMetadata;
   }

   private static Map addArrayElementCascadingMetaData(Class enclosingType, Map containerElementTypesCascadingMetaData, Map groupConversions) {
      Map amendedCascadingMetadata = CollectionHelper.newHashMap(containerElementTypesCascadingMetaData.size() + 1);
      amendedCascadingMetadata.putAll(containerElementTypesCascadingMetaData);
      TypeVariable cascadableTypeParameter = new ArrayElement(enclosingType);
      amendedCascadingMetadata.put(cascadableTypeParameter, new CascadingMetaDataBuilder(enclosingType, cascadableTypeParameter, true, Collections.emptyMap(), groupConversions));
      return amendedCascadingMetadata;
   }

   private static CascadingMetaDataBuilder makeCascading(CascadingMetaDataBuilder cascadingTypeParameter, Map groupConversions) {
      return new CascadingMetaDataBuilder(cascadingTypeParameter.enclosingType, cascadingTypeParameter.typeParameter, true, cascadingTypeParameter.containerElementTypesCascadingMetaData, cascadingTypeParameter.groupConversions.isEmpty() ? groupConversions : cascadingTypeParameter.groupConversions);
   }
}
