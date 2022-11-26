package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedList;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class AspectJAutoProxyBeanDefinitionParser implements BeanDefinitionParser {
   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      AopNamespaceUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext, element);
      this.extendBeanDefinition(element, parserContext);
      return null;
   }

   private void extendBeanDefinition(Element element, ParserContext parserContext) {
      BeanDefinition beanDef = parserContext.getRegistry().getBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator");
      if (element.hasChildNodes()) {
         this.addIncludePatterns(element, parserContext, beanDef);
      }

   }

   private void addIncludePatterns(Element element, ParserContext parserContext, BeanDefinition beanDef) {
      ManagedList includePatterns = new ManagedList();
      NodeList childNodes = element.getChildNodes();

      for(int i = 0; i < childNodes.getLength(); ++i) {
         Node node = childNodes.item(i);
         if (node instanceof Element) {
            Element includeElement = (Element)node;
            TypedStringValue valueHolder = new TypedStringValue(includeElement.getAttribute("name"));
            valueHolder.setSource(parserContext.extractSource(includeElement));
            includePatterns.add(valueHolder);
         }
      }

      if (!includePatterns.isEmpty()) {
         includePatterns.setSource(parserContext.extractSource(element));
         beanDef.getPropertyValues().add("includePatterns", includePatterns);
      }

   }
}
