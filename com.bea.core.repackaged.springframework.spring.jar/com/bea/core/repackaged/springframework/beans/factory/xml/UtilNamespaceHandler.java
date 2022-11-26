package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.FieldRetrievingFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.ListFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.MapFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.PropertiesFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.PropertyPathFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.SetFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Element;

public class UtilNamespaceHandler extends NamespaceHandlerSupport {
   private static final String SCOPE_ATTRIBUTE = "scope";

   public void init() {
      this.registerBeanDefinitionParser("constant", new ConstantBeanDefinitionParser());
      this.registerBeanDefinitionParser("property-path", new PropertyPathBeanDefinitionParser());
      this.registerBeanDefinitionParser("list", new ListBeanDefinitionParser());
      this.registerBeanDefinitionParser("set", new SetBeanDefinitionParser());
      this.registerBeanDefinitionParser("map", new MapBeanDefinitionParser());
      this.registerBeanDefinitionParser("properties", new PropertiesBeanDefinitionParser());
   }

   private static class PropertiesBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
      private PropertiesBeanDefinitionParser() {
      }

      protected Class getBeanClass(Element element) {
         return PropertiesFactoryBean.class;
      }

      protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
         Properties parsedProps = parserContext.getDelegate().parsePropsElement(element);
         builder.addPropertyValue("properties", parsedProps);
         String location = element.getAttribute("location");
         if (StringUtils.hasLength(location)) {
            location = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(location);
            String[] locations = StringUtils.commaDelimitedListToStringArray(location);
            builder.addPropertyValue("locations", locations);
         }

         builder.addPropertyValue("ignoreResourceNotFound", Boolean.valueOf(element.getAttribute("ignore-resource-not-found")));
         builder.addPropertyValue("localOverride", Boolean.valueOf(element.getAttribute("local-override")));
         String scope = element.getAttribute("scope");
         if (StringUtils.hasLength(scope)) {
            builder.setScope(scope);
         }

      }

      // $FF: synthetic method
      PropertiesBeanDefinitionParser(Object x0) {
         this();
      }
   }

   private static class MapBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
      private MapBeanDefinitionParser() {
      }

      protected Class getBeanClass(Element element) {
         return MapFactoryBean.class;
      }

      protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
         Map parsedMap = parserContext.getDelegate().parseMapElement(element, builder.getRawBeanDefinition());
         builder.addPropertyValue("sourceMap", parsedMap);
         String mapClass = element.getAttribute("map-class");
         if (StringUtils.hasText(mapClass)) {
            builder.addPropertyValue("targetMapClass", mapClass);
         }

         String scope = element.getAttribute("scope");
         if (StringUtils.hasLength(scope)) {
            builder.setScope(scope);
         }

      }

      // $FF: synthetic method
      MapBeanDefinitionParser(Object x0) {
         this();
      }
   }

   private static class SetBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
      private SetBeanDefinitionParser() {
      }

      protected Class getBeanClass(Element element) {
         return SetFactoryBean.class;
      }

      protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
         Set parsedSet = parserContext.getDelegate().parseSetElement(element, builder.getRawBeanDefinition());
         builder.addPropertyValue("sourceSet", parsedSet);
         String setClass = element.getAttribute("set-class");
         if (StringUtils.hasText(setClass)) {
            builder.addPropertyValue("targetSetClass", setClass);
         }

         String scope = element.getAttribute("scope");
         if (StringUtils.hasLength(scope)) {
            builder.setScope(scope);
         }

      }

      // $FF: synthetic method
      SetBeanDefinitionParser(Object x0) {
         this();
      }
   }

   private static class ListBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
      private ListBeanDefinitionParser() {
      }

      protected Class getBeanClass(Element element) {
         return ListFactoryBean.class;
      }

      protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
         List parsedList = parserContext.getDelegate().parseListElement(element, builder.getRawBeanDefinition());
         builder.addPropertyValue("sourceList", parsedList);
         String listClass = element.getAttribute("list-class");
         if (StringUtils.hasText(listClass)) {
            builder.addPropertyValue("targetListClass", listClass);
         }

         String scope = element.getAttribute("scope");
         if (StringUtils.hasLength(scope)) {
            builder.setScope(scope);
         }

      }

      // $FF: synthetic method
      ListBeanDefinitionParser(Object x0) {
         this();
      }
   }

   private static class PropertyPathBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
      private PropertyPathBeanDefinitionParser() {
      }

      protected Class getBeanClass(Element element) {
         return PropertyPathFactoryBean.class;
      }

      protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
         String path = element.getAttribute("path");
         if (!StringUtils.hasText(path)) {
            parserContext.getReaderContext().error("Attribute 'path' must not be empty", element);
         } else {
            int dotIndex = path.indexOf(46);
            if (dotIndex == -1) {
               parserContext.getReaderContext().error("Attribute 'path' must follow pattern 'beanName.propertyName'", element);
            } else {
               String beanName = path.substring(0, dotIndex);
               String propertyPath = path.substring(dotIndex + 1);
               builder.addPropertyValue("targetBeanName", beanName);
               builder.addPropertyValue("propertyPath", propertyPath);
            }
         }
      }

      protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) {
         String id = super.resolveId(element, definition, parserContext);
         if (!StringUtils.hasText(id)) {
            id = element.getAttribute("path");
         }

         return id;
      }

      // $FF: synthetic method
      PropertyPathBeanDefinitionParser(Object x0) {
         this();
      }
   }

   private static class ConstantBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
      private ConstantBeanDefinitionParser() {
      }

      protected Class getBeanClass(Element element) {
         return FieldRetrievingFactoryBean.class;
      }

      protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) {
         String id = super.resolveId(element, definition, parserContext);
         if (!StringUtils.hasText(id)) {
            id = element.getAttribute("static-field");
         }

         return id;
      }

      // $FF: synthetic method
      ConstantBeanDefinitionParser(Object x0) {
         this();
      }
   }
}
