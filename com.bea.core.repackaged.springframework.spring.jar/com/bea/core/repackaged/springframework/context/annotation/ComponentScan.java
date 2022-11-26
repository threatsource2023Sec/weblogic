package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.core.annotation.AliasFor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
   @AliasFor("basePackages")
   String[] value() default {};

   @AliasFor("value")
   String[] basePackages() default {};

   Class[] basePackageClasses() default {};

   Class nameGenerator() default BeanNameGenerator.class;

   Class scopeResolver() default AnnotationScopeMetadataResolver.class;

   ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;

   String resourcePattern() default "**/*.class";

   boolean useDefaultFilters() default true;

   Filter[] includeFilters() default {};

   Filter[] excludeFilters() default {};

   boolean lazyInit() default false;

   @Retention(RetentionPolicy.RUNTIME)
   @Target({})
   public @interface Filter {
      FilterType type() default FilterType.ANNOTATION;

      @AliasFor("classes")
      Class[] value() default {};

      @AliasFor("value")
      Class[] classes() default {};

      String[] pattern() default {};
   }
}
