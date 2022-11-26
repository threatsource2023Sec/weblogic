package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.lang.Nullable;
import org.w3c.dom.Element;

public abstract class AbstractSingleBeanDefinitionParser extends AbstractBeanDefinitionParser {
   protected final AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
      String parentName = this.getParentName(element);
      if (parentName != null) {
         builder.getRawBeanDefinition().setParentName(parentName);
      }

      Class beanClass = this.getBeanClass(element);
      if (beanClass != null) {
         builder.getRawBeanDefinition().setBeanClass(beanClass);
      } else {
         String beanClassName = this.getBeanClassName(element);
         if (beanClassName != null) {
            builder.getRawBeanDefinition().setBeanClassName(beanClassName);
         }
      }

      builder.getRawBeanDefinition().setSource(parserContext.extractSource(element));
      BeanDefinition containingBd = parserContext.getContainingBeanDefinition();
      if (containingBd != null) {
         builder.setScope(containingBd.getScope());
      }

      if (parserContext.isDefaultLazyInit()) {
         builder.setLazyInit(true);
      }

      this.doParse(element, parserContext, builder);
      return builder.getBeanDefinition();
   }

   @Nullable
   protected String getParentName(Element element) {
      return null;
   }

   @Nullable
   protected Class getBeanClass(Element element) {
      return null;
   }

   @Nullable
   protected String getBeanClassName(Element element) {
      return null;
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      this.doParse(element, builder);
   }

   protected void doParse(Element element, BeanDefinitionBuilder builder) {
   }
}
