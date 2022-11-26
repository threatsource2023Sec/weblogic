package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import com.bea.core.repackaged.springframework.core.type.filter.AnnotationTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.AspectJTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.AssignableTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.RegexPatternTypeFilter;
import com.bea.core.repackaged.springframework.core.type.filter.TypeFilter;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

class ComponentScanAnnotationParser {
   private final Environment environment;
   private final ResourceLoader resourceLoader;
   private final BeanNameGenerator beanNameGenerator;
   private final BeanDefinitionRegistry registry;

   public ComponentScanAnnotationParser(Environment environment, ResourceLoader resourceLoader, BeanNameGenerator beanNameGenerator, BeanDefinitionRegistry registry) {
      this.environment = environment;
      this.resourceLoader = resourceLoader;
      this.beanNameGenerator = beanNameGenerator;
      this.registry = registry;
   }

   public Set parse(AnnotationAttributes componentScan, final String declaringClass) {
      ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.registry, componentScan.getBoolean("useDefaultFilters"), this.environment, this.resourceLoader);
      Class generatorClass = componentScan.getClass("nameGenerator");
      boolean useInheritedGenerator = BeanNameGenerator.class == generatorClass;
      scanner.setBeanNameGenerator(useInheritedGenerator ? this.beanNameGenerator : (BeanNameGenerator)BeanUtils.instantiateClass(generatorClass));
      ScopedProxyMode scopedProxyMode = (ScopedProxyMode)componentScan.getEnum("scopedProxy");
      if (scopedProxyMode != ScopedProxyMode.DEFAULT) {
         scanner.setScopedProxyMode(scopedProxyMode);
      } else {
         Class resolverClass = componentScan.getClass("scopeResolver");
         scanner.setScopeMetadataResolver((ScopeMetadataResolver)BeanUtils.instantiateClass(resolverClass));
      }

      scanner.setResourcePattern(componentScan.getString("resourcePattern"));
      AnnotationAttributes[] var15 = componentScan.getAnnotationArray("includeFilters");
      int var8 = var15.length;

      int var9;
      AnnotationAttributes filter;
      Iterator var11;
      TypeFilter typeFilter;
      for(var9 = 0; var9 < var8; ++var9) {
         filter = var15[var9];
         var11 = this.typeFiltersFor(filter).iterator();

         while(var11.hasNext()) {
            typeFilter = (TypeFilter)var11.next();
            scanner.addIncludeFilter(typeFilter);
         }
      }

      var15 = componentScan.getAnnotationArray("excludeFilters");
      var8 = var15.length;

      for(var9 = 0; var9 < var8; ++var9) {
         filter = var15[var9];
         var11 = this.typeFiltersFor(filter).iterator();

         while(var11.hasNext()) {
            typeFilter = (TypeFilter)var11.next();
            scanner.addExcludeFilter(typeFilter);
         }
      }

      boolean lazyInit = componentScan.getBoolean("lazyInit");
      if (lazyInit) {
         scanner.getBeanDefinitionDefaults().setLazyInit(true);
      }

      Set basePackages = new LinkedHashSet();
      String[] basePackagesArray = componentScan.getStringArray("basePackages");
      String[] var19 = basePackagesArray;
      int var21 = basePackagesArray.length;

      int var22;
      for(var22 = 0; var22 < var21; ++var22) {
         String pkg = var19[var22];
         String[] tokenized = StringUtils.tokenizeToStringArray(this.environment.resolvePlaceholders(pkg), ",; \t\n");
         Collections.addAll(basePackages, tokenized);
      }

      Class[] var20 = componentScan.getClassArray("basePackageClasses");
      var21 = var20.length;

      for(var22 = 0; var22 < var21; ++var22) {
         Class clazz = var20[var22];
         basePackages.add(ClassUtils.getPackageName(clazz));
      }

      if (basePackages.isEmpty()) {
         basePackages.add(ClassUtils.getPackageName(declaringClass));
      }

      scanner.addExcludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {
         protected boolean matchClassName(String className) {
            return declaringClass.equals(className);
         }
      });
      return scanner.doScan(StringUtils.toStringArray((Collection)basePackages));
   }

   private List typeFiltersFor(AnnotationAttributes filterAttributes) {
      List typeFilters = new ArrayList();
      FilterType filterType = (FilterType)filterAttributes.getEnum("type");
      Class[] var4 = filterAttributes.getClassArray("classes");
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         Class filterClass = var4[var6];
         switch (filterType) {
            case ANNOTATION:
               Assert.isAssignable(Annotation.class, filterClass, "@ComponentScan ANNOTATION type filter requires an annotation type");
               typeFilters.add(new AnnotationTypeFilter(filterClass));
               break;
            case ASSIGNABLE_TYPE:
               typeFilters.add(new AssignableTypeFilter(filterClass));
               break;
            case CUSTOM:
               Assert.isAssignable(TypeFilter.class, filterClass, "@ComponentScan CUSTOM type filter requires a TypeFilter implementation");
               TypeFilter filter = (TypeFilter)BeanUtils.instantiateClass(filterClass, TypeFilter.class);
               ParserStrategyUtils.invokeAwareMethods(filter, this.environment, this.resourceLoader, this.registry);
               typeFilters.add(filter);
               break;
            default:
               throw new IllegalArgumentException("Filter type not supported with Class value: " + filterType);
         }
      }

      String[] var10 = filterAttributes.getStringArray("pattern");
      var5 = var10.length;

      for(var6 = 0; var6 < var5; ++var6) {
         String expression = var10[var6];
         switch (filterType) {
            case ASPECTJ:
               typeFilters.add(new AspectJTypeFilter(expression, this.resourceLoader.getClassLoader()));
               break;
            case REGEX:
               typeFilters.add(new RegexPatternTypeFilter(Pattern.compile(expression)));
               break;
            default:
               throw new IllegalArgumentException("Filter type not supported with String pattern: " + filterType);
         }
      }

      return typeFilters;
   }
}
