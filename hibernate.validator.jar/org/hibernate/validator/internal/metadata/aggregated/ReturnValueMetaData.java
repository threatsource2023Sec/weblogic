package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.ElementKind;
import javax.validation.metadata.ReturnValueDescriptor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.descriptor.ReturnValueDescriptorImpl;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.facets.Validatable;

public class ReturnValueMetaData extends AbstractConstraintMetaData implements Validatable, Cascadable {
   private static final String RETURN_VALUE_NODE_NAME = null;
   private final List cascadables = this.isCascading() ? Collections.singletonList(this) : Collections.emptyList();
   private final CascadingMetaData cascadingMetaData;

   public ReturnValueMetaData(Type type, Set constraints, Set containerElementsConstraints, CascadingMetaData cascadingMetaData) {
      super(RETURN_VALUE_NODE_NAME, type, constraints, containerElementsConstraints, cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements(), !constraints.isEmpty() || containerElementsConstraints.isEmpty() || cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements());
      this.cascadingMetaData = cascadingMetaData;
   }

   public Iterable getCascadables() {
      return this.cascadables;
   }

   public boolean hasCascadables() {
      return !this.cascadables.isEmpty();
   }

   public ElementType getElementType() {
      return ElementType.METHOD;
   }

   public ReturnValueDescriptor asDescriptor(boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      return new ReturnValueDescriptorImpl(this.getType(), this.asDescriptors(this.getDirectConstraints()), this.asContainerElementTypeDescriptors(this.getContainerElementsConstraints(), this.cascadingMetaData, defaultGroupSequenceRedefined, defaultGroupSequence), this.cascadingMetaData.isCascading(), defaultGroupSequenceRedefined, defaultGroupSequence, this.cascadingMetaData.getGroupConversionDescriptors());
   }

   public Object getValue(Object parent) {
      return parent;
   }

   public Type getCascadableType() {
      return this.getType();
   }

   public void appendTo(PathImpl path) {
      path.addReturnValueNode();
   }

   public CascadingMetaData getCascadingMetaData() {
      return this.cascadingMetaData;
   }

   public ElementKind getKind() {
      return ElementKind.RETURN_VALUE;
   }
}
