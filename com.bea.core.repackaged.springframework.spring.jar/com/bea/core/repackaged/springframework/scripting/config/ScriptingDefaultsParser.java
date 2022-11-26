package com.bea.core.repackaged.springframework.scripting.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

class ScriptingDefaultsParser implements BeanDefinitionParser {
   private static final String REFRESH_CHECK_DELAY_ATTRIBUTE = "refresh-check-delay";
   private static final String PROXY_TARGET_CLASS_ATTRIBUTE = "proxy-target-class";

   public BeanDefinition parse(Element element, ParserContext parserContext) {
      BeanDefinition bd = LangNamespaceUtils.registerScriptFactoryPostProcessorIfNecessary(parserContext.getRegistry());
      String refreshCheckDelay = element.getAttribute("refresh-check-delay");
      if (StringUtils.hasText(refreshCheckDelay)) {
         bd.getPropertyValues().add("defaultRefreshCheckDelay", Long.valueOf(refreshCheckDelay));
      }

      String proxyTargetClass = element.getAttribute("proxy-target-class");
      if (StringUtils.hasText(proxyTargetClass)) {
         bd.getPropertyValues().add("defaultProxyTargetClass", new TypedStringValue(proxyTargetClass, Boolean.class));
      }

      return null;
   }
}
