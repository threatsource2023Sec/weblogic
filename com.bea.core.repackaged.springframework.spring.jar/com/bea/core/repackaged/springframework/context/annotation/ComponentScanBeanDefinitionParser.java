package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlReaderContext;
import com.bea.core.repackaged.springframework.core.type.filter.AnnotationTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.AspectJTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.AssignableTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.RegexPatternTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.TypeFilter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {
   private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
   private static final String RESOURCE_PATTERN_ATTRIBUTE = "resource-pattern";
   private static final String USE_DEFAULT_FILTERS_ATTRIBUTE = "use-default-filters";
   private static final String ANNOTATION_CONFIG_ATTRIBUTE = "annotation-config";
   private static final String NAME_GENERATOR_ATTRIBUTE = "name-generator";
   private static final String SCOPE_RESOLVER_ATTRIBUTE = "scope-resolver";
   private static final String SCOPED_PROXY_ATTRIBUTE = "scoped-proxy";
   private static final String EXCLUDE_FILTER_ELEMENT = "exclude-filter";
   private static final String INCLUDE_FILTER_ELEMENT = "include-filter";
   private static final String FILTER_TYPE_ATTRIBUTE = "type";
   private static final String FILTER_EXPRESSION_ATTRIBUTE = "expression";

   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      String basePackage = element.getAttribute("base-package");
      basePackage = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(basePackage);
      String[] basePackages = StringUtils.tokenizeToStringArray(basePackage, ",; \t\n");
      ClassPathBeanDefinitionScanner scanner = this.configureScanner(parserContext, element);
      Set beanDefinitions = scanner.doScan(basePackages);
      this.registerComponents(parserContext.getReaderContext(), beanDefinitions, element);
      return null;
   }

   protected ClassPathBeanDefinitionScanner configureScanner(ParserContext parserContext, Element element) {
      boolean useDefaultFilters = true;
      if (element.hasAttribute("use-default-filters")) {
         useDefaultFilters = Boolean.valueOf(element.getAttribute("use-default-filters"));
      }

      ClassPathBeanDefinitionScanner scanner = this.createScanner(parserContext.getReaderContext(), useDefaultFilters);
      scanner.setBeanDefinitionDefaults(parserContext.getDelegate().getBeanDefinitionDefaults());
      scanner.setAutowireCandidatePatterns(parserContext.getDelegate().getAutowireCandidatePatterns());
      if (element.hasAttribute("resource-pattern")) {
         scanner.setResourcePattern(element.getAttribute("resource-pattern"));
      }

      try {
         this.parseBeanNameGenerator(element, scanner);
      } catch (Exception var7) {
         parserContext.getReaderContext().error(var7.getMessage(), parserContext.extractSource(element), var7.getCause());
      }

      try {
         this.parseScope(element, scanner);
      } catch (Exception var6) {
         parserContext.getReaderContext().error(var6.getMessage(), parserContext.extractSource(element), var6.getCause());
      }

      this.parseTypeFilters(element, scanner, parserContext);
      return scanner;
   }

   protected ClassPathBeanDefinitionScanner createScanner(XmlReaderContext readerContext, boolean useDefaultFilters) {
      return new ClassPathBeanDefinitionScanner(readerContext.getRegistry(), useDefaultFilters, readerContext.getEnvironment(), readerContext.getResourceLoader());
   }

   protected void registerComponents(XmlReaderContext readerContext, Set beanDefinitions, Element element) {
      Object source = readerContext.extractSource(element);
      CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), source);
      Iterator var6 = beanDefinitions.iterator();

      while(var6.hasNext()) {
         BeanDefinitionHolder beanDefHolder = (BeanDefinitionHolder)var6.next();
         compositeDef.addNestedComponent(new BeanComponentDefinition(beanDefHolder));
      }

      boolean annotationConfig = true;
      if (element.hasAttribute("annotation-config")) {
         annotationConfig = Boolean.valueOf(element.getAttribute("annotation-config"));
      }

      if (annotationConfig) {
         Set processorDefinitions = AnnotationConfigUtils.registerAnnotationConfigProcessors(readerContext.getRegistry(), source);
         Iterator var8 = processorDefinitions.iterator();

         while(var8.hasNext()) {
            BeanDefinitionHolder processorDefinition = (BeanDefinitionHolder)var8.next();
            compositeDef.addNestedComponent(new BeanComponentDefinition(processorDefinition));
         }
      }

      readerContext.fireComponentRegistered(compositeDef);
   }

   protected void parseBeanNameGenerator(Element element, ClassPathBeanDefinitionScanner scanner) {
      if (element.hasAttribute("name-generator")) {
         BeanNameGenerator beanNameGenerator = (BeanNameGenerator)this.instantiateUserDefinedStrategy(element.getAttribute("name-generator"), BeanNameGenerator.class, scanner.getResourceLoader().getClassLoader());
         scanner.setBeanNameGenerator(beanNameGenerator);
      }

   }

   protected void parseScope(Element element, ClassPathBeanDefinitionScanner scanner) {
      if (element.hasAttribute("scope-resolver")) {
         if (element.hasAttribute("scoped-proxy")) {
            throw new IllegalArgumentException("Cannot define both 'scope-resolver' and 'scoped-proxy' on <component-scan> tag");
         }

         ScopeMetadataResolver scopeMetadataResolver = (ScopeMetadataResolver)this.instantiateUserDefinedStrategy(element.getAttribute("scope-resolver"), ScopeMetadataResolver.class, scanner.getResourceLoader().getClassLoader());
         scanner.setScopeMetadataResolver(scopeMetadataResolver);
      }

      if (element.hasAttribute("scoped-proxy")) {
         String mode = element.getAttribute("scoped-proxy");
         if ("targetClass".equals(mode)) {
            scanner.setScopedProxyMode(ScopedProxyMode.TARGET_CLASS);
         } else if ("interfaces".equals(mode)) {
            scanner.setScopedProxyMode(ScopedProxyMode.INTERFACES);
         } else {
            if (!"no".equals(mode)) {
               throw new IllegalArgumentException("scoped-proxy only supports 'no', 'interfaces' and 'targetClass'");
            }

            scanner.setScopedProxyMode(ScopedProxyMode.NO);
         }
      }

   }

   protected void parseTypeFilters(Element element, ClassPathBeanDefinitionScanner scanner, ParserContext parserContext) {
      ClassLoader classLoader = scanner.getResourceLoader().getClassLoader();
      NodeList nodeList = element.getChildNodes();

      for(int i = 0; i < nodeList.getLength(); ++i) {
         Node node = nodeList.item(i);
         if (node.getNodeType() == 1) {
            String localName = parserContext.getDelegate().getLocalName(node);

            try {
               TypeFilter typeFilter;
               if ("include-filter".equals(localName)) {
                  typeFilter = this.createTypeFilter((Element)node, classLoader, parserContext);
                  scanner.addIncludeFilter(typeFilter);
               } else if ("exclude-filter".equals(localName)) {
                  typeFilter = this.createTypeFilter((Element)node, classLoader, parserContext);
                  scanner.addExcludeFilter(typeFilter);
               }
            } catch (ClassNotFoundException var10) {
               parserContext.getReaderContext().warning("Ignoring non-present type filter class: " + var10, parserContext.extractSource(element));
            } catch (Exception var11) {
               parserContext.getReaderContext().error(var11.getMessage(), parserContext.extractSource(element), var11.getCause());
            }
         }
      }

   }

   protected TypeFilter createTypeFilter(Element element, @Nullable ClassLoader classLoader, ParserContext parserContext) throws ClassNotFoundException {
      String filterType = element.getAttribute("type");
      String expression = element.getAttribute("expression");
      expression = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(expression);
      if ("annotation".equals(filterType)) {
         return new AnnotationTypeFilter(ClassUtils.forName(expression, classLoader));
      } else if ("assignable".equals(filterType)) {
         return new AssignableTypeFilter(ClassUtils.forName(expression, classLoader));
      } else if ("aspectj".equals(filterType)) {
         return new AspectJTypeFilter(expression, classLoader);
      } else if ("regex".equals(filterType)) {
         return new RegexPatternTypeFilter(Pattern.compile(expression));
      } else if ("custom".equals(filterType)) {
         Class filterClass = ClassUtils.forName(expression, classLoader);
         if (!TypeFilter.class.isAssignableFrom(filterClass)) {
            throw new IllegalArgumentException("Class is not assignable to [" + TypeFilter.class.getName() + "]: " + expression);
         } else {
            return (TypeFilter)BeanUtils.instantiateClass(filterClass);
         }
      } else {
         throw new IllegalArgumentException("Unsupported filter type: " + filterType);
      }
   }

   private Object instantiateUserDefinedStrategy(String className, Class strategyType, @Nullable ClassLoader classLoader) {
      Object result;
      try {
         result = ReflectionUtils.accessibleConstructor(ClassUtils.forName(className, classLoader)).newInstance();
      } catch (ClassNotFoundException var6) {
         throw new IllegalArgumentException("Class [" + className + "] for strategy [" + strategyType.getName() + "] not found", var6);
      } catch (Throwable var7) {
         throw new IllegalArgumentException("Unable to instantiate class [" + className + "] for strategy [" + strategyType.getName() + "]: a zero-argument constructor is required", var7);
      }

      if (!strategyType.isAssignableFrom(result.getClass())) {
         throw new IllegalArgumentException("Provided class name must be an implementation of " + strategyType);
      } else {
         return result;
      }
   }
}
