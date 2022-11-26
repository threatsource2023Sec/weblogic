package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Set;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class PotentiallyContainerCascadingMetaData implements CascadingMetaData {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final GroupConversionHelper groupConversionHelper;
   private final Set potentialValueExtractorDescriptors;

   public static PotentiallyContainerCascadingMetaData of(CascadingMetaDataBuilder cascadingMetaDataBuilder, Set potentialValueExtractorDescriptors, Object context) {
      return new PotentiallyContainerCascadingMetaData(cascadingMetaDataBuilder, potentialValueExtractorDescriptors);
   }

   private PotentiallyContainerCascadingMetaData(CascadingMetaDataBuilder cascadingMetaDataBuilder, Set potentialValueExtractorDescriptors) {
      this(potentialValueExtractorDescriptors, GroupConversionHelper.of(cascadingMetaDataBuilder.getGroupConversions()));
   }

   private PotentiallyContainerCascadingMetaData(Set potentialValueExtractorDescriptors, GroupConversionHelper groupConversionHelper) {
      this.potentialValueExtractorDescriptors = potentialValueExtractorDescriptors;
      this.groupConversionHelper = groupConversionHelper;
   }

   public TypeVariable getTypeParameter() {
      return AnnotatedObject.INSTANCE;
   }

   public boolean isCascading() {
      return true;
   }

   public boolean isMarkedForCascadingOnAnnotatedObjectOrContainerElements() {
      return true;
   }

   public Class convertGroup(Class originalGroup) {
      return this.groupConversionHelper.convertGroup(originalGroup);
   }

   public Set getGroupConversionDescriptors() {
      return this.groupConversionHelper.asDescriptors();
   }

   public boolean isContainer() {
      return false;
   }

   public CascadingMetaData addRuntimeContainerSupport(ValueExtractorManager valueExtractorManager, Class valueClass) {
      ValueExtractorDescriptor compliantValueExtractor = valueExtractorManager.getResolver().getMaximallySpecificValueExtractorForAllContainerElements(valueClass, this.potentialValueExtractorDescriptors);
      return (CascadingMetaData)(compliantValueExtractor == null ? this : new ContainerCascadingMetaData(valueClass, Collections.singletonList(new ContainerCascadingMetaData(compliantValueExtractor.getContainerType(), compliantValueExtractor.getExtractedTypeParameter(), compliantValueExtractor.getContainerType(), compliantValueExtractor.getExtractedTypeParameter(), this.groupConversionHelper.isEmpty() ? GroupConversionHelper.EMPTY : this.groupConversionHelper)), this.groupConversionHelper, Collections.singleton(compliantValueExtractor)));
   }

   public CascadingMetaData as(Class clazz) {
      if (clazz.isAssignableFrom(this.getClass())) {
         return this;
      } else {
         throw LOG.getUnableToCastException(this, clazz);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getSimpleName());
      sb.append(" [");
      sb.append("groupConversions=").append(this.groupConversionHelper).append(", ");
      sb.append("]");
      return sb.toString();
   }
}
