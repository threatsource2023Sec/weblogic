package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public abstract class AbstractSimpleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      NamedNodeMap attributes = element.getAttributes();

      for(int x = 0; x < attributes.getLength(); ++x) {
         Attr attribute = (Attr)attributes.item(x);
         if (this.isEligibleAttribute(attribute, parserContext)) {
            String propertyName = this.extractPropertyName(attribute.getLocalName());
            Assert.state(StringUtils.hasText(propertyName), "Illegal property name returned from 'extractPropertyName(String)': cannot be null or empty.");
            builder.addPropertyValue(propertyName, attribute.getValue());
         }
      }

      this.postProcess(builder, element);
   }

   protected boolean isEligibleAttribute(Attr attribute, ParserContext parserContext) {
      String fullName = attribute.getName();
      return !fullName.equals("xmlns") && !fullName.startsWith("xmlns:") && this.isEligibleAttribute(parserContext.getDelegate().getLocalName(attribute));
   }

   protected boolean isEligibleAttribute(String attributeName) {
      return !"id".equals(attributeName);
   }

   protected String extractPropertyName(String attributeName) {
      return Conventions.attributeNameToPropertyName(attributeName);
   }

   protected void postProcess(BeanDefinitionBuilder beanDefinition, Element element) {
   }
}
