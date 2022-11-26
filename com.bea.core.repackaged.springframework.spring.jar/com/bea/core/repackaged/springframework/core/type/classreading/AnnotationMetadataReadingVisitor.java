package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.asm.AnnotationVisitor;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {
   @Nullable
   protected final ClassLoader classLoader;
   protected final Set annotationSet = new LinkedHashSet(4);
   protected final Map metaAnnotationMap = new LinkedHashMap(4);
   protected final LinkedMultiValueMap attributesMap = new LinkedMultiValueMap(4);
   protected final Set methodMetadataSet = new LinkedHashSet(4);

   public AnnotationMetadataReadingVisitor(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      return (MethodVisitor)((access & 64) != 0 ? super.visitMethod(access, name, desc, signature, exceptions) : new MethodMetadataReadingVisitor(name, access, this.getClassName(), Type.getReturnType(desc).getClassName(), this.classLoader, this.methodMetadataSet));
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      String className = Type.getType(desc).getClassName();
      this.annotationSet.add(className);
      return new AnnotationAttributesReadingVisitor(className, this.attributesMap, this.metaAnnotationMap, this.classLoader);
   }

   public Set getAnnotationTypes() {
      return this.annotationSet;
   }

   public Set getMetaAnnotationTypes(String annotationName) {
      Set metaAnnotationTypes = (Set)this.metaAnnotationMap.get(annotationName);
      return metaAnnotationTypes != null ? metaAnnotationTypes : Collections.emptySet();
   }

   public boolean hasAnnotation(String annotationName) {
      return this.annotationSet.contains(annotationName);
   }

   public boolean hasMetaAnnotation(String metaAnnotationType) {
      Collection allMetaTypes = this.metaAnnotationMap.values();
      Iterator var3 = allMetaTypes.iterator();

      Set metaTypes;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         metaTypes = (Set)var3.next();
      } while(!metaTypes.contains(metaAnnotationType));

      return true;
   }

   public boolean isAnnotated(String annotationName) {
      return !AnnotationUtils.isInJavaLangAnnotationPackage(annotationName) && this.attributesMap.containsKey(annotationName);
   }

   @Nullable
   public AnnotationAttributes getAnnotationAttributes(String annotationName) {
      return this.getAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public AnnotationAttributes getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      AnnotationAttributes raw = AnnotationReadingVisitorUtils.getMergedAnnotationAttributes(this.attributesMap, this.metaAnnotationMap, annotationName);
      return raw == null ? null : AnnotationReadingVisitorUtils.convertClassValues("class '" + this.getClassName() + "'", this.classLoader, raw, classValuesAsString);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName) {
      return this.getAllAnnotationAttributes(annotationName, false);
   }

   @Nullable
   public MultiValueMap getAllAnnotationAttributes(String annotationName, boolean classValuesAsString) {
      MultiValueMap allAttributes = new LinkedMultiValueMap();
      List attributes = this.attributesMap.get(annotationName);
      if (attributes == null) {
         return null;
      } else {
         Iterator var5 = attributes.iterator();

         while(var5.hasNext()) {
            AnnotationAttributes raw = (AnnotationAttributes)var5.next();
            Iterator var7 = AnnotationReadingVisitorUtils.convertClassValues("class '" + this.getClassName() + "'", this.classLoader, raw, classValuesAsString).entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               allAttributes.add(entry.getKey(), entry.getValue());
            }
         }

         return allAttributes;
      }
   }

   public boolean hasAnnotatedMethods(String annotationName) {
      Iterator var2 = this.methodMetadataSet.iterator();

      MethodMetadata methodMetadata;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         methodMetadata = (MethodMetadata)var2.next();
      } while(!methodMetadata.isAnnotated(annotationName));

      return true;
   }

   public Set getAnnotatedMethods(String annotationName) {
      Set annotatedMethods = new LinkedHashSet(4);
      Iterator var3 = this.methodMetadataSet.iterator();

      while(var3.hasNext()) {
         MethodMetadata methodMetadata = (MethodMetadata)var3.next();
         if (methodMetadata.isAnnotated(annotationName)) {
            annotatedMethods.add(methodMetadata);
         }
      }

      return annotatedMethods;
   }
}
