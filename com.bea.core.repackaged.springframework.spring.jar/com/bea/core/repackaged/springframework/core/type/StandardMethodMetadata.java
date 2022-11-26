package com.bea.core.repackaged.springframework.core.type;

import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

public class StandardMethodMetadata implements MethodMetadata {
   private final Method introspectedMethod;
   private final boolean nestedAnnotationsAsMap;

   public StandardMethodMetadata(Method introspectedMethod) {
      this(introspectedMethod, false);
   }

   public StandardMethodMetadata(Method introspectedMethod, boolean nestedAnnotationsAsMap) {
      Assert.notNull(introspectedMethod, (String)"Method must not be null");
      this.introspectedMethod = introspectedMethod;
      this.nestedAnnotationsAsMap = nestedAnnotationsAsMap;
   }

   public final Method getIntrospectedMethod() {
      return this.introspectedMethod;
   }

   public String getMethodName() {
      return this.introspectedMethod.getName();
   }

   public String getDeclaringClassName() {
      return this.introspectedMethod.getDeclaringClass().getName();
   }

   public String getReturnTypeName() {
      return this.introspectedMethod.getReturnType().getName();
   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.introspectedMethod.getModifiers());
   }

   public boolean isStatic() {
      return Modifier.isStatic(this.introspectedMethod.getModifiers());
   }

   public boolean isFinal() {
      return Modifier.isFinal(this.introspectedMethod.getModifiers());
   }

   public boolean isOverridable() {
      return !this.isStatic() && !this.isFinal() && !Modifier.isPrivate(this.introspectedMethod.getModifiers());
   }

   public boolean isAnnotated(String annotationName) {
      return AnnotatedElementUtils.isAnnotated(this.introspectedMethod, (String)annotationName);
   }

   @Nullable
   public Map getAnnotationAttributes(String annotationName) {
      return this.getAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public Map getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      return AnnotatedElementUtils.getMergedAnnotationAttributes(this.introspectedMethod, annotationName, classValuesAsString, this.nestedAnnotationsAsMap);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName) {
      return this.getAllAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      return AnnotatedElementUtils.getAllAnnotationAttributes(this.introspectedMethod, annotationName, classValuesAsString, this.nestedAnnotationsAsMap);
   }
}
