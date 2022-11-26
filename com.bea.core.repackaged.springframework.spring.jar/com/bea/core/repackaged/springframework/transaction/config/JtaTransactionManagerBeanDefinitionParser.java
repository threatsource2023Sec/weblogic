package com.bea.core.repackaged.springframework.transaction.config;

import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class JtaTransactionManagerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   protected String getBeanClassName(Element element) {
      return JtaTransactionManagerFactoryBean.resolveJtaTransactionManagerClassName();
   }

   protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) {
      return "transactionManager";
   }
}
