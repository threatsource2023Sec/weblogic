package com.bea.core.repackaged.springframework.context.config;

import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import org.w3c.dom.Element;

class LoadTimeWeaverBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   public static final String ASPECTJ_WEAVING_ENABLER_BEAN_NAME = "com.bea.core.repackaged.springframework.context.config.internalAspectJWeavingEnabler";
   private static final String ASPECTJ_WEAVING_ENABLER_CLASS_NAME = "com.bea.core.repackaged.springframework.context.weaving.AspectJWeavingEnabler";
   private static final String DEFAULT_LOAD_TIME_WEAVER_CLASS_NAME = "com.bea.core.repackaged.springframework.context.weaving.DefaultContextLoadTimeWeaver";
   private static final String WEAVER_CLASS_ATTRIBUTE = "weaver-class";
   private static final String ASPECTJ_WEAVING_ATTRIBUTE = "aspectj-weaving";

   protected String getBeanClassName(Element element) {
      return element.hasAttribute("weaver-class") ? element.getAttribute("weaver-class") : "com.bea.core.repackaged.springframework.context.weaving.DefaultContextLoadTimeWeaver";
   }

   protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) {
      return "loadTimeWeaver";
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      builder.setRole(2);
      if (this.isAspectJWeavingEnabled(element.getAttribute("aspectj-weaving"), parserContext)) {
         if (!parserContext.getRegistry().containsBeanDefinition("com.bea.core.repackaged.springframework.context.config.internalAspectJWeavingEnabler")) {
            RootBeanDefinition def = new RootBeanDefinition("com.bea.core.repackaged.springframework.context.weaving.AspectJWeavingEnabler");
            parserContext.registerBeanComponent(new BeanComponentDefinition(def, "com.bea.core.repackaged.springframework.context.config.internalAspectJWeavingEnabler"));
         }

         if (this.isBeanConfigurerAspectEnabled(parserContext.getReaderContext().getBeanClassLoader())) {
            (new SpringConfiguredBeanDefinitionParser()).parse(element, parserContext);
         }
      }

   }

   protected boolean isAspectJWeavingEnabled(String value, ParserContext parserContext) {
      if ("on".equals(value)) {
         return true;
      } else if ("off".equals(value)) {
         return false;
      } else {
         ClassLoader cl = parserContext.getReaderContext().getBeanClassLoader();
         return cl != null && cl.getResource("META-INF/aop.xml") != null;
      }
   }

   protected boolean isBeanConfigurerAspectEnabled(@Nullable ClassLoader beanClassLoader) {
      return ClassUtils.isPresent("com.bea.core.repackaged.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect", beanClassLoader);
   }
}
