package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.TypeVariable;
import java.util.Set;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class NonContainerCascadingMetaData implements CascadingMetaData {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final NonContainerCascadingMetaData NON_CASCADING;
   private static final NonContainerCascadingMetaData CASCADING_WITHOUT_GROUP_CONVERSIONS;
   private final boolean cascading;
   private GroupConversionHelper groupConversionHelper;

   public static NonContainerCascadingMetaData of(CascadingMetaDataBuilder cascadingMetaDataBuilder, Object context) {
      if (!cascadingMetaDataBuilder.isCascading()) {
         return NON_CASCADING;
      } else {
         return cascadingMetaDataBuilder.getGroupConversions().isEmpty() ? CASCADING_WITHOUT_GROUP_CONVERSIONS : new NonContainerCascadingMetaData(cascadingMetaDataBuilder);
      }
   }

   private NonContainerCascadingMetaData(CascadingMetaDataBuilder cascadingMetaDataBuilder) {
      this(cascadingMetaDataBuilder.isCascading(), GroupConversionHelper.of(cascadingMetaDataBuilder.getGroupConversions()));
   }

   private NonContainerCascadingMetaData(boolean cascading, GroupConversionHelper groupConversionHelper) {
      this.cascading = cascading;
      this.groupConversionHelper = groupConversionHelper;
   }

   public TypeVariable getTypeParameter() {
      return AnnotatedObject.INSTANCE;
   }

   public boolean isCascading() {
      return this.cascading;
   }

   public boolean isMarkedForCascadingOnAnnotatedObjectOrContainerElements() {
      return this.cascading;
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
      return this;
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
      sb.append("cascading=").append(this.cascading).append(", ");
      sb.append("groupConversions=").append(this.groupConversionHelper).append(", ");
      sb.append("]");
      return sb.toString();
   }

   static {
      NON_CASCADING = new NonContainerCascadingMetaData(false, GroupConversionHelper.EMPTY);
      CASCADING_WITHOUT_GROUP_CONVERSIONS = new NonContainerCascadingMetaData(true, GroupConversionHelper.EMPTY);
   }
}
