package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.aspectj.lang.annotation.After;
import com.bea.core.repackaged.aspectj.lang.annotation.AfterReturning;
import com.bea.core.repackaged.aspectj.lang.annotation.AfterThrowing;
import com.bea.core.repackaged.aspectj.lang.annotation.Around;
import com.bea.core.repackaged.aspectj.lang.annotation.Aspect;
import com.bea.core.repackaged.aspectj.lang.annotation.Before;
import com.bea.core.repackaged.aspectj.lang.annotation.Pointcut;
import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.springframework.aop.framework.AopConfigException;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class AbstractAspectJAdvisorFactory implements AspectJAdvisorFactory {
   private static final String AJC_MAGIC = "ajc$";
   private static final Class[] ASPECTJ_ANNOTATION_CLASSES = new Class[]{Pointcut.class, Around.class, Before.class, After.class, AfterReturning.class, AfterThrowing.class};
   protected final Log logger = LogFactory.getLog(this.getClass());
   protected final ParameterNameDiscoverer parameterNameDiscoverer = new AspectJAnnotationParameterNameDiscoverer();

   public boolean isAspect(Class clazz) {
      return this.hasAspectAnnotation(clazz) && !this.compiledByAjc(clazz);
   }

   private boolean hasAspectAnnotation(Class clazz) {
      return AnnotationUtils.findAnnotation(clazz, Aspect.class) != null;
   }

   private boolean compiledByAjc(Class clazz) {
      Field[] var2 = clazz.getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];
         if (field.getName().startsWith("ajc$")) {
            return true;
         }
      }

      return false;
   }

   public void validate(Class aspectClass) throws AopConfigException {
      if (aspectClass.getSuperclass().getAnnotation(Aspect.class) != null && !Modifier.isAbstract(aspectClass.getSuperclass().getModifiers())) {
         throw new AopConfigException("[" + aspectClass.getName() + "] cannot extend concrete aspect [" + aspectClass.getSuperclass().getName() + "]");
      } else {
         AjType ajType = AjTypeSystem.getAjType(aspectClass);
         if (!ajType.isAspect()) {
            throw new NotAnAtAspectException(aspectClass);
         } else if (ajType.getPerClause().getKind() == PerClauseKind.PERCFLOW) {
            throw new AopConfigException(aspectClass.getName() + " uses percflow instantiation model: This is not supported in Spring AOP.");
         } else if (ajType.getPerClause().getKind() == PerClauseKind.PERCFLOWBELOW) {
            throw new AopConfigException(aspectClass.getName() + " uses percflowbelow instantiation model: This is not supported in Spring AOP.");
         }
      }
   }

   @Nullable
   protected static AspectJAnnotation findAspectJAnnotationOnMethod(Method method) {
      Class[] var1 = ASPECTJ_ANNOTATION_CLASSES;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class clazz = var1[var3];
         AspectJAnnotation foundAnnotation = findAnnotation(method, clazz);
         if (foundAnnotation != null) {
            return foundAnnotation;
         }
      }

      return null;
   }

   @Nullable
   private static AspectJAnnotation findAnnotation(Method method, Class toLookFor) {
      Annotation result = AnnotationUtils.findAnnotation(method, toLookFor);
      return result != null ? new AspectJAnnotation(result) : null;
   }

   private static class AspectJAnnotationParameterNameDiscoverer implements ParameterNameDiscoverer {
      private AspectJAnnotationParameterNameDiscoverer() {
      }

      @Nullable
      public String[] getParameterNames(Method method) {
         if (method.getParameterCount() == 0) {
            return new String[0];
         } else {
            AspectJAnnotation annotation = AbstractAspectJAdvisorFactory.findAspectJAnnotationOnMethod(method);
            if (annotation == null) {
               return null;
            } else {
               StringTokenizer nameTokens = new StringTokenizer(annotation.getArgumentNames(), ",");
               if (nameTokens.countTokens() <= 0) {
                  return null;
               } else {
                  String[] names = new String[nameTokens.countTokens()];

                  for(int i = 0; i < names.length; ++i) {
                     names[i] = nameTokens.nextToken();
                  }

                  return names;
               }
            }
         }
      }

      @Nullable
      public String[] getParameterNames(Constructor ctor) {
         throw new UnsupportedOperationException("Spring AOP cannot handle constructor advice");
      }

      // $FF: synthetic method
      AspectJAnnotationParameterNameDiscoverer(Object x0) {
         this();
      }
   }

   protected static class AspectJAnnotation {
      private static final String[] EXPRESSION_ATTRIBUTES = new String[]{"pointcut", "value"};
      private static Map annotationTypeMap = new HashMap(8);
      private final Annotation annotation;
      private final AspectJAnnotationType annotationType;
      private final String pointcutExpression;
      private final String argumentNames;

      public AspectJAnnotation(Annotation annotation) {
         this.annotation = annotation;
         this.annotationType = this.determineAnnotationType(annotation);

         try {
            this.pointcutExpression = this.resolveExpression(annotation);
            Object argNames = AnnotationUtils.getValue(annotation, "argNames");
            this.argumentNames = argNames instanceof String ? (String)argNames : "";
         } catch (Exception var3) {
            throw new IllegalArgumentException(annotation + " is not a valid AspectJ annotation", var3);
         }
      }

      private AspectJAnnotationType determineAnnotationType(Annotation annotation) {
         AspectJAnnotationType type = (AspectJAnnotationType)annotationTypeMap.get(annotation.annotationType());
         if (type != null) {
            return type;
         } else {
            throw new IllegalStateException("Unknown annotation type: " + annotation);
         }
      }

      private String resolveExpression(Annotation annotation) {
         String[] var2 = EXPRESSION_ATTRIBUTES;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String attributeName = var2[var4];
            Object val = AnnotationUtils.getValue(annotation, attributeName);
            if (val instanceof String) {
               String str = (String)val;
               if (!str.isEmpty()) {
                  return str;
               }
            }
         }

         throw new IllegalStateException("Failed to resolve expression: " + annotation);
      }

      public AspectJAnnotationType getAnnotationType() {
         return this.annotationType;
      }

      public Annotation getAnnotation() {
         return this.annotation;
      }

      public String getPointcutExpression() {
         return this.pointcutExpression;
      }

      public String getArgumentNames() {
         return this.argumentNames;
      }

      public String toString() {
         return this.annotation.toString();
      }

      static {
         annotationTypeMap.put(Pointcut.class, AbstractAspectJAdvisorFactory.AspectJAnnotationType.AtPointcut);
         annotationTypeMap.put(Around.class, AbstractAspectJAdvisorFactory.AspectJAnnotationType.AtAround);
         annotationTypeMap.put(Before.class, AbstractAspectJAdvisorFactory.AspectJAnnotationType.AtBefore);
         annotationTypeMap.put(After.class, AbstractAspectJAdvisorFactory.AspectJAnnotationType.AtAfter);
         annotationTypeMap.put(AfterReturning.class, AbstractAspectJAdvisorFactory.AspectJAnnotationType.AtAfterReturning);
         annotationTypeMap.put(AfterThrowing.class, AbstractAspectJAdvisorFactory.AspectJAnnotationType.AtAfterThrowing);
      }
   }

   protected static enum AspectJAnnotationType {
      AtPointcut,
      AtAround,
      AtBefore,
      AtAfter,
      AtAfterReturning,
      AtAfterThrowing;
   }
}
