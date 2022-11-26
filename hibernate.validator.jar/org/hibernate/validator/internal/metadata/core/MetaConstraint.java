package org.hibernate.validator.internal.metadata.core;

import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.ValidationContext;
import org.hibernate.validator.internal.engine.ValueContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.StringHelper;

public class MetaConstraint {
   private final ConstraintTree constraintTree;
   private final ConstraintLocation location;
   private final ValueExtractionPathNode valueExtractionPath;
   private final int hashCode;
   private final boolean isDefinedForOneGroupOnly;

   MetaConstraint(ConstraintDescriptorImpl constraintDescriptor, ConstraintLocation location, List valueExtractionPath, Type validatedValueType) {
      this.constraintTree = ConstraintTree.of(constraintDescriptor, validatedValueType);
      this.location = location;
      this.valueExtractionPath = getValueExtractionPath(valueExtractionPath);
      this.hashCode = buildHashCode(constraintDescriptor, location);
      this.isDefinedForOneGroupOnly = constraintDescriptor.getGroups().size() <= 1;
   }

   private static ValueExtractionPathNode getValueExtractionPath(List valueExtractionPath) {
      switch (valueExtractionPath.size()) {
         case 0:
            return null;
         case 1:
            return new SingleValueExtractionPathNode((ContainerClassTypeParameterAndExtractor)valueExtractionPath.iterator().next());
         default:
            return new LinkedValueExtractionPathNode((ValueExtractionPathNode)null, valueExtractionPath);
      }
   }

   public final Set getGroupList() {
      return this.constraintTree.getDescriptor().getGroups();
   }

   public final boolean isDefinedForOneGroupOnly() {
      return this.isDefinedForOneGroupOnly;
   }

   public final ConstraintDescriptorImpl getDescriptor() {
      return this.constraintTree.getDescriptor();
   }

   public final ElementType getElementType() {
      return this.constraintTree.getDescriptor().getElementType();
   }

   public boolean validateConstraint(ValidationContext validationContext, ValueContext valueContext) {
      boolean success = true;
      if (this.valueExtractionPath != null) {
         Object valueToValidate = valueContext.getCurrentValidatedValue();
         if (valueToValidate != null) {
            TypeParameterValueReceiver receiver = new TypeParameterValueReceiver(validationContext, valueContext, this.valueExtractionPath);
            ValueExtractorHelper.extractValues(this.valueExtractionPath.getValueExtractorDescriptor(), valueToValidate, receiver);
            success = receiver.isSuccess();
         }
      } else {
         success = this.doValidateConstraint(validationContext, valueContext);
      }

      return success;
   }

   private boolean doValidateConstraint(ValidationContext executionContext, ValueContext valueContext) {
      valueContext.setElementType(this.getElementType());
      boolean validationResult = this.constraintTree.validateConstraints(executionContext, valueContext);
      return validationResult;
   }

   public ConstraintLocation getLocation() {
      return this.location;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MetaConstraint that = (MetaConstraint)o;
         if (!this.constraintTree.getDescriptor().equals(that.constraintTree.getDescriptor())) {
            return false;
         } else {
            return this.location.equals(that.location);
         }
      } else {
         return false;
      }
   }

   private static int buildHashCode(ConstraintDescriptorImpl constraintDescriptor, ConstraintLocation location) {
      int prime = true;
      int result = 1;
      result = 31 * result + constraintDescriptor.hashCode();
      result = 31 * result + location.hashCode();
      return result;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("MetaConstraint");
      sb.append("{constraintType=").append(StringHelper.toShortString((Type)this.constraintTree.getDescriptor().getAnnotation().annotationType()));
      sb.append(", location=").append(this.location);
      sb.append(", valueExtractionPath=").append(this.valueExtractionPath);
      sb.append("}");
      return sb.toString();
   }

   private static final class LinkedValueExtractionPathNode implements ValueExtractionPathNode {
      private final ValueExtractionPathNode previous;
      private final ValueExtractionPathNode next;
      private final Class containerClass;
      private final TypeVariable typeParameter;
      private final Integer typeParameterIndex;
      private final ValueExtractorDescriptor valueExtractorDescriptor;

      private LinkedValueExtractionPathNode(ValueExtractionPathNode previous, List elements) {
         ContainerClassTypeParameterAndExtractor first = (ContainerClassTypeParameterAndExtractor)elements.get(0);
         this.containerClass = first.containerClass;
         this.typeParameter = first.typeParameter;
         this.typeParameterIndex = first.typeParameterIndex;
         this.valueExtractorDescriptor = first.valueExtractorDescriptor;
         this.previous = previous;
         if (elements.size() == 1) {
            this.next = null;
         } else {
            this.next = new LinkedValueExtractionPathNode(this, elements.subList(1, elements.size()));
         }

      }

      public boolean hasNext() {
         return this.next != null;
      }

      public ValueExtractionPathNode getPrevious() {
         return this.previous;
      }

      public ValueExtractionPathNode getNext() {
         return this.next;
      }

      public Class getContainerClass() {
         return this.containerClass;
      }

      public TypeVariable getTypeParameter() {
         return this.typeParameter;
      }

      public Integer getTypeParameterIndex() {
         return this.typeParameterIndex;
      }

      public ValueExtractorDescriptor getValueExtractorDescriptor() {
         return this.valueExtractorDescriptor;
      }

      public String toString() {
         return "LinkedValueExtractionPathNode [containerClass=" + this.containerClass + ", typeParameter=" + this.typeParameter + ", valueExtractorDescriptor=" + this.valueExtractorDescriptor + "]";
      }

      // $FF: synthetic method
      LinkedValueExtractionPathNode(ValueExtractionPathNode x0, List x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class SingleValueExtractionPathNode implements ValueExtractionPathNode {
      private final Class containerClass;
      private final TypeVariable typeParameter;
      private final Integer typeParameterIndex;
      private final ValueExtractorDescriptor valueExtractorDescriptor;

      public SingleValueExtractionPathNode(ContainerClassTypeParameterAndExtractor typeParameterAndExtractor) {
         this.containerClass = typeParameterAndExtractor.containerClass;
         this.typeParameter = typeParameterAndExtractor.typeParameter;
         this.typeParameterIndex = typeParameterAndExtractor.typeParameterIndex;
         this.valueExtractorDescriptor = typeParameterAndExtractor.valueExtractorDescriptor;
      }

      public boolean hasNext() {
         return false;
      }

      public ValueExtractionPathNode getPrevious() {
         throw new NoSuchElementException();
      }

      public ValueExtractionPathNode getNext() {
         throw new NoSuchElementException();
      }

      public Class getContainerClass() {
         return this.containerClass;
      }

      public TypeVariable getTypeParameter() {
         return this.typeParameter;
      }

      public Integer getTypeParameterIndex() {
         return this.typeParameterIndex;
      }

      public ValueExtractorDescriptor getValueExtractorDescriptor() {
         return this.valueExtractorDescriptor;
      }

      public String toString() {
         return "SingleValueExtractionPathNode [containerClass=" + this.containerClass + ", typeParameter=" + this.typeParameter + ", valueExtractorDescriptor=" + this.valueExtractorDescriptor + "]";
      }
   }

   private interface ValueExtractionPathNode {
      boolean hasNext();

      ValueExtractionPathNode getPrevious();

      ValueExtractionPathNode getNext();

      Class getContainerClass();

      TypeVariable getTypeParameter();

      Integer getTypeParameterIndex();

      ValueExtractorDescriptor getValueExtractorDescriptor();
   }

   static final class ContainerClassTypeParameterAndExtractor {
      private final Class containerClass;
      private final TypeVariable typeParameter;
      private final Integer typeParameterIndex;
      private final ValueExtractorDescriptor valueExtractorDescriptor;

      ContainerClassTypeParameterAndExtractor(Class containerClass, TypeVariable typeParameter, Integer typeParameterIndex, ValueExtractorDescriptor valueExtractorDescriptor) {
         this.containerClass = containerClass;
         this.typeParameter = typeParameter;
         this.typeParameterIndex = typeParameterIndex;
         this.valueExtractorDescriptor = valueExtractorDescriptor;
      }

      public String toString() {
         return "ContainerClassTypeParameterAndExtractor [containerClass=" + this.containerClass + ", typeParameter=" + this.typeParameter + ", typeParameterIndex=" + this.typeParameterIndex + ", valueExtractorDescriptor=" + this.valueExtractorDescriptor + "]";
      }
   }

   private final class TypeParameterValueReceiver implements ValueExtractor.ValueReceiver {
      private final ValidationContext validationContext;
      private final ValueContext valueContext;
      private boolean success = true;
      private ValueExtractionPathNode currentValueExtractionPathNode;

      public TypeParameterValueReceiver(ValidationContext validationContext, ValueContext valueContext, ValueExtractionPathNode currentValueExtractionPathNode) {
         this.validationContext = validationContext;
         this.valueContext = valueContext;
         this.currentValueExtractionPathNode = currentValueExtractionPathNode;
      }

      public void value(String nodeName, Object object) {
         this.doValidate(object, nodeName);
      }

      public void iterableValue(String nodeName, Object value) {
         this.valueContext.markCurrentPropertyAsIterable();
         this.doValidate(value, nodeName);
      }

      public void indexedValue(String nodeName, int index, Object value) {
         this.valueContext.markCurrentPropertyAsIterableAndSetIndex(index);
         this.doValidate(value, nodeName);
      }

      public void keyedValue(String nodeName, Object key, Object value) {
         this.valueContext.markCurrentPropertyAsIterableAndSetKey(key);
         this.doValidate(value, nodeName);
      }

      private void doValidate(Object value, String nodeName) {
         ValueContext.ValueState originalValueState = this.valueContext.getCurrentValueState();
         Class containerClass = this.currentValueExtractionPathNode.getContainerClass();
         if (containerClass != null) {
            this.valueContext.setTypeParameter(containerClass, this.currentValueExtractionPathNode.getTypeParameterIndex());
         }

         if (nodeName != null) {
            this.valueContext.appendTypeParameterNode(nodeName);
         }

         this.valueContext.setCurrentValidatedValue(value);
         if (this.currentValueExtractionPathNode.hasNext()) {
            if (value != null) {
               this.currentValueExtractionPathNode = this.currentValueExtractionPathNode.getNext();
               ValueExtractorDescriptor valueExtractorDescriptor = this.currentValueExtractionPathNode.getValueExtractorDescriptor();
               ValueExtractorHelper.extractValues(valueExtractorDescriptor, value, this);
               this.currentValueExtractionPathNode = this.currentValueExtractionPathNode.getPrevious();
            }
         } else {
            this.success &= MetaConstraint.this.doValidateConstraint(this.validationContext, this.valueContext);
         }

         this.valueContext.resetValueState(originalValueState);
      }

      public boolean isSuccess() {
         return this.success;
      }
   }
}
