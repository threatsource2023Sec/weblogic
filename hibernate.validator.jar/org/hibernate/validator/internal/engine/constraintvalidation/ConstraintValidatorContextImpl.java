package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ElementKind;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ConstraintValidatorContextImpl implements HibernateConstraintValidatorContext {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private Map messageParameters;
   private Map expressionVariables;
   private final List methodParameterNames;
   private final ClockProvider clockProvider;
   private final PathImpl basePath;
   private final ConstraintDescriptor constraintDescriptor;
   private List constraintViolationCreationContexts;
   private boolean defaultDisabled;
   private Object dynamicPayload;
   private final Object constraintValidatorPayload;

   public ConstraintValidatorContextImpl(List methodParameterNames, ClockProvider clockProvider, PathImpl propertyPath, ConstraintDescriptor constraintDescriptor, Object constraintValidatorPayload) {
      this.methodParameterNames = methodParameterNames;
      this.clockProvider = clockProvider;
      this.basePath = propertyPath;
      this.constraintDescriptor = constraintDescriptor;
      this.constraintValidatorPayload = constraintValidatorPayload;
   }

   public final void disableDefaultConstraintViolation() {
      this.defaultDisabled = true;
   }

   public final String getDefaultConstraintMessageTemplate() {
      return this.constraintDescriptor.getMessageTemplate();
   }

   public final ConstraintValidatorContext.ConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate) {
      return new ConstraintViolationBuilderImpl(this.methodParameterNames, messageTemplate, PathImpl.createCopy(this.basePath));
   }

   public Object unwrap(Class type) {
      if (type.isAssignableFrom(HibernateConstraintValidatorContext.class)) {
         return type.cast(this);
      } else {
         throw LOG.getTypeNotSupportedForUnwrappingException(type);
      }
   }

   public HibernateConstraintValidatorContext addExpressionVariable(String name, Object value) {
      Contracts.assertNotNull(name, "null is not a valid value for an expression variable name");
      if (this.expressionVariables == null) {
         this.expressionVariables = new HashMap();
      }

      this.expressionVariables.put(name, value);
      return this;
   }

   public HibernateConstraintValidatorContext addMessageParameter(String name, Object value) {
      Contracts.assertNotNull(name, "null is not a valid value for a parameter name");
      if (this.messageParameters == null) {
         this.messageParameters = new HashMap();
      }

      this.messageParameters.put(name, value);
      return this;
   }

   public ClockProvider getClockProvider() {
      return this.clockProvider;
   }

   public HibernateConstraintValidatorContext withDynamicPayload(Object violationContext) {
      this.dynamicPayload = violationContext;
      return this;
   }

   public Object getConstraintValidatorPayload(Class type) {
      return this.constraintValidatorPayload != null && type.isAssignableFrom(this.constraintValidatorPayload.getClass()) ? type.cast(this.constraintValidatorPayload) : null;
   }

   public final ConstraintDescriptor getConstraintDescriptor() {
      return this.constraintDescriptor;
   }

   public final List getConstraintViolationCreationContexts() {
      if (this.defaultDisabled) {
         if (this.constraintViolationCreationContexts != null && this.constraintViolationCreationContexts.size() != 0) {
            return CollectionHelper.toImmutableList(this.constraintViolationCreationContexts);
         } else {
            throw LOG.getAtLeastOneCustomMessageMustBeCreatedException();
         }
      } else if (this.constraintViolationCreationContexts != null && this.constraintViolationCreationContexts.size() != 0) {
         List returnedConstraintViolationCreationContexts = new ArrayList(this.constraintViolationCreationContexts.size() + 1);
         returnedConstraintViolationCreationContexts.addAll(this.constraintViolationCreationContexts);
         returnedConstraintViolationCreationContexts.add(this.getDefaultConstraintViolationCreationContext());
         return CollectionHelper.toImmutableList(returnedConstraintViolationCreationContexts);
      } else {
         return Collections.singletonList(this.getDefaultConstraintViolationCreationContext());
      }
   }

   private ConstraintViolationCreationContext getDefaultConstraintViolationCreationContext() {
      return new ConstraintViolationCreationContext(this.getDefaultConstraintMessageTemplate(), this.basePath, (Map)(this.messageParameters != null ? new HashMap(this.messageParameters) : Collections.emptyMap()), (Map)(this.expressionVariables != null ? new HashMap(this.expressionVariables) : Collections.emptyMap()), this.dynamicPayload);
   }

   public List getMethodParameterNames() {
      return this.methodParameterNames;
   }

   private class DeferredNodeBuilder extends NodeBuilderBase implements ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext, ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext, ConstraintValidatorContext.ConstraintViolationBuilder.NodeContextBuilder, ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeContextBuilder, ConstraintValidatorContext.ConstraintViolationBuilder.ContainerElementNodeBuilderCustomizableContext, ConstraintValidatorContext.ConstraintViolationBuilder.ContainerElementNodeContextBuilder {
      private final String leafNodeName;
      private final ElementKind leafNodeKind;
      private final Class leafNodeContainerType;
      private final Integer leafNodeTypeArgumentIndex;

      private DeferredNodeBuilder(String template, PathImpl path, String nodeName, ElementKind leafNodeKind) {
         super(template, path);
         this.leafNodeName = nodeName;
         this.leafNodeKind = leafNodeKind;
         this.leafNodeContainerType = null;
         this.leafNodeTypeArgumentIndex = null;
      }

      private DeferredNodeBuilder(String template, PathImpl path, String nodeName, Class leafNodeContainerType, Integer leafNodeTypeArgumentIndex) {
         super(template, path);
         this.leafNodeName = nodeName;
         this.leafNodeKind = ElementKind.CONTAINER_ELEMENT;
         this.leafNodeContainerType = leafNodeContainerType;
         this.leafNodeTypeArgumentIndex = leafNodeTypeArgumentIndex;
      }

      public DeferredNodeBuilder inIterable() {
         this.propertyPath.makeLeafNodeIterable();
         return this;
      }

      public DeferredNodeBuilder inContainer(Class containerClass, Integer typeArgumentIndex) {
         this.propertyPath.setLeafNodeTypeParameter(containerClass, typeArgumentIndex);
         return this;
      }

      public NodeBuilder atKey(Object key) {
         this.propertyPath.makeLeafNodeIterableAndSetMapKey(key);
         this.addLeafNode();
         return ConstraintValidatorContextImpl.this.new NodeBuilder(this.messageTemplate, this.propertyPath);
      }

      public NodeBuilder atIndex(Integer index) {
         this.propertyPath.makeLeafNodeIterableAndSetIndex(index);
         this.addLeafNode();
         return ConstraintValidatorContextImpl.this.new NodeBuilder(this.messageTemplate, this.propertyPath);
      }

      /** @deprecated */
      @Deprecated
      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext addNode(String name) {
         return this.addPropertyNode(name);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext addPropertyNode(String name) {
         this.addLeafNode();
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, name, ElementKind.PROPERTY);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class containerType, Integer typeArgumentIndex) {
         this.addLeafNode();
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, name, containerType, typeArgumentIndex);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext addBeanNode() {
         this.addLeafNode();
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, (String)null, ElementKind.BEAN);
      }

      public ConstraintValidatorContext addConstraintViolation() {
         this.addLeafNode();
         return super.addConstraintViolation();
      }

      private void addLeafNode() {
         switch (this.leafNodeKind) {
            case BEAN:
               this.propertyPath.addBeanNode();
               break;
            case PROPERTY:
               this.propertyPath.addPropertyNode(this.leafNodeName);
               break;
            case CONTAINER_ELEMENT:
               this.propertyPath.setLeafNodeTypeParameter(this.leafNodeContainerType, this.leafNodeTypeArgumentIndex);
               this.propertyPath.addContainerElementNode(this.leafNodeName);
               break;
            default:
               throw new IllegalStateException("Unsupported node kind: " + this.leafNodeKind);
         }

      }

      // $FF: synthetic method
      DeferredNodeBuilder(String x1, PathImpl x2, String x3, ElementKind x4, Object x5) {
         this(x1, x2, x3, x4);
      }

      // $FF: synthetic method
      DeferredNodeBuilder(String x1, PathImpl x2, String x3, Class x4, Integer x5, Object x6) {
         this(x1, x2, x3, (Class)x4, (Integer)x5);
      }
   }

   private class NodeBuilder extends NodeBuilderBase implements ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext, ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderDefinedContext, ConstraintValidatorContext.ConstraintViolationBuilder.ContainerElementNodeBuilderDefinedContext {
      private NodeBuilder(String template, PathImpl path) {
         super(template, path);
      }

      /** @deprecated */
      @Deprecated
      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext addNode(String name) {
         return this.addPropertyNode(name);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext addPropertyNode(String name) {
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, name, ElementKind.PROPERTY);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext addBeanNode() {
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, (String)null, ElementKind.BEAN);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class containerType, Integer typeArgumentIndex) {
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, name, containerType, typeArgumentIndex);
      }

      // $FF: synthetic method
      NodeBuilder(String x1, PathImpl x2, Object x3) {
         this(x1, x2);
      }
   }

   private class ConstraintViolationBuilderImpl extends NodeBuilderBase implements ConstraintValidatorContext.ConstraintViolationBuilder {
      private final List methodParameterNames;

      private ConstraintViolationBuilderImpl(List methodParameterNames, String template, PathImpl path) {
         super(template, path);
         this.methodParameterNames = methodParameterNames;
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext addNode(String name) {
         this.dropLeafNodeIfRequired();
         this.propertyPath.addPropertyNode(name);
         return ConstraintValidatorContextImpl.this.new NodeBuilder(this.messageTemplate, this.propertyPath);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext addPropertyNode(String name) {
         this.dropLeafNodeIfRequired();
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, name, ElementKind.PROPERTY);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext addBeanNode() {
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, (String)null, ElementKind.BEAN);
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext addParameterNode(int index) {
         if (this.propertyPath.getLeafNode().getKind() != ElementKind.CROSS_PARAMETER) {
            throw ConstraintValidatorContextImpl.LOG.getParameterNodeAddedForNonCrossParameterConstraintException(this.propertyPath);
         } else {
            this.dropLeafNodeIfRequired();
            this.propertyPath.addParameterNode((String)this.methodParameterNames.get(index), index);
            return ConstraintValidatorContextImpl.this.new NodeBuilder(this.messageTemplate, this.propertyPath);
         }
      }

      public ConstraintValidatorContext.ConstraintViolationBuilder.ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class containerType, Integer typeArgumentIndex) {
         this.dropLeafNodeIfRequired();
         return ConstraintValidatorContextImpl.this.new DeferredNodeBuilder(this.messageTemplate, this.propertyPath, name, containerType, typeArgumentIndex);
      }

      private void dropLeafNodeIfRequired() {
         if (this.propertyPath.getLeafNode().getKind() == ElementKind.BEAN || this.propertyPath.getLeafNode().getKind() == ElementKind.CROSS_PARAMETER) {
            this.propertyPath = this.propertyPath.getPathWithoutLeafNode();
         }

      }

      // $FF: synthetic method
      ConstraintViolationBuilderImpl(List x1, String x2, PathImpl x3, Object x4) {
         this(x1, x2, x3);
      }
   }

   private abstract class NodeBuilderBase {
      protected final String messageTemplate;
      protected PathImpl propertyPath;

      protected NodeBuilderBase(String template, PathImpl path) {
         this.messageTemplate = template;
         this.propertyPath = path;
      }

      public ConstraintValidatorContext addConstraintViolation() {
         if (ConstraintValidatorContextImpl.this.constraintViolationCreationContexts == null) {
            ConstraintValidatorContextImpl.this.constraintViolationCreationContexts = CollectionHelper.newArrayList(3);
         }

         ConstraintValidatorContextImpl.this.constraintViolationCreationContexts.add(new ConstraintViolationCreationContext(this.messageTemplate, this.propertyPath, (Map)(ConstraintValidatorContextImpl.this.messageParameters != null ? new HashMap(ConstraintValidatorContextImpl.this.messageParameters) : Collections.emptyMap()), (Map)(ConstraintValidatorContextImpl.this.expressionVariables != null ? new HashMap(ConstraintValidatorContextImpl.this.expressionVariables) : Collections.emptyMap()), ConstraintValidatorContextImpl.this.dynamicPayload));
         return ConstraintValidatorContextImpl.this;
      }
   }
}
