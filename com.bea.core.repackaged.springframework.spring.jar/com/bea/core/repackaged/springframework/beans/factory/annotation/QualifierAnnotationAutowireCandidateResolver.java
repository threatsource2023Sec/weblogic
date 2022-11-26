package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.SimpleTypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.beans.factory.support.AutowireCandidateQualifier;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class QualifierAnnotationAutowireCandidateResolver extends GenericTypeAwareAutowireCandidateResolver {
   private final Set qualifierTypes = new LinkedHashSet(2);
   private Class valueAnnotationType = Value.class;

   public QualifierAnnotationAutowireCandidateResolver() {
      this.qualifierTypes.add(Qualifier.class);

      try {
         this.qualifierTypes.add(ClassUtils.forName("javax.inject.Qualifier", QualifierAnnotationAutowireCandidateResolver.class.getClassLoader()));
      } catch (ClassNotFoundException var2) {
      }

   }

   public QualifierAnnotationAutowireCandidateResolver(Class qualifierType) {
      Assert.notNull(qualifierType, (String)"'qualifierType' must not be null");
      this.qualifierTypes.add(qualifierType);
   }

   public QualifierAnnotationAutowireCandidateResolver(Set qualifierTypes) {
      Assert.notNull(qualifierTypes, (String)"'qualifierTypes' must not be null");
      this.qualifierTypes.addAll(qualifierTypes);
   }

   public void addQualifierType(Class qualifierType) {
      this.qualifierTypes.add(qualifierType);
   }

   public void setValueAnnotationType(Class valueAnnotationType) {
      this.valueAnnotationType = valueAnnotationType;
   }

   public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
      boolean match = super.isAutowireCandidate(bdHolder, descriptor);
      if (match) {
         match = this.checkQualifiers(bdHolder, descriptor.getAnnotations());
         if (match) {
            MethodParameter methodParam = descriptor.getMethodParameter();
            if (methodParam != null) {
               Method method = methodParam.getMethod();
               if (method == null || Void.TYPE == method.getReturnType()) {
                  match = this.checkQualifiers(bdHolder, methodParam.getMethodAnnotations());
               }
            }
         }
      }

      return match;
   }

   protected boolean checkQualifiers(BeanDefinitionHolder bdHolder, Annotation[] annotationsToSearch) {
      if (ObjectUtils.isEmpty((Object[])annotationsToSearch)) {
         return true;
      } else {
         SimpleTypeConverter typeConverter = new SimpleTypeConverter();
         Annotation[] var4 = annotationsToSearch;
         int var5 = annotationsToSearch.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Annotation annotation = var4[var6];
            Class type = annotation.annotationType();
            boolean checkMeta = true;
            boolean fallbackToMeta = false;
            if (this.isQualifier(type)) {
               if (!this.checkQualifier(bdHolder, annotation, typeConverter)) {
                  fallbackToMeta = true;
               } else {
                  checkMeta = false;
               }
            }

            if (checkMeta) {
               boolean foundMeta = false;
               Annotation[] var12 = type.getAnnotations();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Annotation metaAnn = var12[var14];
                  Class metaType = metaAnn.annotationType();
                  if (this.isQualifier(metaType)) {
                     foundMeta = true;
                     if (fallbackToMeta && StringUtils.isEmpty(AnnotationUtils.getValue(metaAnn)) || !this.checkQualifier(bdHolder, metaAnn, typeConverter)) {
                        return false;
                     }
                  }
               }

               if (fallbackToMeta && !foundMeta) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   protected boolean isQualifier(Class annotationType) {
      Iterator var2 = this.qualifierTypes.iterator();

      Class qualifierType;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         qualifierType = (Class)var2.next();
      } while(!annotationType.equals(qualifierType) && !annotationType.isAnnotationPresent(qualifierType));

      return true;
   }

   protected boolean checkQualifier(BeanDefinitionHolder bdHolder, Annotation annotation, TypeConverter typeConverter) {
      Class type = annotation.annotationType();
      RootBeanDefinition bd = (RootBeanDefinition)bdHolder.getBeanDefinition();
      AutowireCandidateQualifier qualifier = bd.getQualifier(type.getName());
      if (qualifier == null) {
         qualifier = bd.getQualifier(ClassUtils.getShortName(type));
      }

      if (qualifier == null) {
         Annotation targetAnnotation = this.getQualifiedElementAnnotation(bd, type);
         if (targetAnnotation == null) {
            targetAnnotation = this.getFactoryMethodAnnotation(bd, type);
         }

         if (targetAnnotation == null) {
            RootBeanDefinition dbd = this.getResolvedDecoratedDefinition(bd);
            if (dbd != null) {
               targetAnnotation = this.getFactoryMethodAnnotation(dbd, type);
            }
         }

         if (targetAnnotation == null) {
            if (this.getBeanFactory() != null) {
               try {
                  Class beanType = this.getBeanFactory().getType(bdHolder.getBeanName());
                  if (beanType != null) {
                     targetAnnotation = AnnotationUtils.getAnnotation((AnnotatedElement)ClassUtils.getUserClass(beanType), (Class)type);
                  }
               } catch (NoSuchBeanDefinitionException var13) {
               }
            }

            if (targetAnnotation == null && bd.hasBeanClass()) {
               targetAnnotation = AnnotationUtils.getAnnotation((AnnotatedElement)ClassUtils.getUserClass(bd.getBeanClass()), (Class)type);
            }
         }

         if (targetAnnotation != null && targetAnnotation.equals(annotation)) {
            return true;
         }
      }

      Map attributes = AnnotationUtils.getAnnotationAttributes(annotation);
      if (attributes.isEmpty() && qualifier == null) {
         return false;
      } else {
         Iterator var15 = attributes.entrySet().iterator();

         Object expectedValue;
         Object actualValue;
         do {
            String attributeName;
            do {
               if (!var15.hasNext()) {
                  return true;
               }

               Map.Entry entry = (Map.Entry)var15.next();
               attributeName = (String)entry.getKey();
               expectedValue = entry.getValue();
               actualValue = null;
               if (qualifier != null) {
                  actualValue = qualifier.getAttribute(attributeName);
               }

               if (actualValue == null) {
                  actualValue = bd.getAttribute(attributeName);
               }
            } while(actualValue == null && attributeName.equals("value") && expectedValue instanceof String && bdHolder.matchesName((String)expectedValue));

            if (actualValue == null && qualifier != null) {
               actualValue = AnnotationUtils.getDefaultValue(annotation, attributeName);
            }

            if (actualValue != null) {
               actualValue = typeConverter.convertIfNecessary(actualValue, expectedValue.getClass());
            }
         } while(expectedValue.equals(actualValue));

         return false;
      }
   }

   @Nullable
   protected Annotation getQualifiedElementAnnotation(RootBeanDefinition bd, Class type) {
      AnnotatedElement qualifiedElement = bd.getQualifiedElement();
      return qualifiedElement != null ? AnnotationUtils.getAnnotation(qualifiedElement, type) : null;
   }

   @Nullable
   protected Annotation getFactoryMethodAnnotation(RootBeanDefinition bd, Class type) {
      Method resolvedFactoryMethod = bd.getResolvedFactoryMethod();
      return resolvedFactoryMethod != null ? AnnotationUtils.getAnnotation(resolvedFactoryMethod, type) : null;
   }

   public boolean isRequired(DependencyDescriptor descriptor) {
      if (!super.isRequired(descriptor)) {
         return false;
      } else {
         Autowired autowired = (Autowired)descriptor.getAnnotation(Autowired.class);
         return autowired == null || autowired.required();
      }
   }

   public boolean hasQualifier(DependencyDescriptor descriptor) {
      Annotation[] var2 = descriptor.getAnnotations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation ann = var2[var4];
         if (this.isQualifier(ann.annotationType())) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public Object getSuggestedValue(DependencyDescriptor descriptor) {
      Object value = this.findValue(descriptor.getAnnotations());
      if (value == null) {
         MethodParameter methodParam = descriptor.getMethodParameter();
         if (methodParam != null) {
            value = this.findValue(methodParam.getMethodAnnotations());
         }
      }

      return value;
   }

   @Nullable
   protected Object findValue(Annotation[] annotationsToSearch) {
      if (annotationsToSearch.length > 0) {
         AnnotationAttributes attr = AnnotatedElementUtils.getMergedAnnotationAttributes(AnnotatedElementUtils.forAnnotations(annotationsToSearch), this.valueAnnotationType);
         if (attr != null) {
            return this.extractValue(attr);
         }
      }

      return null;
   }

   protected Object extractValue(AnnotationAttributes attr) {
      Object value = attr.get("value");
      if (value == null) {
         throw new IllegalStateException("Value annotation must have a value attribute");
      } else {
         return value;
      }
   }
}
