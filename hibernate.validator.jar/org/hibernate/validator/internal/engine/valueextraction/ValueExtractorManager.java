package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

public class ValueExtractorManager {
   public static final Set SPEC_DEFINED_EXTRACTORS;
   private final Map registeredValueExtractors;
   private final ValueExtractorResolver valueExtractorResolver;

   public ValueExtractorManager(Set externalExtractors) {
      LinkedHashMap tmpValueExtractors = new LinkedHashMap();
      Iterator var3 = SPEC_DEFINED_EXTRACTORS.iterator();

      while(var3.hasNext()) {
         ValueExtractorDescriptor descriptor = (ValueExtractorDescriptor)var3.next();
         tmpValueExtractors.put(descriptor.getKey(), descriptor);
      }

      var3 = externalExtractors.iterator();

      while(var3.hasNext()) {
         ValueExtractor valueExtractor = (ValueExtractor)var3.next();
         ValueExtractorDescriptor descriptor = new ValueExtractorDescriptor(valueExtractor);
         tmpValueExtractors.put(descriptor.getKey(), descriptor);
      }

      this.registeredValueExtractors = Collections.unmodifiableMap(tmpValueExtractors);
      this.valueExtractorResolver = new ValueExtractorResolver(new HashSet(this.registeredValueExtractors.values()));
   }

   public ValueExtractorManager(ValueExtractorManager template, Map externalValueExtractorDescriptors) {
      LinkedHashMap tmpValueExtractors = new LinkedHashMap(template.registeredValueExtractors);
      tmpValueExtractors.putAll(externalValueExtractorDescriptors);
      this.registeredValueExtractors = Collections.unmodifiableMap(tmpValueExtractors);
      this.valueExtractorResolver = new ValueExtractorResolver(new HashSet(this.registeredValueExtractors.values()));
   }

   public static Set getDefaultValueExtractors() {
      return (Set)SPEC_DEFINED_EXTRACTORS.stream().map((d) -> {
         return d.getValueExtractor();
      }).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
   }

   public ValueExtractorDescriptor getMaximallySpecificAndRuntimeContainerElementCompliantValueExtractor(Type declaredType, TypeVariable typeParameter, Class runtimeType, Collection valueExtractorCandidates) {
      if (valueExtractorCandidates.size() == 1) {
         return (ValueExtractorDescriptor)valueExtractorCandidates.iterator().next();
      } else {
         return !valueExtractorCandidates.isEmpty() ? this.valueExtractorResolver.getMaximallySpecificAndRuntimeContainerElementCompliantValueExtractor(declaredType, typeParameter, runtimeType, valueExtractorCandidates) : this.valueExtractorResolver.getMaximallySpecificAndRuntimeContainerElementCompliantValueExtractor(declaredType, typeParameter, runtimeType, this.registeredValueExtractors.values());
      }
   }

   public ValueExtractorResolver getResolver() {
      return this.valueExtractorResolver;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.registeredValueExtractors == null ? 0 : this.registeredValueExtractors.hashCode());
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
         ValueExtractorManager other = (ValueExtractorManager)obj;
         return this.registeredValueExtractors.equals(other.registeredValueExtractors);
      }
   }

   private static boolean isJavaFxInClasspath() {
      return isClassPresent("javafx.beans.value.ObservableValue", false);
   }

   private static boolean isClassPresent(String className, boolean fallbackOnTCCL) {
      try {
         run(LoadClass.action(className, ValueExtractorManager.class.getClassLoader(), fallbackOnTCCL));
         return true;
      } catch (ValidationException var3) {
         return false;
      }
   }

   public void clear() {
      this.valueExtractorResolver.clear();
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   static {
      LinkedHashSet specDefinedExtractors = new LinkedHashSet();
      if (isJavaFxInClasspath()) {
         specDefinedExtractors.add(ObservableValueValueExtractor.DESCRIPTOR);
         specDefinedExtractors.add(ListPropertyValueExtractor.DESCRIPTOR);
         specDefinedExtractors.add(ReadOnlyListPropertyValueExtractor.DESCRIPTOR);
         specDefinedExtractors.add(MapPropertyValueExtractor.DESCRIPTOR);
         specDefinedExtractors.add(ReadOnlyMapPropertyValueExtractor.DESCRIPTOR);
         specDefinedExtractors.add(MapPropertyKeyExtractor.DESCRIPTOR);
         specDefinedExtractors.add(ReadOnlyMapPropertyKeyExtractor.DESCRIPTOR);
         specDefinedExtractors.add(SetPropertyValueExtractor.DESCRIPTOR);
         specDefinedExtractors.add(ReadOnlySetPropertyValueExtractor.DESCRIPTOR);
      }

      specDefinedExtractors.add(ByteArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(ShortArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(IntArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(LongArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(FloatArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(DoubleArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(CharArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(BooleanArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(ObjectArrayValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(ListValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(MapValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(MapKeyExtractor.DESCRIPTOR);
      specDefinedExtractors.add(IterableValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(OptionalValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(OptionalIntValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(OptionalDoubleValueExtractor.DESCRIPTOR);
      specDefinedExtractors.add(OptionalLongValueExtractor.DESCRIPTOR);
      SPEC_DEFINED_EXTRACTORS = Collections.unmodifiableSet(specDefinedExtractors);
   }
}
