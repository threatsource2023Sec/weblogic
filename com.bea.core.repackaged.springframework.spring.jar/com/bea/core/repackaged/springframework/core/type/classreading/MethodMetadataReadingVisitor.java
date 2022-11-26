package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.asm.AnnotationVisitor;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MethodMetadataReadingVisitor extends MethodVisitor implements MethodMetadata {
   protected final String methodName;
   protected final int access;
   protected final String declaringClassName;
   protected final String returnTypeName;
   @Nullable
   protected final ClassLoader classLoader;
   protected final Set methodMetadataSet;
   protected final Map metaAnnotationMap = new LinkedHashMap(4);
   protected final LinkedMultiValueMap attributesMap = new LinkedMultiValueMap(4);

   public MethodMetadataReadingVisitor(String methodName, int access, String declaringClassName, String returnTypeName, @Nullable ClassLoader classLoader, Set methodMetadataSet) {
      super(458752);
      this.methodName = methodName;
      this.access = access;
      this.declaringClassName = declaringClassName;
      this.returnTypeName = returnTypeName;
      this.classLoader = classLoader;
      this.methodMetadataSet = methodMetadataSet;
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      this.methodMetadataSet.add(this);
      String className = Type.getType(desc).getClassName();
      return new AnnotationAttributesReadingVisitor(className, this.attributesMap, this.metaAnnotationMap, this.classLoader);
   }

   public String getMethodName() {
      return this.methodName;
   }

   public boolean isAbstract() {
      return (this.access & 1024) != 0;
   }

   public boolean isStatic() {
      return (this.access & 8) != 0;
   }

   public boolean isFinal() {
      return (this.access & 16) != 0;
   }

   public boolean isOverridable() {
      return !this.isStatic() && !this.isFinal() && (this.access & 2) == 0;
   }

   public boolean isAnnotated(String annotationName) {
      return this.attributesMap.containsKey(annotationName);
   }

   @Nullable
   public AnnotationAttributes getAnnotationAttributes(String annotationName) {
      return this.getAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public AnnotationAttributes getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      AnnotationAttributes raw = AnnotationReadingVisitorUtils.getMergedAnnotationAttributes(this.attributesMap, this.metaAnnotationMap, annotationName);
      return raw == null ? null : AnnotationReadingVisitorUtils.convertClassValues("method '" + this.getMethodName() + "'", this.classLoader, raw, classValuesAsString);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName) {
      return this.getAllAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      if (!this.attributesMap.containsKey(annotationName)) {
         return null;
      } else {
         MultiValueMap allAttributes = new LinkedMultiValueMap();
         List attributesList = this.attributesMap.get(annotationName);
         if (attributesList != null) {
            Iterator var5 = attributesList.iterator();

            while(var5.hasNext()) {
               AnnotationAttributes annotationAttributes = (AnnotationAttributes)var5.next();
               AnnotationAttributes convertedAttributes = AnnotationReadingVisitorUtils.convertClassValues("method '" + this.getMethodName() + "'", this.classLoader, annotationAttributes, classValuesAsString);
               convertedAttributes.forEach(allAttributes::add);
            }
         }

         return allAttributes;
      }
   }

   public String getDeclaringClassName() {
      return this.declaringClassName;
   }

   public String getReturnTypeName() {
      return this.returnTypeName;
   }
}
