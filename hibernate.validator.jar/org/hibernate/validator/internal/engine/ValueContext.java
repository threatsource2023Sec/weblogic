package org.hibernate.validator.internal.engine;

import java.lang.annotation.ElementType;
import javax.validation.groups.Default;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public class ValueContext {
   private final ExecutableParameterNameProvider parameterNameProvider;
   private final Object currentBean;
   private final Class currentBeanType;
   private final BeanMetaData currentBeanMetaData;
   private PathImpl propertyPath;
   private Class currentGroup;
   private Object currentValue;
   private final Validatable currentValidatable;
   private ElementType elementType;

   public static ValueContext getLocalExecutionContext(BeanMetaDataManager beanMetaDataManager, ExecutableParameterNameProvider parameterNameProvider, Object value, Validatable validatable, PathImpl propertyPath) {
      Class rootBeanType = value.getClass();
      return new ValueContext(parameterNameProvider, value, rootBeanType, beanMetaDataManager.getBeanMetaData(rootBeanType), validatable, propertyPath);
   }

   public static ValueContext getLocalExecutionContext(ExecutableParameterNameProvider parameterNameProvider, Object value, BeanMetaData currentBeanMetaData, PathImpl propertyPath) {
      Class rootBeanType = value.getClass();
      return new ValueContext(parameterNameProvider, value, rootBeanType, currentBeanMetaData, currentBeanMetaData, propertyPath);
   }

   public static ValueContext getLocalExecutionContext(BeanMetaDataManager beanMetaDataManager, ExecutableParameterNameProvider parameterNameProvider, Class rootBeanType, Validatable validatable, PathImpl propertyPath) {
      BeanMetaData rootBeanMetaData = rootBeanType != null ? beanMetaDataManager.getBeanMetaData(rootBeanType) : null;
      return new ValueContext(parameterNameProvider, (Object)null, rootBeanType, rootBeanMetaData, validatable, propertyPath);
   }

   public static ValueContext getLocalExecutionContext(ExecutableParameterNameProvider parameterNameProvider, Class currentBeanType, BeanMetaData currentBeanMetaData, PathImpl propertyPath) {
      return new ValueContext(parameterNameProvider, (Object)null, currentBeanType, currentBeanMetaData, currentBeanMetaData, propertyPath);
   }

   private ValueContext(ExecutableParameterNameProvider parameterNameProvider, Object currentBean, Class currentBeanType, BeanMetaData currentBeanMetaData, Validatable validatable, PathImpl propertyPath) {
      this.parameterNameProvider = parameterNameProvider;
      this.currentBean = currentBean;
      this.currentBeanType = currentBeanType;
      this.currentBeanMetaData = currentBeanMetaData;
      this.currentValidatable = validatable;
      this.propertyPath = propertyPath;
   }

   public final PathImpl getPropertyPath() {
      return this.propertyPath;
   }

   public final Class getCurrentGroup() {
      return this.currentGroup;
   }

   public final Object getCurrentBean() {
      return this.currentBean;
   }

   public final Class getCurrentBeanType() {
      return this.currentBeanType;
   }

   public final BeanMetaData getCurrentBeanMetaData() {
      return this.currentBeanMetaData;
   }

   public Validatable getCurrentValidatable() {
      return this.currentValidatable;
   }

   public final Object getCurrentValidatedValue() {
      return this.currentValue;
   }

   public final void appendNode(Cascadable node) {
      PathImpl newPath = PathImpl.createCopy(this.propertyPath);
      node.appendTo(newPath);
      this.propertyPath = newPath;
   }

   public final void appendNode(ConstraintLocation location) {
      PathImpl newPath = PathImpl.createCopy(this.propertyPath);
      location.appendTo(this.parameterNameProvider, newPath);
      this.propertyPath = newPath;
   }

   public final void appendTypeParameterNode(String nodeName) {
      PathImpl newPath = PathImpl.createCopy(this.propertyPath);
      newPath.addContainerElementNode(nodeName);
      this.propertyPath = newPath;
   }

   public final void markCurrentPropertyAsIterable() {
      this.propertyPath.makeLeafNodeIterable();
   }

   public final void markCurrentPropertyAsIterableAndSetKey(Object key) {
      this.propertyPath.makeLeafNodeIterableAndSetMapKey(key);
   }

   public final void markCurrentPropertyAsIterableAndSetIndex(Integer index) {
      this.propertyPath.makeLeafNodeIterableAndSetIndex(index);
   }

   public final void setTypeParameter(Class containerClass, Integer typeParameterIndex) {
      if (containerClass != null) {
         this.propertyPath.setLeafNodeTypeParameter(containerClass, typeParameterIndex);
      }
   }

   public final void setCurrentGroup(Class currentGroup) {
      this.currentGroup = currentGroup;
   }

   public final void setCurrentValidatedValue(Object currentValue) {
      this.propertyPath.setLeafNodeValueIfRequired(currentValue);
      this.currentValue = currentValue;
   }

   public final boolean validatingDefault() {
      return this.getCurrentGroup() != null && this.getCurrentGroup().getName().equals(Default.class.getName());
   }

   public final ElementType getElementType() {
      return this.elementType;
   }

   public final void setElementType(ElementType elementType) {
      this.elementType = elementType;
   }

   public final ValueState getCurrentValueState() {
      return new ValueState(this.propertyPath, this.currentValue);
   }

   public final void resetValueState(ValueState valueState) {
      this.propertyPath = valueState.propertyPath;
      this.currentValue = valueState.currentValue;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ValueContext");
      sb.append("{currentBean=").append(this.currentBean);
      sb.append(", currentBeanType=").append(this.currentBeanType);
      sb.append(", propertyPath=").append(this.propertyPath);
      sb.append(", currentGroup=").append(this.currentGroup);
      sb.append(", currentValue=").append(this.currentValue);
      sb.append(", elementType=").append(this.elementType);
      sb.append('}');
      return sb.toString();
   }

   public Object getValue(Object parent, ConstraintLocation location) {
      return location.getValue(parent);
   }

   public static class ValueState {
      private final PathImpl propertyPath;
      private final Object currentValue;

      private ValueState(PathImpl propertyPath, Object currentValue) {
         this.propertyPath = propertyPath;
         this.currentValue = currentValue;
      }

      // $FF: synthetic method
      ValueState(PathImpl x0, Object x1, Object x2) {
         this(x0, x1);
      }
   }
}
