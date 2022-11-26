package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

class SpringConfiguredBeanDefinitionParser implements BeanDefinitionParser {
   public static final String BEAN_CONFIGURER_ASPECT_BEAN_NAME = "com.bea.core.repackaged.springframework.context.config.internalBeanConfigurerAspect";
   private static final String BEAN_CONFIGURER_ASPECT_CLASS_NAME = "com.bea.core.repackaged.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect";

   public BeanDefinition parse(Element element, ParserContext parserContext) {
      if (!parserContext.getRegistry().containsBeanDefinition("com.bea.core.repackaged.springframework.context.config.internalBeanConfigurerAspect")) {
         RootBeanDefinition def = new RootBeanDefinition();
         def.setBeanClassName("com.bea.core.repackaged.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect");
         def.setFactoryMethodName("aspectOf");
         def.setRole(2);
         def.setSource(parserContext.extractSource(element));
         parserContext.registerBeanComponent(new BeanComponentDefinition(def, "com.bea.core.repackaged.springframework.context.config.internalBeanConfigurerAspect"));
      }

      return null;
   }
}
