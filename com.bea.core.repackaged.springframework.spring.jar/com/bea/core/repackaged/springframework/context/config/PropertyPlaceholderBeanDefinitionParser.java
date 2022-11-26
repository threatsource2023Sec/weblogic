package com.bea.core.repackaged.springframework.context.config;

import com.bea.core.repackaged.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

class PropertyPlaceholderBeanDefinitionParser extends AbstractPropertyLoadingBeanDefinitionParser {
   private static final String SYSTEM_PROPERTIES_MODE_ATTRIBUTE = "system-properties-mode";
   private static final String SYSTEM_PROPERTIES_MODE_DEFAULT = "ENVIRONMENT";

   protected Class getBeanClass(Element element) {
      return "ENVIRONMENT".equals(element.getAttribute("system-properties-mode")) ? PropertySourcesPlaceholderConfigurer.class : PropertyPlaceholderConfigurer.class;
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      super.doParse(element, parserContext, builder);
      builder.addPropertyValue("ignoreUnresolvablePlaceholders", Boolean.valueOf(element.getAttribute("ignore-unresolvable")));
      String systemPropertiesModeName = element.getAttribute("system-properties-mode");
      if (StringUtils.hasLength(systemPropertiesModeName) && !systemPropertiesModeName.equals("ENVIRONMENT")) {
         builder.addPropertyValue("systemPropertiesModeName", "SYSTEM_PROPERTIES_MODE_" + systemPropertiesModeName);
      }

      if (element.hasAttribute("value-separator")) {
         builder.addPropertyValue("valueSeparator", element.getAttribute("value-separator"));
      }

      if (element.hasAttribute("trim-values")) {
         builder.addPropertyValue("trimValues", element.getAttribute("trim-values"));
      }

      if (element.hasAttribute("null-value")) {
         builder.addPropertyValue("nullValue", element.getAttribute("null-value"));
      }

   }
}
