package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;

public final class ParserContext {
   private final XmlReaderContext readerContext;
   private final BeanDefinitionParserDelegate delegate;
   @Nullable
   private BeanDefinition containingBeanDefinition;
   private final Deque containingComponents = new ArrayDeque();

   public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate) {
      this.readerContext = readerContext;
      this.delegate = delegate;
   }

   public ParserContext(XmlReaderContext readerContext, BeanDefinitionParserDelegate delegate, @Nullable BeanDefinition containingBeanDefinition) {
      this.readerContext = readerContext;
      this.delegate = delegate;
      this.containingBeanDefinition = containingBeanDefinition;
   }

   public final XmlReaderContext getReaderContext() {
      return this.readerContext;
   }

   public final BeanDefinitionRegistry getRegistry() {
      return this.readerContext.getRegistry();
   }

   public final BeanDefinitionParserDelegate getDelegate() {
      return this.delegate;
   }

   @Nullable
   public final BeanDefinition getContainingBeanDefinition() {
      return this.containingBeanDefinition;
   }

   public final boolean isNested() {
      return this.containingBeanDefinition != null;
   }

   public boolean isDefaultLazyInit() {
      return "true".equals(this.delegate.getDefaults().getLazyInit());
   }

   @Nullable
   public Object extractSource(Object sourceCandidate) {
      return this.readerContext.extractSource(sourceCandidate);
   }

   @Nullable
   public CompositeComponentDefinition getContainingComponent() {
      return (CompositeComponentDefinition)this.containingComponents.peek();
   }

   public void pushContainingComponent(CompositeComponentDefinition containingComponent) {
      this.containingComponents.push(containingComponent);
   }

   public CompositeComponentDefinition popContainingComponent() {
      return (CompositeComponentDefinition)this.containingComponents.pop();
   }

   public void popAndRegisterContainingComponent() {
      this.registerComponent(this.popContainingComponent());
   }

   public void registerComponent(ComponentDefinition component) {
      CompositeComponentDefinition containingComponent = this.getContainingComponent();
      if (containingComponent != null) {
         containingComponent.addNestedComponent(component);
      } else {
         this.readerContext.fireComponentRegistered(component);
      }

   }

   public void registerBeanComponent(BeanComponentDefinition component) {
      BeanDefinitionReaderUtils.registerBeanDefinition(component, this.getRegistry());
      this.registerComponent(component);
   }
}
