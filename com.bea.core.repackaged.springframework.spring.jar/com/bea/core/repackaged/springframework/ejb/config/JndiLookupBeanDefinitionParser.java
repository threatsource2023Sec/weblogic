package com.bea.core.repackaged.springframework.ejb.config;

import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.jndi.JndiObjectFactoryBean;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

class JndiLookupBeanDefinitionParser extends AbstractJndiLocatingBeanDefinitionParser {
   public static final String DEFAULT_VALUE = "default-value";
   public static final String DEFAULT_REF = "default-ref";
   public static final String DEFAULT_OBJECT = "defaultObject";

   protected Class getBeanClass(Element element) {
      return JndiObjectFactoryBean.class;
   }

   protected boolean isEligibleAttribute(String attributeName) {
      return super.isEligibleAttribute(attributeName) && !"default-value".equals(attributeName) && !"default-ref".equals(attributeName);
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      super.doParse(element, parserContext, builder);
      String defaultValue = element.getAttribute("default-value");
      String defaultRef = element.getAttribute("default-ref");
      if (StringUtils.hasLength(defaultValue)) {
         if (StringUtils.hasLength(defaultRef)) {
            parserContext.getReaderContext().error("<jndi-lookup> element is only allowed to contain either 'default-value' attribute OR 'default-ref' attribute, not both", element);
         }

         builder.addPropertyValue("defaultObject", defaultValue);
      } else if (StringUtils.hasLength(defaultRef)) {
         builder.addPropertyValue("defaultObject", new RuntimeBeanReference(defaultRef));
      }

   }
}
