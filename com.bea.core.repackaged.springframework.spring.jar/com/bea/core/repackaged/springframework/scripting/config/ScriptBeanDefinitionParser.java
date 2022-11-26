package com.bea.core.repackaged.springframework.scripting.config;

import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionDefaults;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlReaderContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.support.ScriptFactoryPostProcessor;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.xml.DomUtils;
import java.util.List;
import org.w3c.dom.Element;

class ScriptBeanDefinitionParser extends AbstractBeanDefinitionParser {
   private static final String ENGINE_ATTRIBUTE = "engine";
   private static final String SCRIPT_SOURCE_ATTRIBUTE = "script-source";
   private static final String INLINE_SCRIPT_ELEMENT = "inline-script";
   private static final String SCOPE_ATTRIBUTE = "scope";
   private static final String AUTOWIRE_ATTRIBUTE = "autowire";
   private static final String DEPENDS_ON_ATTRIBUTE = "depends-on";
   private static final String INIT_METHOD_ATTRIBUTE = "init-method";
   private static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
   private static final String SCRIPT_INTERFACES_ATTRIBUTE = "script-interfaces";
   private static final String REFRESH_CHECK_DELAY_ATTRIBUTE = "refresh-check-delay";
   private static final String PROXY_TARGET_CLASS_ATTRIBUTE = "proxy-target-class";
   private static final String CUSTOMIZER_REF_ATTRIBUTE = "customizer-ref";
   private final String scriptFactoryClassName;

   public ScriptBeanDefinitionParser(String scriptFactoryClassName) {
      this.scriptFactoryClassName = scriptFactoryClassName;
   }

   @Nullable
   protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
      String engine = element.getAttribute("engine");
      String value = this.resolveScriptSource(element, parserContext.getReaderContext());
      if (value == null) {
         return null;
      } else {
         LangNamespaceUtils.registerScriptFactoryPostProcessorIfNecessary(parserContext.getRegistry());
         GenericBeanDefinition bd = new GenericBeanDefinition();
         bd.setBeanClassName(this.scriptFactoryClassName);
         bd.setSource(parserContext.extractSource(element));
         bd.setAttribute(ScriptFactoryPostProcessor.LANGUAGE_ATTRIBUTE, element.getLocalName());
         String scope = element.getAttribute("scope");
         if (StringUtils.hasLength(scope)) {
            bd.setScope(scope);
         }

         String autowire = element.getAttribute("autowire");
         int autowireMode = parserContext.getDelegate().getAutowireMode(autowire);
         if (autowireMode == 4) {
            autowireMode = 2;
         } else if (autowireMode == 3) {
            autowireMode = 0;
         }

         bd.setAutowireMode(autowireMode);
         String dependsOn = element.getAttribute("depends-on");
         if (StringUtils.hasLength(dependsOn)) {
            bd.setDependsOn(StringUtils.tokenizeToStringArray(dependsOn, ",; "));
         }

         BeanDefinitionDefaults beanDefinitionDefaults = parserContext.getDelegate().getBeanDefinitionDefaults();
         String initMethod = element.getAttribute("init-method");
         if (StringUtils.hasLength(initMethod)) {
            bd.setInitMethodName(initMethod);
         } else if (beanDefinitionDefaults.getInitMethodName() != null) {
            bd.setInitMethodName(beanDefinitionDefaults.getInitMethodName());
         }

         String refreshCheckDelay;
         if (element.hasAttribute("destroy-method")) {
            refreshCheckDelay = element.getAttribute("destroy-method");
            bd.setDestroyMethodName(refreshCheckDelay);
         } else if (beanDefinitionDefaults.getDestroyMethodName() != null) {
            bd.setDestroyMethodName(beanDefinitionDefaults.getDestroyMethodName());
         }

         refreshCheckDelay = element.getAttribute("refresh-check-delay");
         if (StringUtils.hasText(refreshCheckDelay)) {
            bd.setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE, Long.valueOf(refreshCheckDelay));
         }

         String proxyTargetClass = element.getAttribute("proxy-target-class");
         if (StringUtils.hasText(proxyTargetClass)) {
            bd.setAttribute(ScriptFactoryPostProcessor.PROXY_TARGET_CLASS_ATTRIBUTE, Boolean.valueOf(proxyTargetClass));
         }

         ConstructorArgumentValues cav = bd.getConstructorArgumentValues();
         int constructorArgNum = 0;
         if (StringUtils.hasLength(engine)) {
            cav.addIndexedArgumentValue(constructorArgNum++, (Object)engine);
         }

         cav.addIndexedArgumentValue(constructorArgNum++, (Object)value);
         if (element.hasAttribute("script-interfaces")) {
            cav.addIndexedArgumentValue(constructorArgNum++, element.getAttribute("script-interfaces"), "java.lang.Class[]");
         }

         if (element.hasAttribute("customizer-ref")) {
            String customizerBeanName = element.getAttribute("customizer-ref");
            if (!StringUtils.hasText(customizerBeanName)) {
               parserContext.getReaderContext().error("Attribute 'customizer-ref' has empty value", element);
            } else {
               cav.addIndexedArgumentValue(constructorArgNum++, (Object)(new RuntimeBeanReference(customizerBeanName)));
            }
         }

         parserContext.getDelegate().parsePropertyElements(element, bd);
         return bd;
      }
   }

   @Nullable
   private String resolveScriptSource(Element element, XmlReaderContext readerContext) {
      boolean hasScriptSource = element.hasAttribute("script-source");
      List elements = DomUtils.getChildElementsByTagName(element, "inline-script");
      if (hasScriptSource && !elements.isEmpty()) {
         readerContext.error("Only one of 'script-source' and 'inline-script' should be specified.", element);
         return null;
      } else if (hasScriptSource) {
         return element.getAttribute("script-source");
      } else if (!elements.isEmpty()) {
         Element inlineElement = (Element)elements.get(0);
         return "inline:" + DomUtils.getTextValue(inlineElement);
      } else {
         readerContext.error("Must specify either 'script-source' or 'inline-script'.", element);
         return null;
      }
   }

   protected boolean shouldGenerateIdAsFallback() {
      return true;
   }
}
