package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import java.util.Set;
import org.w3c.dom.Element;

public class AnnotationConfigBeanDefinitionParser implements BeanDefinitionParser {
   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      Object source = parserContext.extractSource(element);
      Set processorDefinitions = AnnotationConfigUtils.registerAnnotationConfigProcessors(parserContext.getRegistry(), source);
      CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
      parserContext.pushContainingComponent(compDefinition);
      Iterator var6 = processorDefinitions.iterator();

      while(var6.hasNext()) {
         BeanDefinitionHolder processorDefinition = (BeanDefinitionHolder)var6.next();
         parserContext.registerComponent(new BeanComponentDefinition(processorDefinition));
      }

      parserContext.popAndRegisterContainingComponent();
      return null;
   }
}
