package com.bea.core.repackaged.springframework.ejb.config;

import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

abstract class AbstractJndiLocatingBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
   public static final String ENVIRONMENT = "environment";
   public static final String ENVIRONMENT_REF = "environment-ref";
   public static final String JNDI_ENVIRONMENT = "jndiEnvironment";

   protected boolean isEligibleAttribute(String attributeName) {
      return super.isEligibleAttribute(attributeName) && !"environment-ref".equals(attributeName) && !"lazy-init".equals(attributeName);
   }

   protected void postProcess(BeanDefinitionBuilder definitionBuilder, Element element) {
      Object envValue = DomUtils.getChildElementValueByTagName(element, "environment");
      String lazyInit;
      if (envValue != null) {
         definitionBuilder.addPropertyValue("jndiEnvironment", envValue);
      } else {
         lazyInit = element.getAttribute("environment-ref");
         if (StringUtils.hasLength(lazyInit)) {
            definitionBuilder.addPropertyValue("jndiEnvironment", new RuntimeBeanReference(lazyInit));
         }
      }

      lazyInit = element.getAttribute("lazy-init");
      if (StringUtils.hasText(lazyInit) && !"default".equals(lazyInit)) {
         definitionBuilder.setLazyInit("true".equals(lazyInit));
      }

   }
}
