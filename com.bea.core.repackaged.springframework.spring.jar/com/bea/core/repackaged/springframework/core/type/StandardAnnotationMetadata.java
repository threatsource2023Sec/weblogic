package com.bea.core.repackaged.springframework.core.type;

import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class StandardAnnotationMetadata extends StandardClassMetadata implements AnnotationMetadata {
   private final Annotation[] annotations;
   private final boolean nestedAnnotationsAsMap;

   public StandardAnnotationMetadata(Class introspectedClass) {
      this(introspectedClass, false);
   }

   public StandardAnnotationMetadata(Class introspectedClass, boolean nestedAnnotationsAsMap) {
      super(introspectedClass);
      this.annotations = introspectedClass.getAnnotations();
      this.nestedAnnotationsAsMap = nestedAnnotationsAsMap;
   }

   public Set getAnnotationTypes() {
      Set types = new LinkedHashSet();
      Annotation[] var2 = this.annotations;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation ann = var2[var4];
         types.add(ann.annotationType().getName());
      }

      return types;
   }

   public Set getMetaAnnotationTypes(String annotationName) {
      return this.annotations.length > 0 ? AnnotatedElementUtils.getMetaAnnotationTypes(this.getIntrospectedClass(), (String)annotationName) : Collections.emptySet();
   }

   public boolean hasAnnotation(String annotationName) {
      Annotation[] var2 = this.annotations;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation ann = var2[var4];
         if (ann.annotationType().getName().equals(annotationName)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasMetaAnnotation(String annotationName) {
      return this.annotations.length > 0 && AnnotatedElementUtils.hasMetaAnnotationTypes(this.getIntrospectedClass(), (String)annotationName);
   }

   public boolean isAnnotated(String annotationName) {
      return this.annotations.length > 0 && AnnotatedElementUtils.isAnnotated(this.getIntrospectedClass(), (String)annotationName);
   }

   public Map getAnnotationAttributes(String annotationName) {
      return this.getAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public Map getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      return this.annotations.length > 0 ? AnnotatedElementUtils.getMergedAnnotationAttributes(this.getIntrospectedClass(), annotationName, classValuesAsString, this.nestedAnnotationsAsMap) : null;
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName) {
      return this.getAllAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      return this.annotations.length > 0 ? AnnotatedElementUtils.getAllAnnotationAttributes(this.getIntrospectedClass(), annotationName, classValuesAsString, this.nestedAnnotationsAsMap) : null;
   }

   public boolean hasAnnotatedMethods(String annotationName) {
      try {
         Method[] methods = this.getIntrospectedClass().getDeclaredMethods();
         Method[] var3 = methods;
         int var4 = methods.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            if (!method.isBridge() && method.getAnnotations().length > 0 && AnnotatedElementUtils.isAnnotated(method, (String)annotationName)) {
               return true;
            }
         }

         return false;
      } catch (Throwable var7) {
         throw new IllegalStateException("Failed to introspect annotated methods on " + this.getIntrospectedClass(), var7);
      }
   }

   public Set getAnnotatedMethods(String annotationName) {
      try {
         Method[] methods = this.getIntrospectedClass().getDeclaredMethods();
         Set annotatedMethods = new LinkedHashSet(4);
         Method[] var4 = methods;
         int var5 = methods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (!method.isBridge() && method.getAnnotations().length > 0 && AnnotatedElementUtils.isAnnotated(method, (String)annotationName)) {
               annotatedMethods.add(new StandardMethodMetadata(method, this.nestedAnnotationsAsMap));
            }
         }

         return annotatedMethods;
      } catch (Throwable var8) {
         throw new IllegalStateException("Failed to introspect annotated methods on " + this.getIntrospectedClass(), var8);
      }
   }
}
