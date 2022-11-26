package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.annotation.ElementType;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.validation.ElementKind;
import javax.validation.metadata.ParameterDescriptor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ParameterDescriptorImpl;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

public class ParameterMetaData extends AbstractConstraintMetaData implements Cascadable {
   private final int index;
   private final CascadingMetaData cascadingMetaData;

   private ParameterMetaData(int index, String name, Type type, Set constraints, Set containerElementsConstraints, CascadingMetaData cascadingMetaData) {
      super(name, type, constraints, containerElementsConstraints, cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements(), !constraints.isEmpty() || !containerElementsConstraints.isEmpty() || cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements());
      this.index = index;
      this.cascadingMetaData = cascadingMetaData;
   }

   public int getIndex() {
      return this.index;
   }

   public ElementType getElementType() {
      return ElementType.PARAMETER;
   }

   public ParameterDescriptor asDescriptor(boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      return new ParameterDescriptorImpl(this.getType(), this.index, this.getName(), this.asDescriptors(this.getDirectConstraints()), this.asContainerElementTypeDescriptors(this.getContainerElementsConstraints(), this.cascadingMetaData, defaultGroupSequenceRedefined, defaultGroupSequence), this.cascadingMetaData.isCascading(), defaultGroupSequenceRedefined, defaultGroupSequence, this.cascadingMetaData.getGroupConversionDescriptors());
   }

   public Object getValue(Object parent) {
      return ((Object[])((Object[])parent))[this.getIndex()];
   }

   public Type getCascadableType() {
      return this.getType();
   }

   public void appendTo(PathImpl path) {
      path.addParameterNode(this.getName(), this.getIndex());
   }

   public CascadingMetaData getCascadingMetaData() {
      return this.cascadingMetaData;
   }

   public ElementKind getKind() {
      return ElementKind.PARAMETER;
   }

   // $FF: synthetic method
   ParameterMetaData(int x0, String x1, Type x2, Set x3, Set x4, CascadingMetaData x5, Object x6) {
      this(x0, x1, x2, x3, x4, x5);
   }

   public static class Builder extends MetaDataBuilder {
      private final ExecutableParameterNameProvider parameterNameProvider;
      private final Type parameterType;
      private final int parameterIndex;
      private Executable executableForNameRetrieval;
      private CascadingMetaDataBuilder cascadingMetaDataBuilder;

      public Builder(Class beanClass, ConstrainedParameter constrainedParameter, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, ExecutableParameterNameProvider parameterNameProvider) {
         super(beanClass, constraintHelper, typeResolutionHelper, valueExtractorManager);
         this.parameterNameProvider = parameterNameProvider;
         this.parameterType = constrainedParameter.getType();
         this.parameterIndex = constrainedParameter.getIndex();
         this.add(constrainedParameter);
      }

      public boolean accepts(ConstrainedElement constrainedElement) {
         if (constrainedElement.getKind() != ConstrainedElement.ConstrainedElementKind.PARAMETER) {
            return false;
         } else {
            return ((ConstrainedParameter)constrainedElement).getIndex() == this.parameterIndex;
         }
      }

      public void add(ConstrainedElement constrainedElement) {
         super.add(constrainedElement);
         ConstrainedParameter newConstrainedParameter = (ConstrainedParameter)constrainedElement;
         if (this.cascadingMetaDataBuilder == null) {
            this.cascadingMetaDataBuilder = newConstrainedParameter.getCascadingMetaDataBuilder();
         } else {
            this.cascadingMetaDataBuilder = this.cascadingMetaDataBuilder.merge(newConstrainedParameter.getCascadingMetaDataBuilder());
         }

         if (this.executableForNameRetrieval == null || newConstrainedParameter.getExecutable().getDeclaringClass().isAssignableFrom(this.executableForNameRetrieval.getDeclaringClass())) {
            this.executableForNameRetrieval = newConstrainedParameter.getExecutable();
         }

      }

      public ParameterMetaData build() {
         return new ParameterMetaData(this.parameterIndex, (String)this.parameterNameProvider.getParameterNames(this.executableForNameRetrieval).get(this.parameterIndex), this.parameterType, this.adaptOriginsAndImplicitGroups(this.getDirectConstraints()), this.adaptOriginsAndImplicitGroups(this.getContainerElementConstraints()), this.cascadingMetaDataBuilder.build(this.valueExtractorManager, this.executableForNameRetrieval));
      }
   }
}
