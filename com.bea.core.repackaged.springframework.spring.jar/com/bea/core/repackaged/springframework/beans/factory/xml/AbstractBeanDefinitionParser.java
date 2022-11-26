package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

public abstract class AbstractBeanDefinitionParser implements BeanDefinitionParser {
   public static final String ID_ATTRIBUTE = "id";
   public static final String NAME_ATTRIBUTE = "name";

   @Nullable
   public final BeanDefinition parse(Element element, ParserContext parserContext) {
      AbstractBeanDefinition definition = this.parseInternal(element, parserContext);
      if (definition != null && !parserContext.isNested()) {
         try {
            String id = this.resolveId(element, definition, parserContext);
            if (!StringUtils.hasText(id)) {
               parserContext.getReaderContext().error("Id is required for element '" + parserContext.getDelegate().getLocalName(element) + "' when used as a top-level tag", element);
            }

            String[] aliases = null;
            if (this.shouldParseNameAsAliases()) {
               String name = element.getAttribute("name");
               if (StringUtils.hasLength(name)) {
                  aliases = StringUtils.trimArrayElements(StringUtils.commaDelimitedListToStringArray(name));
               }
            }

            BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, id, aliases);
            this.registerBeanDefinition(holder, parserContext.getRegistry());
            if (this.shouldFireEvents()) {
               BeanComponentDefinition componentDefinition = new BeanComponentDefinition(holder);
               this.postProcessComponentDefinition(componentDefinition);
               parserContext.registerComponent(componentDefinition);
            }
         } catch (BeanDefinitionStoreException var8) {
            String msg = var8.getMessage();
            parserContext.getReaderContext().error(msg != null ? msg : var8.toString(), element);
            return null;
         }
      }

      return definition;
   }

   protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
      if (this.shouldGenerateId()) {
         return parserContext.getReaderContext().generateBeanName(definition);
      } else {
         String id = element.getAttribute("id");
         if (!StringUtils.hasText(id) && this.shouldGenerateIdAsFallback()) {
            id = parserContext.getReaderContext().generateBeanName(definition);
         }

         return id;
      }
   }

   protected void registerBeanDefinition(BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {
      BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
   }

   @Nullable
   protected abstract AbstractBeanDefinition parseInternal(Element var1, ParserContext var2);

   protected boolean shouldGenerateId() {
      return false;
   }

   protected boolean shouldGenerateIdAsFallback() {
      return false;
   }

   protected boolean shouldParseNameAsAliases() {
      return true;
   }

   protected boolean shouldFireEvents() {
      return true;
   }

   protected void postProcessComponentDefinition(BeanComponentDefinition componentDefinition) {
   }
}
