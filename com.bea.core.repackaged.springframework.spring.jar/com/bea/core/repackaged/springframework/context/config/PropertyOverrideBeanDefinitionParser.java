package com.bea.core.repackaged.springframework.context.config;

import com.bea.core.repackaged.springframework.beans.factory.config.PropertyOverrideConfigurer;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

class PropertyOverrideBeanDefinitionParser extends AbstractPropertyLoadingBeanDefinitionParser {
   protected Class getBeanClass(Element element) {
      return PropertyOverrideConfigurer.class;
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      super.doParse(element, parserContext, builder);
      builder.addPropertyValue("ignoreInvalidKeys", Boolean.valueOf(element.getAttribute("ignore-unresolvable")));
   }
}
