package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.Introspector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {
   private static final String COMPONENT_ANNOTATION_CLASSNAME = "com.bea.core.repackaged.springframework.stereotype.Component";

   public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
      if (definition instanceof AnnotatedBeanDefinition) {
         String beanName = this.determineBeanNameFromAnnotation((AnnotatedBeanDefinition)definition);
         if (StringUtils.hasText(beanName)) {
            return beanName;
         }
      }

      return this.buildDefaultBeanName(definition, registry);
   }

   @Nullable
   protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
      AnnotationMetadata amd = annotatedDef.getMetadata();
      Set types = amd.getAnnotationTypes();
      String beanName = null;
      Iterator var5 = types.iterator();

      while(var5.hasNext()) {
         String type = (String)var5.next();
         AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(amd, (String)type);
         if (attributes != null && this.isStereotypeWithNameValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
            Object value = attributes.get("value");
            if (value instanceof String) {
               String strVal = (String)value;
               if (StringUtils.hasLength(strVal)) {
                  if (beanName != null && !strVal.equals(beanName)) {
                     throw new IllegalStateException("Stereotype annotations suggest inconsistent component names: '" + beanName + "' versus '" + strVal + "'");
                  }

                  beanName = strVal;
               }
            }
         }
      }

      return beanName;
   }

   protected boolean isStereotypeWithNameValue(String annotationType, Set metaAnnotationTypes, @Nullable Map attributes) {
      boolean isStereotype = annotationType.equals("com.bea.core.repackaged.springframework.stereotype.Component") || metaAnnotationTypes.contains("com.bea.core.repackaged.springframework.stereotype.Component") || annotationType.equals("javax.annotation.ManagedBean") || annotationType.equals("javax.inject.Named");
      return isStereotype && attributes != null && attributes.containsKey("value");
   }

   protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
      return this.buildDefaultBeanName(definition);
   }

   protected String buildDefaultBeanName(BeanDefinition definition) {
      String beanClassName = definition.getBeanClassName();
      Assert.state(beanClassName != null, "No bean class name set");
      String shortClassName = ClassUtils.getShortName(beanClassName);
      return Introspector.decapitalize(shortClassName);
   }
}
