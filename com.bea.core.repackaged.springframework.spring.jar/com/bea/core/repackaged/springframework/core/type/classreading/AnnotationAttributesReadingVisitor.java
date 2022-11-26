package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class AnnotationAttributesReadingVisitor extends RecursiveAnnotationAttributesVisitor {
   private final MultiValueMap attributesMap;
   private final Map metaAnnotationMap;

   public AnnotationAttributesReadingVisitor(String annotationType, MultiValueMap attributesMap, Map metaAnnotationMap, @Nullable ClassLoader classLoader) {
      super(annotationType, new AnnotationAttributes(annotationType, classLoader), classLoader);
      this.attributesMap = attributesMap;
      this.metaAnnotationMap = metaAnnotationMap;
   }

   public void visitEnd() {
      super.visitEnd();
      Class annotationClass = this.attributes.annotationType();
      if (annotationClass != null) {
         List attributeList = (List)this.attributesMap.get(this.annotationType);
         if (attributeList == null) {
            this.attributesMap.add(this.annotationType, this.attributes);
         } else {
            attributeList.add(0, this.attributes);
         }

         if (!AnnotationUtils.isInJavaLangAnnotationPackage(annotationClass.getName())) {
            try {
               Annotation[] metaAnnotations = annotationClass.getAnnotations();
               if (!ObjectUtils.isEmpty((Object[])metaAnnotations)) {
                  Set visited = new LinkedHashSet();
                  Annotation[] var5 = metaAnnotations;
                  int var6 = metaAnnotations.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     Annotation metaAnnotation = var5[var7];
                     this.recursivelyCollectMetaAnnotations(visited, metaAnnotation);
                  }

                  if (!visited.isEmpty()) {
                     Set metaAnnotationTypeNames = new LinkedHashSet(visited.size());
                     Iterator var11 = visited.iterator();

                     while(var11.hasNext()) {
                        Annotation ann = (Annotation)var11.next();
                        metaAnnotationTypeNames.add(ann.annotationType().getName());
                     }

                     this.metaAnnotationMap.put(annotationClass.getName(), metaAnnotationTypeNames);
                  }
               }
            } catch (Throwable var9) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Failed to introspect meta-annotations on " + annotationClass + ": " + var9);
               }
            }
         }
      }

   }

   private void recursivelyCollectMetaAnnotations(Set visited, Annotation annotation) {
      Class annotationType = annotation.annotationType();
      String annotationName = annotationType.getName();
      if (!AnnotationUtils.isInJavaLangAnnotationPackage(annotationName) && visited.add(annotation)) {
         try {
            if (Modifier.isPublic(annotationType.getModifiers())) {
               this.attributesMap.add(annotationName, AnnotationUtils.getAnnotationAttributes(annotation, false, true));
            }

            Annotation[] var5 = annotationType.getAnnotations();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Annotation metaMetaAnnotation = var5[var7];
               this.recursivelyCollectMetaAnnotations(visited, metaMetaAnnotation);
            }
         } catch (Throwable var9) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Failed to introspect meta-annotations on " + annotation + ": " + var9);
            }
         }
      }

   }
}
